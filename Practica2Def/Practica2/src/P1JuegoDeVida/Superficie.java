package P1JuegoDeVida;

/**
 *  Contiene la superficie en la que se mueven, reproducen
 *  o mueren las celulas de la simulación.
 *  @see Celula
 */
public class Superficie {
	
	//Tamaño de las filas del supercifie.
	private int nf;
	
	//Tamaño de las columnas del superficie.
	private int nc; 
	
	//La superficie del mundo.
	private Celula[][] superficie;
	
	/** 
	 * Constructora de clase Superficie. 
	 * Se inicializa la superficie de manera que comienza vacía.
	 */
	public Superficie(int nf,int nc){
		this.nf = nf;
		this.nc = nc;
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
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
		{
			if(this.superficie[f][c] != null)
				ocupada = true;
		}
		return ocupada;
	}
	
	/**
	 * Comprueba si existe al menos una posicion a la que pueda moverse la celula simple
	 * posicionada en (f, c).
	 * @param f Fila donde está la célula.
	 * @param c Columna donde está la célula.
	 * @return Devuelve true si existe una posición a la que moverse. False si no existe.
	 */
	public boolean posicionDisponibleSimple(int f, int c){
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
				if(fila < 0 || columna < 0 || fila >= Mundo.FILA_SUPERFICIE || columna >= Mundo.COLUMNA_SUPERFICIE)
					disponible = false;
				else
					disponible = !this.posicionOcupada(fila, columna);
				columna++;
			}
			fila++;
		}
		return disponible;
	}
	
	/**
	 * Comprueba si existe al menos una posicion a la que pueda moverse la celula compleja
	 * posicionada en (f, c).
	 * @param f Fila donde está la célula.
	 * @param c Columna donde está la célula.
	 * @return Devuelve true si existe una posición a la que moverse. False si no existe.
	 */
	public boolean posicionDisponibleCompleja(int f, int c) {
		int i = 0;
		int j = 0;
		boolean disponible = false;
		/*
		 * Dado a que la célula compleja puede moverse por cualquier parte del
		 * mundo, buscamos una posicion que este vacía o que contenga una célula
		 * simple, ya que puede moverse hacia esa posición.
		 */
		while(i < Mundo.FILA_SUPERFICIE && !disponible)
		{
			j = 0;
			while(j < Mundo.COLUMNA_SUPERFICIE && !disponible)
			{
				if(!posicionOcupada(i, j))
					disponible = true;
				else if(superficie[i][j].esComestible())
					disponible = true;
				j++;
			}
			i++;
		}
		return disponible;
	}
	
	/**
	 * Crea una célula en (f, c).
	 * @param f Fila donde se crea la célula.
	 * @param c Columna donde se crea la célula.
	 * @return True si se ha podido crear la célula. False si no se ha podido crear.
	 */
	public boolean crearCelulaSimple(int f, int c){
		boolean creada = false;
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
		{
			/* Solo se creará una célula si el espacio esta vacío. */
			if(superficie[f][c] == null)
			{
				superficie[f][c] = new CelulaSimple();
				creada = true;
			}
		}
		return creada;
	}
	
	/**
	 * Crea una célula en (f, c).
	 * @param f Fila donde se crea la célula.
	 * @param c Columna donde se crea la célula.
	 * @return True si se ha podido crear la célula. False si no se ha podido crear.
	 */
	public boolean crearCelulaCompleja(int f, int c){
		boolean creada = false;
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
		{
			/* Solo se creará una célula si el espacio esta vacío. */
			if(superficie[f][c] == null)
			{
				superficie[f][c] = new CelulaCompleja();
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
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
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
	 * Mueve una célula de (f1, c1) a (f2, c2).
	 * @param f1 Fila donde está la célula.
	 * @param c1 Columna donde está la célula.
	 * @param f2 Fila hacia donde se mueve la célula.
	 * @param c2 Columna hacia donde se mueve la célula.
	 */
	public void movimientoCelula(int f1, int c1, int f2, int c2)
	{
		this.superficie[f2][c2] = this.superficie[f1][c1];
		this.superficie[f1][c1]=null;
	}
	
	/** 
	 * Método toString de la clase Superficie.
	 */
	public String toString()
	{
		String output = "";
		for(int i = 0; i < this.nf; i++)
		{
			output = output + "\n";
			for(int j = 0; j < this.nc; j++)
			{
				if(this.superficie[i][j] != null)
					output += this.superficie[i][j].toString();
				else
					output += " - ";
			}
		}
		output += "\n";
		return output;
	}
	
	/**
	 * Comprueba si es comestible una célula.
	 * @param fila Fila donde esta la célula a comprobar.
	 * @param columna Columna donde esta la célula a comprobar.
	 * @return True si es comestible, false si no lo es.
	 */
	public boolean esComestible(int fila, int columna) {
		return this.superficie[fila][columna].esComestible();
	}

	/**
	 * Ejecuta el movimiento de una célula.
	 * @param f Fila de la célula a la que mover.
	 * @param c Columna de la célula a la que mover.
	 * @return La casilla a la que se ha movido la célula, o null si no se ha movido.
	 */
	public Casilla ejecutarMovimiento(int f, int c) {
		return this.superficie[f][c].ejecutaMovimiento(f, c, this);
	}
}
