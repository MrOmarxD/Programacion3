package es.deusto.ingenieria.prog3.easybooking.service;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import es.deusto.ingenieria.prog3.easybooking.domain.Flight;

//T3.A - Valida el método loadFlights() de la alianza SkyTeam
//Define un método que se ejecute una única vez independientemente de los casos 
//de prueba existentes. Crea un caso de prueba en la clase TestSkyTeamService.java 
//que compruebe lo siguiente:
//- Que el vuelo "UX2106" no tiene ninguna reserva.
//- Que el pasajero "PETER PARKER" tiene una reserva en el vuelo "DL2983".
//- Que en el vuelo "AF3474" hay más de 100 asientos libres.
 
public class TestSkyTimeService {
	private SkyTimeService service;
	
	@Before
	public void setUp() throws Exception {
		service = new SkyTimeService();
	}

	@Test
	public void testLoadFlights() {
		Map<String, Flight> flights = service.loadFlights();
		assertTrue(flights.get("UX2106").getReservations().isEmpty());
		
		boolean isPeterParker = false;
		for (Reservation r : flights.get("DL2983").getReservations()) {
			if (r.getPassengers().contains("PETER PARKER")) {
				isPeterParker = true;
				break;
			}
		}
		assertTrue(isPeterParker);
		assertTrue(flights.get("AF3474").getRemainingSeats() > 100);
	}
}