package p3Comandos;

import java.io.FileNotFoundException;
import java.io.IOException;

import controlador.Controlador;

public class Cargar extends Comando {
	// Nombre del fichero a cargar.
	private String nombFichero;
	
	public Cargar()
	{
		this.nombFichero = "";
	}
	
	public Cargar(String fichero)
	{
		this.nombFichero = fichero;
	}
	
	@Override
	public void ejecuta(Controlador controlador) {
		try{
			controlador.cargar(nombFichero);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("EXCEPCION: Fichero no encontrado.");
		}
		catch(IOException e)
		{
			System.err.println("EXCEPCION: Problema de entrada/salida.");
		}
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==2)
		{
			if(cadenaComando[0].equals("CARGAR"))
			{
				return new Cargar(cadenaComando[1]);
			}
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "CARGAR X: Carga el mundo dentro del fichero X.";
	}

}
