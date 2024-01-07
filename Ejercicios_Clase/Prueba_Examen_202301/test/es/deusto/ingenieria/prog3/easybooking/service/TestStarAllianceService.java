package es.deusto.ingenieria.prog3.easybooking.service;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import es.deusto.ingenieria.prog3.easybooking.domain.Flight;

//T3.B - Valida el método loadFlights() de la alianza StarAlliance
//Define un método que se ejecute una única vez independientemente de los casos de prueba existentes.
//Crea un caso de prueba en la clase TestStarAllianceService.java que compruebe lo siguiente:
//- Comprueba que el mapa devuelve 109 vuelos.
//- Comprueba que el vuelo con código "LH3465" cuesta 184.08€.
//- Comprueba que el nombre de la aerolínea del vuelo "UA3245" es "United Airlines".
public class TestStarAllianceService {

	private StarAllianceService service;
	
	@Before
	public void setUp() throws Exception {
		service = new StarAllianceService();
	}

	@Test
	public void testLoadFlights() {
		Map<String, Flight> flights = service.loadFlights();
		assertEquals(109, flights.size());
		
		assertEquals(184.08d, flights.get("LH3465").getPrice(), 0.1d);
		assertEquals("United Airlines", flights.get("UA3245").getAirline().getName());
	}
}
