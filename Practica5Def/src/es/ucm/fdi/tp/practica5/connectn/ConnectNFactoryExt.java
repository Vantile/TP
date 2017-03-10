package es.ucm.fdi.tp.practica5.connectn;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNFactory;

//Extension de la factoria de ConnectN; para incorporar SwingViews.
@SuppressWarnings("serial")
public class ConnectNFactoryExt extends ConnectNFactory 
{
	
	public ConnectNFactoryExt(){
		super();
	}
	
	public ConnectNFactoryExt(int dim){
		super(dim);
	}
	
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		new ConnectNSwingView(g, c, viewPiece, random, ai);
	}
}
