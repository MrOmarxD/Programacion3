package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import bd.GestorBD;
import domain.GestorMarket;
import gui.*;

public class GAOmarket {
	protected static GestorMarket gestor;
	protected static GestorBD gestorBD;
	private static Logger logger = Logger.getLogger(GAOmarket.class.getName());

	public static void main(String[] args) {
		VentanaCarga vCarga = new VentanaCarga();
		vCarga.setVisible(true);
		gestorBD = new GestorBD();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gestor = new GestorMarket();
				VentanaPrincipal ventana = new VentanaPrincipal(gestor);
				Thread hilo = new Thread(new Runnable() {
					@Override
					public void run() {
						
						 
						try {
							Thread.sleep(5000);
							logger.log(Level.INFO, "Hilo finalizado");
						} catch (InterruptedException e) {
							logger.log(Level.INFO, "Hilo finalizado");
						}
						 finally {
							 vCarga.dispose();
						 }
						
					}
				});
				hilo.start();
				CadenaProductos.cargarMapaTipoProducto("resources/datos/productos.csv");
				gestorBD.cargarMapaTipoProducto("resources/datos/productos.csv");

				ventana.setVisible(true);
				hilo.interrupt();
			}
		});
	}
	
	static class VentanaCarga extends JDialog {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected ImageIcon iconoPrincipal;
		protected JLabel label;
		protected JPanel panel;
		public VentanaCarga() {
			iconoPrincipal = new ImageIcon("resources/iconos/iconoGAO.png");
			iconoPrincipal = new ImageIcon(iconoPrincipal.getImage().getScaledInstance(207, 207, Image.SCALE_SMOOTH));
			
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setTitle("GAO Market");
			setIconImage(iconoPrincipal.getImage());
			
			label = new JLabel("Cargando datos, espere...");
			label.setForeground(Color.WHITE);
			panel = new JPanel();
			panel.setBorder(new EmptyBorder(20, 0, 20, 0));
			panel.add(label, BorderLayout.CENTER);
			panel.setBackground(Color.GREEN);

			getContentPane().add(panel);
			setSize(200, 100);
			setLocationRelativeTo(null);
		}
	}
}
