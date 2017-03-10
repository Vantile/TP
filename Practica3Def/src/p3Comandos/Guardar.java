package p3Comandos;

import java.io.FileNotFoundException;
import java.io.IOException;

import controlador.Controlador;

public class Guardar extends Comando {
	// Nombre del fichero a guardar.
	private String nombFichero;
	
	public Guardar()
	{
		this.nombFichero = "";
	}
	
	public Guardar(String nombFich)
	{
		this.nombFichero = nombFich;
	}
	
	@Override
	public void ejecuta(Controlador controlador) {
		try{
			controlador.guardar(nombFichero);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("EXCEPCION: Archivo no encontrado.");
		}
		catch(IOException e)
		{
			System.err.println("EXCEPCION: Error de entrada/salida.");
		}
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==2)
		{
			if(cadenaComando[0].equals("GUARDAR"))
				return new Guardar(cadenaComando[1]);
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "GUARDAR X: Guarda el mundo actual en el fichero X.";
	}

}
