package tema4;

import java.util.List;

public class Ejercicio07 {

	// Recibe una lista de palabras y una palabra a buscar en la lista e 
	// indica con un valor booleano si la palabra existe o no en la lista.
	public static boolean existePalabra(List<String> palabras, String Palabra) {
		return palabras.contains(Palabra);
	}
}
