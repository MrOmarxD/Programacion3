import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculadoraTest {
	
	@Test
	public void testElevar() {
		// elevar(3, 2) -> 9
		assertEquals(9, Calculadora.elevar(3, 2));
		//Hay que buscar casos en los que puede dar error
		assertEquals(1, Calculadora.elevar(5, 0));
	}
}
