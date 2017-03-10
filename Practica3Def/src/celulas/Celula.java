package celulas;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import p3Simulacion.Casilla;
import p3Simulacion.Superficie;

/**
 * Interfaz Celula. Celulas de cualquier mundo del juego.
 * Puede haber celulas simples y complejas.
 */
public interface Celula {
	/** Ejecuta el movimiento correspondiente a una célula. */
	public abstract Casilla ejecutaMovimiento(int f, int c, Superficie superficie);
	/** Devuelve si una célula es comestible. */
	public abstract boolean esComestible();
	public abstract void cargar(Scanner sc) throws NumberFormatException;
	public abstract void guardar(FileWriter fw) throws IOException;
}
