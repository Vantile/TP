package es.ucm.fdi.tp.practica5.connectn;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

/**
 * Jugador de ConnectN.
 */
@SuppressWarnings("serial")
public class ConnectNSwingPlayer extends Player {

	// Movimiento.
	private GameMove move;
	
	// Devuelve el movimiento.
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return move;
	}
	// Inicializa el movimiento.
	public void setMove(int row, int col, Piece p)
	{
		move = new ConnectNMove(row, col, p);
	}
}
