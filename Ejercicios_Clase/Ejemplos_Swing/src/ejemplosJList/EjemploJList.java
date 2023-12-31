package ejemplosJList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tema3A.Person;

public class EjemploJList extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EjemploJList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Ejemplo JList");
		
		/////////////////
		List<Person> personas = new ArrayList<Person>();
		personas.add(new Person("Nombre1", "Apellido1", LocalDate.of(1900, 11, 10)));
		personas.add(new Person("Nombre2", "Apellido2", LocalDate.now()));
		personas.add(new Person("Nombre3", "Apellido3", LocalDate.now()));
		
		DefaultListModel<Person> listModel = new DefaultListModel<>();
		listModel.addAll(personas);
		
		JList<Person> jList = new JList<Person>(listModel);
		
		// la lista solamente admite selección sencilla
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Evento, al selecionar una opcion, imprime su fecha de nacimiento
		jList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					System.out.println(jList.getSelectedValue().getBirthDate());
				}
			}
		});
		
		// se establece el renderer para los elementos de la lista
		//jList.setCellRenderer(new MyCellRenderer());
		
		
		////////////////
		
		//String[] nombres = {"Nombre1 Apellido1", "Nombre2 Apellido2", "Nombre3 Apellido3"};
		
		//JList<String> jList = new JList<String>(nombres);
		JScrollPane scrollPane = new JScrollPane(jList);
		add(scrollPane);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new EjemploJList();
			}
		});
	}
}
