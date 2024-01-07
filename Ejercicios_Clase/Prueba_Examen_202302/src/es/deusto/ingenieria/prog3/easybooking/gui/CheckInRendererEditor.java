package es.deusto.ingenieria.prog3.easybooking.gui;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.easybooking.domain.Flight;

public class CheckInRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private Flight flight;
	private MainWindow mainWindow;
	
	public CheckInRendererEditor(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	private JButton prepare(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		flight = (Flight) value;
		
		JButton button = new JButton("Check-In");
		button.setEnabled(true);
		button.setToolTipText(String.format("Check-In - %s", flight.getCode()));				
		button.setBackground(table.getBackground());
		
		if (isSelected || hasFocus) {
			button.setBackground(table.getSelectionBackground());
		}
		
		button.addActionListener((e) -> {
			//Se abre el cuadro de diálogo para realizar el Check-in
			new CheckInDialog(mainWindow, flight);
		});
		
		button.setOpaque(true);
		
		return button;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return prepare(table, value, isSelected, false, row, column);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return prepare(table, value, isSelected, hasFocus, row, column);		
	}
	
	@Override
	public Object getCellEditorValue() {
		return flight;
	}
	
    @Override
    public boolean shouldSelectCell(EventObject event) {
        return true;
    }
}