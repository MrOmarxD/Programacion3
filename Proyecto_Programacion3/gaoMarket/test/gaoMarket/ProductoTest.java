package gaoMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.Producto;
import domain.TipoAlimento;
import domain.TipoProducto;
import domain.Producto.Estado;

public class ProductoTest {
	private Producto p;
	Producto p2;
	
	@Before
	public void setup() {
		p = new Producto(1, "Pan", "pan.jpg", 15.7, 2, TipoProducto.ALIMENTO, TipoAlimento.PAN, Estado.POCAS_UNIDADES, 0);
		p2 = new Producto();
	}
	
	@Test
	public void testGetNombre() {
		assertEquals("Pan", p.getNombre());
	}
	
	@Test
	public void testSetNombre() {
		p.setNombre("Lechuga");
		assertEquals("Lechuga", p.getNombre());
	}
	
	@Test
	public void testGetImagen() {
		assertEquals("pan.jpg", p.getImagen());
	}
	
	@Test
	public void testSetImagen() {
		p.setImagen("lechuga.jpg");
		assertEquals("lechuga.jpg", p.getImagen());
	}
	
	@Test
	public void testGetPrecio() {
		assertEquals(15.7, p.getPrecio(), 0.1);
	}
	
	@Test
	public void testSetPrecio() {
		p.setPrecio(10.5);
		assertEquals(10.5, p.getPrecio(), 0.1);
	}
	
	@Test
	public void testGetCantidad() {
		assertEquals(2, p.getCantidad());
	}
	
	@Test
	public void testSetCantidad() {
		p.setCantidad(1);
		assertEquals(1, p.getCantidad());
	}
	
	@Test
	public void testGetTipoProducto() {
		assertEquals(TipoProducto.ALIMENTO, p.getTipoProducto());
	}
	
	@Test
	public void testSetTipoProducto() {
		p.setTipoProducto(TipoProducto.LIMPIEZA);;
		assertEquals(TipoProducto.LIMPIEZA, p.getTipoProducto());
	}
	
	@Test
	public void testGetCategoria() {
		assertEquals(TipoAlimento.PAN, p.getCategoria());
	}
	
	@Test
	public void testSetCategoria() {
		p.setCategoria(TipoAlimento.PAN);;
		assertEquals(TipoAlimento.PAN, p.getCategoria());
	}
	@Test
	public void testGetEstado() {
		assertEquals(Estado.POCAS_UNIDADES, p.getEstado());
	}
	
	@Test
	public void testSetEstado() {
		p.setEstado(Estado.POCAS_UNIDADES);
		assertEquals(Estado.POCAS_UNIDADES, p.getEstado());
	}
	@Test
	public void testGetDescuento() {
		assertEquals(0, p.getDescuento());
	}
	
	@Test
	public void testSetDescuento() {
		p.setDescuento(0);
		assertEquals(0, p.getDescuento());
	}
	
	@Test
    public void testToString() {
    	assertEquals("Producto [id=1, nombre=Pan, imagen=pan.jpg, precio=15.7, cantidad=2, tipoProducto=ALIMENTO, categoria=PAN, estado=POCAS_UNIDADES, descuento=0]", p.toString());
	}
	
	@Test
	public void testHashCode() {
		Producto p3 = p;
		
        assertEquals(p.hashCode(), p.hashCode());
        assertEquals(p2.hashCode(), p2.hashCode());
        assertEquals(p3.hashCode(), p3.hashCode());

        assertNotEquals(p.hashCode(), p2.hashCode());
        assertEquals(p.hashCode(), p3.hashCode());
	}
	
	@Test
	public void testEquals(){
		Producto p3 = p;
		Producto p4 = new Producto(4, "Pan", "pan.jpg", 15.73, 2, TipoProducto.ALIMENTO, TipoAlimento.PAN, Estado.POCAS_UNIDADES, 0);
		Producto p5 = new Producto(1, "Lechuga", "pan.jpg", 2.12, 4, TipoProducto.ALIMENTO, TipoAlimento.VEGETALES, Estado.POCAS_UNIDADES, 0);;

        assertTrue(p.equals(p));
        assertTrue(p2.equals(p2));
        assertTrue(p.equals(p3));
        assertFalse(p.equals(null));
        assertFalse(p.equals(p2));
        assertFalse(p.equals(p4));
        assertFalse(p.equals(p5));
        assertFalse(p.equals("No es un Producto"));
	}
	
	@Test
	public void testComapreTo() {
		p2.setNombre("Pan");
		Producto p3 = new Producto();
		p3.setNombre("Lechuga");
		assertTrue(p.compareTo(p2) == 0);
		assertTrue(p3.compareTo(p) < 0);
		assertTrue(p2.compareTo(p3) > 0);
	}
}
