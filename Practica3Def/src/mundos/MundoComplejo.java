package mundos;

import p3Excepciones.ErrorDeInicializacion;
import p3Excepciones.IndicesFueraDeRango;
import p3Excepciones.PalabraIncorrecta;
import p3Excepciones.PosicionOcupada;
import p3Simulacion.Superficie;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import celulas.Celula;
import celulas.CelulaCompleja;
import celulas.CelulaSimple;

/**
 * Mundo complejo. Puede tener celulas simples y complejas.
 */
public class MundoComplejo extends Mundo {
	protected int simples;
	protected int complejas;
	
	public MundoComplejo()
	{
		super();
		this.simples = 0;
		this.complejas = 0;
	}
	
	public MundoComplejo(int f, int c, int s, int cm) throws ErrorDeInicializacion
	{
		super(f, c);
		this.simples = s;
		this.complejas = cm;
		inicializaMundo();
	}

	@Override
	public void inicializaMundo() throws ErrorDeInicializacion {
		superficie = new Superficie(this.filas, this.columnas);
		boolean posicionOcupado;
		if((this.simples + this.complejas > this.filas * this.columnas) || (this.filas == 0) || (this.columnas == 0))
			throw new ErrorDeInicializacion();
		else
		{
			for(int i = 0; i < this.simples; i++)
			{
				/*
				 * Crea posiciones aleatorias y, si están vacías,
				 * crea una célula simple en dicha posición.
				 */
				do{
				int f = aleatorio(0, this.filas - 1);
				int c = aleatorio(0, this.columnas - 1);
				posicionOcupado=superficie.posicionOcupada(f, c);
				if(!posicionOcupado)
					superficie.crearCelula(f, c, new CelulaSimple());
				}while(posicionOcupado);
			}
			
			for(int i = 0; i < this.complejas; i++)
			{
				/*
				 * Crea posiciones aleatorias y, si están vacías,
				 * crea una célula compleja en dicha posición.
				 */
				do{
				int f = aleatorio(0, this.filas - 1);
				int c = aleatorio(0, this.columnas - 1);
				posicionOcupado=superficie.posicionOcupada(f, c);
				if(!posicionOcupado)
					superficie.crearCelula(f, c, new CelulaCompleja());
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
		{
			System.out.print("¿Qué tipo de célula quieres crear? (simple o compleja): ");
			String tipo = "";
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			tipo = in.next();
			tipo = tipo.toUpperCase();
			if(tipo.equals("SIMPLE"))
				superficie.crearCelula(f, c, new CelulaSimple());
			else if(tipo.equals("COMPLEJA"))
				superficie.crearCelula(f, c, new CelulaCompleja());
			else
				System.err.println("No se reconoce el tipo de célula. Creación cancelada.");
		}

	}
	
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
				else if(tipo.equals("COMPLEJA"))
				{
					Celula celula = new  CelulaCompleja();
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
			fw.write("COMPLEJO");
			fw.write(System.getProperty("line.separator"));
		}
		catch(IOException e)
		{
			throw e;
		}
	}
}
