package P1JuegoDeVida;

/**
 * Contiene atributos privados para contabilizar el número de pasos en los que la célula 
 * no se ha movido y el número de pasos dados (tanto si se ha movido como si no) realizados en el mundo.
 */ 
public class Celula {
	/** Numero máximo de pasos que puede estar sin moverse una célula. */
	public static final int MAX_PASOS_SIN_MOVER = 1;
	/** Numero de pasos que necesita una célula para reproducirse. */
	public static final int PASOS_REPRODUCCION = 2; 
	private int PNM; // Numero de pasos sin moverse de una celula.
	private int NPD; // Numero de pasos dados por una celula.
	
	/** Constructora de clase Celula. */
	public Celula(){
		this.PNM = 0;
		this.NPD = 0;
	}
	
	/** Actualiza la célula cuando no se mueve en un paso. */
	public void pasoSinMoverse(){
		this.PNM = this.PNM + 1;	
	}
	
	/** Actualiza la célula cuando se mueve en un paso. */
	public void pasoDado(){
		this.NPD = this.NPD + 1;
	}
	
	/**
	 * Método Getter de PNM.
	 * @return El atributo PNM.
	 */
	public int getPNM(){
		return this.PNM;
	}
	
	/**
	 * Método Getter de NPD.
	 * @return El atributo NPD.
	 */
	public int getNPD(){
		return this.NPD;
	}
	
	/**
	 * Método Setter de PNM.
	 * @param p Parámetro al que se cambia PNM.
	 */
	public void setPNM(int p){
		this.PNM = p;
	}
	
	/**
	 * Método Setter de NPD.
	 * @param p Parámentro al que se cambia NPD.
	 */
	public void setNPD(int p){
		this.NPD = p;
	}
	
	/**
	 * Método Clone para la clase Celula.
	 * @return Un objeto de clase Celula idéntico.
	 */
	public Celula Clone(){
		Celula celula = new Celula();
		celula.PNM = this.PNM;
		celula.NPD = this.NPD;
		return celula;
	}
}

