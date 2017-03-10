package es.ucm.fdi.tp.practica5.ataxx;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxFactory;

// Extension de la factoria de Ataxx; para incorporar SwingViews.
@SuppressWarnings("serial")
public class AtaxxFactoryExt extends AtaxxFactory {
	
	public AtaxxFactoryExt(){
		super();
	}
	
	public AtaxxFactoryExt(int obs){
		super(obs);
	}
	
	public AtaxxFactoryExt(int tam, int obs){
		super(tam, obs);
	}

	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		try{
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new AtaxxSwingView(g, c, viewPiece, random, ai);
				}		
			});
		}
		catch(InvocationTargetException | InterruptedException e){
			throw new GameError("Error creating Swing View.");
		}
	}
}
