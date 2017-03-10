package P1JuegoDeVida;

/**
 * Celula de la simulación. Puede ser simple o compleja.
 */
public abstract class Celula {
	/** True si una célula es comestible, false si no lo es. */
	protected boolean esComestible;
	/** Ejecuta el movimiento correspondiente a una célula. */
	public abstract Casilla ejecutaMovimiento(int f, int c, Superficie superficie);
	/** Devuelve si una célula es comestible. */
	public abstract boolean esComestible();
}

