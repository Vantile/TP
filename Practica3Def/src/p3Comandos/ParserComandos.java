package p3Comandos;

public class ParserComandos {
	/**
	 * Array de comandos disponibles en la simulación.
	 */
	public static final Comando comandos[] = {new Vaciar(), new Paso(),
			/*new Iniciar()*/ new Ayuda(), new EliminarCelula(),
			new CrearCelula(), /*new CrearCelulaCompleja()*/
			new Salir(), new Cargar(), new Guardar(), new Jugar()};
	/**
	 * Construye el texto de ayuda al usuario con los comandos disponibles.
	 * @return El texto que muestra al usuario los comandos.
	 */
	static public String ayudaComandos()
	{
		String ayuda = "";
		for(int i = 0; i < comandos.length; i++)
		{
			ayuda = ayuda + comandos[i].textoAyuda() + "\n";
		}
		return ayuda;
	}
	
	/**
	 * Identifica el comando que ha introducido el usuario.
	 * @param cadenas Comando que ha introducido el usuario.
	 * @return El comando que quiere ejecutar el usuario, o null si no se ha encontrado o no existe.
	 */
	static public Comando parseaComandos(String[] cadenas)
	{
		int i = 0;
		boolean seguir = true;
		Comando comando = null;
		while(i < ParserComandos.comandos.length && seguir)
		{
			comando = comandos[i].parsea(cadenas);
			if(comando != null)
				seguir = false;
			else
				i++;
		}
		return comando;
	}
}
