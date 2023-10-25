import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Ejercicio16Test {

    private Ejercicio16 ejercicio16;

    @Before
    public void setUp() {
        List<Producto> productos = Arrays.asList(
            new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20)),
            new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29)),
            new Producto(3, "Tornillo", 5, LocalDate.of(2018, 5, 20)),
            new Producto(10, "Clavo", 10, LocalDate.of(2019, 12, 21)),
            new Producto(7, "Palometa", 20, LocalDate.of(2019, 12, 21))
        );

        ejercicio16 = new Ejercicio16(productos);
    }
    
    @Test
    public void testObtenerProductos() {
        List<Producto> productos = ejercicio16.obtenerProductos(LocalDate.of(2018, 5, 20));
        assertEquals(2, productos.size());
        assertTrue(productos.contains(new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20))));
        assertTrue(productos.contains(new Producto(3, "Tornillo", 5, LocalDate.of(2018, 5, 20))));

        productos = ejercicio16.obtenerProductos(LocalDate.of(2019, 6, 29));
        assertEquals(1, productos.size());
        assertTrue(productos.contains(new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29))));

        productos = ejercicio16.obtenerProductos(LocalDate.of(2019, 12, 21));
        assertEquals(2, productos.size());
        assertTrue(productos.contains(new Producto(10, "Clavo", 10, LocalDate.of(2019, 12, 21))));
        assertTrue(productos.contains(new Producto(7, "Palometa", 20, LocalDate.of(2019, 12, 21))));

        productos = ejercicio16.obtenerProductos(LocalDate.of(2020, 12, 21));
        assertTrue(productos.isEmpty());
    }

    @Test
    public void testObtenerFechas() {
        List<LocalDate> fechas = ejercicio16.obtenerFechas();
        assertEquals(3, fechas.size());

        List<LocalDate> esperado = Arrays.asList(
            LocalDate.of(2019, 12, 21),
            LocalDate.of(2019, 6, 29),
            LocalDate.of(2018, 5, 20)
        );

        assertEquals(esperado, fechas);
    }
}
