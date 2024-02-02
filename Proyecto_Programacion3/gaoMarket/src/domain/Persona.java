package domain;

import java.io.Serializable;
import java.util.Objects;

abstract public class Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String nombre;
	protected String apellidos;
	protected String nomUsuario;
	protected int numTelefono;
	protected String correoElectronico;
	protected String contrasenya;
	
	public Persona(String nombre, String apellidos, String nomUsuario, int numTelefono, String correoElectronico,
			String contrasenya) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nomUsuario = nomUsuario;
		this.numTelefono = numTelefono;
		this.correoElectronico = correoElectronico;
		this.contrasenya = contrasenya;
	}

	public Persona() {
		super();
		this.nombre = "";
		this.apellidos = "";
		this.nomUsuario = "";
		this.numTelefono = 0;
		this.correoElectronico = "";
		this.contrasenya = "";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public int getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(int numTelefono) {
		this.numTelefono = numTelefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellidos=" + apellidos + ", nomUsuario=" + nomUsuario
				+ ", numTelefono=" + numTelefono + ", correoElectronico=" + correoElectronico + ", contrasenya="
				+ contrasenya + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(correoElectronico, nomUsuario, numTelefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(correoElectronico, other.correoElectronico)
				&& Objects.equals(nomUsuario, other.nomUsuario) && numTelefono == other.numTelefono;
	}
	
	public abstract boolean esEmpleado();
}
