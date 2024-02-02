package io;

public class RegistroException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String caso;
	
	public RegistroException(String caso) {
		this.caso = caso;
	}

	public String getCaso() {
		return caso;
	}

	public String mostrar() {
		switch (caso) {
			case "1":
				return "Debes de rellenar todas las casillas";
			case "2":
				return "Error a la hora de introducir numero de telefono";
			case "3":
				return "Error a la hora de introducir el correo";
			case "4":
				return "Error a la hora de introducir DNI, (La letra debe de estar en mayúscula)";
		}
		return null;
	}
}
