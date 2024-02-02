package gaoMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.Producto;
import domain.ProductoCarrito;
import domain.TipoAlimento;
import domain.TipoProducto;
import domain.Producto.Estado;

public class ProductoCarritoTest {
	private ProductoCarrito pc;
	private ProductoCarrito pc2;
	private Producto p;
	
	@Before
	public void setup() {
		p = new Producto(1, "Pan", "pan.jpg", 15.7, 2, TipoProducto.ALIMENTO, TipoAlimento.PAN, Estado.POCAS_UNIDADES, 0);
		pc = new ProductoCarrito(p, 2);
		pc2 = new ProductoCarrito();
	}
	
	@Test
	public void testGetProducto() {
		assertEquals(p, pc.getProducto());
	}
	
	@Test
	public void testProducto() {
		pc2.setProducto(p);
		assertEquals(p, pc2.getProducto());
	}
	
	@Test
	public void testGetCantidad() {
		assertEquals(2, pc.getCantidad());
	}
	
	@Test
	public void testSetCantidad() {
		pc.setCantidad(1);
		assertEquals(1, pc.getCantidad());
	}
	
	@Test
    public void testToString() {
    	assertEquals("ProductoCarrito [producto=Pan, cantidad=2]", pc.toString());
	}
	
	@Test
	public void testHashCode() {
		pc2.setProducto(p);
		assertEquals(pc.hashCode(), pc2.hashCode());
	}
	
	@Test
	public void testEquals() {
		assertTrue(pc.equals(pc));
		
		pc2.setProducto(p);
		assertTrue(pc.equals(pc2));
		
		Producto p2 = new Producto();
		ProductoCarrito pc3 = new ProductoCarrito(p2, 2);
		assertFalse(pc.equals(pc3));
		
		assertFalse(pc.equals(null));
		assertFalse(pc.equals("No es un ProductoCarrito"));
	}
}
