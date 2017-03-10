package es.ucm.fdi.tp.practica5.bgame.views;

import java.awt.Color;
import java.awt.event.MouseEvent;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxRules;

/**
 * Ventana del juego; incorpora un tablero rectangular.
 */
public abstract class RectBoardSwingView extends SwingView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4034943295880607516L;
	// Componente del tablero.
	private BoardComponent boardComp;
	
	public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player AIPlayer)
	{
		super(g, c, localPiece, randPlayer, AIPlayer);
	}
	// Inicializa el componente del tablero.
	protected void initBoardGUI() {
		boardComp = new BoardComponent(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -3668953702790301750L;
			// Maneja el click en el tablero.
			protected void mouseClicked(int row, int col, int numButton)
			{
				handleMouseClick(row, col, numButton);
			};
			// Si hay un color, lo devuelve. Si no, devuelve uno por defecto.
			protected Color getPieceColor(Piece p)
			{
				Color color = getPlayerColor(p);
				if(color != null)
					return color;
				else
				{
					setPlayerColor(p, Color.BLUE);
					return Color.BLUE;
				}
			};
			// Devuelve si es una pieza de jugador.
			protected boolean isPlayerPiece(Piece p)
			{
				if(p == null)
					return false;
				else
					return (!p.equals(AtaxxRules.obstacle));
			}

			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		};
		// Coloca el componente del tablero en la vista.
		setBoardArea(boardComp);
	}
	
	// Devuelve el componente del tablero.
	protected BoardComponent getBoardComp(){ return boardComp; }
	// Maneja el click del raton en el componente del tablero.
	protected abstract void handleMouseClick(int row, int col, int numButton);
	protected abstract void setMoveMode(boolean b);

}
