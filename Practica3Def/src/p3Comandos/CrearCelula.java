package p3Comandos;
import controlador.Controlador;
import p3Simulacion.Casilla;
import p3Excepciones.IndicesFueraDeRango;
import p3Excepciones.PosicionOcupada;

public class CrearCelula extends Comando {
		// Casilla en la que ejecutar el comando.
		private Casilla casilla;
		
		public CrearCelula()
		{
			casilla = null;
		}
		
		public CrearCelula(int f, int c)
		{
			casilla = new Casilla(f, c);
		}

		@Override
		public void ejecuta(Controlador controlador) {
			try{
				controlador.crearCelula(casilla.getFila(), casilla.getColumna());
				System.out.println("Célula creada en (" + casilla.getFila() + ", " + casilla.getColumna() + ").");
			}
			catch(IndicesFueraDeRango e)
			{
				System.err.println("No se pudo crear la célula (La posicion elegida está fuera de rango.)");
			}
			catch(PosicionOcupada e)
			{
				System.err.println("No se pudo crear la célula (La posicion elegida está ocupada.)");
			}
				
		}

		@Override
		public Comando parsea(String[] cadenaComando) {
			if(cadenaComando.length==3)
			{
				if(cadenaComando[0].equals("CREARCELULA"))
				{
					try{
						int f = Integer.parseInt(cadenaComando[1]);
						int c = Integer.parseInt(cadenaComando[2]);
						return new CrearCelula(f, c);
					}
					catch(NumberFormatException e)
					{
						System.err.println("Formato de entrada no valido.");
						return null;
					}
				}
				else
					return null;
			}
			else
				return null;
		}

		@Override
		public String textoAyuda() {
			return "CREARCELULA F C: Crea una célula en la posicion (F, C).";
		}

}
