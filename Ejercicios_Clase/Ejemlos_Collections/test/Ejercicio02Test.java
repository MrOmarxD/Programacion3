import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Ejercicio02Test {
    
    @Test
    public void testOrdenarPalabrasInverso() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "empatar", "zapato", "cueva");
        palabras = Ejercicio02.ordenarPalabrasInverso(palabras);
        assertEquals("zapato", palabras.get(0));
        assertEquals("historia", palabras.get(1));
        assertEquals("empatar", palabras.get(2));
        assertEquals("cueva", palabras.get(3));
        assertEquals("caricatura", palabras.get(4));
    }
}
