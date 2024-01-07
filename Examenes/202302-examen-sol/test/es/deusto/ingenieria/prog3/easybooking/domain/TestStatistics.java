package es.deusto.ingenieria.prog3.easybooking.domain;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.ingenieria.prog3.easybooking.service.OneWorldService;
import es.deusto.ingenieria.prog3.easybooking.service.SkyTimeService;
import es.deusto.ingenieria.prog3.easybooking.service.StarAllianceService;


//Crea un caso de prueba para validar el correcto funcionamiento de la 
//implementación de los dos métodos de la clase Statistics.java. Para 
//ello crea la clase TestStatistics.java en la carpeta de código fuente 
//test y añade una prueba para cada uno de los métodos de esta clase. 
//Para el método List<Airport> passengerTransitRanking(List<Flight> flights):
//- Comprueba que la lista no es null.
//- Comprueba que la lista no está vacía.
//- Comprueba que cada elemento de la lista no es null.
//Para el método Map<Airline, List<Float>> getAirlineOperationsData(List<Flight> flights)
//- Comprueba que el mapa no es null.
//- Comprueba que el mapa incluye 12 aerolineas.
//- Comprueba que ninguna clave ni valor son null.
//- Comprueba que todos los valores tienen un tamaño de 3.

public class TestStatistics {

	private static List<Flight> flights;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		flights = new ArrayList<>();
		//Se recuperan los vuelos de todas las alianzas de aerolíneas
		new OneWorldService().getAllFlights().forEach(f -> flights.add(f));
		new SkyTimeService().getAllFlights().forEach(f -> flights.add(f));
		new StarAllianceService().getAllFlights().forEach(f -> flights.add(f));
	}

	@Test
	public void testGetAirportTransitRanking() {
		List<Airport> ranking = Statistics.getAirportTransitRanking(flights);
		
		//- Comprueba que la lista no es null.		
		assertNotNull(ranking);
		//- Comprueba que la lista no está vacía.
		assertTrue(!ranking.isEmpty());		
		//- Comprueba que cada elemento de la lista no no es null.
		ranking.forEach(a -> assertNotNull(a));
	}

	@Test
	public void testGetAirlineOperationsData() {
		Map<Airline, List<Float>> operations = Statistics.getAirlineOperationsData(flights);
		
		//- Comprueba que el mapa no es null.
		assertNotNull(operations);
		//- Comprueba que el mapa incluye 12 aerolineas.
		assertEquals(12, operations.keySet().size());
				
		operations.values().forEach(value -> {
			//- Comprueba que ningun valor son null.
			assertNotNull(value);
			//- Comprueba que todos los valores tienen un tamaño de 3.
			assertEquals(3, value.size());
		});
		
		//- Comprueba que ninguna clave son null.
		operations.keySet().forEach(key -> assertNotNull(key));
	}
}