package es.deusto.ingenieria.prog3.easybooking.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import es.deusto.ingenieria.prog3.easybooking.domain.AirAlliance;
import es.deusto.ingenieria.prog3.easybooking.domain.BoardingPass;
import es.deusto.ingenieria.prog3.easybooking.domain.Flight;
import es.deusto.ingenieria.prog3.easybooking.domain.Reservation;

public class SkyTimeService extends AirAllianceService {

	//Nombre del fichero con la información serializada de los vuelos
	private static final String FILE_NAME = String.format("resources/data/%s.dat", AirAlliance.SKY_TEAM);
	
	public SkyTimeService() {
		super(AirAlliance.SKY_TEAM);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Flight> loadFlights() {
		Map<String, Flight> flights = new HashMap<>();
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			flights = (Map<String, Flight>) in.readObject();
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error al guardar una nueva reserva: %s", getAirAlliance(), ex.getMessage()));
		}
		
		logger.info(String.format("%s - %d datos cargados correctamente", getAirAlliance(), flights.values().size()));
		
		return flights;
	}	
		
	@Override
	public void storeReservation(Reservation reservation) {		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			out.writeObject(flights);
			logger.info(String.format("%s - datos actualizados en el fichero '%s'", getAirAlliance(), FILE_NAME));
		} catch(Exception ex) {
			logger.warning(String.format("%s - Error al actualizar los datos: %s", getAirAlliance(), ex.getMessage()));
		}
 	}

	@Override
	public void storeBoardingPass(BoardingPass boardingPass) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			out.writeObject(flights);
			logger.info(String.format("%s - datos actualizados en el fichero '%s'", getAirAlliance(), FILE_NAME));
		} catch(Exception ex) {
			logger.warning(String.format("%s - Error al guardar una nueva tarjeta de embarque: %s", getAirAlliance(), ex.getMessage()));
		}		
	}
}