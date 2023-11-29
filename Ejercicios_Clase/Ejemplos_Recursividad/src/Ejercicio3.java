
public class Ejercicio3 {

	//Método esPalindromo que devuelve true/false indicando si el string recibido es palíndromo 
	//o no, respectivamente
	public static boolean esPalindromo(String str) {
		int length = str.length();
		if(length <= 1) return true;
		if(str.charAt(length - 1) != str.charAt(0)) return false;
		return esPalindromo(str.substring(1,length - 1));
	}
	
	public static void main(String[] args) {
		String str = "1bb1";
		if(esPalindromo(str))
			System.out.println("si");
		else
			System.out.println("no");
	}
}
