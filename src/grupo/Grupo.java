package grupo;

import java.util.ArrayList;
import java.util.List;

import persona.Trabajador;

public class Grupo {
	private List<Trabajador> trabajadores;

	public Grupo() {
		trabajadores = new ArrayList<Trabajador>();
	}

	public boolean existe(Trabajador t) {
		long encontrado = trabajadores.stream().filter(x->x.equals(t)).limit(1).count();
		return encontrado==1;
	}

	public void agregar(Trabajador trabajador) {
		if (!existe(trabajador))
			trabajadores.add(trabajador);
	}

	private int getIndice(Trabajador trabajador) {
		return trabajadores.indexOf(trabajador);
	}

	public Trabajador obtener(int indice) {
		if (indice >= 0 && indice < tamano())
			return trabajadores.get(indice);
		else
			return null;
	}

	public int tamano() {
		return trabajadores.size();
	}

	public int cantidadPorPuesto(String puesto) {
		long cont =trabajadores.stream().filter(x->x.getPuesto().equals(puesto)).count();
		return (int) cont;
	}

	private void eliminar(int i) {
		if (i <= tamano())
			trabajadores.remove(i);
	}

	public void eliminar(Trabajador p) {
		if (existe(p))
			eliminar(getIndice(p));
	}


	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("");
		if (tamano() > 0) {
			for (int i = 0; i < trabajadores.size() - 1; i++) {
				ret.append(trabajadores.get(i).getNombre() + ", ");
			}
			ret.append(trabajadores.get(trabajadores.size() - 1).getNombre());
		}
		return ret.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != getClass())
			return false;
		Grupo grupo = (Grupo) o;
		if (grupo.tamano() == tamano()) {
			for (Trabajador trabajador : trabajadores) {
				if (!grupo.existe(trabajador))
					return false;
			}

		}
		return true;
	}

}
