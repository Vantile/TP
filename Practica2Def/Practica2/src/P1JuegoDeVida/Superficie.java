package P1JuegoDeVida;

/**
 *  Contiene la superficie en la que se mueven, reproducen
 *  o mueren las celulas de la simulaci�n.
 *  @see Celula
 */
public class Superficie {
	
	//Tama�o de las filas del supercifie.
	private int nf;
	
	//Tama�o de las columnas del superficie.
	private int nc; 
	
	//La superficie del mundo.
	private Celula[][] superficie;
	
	/** 
	 * Constructora de clase Superficie. 
	 * Se inicializa la superficie de manera que comienza vac�a.
	 */
	public Superficie(int nf,int nc){
		this.nf = nf;
		this.nc = nc;
		superficie = new Celula[this.nf][this.nc];
		/* Inicializa la superficie haciendola vac�a. */
		for(int i=0;i<this.nf;i++){
			for(int j=0;j<this.nc;j++){
				superficie[i][j]=null;
			}
		}
	}
	
	/**
	 * Comprueba si la posicion (f, c) esta ocupada por una c�lula.
	 * @param f Fila de la posici�n a comprobar.
	 * @param c Columna de la posici�n a comprobar.
	 * @return True si la posici�n esta ocupada, false si no lo est�.
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
	 * @param f Fila donde est� la c�lula.
	 * @param c Columna donde est� la c�lula.
	 * @return Devuelve true si existe una posici�n a la que moverse. False si no existe.
	 */
	public boolean posicionDisponibleSimple(int f, int c){
		int fila = f - 1;
		int columna = c - 1;
		boolean disponible = false;
		/* 
		 * Si encuentra al menos una posici�n disponible
		 * sale del bucle y devuelve true.
		 */
		while((fila <= f + 1) && !disponible)
		{
			columna = c - 1;
			while((columna <= c + 1) && !disponible)
			{
				/*
				 * Esta condici�n controla que la comprobaci�n de
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
	 * @param f Fila donde est� la c�lula.
	 * @param c Columna donde est� la c�lula.
	 * @return Devuelve true si existe una posici�n a la que moverse. False si no existe.
	 */
	public boolean posicionDisponibleCompleja(int f, int c) {
		int i = 0;
		int j = 0;
		boolean disponible = false;
		/*
		 * Dado a que la c�lula compleja puede moverse por cualquier parte del
		 * mundo, buscamos una posicion que este vac�a o que contenga una c�lula
		 * simple, ya que puede moverse hacia esa posici�n.
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
	 * Crea una c�lula en (f, c).
	 * @param f Fila donde se crea la c�lula.
	 * @param c Columna donde se crea la c�lula.
	 * @return True si se ha podido crear la c�lula. False si no se ha podido crear.
	 */
	public boolean crearCelulaSimple(int f, int c){
		boolean creada = false;
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
		{
			/* Solo se crear� una c�lula si el espacio esta vac�o. */
			if(superficie[f][c] == null)
			{
				superficie[f][c] = new CelulaSimple();
				creada = true;
			}
		}
		return creada;
	}
	
	/**
	 * Crea una c�lula en (f, c).
	 * @param f Fila donde se crea la c�lula.
	 * @param c Columna donde se crea la c�lula.
	 * @return True si se ha podido crear la c�lula. False si no se ha podido crear.
	 */
	public boolean crearCelulaCompleja(int f, int c){
		boolean creada = false;
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
		{
			/* Solo se crear� una c�lula si el espacio esta vac�o. */
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
	 * @param f Fila donde eliminar la c�lula.
	 * @param c Columna donde eliminar la c�lula.
	 * @return True si se ha podido eliminar la c�lula. False si no se ha eliminado.
	 */
	public boolean eliminarCelula(int f, int c){
		boolean eliminada = false;
		if(f >= 0 && c >= 0 && f < this.nf && c < this.nc)
		{
			/* S�lo se eliminar� una c�lula si hay una c�lula en la posici�n. */
			if(superficie[f][c] != null)
			{
				superficie[f][c] = null;
				eliminada = true;
			}
		}
		return eliminada;
	}
	
	/**
	 * Mueve una c�lula de (f1, c1) a (f2, c2).
	 * @param f1 Fila donde est� la c�lula.
	 * @param c1 Columna donde est� la c�lula.
	 * @param f2 Fila hacia donde se mueve la c�lula.
	 * @param c2 Columna hacia donde se mueve la c�lula.
	 */
	public void movimientoCelula(int f1, int c1, int f2, int c2)
	{
		this.superficie[f2][c2] = this.superficie[f1][c1];
		this.superficie[f1][c1]=null;
	}
	
	/** 
	 * M�todo toString de la clase Superficie.
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
	 * Comprueba si es comestible una c�lula.
	 * @param fila Fila donde esta la c�lula a comprobar.
	 * @param columna Columna donde esta la c�lula a comprobar.
	 * @return True si es comestible, false si no lo es.
	 */
	public boolean esComestible(int fila, int columna) {
		return this.superficie[fila][columna].esComestible();
	}

	/**
	 * Ejecuta el movimiento de una c�lula.
	 * @param f Fila de la c�lula a la que mover.
	 * @param c Columna de la c�lula a la que mover.
	 * @return La casilla a la que se ha movido la c�lula, o null si no se ha movido.
	 */
	public Casilla ejecutarMovimiento(int f, int c) {
		return this.superficie[f][c].ejecutaMovimiento(f, c, this);
	}
}
