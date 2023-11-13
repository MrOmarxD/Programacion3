package es.deusto.ingenieria.prog3.easybooking.service;
import static org.junit.Assert.*;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import es.deusto.ingenieria.prog3.easybooking.domain.Flight;
import es.deusto.ingenieria.prog3.easybooking.domain.Reservation;

public class SkyTimeServiceTest {

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
