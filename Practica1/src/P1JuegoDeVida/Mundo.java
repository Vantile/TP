package P1JuegoDeVida; 

/**
 * Implementa el mundo en el
 * que viven las c�lulas.
 * @see Superficie
 * @see Celula
 */ 
public class Mundo {

	/** Numero de c�lulas que nacen cuando se crea el mundo. */
	public static final int CELULAS_INIC = 2;
	
	private Superficie superficie; // La superficie de c�lulas dentro del mundo.
	
	/** 
	 * Constructora de la clase Mundo.
	 * Se crea con CELULAS_INIC c�lulas.
	 */
	public Mundo(){
		superficie = new Superficie();
		boolean posicionOcupado;
		for(int i = 0; i < CELULAS_INIC; i++)
		{
			/*
			 * Crea posiciones aleatorias y, si est�n vac�as,
			 * crea una c�lula en dicha posici�n.
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
	
	/* Crea un numero aleatorio cuyo valor est� entre minimo y maximo. */
	private int aleatorio(int minimo, int maximo){
        
        int num=(int)Math.floor(Math.random()*(minimo-(maximo+1))+(maximo+1));
        return num;
    }
	
	/* 
	 * Reinicia el array de booleanos que indican si una c�lula
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
	 * Ejecuta un paso en la evolucion de las c�lulas.
	 * Cada c�lula del mundo se mueve una sola vez por paso hacia una posicion disponible.
	 * Si la c�lula no se mueve, aumenta el numero de pasos sin moverse.
	 * Si lleva MAX_PASOS_SIN_MOVER sin moverse, muere.
	 * Si la c�lula se mueve PASOS_REPRODUCCION, se reproduce creando otra
	 * en la posici�n en la que estaba antes.
	 * @see Celula
	 */
	public void evoluciona(){
		boolean movida[][] = new boolean[Superficie.FILA_SUPERFICIE][Superficie.COLUMNA_SUPERFICIE];
		resetPaso(movida);
		/*
		 * Recorre la superficie, comprobando si hay una c�lula en
		 * cada posici�n que recorre y, dependiendo de las condiciones,
		 * ejecuta una acci�n u otra.
		 */
		for(int i = 0; i < Superficie.FILA_SUPERFICIE; i++)
		{
			for(int j = 0; j < Superficie.COLUMNA_SUPERFICIE; j++)
			{
				boolean ocupada = this.superficie.posicionOcupada(i, j);
				if(ocupada) // Si la posicion contiene una c�lula.
				{
					boolean disponible = this.superficie.posicionDisponible(i, j);
					boolean desplazable = !movida[i][j];
					if(disponible && desplazable)
					{
						/*
						 * Si hay una posicion disponible y la c�lula no
						 * se ha movido en este paso, se mueve.
						 */
						moverCelula(i, j, movida);
					}
					else if(!disponible)
					{
						// Si no se cumple alguna condici�n.
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
	 * Ejecuta el movimiento de la c�lula dentro de la superficie.
	 */
	private void moverCelula(int f, int c, boolean movida[][]){
		int fila;
		int columna;
		do{
			/*
			 * Aleatoriamente, se calcula la posicion a la
			 * que se mover� la c�lula.
			 */
			fila = aleatorio(f - 1, f + 1);
			columna = aleatorio(c - 1, c + 1);
		}while((fila < 0) || (columna < 0) || (fila >= Superficie.FILA_SUPERFICIE) || (columna >= Superficie.COLUMNA_SUPERFICIE) || (this.superficie.posicionOcupada(fila, columna)));
		this.superficie.movimientoCelula(f, c, fila, columna);
		System.out.println("C�lula movida de (" + f + ", " + c + ") a (" + fila + ", " + columna + ").");
		movida[fila][columna] = true;
		this.superficie.pasoNPD(f, c, fila, columna);
	}
	
	/**
	 * Crea una c�lula en la posicion (f, c).
	 * @param f Fila de la posici�n.
	 * @param c Columna de la posici�n.
	 * @return True si se ha creado la c�lula, false si no se ha creado.
	 */
	public boolean crearCelula(int f, int c)
	{
		return this.superficie.crearCelula(f, c);
	}
	
	/**
	 * Elimina una c�lula en la posici�n (f, c).
	 * @param f Fila de la posici�n.
	 * @param c Columna de la posici�n.
	 * @return True si se ha eliminado la c�lula, false si no se ha eliminado.
	 */
	public boolean eliminarCelula(int f, int c)
	{
		return this.superficie.eliminarCelula(f, c);
	}
	
	/**
	 * Vac�a el mundo.
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
