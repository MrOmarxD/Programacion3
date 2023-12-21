
public class Ejercicio10 {

	//Método invertirPalabras que recibe un string que contiene una frase y lo devuelve 
	//invertido palabra a palabra (considerando los separadores habituales espacio, tabulador, 
	//salto de línea, símbolos de puntuación)
	public static String invertirPalabras(String str) {
		if(str.isEmpty()) return "";
		if(str.indexOf(" ") == -1) return str;
		return invertirPalabras(str.substring(str.indexOf(" ") + 1)) + " " + str.substring(0, str.indexOf(" "));
	}
	
	public static String invertirPalabras2(String str) {
		if(str.isEmpty()) return "";
		String[] partes = str.split("[\\W]", 2);
		if(partes.length < 2) return str;
		return invertirPalabras2(partes[1]) + " " + partes[0];
	}
	
	public static void main(String[] args) {
		String str = "Hola que tal";
		System.out.println(invertirPalabras2(str));
	}
}
