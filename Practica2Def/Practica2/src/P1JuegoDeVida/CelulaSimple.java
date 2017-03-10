package P1JuegoDeVida;

/**
 * Celula simple de la simulación.
 * Puede reproducirse por el mundo, pero tiene movimiento limitado.
 * Muere cuando esta inactiva mucho tiempo o cuando se la come una célula compleja.
 * @see Celula
 * @see CelulaCompleja
 */
public class CelulaSimple extends Celula {
	/** Numero máximo de pasos que puede estar inactiva una célula. */
	public static final int MAX_PASOS_SIN_MOVER = 1;
	/** Numero de pasos que necesita una célula para reproducirse. */
	public static final int PASOS_REPRODUCCION = 3;
	private int PNM; // Numero de pasos sin moverse de una celula.
	private int NPD; // Numero de pasos dados por una celula.
	
	public CelulaSimple()
	{
		this.esComestible = true;
		this.PNM = 0;
		this.NPD = 0;
	}

	/**
	 * Ejecuta el movimiento correspondiente a una célula compleja.
	 * @return La casilla a la que se mueve la célula, o null si no se mueve.
	 */
	public Casilla ejecutaMovimiento(int f, int c, Superficie superficie)
	{
		boolean disponible = superficie.posicionDisponibleSimple(f, c);
		Casilla casilla = null;
		if(disponible)
		{
			/*
			 * Si hay una posicion disponible para mover la célula simple,
			 * se calcula la posicion.
			 */
			int fila;
			int columna;
			do{
				/*
				 * La posicion a la que mover la célula simple se calcula aleatoriamente
				 * de acuerdo a las reglas de la evolucion.
				 */
				fila = (int)Math.floor(Math.random()*((f - 1)-((f + 1)+1))+((f + 1)+1));
				columna = (int)Math.floor(Math.random()*((c - 1)-((c + 1)+1))+((c + 1)+1));
			}while((fila < 0) || (columna < 0) || (fila >= Mundo.FILA_SUPERFICIE) || (columna >= Mundo.COLUMNA_SUPERFICIE) || (superficie.posicionOcupada(fila, columna)));
			superficie.movimientoCelula(f, c, fila, columna);
			System.out.println("Célula movida de (" + f + ", " + c + ") a (" + fila + ", " + columna + ").");
			pasoDado(); // Se incrementa el numero de pasos que ha dado la célula.
			casilla = new Casilla(fila, columna);
			if(this.NPD >= PASOS_REPRODUCCION)
			{
				/*
				 * Si la célula se ha movido lo suficiente, se reproducirá,
				 * dejando una célula hija en la posición de la que viene.
				 */
				superficie.crearCelulaSimple(f, c);
				this.NPD = 0;
				System.out.println("Célula nacida en (" + f + ", " + c  + ") cuyo padre es (" + fila + " ," + columna + ").");
			}
		}
		else
		{
			/*
			 * Si no hay una posición disponible a la que moverse,
			 * se incrementa el numero de pasos que lleva inactiva.
			 */
			this.pasoSinMoverse();
			if(this.PNM >= MAX_PASOS_SIN_MOVER)
			{
				/*
				 * Si lleva demasiados pasos inactiva, muere.
				 */
				superficie.eliminarCelula(f, c);
				System.out.println("Celula muerta en (" + f + ", " + c + ") por inactividad.");
			}
		}	
		return casilla;
	}
	@Override
	/**
	 * Devuelve si una célula es comestible.
	 */
	public boolean esComestible() {
		return this.esComestible;
	}
	
	/* Actualiza la célula cuando no se mueve en un paso. */
	private void pasoSinMoverse(){
		this.PNM = this.PNM + 1;	
	}
	
	/* Actualiza la célula cuando se mueve en un paso. */
	private void pasoDado(){
		this.NPD = this.NPD + 1;
	}
	
	public String toString()
	{
		return " X ";
	}
}
