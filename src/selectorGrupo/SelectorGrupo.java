package selectorGrupo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import grupo.Grupo;
import javafx.util.Pair;
import persona.Trabajador;
import restriccion.Restriccion;

public class SelectorGrupo extends Thread {
	private static List<String> puestosPosibles = Arrays.asList("Arquitecto", "Lider de Proyecto", "Programador","Tester");
	private Grupo grupo;
	private List<Restriccion> restricciones;
	private List<Incompatibilidad> incompatibles;
	private Grupo _grupoActual;
	private Grupo _grupoResultante;

	public SelectorGrupo(Grupo g) {
		grupo = g;
		restricciones = new ArrayList<Restriccion>(puestosPosibles.size());
		incompatibles = new ArrayList<Incompatibilidad>();
		inicializarRestricciones();
	}

	private void inicializarRestricciones() {
		for (int i = 0; i < puestosPosibles.size(); i++)
			agregarRestriccionNula();
	}

	private void agregarRestriccionNula() {
		Restriccion r = new Restriccion(0);
		r.set(0, 0);
		restricciones.add(r);
	}

	public void agregarIncompatibilidad(Trabajador t1, Trabajador t2) {
		Incompatibilidad incompatibilidad = new Incompatibilidad();
		if (existe(t1) && existe(t2)) {
			incompatibilidad.set(t1, t2);
			incompatibles.add(incompatibilidad);
		}
	}

	private boolean actualTieneIncompatibles() {
		long encontro = incompatibles.stream()
				.filter(x -> _grupoActual.existe(x.primerTrabajador()) && _grupoActual.existe(x.segundoTrabajador()))
				.limit(1).count();
		return encontro == 1;
	}

	@Override
	public void run() {
		resolver();
	}

	public void resolver() {
		_grupoActual = new Grupo();
		_grupoResultante = new Grupo();

		recursion(0);
	}

	private void recursion(int inicial) {
		if (inicial == grupo.tamano()) {
			if (_grupoActualCumple() && _grupoActual.tamano() > _grupoResultante.tamano()) {
				_grupoResultante = clonarGrupoActual();
				return;
			}
		}

		if (inicial < grupo.tamano()) {
			Trabajador p = grupo.obtener(inicial);

			_grupoActual.agregar(p);
			recursion(inicial + 1);

			_grupoActual.eliminar(p);
			recursion(inicial + 1);
		}
	}

	private Grupo clonarGrupoActual() {
		Grupo ret = new Grupo();
		IntStream.range(0, _grupoActual.tamano()).forEach(x -> ret.agregar(_grupoActual.obtener(x)));

		return ret;
	}

	private boolean _grupoActualCumple() {
		if (actualTieneIncompatibles())
			return false;

		for (int i = 0; i < restricciones.size(); i++) {
			int cantidadPorPuesto = _grupoActual.cantidadPorPuesto(puestosPosibles.get(i));
			int minimo = restricciones.get(i).getMinimo();
			int maximo = restricciones.get(i).getMaximo();
			if (cantidadPorPuesto < minimo || cantidadPorPuesto > maximo)
				return false;
		}
		return true;
	}

	public static List<String> obtenerPosiblesPuestos() {
		return puestosPosibles;
	}

	public int cantidadPuestos() {
		return puestosPosibles.size();
	}

	void agregarRestriccion(String puesto, int minimo, int maximo) {
		int indice = puestosPosibles.indexOf(puesto);
		if (indice != -1) {
			try {
				Restriccion r = new Restriccion(grupo.cantidadPorPuesto(puesto));
				r.set(minimo, maximo);
				restricciones.set(indice, r);
			} catch (IllegalArgumentException e) {
			}
		}
	}

	public int tamano() {
		return grupo != null ? grupo.tamano() : 0;
	}

	public boolean existe(Trabajador trabajador) {
		return grupo.existe(trabajador);
	}

	public boolean existeRestriccion(String puesto, int minimo, int maximo) {
		int i = indiceEquivalente(puesto);
		if (i == -1)
			return false;
		return restricciones.get(i).getMinimo() == minimo && restricciones.get(i).getMaximo() == maximo;
	}

	private int indiceEquivalente(String puesto) {
		return puestosPosibles.indexOf(puesto);
	}

	public Grupo getGrupoResultante() {
		return _grupoResultante;
	}

	public void setRestricciones(List<Pair<String, Restriccion>> restriccion) {
		restriccion.stream()
				.forEach(r -> agregarRestriccion(r.getKey(), r.getValue().getMinimo(), r.getValue().getMaximo()));
	}

	public void setGrupo(Grupo grupo2) {
		grupo = grupo2;
	}

	public List<Pair<String, Integer>> valoresLimitePorPuesto() {
		List<Pair<String, Integer>> ret = new ArrayList<Pair<String, Integer>>();
		for (String puesto : puestosPosibles) {
			ret.add(new Pair<String, Integer>(puesto, grupo.cantidadPorPuesto(puesto)));
		}
		return ret;
	}

	public ArrayList<Pair<String, Restriccion>> restriccionesPorPuesto() {
		ArrayList<Pair<String, Restriccion>> ret = new ArrayList<Pair<String, Restriccion>>();
		for (String puesto : puestosPosibles)
			ret.add(new Pair<String, Restriccion>(puesto, restricciones.get(indiceEquivalente(puesto))));
		return ret;
	}

	public List<Trabajador> getIntegrantes() {
		List<Trabajador> integrantes = new ArrayList<Trabajador>();
		for (int i = 0; i < grupo.tamano(); i++)
			integrantes.add(grupo.obtener(i));
		return integrantes;
	}

	public List<Incompatibilidad> getIncompatibilidades() {
		List<Incompatibilidad> ret = new ArrayList<Incompatibilidad>();
		for (Incompatibilidad incompatibilidad : incompatibles)
			ret.add(incompatibilidad);
		return ret;
	}

}
