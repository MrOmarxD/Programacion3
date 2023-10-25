import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Ejercicio11Test {
    
    @Test
    public void testConvertirMayusculas() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "historia", "empatar", "zapato", "caricatura", "zapato", "cueva");
        assertEquals(8, palabras.size());
        
        palabras = Ejercicio11.eliminarDuplicados(palabras);
        assertEquals(5, palabras.size());

        assertEquals("historia", palabras.get(0));
        assertEquals("caricatura", palabras.get(1));
        assertEquals("empatar", palabras.get(2));
        assertEquals("zapato", palabras.get(3));
        assertEquals("cueva", palabras.get(4));
    }
}
