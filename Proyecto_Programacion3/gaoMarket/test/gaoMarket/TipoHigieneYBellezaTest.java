package gaoMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import domain.TipoHigieneYBelleza;

public class TipoHigieneYBellezaTest {
	@Test
    public void testEnumValues() {
        assertEquals("AFEITADO_DEPILACION", TipoHigieneYBelleza.AFEITADO_DEPILACION.name());
        assertEquals("HIGIENE_BUCAL", TipoHigieneYBelleza.HIGIENE_BUCAL.name());
        assertEquals("HIGIENE_INTIMA", TipoHigieneYBelleza.HIGIENE_INTIMA.name());
        assertEquals("CUIDADO_CORPORAL", TipoHigieneYBelleza.CUIDADO_CORPORAL.name());
        assertEquals("PARAFARMACIA_SOLARES", TipoHigieneYBelleza.PARAFARMACIA_SOLARES.name());
    }

    @Test
    public void testEnumComparisons() {
        assertEquals(TipoHigieneYBelleza.AFEITADO_DEPILACION, TipoHigieneYBelleza.AFEITADO_DEPILACION);
        assertEquals(TipoHigieneYBelleza.HIGIENE_BUCAL, TipoHigieneYBelleza.HIGIENE_BUCAL);
        assertEquals(TipoHigieneYBelleza.HIGIENE_INTIMA, TipoHigieneYBelleza.HIGIENE_INTIMA);
        assertEquals(TipoHigieneYBelleza.CUIDADO_CORPORAL, TipoHigieneYBelleza.CUIDADO_CORPORAL);
        assertEquals(TipoHigieneYBelleza.PARAFARMACIA_SOLARES, TipoHigieneYBelleza.PARAFARMACIA_SOLARES);
        assertNotEquals(TipoHigieneYBelleza.AFEITADO_DEPILACION, TipoHigieneYBelleza.HIGIENE_BUCAL);
        assertNotEquals(TipoHigieneYBelleza.HIGIENE_INTIMA, TipoHigieneYBelleza.CUIDADO_CORPORAL);
        assertNotEquals(TipoHigieneYBelleza.PARAFARMACIA_SOLARES, TipoHigieneYBelleza.AFEITADO_DEPILACION);
    }

    @Test
    public void testEnumConversion() {
        assertEquals(TipoHigieneYBelleza.AFEITADO_DEPILACION, TipoHigieneYBelleza.valueOf("AFEITADO_DEPILACION"));
        assertEquals(TipoHigieneYBelleza.HIGIENE_BUCAL, TipoHigieneYBelleza.valueOf("HIGIENE_BUCAL"));
        assertEquals(TipoHigieneYBelleza.HIGIENE_INTIMA, TipoHigieneYBelleza.valueOf("HIGIENE_INTIMA"));
        assertEquals(TipoHigieneYBelleza.CUIDADO_CORPORAL, TipoHigieneYBelleza.valueOf("CUIDADO_CORPORAL"));
        assertEquals(TipoHigieneYBelleza.PARAFARMACIA_SOLARES, TipoHigieneYBelleza.valueOf("PARAFARMACIA_SOLARES"));
    }
}
