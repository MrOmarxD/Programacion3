package es.deusto.ingenieria.prog3.checkin.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.checkin.domain.Seat;

public class ModeloTabla extends DefaultTableModel {
	private List<Seat> lista;
	private List<String> titulos = Arrays.asList( "", "A", "B", "C", "", "D", "E", "F");
	
	public ModeloTabla(List<Seat> l) {
		lista = l;
		System.out.println(lista.size());
	}

	@Override
	public int getRowCount() {
		if(lista==null)
			return 0;
		return lista.size()/6; //Dividimos entre 6 porque hay 6 asiento por fila
	}

	@Override
	public int getColumnCount() {
		return titulos.size();
	}
	
	
	@Override
	public String getColumnName(int column) {
		return titulos.get(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		// row = 1  column = 1  -> 1A
		// row = 1  column = 2  -> 1B
		// row = 1  column = 6  -> 1E
		//                    fila 0                 fila 1                  fila 2
		//           0  1  2  3  4  5         6  7  8  9  10  11        12 13 14 15 16 17 posiciones en la lista
		//           1  2  3  5  6  7         1  2  3  5   6  7          1  2   3  5  6 7 posiciones en la JTable
		//           
		//lista ->   1A 1B 1C 1D 1E 1F        2A 2B 2C 2D 2E 2F...      3A             3F
		//Relación entre el número de fila y la posición de la lista en la que está el primer asiento de esa fila
		// fila*6+column-1  (1-3)
		// fila*6+column-2  (5-7)
			switch(column) {
				case 0: return row+1;
				case 4: return "";
				case 1: return lista.get(row*6+column-1);
				case 2: return lista.get(row*6+column-1);
				case 3: return lista.get(row*6+column-1);
				case 5: return lista.get(row*6+column-2);
				case 6: return lista.get(row*6+column-2);
				case 7: return lista.get(row*6+column-2);
				default: return null;
			}
	}
	
	
}






