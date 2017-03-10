package p3Simulacion;

/**
 * Casilla de la superficie.
 */
public class Casilla {
	private int fila;
	private int columna;
	
	public Casilla(int f, int c){
		this.fila = f;
		this.columna = c;
	}
	
	public int getFila()
	{
		return this.fila;
	}
	
	public int getColumna()
	{
		return this.columna;
	}
}