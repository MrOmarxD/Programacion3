
public class Ejercicio4 {
	
	//Método imprimirStrBinarios que imprime todos los strings binarios que pueden formarse a partir de un patrón dado, donde 'b' 
	//representa un caracter a substituir por '0' o '1'. Por ejemplo, a partir del patrón "1bb0" se generarán 4 strings posibles 
	//resultado de la substitución: "1000", "1010", "1100", "1110".
	public static void imprimirStrBinarios(String str) {
		if(str.indexOf('b') == -1) System.out.println(str);
		else {
			imprimirStrBinarios(str.substring(0, str.indexOf('b')) + '1' + str.substring((str.indexOf('b') + 1)));
			imprimirStrBinarios(str.substring(0, str.indexOf('b')) + '0' + str.substring((str.indexOf('b') + 1)));
		}
	}
	
	//Forma del profe, la mia es mejor.
	public static void imprimirStrBinarios2(String str) {
		if(!str.contains("b")) System.out.println(str);
		else {
			imprimirStrBinarios2(str.replaceFirst("b", "0"));
			imprimirStrBinarios2(str.replaceFirst("b", "1"));
		}
	}
	
	public static void main(String[] args) {
		String str = "1bb0";
		imprimirStrBinarios2(str);
	}
}
