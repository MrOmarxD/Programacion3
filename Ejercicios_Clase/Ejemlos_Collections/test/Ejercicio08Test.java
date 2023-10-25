import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Ejercicio08Test {
    
    @Test
    public void testExisteProducto() {
        List<Producto> productos = Arrays.asList(
            new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20)),
            new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29)),
            new Producto(3, "Tornillo", 5, LocalDate.of(2019, 6, 30)),
            new Producto(10, "Clavo", 10, LocalDate.of(2019, 12, 21)),
            new Producto(7, "Palometa", 20, LocalDate.of(2018, 7, 21))
        );
        productos = Ejercicio06.ordenarProductosEntrega(productos);
        assertTrue(Ejercicio08.existeProducto(productos, new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29))));
        assertTrue(Ejercicio08.existeProducto(productos, new Producto(3, "Tornillo", 5, LocalDate.of(2019, 6, 30))));

        assertFalse(Ejercicio08.existeProducto(productos, new Producto(20, "Perno", 1, LocalDate.of(2019, 6, 29))));
        assertFalse(Ejercicio08.existeProducto(productos, new Producto(30, "Tornillo", 5, LocalDate.of(2019, 6, 30))));

    }
}
