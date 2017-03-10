package es.ucm.fdi.tp.practica5.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxMove;

/**
 * Jugador de Ataxx para la vista Swing.
 */
@SuppressWarnings("serial")
public class AtaxxSwingPlayer extends Player {
	
	// Movimiento Ataxx.
	private AtaxxMove move;
	// Fila de ficha seleccionada.
	private int selectRow;
	// Columna de ficha seleccionada.
	private int selectCol;
	// Fila de espacio seleccionado.
	private int moveRow;
	// Columna de espacio seleccionado.
	private int moveCol;

	/**
	 * Devuelve el movimiento correspondiente.
	 */
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return move;
	}
	
	/**
	 * Construye la primera parte del movimiento.
	 */
	public void setSourceMove(int row, int col, Piece p)
	{
		this.selectRow = row;
		this.selectCol = col;
	}
	
	/**
	 * Completa el movimiento.
	 */
	public void completeMove(int row, int col, Piece p)
	{
		this.moveRow = row;
		this.moveCol = col;
	}
	
	/**
	 * Inicializa el movimiento.
	 */
	public void setMove(Piece p)
	{
		this.move = new AtaxxMove(this.selectRow, this.selectCol, this.moveRow, this.moveCol, p);
	}

}
