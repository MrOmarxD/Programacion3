import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Ejercicio01Test {
    
    @Test
    public void testOrdenarPalabras() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "empatar", "zapato", "cueva");
        palabras = Ejercicio01.ordenarPalabras(palabras);
        assertEquals("caricatura", palabras.get(0));
        assertEquals("cueva", palabras.get(1));
        assertEquals("empatar", palabras.get(2));
        assertEquals("historia", palabras.get(3));
        assertEquals("zapato", palabras.get(4));
    }
}
