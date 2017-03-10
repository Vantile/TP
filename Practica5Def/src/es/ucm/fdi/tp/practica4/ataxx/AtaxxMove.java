package es.ucm.fdi.tp.practica4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Clase que representa un movimiento en Ataxx.
 */
public class AtaxxMove extends GameMove {

	private static final long serialVersionUID = -8337796338772748731L;
	
	// Columna de la ficha seleccionada para mover.
	protected int selectCol;
	// Fila de la ficha seleccionada para mover.
	protected int selectRow;
	// Columna de la posicion a la que mover la ficha.
	protected int moveCol;
	// Fila de la posicion a la que mover la ficha.
	protected int moveRow;
	
	public AtaxxMove(){}
	
	public AtaxxMove(int selectRow, int selectCol, int moveRow, int moveCol, Piece p) {
		super(p);
		this.selectRow = selectRow;
		this.selectCol = selectCol;
		this.moveRow = moveRow;
		this.moveCol = moveCol;
	}

	public void execute(Board board, List<Piece> pieces) {
		// Comprueba que el movimiento es valido (no se sale de los limites) y la ficha seleccionada
		// es del jugador que esta jugando.
		if (validMove(board, pieces) && this.getPiece().equals(board.getPosition(selectRow, selectCol))) 
		{
			// Comprueba si el movimiento es adyacente o no.
			if(adjacentMove())
			{
				board.setPosition(moveRow, moveCol, getPiece());
				spread(board, pieces);
			}
			else
			{
				board.setPosition(moveRow, moveCol, getPiece());
				board.setPosition(selectRow, selectCol, null);
				spread(board, pieces);
			}
		} 
		else 
		{
			throw new GameError("Invalid move!");
		}
	}
	
	// Comprueba si un movimiento es valido; si no esta ocupada la posicion a la que mover la ficha
	// y el movimiento no sobrepasa los limites del tablero y del movimiento.
	private boolean validMove(Board board, List<Piece> pieces)
	{
		if(board.getPosition(moveRow, moveCol) != null)
			throw new GameError("position (" + moveRow + "," + moveCol + ") is already occupied!");
		else if(moveRow >= selectRow-2 && moveRow <= selectRow+2 && moveCol >= selectCol-2 && moveCol <= selectCol+2 &&
				moveRow >= 0 && selectRow >= 0 && moveRow < board.getRows() && selectRow < board.getRows() &&
				moveCol >= 0 && selectCol >= 0 && moveCol < board.getCols() && selectCol < board.getCols())
			return true;
		else 
			return false;
	}
	
	// Comprueba si el movimiento de una ficha es a una posicion adyacente.
	private boolean adjacentMove()
	{
		boolean aux;
		if(moveCol < selectCol-1 || moveCol > selectCol+1 || moveRow < selectRow-1 || moveRow > selectRow+1)
			aux = false;
		else
			aux = true;
		return aux;
	}
	
	// "Contagia" a las fichas contiguas a la ficha movida, para convertirlas en fichas del jugadorr
	// que ha ejecutado el movimiento. (Los obstaculos no cambian)
	private void spread(Board board, List<Piece> pieces)
	{
		int i = moveRow - 1;
		int j;
		while(i <= moveRow + 1)
		{
			j = moveCol - 1;
			while(j <= moveCol + 1)
			{
				if(i >= 0 && i < board.getRows() && j >= 0 && j < board.getCols())
				{
					Piece aux = board.getPosition(i, j);
					if(aux != null && !aux.equals(getPiece()) && !aux.equals(AtaxxRules.obstacle))
					{
						board.setPosition(i, j, getPiece());
					}
				}
				j++;
			}
			i++;
		}
	}

	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 4) {
			return null;
		}

		try {
			int selectRow, selectCol, moveRow, moveCol;
			selectRow = Integer.parseInt(words[0]);
			selectCol = Integer.parseInt(words[1]);
			moveRow = Integer.parseInt(words[2]);
			moveCol = Integer.parseInt(words[3]);
			return createMove(selectRow, selectCol, moveRow, moveCol, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	protected GameMove createMove(int selectRow, int selectCol, int moveRow, int moveCol, Piece p) {
		return new AtaxxMove(selectRow, selectCol, moveRow, moveCol, p);
	}

	@Override
	public String help() {
		return "Select a piece of the board with 'row column' and then place it with 'row column' "
				+ "(row column row column).";
	}
	
	public String toString() {
		if (getPiece() == null) {
			return help();
		} else {
			return "Select a piece (" + selectRow + "," + selectCol + ")" + 
					"'" + getPiece() + "' and place it at (" + moveRow + "," + moveCol + ")";
		}
	}

}
