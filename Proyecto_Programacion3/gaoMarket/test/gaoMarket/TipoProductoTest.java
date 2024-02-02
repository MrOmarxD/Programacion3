package gaoMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import domain.TipoProducto;

public class TipoProductoTest {
	@Test
    public void testEnumValues() {
        assertEquals("ALIMENTO", TipoProducto.ALIMENTO.name());
        assertEquals("HIGIENE_Y_BELLEZA", TipoProducto.HIGIENE_Y_BELLEZA.name());
        assertEquals("LIMPIEZA", TipoProducto.LIMPIEZA.name());
    }

    @Test
    public void testEnumComparisons() {
        assertEquals(TipoProducto.ALIMENTO, TipoProducto.ALIMENTO);
        assertEquals(TipoProducto.HIGIENE_Y_BELLEZA, TipoProducto.HIGIENE_Y_BELLEZA);
        assertEquals(TipoProducto.LIMPIEZA, TipoProducto.LIMPIEZA);

        assertNotEquals(TipoProducto.ALIMENTO, TipoProducto.HIGIENE_Y_BELLEZA);
        assertNotEquals(TipoProducto.HIGIENE_Y_BELLEZA, TipoProducto.LIMPIEZA);
        assertNotEquals(TipoProducto.LIMPIEZA, TipoProducto.ALIMENTO);
    }

    @Test
    public void testEnumConversion() {
        assertEquals(TipoProducto.ALIMENTO, TipoProducto.valueOf("ALIMENTO"));
        assertEquals(TipoProducto.HIGIENE_Y_BELLEZA, TipoProducto.valueOf("HIGIENE_Y_BELLEZA"));
        assertEquals(TipoProducto.LIMPIEZA, TipoProducto.valueOf("LIMPIEZA"));

    }
}
