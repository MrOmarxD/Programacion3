
public class Ejercicio15 {

	//Método imprimirVariaciones que imprima todos los strings de longitud r obtenidos al tomar r 
	//elementos con repetición de un conjunto de n elementos pasado como un array de caracteres. 
	//Por ejemplo, si recibe el array {A, B, C} y r = generará, AAA, AAB, AAC, ABA, ABB, . . . CCA, 
	//CCB, CCC.
	private static void imprimirVariaciones(char[] conjunto, int r, String resultado) {
		if(r == 0) System.out.println(resultado);
		for(char c : conjunto) {
			imprimirVariaciones(conjunto, r - 1, resultado + c);
		}
	}
	
	public static void main(String[] args) {
		char[] conjunto = {'A', 'B', 'C'};
		imprimirVariaciones(conjunto, conjunto.length, "");
		
	}
}
