package es.ucm.fdi.tp.practica5.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Jugador Swing de ATTT.
 */
@SuppressWarnings("serial")
public class AdvancedTTTSwingPlayer extends Player {
	
	// Movimiento de ATTT.
	private AdvancedTTTMove move;
	// Fila de la ficha seleccionada.
	private int srcRow;
	// Columna de la ficha seleccionada.
	private int srcCol;
	// Fila del hueco seleccionado.
	private int row;
	// Columna del hueco seleccionado.
	private int col;

	// Devuelve el movimiento correspondiente.
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
			return move;
	}
	
	/**
	 * Construye la primera parte del movimiento.
	 */
	public void setSourceMove(int srcRow, int srcCol, Piece p)
	{
		this.srcRow = srcRow;
		this.srcCol = srcCol;
	}
	
	/**
	 * Realiza un movimiento simple.
	 */
	public void setSimpleMove(int row, int col, Piece p)
	{
		//move = new AdvancedTTTMove(-1, -1, row, col, p);
		this.row = row;
		this.col = col;
		this.srcRow = -1;
		this.srcCol = -1;
	}
	
	/**
	 * Completa un movimiento compuesto.
	 */
	public void completeMove(int row, int col, Piece p)
	{
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Inicializa el movimiento.
	 */
	public void setMove(Piece p)
	{
		move = new AdvancedTTTMove(this.srcRow, this.srcCol, this.row, this.col, p);
	}
	
	

}
