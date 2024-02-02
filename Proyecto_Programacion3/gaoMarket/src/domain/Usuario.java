package domain;


public class Usuario extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Usuario(String nombre, String apellidos, String nomUsuario, int numTelefono, String correoElectronico,
			String contrasenya) {
		super(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya);
	}
	
	public Usuario() {
		super();
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", nomUsuario=" + nomUsuario
				+ ", numTelefono=" + numTelefono + ", correoElectronico=" + correoElectronico + ", contrasenya="
				+ contrasenya + "]";
	}

	@Override
	public boolean esEmpleado() {
		return false;
	}
	

}
