package examen.parc202211.data;

import java.io.Serializable;

/** Clase de datos de cada categoría del sistema DeustoBeReal
 */
public class Categoria implements Serializable, Comparable<Categoria> {
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private String nombre;
	private int numFotos;

	/** Constructor de categoría
	 * @param codigo	Código (único) de categoría
	 * @param nombre	Nombre de categoría
	 * @param numFotos	Número de fotos que se han compartido en esa categoría
	 */
	public Categoria(int codigo, String nombre, int numFotos) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.numFotos = numFotos;
	}
	
	/** Constructor de categoría con cero fotos
	 * @param codigo	Código (único) de categoría
	 * @param nombre	Nombre de la categoría
	 */
	public Categoria(int codigo, String nombre) {
		this( codigo, nombre, 0 );
	}
	
	public int getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public int getNumFotos() {
		return numFotos;
	}
	public void setNumFotos(int numFotos) {
		this.numFotos = numFotos;
	}
	public void incNumFotos() {
		this.numFotos++;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Categoria) {
			return ((Categoria)obj).codigo == codigo;  // Igualdad a igualdad de identificador único
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(C" + codigo + ") " + nombre;
	}

	@Override
	public int compareTo(Categoria o) {
		return nombre.compareTo(o.nombre);
	}
}
