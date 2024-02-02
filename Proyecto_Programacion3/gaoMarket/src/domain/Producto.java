package domain;

import java.io.Serializable;
import java.util.Objects;

public class Producto implements Serializable, Comparable<Producto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum Estado {
		NORMAL,
		AGOTADO,
		POCAS_UNIDADES,
	}
	
	protected int id;
	protected String nombre;
	protected String imagen;
	protected Double precio;
	protected int cantidad;
	protected TipoProducto tipoProducto;
	protected Enum<?> categoria;
	protected Estado estado;
	protected int descuento;
	
	
	public Producto(int id, String nombre, String imagen, Double precio, int cantidad, TipoProducto tipoProducto, Enum<?> categoria, Estado estado, int descuento) {
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.precio = precio;
		this.cantidad = cantidad;
		this.tipoProducto = tipoProducto;
		this.categoria = categoria;
		this.estado = estado;
		this.descuento = descuento;
	}
	
	public Producto() {
		this.nombre = "";
		this.imagen = "";
		this.precio = 0.0;
		this.cantidad = 0;
		this.tipoProducto = TipoProducto.ALIMENTO;
		this.categoria = TipoAlimento.BEBIDAS;
		this.estado = Estado.AGOTADO;
		this.descuento = 0;
	}


	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
//		if (this.precio >= 0) {
//			this.precio = precio * (1 - this.getDescuento()/100);
//		}
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	public Enum<?> getCategoria() {
		return categoria;
	}


	public void setCategoria(Enum<?> categoria) {
		if (this.tipoProducto == TipoProducto.ALIMENTO) {
			this.categoria = (TipoAlimento) categoria;
		} else if (this.tipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
			this.categoria = (TipoHigieneYBelleza) categoria;
		} else if (this.tipoProducto == TipoProducto.LIMPIEZA) {
			this.categoria = (TipoLimpieza) categoria;
		} else {
			throw new IllegalArgumentException("Categoria de producto no reconocido.");
	    }
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		if(this.cantidad == 0) {
			this.estado = Estado.AGOTADO;
		}
		else if (this.cantidad <= 10) {
			this.estado = Estado.POCAS_UNIDADES;
		} else {
			this.estado = Estado.NORMAL;
		}
	}


	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		if (descuento >= 0 && descuento <= 100) {
			this.descuento = descuento;
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + ", precio=" + precio + ", cantidad="
				+ cantidad + ", tipoProducto=" + tipoProducto + ", categoria=" + categoria + ", estado="
				+ estado + ", descuento=" + descuento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return id == other.id && Objects.equals(nombre, other.nombre);
	}

//	Ordena los productos por su nombre alfabeticamente
	@Override
	public int compareTo(Producto o) {
		return this.nombre.compareTo(o.nombre);
	}
	
}
