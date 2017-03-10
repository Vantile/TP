package es.ucm.fdi.tp.practica5.ataxx;

import javax.swing.JOptionPane;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.bgame.views.RectBoardSwingView;

/**
 * SwingView de Ataxx. 
 */
@SuppressWarnings("serial")
public class AtaxxSwingView extends RectBoardSwingView {
	
	// Jugador Swing de Ataxx.
	private AtaxxSwingPlayer player;
	// True si se puede mover. Se usa para activar/desactivar el tablero.
	private boolean canMove;
	// Se utiliza para realizar correctamente el movimiento compuesto.
	private boolean changeMoveMode;

	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player AIPlayer)
	{
		super(g, c, localPiece, randPlayer, AIPlayer);
		player = new AtaxxSwingPlayer();
		canMove = true;
		changeMoveMode = false;
	}
	/**
	 * Si changeMoveMode es false, el jugador tiene que seleccionar una ficha
	 * que le pertenezca con el boton izquierdo del raton.
	 * Si changeMoveMode es true, el jugador tiene que seleccionar un hueco vacío
	 * con el boton izquierdo del raton o cancelar el movimiento con el boton derecho.
	 * Si canMove es false, no se hace nada.
	 */
	@Override
	protected void handleMouseClick(int row, int col, int numButton) {
		if(canMove)
		{
				if(!changeMoveMode)
				{
					if(this.getBoard().getPosition(row, col) != null && this.getTurn().equals(this.getBoard().getPosition(row, col)) && numButton != 1)
					{
						player.setSourceMove(row, col, this.getTurn());
						this.addMsg("Clicked on " + row + ", " + col + ".\n");
						this.changeMoveMode = true;
					}
					else if(this.getBoard().getPosition(row, col) == null)
						JOptionPane.showMessageDialog(this, "That position is empty!");
					else if(!(this.getTurn().equals(this.getBoard().getPosition(row, col))))
						JOptionPane.showMessageDialog(this, "This piece is not yours!");
				}
				else
				{
					if(numButton == 0)
					{
						if(this.getBoard().getPosition(row, col) == null)
						{
							player.completeMove(row, col,this.getTurn());
							player.setMove(this.getTurn());
							decideMakeManualMove(player);
							this.addMsg("Piece moved to " + row + ", " + col + ".\n");
							this.changeMoveMode = false;
						}
						else
						{
							JOptionPane.showMessageDialog(this, "That position is not empty! Move cancelled.");
							this.changeMoveMode = false;
						}
					}
					else if(numButton == 1)
					{
						this.addMsg("Move cancelled.\n");
						this.addMsg(this.createMoveMsg());
						this.changeMoveMode = false;
					}
				}
		}

	}

	// Activa el tablero.
	@Override
	protected void activateBoard() {
		canMove = true;

	}

	// Desactiva el tablero.
	@Override
	protected void deactivateBoard() {
		canMove = false;

	}

	// Devuelve si el tablero esta activo.
	@Override
	protected boolean isBoardActive() {
		return canMove;
	}
	
	protected void setMoveMode(boolean b){
		this.changeMoveMode = b;
	}

	// Redibuja el tablero.
	@Override
	protected void redrawBoard() {
		this.getBoardComp().redraw(this.getBoard());

	}

	// Crea el mensaje de ayuda de movimiento.
	@Override
	protected String createMoveMsg() {
		return "Click on one of your pieces and move it to an empty position.\n";
	}

}
