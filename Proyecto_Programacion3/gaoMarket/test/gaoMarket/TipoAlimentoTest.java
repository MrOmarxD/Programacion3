package gaoMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import domain.TipoAlimento;

public class TipoAlimentoTest {
	
	@Test
	public void testEnumValues() {
		assertEquals("CARNICOS", TipoAlimento.CARNICOS.name());
		assertEquals("VEGETALES", TipoAlimento.VEGETALES.name());
		assertEquals("BEBIDAS", TipoAlimento.BEBIDAS.name());
		assertEquals("CONGELADOS", TipoAlimento.CONGELADOS.name());
		assertEquals("DULCES", TipoAlimento.DULCES.name());
		assertEquals("PAN", TipoAlimento.PAN.name());
		assertEquals("CEREALES", TipoAlimento.CEREALES.name());
		assertEquals("PASTA", TipoAlimento.PASTA.name());
	}
	
	@Test
	public void testEnumComparisons() {
		assertEquals(TipoAlimento.CARNICOS, TipoAlimento.CARNICOS);
		assertEquals(TipoAlimento.VEGETALES, TipoAlimento.VEGETALES);
		assertEquals(TipoAlimento.BEBIDAS, TipoAlimento.BEBIDAS);
		assertEquals(TipoAlimento.CONGELADOS, TipoAlimento.CONGELADOS);
		assertEquals(TipoAlimento.DULCES, TipoAlimento.DULCES);
		assertEquals(TipoAlimento.PAN, TipoAlimento.PAN);
		assertEquals(TipoAlimento.CEREALES, TipoAlimento.CEREALES);
		assertEquals(TipoAlimento.PASTA, TipoAlimento.PASTA);
		assertNotEquals(TipoAlimento.CARNICOS, TipoAlimento.VEGETALES);
		assertNotEquals(TipoAlimento.BEBIDAS, TipoAlimento.CONGELADOS);
		assertNotEquals(TipoAlimento.DULCES, TipoAlimento.CARNICOS);
		assertNotEquals(TipoAlimento.PAN, TipoAlimento.PASTA);
		assertNotEquals(TipoAlimento.CEREALES, TipoAlimento.PAN);
    }

    @Test
    public void testEnumConversion() {
        assertEquals(TipoAlimento.CARNICOS, TipoAlimento.valueOf("CARNICOS"));
        assertEquals(TipoAlimento.VEGETALES, TipoAlimento.valueOf("VEGETALES"));
        assertEquals(TipoAlimento.BEBIDAS, TipoAlimento.valueOf("BEBIDAS"));
        assertEquals(TipoAlimento.CONGELADOS, TipoAlimento.valueOf("CONGELADOS"));
        assertEquals(TipoAlimento.DULCES, TipoAlimento.valueOf("DULCES"));
        assertEquals(TipoAlimento.PASTA, TipoAlimento.valueOf("PASTA"));
        assertEquals(TipoAlimento.CEREALES, TipoAlimento.valueOf("CEREALES"));
        assertEquals(TipoAlimento.PAN, TipoAlimento.valueOf("PAN"));
        
    }
}
