package controlador;
import p3Comandos.Comando;
import p3Comandos.ParserComandos;
import p3Excepciones.PosicionOcupada;
import p3Excepciones.IndicesFueraDeRango;
import p3Excepciones.PalabraIncorrecta;
import p3Excepciones.PosicionVacia;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import mundos.Mundo;
import mundos.MundoComplejo;
import mundos.MundoSimple;

/**
 * Controla toda la simulacion.
 */
public class Controlador {
	/* Atributo mundo del controlador. */
	private Mundo mundo;
	/* Atributo escaner para la entrada de datos del usuario. */
	private Scanner in;
	private boolean esSimulacionTerminada; // True si la simulacion ha terminado, false si no.
	
	
	
	/**
	 * Inicializa los atributos de la clase Controlador
	 * con variables Mundo y Scanner ya inicializadas.
	 * @param mundo Clase Mundo ya inicializada.
	 * @param in Clase Scanner ya inicializada.
	 */
	public Controlador(Mundo mundo, Scanner in){
		this.mundo = mundo;
		this.in = in;
		this.esSimulacionTerminada = false;
	}
	
	/**
	 * Realiza toda la simulación del mundo, actualizando el mundo
	 * dependiendo de los comandos que introduzca el usuario.
	 * Permite avanzar en la evolución del mundo, iniciar/reiniciar el mundo,
	 * vaciar el mundo, crear una célula (simple o compleja), eliminar una célula,
	 * mostrar una lista de comandos disponibles y salir del programa.
	 */
	public void realizaSimulacion()
	{
		String lectura;
		while(!esSimulacionTerminada)
		{
			/*
			 * Si la simulación no ha terminado, se procede a recoger un comando del usuario.
			 */
			System.out.print(this.mundo.toString());
			System.out.println("Introduzca un comando > ");
			lectura = this.in.nextLine();
			lectura = lectura.toUpperCase();
			String[] comandoString = lectura.split(" ");
			// Se busca el comando al que el usuario se refiere.
			Comando comando = ParserComandos.parseaComandos(comandoString);
			if(comando != null)
				/*
				 * Si se ha encontrado el comando, se ejecuta en el mundo.
				 */
				comando.ejecuta(this);
			else
				/*
				 * Si no se ha encontrado, se muestra un mensaje de error.
				 */
				System.err.println("Comando incorrecto. Escribe AYUDA para tener una lista de comandos.");
		}
		System.out.println("¡Hasta pronto!");
	}
	
	public void setEsSimulacionTerminada(boolean b)
	{
		this.esSimulacionTerminada = b;
	}
	
	public void crearCelula(int f, int c) throws IndicesFueraDeRango, PosicionOcupada
	{
		this.mundo.crearCelula(f, c);
	}
	
	public void eliminarCelula(int f, int c) throws PosicionVacia, IndicesFueraDeRango
	{
		this.mundo.eliminarCelula(f, c);
	}
	
	public void jugar(Mundo mundo)
	{
		this.mundo = mundo;
		System.out.println("Mundo iniciado correctamente.");
	}
	
	public void vaciar()
	{
		this.mundo.vaciar();
	}
	
	public void evoluciona()
	{
		this.mundo.evoluciona();
	}
	

	public void cargar(String nombFichero) throws IOException, FileNotFoundException
	{
		Scanner sc = null;
	
		try{
			File archivo = new File(nombFichero);
			
			if(archivo.exists())
			{
				sc = new Scanner(archivo);
				String tipo = "";
				tipo = sc.nextLine();
				if(tipo.equals("SIMPLE"))
				{
					Mundo cargaMundo = new MundoSimple();
					cargaMundo.cargar(sc);
					this.mundo = cargaMundo;
					System.out.println("Mundo cargado correctamente.");
				}
				else if(tipo.equals("COMPLEJO"))
				{
					Mundo cargaMundo = new MundoComplejo();
					cargaMundo.cargar(sc);
					this.mundo = cargaMundo;
					System.out.println("Mundo cargado correctamente.");
				}
				else
					throw new IOException();
				
			}
			else
				throw new FileNotFoundException();
		}
		catch(NumberFormatException e)
		{
			System.err.println("Formato numerico incorrecto: No se pudo cargar el archivo.");
		}
		catch(PalabraIncorrecta e)
		{
			System.err.println("Palabra incorrecta: No se pudo cargar el archivo.");
		}
		finally {
		if (sc!=null)	sc.close();
		}
	}
	
	public void guardar(String nombFichero) throws IOException, FileNotFoundException
	{
		try{
			File archivo = new File(nombFichero);
			archivo.createNewFile();
			if(archivo.exists())
			{
				FileWriter fw = new FileWriter(archivo);
				this.mundo.guardar(fw);
				fw.close();
				System.out.println("Mundo guardado correctamente en " + nombFichero + ".");
			}
			else
				throw new FileNotFoundException();
		}
		catch(IOException e)
		{
			throw e;
		}
	}
}
