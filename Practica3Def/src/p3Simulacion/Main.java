package p3Simulacion;
import java.util.Scanner;

import controlador.Controlador;
import mundos.Mundo;
import mundos.MundoSimple;
import p3Excepciones.ErrorDeInicializacion;

public class Main {

	public static void main(String[] args) {
		try{
		Mundo mundo = new MundoSimple(3, 3, 3);
		Scanner in = new Scanner(System.in);
		Controlador controlador = new Controlador(mundo, in);
		controlador.realizaSimulacion();
		}
		catch(ErrorDeInicializacion e)
		{
			System.err.println("No se pudo iniciar el juego. Saliendo...");
		}

	}

}
