package tema4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Ejercicio13 {
	
	//comparador especifico para comparar dos productos por fecha de entrega
	static class ComparadorEntrega implements Comparator<Producto>{

		// el metodo devuelve 1, 0, -1 si la fecha de entrega del Producto a es mayor, igual o 
		// menor que el Producto b, restando sus fechas de entrega
		@Override
		public int compare(Producto a, Producto b) {
			//delegamos internamente en el comparador para LocalDate
			return a.getEntrega().compareTo(b.getEntrega());
		}
	}

	// Recibe una lista de Productos y devuelve una nueva lista de Productos eliminando los 
	// elementos duplicados de la primera lista. Se considera que dos Productos son iguales 
	// si tienen el mismo id. La lista devuelta debe estar ordenada por fecha de entrega del
	// producto (de más reciente a más lejana en el tiempo). Se considera que dos productos
	// son iguales si tienen el mismo id.
	public static List<Producto> eliminarDuplicados(List<Producto> productos){
		Set<Producto> setProductos = new TreeSet<>(new ComparadorEntrega().reversed());
		setProductos.addAll(productos);
		return new ArrayList<>(setProductos);
	}
}
