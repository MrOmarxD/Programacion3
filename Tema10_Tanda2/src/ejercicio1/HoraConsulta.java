package ejercicio1;

public class HoraConsulta {
	private int hora;
	private int minutos;
	
	//Constructor
	public HoraConsulta(int hora, int minutos) {
		super();
		this.hora = hora;
		this.minutos = minutos;
	}
	
	//toSring
	@Override
	public String toString() {
		String str1="";
		String str2="";
		if(hora<=9)
			str1=0+"";
		if(minutos<=9)
			str2=0+"";
		return str1+hora+":"+str2+minutos;
	}
	
	public static void main(String[] args) {
		HoraConsulta h1 = new HoraConsulta(9, 30);
		HoraConsulta h2 = new HoraConsulta(20, 9);
		HoraConsulta h3 = new HoraConsulta(9, 9);
		System.out.println(h1);
		System.out.println(h2);
		System.out.println(h3);
	}
}
