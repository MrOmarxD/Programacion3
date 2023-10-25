import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

public class Ejercicio07Test {
    
    @Test
    public void testExistePalabra() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "empatar", "zapato", "cueva");
        assertTrue(Ejercicio07.existePalabra(palabras, "caricatura"));
        assertTrue(Ejercicio07.existePalabra(palabras, "zapato"));

        assertFalse(Ejercicio07.existePalabra(palabras, "perro"));
    }
}
