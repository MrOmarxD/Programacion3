package domain;

import java.util.Objects;

public class ProductoCarrito {
	protected Producto producto;
	protected int cantidad;
	
	public ProductoCarrito() {}

	public ProductoCarrito(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ProductoCarrito [producto=" + producto.getNombre() + ", cantidad=" + cantidad + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(producto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductoCarrito other = (ProductoCarrito) obj;
		return Objects.equals(producto, other.producto);
	}
}
