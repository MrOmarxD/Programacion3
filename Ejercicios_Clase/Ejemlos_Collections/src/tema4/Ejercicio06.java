package tema4;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ejercicio06 {
	
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
	
	//Recibe una lista de Productos y la devuelve ordenada por la fecha de entrega de más reciente
	//a más lejana en el tiempo
	public static List<Producto> ordenarProductosEntrega(List<Producto> productos) {
		Collections.sort(productos, Collections.reverseOrder());
	}
}
