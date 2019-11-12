package memento;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import repoLocal.RepoLocal;

public class CareTaker {
	private List<Memento> estadosGuardados;
	private int estadoActual;
	public RepoLocal repo;

	public CareTaker() throws FileNotFoundException {
		repo = new RepoLocal();
		this.estadosGuardados = new ArrayList<Memento>();

	}

	public void addMemento(Memento m) {
		estadosGuardados.add(m);
		estadoActual = estadosGuardados.size() - 1;
	}

	public Memento getMemento(int i) {
		if (i > tamano() - 1 || i < 0)
			throw new IllegalArgumentException("Indice ingresado no valido");
		estadoActual = i;
		return estadosGuardados.get(i);
	}

	public int estadoActual() {
		return estadoActual;
	}

	public int tamano() {
		return estadosGuardados.size();
	}

	public void guardar() {
		repo.agregarAMemoria(this, "Grupos");
	}

	public CareTaker traerCareDArch() throws FileNotFoundException {
		CareTaker temp = this.repo.leerJSON(("Grupos"));
		return temp;
	}

	public void borrarHistorial() {
		estadosGuardados.clear();
		repo.generarJSON("Grupos", this);
	}

	public void borrarDeHistorial(int index) throws FileNotFoundException {
		estadosGuardados.clear();
		estadosGuardados.addAll(this.traerCareDArch().estadosGuardados);
		estadosGuardados.remove(index);
		repo.generarJSON("Grupos", this);
	}

	@Override
	public String toString() {
		String ret = "";
		int cont = 1;

		try {
			for (Memento m : this.traerCareDArch().estadosGuardados) {
				ret += "Grupo" + cont + '\n';
				ret += m.getEstado().toString() + '\n';
				cont++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
