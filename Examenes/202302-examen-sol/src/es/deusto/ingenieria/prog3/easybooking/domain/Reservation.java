package es.deusto.ingenieria.prog3.easybooking.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Reservation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String locator;
	private Flight flight;
	private long date;
	private List<String> passengers;
	private List<BoardingPass> boardingPasses;
	
	public Reservation(String locator, Flight flight, long date, List<String> passengers) {
		this.locator = locator;
		this.flight = flight;
		this.date = date;
		this.passengers = passengers;
		this.boardingPasses = new ArrayList<>();
	}
	
	public String getLocator() {
		return locator;
	}

	public Flight getFlight() {
		return flight;
	}
	
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public long getDate() {
		return date;
	}

	public List<String> getPassengers() {
		return passengers;
	}
	
	public List<BoardingPass> getBoardingPasses() {
		return boardingPasses;
	}
	
	public void setBoardingPasses(List<BoardingPass> boardingPasses) {
		if (boardingPasses != null) {
			this.boardingPasses = boardingPasses;
		}
	}
	
	public void addBoardingPass(BoardingPass boardingPass) {
		if (boardingPass != null && boardingPass.getLocator().equals(this.locator)) {
			this.boardingPasses.add(boardingPass);
		}
	}
	
	public BoardingPass getBoardingPass(String name) {		
		if (name != null) {
			for (BoardingPass b : boardingPasses)
				if (b.getPassenger().equalsIgnoreCase(name)) {
					return b;
				}
		}
		
		return null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(flight, locator);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			Reservation other = (Reservation) obj;
			return other.locator.equals(this.locator);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s: %s - %d (%02d passengers)", locator, flight, date, passengers.size());		
	}
	
	/**
	 * Crea un objeto Reservation a partir de una cadena de texto separada por "#".
	 * @param data String con la cadena de texto separada por "#".
	 * @return Reservation con el nuevo objeto creado.
	 * @throws Exception Si se produce un error al generar el objeto Reservation.
	 */
	public static Reservation parseCSV(String data) throws Exception {
		try {
			String[] fields = data.split("#");			
			//El vuelo sólo tiene el código porque el resto de datos son desconocidos.
			return new Reservation(fields[0], 
								   new Flight(fields[1], null, null, null, null, 0, 0f), 
								   Long.valueOf(fields[2]), 
								   Arrays.asList(fields[3].split(";")));			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(String.format("%s from CSV error: %s", Reservation.class, data));
		}
	}
}