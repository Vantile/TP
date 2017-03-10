package P1JuegoDeVida;

/**
 * Celula de la simulaci�n. Puede ser simple o compleja.
 */
public abstract class Celula {
	/** True si una c�lula es comestible, false si no lo es. */
	protected boolean esComestible;
	/** Ejecuta el movimiento correspondiente a una c�lula. */
	public abstract Casilla ejecutaMovimiento(int f, int c, Superficie superficie);
	/** Devuelve si una c�lula es comestible. */
	public abstract boolean esComestible();
}

