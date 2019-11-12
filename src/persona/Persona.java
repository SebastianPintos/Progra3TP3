package persona;

public abstract class Persona {
    private String nombre;
    private int edad;
    private int dni;

    public Persona(String nombre,  int edad, int dni) {
    	this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
    }
    
    public Persona() {
    	nombre="";
    	edad=0;
    	dni=0;
    }
    
	public String getNombre() {
        return nombre;
    }

    public int getDNI() {
        return dni;
    }

    public int getEdad() {
        return edad;
    }

  
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("");
        ret.append("Nombre: " + nombre + ", Edad: " + edad + ", DNI: " + dni);
        return ret.toString();
    }
}
