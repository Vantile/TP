package mundos;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import p3Excepciones.ErrorDeInicializacion;
import p3Excepciones.IndicesFueraDeRango;
import p3Excepciones.PalabraIncorrecta;
import p3Excepciones.PosicionVacia;
import p3Excepciones.PosicionOcupada;
import p3Simulacion.Casilla;
import p3Simulacion.Superficie;

/**
 * Mundos del juego.
 * Puede haber mundos simples y complejos.
 * Cada mundo tiene una superficie con X filas y Y columnas.
 */
public abstract class Mundo{
	protected Superficie superficie;
	protected int filas;
	protected int columnas;
	
	public Mundo(){
		filas = 0;
		columnas = 0;
		superficie = null;
	}
	
	public Mundo(int f, int c){
		filas = f;
		columnas = c;
	}
	
	/* Crea un numero aleatorio cuyo valor esté entre minimo y maximo. */
	protected int aleatorio(int minimo, int maximo){
        
        int num=(int)Math.floor(Math.random()*(minimo-(maximo+1))+(maximo+1));
        return num;
    }
	
	public int getFilas()
	{
		return this.filas;
	}
	
	public int getColumnas()
	{
		return this.columnas;
	}
	
	abstract public void inicializaMundo() throws ErrorDeInicializacion;
	
	/* 
	 * Reinicia el array de booleanos que indican si una célula
	 * se ha movido en el paso actual o no.
	 */
	private void resetPaso(boolean movida[][])
	{
		for(int i = 0; i < filas; i++)
		{
			for(int j = 0; j < columnas; j++)
			{
				movida[i][j] = false;
			}
		}
	}
	
	public void evoluciona()
	{
		boolean movida[][] = new boolean[this.filas][this.columnas];
		Casilla casilla = null;
		resetPaso(movida);
		/*
		 * Recorre la superficie, comprobando si hay una célula en
		 * cada posición que recorre y, dependiendo de las condiciones,
		 * ejecuta una acción u otra.
		 */
		for(int i = 0; i < filas; i++)
		{
			for(int j = 0; j < columnas; j++)
			{
				boolean ocupada = this.superficie.posicionOcupada(i, j);
				boolean desplazable = !movida[i][j];
				if(ocupada && desplazable) // Si la posicion contiene una célula que se puede mover.
				{
					casilla = this.superficie.ejecutarMovimiento(i, j);
					if(casilla != null)
						movida[casilla.getFila()][casilla.getColumna()] = true;
				}
			}
		}	
	}
	
	public String toString()
	{
		return superficie.toString();
	}
	
	public void eliminarCelula(int f, int c) throws IndicesFueraDeRango, PosicionVacia
	{
		if(f < 0 || f >= this.filas || c < 0 || c >= this.columnas)
			throw new IndicesFueraDeRango();
		else if(!superficie.posicionOcupada(f, c))
			throw new PosicionVacia();
		else
			this.superficie.eliminarCelula(f, c);
	}
	
	public void vaciar()
	{
		for(int i = 0; i < filas; i++)
		{
			for(int j = 0; j < columnas; j++)
				this.superficie.eliminarCelula(i, j);
		}
	}
	
	abstract public void crearCelula(int f, int c) throws IndicesFueraDeRango, PosicionOcupada;
	
	public void cargar(Scanner sc) throws PalabraIncorrecta, NumberFormatException
	{
		try{
			this.filas = Integer.parseInt(sc.next());
			this.columnas = Integer.parseInt(sc.next());
			this.superficie = new Superficie(this.filas, this.columnas);
			cargarCelulas(sc);	
		}
		catch(PalabraIncorrecta e)
		{
			throw e;
		}
		catch(NumberFormatException e)
		{
			throw e;
		}
	}
	
	public void guardar(FileWriter fw) throws IOException
	{
		this.guardaTipoJuego(fw);
		fw.write(Integer.toString(this.filas));
		fw.write(System.getProperty("line.separator"));
		fw.write(Integer.toString(this.columnas));
		fw.write(System.getProperty("line.separator"));
		this.superficie.guardar(fw);
	}
	
	abstract protected void cargarCelulas(Scanner sc) throws PalabraIncorrecta, NumberFormatException;
	abstract protected void guardaTipoJuego(FileWriter fw) throws IOException;
}
