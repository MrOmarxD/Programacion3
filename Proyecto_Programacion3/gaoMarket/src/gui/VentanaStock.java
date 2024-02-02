package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import domain.GestorMarket;
import domain.Producto;
import domain.Producto.Estado;
import domain.TipoAlimento;
import domain.TipoHigieneYBelleza;
import domain.TipoLimpieza;
import domain.TipoProducto;

public class VentanaStock extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GestorMarket gestor;
	protected JPanel pDerecha;
	protected JPanel pArriba;
	protected JPanel pCentro; 
	protected JPanel pIzquierda;
	protected DefaultTreeModel modeloArbol;
	protected JTree arbol;
	protected JScrollPane scrollArbol;
	protected ModeloStock modeloTabla;
	protected JTable tabla;
	protected JScrollPane scrollTabla;
	protected JSlider sUnidades;
	protected RendererStock renderer;
	protected ImageIcon iconoStock;
	protected List<Producto> listaProductos;
	
	protected String tipoProductoSeleccionado;
	protected String categoriaSeleccionada;
    
    public VentanaStock(GestorMarket gestor) {
		super();
		this.gestor = gestor;
		tipoProductoSeleccionado = "";
		categoriaSeleccionada = "";
		
		iconoStock = new ImageIcon("resources/iconos/iconoGAO.png");
		iconoStock = new ImageIcon(iconoStock.getImage().getScaledInstance(207, 207, Image.SCALE_SMOOTH));
		
		
		pIzquierda = new JPanel();
		pArriba = new JPanel();
		pCentro = new JPanel();
		
		
		listaProductos = new ArrayList<Producto>();
		getContentPane().add(pArriba, BorderLayout.NORTH);
		
		DefaultMutableTreeNode nraiz = new DefaultMutableTreeNode("TIPOS DE PRODUCTO");
		modeloArbol = new DefaultTreeModel(nraiz);
		arbol = new JTree(modeloArbol);
		scrollArbol = new JScrollPane(arbol);
		cargarArbol();
		getContentPane().add(scrollArbol, BorderLayout.WEST);

		renderer = new RendererStock();
		modeloTabla = new ModeloStock(null);
		tabla = new JTable(modeloTabla);
		tabla.setDefaultRenderer(Object.class, renderer);
 
		scrollTabla = new JScrollPane(tabla);
		getContentPane().add(scrollTabla, BorderLayout.CENTER);

        arbol.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath tp = e.getPath();
                tipoProductoSeleccionado = tp.getLastPathComponent().toString();
                if(tipoProductoSeleccionado.equals("TIPOS DE PRODUCTO")) return;
                categoriaSeleccionada = tp.getLastPathComponent().toString();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
                Object selectedObject = selectedNode.getUserObject();
                
                if (selectedObject instanceof TipoProducto) {
                    tipoProductoSeleccionado = selectedObject.toString();

                    if (CadenaProductos.getMapaTipoProducto().containsKey(TipoProducto.valueOf(tipoProductoSeleccionado))) {
                    	listaProductos = CadenaProductos.obtenerListaTipoProductos(TipoProducto.valueOf(tipoProductoSeleccionado));
                        Collections.sort(listaProductos);
                        tabla.setModel(new ModeloStock(listaProductos));
                    }
                } else if (selectedObject instanceof TipoAlimento) {
                	categoriaSeleccionada = ((TipoAlimento) selectedObject).toString();

                    if (CadenaProductos.getMapaCategoriaProducto().containsKey(TipoAlimento.valueOf(categoriaSeleccionada))) {
                    	listaProductos = CadenaProductos.obtenerListaCategoriaProductos(TipoAlimento.valueOf(categoriaSeleccionada));
                        Collections.sort(listaProductos);
                        tabla.setModel(new ModeloStock(listaProductos));
                    }
                } else if (selectedObject instanceof TipoHigieneYBelleza) {
                	categoriaSeleccionada = ((TipoHigieneYBelleza) selectedObject).toString();

                    if (CadenaProductos.getMapaCategoriaProducto().containsKey(TipoHigieneYBelleza.valueOf(categoriaSeleccionada))) {
                    	listaProductos = CadenaProductos.obtenerListaCategoriaProductos(TipoHigieneYBelleza.valueOf(categoriaSeleccionada));
                        Collections.sort(listaProductos);
                        tabla.setModel(new ModeloStock(listaProductos));
                    }
                } else {
                	categoriaSeleccionada = ((TipoLimpieza) selectedObject).toString();

                    if (CadenaProductos.getMapaCategoriaProducto().containsKey(TipoLimpieza.valueOf(categoriaSeleccionada))) {
                    	listaProductos = CadenaProductos.obtenerListaCategoriaProductos(TipoLimpieza.valueOf(categoriaSeleccionada));
                        Collections.sort(listaProductos);
                        tabla.setModel(new ModeloStock(listaProductos));
                    }
                }
            }
            
           
        });
        JPanel panelAbajo = new JPanel(new GridLayout(1, 2));
        JButton botonEliminar = new JButton("Eliminar Producto");
        botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tabla.getSelectedRow();
				ModeloStock modelo = (ModeloStock) tabla.getModel();
				Producto productoEliminado = modelo.getProductoEnFila(filaSeleccionada);
				
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres vaciar la cesta?", "Vaciar cesta", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					modelo.eliminarProducto(productoEliminado);
					gestor.getGestorBD().borrarProducto(productoEliminado.getId());
					modelo.fireTableDataChanged();
				}

			}
        	
        });
        
        JButton botonAnyadir = new JButton("Añadir Producto");
        botonAnyadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 manejarAccionAnadirProducto();
				
			}
        	
        });
        panelAbajo.add(botonAnyadir);
        panelAbajo.add(botonEliminar);
        
        getContentPane().add(panelAbajo, BorderLayout.SOUTH);
        
        setBounds(25, 100, 1490, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(false);
		setIconImage(iconoStock.getImage());
		
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
        		CadenaProductos.guardarProductos("resources/datos/productos.csv", gestor.getGestorBD().listarProductos());
        		
            }

			@Override
			public void windowOpened(WindowEvent e) {
				
			}

        });
        
	}
    
    private void manejarAccionAnadirProducto() {
        // Obtener información del usuario para crear un nuevo producto
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String imagen = (nombre + ".png").toLowerCase();
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto:"));
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la cantidad de stock que hay del producto: "));
        TipoProducto tipoProducto = obtenerTipoProductoDesdeUsuario();
        Enum<?> categoria = obtenerCategoria(tipoProducto);
        int descuento = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el descuento que tendra el producto: "));

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setImagen(imagen);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setCantidad(cantidad);
        nuevoProducto.setTipoProducto(tipoProducto);
        nuevoProducto.setCategoria(categoria);
        if (cantidad > 10) nuevoProducto.setEstado(Estado.NORMAL);
        else if (cantidad == 0) nuevoProducto.setEstado(Estado.AGOTADO);
        else nuevoProducto.setEstado(Estado.POCAS_UNIDADES);
        nuevoProducto.setDescuento(descuento);

       
        if (gestor.getGestorBD().anyadirProducto(nuevoProducto)) {
        	 listaProductos.add(gestor.getGestorBD().buscarProducto(nuevoProducto.getNombre()));
             tabla.setModel(new ModeloStock(listaProductos));
        }
    }

    private TipoProducto obtenerTipoProductoDesdeUsuario() {
    	TipoProducto[] opciones = TipoProducto.values();

        JComboBox<TipoProducto> comboTipoProducto = new JComboBox<>(opciones);
        int resultado = JOptionPane.showConfirmDialog(
                null,
                comboTipoProducto,
                "Seleccione el tipo de producto:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            return (TipoProducto) comboTipoProducto.getSelectedItem();
        } else {

            return null;
        }
    }

    private Enum<?> obtenerCategoria(TipoProducto tipoProducto) {
    	Enum<?>[] opciones;
    	
    	if (tipoProducto == TipoProducto.ALIMENTO) {
    		opciones = TipoAlimento.values();
    		
    	} else if (tipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
    		opciones = TipoHigieneYBelleza.values();
    	} else {
    		opciones = TipoLimpieza.values();
    	}
    	
    	JComboBox<Enum<?>> comboCategoria = new JComboBox<>(opciones);
        int resultado = JOptionPane.showConfirmDialog(
                null,
                comboCategoria,
                "Seleccione la categoría del producto:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            return (Enum<?>) comboCategoria.getSelectedItem();
        } else {
            return null;
        }

        
    }
    
	private void cargarArbol() {
		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modeloArbol.getRoot();

		for (TipoProducto tipoProducto : TipoProducto.values()) {
			DefaultMutableTreeNode tipoProductoNode = new DefaultMutableTreeNode(tipoProducto);
		    
			if (tipoProducto.equals(TipoProducto.ALIMENTO)) {
				for (TipoAlimento tipoAlimento : TipoAlimento.values()) {
					DefaultMutableTreeNode tipoAlimentoNode = new DefaultMutableTreeNode(tipoAlimento);
					tipoProductoNode.add(tipoAlimentoNode);
				}
			} else if (tipoProducto.equals(TipoProducto.HIGIENE_Y_BELLEZA)) {
				for (TipoHigieneYBelleza tipoHigiene : TipoHigieneYBelleza.values()) {
					DefaultMutableTreeNode tipoHigieneNode = new DefaultMutableTreeNode(tipoHigiene);
					tipoProductoNode.add(tipoHigieneNode);
				}
			} else if (tipoProducto.equals(TipoProducto.LIMPIEZA)) {
				for (TipoLimpieza tipoLimpieza : TipoLimpieza.values()) {
					DefaultMutableTreeNode tipoLimpiezaNode = new DefaultMutableTreeNode(tipoLimpieza);
					tipoProductoNode.add(tipoLimpiezaNode);
				}
			}
			raiz.add(tipoProductoNode);
		}
		modeloArbol.reload();
    }
}
