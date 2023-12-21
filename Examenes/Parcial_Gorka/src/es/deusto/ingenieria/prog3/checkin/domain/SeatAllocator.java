package es.deusto.ingenieria.prog3.checkin.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;

public class SeatAllocator {
	private Aircraft aircraft;
	private Map<Integer, List<Seat>> seatsMap;

	public SeatAllocator(Aircraft aircraft) {
		this.aircraft = aircraft;
		this.seatsMap = initRowsMap(aircraft.getSeats());		
	}
	
	public Aircraft getAircraft() {
		return aircraft;
	}
	
	public Map<Integer, List<Seat>> getSeatsMap() {
		return seatsMap;
	}
	
	public int getRowSize() {
		return this.seatsMap.values().iterator().next().size();	
	}
	
	public List<SeatClass> getSeatClasses() {
		List<SeatClass> result = new ArrayList<>();
		
		for(List<Seat> rowSeats : seatsMap.values()) {
			if (!result.contains(rowSeats.get(0).getSeatClass())) {
				result.add(rowSeats.get(0).getSeatClass());
			}			
		}
		
		return result;
	}
	
	//TAREA 4. Inicializa un mapa a partir de una lista [1 punto]
	//A partir de la lista de asientos List<Seat> seats debes crear el mapa 
	//Map<Integer, List<Seat>> que agrupa los asientos por número de fila.
	//Este mapa se utilizará como una estructura de datos auxiliar para 
	//buscar los asientos recomendados al realizar el Check-in.
	public Map<Integer, List<Seat>> initRowsMap(List<Seat> seats) {
		Map<Integer, List<Seat>> m = new HashMap<>();
		for(Seat s: seats) {
			if(!m.containsKey(s.row)) {
				m.put(s.row, new ArrayList<>());
			}
			m.get(s.row).add(s);
		}
		return m;
	}

	//TAREA 5. Crea una estructura de datos compleja a partir de otra [2,5 puntos]
	//A partir de una clase de asiento (FIRST_CLASS / EMERGENCY / ECONOMY) y un 
	//número de asientos, debes crear una lista que contenga listas de asientos 
	//contiguos (no ocupados) en una misma fila.
	//Debes procesar los asientos de cada fila (usando el mapa seatsMap que has 
	//inicializado en la tarea anterior) para ir agrupando los asientos libres 
	//que estén juntos. Tienes que agrupar los asientos contiguos de cada fila 
	//e ir guardando los grupos cuyo tamaño sea mayor o igual al número de asientos
	//necesarios. Es decir, si necesitas 3 asientos pero en una fila todos los 
	//asientos están libres, guardas un grupo con los 6 asientos libres.
	//Por otro lado, si necesitas dos asientos y en una fila están ocupados los
	//dos asientos del pasillo (C y D), en ese caso, guardas dos grupos de asientos:
	//(A, B) y (E, F). Para realizar este proceso, ten en cuenta que los asientos
	//de cada fila son todos de la misma clase, están ordenados de la A a la F
	//y que los asientos del pasillo (C y D) se consideran contiguos.
	//Si no existe ningún grupo de asientos que cumpla las condiciones de la 
	//clase y el número de asientos, la lista devuelta estará vacía.
	//Por ejemplo, si se piden 2 asientos de FIRST_CLASS, se devolvería la lista
	//de listas [[1C,1D], [2C,2D], [3C,3D], [4C,4D], [5C, 5D], [6C, 6D], [7C,7D]], 
	//y si se piden 3 de FIRST_CLASS, lista vacía.
	public List<List<Seat>> findAdjacentGroups(SeatClass seatClass, int number, Map<Integer, List<Seat>> seatsMap) {
		List<List<Seat>> l = new ArrayList<>();
		for(int fila: seatsMap.keySet()) {
			List<Seat> libres = new ArrayList<>();
			for(Seat s: seatsMap.get(fila)) {
				if(!s.occupied && s.seatClass.equals(seatClass)) {
					libres.add(s);
				}else {
					if(libres.size()>=number) {
						l.add(new ArrayList<>(libres));
					}else {
						libres = new ArrayList<>();
					}
				}
			}
			if(libres.size()>=number) {
				l.add(new ArrayList<>(libres));
			}else {
				libres = new ArrayList<>();
			}
		}
		return l;
	}
	
	//TAREA 6. Ordena una lista usando 2 criterios [1,5 puntos] 
	//Debes ordenar una lista de listas de asientos List<List<Seat>> siguiendo
	//estos criterios:
	//- Criterio 1: De menor a mayor número de asientos de la lista.
	//- Criterio 2: Si dos listas coinciden en el número de asientos, 
	//  ordenarlas de mayor a menor número de fila de los asientos de la lista.
	public void orderAdjacentGroups(List<List<Seat>> adjacentGroups) {

	}
		
	public List<Seat> findSeats(SeatClass seatClass, int number) {
		List<Seat> result = null;
		
		if (seatsMap != null && seatClass != null && number >= 0 && number <= this.getRowSize()) {
			List<List<Seat>> groupsSeats = findAdjacentGroups(seatClass, number, seatsMap);
			
			orderAdjacentGroups(groupsSeats);
			
			if (!groupsSeats.isEmpty()) {
				result = new ArrayList<>();
				
				for (int i=0; i<number; i++) {
					result.add(groupsSeats.get(0).get(i));
				}
			}
		}
		
		return result;
	}
	
	public void confirmSeats(List<Seat> seatsGroup) {
		if (seatsGroup != null) {
			seatsGroup.forEach(seat -> seat.setOccupied(true));		
		}
	}
}