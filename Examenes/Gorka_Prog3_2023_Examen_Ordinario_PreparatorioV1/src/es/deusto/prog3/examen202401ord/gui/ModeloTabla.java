package es.deusto.prog3.examen202401ord.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.prog3.examen202401ord.datos.PreguntaRespuesta;

public class ModeloTabla extends DefaultTableModel{
	private List<PreguntaRespuesta> lista;
	private List<String> titulos = (List<String>) Arrays.asList("PREGUNTA","RESPUESTA");

	public ModeloTabla(List<PreguntaRespuesta> l) {
		lista = l;
	}
	
	@Override
	public int getRowCount() {
		if(lista==null)
			return 0;
		return lista.size();
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
		PreguntaRespuesta pr = lista.get(row); //Relacionamos fila de la tabla con posición de la lista
		switch(column) {
			case 0: return pr.getPregunta();
			case 1: return pr.getRespuesta();
			default: return null;
		}
	}

	
	
}
