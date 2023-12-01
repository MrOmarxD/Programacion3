
public class Ejercicio9 {

	//Método invertirString que recibe un string y lo devuelve invertido carácter a carácter.
	public static String invertirString(String str, int cont) {
		if(cont < str.length()/2) 
			return invertirString(str.substring(0, str.length() - 1) + str.charAt(cont), ++cont);
		return str;
	}
	
	public static void main(String[] args) {
		String str = "1234";
		System.out.println(str + " - " + invertirString(str, 0));
	}
}