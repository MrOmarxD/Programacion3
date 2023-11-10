package tema3A;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.DefaultListModel;
import javax.swing.ListCellRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import java.time.LocalDate;

public class RendererJList extends JFrame {

    class MyCellRenderer extends JLabel implements ListCellRenderer<Persona> {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        // constructor del renderer que carga los iconos a utilizar
        public MyCellRenderer() {
            setOpaque(true);
        }

        // este es el método que se llama para obtener el componente
        // que se debe pintar en cada elemento de la lista
        @Override
        public Component getListCellRendererComponent(JList<? extends Persona> list, 
                Persona value, int index, boolean isSelected, boolean hasFocus) {

            // establecemos el texto que se visualiza en el label
            // esto es necesario porque estamos reimplementando el renderer
            // vamos a pintar solamente el nombre y el apellido
            setText(value.getNombre() + " " + value.getApellido());

            // establecemos el color de fondo de 'this', que es un JLabel
            // este método se llama por cada elemento del JList a pintar

            if (value.getNacimiento().getYear() >= 1900) {
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.YELLOW);
            }

            // se devuelve el componente visual. siempre se devuelve this
            // porque se evita crear uno nuevo para cada elemento de la lista
            return this;
        }

    }
    
    public RendererJList() {
        // datos de prueba
        List<Persona> personas = List.of(
            new Persona("John", "Smith", LocalDate.now()),
            new Persona("Albert", "Einstein", LocalDate.of(1879, 3, 14)),
            new Persona("Emily", "Noether", LocalDate.of(1882, 3, 23)),
            new Persona("Marie", "Curie", LocalDate.of(1867, 11, 7))
        );


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // creamos el modelo de datos y lo asociamos a la lista
        DefaultListModel<Persona> modelo = new DefaultListModel<>();
        JList<Persona> jList = new JList<>(modelo);
        jList.setCellRenderer(new MyCellRenderer());

        jList.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    System.out.println(jList.getSelectedValue().getNacimiento());
                }
            }
            
        });

        // ahora añadimos los datos al modelo de datos
        for (Persona p : personas) {
            modelo.addElement(p);
        }

        JPanel panel = new JPanel();
        panel.add(jList);
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RendererJList::new);
    }
}
