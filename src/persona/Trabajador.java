package persona;

public class Trabajador extends Persona {

	private String _puesto;

	public Trabajador(String nombre, int edad, int dni, String puesto) {
		super(nombre, edad, dni);
		_puesto = puesto;
	}
	
	public Trabajador() {
		super();
		_puesto="";
	}

	public String getPuesto() {
		return _puesto;
	}

	@Override
	public String toString() {
		return "Nombre: " + getNombre() + ", edad: " + getEdad() + ", DNI: " + getDNI() + ", puesto: " + _puesto;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != getClass())
			return false;
		Trabajador otro = (Trabajador) o;
		return otro.getDNI() == getDNI() && otro.getEdad() == getEdad() && otro.getNombre().equals(getNombre())
				&& otro.getPuesto().contentEquals(getPuesto());

	}
}
