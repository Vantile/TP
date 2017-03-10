package P1JuegoDeVida;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Mundo mundo = new Mundo();
		Scanner in = new Scanner(System.in);
		Controlador controlador = new Controlador(mundo, in);
		controlador.realizaSimulacion();
	}

}
