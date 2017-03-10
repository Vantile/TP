package es.ucm.fdi.tp.practica6.bgame.model;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {

	private int port;
	private int numPlayers;
	private int numOfConnectedPlayers;
	private GameFactory gameFactory;
	private List<Piece> pieces;
	private List<Connection> clients;
	private boolean started;
	
	volatile private ServerSocket server;
	volatile private boolean stopped;
	volatile private boolean gameOver;
	
	private JTextArea infoArea;
	
	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port)
	{
		super(new Game(gameFactory.gameRules()), pieces);
		this.port = port;
		this.pieces = pieces;
		numPlayers = pieces.size();
		numOfConnectedPlayers = 0;
		this.gameFactory = gameFactory;
		this.started = false;
		clients = new ArrayList<Connection>();
		game.addObserver(this);
	}
	
	public synchronized void makeMove(Player player){
		try{
			super.makeMove(player);
		}
		catch(GameError e)
		{
			log("GameError in MakeMove\n");
		}
	}
	
	public synchronized void stop(){
		try{
			super.stop();
			gameOver = true;
			for(Connection c : clients)
				c.stop();
			numOfConnectedPlayers = 0;
			clients = new ArrayList<Connection>();
		}
		catch(GameError e)
		{
			log("GameError in stop.\n");
		} catch (IOException e) {
			log("IOException in stop.\n");
		}
	}
	
	public synchronized void restart(){
		try{
			log("Restarting game...\n");
			super.restart();
		}
		catch(GameError e)
		{
			log("GameError in restart.\n");
		}
	}
	
	public void start(){
		controlGUI();
		startServer();
	}
	
	private void controlGUI(){
		try{
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){ initGUI(); }
			});
		} catch(InvocationTargetException | InterruptedException e){
			throw new GameError("GUI Error");
		}
	}
	
	private void initGUI(){
		JFrame window = new JFrame("Game Server");
		infoArea = new JTextArea();
		infoArea.setEditable(false);
		infoArea.setLineWrap(true);
		JScrollPane infoPane = new JScrollPane(infoArea);
		JButton quitButton = new JButton("Stop Server");
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					stopped = true;
					GameServer.this.stop();
					server.close();
					System.exit(0);
				} catch (IOException e1) {
					log("IOException in quit button.\n");
				}
			}
		});
		window.getContentPane().setLayout(new GridLayout(1, 2, 5, 5));
		window.getContentPane().add(infoPane);
		window.getContentPane().add(quitButton);
		window.setPreferredSize(new Dimension(400, 300));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}
	
	private void log(String msg){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){ infoArea.append(msg); }
		});
	}
	
	private void startServer(){
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			log("IOException in start server.\n");
		}
		stopped = false;
		
		while(!stopped){
			try {
				Socket s = server.accept();
				log("New connection accepted.\n");
				handleRequest(s);
			} catch (IOException e) {
				if(!stopped){
					log("Error waiting for a connection.");
				}
				System.err.println("Server closed.");
			}
		}
	}
	
	private void handleRequest(Socket s)
	{
		try{
			Connection c = new Connection(s);
			Object clientRequest = c.getObject();
			if ( !(clientRequest instanceof String) &&
					 !((String) clientRequest).equalsIgnoreCase("Connect")) 
			{
				log("Invalid request from client.\n");
				c.sendObject(new GameError("Invalid Request."));
				c.stop();
				return;
			}
			if(this.numPlayers <= this.numOfConnectedPlayers)
			{
				log("Connection refused with client. The game already started.\n");
				c.sendObject(new GameError("No more players can enter the server."));
				c.stop();
				return;
			}
			numOfConnectedPlayers++;
			clients.add(c);
			c.sendObject("OK");
			c.sendObject(gameFactory);
			c.sendObject(pieces.get(numOfConnectedPlayers-1));
			if(this.numPlayers == this.numOfConnectedPlayers)
			{
				log("All players connected. Starting game...\n");
				gameOver = false;
				if(!started)
				{
					super.start();
					started = true;
				}
				else
					super.restart();
			}
			startClientListener(c);
		}
		catch(IOException | ClassNotFoundException _e){
			log("Exception in handle request.\n");
		}
	}
	
	private void startClientListener(Connection c){
		gameOver = false;
		Thread t = new Thread(new Runnable(){
			public void run(){
				while(!stopped && !gameOver){
					try{
						Command cmd;
						cmd = (Command)c.getObject();
						cmd.execute(GameServer.this);
					}
					catch(ClassNotFoundException | IOException e)
					{
						if(!stopped && !gameOver)
							GameServer.this.stop();
					}
				}
			}
		});
		t.start();
	}
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		forwardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}
	
	public void forwardNotification(Response r){
		for(Connection c : clients)
		{
			try {
				c.sendObject(r);
			} catch (IOException e) {
				log("IOException in forward notification.\n");
			}
		}
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		forwardNotification(new GameOverResponse(board, state, winner));
		this.stop();
		log("Game stopped.\n");
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		forwardNotification(new MoveStartResponse(board, turn));
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		forwardNotification(new MoveEndResponse(board, turn, success));
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		forwardNotification(new ChangeTurnResponse(board, turn));
	}

	@Override
	public void onError(String msg) {
		forwardNotification(new ErrorResponse(msg));
	}
}
