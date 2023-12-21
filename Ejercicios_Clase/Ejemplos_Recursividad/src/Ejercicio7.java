
public class Ejercicio7 {

	//Método imprimirPermutaciones que imprime por pantalla todas las permutaciones, sin 
	//repetición, para los caractéres de un string dado. Por ejemplo, a partir de “ABC” se generan 
	//6 permutaciones: "ABC", "ACB", "BCA", "BAC", "CAB", CBA"
	public static void imprimirPermutaciones(String str) {
		if(str.length() == 1) System.out.println(str);
	}
	
	private static void permutarRec(char[] str, int i) {
		if(i == str.length - 1) System.out.println(str);
		for(int c = i; c < str.length; c++) {
			interCambiar(str, c, i);
			permutarRec(str, i + 1);
			interCambiar(str, i, c);
		}
	}

	private static void interCambiar(char[] str, int c, int i) {
		
	}
	
	public static void main(String[] args) {
		
	}
}
