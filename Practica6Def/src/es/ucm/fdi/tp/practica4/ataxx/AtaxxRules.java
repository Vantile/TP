package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxRules implements GameRules {
	
	// Objeto tipo Pair que hay que devolver si el juego sigue en marcha.
	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

	// Tamaño del tablero (filas == columnas == tam)
	private int tam;
	// Numero de obstaculos en el tablero.
	private int obstacles;
	// ID del obstaculo.
	private static final String obstacleId = "*";
	// Piece correspondiente al obstaculo.
	public static final Piece obstacle = new Piece(obstacleId);
	
	public AtaxxRules()
	{
		this.tam = 5;
		this.obstacles = 0;
	}
	public AtaxxRules(int tam)
	{
		if(tam % 2 == 1 && tam >= 5)
			this.tam = tam;
		else
			throw new GameError("El tamaño debe ser mayor o igual que 5x5 y con impares.");
		this.obstacles = 0;
	}
	
	public AtaxxRules(int tam, int obs)
	{
		if(tam % 2 == 1 && tam >= 5)
			this.tam = tam;
		else
			throw new GameError("El tamaño debe ser mayor o igual que 5x5 y con impares.");
		this.obstacles = obs;
	}
	
	// Metodo que devuelve un valor aleatorio entre min(incluido) y max(excluido).
	private int randInt(int min, int max) {
		
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min)) + min;

	    return randomNum;
	}
	
	public String gameDesc() {
		return ("Ataxx " + this.tam + "x" + this.tam);
	}

	public Board createBoard(List<Piece> pieces) {
		FiniteRectBoard board = new FiniteRectBoard(tam, tam);
		// Creacion de las piezas (jugadores disponibles).
		for(int i = 0; i < pieces.size(); i++)
		{
			switch(i)
			{
			case 0:
			{
				board.setPosition(0, 0, pieces.get(i));
				board.setPosition(tam-1, tam-1, pieces.get(i));
				break;
			}
			case 1:
			{
				board.setPosition(0, tam-1, pieces.get(i));
				board.setPosition(tam-1, 0, pieces.get(i));
				break;
			}
			case 2:
			{
				board.setPosition((tam-1)/2, 0, pieces.get(i));
				board.setPosition((tam-1)/2, tam-1, pieces.get(i));
				break;
			}
			case 3:
			{
				board.setPosition(0, (tam-1)/2, pieces.get(i));
				board.setPosition(tam-1, (tam-1)/2, pieces.get(i));
				break;
			}
			default:
			{
				throw new GameError("Invalid number of players.");
			}
			}
		}
		// Creacion de obstaculos (si hay mas de 0).
		if(obstacles > 0)
		{
			int i = 0;
			int row, col;
			while(i < obstacles)
			{
				row = randInt(0, board.getRows());
				col = randInt(0, board.getCols());
				if(board.getPosition(row, col) == null)
				{
					board.setPosition(row, col, obstacle);
					i++;
				}
			}
		}
		return board;
	}

	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return pieces.get(0);
	}

	public int minPlayers() {
		return 2;
	}

	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces, Piece turn) {
		// Si el tablero esta lleno, se cuentan las piezas que tiene cada jugador
		// y se decide un ganador (o empate).
		if(board.isFull())
		{
			int bestCount = 0;
			int count = 1;
			Piece winner = null;
			for(int i = 0; i < pieces.size(); i++)
			{
				// Si el numero de piezas es mayor que el guardado anteriormente,
				// hay un nuevo ganador potencial.
				if(pieceCount(board, pieces.get(i)) > bestCount)
				{
					bestCount = pieceCount(board, pieces.get(i));
					winner = pieces.get(i);
					count = 1;
				}
				// Si el numero de piezas es igual que el guardado, puede haber mas de
				// un ganador (empate).
				else if(pieceCount(board, pieces.get(i)) == bestCount)
				{
					count++;
				}	
			}
			if(count > 1)
				return new Pair<State, Piece>(State.Draw, null);
			else if(count == 1)
				return new Pair<State, Piece>(State.Won, winner);
			else
				throw new GameError("Unexpected error updating game state. Please restart the game.");
		}
		// Si el tablero no esta vacio, se decidira si se sigue jugando.
		else if(!board.isEmpty())
		{
			int playersPlaying = 0;
			// Se comprueba cuantos jugadores siguen teniendo piezas.
			for(int i = 0; i < pieces.size(); i++)
			{
				if(pieceCount(board, pieces.get(i)) > 0)
					playersPlaying++;
			}
			// Si no hay ninguno, es un error.
			if(playersPlaying == 0)
				throw new GameError("There's no players available in this game!");
			// Si hay uno, es el ganador.
			else if(playersPlaying == 1)
			{
				int i = 0;
				while(pieceCount(board, pieces.get(i)) == 0)
					i++;
				return new Pair<State, Piece>(State.Won, pieces.get(i));
			}
			// Si no hay jugadores disponibles (es decir, nadie puede mover ficha), se hace el mismo
			// procedimiento que si el tablero estuviese lleno.
			else if(!playersAvailable(board, pieces))
			{
				int bestCount = 0;
				int count = 1;
				Piece winner = null;
				for(int i = 0; i < pieces.size(); i++)
				{
					if(pieceCount(board, pieces.get(i)) > bestCount)
					{
						bestCount = pieceCount(board, pieces.get(i));
						winner = pieces.get(i);
						count = 1;
					}
					else if(pieceCount(board, pieces.get(i)) == bestCount)
					{
						count++;
					}	
				}
				if(count > 1)
					return new Pair<State, Piece>(State.Draw, null);
				else if(count == 1)
					return new Pair<State, Piece>(State.Won, winner);
				else
					throw new GameError("Unexpected error updating game state. Please restart the game.");
			}
			// En otro caso, el juego sigue.
			else
				return gameInPlayResult;
		}
		// Si el tablero esta vacio, hay un error.
		else
		{
			throw new GameError("The board is empty!");
		}
	}
	
	// Cuenta el numero de piezas de un mismo jugador que hay en el tablero.
	// (Este metodo esta hecho porque el metodo getPieceCount de board se comportaba de forma extraña.)
	private int pieceCount(Board board, Piece piece)
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

	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn) {
		int i = pieces.indexOf(turn);
		while(i <= pieces.size())
		{
			i++;
			if(i == pieces.size())
				i = 0;
			if(pieceCount(board, pieces.get(i)) > 0 && playerCanMove(board, pieces.get(i)))
				return pieces.get(i);
		}
		return null;
	}

	// Aun no disponible.
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece piece) {
		return 0;
	}
	
	// Comprueba si hay algun jugador que puede mover ficha en la partida.
	private boolean playersAvailable(Board board, List<Piece> pieces)
	{
		boolean aux = false;
		for(int i = 0; i < pieces.size() && !aux; i++)
		{
			aux = playerCanMove(board, pieces.get(i));
		}
		return aux;
	}
	
	// Comprueba si un jugador en concreto puede mover al menos una ficha.
	private boolean playerCanMove(Board board, Piece player)
	{
		boolean move = false;
		int i = 0;
		int j;
		while(i < board.getRows() && !move)
		{
			j = 0;
			while(j < board.getCols() && !move)
			{
				if(board.getPosition(i, j) != null && board.getPosition(i, j).equals(player))
					move = pieceCanBeMoved(board, i, j);
				j++;
			}
			i++;
		}
		return move;
	}
	
	// Comprueba si una ficha en concreto se puede mover a algun espacio libre.
	private boolean pieceCanBeMoved(Board board, int row, int col)
	{
		boolean move = false;
		int i = row - 2;
		int rowLim = row + 2;
		int colLim = col + 2;
		int j = col - 2;
		if(row + 2 >= board.getRows())
			rowLim = board.getRows() - 1;
		if(col + 2 >= board.getCols())
			colLim = board.getCols() - 1;
		if(row - 2 < 0)
			i = 0;
		
		while(i <= rowLim && !move)
		{
			j = col - 2;
			if(col - 2 < 0)
				j = 0;
			while(j <= colLim && !move)
			{
				move = (board.getPosition(i, j) == null);
				j++;
			}
			i++;
		}
		return move;
	}

	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List<GameMove> list = new ArrayList<GameMove>();
		for(int i = 0; i < board.getRows(); i++)
		{
			for(int j = 0; j < board.getCols(); j++)
			{
				if(board.getPosition(i, j) != null && board.getPosition(i, j).equals(turn) && 
						pieceCanBeMoved(board, i, j))
				{
					validPieceMoves(board, list, i, j);
				}
			}
		}
		return list;
	}
	
	// Añade a una List<GameMove> los movimientos que puede realizar la ficha situada en (row,col).
	private void validPieceMoves(Board board, List<GameMove> list, int row, int col)
	{
		int i = row - 2;
		int rowLim = row + 2;
		int colLim = col + 2;
		int j = col - 2;
		if(row + 2 >= board.getRows())
			rowLim = board.getRows() - 1;
		if(col + 2 >= board.getCols())
			colLim = board.getCols() - 1;
		if(row - 2 < 0)
			i = 0;
		
		while(i <= rowLim)
		{
			j = col - 2;
			if(col - 2 < 0)
				j = 0;
			while(j <= colLim)
			{
				if(board.getPosition(i, j) == null)
				{
					list.add(new AtaxxMove(row, col, i, j, board.getPosition(row, col)));
				}
				j++;
			}
			i++;
		}
	}

}
