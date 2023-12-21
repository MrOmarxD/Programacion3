
public class Ejercicio8 {

	//Método calcularCociente que obtiene el cociente entre dos números enteros, mayores o 
	//iguales que cero, aplicando restas sucesivas.
	public static int calcularCociente(int num1, int num2) {
		if(num1 == 0 || num2 == 0 || num2 > num1) return 0;
		return 1 + calcularCociente(num1 - num2, num2);
	}
	
	public static void main(String[] args) {
		int num1 = 12;
		int num2 = 2;
		System.out.println("El cociente de " + num1 + "/" + num2 + ":" + calcularCociente(num1, num2));
	}
}
