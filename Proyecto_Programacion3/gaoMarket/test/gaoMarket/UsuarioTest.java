package gaoMarket;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.Usuario;

public class UsuarioTest {
	private Usuario u;
	private Usuario u2;
	
	@Before
	public void setup() {
		u = new Usuario("Juan", "Fernandez", "juanfer", 660044567, "juan@gmail.com", "juan123");
		u2 = new Usuario();
	}
	
	@Test
    public void testToString() {
    	assertEquals("Usuario [nombre=Juan, apellidos=Fernandez, nomUsuario=juanfer, numTelefono=660044567, correoElectronico=juan@gmail.com, contrasenya=juan123]", u.toString());
    	assertEquals("Usuario [nombre=, apellidos=, nomUsuario=, numTelefono=0, correoElectronico=, contrasenya=]", u2.toString());
	}
}
