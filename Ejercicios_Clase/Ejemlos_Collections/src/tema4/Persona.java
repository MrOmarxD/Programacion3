package tema4;

public class Persona {

	private String nombre;
	private int edad;
	
	// Constructor
	public Persona(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	//get/set
	public String getNombre() {
		return nombre;
	}

	public int getEdad() {
		return edad;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %d", nombre, edad);
	}
}
