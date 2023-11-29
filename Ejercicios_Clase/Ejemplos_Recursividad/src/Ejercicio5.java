
public class Ejercicio5 {

	//Método imprimirStrNumericos que imprime todos los strings numéricos que pueden formarse a 
	//partir de un patrón dado, donde # representa que se substituye por los números de 0-9. 
	//Por ejemplo, a partir del patrón "23#4#", se pueden obtener 100 strings 
	//(10 en el primero x 10 en el segundo): "23040", "23041", "23042", ..., "23948", "23949".
	public static void imprimirStrNumericos(String str) {
		if(str.indexOf('#') == -1) System.out.println(str);
		else {
			for(int i = 0; i<= 9; i++){
				imprimirStrNumericos(str.replaceFirst("#", i+""));
			}
		}
	}
	
	public static void main(String[] args) {
		String str = "23#4#";
		imprimirStrNumericos(str);
	}
}
