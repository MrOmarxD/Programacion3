import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Ejercicio13Test {
    
    @Test
    public void testEliminarDuplicados() {
        List<Producto> productos = Arrays.asList(
            new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20)),
            new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20)),
            new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29)),
            new Producto(3, "Tornillo", 5, LocalDate.of(2019, 6, 30)),
            new Producto(10, "Clavo", 10, LocalDate.of(2019, 12, 21)),
            new Producto(3, "Tornillo", 5, LocalDate.of(2019, 6, 30)),
            new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29)),
            new Producto(7, "Palometa", 20, LocalDate.of(2018, 7, 21))
        );
        assertEquals(8, productos.size());

        productos = Ejercicio13.eliminarDuplicados(productos);

        assertEquals(10, productos.get(0).getId());
        assertEquals(3, productos.get(1).getId());
        assertEquals(1, productos.get(2).getId());
        assertEquals(7, productos.get(3).getId());
        assertEquals(5, productos.get(4).getId());
    }
}
