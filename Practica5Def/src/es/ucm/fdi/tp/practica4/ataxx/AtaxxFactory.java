package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

/**
 * Factoria para la creacion de juegos Ataxx.
 * @see AtaxxRules
 */
public class AtaxxFactory implements GameFactory {

	// Tamaño del tablero (filas == columnas == tam).
	private int tam;
	// Numero de obstaculos en el tablero.
	private int obstacles;
	
	public AtaxxFactory()
	{
		this.tam = 5;
		this.obstacles = 0;
	}
	public AtaxxFactory(int obs)
	{
		this.tam = 5;
		this.obstacles = obs;
	}
	public AtaxxFactory(int tam, int obs)
	{
		if(tam % 2 == 1 && tam >= 5)
			this.tam = tam;
		else
			throw new GameError("El tamaño debe ser mayor o igual que 5x5 y con impares.");
		this.obstacles = obs;
	}
	
	public GameRules gameRules() {
		return new AtaxxRules(tam, obstacles);
	}

	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new AtaxxMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	public Player createRandomPlayer() {
		return new AtaxxRandomPlayer();
	}

	// Aun no disponible.
	public Player createAIPlayer(AIAlgorithm alg) {
		return null;
	}

	// Por defecto, son dos jugadores con piezas "X" y "O".
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}

	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		new GenericConsoleView(game, ctrl);

	}

	// Aun no disponible.
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		throw new UnsupportedOperationException("There is no swing view");

	}

}
