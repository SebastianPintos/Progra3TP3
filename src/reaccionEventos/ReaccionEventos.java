package reaccionEventos;

import java.awt.event.KeyEvent;

public class ReaccionEventos {
	
	public static void ignorarSiNoEsLetraMinuscula(KeyEvent tecla) {
		char c = tecla.getKeyChar();
		if (!Character.isLetter(c) || !Character.isLowerCase(c))
			tecla.consume();
	}

	public static void ignorarSiNoEsNumero(KeyEvent tecla) {
		char c = tecla.getKeyChar();
		if (!Character.isDigit(c))
			tecla.consume();
	}

	public static void ignorarSiNoEsLetraMayuscula(KeyEvent tecla) {
		char c = tecla.getKeyChar();
		if (!Character.isUpperCase(c) || !Character.isLetter(c))
			tecla.consume();
	}
	
	public static void evitarTecla(KeyEvent tecla) {
		tecla.consume();
	}
}
