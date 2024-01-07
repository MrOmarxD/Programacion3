package es.deusto.ingenieria.prog3.easybooking.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardingPass implements Serializable {

	private static final long serialVersionUID = 1L;	
	private long date;
	private String locator;
	private String passenger;
	private int seat;

	public BoardingPass(long date, String locator, String passenger, int seat) {
		this.date = date;
		this.locator = locator;
		this.passenger = passenger;
		this.seat = seat;
	}
	
	public BoardingPass(String locator, String passenger, int seat) {
		this.date = System.currentTimeMillis();
		this.locator = locator;
		this.passenger = passenger;
		this.seat = seat;
	}

	public long getDate() {
		return date;
	}

	public String getLocator() {
		return locator;
	}
	
	public String getPassenger() {
		return passenger;
	}

	public int getSeat() {
		return seat;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMMM-dd - HH:mm:ss");
		
		return String.format("%s : %03d - %s [%s]",
							formatter.format(new Date(date)), seat, passenger, locator);		
	}
	
	/**
	 * Crea un objeto BoardingPass a partir de una cadena de texto separada por "#".
	 * @param data String con la cadena de texto separada por "#".
	 * @return BoardingPass con el nuevo objeto creado.
	 * @throws Exception Si se produce un error al generar el objeto BoardingPass.
	 */
	public static BoardingPass parseCSV(String data) throws Exception {
		try {
			String[] fields = data.split("#");			

			return new BoardingPass(Long.valueOf(fields[0]),
									fields[1],
									fields[2],
								   	Integer.valueOf(fields[3]));			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(String.format("%s from CSV error: %s", BoardingPass.class, data));
		}
	}
}