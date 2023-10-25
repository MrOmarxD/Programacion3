import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Ejercicio03Test {
    
    @Test
    public void testOrdenarPalabrasInverso() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "empatar", "zapato", "cueva");
        palabras = Ejercicio03.ordenarPalabrasLongitud(palabras);
        assertEquals("cueva", palabras.get(0));
        assertEquals("zapato", palabras.get(1));
        assertEquals("empatar", palabras.get(2));
        assertEquals("historia", palabras.get(3));
        assertEquals("caricatura", palabras.get(4));
    }
}
