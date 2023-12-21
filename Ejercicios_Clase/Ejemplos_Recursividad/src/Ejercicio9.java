
public class Ejercicio9 {

	//Método invertirString que recibe un string y lo devuelve invertido carácter a carácter.
	public static String invertirString(String str) {
		if(str.isEmpty()) return "";
		return invertirString(str.substring(1, str.length())) + str.charAt(0);
	}
	
	public static void main(String[] args) {
		String str = "abc";
		System.out.println(str + " - " + invertirString(str));
	}
}