package ejemplosJList;

import javax.swing.JFrame;

public class EjemploJList extends JFrame{
	/**
	 * 
	 */
	private static final long serialversionID = 1L;
	
	public EjemploJList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Ejemplo JList");
		
		/////////////////
		List<Person> personas = new ArrayList<Person>();
		personas.add(new Person("Nombre1", "Apellido1", LocalDate.of(1900, 11, 10)));
		personas.add(new Person("Nombre2", "Apellido2", LocalDate.now()));
		personas.add(new Person("Nombre3", "Apellido3", LocalDate.now()));
		
		DefaultListModel listModel = new DefaultListModel<>();
		listModel.addAll(personas);
		
		JList<Person> jList = new JList<Person>(listModel);
		
		jList.setSelectionMode(ListSelectionMode.SINGLE_SELECTION);
		jList.setCellRenderer(new MyCellRenderer());
		
		
		////////////////
		
		//String[] nombres = {"Nombre1 Apellido1", "Nombre2 Apellido2", "Nombre3 Apellido3"};
		
		//JList<String> jList = new JList<String>(nombres);
		JScrollPane scrollPane = new JScrollPane(JList);
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
