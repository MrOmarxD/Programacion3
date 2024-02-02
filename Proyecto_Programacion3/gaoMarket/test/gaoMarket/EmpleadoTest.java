package gaoMarket;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.Empleado;

public class EmpleadoTest {
	private Empleado e;
	
	@Before
	public void setup() {
		e = new Empleado("Juan", "Fernandez", "juanfer", 660044567, "juan@gmail.com", "juan123", "00000000A");
	}
	
	@Test
	public void testEmpleado() {
		Empleado e1 = new Empleado();
		assertEquals("00000000A", e1.getDni());
	}
	
	@Test
	public void testGetDni() {
		assertEquals("00000000A", e.getDni());
	}
	
	@Test
	public void testSetDni() {
		e.setDni("00000000B");
		assertEquals("00000000B", e.getDni());
	}
	
	@Test
    public void testToString() {
    	assertEquals("Empleado [dni=00000000A, nombre=Juan, apellidos=Fernandez, "
    			+ "nomUsuario=juanfer, numTelefono=660044567, correoElectronico=juan@gmail.com, "
    			+ "contrasenya=juan123]", e.toString());
	}
}
