package tema5A;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EjemploRutas2 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EjemploRutas2() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(320,240);
		setTitle("Mi ventana");
		setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(EjemploRutas2::new);
	}
}
