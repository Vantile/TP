package es.ucm.fdi.tp.practica5.connectn;

import javax.swing.JOptionPane;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.bgame.views.RectBoardSwingView;

/**
 * SwingView de ConnectN.
 */
@SuppressWarnings("serial")
public class ConnectNSwingView extends RectBoardSwingView {
	
	// Jugador Swing de ConnectN.
	private ConnectNSwingPlayer player;
	// True si se puede mover; false si no.
	private boolean canMove;

	public ConnectNSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player AIPlayer)
	{
		super(g, c, localPiece, randPlayer, AIPlayer);
		player = new ConnectNSwingPlayer();
		canMove = true;
	}
	
	/**
	 * Si canMove es false no hace nada.
	 * Si canMove es true, se realizan movimientos simples, colocando fichas
	 * en el tablero con el boton izquierdo del raton.
	 */
	@Override
	protected void handleMouseClick(int row, int col, int numButton) {
		if(canMove)
		{
			if(numButton != 1)
			{
				if(this.getBoard().getPosition(row, col) == null)
				{
					player.setMove(row, col, this.getTurn());
					decideMakeManualMove(player);
					this.addMsg("Piece placed in: " + row + ", " + col + ".\n");
				}
				else
					JOptionPane.showMessageDialog(this, "That position is not empty!");
			}
		}
		
	}
	
	protected boolean isBoardActive(){
		return canMove;
	}

	@Override
	protected void activateBoard() {
		canMove = true;

	}

	@Override
	protected void deactivateBoard() {
		canMove = false;

	}

	@Override
	protected void redrawBoard() {
		this.getBoardComp().redraw(this.getBoard());
	}
	
	protected String createMoveMsg()
	{
		return new String("Click an empty position to place a piece.\n");
	}
	
	// Este metodo no es necesario en ConnectN.
	protected void setMoveMode(boolean b){}

}
