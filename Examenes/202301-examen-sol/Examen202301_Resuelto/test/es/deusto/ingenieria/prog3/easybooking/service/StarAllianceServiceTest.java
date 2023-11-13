package es.deusto.ingenieria.prog3.easybooking.service;
import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import es.deusto.ingenieria.prog3.easybooking.domain.Flight;

public class StarAllianceServiceTest {

	private StarAllianceService service;
	
	@Before
	public void setUp() throws Exception {
		service = new StarAllianceService();
	}

	@Test
	public void testLoadFlights() {
		Map<String, Flight> flights = service.loadFlights();
		assertEquals(109, flights.values().size());
		assertEquals(184.08d, flights.get("LH3465").getPrice(), 0.1d);
		assertEquals("United Airlines", flights.get("UA3245").getAirline().getName());
	}
	
}