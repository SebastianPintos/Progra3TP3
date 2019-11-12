package memento;

import grupo.Grupo;

public class Memento {
	private Grupo estado;

	public Memento(Grupo estadoAGuardar) {
		estado = estadoAGuardar;
	}

	public Grupo getEstado() {
		return estado;
	}

}
