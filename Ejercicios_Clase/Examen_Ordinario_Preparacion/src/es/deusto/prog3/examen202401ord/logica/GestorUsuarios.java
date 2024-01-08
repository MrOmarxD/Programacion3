package es.deusto.prog3.examen202401ord.logica;

import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import es.deusto.prog3.examen202401ord.datos.*;
import es.deusto.prog3.examen202401ord.gui.*;
import es.deusto.prog3.examen202401ord.main.Main;

/** Gestor de usuarios
 * @author prog3 @ ingenieria.deusto.es
 */
public class GestorUsuarios {

	private Usuario usuarioLogueado = null;
	private GestorPersistencia bd = null;
	private VentanaLogin ventLogin = null;
	private VentanaChat ventChat = null;
	private ChatNoGPT chatNoGPT = new ChatNoGPT();

	/** Crea un gestor de usuarios
	 * @param bd	Base de datos con la que trabajar para la persistencia de usuarios
	 */
	public GestorUsuarios( GestorPersistencia bd ) {
		this.bd = bd;
	}
	
	/** Asocia el gestor de usuarios a una ventana de login
	 * @param ventLogin	Ventana de login en la que realizar trabajo de registro e identificación de usuarioss
	 */
	public void setVentanaLogin( VentanaLogin ventLogin ) {
		this.ventLogin = ventLogin;
	}
	
	/** Asocia el gestor de usuarios a una ventana de chat
	 * @param ventLogin	Ventana de chat en la que realizar el proceso de chat
	 */
	public void setVentanaChat( VentanaChat ventChat ) {
		this.ventChat = ventChat;
	}
	
	/** Registra e identifica un usuario nuevo
	 * @param nick	Nick de ese usuario
	 * @param password	Password de ese usuario
	 * @return	true si el nick no existía y la password es correcta (no null ni string vacío), false si el usuario (nick) ya existía
	 */
	public boolean registrarUsuario( String nick, String password ) {
		return registrarUsuario( new Usuario( nick, password, "", "", 0, new ArrayList<>() ) );
	}
	
	/** Registra e identifica un usuario
	 * @param usuario	Usuario a registrar
	 * @return	true si el nick no existía y la password es correcta (no null ni string vacío), false si el usuario (nick) ya existía
	 */
	public boolean registrarUsuario( Usuario usuario ) {
		if (usuario.getNick() == null || usuario.getPassword() == null || 
			usuario.getNick().isEmpty() || usuario.getPassword().isEmpty() || usuario.getNick().equals(Main.NOMBRE_CHAT)) {
			return false;
		}
		ArrayList<Usuario> lU = bd.leerUsuarios( null, "where nick='" + usuario.getNick() +"'" );
		if (lU.isEmpty()) {
			return loginUsuario( usuario.getNick(), usuario.getPassword() );
		} else {
			return false;
		}
	}

	/** Identifica y loguea un usuario
	 * @param nick	Nick de usuario a loguear
	 * @param password	Password de usuario a loguear
	 * @return	true si el nick y password son correctas, false si el usuario no existe o su password es incorrecta
	 */
	public boolean loginUsuario( String nick, String password ) {
		if (nick == null || password == null || nick.equals(Main.NOMBRE_CHAT)) {
			return false;
		}
		ArrayList<Usuario> lU = bd.leerUsuarios( null, "nick='" + nick +"'" );
		if (lU.isEmpty()) {
			return false;
		} else {
			boolean login = password.equals( lU.get(0).getPassword() );
			if (login) {
				usuarioLogueado = lU.get(0);
			}
			return login;
		}
	}
	
	/** Lanza proceso tras login
	 */
	public void procesoPostLogin() {
		if (ventLogin!=null) {
	        if (getUsuarioLogueado()==null) {
	        	JOptionPane.showMessageDialog( ventLogin, "Proceso incorrecto", "Error en login/registro", JOptionPane.ERROR_MESSAGE );
	        } else {
	        	if (ventChat!=null) {
	        		ventLogin.setVisible( false );
	        		ventChat.setVisible( true );
	        	}
	        }
		}
	}
	
	/** Hace un logout del usuario identificado (si lo hay)
	 */
	public void logout() {
		usuarioLogueado = null;
	}
	
	/** Lanza final de ventana de chat
	 */
	public void acabaChat() {
		if (ventLogin!=null && ventChat!=null) {
			ventChat.setVisible( false );
			ventLogin.setVisible( true );
		}
	}
	
	/** Lanza cierre de ventanas
	 */
	public void cierraVentanas() {
		if (ventChat!=null) ventChat.dispose();
	}
	

	/** Devuelve el usuario que esté logueado
	 * @return	usuario con login en curso, null si no hay ningún usuario logueado
	 */
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	/** Gestiona una nueva frase de chat que lanza el usuario en la ventana, 
	 * generando respuesta, mostrándola en la ventana, y almacenando las frases.
	 */
	public void nuevoChat() {
		if (ventChat==null) return;
		String texto = ventChat.getTaChat().getText();
		if (texto.isEmpty()) return;
		// Muestra y registra la frase del usuario
		ventChat.nuevaFraseUsuario( texto );
		bd.anyadirFrase( new Frase( usuarioLogueado.getNick(), Main.NOMBRE_CHAT, System.currentTimeMillis(), texto ), null );

		// Calcula, muestra y registra la respuesta de ChatNoGPT
		try {
			Thread.sleep( 200 + (int)(Math.random()*400) );  // Simula una pequeña espera (décimas de segundo) aleatoria
		} catch (InterruptedException e) {} 
		String respuesta = chatNoGPT.devuelveRespuesta( texto );
		ventChat.nuevaRespuestaChat( respuesta );
		bd.anyadirFrase( new Frase( Main.NOMBRE_CHAT, usuarioLogueado.getNick(), System.currentTimeMillis(), respuesta ), null );

		// Desplaza el scroll al final
		Rectangle rect = new Rectangle(0, ventChat.getPanelChat().getHeight() - 3, 10, 3);
		ventChat.getPanelChat().scrollRectToVisible(rect);
		// Vacía y prepara la caja de texto para siguiente interacción
		ventChat.getTaChat().setText( "" );
		ventChat.getTaChat().requestFocus();
	}
	
}
