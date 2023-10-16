package tema4;

import java.util.ArrayList;
import java.util.List;

public class EjemploListaPersonas {

	public static void main(String[] args) {
		List<Persona> perosnas = new ArrayList<>();
		
		Persona a = new Persona("Omar", 22);
		Persona b = new Persona("Beñat", 20);
		Persona c = new Persona("Carlos", 18);
		Persona d = new Persona("Danel", 19);
		Persona f = new Persona("Omar", 22);
		
		perosnas.add(a);
		perosnas.add(b);
		perosnas.add(c);
		
		System.out.println("Num personas: " + perosnas.size());
		System.out.println("Contiene b? " + perosnas.contains(b));
		System.out.println("Contiene d? " + perosnas.contains(d));
		System.out.println("Contiene f? " + perosnas.contains(f));
	}
}
