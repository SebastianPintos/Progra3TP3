package memento;

import grupo.Grupo;

public class Originator {
	private Grupo estado;

	public Grupo getEstado() {
		return estado;
	}

	public void setEstado(Grupo estado) {
		this.estado = estado;
	}

	public Originator(Grupo estado) {
		super();
		this.estado = estado;
	}

	public Memento guardarAMemento() {
		return new Memento(estado);
	}

	public void obtenerDeMemento(Memento m) {
		estado = m.getEstado();
	}
}
