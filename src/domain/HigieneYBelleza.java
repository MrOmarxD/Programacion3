package domain;

public class HigieneYBelleza extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TipoHigieneYBelleza tipoHigieneYBelleza;
	
	public HigieneYBelleza(String nombre, String descripcion, String imagen, Double precio, int cantidad,
			TipoHigieneYBelleza tipoHigieneYBelleza) {
		super(nombre, descripcion, imagen, precio, cantidad);
		this.tipoHigieneYBelleza = tipoHigieneYBelleza;
	}

	public HigieneYBelleza() {
		super();
		this.tipoHigieneYBelleza = TipoHigieneYBelleza.AFEITADO_DEPILACION;
	}

	public TipoHigieneYBelleza getTipoHigieneYBelleza() {
		return tipoHigieneYBelleza;
	}

	public void setTipoHigieneYBelleza(TipoHigieneYBelleza tipoHigieneYBelleza) {
		this.tipoHigieneYBelleza = tipoHigieneYBelleza;
	}

	@Override
	public String toString() {
		return "HigieneYBelleza [tipoHigieneYBelleza=" + tipoHigieneYBelleza + ", id=" + id + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", imagen=" + imagen + ", precio=" + precio + ", cantidad="
				+ cantidad + "]";
	}
	

	
	
	
}
