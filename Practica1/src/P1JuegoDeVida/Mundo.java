package P1JuegoDeVida; 

/**
 * Implementa el mundo en el
 * que viven las células.
 * @see Superficie
 * @see Celula
 */ 
public class Mundo {

	/** Numero de células que nacen cuando se crea el mundo. */
	public static final int CELULAS_INIC = 2;
	
	private Superficie superficie; // La superficie de células dentro del mundo.
	
	/** 
	 * Constructora de la clase Mundo.
	 * Se crea con CELULAS_INIC células.
	 */
	public Mundo(){
		superficie = new Superficie();
		boolean posicionOcupado;
		for(int i = 0; i < CELULAS_INIC; i++)
		{
			/*
			 * Crea posiciones aleatorias y, si están vacías,
			 * crea una célula en dicha posición.
			 */
			do{
			int f = aleatorio(0, Superficie.FILA_SUPERFICIE - 1);
			int c = aleatorio(0, Superficie.COLUMNA_SUPERFICIE - 1);
			posicionOcupado=superficie.posicionOcupada(f, c);
			if(!posicionOcupado)
				superficie.crearCelula(f, c);
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
		for(int i = 0; i < Superficie.FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < Superficie.COLUMNA_SUPERFICIE; j++)
			{
				movida[i][j] = false;
			}
		}
	}
	
	/**
	 * Ejecuta un paso en la evolucion de las células.
	 * Cada célula del mundo se mueve una sola vez por paso hacia una posicion disponible.
	 * Si la célula no se mueve, aumenta el numero de pasos sin moverse.
	 * Si lleva MAX_PASOS_SIN_MOVER sin moverse, muere.
	 * Si la célula se mueve PASOS_REPRODUCCION, se reproduce creando otra
	 * en la posición en la que estaba antes.
	 * @see Celula
	 */
	public void evoluciona(){
		boolean movida[][] = new boolean[Superficie.FILA_SUPERFICIE][Superficie.COLUMNA_SUPERFICIE];
		resetPaso(movida);
		/*
		 * Recorre la superficie, comprobando si hay una célula en
		 * cada posición que recorre y, dependiendo de las condiciones,
		 * ejecuta una acción u otra.
		 */
		for(int i = 0; i < Superficie.FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < Superficie.COLUMNA_SUPERFICIE; j++)
			{
				boolean ocupada = this.superficie.posicionOcupada(i, j);
				if(ocupada) // Si la posicion contiene una célula.
				{
					boolean disponible = this.superficie.posicionDisponible(i, j);
					boolean desplazable = !movida[i][j];
					if(disponible && desplazable)
					{
						/*
						 * Si hay una posicion disponible y la célula no
						 * se ha movido en este paso, se mueve.
						 */
						moverCelula(i, j, movida);
					}
					else if(!disponible)
					{
						// Si no se cumple alguna condición.
						this.superficie.pasoPNM(i, j);
					}
				}
			}
		}	
	}
	
	/**
	 * Muestra el mundo por consola.
	 */
	public void mostrar()
	{
		this.superficie.mostrar();
	}
	
	/*
	 * Ejecuta el movimiento de la célula dentro de la superficie.
	 */
	private void moverCelula(int f, int c, boolean movida[][]){
		int fila;
		int columna;
		do{
			/*
			 * Aleatoriamente, se calcula la posicion a la
			 * que se moverá la célula.
			 */
			fila = aleatorio(f - 1, f + 1);
			columna = aleatorio(c - 1, c + 1);
		}while((fila < 0) || (columna < 0) || (fila >= Superficie.FILA_SUPERFICIE) || (columna >= Superficie.COLUMNA_SUPERFICIE) || (this.superficie.posicionOcupada(fila, columna)));
		this.superficie.movimientoCelula(f, c, fila, columna);
		System.out.println("Célula movida de (" + f + ", " + c + ") a (" + fila + ", " + columna + ").");
		movida[fila][columna] = true;
		this.superficie.pasoNPD(f, c, fila, columna);
	}
	
	/**
	 * Crea una célula en la posicion (f, c).
	 * @param f Fila de la posición.
	 * @param c Columna de la posición.
	 * @return True si se ha creado la célula, false si no se ha creado.
	 */
	public boolean crearCelula(int f, int c)
	{
		return this.superficie.crearCelula(f, c);
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
		for(int i = 0; i < Superficie.FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < Superficie.COLUMNA_SUPERFICIE; j++)
				this.superficie.eliminarCelula(i, j);
		}
	}
}
