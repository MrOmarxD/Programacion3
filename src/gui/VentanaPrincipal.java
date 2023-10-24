package gui;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.Producto;

public class VentanaPrincipal extends JFrame{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(VentanaPrincipal.class.getName());
	
	public VentanaPrincipal() {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("GAOmarket");
		
		//Prueba de como añadir un panel "carta" de cada producto
		/*
		Producto p = new Producto("Producto1", "descipcion 1........................",
				"Acer_Wallpaper_01_3840x2400.jpg", 15.7, 2, null);
		CartaProducto cartaP1 = new CartaProducto();
		cartaP1.Dibujar(p);
		
		CartaProducto cp1 = new CartaProducto();
		JPanel cartaProducto = cp1.Dibujar(p);
		this.add(cartaProducto);
		*/
		
		setVisible(true);
	}	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VentanaPrincipal();
			}
		});
	}
	
}
