import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Ejercicio04Test {
    
    @Test
    public void testOrdenarPalabrasInverso() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "empatar", "zapato", "cueva");
        palabras = Ejercicio04.ordenarPalabrasLongitudInv(palabras);
        assertEquals("caricatura", palabras.get(0));
        assertEquals("historia", palabras.get(1));
        assertEquals("empatar", palabras.get(2));
        assertEquals("zapato", palabras.get(3));
        assertEquals("cueva", palabras.get(4));
    }
}
