package ejercicio2;

public class Ahorcado {
	
	private final String PALABRA = "impresora";
	private String palAdivinar;
	private String palEnCurso;
	private int maxIntentos;
	private int numIntentos;
	private GestorXML gestorxml = new GestorXML("palabras.xml");
	
	
	//Constructores
	public Ahorcado() {
		this.palAdivinar = this.PALABRA;
		this.palEnCurso = "";
		for(int i = 0; i<palAdivinar.length(); i++) {
			palEnCurso+='-';
		}
		this.maxIntentos = palAdivinar.length()/2;
		this.numIntentos = 0;
	}
	public Ahorcado(int numLetras) {
		this.palAdivinar = gestorxml.palabraAleatoria(numLetras);
		this.palEnCurso = "";
		for(int i = 0; i<palAdivinar.length(); i++) {
			palEnCurso+='-';
		}
		this.maxIntentos = palAdivinar.length()/2;
		this.numIntentos = 0;
	}
	
	//Metodo tirar
	public boolean tirar(String letra) {
		for (int i = 0; i<palAdivinar.length(); i++) {
			if(palAdivinar.substring(i, i+1).equals(letra+"")) 
				palEnCurso = palEnCurso.substring(0,i)+letra+palEnCurso.substring(i+1);
		}
		if(palAdivinar.indexOf(letra)!=-1)
			return true;
		numIntentos++;
		return false;
	}
	
	//Metodo completo
	public boolean completo() {
		if(palAdivinar.equals(palEnCurso))
			return true;
		return false;
	}
	
	//Metodo respuestaToBigString
	public String respuestaToBigString() {
		String p="";
		for(int i = 0; i<palEnCurso.length(); i++) {
			p+=palEnCurso.charAt(i)+" ";
		}
		return p.trim().toUpperCase();
	}
	
	//get/set
	public int getMaxIntentos() {
		return maxIntentos;
	}
	public int getNumIntentos() {
		return numIntentos;
	}
	public String getPalAdivinar() {
		return palAdivinar;
	}
	
	//Metodo anidirAcierto
	public void anidirAcierto() {
		gestorxml.anidirAcierto(palAdivinar.length(), palAdivinar);
	}
	
	//Main
	public static void main(String[] args) {
		Ahorcado a = new Ahorcado();
		a.palEnCurso = "cr---a--ra";
		System.out.println(a.respuestaToBigString());
	}
}
