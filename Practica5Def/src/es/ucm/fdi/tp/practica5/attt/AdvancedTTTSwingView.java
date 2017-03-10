package es.ucm.fdi.tp.practica5.attt;

import javax.swing.JOptionPane;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.bgame.views.RectBoardSwingView;

/**
 * SwingView de ATTT.
 */
@SuppressWarnings("serial")
public class AdvancedTTTSwingView extends RectBoardSwingView {
	
	// Jugador de ATTT.
	private AdvancedTTTSwingPlayer player;
	// True si se puede mover; false si no.
	private boolean canMove;
	// Se utiliza para realizar correctamente el movimiento compuesto.
	private boolean changeMoveMode;

	public AdvancedTTTSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player AIPlayer)
	{
		super(g, c, localPiece, randPlayer, AIPlayer);
		player = new AdvancedTTTSwingPlayer();
		canMove = true;
		changeMoveMode = false;
	}
	/**
	 * Si el jugador tiene menos de 3 piezas colocadas, se realizan
	 * movimientos simples con el boton izquierdo del raton.
	 * Si el jugador tiene mas de 3 piezas colocadas, se realizan movimientos compuestos
	 * seleccionando una pieza y moviendola de sitio con el boton izquierdo.
	 * Si canMove es false, no hace nada.
	 */
	@Override
	protected void handleMouseClick(int row, int col, int numButton) 
	{
		if((this.pieceCount(this.getBoard(), this.getTurn()) >= 3))
		{
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
		else
		{
			if(canMove)
			{
				if(numButton != 1)
				{
					if(this.getBoard().getPosition(row, col) == null)
					{
						player.setSimpleMove(row, col, this.getTurn());
						player.setMove(this.getTurn());
						decideMakeManualMove(player);
						this.addMsg("Piece placed in: " + row + ", " + col + ".\n");
					}
					else
						JOptionPane.showMessageDialog(this, "That position is not empty!");
				}
			}
		}
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
	protected boolean isBoardActive() {
		return canMove;
	}

	@Override
	protected void redrawBoard() {
		this.getBoardComp().redraw(this.getBoard());

	}
	
	protected void setMoveMode(boolean b){
		this.changeMoveMode = b;
	}

	@Override
	protected String createMoveMsg() {
		if((this.pieceCount(this.getBoard(), this.getTurn()) >= 3))
		{
			return "Click on one of your pieces and move it to an empty position.\n";
		}
		else
			return "Click on an empty position to place a piece.\n";
	}

}
