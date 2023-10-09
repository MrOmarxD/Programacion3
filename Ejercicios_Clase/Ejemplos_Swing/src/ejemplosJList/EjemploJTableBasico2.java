package ejemplosJList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import tema3A.Person;

public class EjemploJTableBasico2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EjemploJTableBasico2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Ejemplo JTable Básico");
		
		////////////////////
		ArrayList<Person> personas = new ArrayList<>();
		personas.add(new Person("Nombre1", "Apellido1", LocalDate.of(1920, 03, 5)));
		personas.add(new Person("Nombre2", "Apellido2", LocalDate.of(1921, 05, 3)));
		personas.add(new Person("Nombre3", "Apellido3", LocalDate.of(1922, 06, 2)));
		personas.add(new Person("Nombre4", "Apellido4", LocalDate.of(1923, 02, 1)));
		//////////////////
		
//		DefaultTableModel modelo = new DefaultTableModel();
//		modelo.addColumn("Nombre");
//		modelo.addColumn("Apellido");
//		modelo.addColumn("Nacimiento");
//		
//		for (Person p : personas) {
//			Object[] fila = new Object[3];
//			fila[0] = p.getName();
//			fila[1] = p.getSurname();
//			fila[2] = p.getBirthDate(); 
//			modelo.addRow(fila);
//		}
		
		class MyTableModel extends AbstractTableModel {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			private List<Person> personas;
			
			String[] cabeceras = { "Nombre", "Apellido", "Nacimiento" };
				
			public MyTableModel(List<Person> personas) {
				this.personas = personas;
			}

			@Override
			public Object getValueAt(int row, int column) {
				Person p = personas.get(row);
				switch (column) {
					case 0: return p.getName();
					case 1: return p.getSurname();
					case 2: return p.getBirthDate();
				}
				return null;
			}

			@Override
			public int getRowCount() {
				return personas.size();
			}

			@Override
			public int getColumnCount() {
				return cabeceras.length;
			}

			@Override
			public String getColumnName(int column) {
				return cabeceras[column];
			}
			
			// el JTable utiliza este método para determinar si una
	        // celda concreta de la tabla es editable
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            // para este ejemplo, es editables la primera columna
	            return column == 0;
	        }
	        
	        // este método es utilizado por el JTable para actualizar
	        // el modelo de datos asociado.
	        // Recibe un Object por lo que, sabiendo el dato interno
	        // es necesario hacer un cast
	        @Override
	        public void setValueAt(Object value, int row, int column) {
	            // Obtenemos el objeto del modelo interno (lista) que
	            // que se debe actualizar en función del valor de fila recibido
	            Person p = personas.get(row);

	            // teniendo en cuenta el valor de la columna recibida
	            // actualizamos la propiedad correspondiente de la Persona
	            // teniendo en cuenta el tipo concreto de dato
	            switch (column) {
	                case 0:
	                    p.setName((String) value);
	                    break;
	                case 1:
	                    p.setSurname((String) value);
	                    break;
	                case 2:
	                    p.setBirthdate((LocalDate) value);
	                    break;
	            }
	            
	            // este método se utiliza para notificar que el modelo de datos
	            // se ha actualizado y se debe repintar la celda visual
	            fireTableCellUpdated(row, column);
	        }
		}

		JTextField campoBusqueda = new JTextField(20);
		
		class MyRenderer extends JLabel implements TableCellRenderer{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				setText(value.toString());
				if(!campoBusqueda.getText().isBlank() && getText().contains(campoBusqueda.getText())) {
					setBackground(Color.RED);
					setOpaque(true);
				}
				else {
					setOpaque(false);
				}
				return this;
			}
			
		}
		
		MyTableModel model = new MyTableModel(personas);
		JTable jTable = new JTable(model);
		jTable.setDefaultRenderer(Object.class, new MyRenderer());
		JScrollPane scrollPane = new JScrollPane(jTable);
		add(scrollPane);
		
		//// boton que imprime los datos
		JButton boton = new JButton("Imprimir Datos");
		JPanel panelDeAbajo = new JPanel();
		panelDeAbajo.add(boton);
		
		add(panelDeAbajo, BorderLayout.SOUTH);
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Person p : personas) {
					System.out.println(p);
				}
			}
		});
		
		//TextFild, campo de busqueda
		campoBusqueda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Person p: personas) {
					
				}
			}
		});
		panelDeAbajo.add(campoBusqueda);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new EjemploJTableBasico2();
			}
			
		});
	}

}
