package tema4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EjemploList {
	
	public static void main(String[] args) {
		List<Integer> enteros = new ArrayList<>();
		enteros.add(3);
		enteros.add(7);
		enteros.add(1);
		enteros.add(-7);
		
		System.out.println(enteros.isEmpty());
		System.out.println(enteros.size());
		System.out.println(enteros.get(1));
		System.out.println(enteros.contains(1));
		
		// Ordenamos la lista
		Collections.sort(enteros); // Ordena la propia lista; lo hace de forma ascendente y natural
		
		System.out.println("Contenido de la lista");
		for(Integer i : enteros) {
			System.out.println(i);
		}
	}
}
