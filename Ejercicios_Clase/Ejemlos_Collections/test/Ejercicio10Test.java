import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

public class Ejercicio10Test {
    
    @Test
    public void testConvertirMayusculas() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "historia", "empatar", "zapato", "caricatura", "zapato", "cueva");
        assertEquals(8, palabras.size());
        
        palabras = Ejercicio10.eliminarDuplicados(palabras);
        assertEquals(5, palabras.size());

        assertTrue(palabras.contains("historia"));
        assertTrue(palabras.contains("caricatura"));
        assertTrue(palabras.contains("empatar"));
        assertTrue(palabras.contains("zapato"));
        assertTrue(palabras.contains("cueva"));
    }
}
