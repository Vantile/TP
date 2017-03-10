package P1JuegoDeVida;

/**
 * Comandos del usuario para manipular la simulación.
 */
public abstract class Comando {
	/**
	 * Ejecuta la acción correspondiente al comando.
	 * @param mundo Mundo en el que se ejecuta la acción.
	 */
	public abstract void ejecuta(Mundo mundo);
	
	/**
	 * Convierte una cadena de String en un comando.
	 * @param cadenaComando Cadena de String en la que buscar el comando.
	 * @return El comando a devolver, o null en caso de que no coincida.
	 */
	public abstract Comando parsea(String[] cadenaComando);
	
	/**
	 * Produce el texto de ayuda de cada comando.
	 * @return String con el texto de ayuda.
	 */
	public abstract String textoAyuda();
}
