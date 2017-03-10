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
	 * Constructora de la clase Controlador.
	 * Inicializa el mundo, creando uno nuevo y
	 * inicializa el Scanner.
	 */
	public Controlador(){
		this.mundo = new Mundo();
		this.in = new Scanner(System.in);
	}
	
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
	 * vaciar el mundo, crear una célula, eliminar una célula,
	 * mostrar una lista de comandos disponibles y salir del programa.
	 */
	public void realizaSimulacion()
	{
		String comando = " ";
		// Booleano para salir del programa en caso de introducir SALIR.
		boolean exit = false;
		while(!exit)
		{
			this.mundo.mostrar();
			System.out.println("Introduzca un comando > ");
			comando = this.in.next();
			// Pasa todo el string a mayúscula para permitir la entrada de comandos
			// en minúscula.
			comando = comando.toUpperCase();
			switch(comando)
			{
				case "PASO":
				{
					/* Ejecuta un paso en la evolución del mundo. */
					this.mundo.evoluciona();
				}
				break;
				case "INICIAR":
				{
					/* Inicia/reinicia el mundo. */
					this.mundo = new Mundo();
					System.out.println("Simulación iniciada.");
				}
				break;
				case "CREARCELULA":
				{
					/* Crea una célula. */
					int f = this.in.nextInt(); // Coordenada de la fila.
					int c = this.in.nextInt(); // Coordenada de la columna.
					boolean creada = this.mundo.crearCelula(f, c);
					if(!creada) // Si ha habido un problema en la creación.
						System.err.println("La célula no se pudo crear.");
					else // Si ha salido bien.
						System.out.println("Célula creada en (" + f + " " + c + ").");
				}
				break;
				case "ELIMINARCELULA":
				{
					/* Elimina una célula. */
					int f = this.in.nextInt(); // Coordenada de la fila.
					int c = this.in.nextInt(); // Coordenada de la columna.
					boolean eliminada = this.mundo.eliminarCelula(f, c);
					if(!eliminada) // Si ha habido un problema eliminandola.
						System.err.println("La célula no pudo eliminarse.");
					else // Si ha salido bien.
						System.out.println("Célula eliminada en (" + f + " " + c + ").");
				}
				break;
				case "AYUDA":
				{
					/* Muestra una lista de los comandos disponibles. */
					System.out.println("COMANDOS DISPONIBLES: ");
					System.out.println("PASO: Avanza la simulacion.");
					System.out.println("INICIAR: Inicia la simulacion.");
					System.out.println("CREARCELULA F C: Crea una celula en la posicion (F, C).");
					System.out.println("ELIMINARCELULA F C: Elimina una celula de la posicion (F, C).");
					System.out.println("VACIAR: Crea un mundo vacio.");
					System.out.println("AYUDA: Muestra esta ayuda.");
					System.out.println("SALIR: Finaliza la simulacion y termina el programa.");
				}
				break;
				case "VACIAR":
				{
					/* Vacia el mundo. */
					this.mundo.vaciar();
					System.out.println("Mundo vacio creado.");
				}
				break;
				case "SALIR":
				{
					/* Sale del programa. */
					System.out.println("Finalizando simulacion...");
					exit = true;
				}
				break;
				default:
				{
					/* Cualquier comando incorrecto ejecuta este mensaje. */
					System.err.println("Comando incorrecto. Use el comando AYUDA para consultar los comandos disponibles.");
				}
			}
		}
	}
}
