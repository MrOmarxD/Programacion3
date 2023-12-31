package tema4;

import java.util.Comparator;
import java.util.List;

public class Ejercicio05 {
	
	
	static class ComparadorProductosId implements Comparator<Producto>{

		@Override
		public int compare(Producto a, Producto b) {
			return a.getId() - b.getId();		
		}
	}
	
	// Recibe una lista de Productos y la devuelve ordenada por el identificador de pedido
	public static List<Producto> ordenarProductosId(List<Producto> lista) {
		lista.sort(new ComparadorProductosId());
		return lista;
	}
}
