package selectorGrupo;

import persona.Trabajador;

public class Incompatibilidad {
	private Trabajador incompatible1;
	private Trabajador incompatible2;

	public Incompatibilidad() {
		incompatible1 = new Trabajador();
		incompatible2 = new Trabajador();
	}

	public void set(Trabajador t1, Trabajador t2) {
		if (!t1.equals(t2)) {
			incompatible1 = t1;
			incompatible2 = t2;
		} else
			throw new IllegalArgumentException("No debe ser la misma persona");
	}

	public Trabajador primerTrabajador() {
		return incompatible1;
	}

	public Trabajador segundoTrabajador() {
		return incompatible2;
	}
}
