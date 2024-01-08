package examen.parc202211.gui;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import org.junit.Test;

import examen.parc202211.data.ServicioPersistenciaDeustoBeReal;
import examen.parc202211.data.ServicioPersistenciaFicheros;

public class TestCargaDatos {

	@Test
	public void test() {
		ServicioPersistenciaDeustoBeReal servicio = new ServicioPersistenciaFicheros();
		servicio.initDatosTest( "res/examen/parc202211/test/deustoBeReal.dat", "res/examen/parc202211/test", "" );
		int numFotosAnterior = servicio.getNumeroFotos();
		List<String> errores = VentanaDeustoBeReal.cargarDatos( servicio, new File( "res/examen/parc202211/carga/datos_fotos_aCargar.txt" ) );
		assertEquals( 6, errores.size() );
		assertEquals( numFotosAnterior + 9, servicio.getNumeroFotos() );
		assertEquals( "Línea 9: no existe categoría", errores.get(0) );
		assertEquals( "Línea 10: fecha incorrecta", errores.get(1) );
		assertEquals( "Línea 11: no existe usuario", errores.get(2) );
		assertEquals( "Línea 13: no existe categoría", errores.get(3) );
		assertEquals( "Línea 14: no existe foto", errores.get(4) );
		assertEquals( "Línea 15: formato incorrecto", errores.get(5) );
	}

}
