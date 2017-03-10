package es.ucm.fdi.tp.practica6.bgame.model;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

public class GameOverResponse implements Response{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1911032108123868099L;
	private Board board;
	private State state;
	private Piece winner;
	
	public GameOverResponse(Board board, State state, Piece winner){
		this.board = board;
		this.state = state;
		this.winner = winner;
	}
	
	public void run(GameObserver o){
		o.onGameOver(board, state, winner);
	}
}
