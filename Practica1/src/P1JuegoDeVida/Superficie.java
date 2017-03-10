package P1JuegoDeVida;

/**
 *  Contiene la superficie en la que se mueven, reproducen
 *  o mueren las celulas de la simulación.
 *  @see Celula
 */
public class Superficie {
	/** Numero de filas de la superficie. */
	public static final int FILA_SUPERFICIE = 3;
	/** Numero de columnas de la superficie. */
	public static final int COLUMNA_SUPERFICIE = 4;

	/*
	 * tamaño de las filas del supercifie
	 */
	private int nf; 
	
	/*
	 * tamaño de las columnas del superficie
	 */
	private int nc; 
	
	/*
	 * la superficie 
	 */
	private Celula[][] superficie;
	
	/** 
	 * Constructora de clase Superficie. 
	 * Se inicializa la superficie de manera que comienza vacía.
	 */
	public Superficie(){
		this.nf = FILA_SUPERFICIE;
		this.nc = COLUMNA_SUPERFICIE;
		superficie = new Celula[this.nf][this.nc];
		/* Inicializa la superficie haciendola vacía. */
		for(int i=0;i<this.nf;i++){
			for(int j=0;j<this.nc;j++){
				superficie[i][j]=null;
			}
		}
	}
	
	/**
	 * Comprueba si la posicion (f, c) esta ocupada por una célula.
	 * @param f Fila de la posición a comprobar.
	 * @param c Columna de la posición a comprobar.
	 * @return True si la posición esta ocupada, false si no lo está.
	 */
	public boolean posicionOcupada(int f, int c){
		boolean ocupada = false;
		if(f >= 0 && c >= 0 && f < FILA_SUPERFICIE && c < COLUMNA_SUPERFICIE)
		{
			if(this.superficie[f][c] != null)
				ocupada = true;
		}
		return ocupada;
	}
	
	/**
	 * Crea una célula en (f, c).
	 * @param f Fila donde se crea la célula.
	 * @param c Columna donde se crea la célula.
	 * @return True si se ha podido crear la célula. False si no se ha podido crear.
	 */
	public boolean crearCelula(int f, int c){
		boolean creada = false;
		if(f >= 0 && c >= 0 && f < FILA_SUPERFICIE && c < COLUMNA_SUPERFICIE)
		{
			/* Solo se creará una célula si el espacio esta vacío. */
			if(superficie[f][c] == null)
			{
				superficie[f][c] = new Celula();
				creada = true;
			}
		}
		return creada;
	}
	
	/** 
	 * Elimina una celula en (f, c). Devuelve false si no se pudo eliminar.
	 * @param f Fila donde eliminar la célula.
	 * @param c Columna donde eliminar la célula.
	 * @return True si se ha podido eliminar la célula. False si no se ha eliminado.
	 */
	public boolean eliminarCelula(int f, int c){
		boolean eliminada = false;
		if(f >= 0 && c >= 0 && f < FILA_SUPERFICIE && c < COLUMNA_SUPERFICIE)
		{
			/* Sólo se eliminará una célula si hay una célula en la posición. */
			if(superficie[f][c] != null)
			{
				superficie[f][c] = null;
				eliminada = true;
			}
		}
		return eliminada;
	}
	
	/**
	 * Comprueba si existe al menos una posicion a la que pueda moverse la celula
	 * posicionada en (f, c).
	 * @param f Fila donde está la célula.
	 * @param c Columna donde está la célula.
	 * @return Devuelve true si existe una posición a la que moverse. False si no existe.
	 */
	public boolean posicionDisponible(int f, int c){
		int fila = f - 1;
		int columna = c - 1;
		boolean disponible = false;
		/* 
		 * Si encuentra al menos una posición disponible
		 * sale del bucle y devuelve true.
		 */
		while((fila <= f + 1) && !disponible)
		{
			columna = c - 1;
			while((columna <= c + 1) && !disponible)
			{
				/*
				 * Esta condición controla que la comprobación de
				 * posiciones adyacentes disponibles no sobrepase
				 * el limite de la superficie.
				 */
				if(fila < 0 || columna < 0 || fila >= FILA_SUPERFICIE || columna >= COLUMNA_SUPERFICIE)
					disponible = false;
				else
					disponible = !posicionOcupada(fila, columna);
				columna++;
			}
			fila++;
		}
		return disponible;
	}
	
	/**
	 * Hace los cambios evolutivos en una célula cuando ésta se mueve.
	 * @param f1 Fila de donde viene la célula.
	 * @param c1 Columna de donde viene la célula.
	 * @param f2 Fila a donde va la célula.
	 * @param c2 Columna a donde va la célula.
	 */
	public void pasoNPD(int f1, int c1, int f2, int c2)
	{
		/* Si la célula aún no se puede reproducir. */
		if(this.superficie[f1][c1].getNPD() < Celula.PASOS_REPRODUCCION)
		{
			eliminarCelula(f1, c1);
			this.superficie[f2][c2].pasoDado();
		}
		/* Si la célula esta lista para reproducirse. */
		else
		{
			eliminarCelula(f1, c1);
			crearCelula(f1, c1);
			this.superficie[f2][c2].setNPD(0);
			System.out.println("Celula nacida en (" + f1 + ", " + c1 + ") cuyo padre es (" + f2 + ", " + c2 + ").");
		}
	}
	
	/**
	 * Hace los cambios evolutivos en una célula cuando ésta no se mueve.
	 * @param f Fila donde está la célula.
	 * @param c Columna donde está la célula.
	 */
	public void pasoPNM(int f, int c)
	{
		if(this.superficie[f][c].getPNM() < Celula.MAX_PASOS_SIN_MOVER)
			this.superficie[f][c].pasoSinMoverse();
		/* Si la célula ha estado demasiado inactiva, muere. */
		else
		{
			eliminarCelula(f, c);
			System.out.println("Celula muerta en (" + f + ", " + c + ") por falta de actividad.");
		}
	}
	
	/**
	 * Mueve una célula de (f1, c1) a (f2, c2).
	 * @param f1 Fila donde está la célula.
	 * @param c1 Columna donde está la célula.
	 * @param f2 Fila hacia donde se mueve la célula.
	 * @param c2 Columna hacia donde se mueve la célula.
	 */
	public void movimientoCelula(int f1, int c1, int f2, int c2)
	{
		this.superficie[f2][c2] = this.superficie[f1][c1].Clone();
	}
	
	/** Muestra la superficie por consola. */
	public void mostrar()
	{
		for(int i = 0; i < FILA_SUPERFICIE; i++)
		{
			System.out.println();
			for(int j = 0; j < COLUMNA_SUPERFICIE; j++)
			{
				if(this.superficie[i][j] != null)
					System.out.print((Celula.PASOS_REPRODUCCION - this.superficie[i][j].getNPD()) + "-" + (Celula.MAX_PASOS_SIN_MOVER - this.superficie[i][j].getPNM()) + " ");
				else
					System.out.print(" - ");
			}
		}
		System.out.println();
	}
}
