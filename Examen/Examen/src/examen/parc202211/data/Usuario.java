package examen.parc202211.data;

import java.io.Serializable;

/** Clase de datos de cada usuario del sistema DeustoBeReal
 */
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private int nivel;
	private String nick;
	private int numFotos;

	/** Constructor de usuario
	 * @param codigo	Código (único) de usuario
	 * @param nivel	Nivel de experiencia que tiene el usuario en el sistema (0-10, siendo 0=novato y 10=experto)
	 * @param nick	Nick del usuario
	 * @param numFotos	Número de fotos que ha realizado el usuario
	 */
	public Usuario(int codigo, int nivel, String nick, int numFotos) {
		this.codigo = codigo;
		this.nivel = nivel;
		this.nick = nick;
		this.numFotos = numFotos;
	}
	
	/** Constructor de usuario con cero fotos
	 * @param codigo	Código (único) de usuario
	 * @param nivel	Nivel de experiencia que tiene el usuario en el sistema (0-10, siendo 0=novato y 10=experto)
	 * @param nick	Nick del usuario
	 */
	public Usuario(int codigo, int nivel, String nick) {
		this( codigo, nivel, nick, 0 );
	}
	
	public int getCodigo() {
		return codigo;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getNick() {
		return nick;
	}
	public int getNumFotos() {
		return numFotos;
	}
	public void setNumFotos(int numFotos) {
		this.numFotos = numFotos;
	}
	public void incNumFotos() {
		this.numFotos++;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			return ((Usuario)obj).nick.equals( nick );  // Igualdad a igualdad de identificador único
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(U" + codigo + ") " + nick;
	}
}
