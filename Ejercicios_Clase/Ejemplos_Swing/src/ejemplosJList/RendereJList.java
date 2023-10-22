package ejemplosJList;

import java.awt.Component;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import tema3A.Person;

public class RendereJList extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// este modelo de lista proporciona un método
    // addAll para añadir una lista de personas directamente
    // DefaultListModel no contiene este método en Java 8
	class MyListModel extends DefaultListModel<Person>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyListModel(Collection<Person> persons) {
            add(persons);
        }

        public void add(Collection<Person> persons) {
            for (Person p : persons) {
                addElement(p);
            }
        }
	}
	
	// Esta clase define el renderer para los elementos visuales de la lista
    // El renderer debe implementar la interfaz ListCellRender.
    // En este caso se extiende un JLabel como componente a visualizar
    class MyCellRenderer extends JLabel implements ListCellRenderer<Person> {
    	    	
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        // este es el método que se llama para obtener el componente
        // que se debe pintar en cada elemento de la lista
        @Override
        public Component getListCellRendererComponent(JList<? extends Person> list, Person value, int index,
                boolean isSelected, boolean hasFocus) {
            // se establece el valor del texto mostrado en el JLabel de la celda
            setText(value.toString());

            // Cuando se seleccione una persona se imprime su fecha de nacimiento por consola
            if (isSelected) {
                System.out.println(value.getBirthDate().toString());
            }

            // se devuelve el componente visual. siempre se devuelve this
            // porque se evita crear uno nuevo para cada elemento de la lista
            return this;
        }
    }
}
