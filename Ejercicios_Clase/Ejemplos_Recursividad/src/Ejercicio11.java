
public class Ejercicio11 {

	//Método longAHexa que recibe un long y devuelve su conversión a hexadecimal
	public static String longAHexa(long num, String str) {
		if(num == 0) return str;
		if(num < 15) {
			switch (num+"") { 
		    case "14":
		    	return str + 'E';
			case "13":
		    	return str + 'D';
			case "12":
		    	return str + 'C';
			case "11":
		    	return str + 'B';
			case "10":
		    	return str + 'A';
			default:
				return str + num;
			}
		}
		return longAHexa(num -15, str + 'F');
	}
	
	public static void main(String[] args) {
		long num = 42;
		System.out.println(num + " - " + longAHexa(num, ""));
	}
}
