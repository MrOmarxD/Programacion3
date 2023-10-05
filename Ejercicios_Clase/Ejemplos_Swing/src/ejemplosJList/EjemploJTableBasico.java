package ejemplosJList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import tema3A.Person;

@SuppressWarnings("serial")
public class EjemploJTableBasico extends JFrame{

	/**
	 * 
	 */
	private static final long serialversionID = 1L;
	
	public EjemploJTableBasico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Ejemplo JList");
		
		List<Person> personas = new ArrayList<Person>();
		personas.add(new Person("Nombre1", "Apellido1", LocalDate.of(1900, 11, 10)));
		personas.add(new Person("Nombre2", "Apellido2", LocalDate.now()));
		personas.add(new Person("Nombre3", "Apellido3", LocalDate.now()));
		
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Nombre"); // configuración del nombre de las columnas
		tableModel.addColumn("Apellido");
		tableModel.addColumn("Nacimiento");
		
		for (Person p : personas) {
			String[] fila = { p.getName(), p.getSurname(), p.getBirthDate().toString() };
			tableModel.addRow(fila); // añadimos los datos de la fila al modelo.
		}
		
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jTable);
		add(scrollPane);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new EjemploJTableBasico();
			}
		});
	}

	public static long getSerialversionid() {
		return serialversionID;
	}
}
