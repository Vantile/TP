package P1JuegoDeVida;

/**
 * Comando que crea una célula simple en el mundo.
 */
public class CrearCelulaSimple extends Comando {
	// Casilla en la que ejecutar el comando.
	private Casilla casilla;
	
	public CrearCelulaSimple()
	{
		casilla = null;
	}
	
	public CrearCelulaSimple(int f, int c)
	{
		casilla = new Casilla(f, c);
	}

	@Override
	public void ejecuta(Mundo mundo) {
		boolean creada = mundo.crearCelulaSimple(casilla.getFila(), casilla.getColumna());
		if(creada)
			System.out.println("Célula simple creada en (" + casilla.getFila() + ", " + casilla.getColumna() + ").");
		else
			System.err.println("No se pudo crear la célula.");
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==3)
		{
			if(cadenaComando[0].equals("CREARCELULASIMPLE"))
			{
				int f = Integer.parseInt(cadenaComando[1]);
				int c = Integer.parseInt(cadenaComando[2]);
				return new CrearCelulaSimple(f, c);
			}
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "CREARCELULASIMPLE F C: Crea una célula simple en la posicion (F, C).";
	}

}
