package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.GestorMarket;
import domain.ProductoCarrito;

public class VentanaCarroCompra extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected GestorMarket gestor;
	protected List<ProductoCarrito> productoCarrito;
	protected JTable tabla;
	protected JScrollPane tablaScroll;
	protected ModeloCarroCompra modeloCarrito;
	protected RendererCarroCompra rendererCompra;
	protected JButton btnEliminar;
	protected JButton btnComprar;
	protected JButton btnVaciar;
	protected JPanel panelCentral;
	protected JPanel panelAbajo;
	protected JLabel lblPrecioTotal;
	protected String usuario;
    
	public VentanaCarroCompra(GestorMarket gestor) {
		this.gestor = gestor;
		Container cp = this.getContentPane();

		
		this.setLayout(new BorderLayout());
		if(gestor.getUsuario() != null)
			usuario = gestor.getUsuario().getNomUsuario();
		else
			usuario = gestor.getEmpleado().getNomUsuario();
		
		productoCarrito = gestor.getGestorXML().dameProductos(usuario);
		
		modeloCarrito = new ModeloCarroCompra(productoCarrito);
		tabla = new JTable(modeloCarrito);
		tabla.setDefaultRenderer(Object.class, new RendererCarroCompra());
		tabla.setRowHeight(35);
		tablaScroll = new JScrollPane(tabla);
		
		btnEliminar = new JButton("Eliminar del carrito");
		btnComprar = new JButton("Comprar");
		btnVaciar = new JButton("Vaciar carrito");
		
		panelCentral = new JPanel(new BorderLayout());
		panelAbajo = new JPanel();
		
		btnVaciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres vaciar la cesta?", "Vaciar cesta", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION)
					gestor.getGestorXML().vaciarCarrito(usuario);
			}
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabla.getSelectedRow() != -1){
					int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar el producto seleccionado de la cesta?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
						gestor.getGestorXML().eliminarProducto(usuario, productoCarrito.get(tabla.getSelectedRow()).getProducto().getNombre());
					}
				}
			}
		});
		
		if(productoCarrito.size() == 0) lblPrecioTotal = new JLabel("Total: 0€");
		else lblPrecioTotal = new JLabel("Total: " + String.format("%.2f €", sumarPrecioTotalCarroRecursivo(productoCarrito, productoCarrito.size() - 1)));
		panelCentral.add(tablaScroll, "Center");
		panelCentral.add(lblPrecioTotal, "South");
		
		btnComprar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(productoCarrito.size() > 0) {
					int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea finalizar la compra?", "Confirmación de compra", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
						for(ProductoCarrito pc: productoCarrito) {
							gestor.getGestorBD().realizarCompra(pc.getProducto().getNombre(), pc.getProducto().getCantidad() - pc.getCantidad());
						}
						gestor.getGestorXML().vaciarCarrito(usuario);
						gestor.getGestorBD().crearCompra(usuario, sumarPrecioTotalCarroRecursivo(productoCarrito, productoCarrito.size() - 1));
					}
				}
			}
		});
		
		panelAbajo.add(btnVaciar);
		panelAbajo.add(btnEliminar);
		panelAbajo.add(btnComprar);
		
		cp.add(panelCentral);
		cp.add(panelAbajo, BorderLayout.SOUTH);
		
		ImageIcon iconoPrincipal = new ImageIcon("resources/iconos/iconoGAO.png");
		iconoPrincipal = new ImageIcon(iconoPrincipal.getImage().getScaledInstance(207, 207, Image.SCALE_SMOOTH));
		
		this.setIconImage(iconoPrincipal.getImage());
		
		this.setTitle("Carrito de la Compra");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(680, 80, 500, 400);
		this.setVisible(false);
		
		this.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {

			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				productoCarrito = gestor.getGestorXML().dameProductos(usuario);
				modeloCarrito.setProductoCarrito(productoCarrito);
				modeloCarrito.fireTableDataChanged();
			}
		});
	}
	
	public static Double sumarPrecioTotalCarroRecursivo(List<ProductoCarrito> productos, int cont) {
		if(cont < 0) return 0.0;
		return sumarPrecioTotalCarroRecursivo(productos, cont - 1) + sumarPrecioProductoRecursivo(productos.get(cont).getProducto().getPrecio(), productos.get(cont).getCantidad());
	}
	
	public static Double sumarPrecioProductoRecursivo(Double precio, int cantidad) {
		if(cantidad < 2) return precio;
		return sumarPrecioProductoRecursivo(precio, --cantidad) + precio;
	}
}
