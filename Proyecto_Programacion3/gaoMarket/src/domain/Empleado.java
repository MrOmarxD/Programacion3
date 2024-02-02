package domain;

public class Empleado extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String dni;
	

	public Empleado(String nombre, String apellidos, String nomUsuario, int numTelefono, String correoElectronico,
			String contrasenya, String dni) {
		super(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya);
		this.dni = dni;
	}
	
	public Empleado() {
		super();
		this.dni = "00000000A";
	}	

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	

	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", nomUsuario=" + nomUsuario
				+ ", numTelefono=" + numTelefono + ", correoElectronico=" + correoElectronico + ", contrasenya="
				+ contrasenya + "]";
	}

	@Override
	public boolean esEmpleado() {
		return true;
	}

}
