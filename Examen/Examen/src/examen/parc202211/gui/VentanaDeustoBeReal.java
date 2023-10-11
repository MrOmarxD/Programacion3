package examen.parc202211.gui;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

import examen.parc202211.data.Categoria;
import examen.parc202211.data.Foto;
import examen.parc202211.data.ServicioPersistenciaDeustoBeReal;
import examen.parc202211.data.Usuario;

/** Ventana principal de análisis de sistema DeustoBeReal
 */
@SuppressWarnings("serial")
public class VentanaDeustoBeReal extends JFrame {

	private final static String RUTA_DATOS_POR_DEFECTO = "res/examen/parc202211/carga";
	private final static SimpleDateFormat SDF_FECHA_FOTO = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );  // Formateador de fecha

	private DefaultListModel<Usuario> mUsuarios = new DefaultListModel<>();
	private JList<Usuario> lUsuarios = new JList<>( mUsuarios );
	private DefaultListModel<Categoria> mCategorias = new DefaultListModel<>();
	private JList<Categoria> lCategorias = new JList<>( mCategorias );
	private DefaultTableModel mFotos = new DefaultTableModel(
				new Object[] { "Código", "Categoría", "Fecha" }, 0
			);
	private JTable tFotos = new JTable( mFotos );
	private JLabel lMensaje = new JLabel( " " );
	private JLabelAjustado lFoto = new JLabelAjustado( null );
	private JButton bCargar = new JButton( "Carga de fotos" );

	private List<Foto> listaFotosActual; // Lista de fotos actual en la tabla de pantalla
	
	private ServicioPersistenciaDeustoBeReal servicioPersistencia;
		
	// T3a - posibles atributos necesarios

	/** Construye una nueva ventana de visualización de Deusto BeReal
	 * @param servicio	Servicio de persistencia a utilizar. Debe estar correctamente inicializado
	 */
	public VentanaDeustoBeReal( ServicioPersistenciaDeustoBeReal servicio ) {
		this.servicioPersistencia = servicio;
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 900, 600 );
		setTitle( "Ventana de administración de Deusto BeReal" );
		JPanel pNorte = new JPanel(); // Panel norte
			pNorte.add( lMensaje );
			getContentPane().add( pNorte, BorderLayout.NORTH );
		JSplitPane pOeste = new JSplitPane( JSplitPane.VERTICAL_SPLIT ); // Listas oeste
			JPanel pUsuarios = new JPanel( new BorderLayout() );
			pUsuarios.add( new JLabel( "Usuarios:" ), BorderLayout.NORTH );
			pUsuarios.add( new JScrollPane(lUsuarios), BorderLayout.CENTER );
			pOeste.setTopComponent( pUsuarios );
			JPanel pCategorias = new JPanel( new BorderLayout() );
			pCategorias.add( new JLabel( "Categorías:" ), BorderLayout.NORTH );
			pCategorias.add( new JScrollPane(lCategorias), BorderLayout.CENTER );
			pOeste.setBottomComponent( pCategorias );
			getContentPane().add( pOeste, BorderLayout.WEST ); 
		JPanel pPrincipal = new JPanel( new BorderLayout() ); // Panel central (tabla)
			pPrincipal.add( new JLabel( "Fotos:" ), BorderLayout.NORTH );
			pPrincipal.add( new JScrollPane( tFotos ), BorderLayout.CENTER );
			getContentPane().add( pPrincipal, BorderLayout.CENTER );
		getContentPane().add( lFoto, BorderLayout.EAST );  // Foto este
		JPanel pBotonera = new JPanel(); // Panel inferior (botonera)
			pBotonera.add( bCargar );
			getContentPane().add( pBotonera, BorderLayout.SOUTH );

		// Formatear componentes
		lFoto.setPreferredSize( new Dimension( 200, 200 ) );  // Para que la foto tenga 200 píxeles de ancho
			
		// Primera lista - todos los usuarios
		List<Usuario> l = servicioPersistencia.buscarUsuarios();
		for (Usuario usuario : l) {
			mUsuarios.addElement( usuario );
		}
		
		// Eventos
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				servicioPersistencia.close();
			}
		});
		lUsuarios.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && lUsuarios.getSelectedValue()!=null) {
					refrescaUsuario( lUsuarios.getSelectedValue() );
				}
			}
		});
		tFotos.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (tFotos.getSelectedRow() >= 0 && tFotos.getSelectedRow() < listaFotosActual.size() ) {
						Foto fotoSeleccionada = listaFotosActual.get( tFotos.getSelectedRow() );
						refrescaFoto( fotoSeleccionada );
					} else {
						refrescaFoto( null );
					}
				}
			}
		});
		bCargar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser( new File( RUTA_DATOS_POR_DEFECTO ) );
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
				fc.setDialogTitle( "Selecciona fichero de datos a cargar" );
				fc.setFileFilter( new FileNameExtensionFilter( "Datos de fotos", "txt" ) );
				int resp = fc.showOpenDialog( VentanaDeustoBeReal.this );
				File file = fc.getSelectedFile();
				if (resp==JFileChooser.APPROVE_OPTION && file!=null) {
					List<String> errores = cargarDatos( servicioPersistencia, file );
					if (errores.size()==0) {
						JOptionPane.showMessageDialog( VentanaDeustoBeReal.this, "Fotos cargadas sin errores", "Información de carga", JOptionPane.INFORMATION_MESSAGE );
					} else {
						String mensajeError = "";
						for (String error : errores) {
							mensajeError += error + "\n";
						}
						JOptionPane.showMessageDialog( VentanaDeustoBeReal.this, "Fotos cargadas con errores:\n" + mensajeError, "Información de carga", JOptionPane.ERROR_MESSAGE );
					}
				}
			}
		});
		
		// T3a: Renderer/eventos para mouseover
		
		// T3b: Eventos para lanzar botón
	}
	
	// Refresca los datos de la ventana (fotos, categorías) al seleccionar un usuario
	private void refrescaUsuario( Usuario usuario ) {
		// Busca fotos del usuario y recarga la JTable
		listaFotosActual = servicioPersistencia.buscarFotosDeUsuario( usuario.getNick() );
		while (mFotos.getRowCount() > 0) {
			mFotos.removeRow( 0 );
		}
		for (Foto foto : listaFotosActual) {
			mFotos.addRow( new Object[] { foto.getCodigo(), foto.getCategoria().getNombre(), SDF_FECHA_FOTO.format( foto.getFechaEnDate() ) } );
		}
		// Calcula categorías de las fotos del usuario
		mCategorias.removeAllElements();
		Set<Categoria> conjCategorias = servicioPersistencia.buscarCategoriasDeUsuario( usuario.getNick() );
		for (Categoria categoria : conjCategorias) {
			mCategorias.addElement( categoria );
		}
	}
	
	// Refresca la foto de la derecha
	private void refrescaFoto( Foto foto ) {
		if (foto==null) {
			lFoto.setImagen( null );
			lFoto.repaint();
		} else {
			ImageIcon imagen = new ImageIcon( foto.getRutaFoto() );
			lFoto.setImagen( imagen );
			lFoto.repaint();
		}
	}

	// Método de gestión de datos - no necesita ventana para funcionar (por eso es método static)
	/** Carga datos de fotos desde un fichero de texto y los añade al servicio de persistencia de datos
	 * El fichero debe tener líneas separadas, y el formato de cada línea debe ser el siguiente: nick\tcodigoCategoria\tfechaEnTipoLong\trutaFoto
	 * Errores posibles en la carga:
	 * - formato incorrecto (la línea no tiene los datos o el formato adecuado)
	 * - no existe usuario (el nick no existe)
	 * - no existe categoría (el código de categoría no es numérico o no existe)
	 * - fecha incorrecta (la fecha no es un long válido)
	 * - no existe foto (la ruta del fichero no existe)
	 * @param servicioPersistencia	Servicio de persistencia donde gestionar los datos
	 * @param file	Fichero de datos de fotos a añadir al servicio
	 * @return	Lista de errores encontrados en el proceso, vacía si no ha habido ninguno
	 */
	public static List<String> cargarDatos( ServicioPersistenciaDeustoBeReal servicioPersistencia, File file ) {
		List<String> errores = new ArrayList<>();
		// T1a - carga de datos de fotos de fichero de texto
		return errores;
	}
	
	private static class JLabelAjustado extends JLabel {
		private ImageIcon imagen; 
		private int tamX;
		private int tamY;
		/** Crea un jlabel que ajusta una imagen cualquiera con fondo blanco a su tamaño (a la que ajuste más de las dos escalas, horizontal o vertical)
		 * @param imagen	Imagen a visualizar en el label
		 */
		public JLabelAjustado( ImageIcon imagen ) {
			setImagen( imagen );
		}
		/** Modifica la imagen
		 * @param imagen	Nueva imagen a visualizar en el label
		 */
		public void setImagen( ImageIcon imagen ) {
			this.imagen = imagen;
			if (imagen==null) {
				tamX = 0;
				tamY = 0;
			} else {
				this.tamX = imagen.getIconWidth();
				this.tamY = imagen.getIconHeight();
			}
		}
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
			g2.setColor( Color.WHITE );
			g2.fillRect( 0, 0, getWidth(), getHeight() );
			if (imagen!=null && tamX>0 && tamY>0) {
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
				double escalaX = 1.0 * getWidth() / tamX;
				double escalaY = 1.0 * getHeight() / tamY;
				double escala = escalaX;
				int x = 0;
				int y = 0;
				if (escalaY < escala) {
					escala = escalaY;
					x = (int) ((getWidth() - (tamX * escala)) / 2);
				} else {
					y = (int) ((getHeight() - (tamY * escala)) / 2);
				}
		        g2.drawImage( imagen.getImage(), x, y, (int) (tamX*escala), (int) (tamY*escala), null );
			}
		}
	}
}
