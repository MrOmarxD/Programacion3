package tema4;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Ejercicio11 {

	//Recibe una lista de palabras y devuelve una nueva lista de palabras eliminando los elementos
	//duplicados de la primera lista. Se debe mantener el orden relativo de las palabras de la
	//primera lista.
	public static List<String> eliminarDuplicados(List<String> palabras){
		Set<String> setPalabras = new LinkedHashSet<>(palabras);
		return new ArrayList<>(setPalabras); 
	}
}
