import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Ejercicio15Test {
    
    @Test
    public void testAgruparEnvios() {
        List<Producto> productos = Arrays.asList(
            new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20)),
            new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29)),
            new Producto(3, "Tornillo", 5, LocalDate.of(2018, 5, 20)),
            new Producto(10, "Clavo", 10, LocalDate.of(2019, 12, 21)),
            new Producto(7, "Palometa", 20, LocalDate.of(2019, 12, 21))
        );

        List<Envio> envios = Ejercicio15.agruparEnvios(productos);
        assertEquals(3, envios.size());

        Envio e1 = new Envio(LocalDate.of(2018, 5, 20), Arrays.asList(
            new Producto(5, "Tuerca", 5, LocalDate.of(2018, 5, 20)),
            new Producto(3, "Tornillo", 5, LocalDate.of(2018, 5, 20))
        ));

        Envio e2 = new Envio(LocalDate.of(2019, 6, 29), Arrays.asList(
            new Producto(1, "Perno", 1, LocalDate.of(2019, 6, 29))
        ));

        Envio e3 = new Envio(LocalDate.of(2019, 12, 21), Arrays.asList(
            new Producto(10, "Clavo", 10, LocalDate.of(2019, 12, 21)),
            new Producto(7, "Palometa", 20, LocalDate.of(2019, 12, 21))
        ));

        List<Envio> esperado = Arrays.asList(e1, e2, e3);
        assertEquals(esperado, envios);
    }
}
