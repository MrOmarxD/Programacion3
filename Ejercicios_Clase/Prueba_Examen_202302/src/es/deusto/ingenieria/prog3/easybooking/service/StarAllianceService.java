package es.deusto.ingenieria.prog3.easybooking.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.prog3.easybooking.domain.AirAlliance;
import es.deusto.ingenieria.prog3.easybooking.domain.Airline;
import es.deusto.ingenieria.prog3.easybooking.domain.Airport;
import es.deusto.ingenieria.prog3.easybooking.domain.BoardingPass;
import es.deusto.ingenieria.prog3.easybooking.domain.Country;
import es.deusto.ingenieria.prog3.easybooking.domain.Flight;
import es.deusto.ingenieria.prog3.easybooking.domain.Plane;
import es.deusto.ingenieria.prog3.easybooking.domain.Reservation;

public class StarAllianceService extends AirAllianceService {

	//Driver de la BBDD
	private static final String DRIVER = "org.sqlite.JDBC";
	//Cadena de conexión a la BBDD
	private static final String CONNECTION_STRING = String.format("jdbc:sqlite:resources/db/%s.db", AirAlliance.STAR_ALLIANCE);
	//Conexión a la BBDD
	private Connection conn;

	public StarAllianceService() {
		super(AirAlliance.STAR_ALLIANCE);
	}
	
	/**
	 * Establece la conexión con la BBDD.
	 */
	private void connectDB() {
		try {			
			Class.forName(DRIVER);			
			conn = DriverManager.getConnection(CONNECTION_STRING);			
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error conectando con la BBDD: %s", getAirAlliance(), ex.getMessage()));
		}
	}
	/**
	 * Cierra la conexión con la BBDD.
	 */		
	public void disconnectDB() {
		try {			
			conn.close();			
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error cerrando conexión con la BBDD: %s", getAirAlliance(), ex.getMessage()));
		}
	}
		
	//T2.A Crear la tabla BoardingPass:
	//Implementa el método initDB() para crear la tabla BoardingPass desde código.
	private void createBoardingPassesTable() {
		String sql = "CREATE TABLE IF NOT EXISTS BoardingPasses (\n"
                + " LOCATOR TEXT NOT NULL,\n"
                + " DATE TEXT NOT NULL,\n"
                + " PASSENGER TEXT NOT NULL,\n"
                + " SEAT NUMBER,\n"
                + " PRIMARY KEY(LOCATOR, PASSENGER)\n"
                + " FOREIGN KEY(LOCATOR) REFERENCES RESERVATION(LOCATOR) ON DELETE CASCADE\n"               
                + ");";
		try (Statement stmt = conn.createStatement()) {
			if (stmt.executeUpdate(sql) == 1) {
				logger.info(String.format("%s - Se ha creando tabla BoardingPasses", getAirAlliance()));
			}
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error creando tabla BoardingPasses: %s", getAirAlliance(), ex.getMessage()));
		}
	}
	
	@Override
	public Map<String, Flight> loadFlights() {
		//Se establece la conexión con la BBDD
		connectDB();
		
		//Se crea la tabla BoardingPass (sólo si no existe)
		createBoardingPassesTable();
		
		//Mapas auxiliares para aerolíneas, aeropuertos y aviones
		Map<String, Airport> airportsMap = loadAirportsDB();
		Map<String, Airline> airlinesMap = loadAirlinesDB();
		Map<String, Plane> planesMap = loadPlanesDB();
		Map<String, List<Reservation>> reservationsMap = loadReservationsDB();
		
		//Se inicializa el mapa de vuelos
		flights = new HashMap<>();
		
		//Se recuperan todos los vuelos de la BBDD
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM Flight;")) {
			Flight flight = null;
						
			while (rs.next()) {
				//Se crea un nuevo vuelo por cada registro de la BBDD
				flight = new Flight(rs.getString("CODE"),
						   airportsMap.get(rs.getString("ORIGIN")),
						   airportsMap.get(rs.getString("DESTINATION")),
						   airlinesMap.get(rs.getString("AIRLINE")),								   
						   planesMap.get(rs.getString("PLANE")),
						   rs.getInt("DURATION"),
						   rs.getFloat("PRICE"));
				//Se actualizan las reservas del vuelo, si hubiese alguna reserva previa.
				if (reservationsMap.containsKey(flight.getCode())) {				
					flight.setReservations(reservationsMap.get(flight.getCode()));
				}
				
				//Se añade el vuelo al mapa de vuelos
				flights.put(rs.getString("CODE"), flight);
			}
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error al recuperar los vuelos: %s", getAirAlliance(), ex.getMessage()));
		}
		
		logger.info(String.format("%s - %d vuelos cargados de la BBDD", getAirAlliance(), flights.values().size()));
		
		return flights;
	}
	
	@Override
	public void storeReservation(Reservation reservation) {
		String sql = "INSERT INTO Reservation VALUES('%s', '%s', '%s', '%s');";
		
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(String.format(sql,
											reservation.getLocator(),
											reservation.getFlight().getCode(),
											reservation.getDate(),
											String.join(";", reservation.getPassengers())));				
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error guardando reserva: %s", getAirAlliance(), ex.getMessage()));
		}
 	}
	
	//T2.C Guardar una tarjeta de embarque en la BBDD:
	//Implementa el método storeBoardingPass(BoardingPass boardingPass) para almacenar
	//una tarjeta de embarque en la BBDD. 
	@Override
	public void storeBoardingPass(BoardingPass boardingPass) {
		String sql = "INSERT INTO BoardingPasses VALUES('%s', '%s', '%s', %d);";
		
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(String.format(sql,
											boardingPass.getLocator(),										
											String.valueOf(boardingPass.getDate()),
											boardingPass.getPassenger(),
											boardingPass.getSeat()));		
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error guardando tarjeta de embarque: %s", getAirAlliance(), ex.getMessage()));
		}
	}
		
	/**
	 * Devuelve un mapa que indexa las aerolíneas por código de aerolínea.
	 * @return Map<String, Airline> con las aerolíneas.
	 */
	private Map<String, Airline> loadAirlinesDB() {
		Map<String, Airline> airline = new HashMap<>();	
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM Airline;")) {
			
			while (rs.next()) {
				airline.put(rs.getString("CODE"), 
						new Airline(rs.getString("CODE"), 
								    rs.getString("NAME"),
								    Country.valueOf(rs.getString("COUNTRY")),
								    AirAlliance.valueOf(rs.getString("ALLIANCE")))
				);
			}			
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error recuperando aerolíneas: %s", getAirAlliance(), ex.getMessage()));
		}
		
		return airline;
	}
	
	/**
	 * Devuelve un mapa que indexa los aeropuertos por código de aeropuerto.
	 * @return Map<String, Airport> con los aeropuertos.
	 */
	private Map<String, Airport> loadAirportsDB() {
		Map<String, Airport> airports = new HashMap<>();	
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM Airport;")) {
			
			while (rs.next()) {
				airports.put(rs.getString("CODE"), 
						new Airport(rs.getString("CODE"), 
								    rs.getString("NAME"),
								    rs.getString("CITY"),
								    Country.valueOf(rs.getString("COUNTRY")))
				);
			}			
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error recuperando aeropuertos: %s", getAirAlliance(), ex.getMessage()));
		}
		
		return airports;
	}
	
	/**
	 * Devuelve un mapa que indexa los aviones por código de avión.
	 * @return Map<String, Plane> con los vuelos.
	 */
	private Map<String, Plane> loadPlanesDB() {
		Map<String, Plane> planes = new HashMap<>();	
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM Plane;")) {
			
			while (rs.next()) {
				planes.put(rs.getString("CODE"), 
						new Plane(rs.getString("CODE"), 
								  rs.getString("NAME"),
								  rs.getInt("SEATS"))
				);
			}			
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error recuperando aviones: %s", getAirAlliance(), ex.getMessage()));
		}
		
		return planes;
	}
	
	
	//T2.B Cargar las tarjetas de embarque desde la BBDD:
	//Modifica el método loadReservationsDB() para que al recuperar los datos de las reservas
	//se recuperen también los datos de las tarjetas de embarque asociadas a cada reserva.
	/**
	 * Devuelve un mapa que indexa la lista de reservas de un vuelo por el código de vuelo.
	 * @return Map<String, List<Reservation>> con las reservas.
	 */
	private Map<String, List<Reservation>> loadReservationsDB() {
		Map<String, List<Reservation>> reservations = new HashMap<>();	
		
		try (Statement stmt1 = conn.createStatement();
				 Statement stmt2 = conn.createStatement();
				 ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Reservation;")) {
				
			Reservation reservation;
			String sql = "SELECT * from BoardingPasses WHERE LOCATOR = '%s'";
			ResultSet rs2;
			
			while (rs1.next()) {
				reservations.putIfAbsent(rs1.getString("FLIGHT"), new ArrayList<>());
				
				reservation = new Reservation(rs1.getString("LOCATOR"), 
						 					  flights.get(rs1.getString("FLIGHT")),
						 					  Long.valueOf(rs1.getString("DATE")),
						 					  Arrays.asList(rs1.getString("PASSENGERS").split(";")));
				
				//Se recuperan las tarjetas de embarque de la BBDD y se añaden a la reserva
				rs2 = stmt2.executeQuery(String.format(sql, reservation.getLocator()));
				
				while (rs2.next()) {
					reservation.addBoardingPass(new BoardingPass(Long.parseLong(rs2.getString("DATE")),
																 rs2.getString("LOCATOR"),
																 rs2.getString("PASSENGER"),
																 rs2.getInt("SEAT")));
				}
				
				rs2.close();
				
				reservations.get(rs1.getString("FLIGHT")).add(reservation);
			}
		} catch (Exception ex) {
			logger.warning(String.format("%s - Error recuperando reservas: %s", getAirAlliance(), ex.getMessage()));
		}
			
		return reservations;
	}
}