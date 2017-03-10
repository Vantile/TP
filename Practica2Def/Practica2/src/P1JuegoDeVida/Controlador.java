package P1JuegoDeVida;
import java.util.Scanner;

/**
 * Controla todos los comandos que introduce el usuario
 * y actúa acorde al comando introducido.
 * @see Mundo
 */
public class Controlador {
	/* Atributo mundo del controlador. */
	private Mundo mundo;
	/* Atributo escaner para la entrada de datos del usuario. */
	private Scanner in;
	
	
	
	/**
	 * Inicializa los atributos de la clase Controlador
	 * con variables Mundo y Scanner ya inicializadas.
	 * @param mundo Clase Mundo ya inicializada.
	 * @param in Clase Scanner ya inicializada.
	 */
	public Controlador(Mundo mundo, Scanner in){
		this.mundo = mundo;
		this.in = in;
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
		while(!mundo.getEsSimulacionTerminada())
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
				comando.ejecuta(this.mundo);
			else
				/*
				 * Si no se ha encontrado, se muestra un mensaje de error.
				 */
				System.err.println("Comando incorrecto. Escribe AYUDA para tener una lista de comandos.");
		}
		System.out.println("Hasta pronto!");
	}
}
