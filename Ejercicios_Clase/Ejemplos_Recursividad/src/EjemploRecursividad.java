
public class EjemploRecursividad {
	public static void contar(int n) {
		int cont = 0;
		while(cont <= n) {
			System.out.println(cont);
			cont++;
		}
	}
	public static void contarRecursivo(int n, int cont) {
		System.out.println(cont);
		if(cont < n)
			contarRecursivo(n, ++cont);
	}
	
	public static void contarRecursivoDescendente(int n, int cont) {
		System.out.println(cont);
		if(cont > n)
			contarRecursivoDescendente(n, --cont);
	}
	
	
	// Cuando termina una funcion llama la anterior para terminarla
	public static void contarRecursivoDescendente2(int n, int cont) {
		if(cont < n)
			contarRecursivo(n, ++cont);
		System.out.println(cont);
	}
	
	public static int factorialRecursivo(int n) {
		if(n == 0)
			return 1;
		return n * factorialRecursivo(n - 1);
	}
	
	public static int mcdRecursivo(int n, int m) {
		if(n == m)
			return n;
		if(n > m)
			return mcdRecursivo(n - m, m);
		return mcdRecursivo(n, m - n);
	}
	
	public static int fibonacciRec(int n) {
		if(n == 0) return 0;
		if(n == 1) return 1;
		int f = fibonacciRec(n - 1);
		int g = fibonacciRec(n - 2);
		return f + g;
	}
	
	public static void main(String[] args) {
		//contarRecursivoDescendente2(0, 10);
		/*System.out.println("El factorila de 5 es: " + factorialRecursivo(5));
		System.out.println("El mcd de 5 y 7 es: " + mcdRecursivo(5,7));
		System.out.println("El mcd de 7 y 5 es: " + mcdRecursivo(7,5));
		System.out.println("El mcd de 8 y 12 es: " + mcdRecursivo(4,12));
		System.out.println("El mcd de 35 y 7 es: " + mcdRecursivo(35,7));*/
		int n = 9;
		System.out.println("En la posicion " + n + " de fibonacci: " + fibonacciRec(n));
	}
}
