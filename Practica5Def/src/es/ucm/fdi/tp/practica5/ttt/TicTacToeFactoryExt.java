package es.ucm.fdi.tp.practica5.ttt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.ttt.TicTacToeFactory;
import es.ucm.fdi.tp.practica5.connectn.ConnectNSwingView;

// Extension de la factoria de TicTacToe; para incorporar SwingViews.
// Utiliza la vista de ConnectN.
@SuppressWarnings("serial")
public class TicTacToeFactoryExt extends TicTacToeFactory {
	
	public TicTacToeFactoryExt(){ super(); }
	
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		new ConnectNSwingView(g, c, viewPiece, random, ai);
	}
}
