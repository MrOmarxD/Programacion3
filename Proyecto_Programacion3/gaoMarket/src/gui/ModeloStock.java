package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import domain.*;

public class ModeloStock extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected GestorMarket gestor;
	protected List<Producto> productos;
	protected final List<String> cabeceras = Arrays.asList("ID", "Nombre", "Imagen", "Precio", "Cantidad", "Tipo Producto", "Categoría", "Estado", "Descuento", "Precio Con Descuento");
			
	
	public ModeloStock(List<Producto> productos) {
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
		return column >= 1 && column <= 4;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		Producto p = productos.get(row);
		switch (column) {
			case 0: return p.getId();
			case 1: return p.getNombre();
			case 2: return p.getImagen();
			case 3: return p.getPrecio();
			case 4: return Integer.valueOf(p.getCantidad());
			case 5: return p.getTipoProducto();
			case 6: return p.getCategoria();
			case 7: return p.getEstado();
			case 8: return p.getDescuento();
			case 9:  double resultado = ((Double) p.getPrecio()) * (1 - ((double) p.getDescuento()) / 100);
				return Math.round(resultado * 100.0) / 100.0;
			default: return null;
		}
	}
	
	public Producto getProductoEnFila(int fila) {
		return productos.get(fila);
	}

	public void eliminarProducto(Producto producto) {
		productos.remove(producto);
	}

	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
	    try {
	        if (isCellEditable(row, column)) {
	            Producto producto = productos.get(row);

	            switch (column) {
	                case 1:
	                    producto.setNombre((String) aValue);
	                    break;
	                case 2:
	                    producto.setImagen((String) aValue);
	                    break;
	                case 3:
	                    producto.setPrecio(Double.parseDouble(aValue.toString()));
	                    break;
	                case 4:
	                    producto.setCantidad(Integer.parseInt(aValue.toString()));
	                    break;
	                default:
	                    break;
	            }

	            if (!productos.contains(producto)) {
	                gestor.getGestorBD().anyadirProducto(producto);
		            CadenaProductos.guardarProductos("resources/datos/productos.csv", gestor.getGestorBD().listarProductos());
	            }

	            fireTableCellUpdated(row, column);
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Error: La cantidad o el precio no es un número válido", "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error: Los datos no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}



	
	
}
