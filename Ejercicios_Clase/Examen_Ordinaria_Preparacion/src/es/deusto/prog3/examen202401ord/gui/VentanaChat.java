package es.deusto.prog3.examen202401ord.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import es.deusto.prog3.examen202401ord.logica.GestorUsuarios;

@SuppressWarnings("serial")
public class VentanaChat extends JFrame {

	private GestorUsuarios gestor;

	private JPanel panelChat;
	private JScrollPane spChat;
	private JTextArea taChat;

	/** Crea una ventana de login asociada a un gestor de usuarios
	 * @param gestorUsuarios	Gestor de usuarios que trabajará con la ventana
	 */
	public VentanaChat( GestorUsuarios gestorUsuarios ) {
		this.gestor = gestorUsuarios;
		gestor.setVentanaChat( this );

		setTitle( "Chat no GPT" );
		setSize( 1000, 600 );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		
		JPanel panelConversacion = new JPanel();
		panelConversacion.setLayout( new BorderLayout() );
		JPanel panelInferior = new JPanel();
		panelChat = new JPanel();
		panelChat.setSize( 500, 1000 );
		panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
		spChat = new JScrollPane( panelChat );
		nuevoEspacioInicioChat();
		panelConversacion.add( spChat, BorderLayout.CENTER );
		
		JButton botonChat = new JButton( "==>" );
		taChat = new JTextArea( 4, 50 ); 
		panelInferior.add( new JScrollPane( taChat ) );
		panelInferior.add( botonChat );
		spChat = new JScrollPane();
		panelConversacion.add( panelInferior, BorderLayout.SOUTH );
		
		add( panelConversacion, BorderLayout.CENTER );
		
		// Eventos
		botonChat.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gestor.nuevoChat();
			}
		});
		
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gestor.acabaChat();
			}
		});
		
	}
	
	public JScrollPane getSpChat() {
		return spChat;
	}
	
	public JTextArea getTaChat() {
		return taChat;
	}
	
	public JPanel getPanelChat() {
		return panelChat;
	}

	/** Genera espacio al inicio del panel de chat para que la conversación empiece a mostrarse abajo
	 */
	public void nuevoEspacioInicioChat() {
		for (int i=0; i<5; i++) { // Paneles de relleno
			JTextArea taRelleno = new JTextArea( " ", 4, 50 );
			taRelleno.setOpaque( false );
			taRelleno.setFocusable( false );
			panelChat.add( taRelleno );
		}
	}
	
	/** Añade una nueva frase de usuario al chat en la parte inferior
	 * @param texto	Texto del usuario
	 */
	public void nuevaFraseUsuario( String texto ) {
		JTextArea taChatUsuario = new JTextArea( texto, 2, 50 );
		taChatUsuario.setLineWrap( true );
		taChatUsuario.setEditable( false );
		taChatUsuario.setBackground( Color.WHITE );
		taChatUsuario.setFocusable( false );
		panelChat.add( taChatUsuario );
	}
	
	/** Añade una nueva respuesta del chat en la parte inferior
	 * @param respuesta	Texto generado de respuesta
	 */
	public void nuevaRespuestaChat( String respuesta ) {
		JTextArea taChatIA = new JTextArea( respuesta, 2, 50 );
		taChatIA.setLineWrap( true );
		taChatIA.setEditable( false );
		taChatIA.setBackground( Color.LIGHT_GRAY );
		taChatIA.setFocusable( false );
		panelChat.add( taChatIA );
	}
	
}
