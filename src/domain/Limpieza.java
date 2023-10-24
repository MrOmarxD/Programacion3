package domain;

public class Limpieza extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected TipoLimpieza tipoLimpieza;

	public Limpieza(String nombre, String descripcion, String imagen, Double precio, int cantidad,
			TipoLimpieza tipoLimpieza) {
		super(nombre, descripcion, imagen, precio, cantidad);
		this.tipoLimpieza = tipoLimpieza;
	}

	public Limpieza() {
		super();
		this.tipoLimpieza = TipoLimpieza.UTENSILIOS;
	}
	

	public TipoLimpieza getTipoLimpieza() {
		return tipoLimpieza;
	}

	public void setTipoLimpieza(TipoLimpieza tipoLimpieza) {
		this.tipoLimpieza = tipoLimpieza;
	}

	@Override
	public String toString() {
		return "Limpieza [tipoLimpieza=" + tipoLimpieza + ", id=" + id + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", imagen=" + imagen + ", precio=" + precio + ", cantidad=" + cantidad + "]";
	}
	

}
