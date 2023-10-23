package tema4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ejercicio10 {

	//Recibe una lista de palabras y devuelve una nueva lista de palabras eliminando los elementos
	//duplicados de la primera lista. No importa el orden resultante en la nueva lista
	public static List<String> eliminarDuplicados(List<String> palabras){
		// se construye un Set de Strings a partir de la lista como en la implementacion del Set
		// hemos utilizado un HashSet por lo que no garantizamos el orden de la lista
		Set<String> setPalabras = new HashSet<String>(palabras);
		return new ArrayList<>(setPalabras);
	}
}
