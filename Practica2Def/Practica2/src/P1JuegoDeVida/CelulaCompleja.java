package P1JuegoDeVida;

/**
 * C�lula compleja de la simulaci�n.
 * Puede comerse a las c�lulas simples y moverse por todo el mundo.
 * Muere cuando come demasiadas c�lulas.
 * @see Celula
 * @see CelulaSimple
 */
public class CelulaCompleja extends Celula {

	/** Numero m�ximo de c�lulas que puede comerse una c�lula compleja. */
	public static final int MAX_COMER = 3;
	private int NCC; // Numero de c�lulas que se ha comido la celula compleja.
	
	public CelulaCompleja()
	{
		this.esComestible = false;
		this.NCC = 0;
	}
	
	/**
	 * Ejecuta el movimiento correspondiente a una c�lula compleja.
	 * @return La casilla a la que se mueve la c�lula, o null si no se mueve.
	 */
	public Casilla ejecutaMovimiento(int f, int c, Superficie superficie)
	{
		boolean disponible = superficie.posicionDisponibleCompleja(f, c);
		Casilla casilla = null;
		if(disponible)
		{
			/*
			 * Si hay una posicion disponible para que la c�lula se mueva,
			 * se procede a mover aleatoriamente.
			 */
			int fila = 0;
			int columna = 0;
			boolean mover = false;
			do
			{
				/*
				 * Se calcula el valor de fila y columna aleatoriamente.
				 * Cuando cumplen la condicion, se mueve la c�lula hacia esa posicion.
				 */
				fila = (int)Math.floor(Math.random()*(0-(Mundo.FILA_SUPERFICIE))+(Mundo.FILA_SUPERFICIE));
				columna = (int)Math.floor(Math.random()*(0-(Mundo.COLUMNA_SUPERFICIE))+(Mundo.COLUMNA_SUPERFICIE));
				if(!superficie.posicionOcupada(fila, columna) || (superficie.posicionOcupada(fila, columna) && superficie.esComestible(fila, columna)))
						mover = true;
			}while(!mover);
			if(superficie.posicionOcupada(fila, columna))
			{
				/*
				 * Si la casilla a la que se mueve la c�lula esta ocupada,
				 * significa que hay una c�lula simple en ella.
				 * Por tanto, la c�lula compleja se come a la simple.
				 */
				System.out.println("C�lula compleja movida de (" + f + ", " + c + ") a (" + fila + ", " + columna + ")." + "--COME--");
				superficie.eliminarCelula(fila, columna);
				if(this.NCC < MAX_COMER)
				{
					/*
					 * Si la c�lula compleja no ha comido demasiado, se incrementa su atributo NCC.
					 */
					superficie.movimientoCelula(f, c, fila, columna);
					this.celulaComida();
				}
				else
				{
					/*
					 * Si la c�lula compleja ha comido demasiado, explota.
					 */
					superficie.eliminarCelula(f, c);
					System.out.println("C�lula explota por exceso de comida en " + "(" + fila + ", " + columna + ").");
				}
			}
			else
			{
				/*
				 * Si no esta ocupada, se mueve la c�lula compleja sin consecuencias.
				 */
				superficie.movimientoCelula(f, c, fila, columna);
				System.out.println("C�lula compleja movida de (" + f + ", " + c + ") a (" + fila + ", " + columna + ")." + "--NO COME--");
			}
			casilla = new Casilla(fila, columna);
		}
		return casilla;
	}
	@Override
	/**
	 * Devuelve si la c�lula es comestible o no.
	 */
	public boolean esComestible() {
		return this.esComestible;
	}
	
	/*
	 * Incrementa el atributo NCC.
	 */
	private void celulaComida(){
		this.NCC = this.NCC + 1;
	}
	
	/**
	 * M�todo toString de la clase C�lulaCompleja.
	 */
	public String toString()
	{
		return " * ";
	}
}
