package P1JuegoDeVida;

/**
 * Comando que crea una célula compleja en el mundo.
 */
public class CrearCelulaCompleja extends Comando {
	// Casilla en la que ejecutar el comando.
	private Casilla casilla;
	
	public CrearCelulaCompleja()
	{
		casilla = null;
	}
	
	public CrearCelulaCompleja(int f, int c)
	{
		casilla = new Casilla(f, c);
	}
	
	@Override
	public void ejecuta(Mundo mundo) {
		boolean creada = mundo.crearCelulaCompleja(casilla.getFila(), casilla.getColumna());
		if(creada)
			System.out.println("Célula compleja creada en (" + casilla.getFila() + ", " + casilla.getColumna() + ").");
		else
			System.err.println("No se pudo crear la célula.");
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==3)
		{
			if(cadenaComando[0].equals("CREARCELULACOMPLEJA"))
			{
				int f = Integer.parseInt(cadenaComando[1]);
				int c = Integer.parseInt(cadenaComando[2]);
				return new CrearCelulaCompleja(f, c);
			}
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "CREARCELULACOMPLEJA F C: Crea una célula compleja en la posicion (F, C).";
	}

}
