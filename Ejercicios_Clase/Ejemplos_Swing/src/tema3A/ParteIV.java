package tema3A;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class ParteIV extends JFrame { 

    // vamos a crear nuestra propio modelo de datos
    // extendiendo la clase DefaultTableModel

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	class MyTableModel extends AbstractTableModel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// referencia a los datos almacenados en el modelo
        List<Persona> datos;

        // nombres de la cabecera
        private String[] cabecera = { "Nombre", "Apellido", "Nacimiento", "Pais" };

        // los datos del modelo se reciben del exterior
        // pero se guardan como una referencia
        public MyTableModel(List<Persona> datos) {
            this.datos = datos;
        }

        // devuelve los nombres de cada columna
        @Override
        public String getColumnName(int index) {
            return cabecera[index];
        }

        // obtiene de los datos el valor para la celda indicada
        @Override
        public Object getValueAt(int row, int column) {
            Persona p = datos.get(row);

            switch (column) {
                case 0: return p.getNombre();
                case 1: return p.getApellido();
                case 2: return p.getNacimiento().toString();
                case 3: return p.getPais();
                default: return null;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            // Obtenemos el objeto del modelo interno (lista) que
            // que se debe actualizar en función del valor de fila recibido
            Persona p = datos.get(row);

            // teniendo en cuenta el valor de la columna recibida
            // actualizamos la propiedad correspondiente de la Persona
            // teniendo en cuenta el tipo concreto de dato
            switch (column) {
                case 0:
                    p.setNombre((String) value);
                    break;
                case 1:
                    p.setApellido((String) value);
                    break;
                case 3:
                    p.setPais((String) value);
                    break;
                default: break;
            }
        }
        
        // indica el tipo (clase) de cada columna de datos
        @Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
				case 0:
				case 1: 
				case 3: return String.class;
				case 2: return LocalDate.class; 
				default: return null;
			}
		}


        // obtiene el número de filas de la tabla
        @Override
        public int getRowCount() {
            return datos.size();
        }

        // obtiene el número de columnas de la tabla
        @Override
        public int getColumnCount() {
            return cabecera.length;
        }

        // indica si la celda es editable o no
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 2; // toda la columna 2 es no editable
        }
    }

    class ComboPaisesEditor extends AbstractCellEditor implements TableCellEditor {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JComboBox<String> jComboBox;

        public ComboPaisesEditor() {
            // creamos el componente con los países a seleccionar
            jComboBox = new JComboBox<>(new String[] { "Alemania", "Inglaterra", "Desconocido", "Japón"});
            Font currentFont = jComboBox.getFont();
            jComboBox.setFont(new Font(currentFont.getName(), currentFont.getStyle(), 9));
            
            //Es obligatorio utilizar este evento
            //Cuando el usuario termina de seleccionar se actualiza
            jComboBox.addActionListener(e -> fireEditingStopped());
        }

        // este metodo devuelve el valor seleccionado en el combobox
        @Override
        public Object getCellEditorValue() {
            return jComboBox.getSelectedItem();
        }

        // este método devuelve el componente de edición para las celdas
        // en este caso se trata de un combobox.
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return jComboBox;
        } 
    }

    // datos de prueba
    private static List<Persona> personas = List.of(
        new Persona("John", "Smith", LocalDate.now(), "Desconocido"),
        new Persona("Albert", "Einstein", LocalDate.of(1879, 3, 14), "Alemania"),
        new Persona("Emily", "Noether", LocalDate.of(1882, 3, 23), "Alemania"),
        new Persona("Marie", "Curie", LocalDate.of(1867, 11, 7), "Francia")
    );

    public ParteIV() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel();
        add(jPanel, BorderLayout.SOUTH);
        
        // botón para imprimir los datos por consola
        JButton jButton = new JButton("Imprimir datos");
        jPanel.add(jButton);
        
        JLabel searchLabel = new JLabel("Resaltar: ");
        jPanel.add(searchLabel);
        
        JTextField searchField = new JTextField(12);
        jPanel.add(searchField);
               
        // listener del botón especificado con una expresión lambda
        jButton.addActionListener(e -> imprimirDatos());

        // creamos el modelo de la tabla
        MyTableModel modelo = new MyTableModel(personas);
        
        // creamos la tabla con su modelo de datos
        JTable jTable = new JTable(modelo);
        jTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// Comportamiendo por defecto del renderer de la tabla (JLabel)
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				// si el valor (string) empieza por el string de búsqueda se establece
				// todo el texto en negrita
//				Font currentFont = getFont();
//				String cellText = (String) value;
//				
//				if (!searchField.getText().isBlank() && cellText.startsWith(searchField.getText())) {
//					setFont(new Font(currentFont.getName(), Font.BOLD, currentFont.getSize()));
//				} else {
//					setFont(new Font(currentFont.getName(), Font.PLAIN, currentFont.getSize()));
//				}
				
				// en este caso, únicamente mostramos el texto que encaja en negrita, usando HTML
				String cellText = (String) value;
				if (!searchField.getText().isBlank() && cellText.startsWith(searchField.getText())) {
					setText(String.format("<html><b>%s</b>%s</html>", 
						searchField.getText(),
						cellText.substring(searchField.getText().length())
					));
				} else {
					setText(cellText);
				}
			
				return this;
			}
			        	
        });
        
        /// evento para detectar el cambio del string de búsqueda
        searchField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				jTable.repaint();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				jTable.repaint();			
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				//
			}
		});
        
        // obtenemos la referencia a la última columna de la tabla (país)
        // y establecemos el editor para esa columna únicamente
        TableColumn countryColumn = jTable.getColumnModel().getColumn(3);
        countryColumn.setCellEditor(new ComboPaisesEditor());
          
        // añadimos la tabla a un scroll pane
        // permite ver el header de la tabla
        JScrollPane scrollPane = new JScrollPane(jTable);
        
        add(scrollPane);

        pack();
        setVisible(true);
    }

    public static void imprimirDatos() {
        for (Persona p : personas) {
            System.out.format(
                "%s %s - %s - %s%n", 
                p.getNombre(), p.getApellido(), p.getNacimiento(), p.getPais()
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParteIV::new);
    }
}