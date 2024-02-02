package gaoMarket;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.RegistroException;

public class RegistroExceptionTest {

	@Test
	public void testConstructorAndGetCaso() {
		RegistroException exception = new RegistroException("2");
		assertEquals("2", exception.getCaso());
	}
	
	@Test
	public void testMostrar() {		
		RegistroException exception1 = new RegistroException("1");
		RegistroException exception2 = new RegistroException("2");
		RegistroException exception3 = new RegistroException("3");
		RegistroException exception4 = new RegistroException("4");
		RegistroException exception5 = new RegistroException("8");
		
		assertEquals("Debes de rellenar todas las casillas", exception1.mostrar());
		assertEquals("Error a la hora de introducir numero de telefono", exception2.mostrar());
		assertEquals("Error a la hora de introducir el correo", exception3.mostrar());
		assertEquals("Error a la hora de introducir DNI, (La letra debe de estar en mayúscula)", exception4.mostrar());
		assertEquals(null, exception5.mostrar());
	}
}
