package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RendererCarroCompra implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		JLabel label = new JLabel();
		
		
		if (column == 0) {
			ImageIcon productoImagen = new ImageIcon("resources/productos/" + value.toString());
			productoImagen = new ImageIcon(productoImagen.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			label.setIcon(productoImagen);
			
		}
		
		label.setHorizontalAlignment(JLabel.CENTER);
		if (column == 1) {
			label.setText(value.toString());
		}	
		
		if (column == 2) {
			label.setText(String.format("%.2f €", Double.parseDouble(value.toString())));
		}
		
		if (column == 3) {
			label.setText(value.toString());
		}
		
		if (column == 4) {
			label.setText(String.format("%.2f €", Double.parseDouble(value.toString())));
		}
		
		if (isSelected) {
			label.setOpaque(true);
			label.setBackground(new Color(0, 255, 158));
			
		}
		
		label.setOpaque(true);
		return label;
	}

}
