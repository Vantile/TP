package es.ucm.fdi.tp.practica5.bgame.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Esta clase es cada casilla del tablero. Es un componente que se actualiza
 * cuando la posicion correspondiente cambia.
 */
@SuppressWarnings("serial")
public class BoardButton extends JComponent {

	// True si la casilla esta ocupada.
	private boolean filled;
	// True si la casilla es un obstaculo.
	private boolean isObstacle;
	// Color del boton.
	private Color btnColor;
	
	public BoardButton()
	{
		filled = false;
	}
	
	/**
	 * Este metodo se llama cada vez que se necesite (re)dibujar el boton.
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(filled && !isObstacle)
		{
			g2.setColor(btnColor);
			g2.fillOval(0, 0, this.getWidth(), this.getHeight());
		}
		else if (filled && isObstacle)
		{
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(10, 10, (this.getWidth() - 20), (this.getHeight() - 20));
		}
	}
	
	// Marca si la casilla esta vacia o llena.
	public void setFilled(boolean b)
	{
		this.filled = b;
	}
	
	// Marca si la casilla es un obstaculo.
	public void setObstacle(boolean b)
	{
		this.isObstacle = b;
	}
	
	// Marca el color del boton.
	public void setColor(Color c)
	{
		this.btnColor = c;
	}

}
