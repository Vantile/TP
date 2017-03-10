package P1JuegoDeVida;

/**
 * Contiene atributos privados para contabilizar el n�mero de pasos en los que la c�lula 
 * no se ha movido y el n�mero de pasos dados (tanto si se ha movido como si no) realizados en el mundo.
 */ 
public class Celula {
	/** Numero m�ximo de pasos que puede estar sin moverse una c�lula. */
	public static final int MAX_PASOS_SIN_MOVER = 1;
	/** Numero de pasos que necesita una c�lula para reproducirse. */
	public static final int PASOS_REPRODUCCION = 2; 
	private int PNM; // Numero de pasos sin moverse de una celula.
	private int NPD; // Numero de pasos dados por una celula.
	
	/** Constructora de clase Celula. */
	public Celula(){
		this.PNM = 0;
		this.NPD = 0;
	}
	
	/** Actualiza la c�lula cuando no se mueve en un paso. */
	public void pasoSinMoverse(){
		this.PNM = this.PNM + 1;	
	}
	
	/** Actualiza la c�lula cuando se mueve en un paso. */
	public void pasoDado(){
		this.NPD = this.NPD + 1;
	}
	
	/**
	 * M�todo Getter de PNM.
	 * @return El atributo PNM.
	 */
	public int getPNM(){
		return this.PNM;
	}
	
	/**
	 * M�todo Getter de NPD.
	 * @return El atributo NPD.
	 */
	public int getNPD(){
		return this.NPD;
	}
	
	/**
	 * M�todo Setter de PNM.
	 * @param p Par�metro al que se cambia PNM.
	 */
	public void setPNM(int p){
		this.PNM = p;
	}
	
	/**
	 * M�todo Setter de NPD.
	 * @param p Par�mentro al que se cambia NPD.
	 */
	public void setNPD(int p){
		this.NPD = p;
	}
	
	/**
	 * M�todo Clone para la clase Celula.
	 * @return Un objeto de clase Celula id�ntico.
	 */
	public Celula Clone(){
		Celula celula = new Celula();
		celula.PNM = this.PNM;
		celula.NPD = this.NPD;
		return celula;
	}
}

