package tema4;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Ejercicio12 {

	
	//Recibe una lista de Productos y devuelve una nueva lista de Productos eliminando los
	//elementos duplicados de la primera lista. Se debe mantener el orden relativo de los
	//productos de la primera lista. Se considera que dos Productos son iguales si tienen el
	//mismo id.
	public static List<Producto> eliminarDuplicados(List<Producto> productos){
		Set<Producto> setProductos = new LinkedHashSet()<>(productos);
		return new ArrayList<>(setProductos);
	}
}
