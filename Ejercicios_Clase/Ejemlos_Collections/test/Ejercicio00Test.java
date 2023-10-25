import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class Ejercicio00Test {

	@Test
	void testNoHacerNada() {
		List<Integer> valores = Arrays.asList(3, 5, 1, 2, 5);
		assertEquals(valores, Ejercicio00.noHacerNada(valores));
	}

}
