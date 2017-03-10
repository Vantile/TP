package es.ucm.fdi.tp.practica5.bgame.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxRules;

/**
 * Componente del tablero. Contiene un panel que se divide en rowsXcols y un array de
 * boardButtons.
 */
@SuppressWarnings("serial")
public abstract class BoardComponent extends JComponent implements MouseListener {

	// Tablero del juego.
	private Board board;
	// Panel del tablero.
	private JPanel boardPanel;
	// Filas del tablero.
	private int rows;
	// Columnas del tablero.
	private int cols;
	// Array de botones.
	private BoardButton[][] boardButtons;
	
	public BoardComponent(){
		boardPanel = new JPanel();
	}
	
	// (Re)dibuja el tablero dependiendo del tablero del juego.
	public void redraw(Board b)
	{
		board = b;
		// Si el array de botones es null, es la primera vez que se dibuja el tablero.
		if(boardButtons == null)
		{
			this.rows = board.getRows();
			this.cols = board.getCols();
			boardPanel.setPreferredSize(new Dimension(((SwingView.DEFAULT_WIDTH*2)/3), 
					SwingView.DEFAULT_HEIGHT));
			boardPanel.setLayout(new GridLayout(rows, cols, 5, 5));
			boardButtons = new BoardButton[rows][cols];
			for(int i = 0; i < rows; i++)
			{
				for(int j = 0; j < cols; j++)
				{
					boardButtons[i][j] = new BoardButton();
					boardButtons[i][j].addMouseListener(this);
					boardPanel.add(boardButtons[i][j]);
				}
			}
		}
		// Se actualizan y se redibujan los botones.
		for(int i = 0; i < board.getRows(); i++)
		{
			for(int j = 0; j < board.getCols(); j++)
			{
				if(board.getPosition(i, j) != null)
				{
					boardButtons[i][j].setFilled(true);
					if(board.getPosition(i, j) == AtaxxRules.obstacle)
					{
						boardButtons[i][j].setObstacle(true);
					}
				}
				if(this.isPlayerPiece(board.getPosition(i, j)))
						boardButtons[i][j].setColor(this.getPieceColor(board.getPosition(i, j)));
				else if(board.getPosition(i, j) == null)
					boardButtons[i][j].setFilled(false);
				boardButtons[i][j].repaint();
			}
		}
	}
	
	@Override
	// Cuando el raton hace click en el tablero, se llama esta funcion.
	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				if(boardButtons[i][j] == e.getSource())
				{
					// Si el boton es el izquierdo, pasa el numero 0.
					if(SwingUtilities.isLeftMouseButton(e))
						mouseClicked(i, j, 0);
					// Si el boton es el derecho, pasa el numero 1.
					else if(SwingUtilities.isRightMouseButton(e))
						mouseClicked(i, j, 1);
				}
			}
		}
	}
	
	// Devuelve el boton correspondiente a row,col.
	public JComponent getButton(int row, int col){ return boardButtons[row][col]; }
	// Devuelve el panel del tablero.
	public JPanel getBoardPanel(){ return boardPanel; }
	
	// Devuelve el color de la pieza.
	protected abstract Color getPieceColor(Piece p);
	// Devuelve si es pieza de jugador.
	protected abstract boolean isPlayerPiece(Piece p);
	// Maneja el click en el tablero. Depende del juego.
	protected abstract void mouseClicked(int row, int col, int numButton);
}
