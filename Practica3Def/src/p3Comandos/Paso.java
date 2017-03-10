package p3Comandos;
import controlador.Controlador;

public class Paso extends Comando {

	@Override
	public void ejecuta(Controlador controlador) {
		controlador.evoluciona();

	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==1)
		{
			if(cadenaComando[0].equals("PASO"))
				return new Paso();
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "PASO: Avanza la simulacion.";
	}

}
