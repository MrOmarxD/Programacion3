package es.deusto.ingenieria.prog3.easybooking.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Statistics {

	
	//T3.A Crea el ranking de tránsito de pasajeros por un aeropuerto:
	//Este método devuelve la lista ordenada de mayor a menor tránsito de pasajeros 
	//por un aeropuerto. El tránsito de pasajeros se calcula sumando el número de 
	//tarjetas de embarque generadas para los vuelos que despegan o aterrizan en 
	//dicho aeropuerto. La lista debe contener únicamente los aeropuertos por los 
	//que ha transitado al menos una persona.
	/**
	 * Delvuelve el ranking de aeropuertos en base al tránsito de pasajeros. 
	 * @param flights List<Flight> con la lista completa de vuelos
	 * @return List<Airport> ranking de aeropuertos.
	 */
	public static List<Airport> getAirportTransitRanking(List<Flight> flights) {
		//Mapa con el contador de pasajeros para cada aeropuerto		
		Map<Airport, Integer> transitMap = new HashMap<>();
		
		//Se recorren todos los vuelos
		flights.forEach(flight -> {
			//Si el vuelo tiene reservas
			if (!flight.getReservations().isEmpty()) {				
				//Se recorren las reservas
				flight.getReservations().forEach(r -> {
					//Si el aeropuerto origen no estaba en el mapa, se inicializa el contador a 0
					transitMap.putIfAbsent(flight.getOrigin(), 0);
					//Se incrementa el contador de personas del aeropuerto origen
					transitMap.put(flight.getOrigin(), 
							Integer.valueOf(transitMap.get(flight.getOrigin()) + r.getPassengers().size()));
					//Si el aeropuerto origen no estaba en el mapa, se inicializa el contador a 0
					transitMap.putIfAbsent(flight.getDestination(), 0);
					//Se incrementa el contador de personas del aeropuerto destino
					transitMap.put(flight.getDestination(), 
							Integer.valueOf(transitMap.get(flight.getDestination()) + r.getPassengers().size()));					
				});
			}	
		});
		
		//Se crea un Comparator basado en el número de pasajeros de mayor a menor.
		Comparator<Airport> comparator = (a1, a2) -> { return Integer.compare(transitMap.get(a1), transitMap.get(a2)) * -1; };

		//Se crea un TreeMap usando el comparador. De esta forma se ordenan las claves
		Map<Airport, Integer> sortedMap = new TreeMap<>(comparator);
		sortedMap.putAll(transitMap);		

		//Se retornan las claves del TreeMap		
		return new ArrayList<>(sortedMap.keySet());
	}
	
	
	//T3.B Crea el mapa de estadísticas de operaciones de las compañías aérea:
	//Este método devuelve un mapa que indexa para cada aerolínea una lista con 
	//información acumulada de los vuelos operados por cada aerolínea. La lista
	//contendrá los siguientes valores (en este orden):
	//- Número total de ingresos de todas las reservas.
	//- Número total de pasajeros (se calcula a partir del número de tarjetas de embarque)
	//- Número de asientos de los aviones de todos los vuelos.

	/**
	 * Delvuelve el mapa de estadísticas de operaciones
	 * @param flights List<Flight> con la lista completa de vuelos
	 * @return Map<Airline, List<Float>> con las estadísticas de operaciones
	 */
	public static Map<Airline, List<Float>> getAirlineOperationsData(List<Flight> flights) {
		Map<Airline, List<Float>> result = new HashMap<>();
		
		//Se recorren todos los vuelos 
		flights.forEach(flight -> {
			//Se inicializa la lista de valores para la aerolínea			
			result.putIfAbsent(flight.getAirline(), new ArrayList<>(Arrays.asList(0f,0f,0f)));
			
			//Se incrementa el número de asientos: 3er valor
			result.get(flight.getAirline()).set(2, result.get(flight.getAirline()).get(2) + 
															  flight.getPlane().getSeats());						
			//Si el vuelo tiene reservas
			if (!flight.getReservations().isEmpty()) {	
				//Se recorren las reservas
				flight.getReservations().forEach(reservation -> {
					//Se incrementan los ingresos: 1er valor
					result.get(flight.getAirline()).set(0, result.get(flight.getAirline()).get(0) + 
																	  flight.getPrice() * reservation.getPassengers().size());					
					//Si hay alguna tarjera de embarque
					if (!reservation.getBoardingPasses().isEmpty()) {	
						//Se incrementan los pasajeros: 2do valor
						result.get(flight.getAirline()).set(1, result.get(flight.getAirline()).get(1) + 
																		  reservation.getBoardingPasses().size());					
					}					
				});				
			}
		});
		
		return result;
	}
}