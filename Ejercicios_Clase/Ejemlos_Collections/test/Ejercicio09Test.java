import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Ejercicio09Test {
    
    @Test
    public void testConvertirMayusculas() {
        List<String> palabras = Arrays.asList("historia", "caricatura", "empatar", "zapato", "cueva");
        palabras = Ejercicio09.convertirMayusculas(palabras);
        assertEquals("HISTORIA", palabras.get(0));
        assertEquals("CARICATURA", palabras.get(1));
        assertEquals("EMPATAR", palabras.get(2));
        assertEquals("ZAPATO", palabras.get(3));
        assertEquals("CUEVA", palabras.get(4));
    }
}
