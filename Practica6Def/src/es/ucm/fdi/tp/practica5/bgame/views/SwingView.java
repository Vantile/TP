package es.ucm.fdi.tp.practica5.bgame.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Ventana principal de la vista de los juegos.
 * El panel de control (derecha) se forma aqui, mientras que
 * el panel del tablero se delega en las subclases.
 */
@SuppressWarnings("serial")
public abstract class SwingView extends JFrame implements GameObserver {

	/**
	 * Player modes (manual, random, etc.)
	 * <p>
	 * Modos de juego.
	 */
	enum PlayerMode {
		MANUAL("m", "Manual"), RANDOM("r", "Random"), AI("a", "Automatics");

		private String id;
		private String desc;

		PlayerMode(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}
	
	// Componentes de la vista.
	// Panel del estado del juego. Incluye un JTextArea con un JScrollPane por el que
	// se muestran los mensajes del juego.
	private JPanel statusPanel;
		private JTextArea statusText;
		private JScrollPane statusScl;
		
	// Panel de la tabla de los jugadores. Incluye una tabla en la que se muestran datos de 
	// los jugadores, como su ID, su modo de juego, el numero de fichas en el tablero y el color.
	// En el modo multiviews, solo se muestra el modo de juego del jugador propietario de la ventana.
	private JPanel tablePanel;
		// Array de Strings que forman la cabecera de la tabla.
		private String[] tableHeader = {"Player ID", "Player Mode", "Piece Count" };
		// Array doble que contiene los datos de la tabla.
		private Object[][] tableData;
		// Tabla que se muestra por pantalla.
		private JTable playerTable;
		
	// Panel que contiene los componentes para cambiar el color de los jugadores.
	private JPanel changeColorPanel;
		// ComboBox que contiene la lista de jugadores en la partida.
		private JComboBox<String> piecesList;
		// Boton que efectua el cambio de color.
		private JButton changeColorButton;
	
	// Panel que contiene los componentes para cambiar el modo de juego de los jugadores.
	// Solo apareceran los modos de juego disponibles en el juego correspondiente.
	// En el modo multiviews, solo sera posible cambiar el modo de jugador del propietario de la ventana.
	private JPanel changeModePanel;
		// ComboBox que contiene la lista de jugadores. En modo multiviews, solo contendra 
		// el jugador al que pertenece la ventana.
		private JComboBox<String> playersIdCombo;
		// ComboBox que contiene los modos de juego disponibles.
		private JComboBox<String> playerModeCombo;
		// Boton que efectua el cambio de modo.
		private JButton setPlayerModeBtn;
		
	// Panel que contiene los botones de movimiento automatico. En el modo multiviews, solo podran
	// usarse cuando sea el turno del jugador al que pertenece la ventana.
	private JPanel autoMovesPanel;
		// Boton que realiza un movimiento aleatorio. Si no se permiten, este boton estara deshabilitado.
		private JButton randomMoveBtn;
		// Boton que realiza un movimiento inteligente. Si no se permiten, este boton estara deshabilitado.
		private JButton AIMoveBtn;
	
	// Panel que contiene los botones de salir y reiniciar.
	private JPanel generalBtnsPanel;
		// Boton para salir del juego. Pide confirmacion al salir.
		private JButton quitBtn;
		// Boton para reiniciar el juego.
		private JButton restartBtn;
	
	// Array de Strings que contiene las IDs de los jugadores.
	private String[] piecesId;
	
	// Resolucion por defecto. Se recomienda no cambiar; la ventana se descuadra.
	protected static final int DEFAULT_WIDTH = 800;
	protected static final int DEFAULT_HEIGHT = 600;
	
	// Panel que contiene el tablero.
	protected JPanel boardPanel;
	// Panel que contiene el sistema de control del juego.
	protected JPanel controlPanel;
	
	// True si el juego se esta reiniciando; false si no.
	private boolean restarting;
	// Nombre del juego. Se usa para titular la ventana principal.
	private String gameDesc;
	// Controlador.
	private Controller ctrl;
	// Pieza a la que pertenece la ventana.
	private Piece LocalPiece; // Si es null, no hay multiviews; ventana única.
	// Pieza a la que pertenece el turno.
	private Piece turn;
	// Tablero del juego. Es de solo lectura.
	private Board board;
	// Lista de piezas en el juego.
	private List<Piece> pieces;
	// Mapa que asigna piezas a colores.
	private Map<Piece, Color> pieceColors;
	// Mapa que asigna piezas a modos de juego.
	private Map<Piece, PlayerMode> playerTypes;
	// Jugador aleatorio.
	private Player randPlayer;
	// Jugador inteligente.
	private Player AIPlayer;
	
	/**
	 * Constructora de la vista. Se inicializan los atributos de la clase y
	 * se crea la vista.
	 * @param g Observable
	 * @param c Controlador
	 * @param localPiece Pieza local
	 * @param randPlayer Jugador aleatorio
	 * @param AIPlayer Jugador inteligente
	 */
	public SwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player AIPlayer)
	{
		super();
		// Window Listener para confirmar al intentar salir del juego.
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we)
			  { 
			    String ObjButtons[] = {"Yes","No"};
			    int PromptResult = JOptionPane.showOptionDialog(null, 
			        "Are you sure you want to exit?", "Quit confirmation", 
			        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
			        ObjButtons,ObjButtons[1]);
			    if(PromptResult==0)
			    {
			    	c.stop();
			    	System.exit(0);          
			    }
			  }
			});
		restarting = false;
		ctrl = c;
		this.LocalPiece = localPiece;
		this.randPlayer = randPlayer;
		this.AIPlayer = AIPlayer;
		// Inicializa la GUI.
		initGUI();
		// Añade la ventana a la lista de observadores.
		g.addObserver(this);
	}
	
	/**
	 * Inicializa la GUI. Aqui se crea todo el contenido de los paneles, pero no se añade aun
	 * a la ventana.
	 */
	private void initGUI()
	{
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.getContentPane().setLayout(new BorderLayout());
		boardPanel = new JPanel();
		controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(((DEFAULT_WIDTH)/3), DEFAULT_HEIGHT));
		// Inicializa la zona del tablero. Delega en subclase.
		this.initBoardGUI();
		// Inicializa la zona de control.
		this.createControlPanel();
	}
	
	/**
	 * Crea el panel de control de la ventana.
	 */
	private void createControlPanel()
	{
		this.getContentPane().add(controlPanel, BorderLayout.EAST);
		createStatusPanel();
		parseTableData();
		changePieceColorsPanel();
		changePlayerModePanel();
		automaticMovesPanel();
		generalPanel();
	}
	
	/**
	 * Crea el panel de estado del juego. Se añade directamente.
	 */
	private void createStatusPanel()
	{
		statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension((DEFAULT_WIDTH/3),
				(DEFAULT_HEIGHT/3)));
		statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Status Messages", TitledBorder.LEFT, TitledBorder.TOP));
		statusPanel.setLayout(new BorderLayout());
		statusText = new JTextArea(14, 28);
		statusScl = new JScrollPane(statusText);
		statusScl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		statusScl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		statusText.setEditable(false);
		statusText.setLineWrap(true);
		statusPanel.add(statusScl, BorderLayout.CENTER);
		controlPanel.add(statusPanel);
	}
	
	/**
	 * Crea el panel de la tabla de jugadores.
	 */
	private void parseTableData()
	{
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Player Info.", TitledBorder.LEFT, TitledBorder.TOP));
	}
	
	/**
	 * Crea el panel de cambio de color de los jugadores.
	 */
	private void changePieceColorsPanel()
	{
		changeColorPanel = new JPanel();
		changeColorPanel.setLayout(new FlowLayout());
		changeColorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Piece Colors", TitledBorder.LEFT, TitledBorder.TOP));
		piecesList = new JComboBox<String>();
		changeColorButton = new JButton("Change color");
		changeColorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				// Aparece un JDialog con un JColorChooser para seleccionar un color.
				Color color = JColorChooser.showDialog(changeColorPanel, "Color chooser", Color.BLACK);
				Piece selectedPiece = pieces.get(piecesList.getSelectedIndex());
				pieceColors.put(selectedPiece, color);
				playerTable.repaint();
				redrawBoard();
			}
		});
		changeColorPanel.add(piecesList);
		changeColorPanel.add(changeColorButton);
	}
	
	/**
	 * Crea el panel de cambio de modo de los jugadores.
	 */
	private void changePlayerModePanel()
	{
		changeModePanel = new JPanel();
		changeModePanel.setLayout(new FlowLayout());
		changeModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Player Modes", TitledBorder.LEFT, TitledBorder.TOP));
		playersIdCombo = new JComboBox<String>();
		playerModeCombo = new JComboBox<String>();
		playerModeCombo.addItem(PlayerMode.MANUAL.getDesc());
		if(randPlayer != null) // Si no hay jugador aleatorio, no añade la opcion.
			playerModeCombo.addItem(PlayerMode.RANDOM.getDesc());
		if(AIPlayer != null) // Si no hay jugador inteligente, no añade la opcion.
			playerModeCombo.addItem(PlayerMode.AI.getDesc());
		setPlayerModeBtn = new JButton("Set");
		setPlayerModeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				String pieceDesc = (String) playersIdCombo.getSelectedItem();
				String modeDesc = playerModeCombo.getSelectedItem().toString();
				int i = 0;
				while(i < pieces.size() && !(pieces.get(i).getId().equals(pieceDesc)))
					i++;
				if(i < pieces.size())
				{
					Piece playerSelected = pieces.get(i);
					PlayerMode modeSelected;
					if(modeDesc.equals("Manual"))
						modeSelected = PlayerMode.MANUAL;
					else
					{
						if(modeDesc.equals("Random"))
							modeSelected = PlayerMode.RANDOM;
						else
							modeSelected = PlayerMode.AI;
					}
					playerTypes.put(playerSelected, modeSelected);
					playerTable.getModel().setValueAt(modeDesc, i, 1);
					((AbstractTableModel) playerTable.getModel()).fireTableDataChanged();
					setMoveMode(false);
					if(playerTypes.get(turn).equals(PlayerMode.RANDOM) || playerTypes.get(turn).equals(PlayerMode.AI))
						decideMakeAutomaticMove();
				}
			}
		});
		changeModePanel.add(playersIdCombo);
		changeModePanel.add(playerModeCombo);
		changeModePanel.add(setPlayerModeBtn);
	}
	
	/**
	 * Crea el panel de botones de movimientos automaticos.
	 */
	private void automaticMovesPanel()
	{
		autoMovesPanel = new JPanel();
		autoMovesPanel.setLayout(new FlowLayout());
		autoMovesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Automatic Moves", TitledBorder.LEFT, TitledBorder.TOP));
		randomMoveBtn = new JButton("Random");
		AIMoveBtn = new JButton("Intelligent");
		autoMovesPanel.add(randomMoveBtn);
		autoMovesPanel.add(AIMoveBtn);
		if(randPlayer == null) // Si no hay jugador aleatorio, desactiva el boton.
			randomMoveBtn.setEnabled(false);;
		if(AIPlayer == null) // Si no hay jugador inteligente, desactiva el boton.
			AIMoveBtn.setEnabled(false);
		randomMoveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { 
				if(LocalPiece == null)
					ctrl.makeMove(randPlayer); 
				else if(LocalPiece.equals(turn))
					ctrl.makeMove(randPlayer); 
				else
					addMsg("It's not your turn!\n");
				}
		});
		AIMoveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { 
				if(LocalPiece == null)
				{
					if(AIPlayer != null)
						ctrl.makeMove(AIPlayer); 
					else
						addMsg("You can't make intelligent moves!\n");
				}
				else if(LocalPiece.equals(turn))
				{
					if(AIPlayer != null)
						ctrl.makeMove(AIPlayer); 
					else
						addMsg("You can't make intelligent moves!\n");
				}
				else
					addMsg("It's not your turn!\n");
			}
		});
	}
	
	/**
	 * Crea el panel de los botones de cerrar y reiniciar.
	 */
	private void generalPanel()
	{
		generalBtnsPanel = new JPanel();
		generalBtnsPanel.setLayout(new FlowLayout());
		quitBtn = new JButton("Quit");
		quitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				closeFrame();
			}
		});
		restartBtn = new JButton("Restart");
		restartBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				restarting = true;
				ctrl.restart();
				setStatusTextEmpty();
				redrawBoard();
				refreshPlayerTable();
			}
		});
		generalBtnsPanel.add(quitBtn);
		generalBtnsPanel.add(restartBtn);
	}
	
	// Metodos usados por las subclases.
	// Getter de turn.
	final protected Piece getTurn(){ return turn; }
	// Getter de board.
	final protected Board getBoard(){ return board; }
	// Getter del controlador.
	final protected Controller getCtrl() { return ctrl; }
	// Getter de pieces.
	final protected List<Piece> getPieces(){ return pieces; }
	// Devuelve el color asociado al jugador p.
	final protected Color getPlayerColor(Piece p){ return pieceColors.get(p); }
	// Asocia el color c al jugador p.
	final protected Color setPlayerColor(Piece p, Color c){ return pieceColors.put(p, c); }
	// Añade el panel del tablero a la ventana principal.
	final protected void setBoardArea(BoardComponent c){
		this.boardPanel = c.getBoardPanel();
		this.getContentPane().add(boardPanel, BorderLayout.WEST);
	}
	// Muestra un mensaje en la ventana de estado.
	final protected void addMsg(String msg){ statusText.append(msg); }
	
	final protected void setStatusTextEmpty(){ this.statusText.setText(null); }
	// Realiza un movimiento manual.
	final protected void decideMakeManualMove(Player manualPlayer)
	{
		ctrl.makeMove(manualPlayer);
	}
	// Realiza un movimiento automatico.
	private void decideMakeAutomaticMove()
	{
		if(playerTypes.get(turn).equals(PlayerMode.RANDOM))
			ctrl.makeMove(randPlayer);
		else if(playerTypes.get(turn).equals(PlayerMode.AI))
			ctrl.makeMove(AIPlayer);
	}
	// Cierra la ventana.
	private void closeFrame()
	{ 
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); 
	}
	// Refresca la tabla de jugadores.
	private void refreshPlayerTable()
	{
		for(int i = 0; i < playerTable.getRowCount(); i++)
		{
			playerTable.getModel().setValueAt(pieces.get(i).getId(), i, 0);
			if(LocalPiece == null)
				playerTable.getModel().setValueAt((playerTypes.get(pieces.get(i)).getDesc()), i, 1);
			else if(pieces.get(i).equals(LocalPiece))
				playerTable.getModel().setValueAt((playerTypes.get(pieces.get(i)).getDesc()), i, 1);
			else
				playerTable.getModel().setValueAt("", i, 1);
			playerTable.getModel().setValueAt(Integer.toString(this.pieceCount(this.board, pieces.get(i))), i, 2);
		}
		((AbstractTableModel) playerTable.getModel()).fireTableDataChanged();
	}
	// Activa/desactiva los botones del panel de control.
	private void areButtonsEnabled(boolean b)
	{
		changeColorButton.setEnabled(b);
		setPlayerModeBtn.setEnabled(b);
		if(randPlayer != null)
			randomMoveBtn.setEnabled(b);
		if(AIPlayer != null)
			AIMoveBtn.setEnabled(b);
	}
	
	// Cuenta el numero de piezas de un mismo jugador que hay en el tablero.
	// (Este metodo esta hecho porque el metodo getPieceCount de board se comportaba de forma extraña.)
	protected int pieceCount(Board board, Piece piece)
	{
		int count = 0;
		for(int i = 0; i < board.getRows(); i++)
		{
			for(int j = 0; j < board.getCols(); j++)
			{
				if(board.getPosition(i, j) != null && board.getPosition(i, j).equals(piece))
					count++;
			}
		}
		return count;
	}
	
	// Clases que deben implementar las subclases.
	/**
	 * Crea el panel del tablero.
	 */
	protected abstract void initBoardGUI();
	/**
	 * Activa el tablero (permite mover fichas).
	 */
	protected abstract void activateBoard();
	/**
	 * Desactiva el tablero (no permite mover fichas).
	 */
	protected abstract void deactivateBoard();
	/**
	 * Devuelve si el tablero esta activo.
	 */
	protected abstract boolean isBoardActive();
	/**
	 * (Re)Dibuja el tablero.
	 */
	protected abstract void redrawBoard();
	/**
	 * Devuelve el mensaje de ayuda de movimiento. Delega en subclases porque depende del juego.
	 */
	protected abstract String createMoveMsg();
	/**
	 * Utilizado en juegos con movimientos compuestos (Ataxx, Attt...)
	 * Se usa con true si se realiza un click y hay que realizar un segundo.
	 */
	protected abstract void setMoveMode(boolean b);
	
	// Metodos del observador.
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) 
	{
		// Si no se esta reiniciando, se inicia desde el principio.
		if(!restarting)
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){ 
					handleOnGameStart(board, gameDesc, pieces, turn); }
			});
		}
		// Si se esta reiniciando, llama a un metodo aparte.
		else
			handleOnGameRestart(board, gameDesc, pieces, turn);
	}
	
	/**
	 * Se añaden todos los paneles al panel de control y se actualizan los datos
	 * cuando empieza el juego. Cuando acaba, la ventana se vuelve visible.
	 */
	private void handleOnGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn)
	{
		this.gameDesc = gameDesc;
		if(LocalPiece == null)
			this.setTitle("Playing " + gameDesc);
		else
			this.setTitle("Playing " + gameDesc + " - Player " + LocalPiece.getId());
		this.pieces = pieces;
		this.board = board;
		this.turn = turn;
		pieceColors = new HashMap<Piece, Color>();
		playerTypes = new HashMap<Piece, PlayerMode>();
		for(int i = 0; i < pieces.size(); i++)
		{
			switch (i)
			{
			case 0:
				pieceColors.put(pieces.get(i), Color.CYAN);
				break;
			case 1:
				pieceColors.put(pieces.get(i), Color.YELLOW);
				break;
			case 2:
				pieceColors.put(pieces.get(i), Color.RED);
				break;
			case 3:
				pieceColors.put(pieces.get(i), Color.WHITE);
				break;
			}
		}
		for(int i = 0; i < pieces.size(); i++)
			playerTypes.put(pieces.get(i), PlayerMode.MANUAL);
		if(!restarting)
		{
			tableData = new String[pieces.size()][3];
			for(int i = 0; i < pieces.size(); i++)
			{
				tableData[i][0] = pieces.get(i).getId();
				if(LocalPiece == null)
					tableData[i][1] = playerTypes.get(pieces.get(i)).getDesc();
				else if(pieces.get(i).equals(LocalPiece))
					tableData[i][1] = playerTypes.get(pieces.get(i)).getDesc();
				else
					tableData[i][1] = "";
				tableData[i][2] = Integer.toString(this.pieceCount(this.board, pieces.get(i)));
			}
			piecesId = new String[pieces.size()];
			piecesList.removeAllItems();
			playersIdCombo.removeAllItems();
			for(Piece p : pieces)
			{
				piecesList.addItem(p.getId());
				if(LocalPiece == null)
					playersIdCombo.addItem(p.getId());
				else if(LocalPiece.equals(p))
					playersIdCombo.addItem(p.getId());
			}
			playerTable = new JTable(tableData, tableHeader){
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
				{
					Component c = super.prepareRenderer(renderer, row, column);
					c.setBackground(getPlayerColor(pieces.get(row)));
					return c;
				}
			};
			tablePanel.add(playerTable.getTableHeader(), BorderLayout.NORTH);
			tablePanel.add(playerTable, BorderLayout.CENTER);
			controlPanel.add(tablePanel);
			controlPanel.add(changeColorPanel);
			controlPanel.add(changeModePanel);
			controlPanel.add(autoMovesPanel);
			controlPanel.add(generalBtnsPanel);
			redrawBoard();
			this.setVisible(true);
			if(LocalPiece == null)
			{
				this.addMsg("Turn for " + this.turn.getId() + ".\n");
				this.addMsg(this.createMoveMsg());
				this.activateBoard();
			}
			else if(turn.equals(LocalPiece))
			{
				this.addMsg("Turn for " + this.turn.getId() + " (You!).\n");
				this.addMsg(this.createMoveMsg());
				this.activateBoard();
			}
			else
			{
				this.addMsg("Turn for " + this.turn.getId() + ".\n");
				this.deactivateBoard();
			}
			this.pack();
		}
		this.areButtonsEnabled(true);
		restarting = false;
	}
	
	// Reinicia el juego.
	private void handleOnGameRestart(Board board, String gameDesc, List<Piece> pieces, Piece turn)
	{
		this.gameDesc = gameDesc;
		this.setTitle("Playing " + gameDesc);
		this.pieces = pieces;
		this.board = board;
		this.turn = turn;
		playerTypes = new HashMap<Piece, PlayerMode>();
		for(int i = 0; i < pieces.size(); i++)
			playerTypes.put(pieces.get(i), PlayerMode.MANUAL);
		if(!isBoardActive())
		{
			this.activateBoard();
		}
		this.setStatusTextEmpty();
		if(LocalPiece == null)
		{
			this.addMsg("Turn for " + this.turn.getId() + ".\n");
			this.addMsg(this.createMoveMsg());
			this.activateBoard();
		}
		else if(turn.equals(LocalPiece))
		{
			this.addMsg("Turn for " + this.turn.getId() + " (You!).\n");
			this.addMsg(this.createMoveMsg());
			this.activateBoard();
		}
		else
		{
			this.addMsg("Turn for " + this.turn.getId() + ".\n");
			this.deactivateBoard();
		}
		redrawBoard();
		refreshPlayerTable();
		this.areButtonsEnabled(true);
		restarting = false;
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){ handleOnGameOver(board, state, winner); }
		});
	}
	
	// Cuando acaba el juego, se realiza esta funcion.
	// Se redibuja el tablero, se desactiva y se muestra el resultado.
	private void handleOnGameOver(Board board, State state, Piece winner)
	{
		this.redrawBoard();
		this.areButtonsEnabled(false);
		this.deactivateBoard();
		if(winner != null)
		{
			if(LocalPiece != null && LocalPiece.equals(winner))
				JOptionPane.showMessageDialog(this, "You won!");
			else
				JOptionPane.showMessageDialog(this, "The winner is: " + winner.getId() + ".");
			this.addMsg("The winner is: " + winner.getId() + ".\n");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Draw!");
			this.addMsg("Draw!\n");
		}
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){ deactivateBoard(); }
		});

	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){ 
				if(success)
				{
					SwingView.this.board = board;
					activateBoard();
					refreshPlayerTable();
					areButtonsEnabled(true);
				}
			}
		});
		

	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){ handleOnChangeTurn(board, turn); }
		});
	}
	
	// Cuando el turno cambio, se redibuja el tablero, se muestra el mensaje de movimiento y se activa.
	private void handleOnChangeTurn(Board board, Piece turn)
	{
		this.redrawBoard();
		this.turn = turn;
		if(LocalPiece == null)
		{
			this.addMsg("Turn for " + this.turn.getId() + ".\n");
			this.addMsg(this.createMoveMsg());
			this.activateBoard();
		}
		else if(turn.equals(LocalPiece))
		{
			this.addMsg("Turn for " + this.turn.getId() + " (You!).\n");
			this.addMsg(this.createMoveMsg());
			this.activateBoard();
		}
		else
		{
			this.addMsg("Turn for " + this.turn.getId() + ".\n");
			this.deactivateBoard();
		}
		if(playerTypes.get(turn).equals(PlayerMode.RANDOM) || playerTypes.get(turn).equals(PlayerMode.AI))
			decideMakeAutomaticMove();
	}

	@Override
	public void onError(String msg) {
		if(LocalPiece.equals(turn))
			JOptionPane.showMessageDialog(this, msg);
		this.activateBoard();	
		areButtonsEnabled(true);
	}

}
