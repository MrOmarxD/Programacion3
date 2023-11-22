
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
	public static void main(String[] args) {
		contarRecursivo(10, 0);
	}
}
