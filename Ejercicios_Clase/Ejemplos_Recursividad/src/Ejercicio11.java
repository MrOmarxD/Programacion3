
public class Ejercicio11 {
	private final static char[] HEXADECIMAL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F'};

	//Método longAHexa que recibe un long y devuelve su conversión a hexadecimal
	public static String longAHexa(long num) {
		if(num == 0) return "";
		int resto = (int) num % 16;
		return longAHexa(num / 16) + HEXADECIMAL[resto];
	}
	
	public static void main(String[] args) {
		long num = 30;
		System.out.println(num + " - " + longAHexa(num));
	}
}
