package ejercicio3;

public class Plato {
	
	private String nombre;
	private String tipo;
	private double precio;
	private String imagen;
	
	//Constructores
	public Plato(String nombre, String tipo, double precio, String imagen) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.imagen = imagen;
	}
	public Plato(String nombre, String tipo, double precio) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
	}
	
	//toString
	public String toString() {
		return nombre;
	}
	
	//get/set
	public String getNombre() {
		return nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public String getImagen() {
		return imagen;
	}
}
