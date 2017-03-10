package es.ucm.fdi.tp.practica4.ataxx;

import java.util.List;
import java.util.Random;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Esta clase define el jugador aleatorio del juego Ataxx.
 * @see AtaxxRules
 */
public class AtaxxRandomPlayer extends Player {
	
	private static final long serialVersionUID = 1L;

	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		List<GameMove> list = rules.validMoves(board, pieces, p);
		GameMove move = list.get(randInt(0, list.size()));
		return move;
	}
	
	// Metodo que devuelve un valor aleatorio entre min(incluido) y max(excluido).
	private int randInt(int min, int max) {
		
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min)) + min;

	    return randomNum;
	}

}
