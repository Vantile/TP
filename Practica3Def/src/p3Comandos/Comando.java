package p3Comandos;
import controlador.Controlador;

public abstract class Comando {
	
	/**
	 * Ejecuta la acción correspondiente al comando.
	 * @param controlador Controlador donde se ejecuta el comando.
	 */
	public abstract void ejecuta(Controlador controlador);
	
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
