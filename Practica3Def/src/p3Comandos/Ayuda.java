package p3Comandos;
import controlador.Controlador;

public class Ayuda extends Comando {

	@Override
	public void ejecuta(Controlador controlador) {
		System.out.println(ParserComandos.ayudaComandos());
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==1)
		{
			if(cadenaComando[0].equals("AYUDA"))
				return new Ayuda();
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "AYUDA: Muestra una lista de comandos.";
	}

}
