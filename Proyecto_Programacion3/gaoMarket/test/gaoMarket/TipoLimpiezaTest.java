package gaoMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import domain.TipoLimpieza;

public class TipoLimpiezaTest {
	@Test
    public void testEnumValues() {
        assertEquals("UTENSILIOS", TipoLimpieza.UTENSILIOS.name());
        assertEquals("PRODUCTOS_LIMPIEZA", TipoLimpieza.PRODUCTOS_LIMPIEZA.name());
    }

    @Test
    public void testEnumComparisons() {
        assertEquals(TipoLimpieza.UTENSILIOS, TipoLimpieza.UTENSILIOS);
        assertEquals(TipoLimpieza.PRODUCTOS_LIMPIEZA, TipoLimpieza.PRODUCTOS_LIMPIEZA);
        assertNotEquals(TipoLimpieza.UTENSILIOS, TipoLimpieza.PRODUCTOS_LIMPIEZA);
    }

    @Test
    public void testEnumConversion() {
        assertEquals(TipoLimpieza.UTENSILIOS, TipoLimpieza.valueOf("UTENSILIOS"));
        assertEquals(TipoLimpieza.PRODUCTOS_LIMPIEZA, TipoLimpieza.valueOf("PRODUCTOS_LIMPIEZA"));
    }
}
