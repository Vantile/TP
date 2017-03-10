package p3Comandos;
import controlador.Controlador;

public class Salir extends Comando {

	@Override
	public void ejecuta(Controlador controlador) {
		controlador.setEsSimulacionTerminada(true);
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==1)
		{
			if(cadenaComando[0].equals("SALIR"))
				return new Salir();
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "SALIR: Finaliza el programa.";
	}

}
