package es.ucm.fdi.tp.practica6.bgame.model;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class MoveEndResponse implements Response{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3498638766615153844L;
	private Board board;
	private Piece turn;
	private boolean success;
	
	public MoveEndResponse(Board board, Piece turn, boolean success){
		this.board = board;
		this.turn = turn;
		this.success = success;
	}
	
	public void run(GameObserver o){
		o.onMoveEnd(board, turn, success);
	}
}
