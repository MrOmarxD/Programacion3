import java.util.ArrayList;
import java.util.List;

public class Ejercicio6 {

	//Método generarstringNumericos, que consiste en una modificación del Ejercicio 5 
	//para guardar todos los strings generados en una lista, que deberá ser retornada por el 
	//método, en vez de imprimir los resultados por pantalla.
	public static List<String> generarstringNumericos(String str, List<String> lst){
		List<String> lstStr = lst;
		if(str.indexOf('#') == -1) lstStr.add(str);
		else {
			for(int i = 0; i<= 9; i++){
				generarstringNumericos(str.replaceFirst("#", i+""), lstStr);
			}
		}
		return lstStr;
	}
	
	public static void main(String[] args) {
		List<String> lstStr = new ArrayList<>();
		String str = "23#4#";
		System.out.println(generarstringNumericos(str, lstStr));
	}
	
	//Para casa 8,9,10 y 11
}
