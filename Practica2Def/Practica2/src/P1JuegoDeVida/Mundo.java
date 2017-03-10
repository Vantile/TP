package P1JuegoDeVida; 

/**
 * Implementa el mundo en el
 * que viven las células.
 * @see Superficie
 * @see Celula
 */ 
public class Mundo {
	
	/** Numero de filas de la superficie. */
	public static final int FILA_SUPERFICIE = 4;
	/** Numero de columnas de la superficie. */
	public static final int COLUMNA_SUPERFICIE = 4;


	/** Numero de células que nacen cuando se crea el mundo. */
	public static final int CELULAS_INIC = 2;
	
	private Superficie superficie; // La superficie de células dentro del mundo.
	private boolean esSimulacionTerminada; // True si la simulacion ha terminado, false si no.
	
	/** 
	 * Constructora de la clase Mundo.
	 * Se crea con CELULAS_INIC células.
	 */
	public Mundo(){
		superficie = new Superficie(FILA_SUPERFICIE,COLUMNA_SUPERFICIE);
		esSimulacionTerminada = false;
		boolean posicionOcupado;
		for(int i = 0; i < CELULAS_INIC; i++)
		{
			/*
			 * Crea posiciones aleatorias y, si están vacías,
			 * crea una célula en dicha posición.
			 */
			do{
			int f = aleatorio(0, FILA_SUPERFICIE - 1);
			int c = aleatorio(0, COLUMNA_SUPERFICIE - 1);
			posicionOcupado=superficie.posicionOcupada(f, c);
			if(!posicionOcupado)
				superficie.crearCelulaSimple(f, c);
			}while(posicionOcupado);
		}
	}
	
	/* Crea un numero aleatorio cuyo valor esté entre minimo y maximo. */
	private int aleatorio(int minimo, int maximo){
        
        int num=(int)Math.floor(Math.random()*(minimo-(maximo+1))+(maximo+1));
        return num;
    }
	
	/* 
	 * Reinicia el array de booleanos que indican si una célula
	 * se ha movido en el paso actual o no.
	 */
	private void resetPaso(boolean movida[][])
	{
		for(int i = 0; i < FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < COLUMNA_SUPERFICIE; j++)
			{
				movida[i][j] = false;
			}
		}
	}
	
	/**
	 * Inicia el mundo.
	 */
	public void iniciar(){
		boolean posicionOcupado;
		for(int i = 0; i < CELULAS_INIC; i++)
		{
			/*
			 * Crea posiciones aleatorias y, si están vacías,
			 * crea una célula en dicha posición.
			 */
			do{
			int f = aleatorio(0, FILA_SUPERFICIE - 1);
			int c = aleatorio(0, COLUMNA_SUPERFICIE - 1);
			posicionOcupado=superficie.posicionOcupada(f, c);
			if(!posicionOcupado)
				superficie.crearCelulaSimple(f, c);
			}while(posicionOcupado);
		}
	}

	/**
	 * Ejecuta un paso en la evolucion de las células.
	 * Cada célula del mundo se mueve una sola vez por paso hacia una posicion disponible.
	 * Dependiendo de la célula, el movimiento tendrá unas consecuencias
	 * u otras.
	 * @see CelulaSimple
	 * @see CelulaCompleja
	 */
	public void evoluciona(){
		boolean movida[][] = new boolean[FILA_SUPERFICIE][COLUMNA_SUPERFICIE];
		Casilla casilla = null;
		resetPaso(movida);
		/*
		 * Recorre la superficie, comprobando si hay una célula en
		 * cada posición que recorre y, dependiendo de las condiciones,
		 * ejecuta una acción u otra.
		 */
		for(int i = 0; i < FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < COLUMNA_SUPERFICIE; j++)
			{
				boolean ocupada = this.superficie.posicionOcupada(i, j);
				boolean desplazable = !movida[i][j];
				if(ocupada && desplazable) // Si la posicion contiene una célula que se puede mover.
				{
					casilla = this.superficie.ejecutarMovimiento(i, j);
					if(casilla != null)
						movida[casilla.getFila()][casilla.getColumna()] = true;
				}
			}
		}	
	}
	
	/**
	 * Método toString de la clase Mundo.
	 */
	public String toString()
	{
		return this.superficie.toString();
	}
	
	/**
	 * Crea una célula simple en la posicion (f, c).
	 * @param f Fila de la posición.
	 * @param c Columna de la posición.
	 * @return True si se ha creado la célula, false si no se ha creado.
	 */
	public boolean crearCelulaSimple(int f, int c)
	{
		boolean creada = this.superficie.crearCelulaSimple(f, c);
		return creada;
	}
	
	/**
	 * Crea una célula compleja en la posicion (f, c).
	 * @param f Fila de la posición.
	 * @param c Columna de la posición.
	 * @return True si se ha creado la célula, false si no se ha creado.
	 */
	public boolean crearCelulaCompleja(int f, int c)
	{
		boolean creada = this.superficie.crearCelulaCompleja(f, c);
		return creada;
	}
	
	/**
	 * Elimina una célula en la posición (f, c).
	 * @param f Fila de la posición.
	 * @param c Columna de la posición.
	 * @return True si se ha eliminado la célula, false si no se ha eliminado.
	 */
	public boolean eliminarCelula(int f, int c)
	{
		return this.superficie.eliminarCelula(f, c);
	}
	
	/**
	 * Vacía el mundo.
	 */
	public void vaciar()
	{
		for(int i = 0; i < FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < COLUMNA_SUPERFICIE; j++)
				this.superficie.eliminarCelula(i, j);
		}
	}
	
	/**
	 * Método Setter de esSimulacionTerminada.
	 * @param b Valor al que cambiar el atributo.
	 */
	public void setEsSimulacionTerminada(boolean b)
	{
		this.esSimulacionTerminada = b;
	}
	
	/**
	 * Método Getter de esSimulacionTerminada.
	 * @return Valor del atributo.
	 */
	public boolean getEsSimulacionTerminada()
	{
		return this.esSimulacionTerminada;
	}
}
