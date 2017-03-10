package p3Comandos;
import controlador.Controlador;
import p3Simulacion.Casilla;
import p3Excepciones.IndicesFueraDeRango;
import p3Excepciones.PosicionVacia;

public class EliminarCelula extends Comando {
	// Casilla en la que ejecutar el comando.
		private Casilla casilla;
		
		public EliminarCelula()
		{
			casilla = null;
		}
		
		public EliminarCelula(int f, int c)
		{
			casilla = new Casilla(f, c);
		}
		
		@Override
		public void ejecuta(Controlador controlador) {
			try{
			controlador.eliminarCelula(casilla.getFila(), casilla.getColumna());
			System.out.println("C�lula eliminada en (" + casilla.getFila() + ", " + casilla.getColumna() + ").");
			}
			catch(IndicesFueraDeRango e)
			{
				System.err.println("No se pudo eliminar la c�lula (la posicion indicada esta fuera de rango)");
			}
			catch(PosicionVacia e)
			{
				System.err.println("No se pudo eliminar la c�lula (la posicion indicada esta vac�a)");
			}
			
		}

		@Override
		public Comando parsea(String[] cadenaComando) {
			if(cadenaComando.length==3)
			{
				if(cadenaComando[0].equals("ELIMINARCELULA"))
				{
					try{
						int f = Integer.parseInt(cadenaComando[1]);
						int c = Integer.parseInt(cadenaComando[2]);
						return new EliminarCelula(f, c);
					}
					catch(NumberFormatException e)
					{
						System.err.println("Formato de entrada no v�lido.");
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
			return "ELIMINARCELULA F C: Elimina la c�lula del punto (F, C).";
		}

}
