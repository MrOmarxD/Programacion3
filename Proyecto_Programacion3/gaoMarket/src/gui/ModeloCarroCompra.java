package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import domain.GestorMarket;
import domain.Producto;
import domain.ProductoCarrito;

public class ModeloCarroCompra extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected GestorMarket gestor;
	protected List<ProductoCarrito> productos;
	protected final List<String> cabeceras = Arrays.asList("Producto", "Nombre", "Precio", "Cantidad", "Total");
			
	public ModeloCarroCompra(List<ProductoCarrito> productos) {
		this.productos = productos;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return cabeceras.get(column);
	}
	
	@Override
	public int getRowCount() {
		if (productos != null) {
			return productos.size();
		} else {
			return 0;
		}
	}
	
	@Override
	public int getColumnCount() {
		return cabeceras.size();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (row == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public Object getValueAt(int row, int column) {
		
		Producto p = productos.get(row).getProducto();
		switch (column) {
			case 0: return p.getImagen();
			case 1: return p.getNombre();
			case 2: return Double.valueOf(p.getPrecio());
			case 3: return Integer.valueOf(productos.get(row).getCantidad());
			case 4: return sumarPrecioProductoRecursivo(p.getPrecio(), productos.get(row).getCantidad());
			default: return null;
		}
	}
	
	public void setProductoCarrito(List<ProductoCarrito> productos) {
	    this.productos = productos;
	    fireTableDataChanged();
	}


	public static Double sumarPrecioProductoRecursivo(Double precio, int cantidad) {
		if(cantidad < 2) return precio;
		return sumarPrecioProductoRecursivo(precio, --cantidad) + precio;
	}
}
