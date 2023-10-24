package tema4;

import java.util.List;
import java.util.function.UnaryOperator;

public class Ejercicio09 {

	// Recibe una lista de palabras y devuelve la lista con todas 
	// sus palabras convertidas en mayúsculas.
	public static List<String> convertirMayusculas(List<String> palabras){
		// Primera forma
		/*
		for(String p: palabras) {
			p.toUpperCase();
		}
		*/
		
		// Segunda forma
		
		// Forma 2.1
		/*palabras.replaceAll(new UnaryOperator<String>() {
			
			@Override
			public String apply(String t) {
				return t.toUpperCase();
			}
		});
		*/
		//Forma 2.2 con landa
		palabras.replaceAll(s -> s.toUpperCase());
		
		return palabras;
	}
}
