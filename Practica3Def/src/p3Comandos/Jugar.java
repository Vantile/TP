package p3Comandos;
import controlador.Controlador;
import mundos.Mundo;
import mundos.MundoComplejo;
import mundos.MundoSimple;
import p3Excepciones.ErrorDeInicializacion;

public class Jugar extends Comando {
	// Mundo creado en el comando jugar.
	private Mundo mundo;
	// Tipo de mundo creado.
	private String tipoMundo;
	
	public Jugar()
	{
		this.tipoMundo = "SIMPLE";
	}
	
	public Jugar(Mundo mundo, String tipoM)
	{
		this.mundo = mundo;
		this.tipoMundo = tipoM;
	}
	
	@Override
	public void ejecuta(Controlador controlador) {
		if(this.mundo != null)
			controlador.jugar(this.mundo);
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==5 || cadenaComando.length==6)
		{
			if(cadenaComando[0].equals("JUGAR"))
			{
				if(cadenaComando[1].equals("SIMPLE"))
				{
					try{
						String tipoMundo = "SIMPLE";
						int filas = Integer.parseInt(cadenaComando[2]);
						int columnas = Integer.parseInt(cadenaComando[3]);
						int simples = Integer.parseInt(cadenaComando[4]);
						this.mundo = new MundoSimple(filas, columnas, simples);
						return new Jugar(this.mundo, tipoMundo);
					}
					catch(ErrorDeInicializacion e)
					{
						System.err.println("No hay espacio suficiente para crear el mundo solicitado.");
						return new Jugar(null, tipoMundo);
					}
					catch(NumberFormatException e)
					{
						System.err.println("Formato de entrada no válido.");
						return new Jugar(null, tipoMundo);
					}
				}
				else if(cadenaComando[1].equals("COMPLEJO"))
				{
					String tipoMundo = "COMPLEJO";
					int filas = Integer.parseInt(cadenaComando[2]);
					int columnas = Integer.parseInt(cadenaComando[3]);
					int simples = Integer.parseInt(cadenaComando[4]);
					int complejas = Integer.parseInt(cadenaComando[5]);
					try{
						this.mundo = new MundoComplejo(filas, columnas, simples, complejas);
						return new Jugar(this.mundo, tipoMundo);
					}
					catch(ErrorDeInicializacion e)
					{
						System.err.println("No hay espacio suficiente para crear el mundo solicitado.");
						return new Jugar(null, tipoMundo);
					}
				}
				else
					return null;
			}
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "JUGAR X N M S C: Crea un mundo de tipo X (SIMPLE o COMPLEJO) con N filas, M columnas,"
				+ " S celulas simples y C celulas complejas (si el mundo es complejo).";
	}

}
