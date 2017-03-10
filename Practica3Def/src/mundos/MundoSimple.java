package mundos;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import celulas.Celula;
import celulas.CelulaSimple;
import p3Excepciones.ErrorDeInicializacion;
import p3Excepciones.IndicesFueraDeRango;
import p3Excepciones.PalabraIncorrecta;
import p3Excepciones.PosicionOcupada;
import p3Simulacion.Superficie;

/**
 * Mundo simples. Puede tener solo celulas simples.
 */
public class MundoSimple extends Mundo {
	protected int simples;
	public MundoSimple(){
		super();
		this.simples = 0;
	}
	
	public MundoSimple(int f, int c, int s) throws ErrorDeInicializacion{
		super(f, c);
		this.simples = s;
		inicializaMundo();
	}

	@Override
	public void inicializaMundo() throws ErrorDeInicializacion {
		this.superficie = new Superficie(this.filas, this.columnas);
		boolean posicionOcupado;
		if((this.simples > this.filas * this.columnas) || (this.filas == 0) || (this.columnas == 0))
			throw new ErrorDeInicializacion();
		else
		{
			for(int i = 0; i < this.simples; i++)
			{
				/*
				 * Crea posiciones aleatorias y, si están vacías,
				 * crea una célula en dicha posición.
				 */
				do{
				int f = aleatorio(0, this.filas - 1);
				int c = aleatorio(0, this.columnas - 1);
				posicionOcupado=superficie.posicionOcupada(f, c);
				if(!posicionOcupado)
					superficie.crearCelula(f, c, new CelulaSimple());
				}while(posicionOcupado);
			}
		}
	}

	@Override
	public void crearCelula(int f, int c) throws IndicesFueraDeRango, PosicionOcupada {
		if(f < 0 || f >= this.filas || c < 0 || c >= this.columnas)
			throw new IndicesFueraDeRango();
		else if(this.superficie.posicionOcupada(f, c))
			throw new PosicionOcupada();
		else
			this.superficie.crearCelula(f, c, new CelulaSimple());
	}
	
	
	
	@Override
	protected void cargarCelulas(Scanner sc) throws PalabraIncorrecta, NumberFormatException
	{
		int fila, columna;
		String tipo;
		try{
			do{
				fila = Integer.parseInt(sc.next());
				columna = Integer.parseInt(sc.next());
				tipo = sc.next();
				if(tipo.equals("SIMPLE"))
				{
					Celula celula = new  CelulaSimple();
					celula.cargar(sc);
					this.superficie.crearCelula(fila, columna,celula);
				}
				else
					throw new PalabraIncorrecta();
			}while(sc.hasNext());
		}
		catch(NumberFormatException e)
		{
			throw e;
		}
	}
	
	protected void guardaTipoJuego(FileWriter fw) throws IOException
	{
		try{
			fw.write("SIMPLE");
			fw.write(System.getProperty("line.separator"));
		}
		catch(IOException e)
		{
			throw e;
		}
	}
}
