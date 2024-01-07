package es.deusto.ingenieria.prog3.easybooking.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.easybooking.domain.BoardingPass;
import es.deusto.ingenieria.prog3.easybooking.domain.Flight;

public class CheckInTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private Flight flight;
	private Vector<Vector<String>> data;
	private final List<String> headers = Arrays.asList("LOCALIZADOR", "PERSONA", "ASIENTO");

	public CheckInTableModel(Flight flight) {
		this.flight = flight;
		
		data = new Vector<>();
		
		//Se inicializa el vector data con: localizador, nombre, asiento
		flight.getReservations().forEach(r -> {
			r.getPassengers().forEach(p -> {
				BoardingPass pass = r.getBoardingPass(p);
				
				if (pass != null) {
					data.add(new Vector<String>(Arrays.asList(r.getLocator(), p, String.valueOf(pass.getSeat()))));
				} else {
					data.add(new Vector<String>(Arrays.asList(r.getLocator(), p, "-")));
				}
			});
		});		
	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}

	@Override
	public int getRowCount() {
		if (flight != null) {
			return flight.getPlane().getSeats() - flight.getRemainingSeats();
		} else { 
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return headers.size(); 
	}
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return false;
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {    	
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch (columnIndex) {
			case 0: return flight.getReservation(data.get(rowIndex).get(columnIndex));
			case 1: return data.get(rowIndex).get(columnIndex);
			case 2: return data.get(rowIndex).get(columnIndex);
			default: return null;
		}
	}
}