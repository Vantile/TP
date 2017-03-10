package es.ucm.fdi.tp.practica6.bgame.model;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class ChangeTurnResponse implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8683093245088720646L;
	private Board board;
	private Piece turn;
	
	public ChangeTurnResponse(Board board, Piece turn){
		this.board = board;
		this.turn = turn;
	}
	
	public void run(GameObserver o){
		o.onChangeTurn(board, turn);
	}
}
