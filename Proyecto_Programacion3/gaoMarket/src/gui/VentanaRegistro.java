package gui;

import domain.GestorMarket;
import domain.Usuario;
import io.RegistroException;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class VentanaRegistro extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(VentanaRegistro.class.getName());
	
	protected JTextField cajaNombre;
	protected JTextField cajaApellidos;
	protected JTextField cajaUsuario;
	protected JTextField cajaCorreo;
    protected JTextField cajaTelefono;
    protected JPasswordField cajaContrasena;
    protected GestorMarket gestor;
    
    public VentanaRegistro(GestorMarket gestor) {
    	this.gestor = gestor;
		Container cp = this.getContentPane();   
        
        // Crear componentes
        JLabel nombreLabel = new JLabel("Nombre:");
        cajaNombre = new JTextField(15);
        JLabel apellidosLabel = new JLabel("Apellidos:");
        cajaApellidos = new JTextField(30);  
        JLabel telefonoLabel = new JLabel("Número de teléfono:");
        cajaTelefono = new JTextField(30);
        
        JLabel usuarioLabel = new JLabel("Nombre Usuario:");
        cajaUsuario = new JTextField(15);
        JLabel correoLabel = new JLabel("Correo electrónico:");
        cajaCorreo = new JTextField(40);
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        cajaContrasena = new JPasswordField(15);
        JButton botonRegistrarse = new JButton("Registrarse");
        
        // Agregar ActionListener al botón de registro
        botonRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                	registrar();
                }catch (RegistroException ex){
                	logger.log(Level.INFO, ex.mostrar());
                	JOptionPane.showMessageDialog(null, ex.mostrar());
                }
               
                // Limpia los campos después del registro
                cajaNombre.setText("");
                cajaApellidos.setText("");
                cajaTelefono.setText("");
                cajaUsuario.setText("");
                cajaCorreo.setText("");
                cajaContrasena.setText("");
            }
        });
        
        JButton botonInicioSesion = new JButton("Volver al inicio de sesión");
		
		botonInicioSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaInicioSesion v = new VentanaInicioSesion(gestor);
				v.setVisible(true);
				dispose();				
			}
			
		});
        
        // Crear un panel para organizar los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(nombreLabel);
        panel.add(cajaNombre);
        panel.add(apellidosLabel);
        panel.add(cajaApellidos);
        panel.add(telefonoLabel);
        panel.add(cajaTelefono);
        panel.add(usuarioLabel);
        panel.add(cajaUsuario);
        panel.add(correoLabel);
        panel.add(cajaCorreo);
        panel.add(contrasenaLabel);
        panel.add(cajaContrasena);
        panel.add(botonRegistrarse);
        panel.add(botonInicioSesion);
        panel.setBorder(new EmptyBorder(10,20,10,20));
        
        cp.add(panel);
        
        ImageIcon iconoPrincipal = new ImageIcon("resources/iconos/iconoGAO.png");
		iconoPrincipal = new ImageIcon(iconoPrincipal.getImage().getScaledInstance(207, 207, Image.SCALE_SMOOTH));
		
		this.setIconImage(iconoPrincipal.getImage());
        
        this.setTitle("Registro de usuario");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(700, 110, 450, 200);
        this.setVisible(false);
        
    }
    
    public void registrar() throws RegistroException{
    	String nombre = cajaNombre.getText();
    	String apellido = cajaApellidos.getText();
    	String usuario = cajaUsuario.getText();
    	String correo = cajaCorreo.getText();
    	char[] contrasenya = cajaContrasena.getPassword();
    	String cont = "";
        for(int i = 0; i < contrasenya.length; i++) {
        	cont += contrasenya[i];
        }
        if(!validarCorreo(correo))
        	throw new RegistroException("3");
        int telefono = 0;
        try {
            telefono = Integer.parseInt(cajaTelefono.getText());
        }
        catch (NumberFormatException ex){
        	throw new RegistroException("2");
        }
        if(nombre.isBlank() || apellido.isBlank() || usuario.isBlank() || correo.isBlank() || cont.isBlank())
        	throw new RegistroException("1");
        if(gestor.getGestorBD().guardarUsuario(new Usuario(nombre, apellido, usuario, 
        		telefono,correo, cont))) {
        	 JOptionPane.showMessageDialog(null, "Registrado con éxito: " + usuario);
        	 dispose();
        }
        else
        	JOptionPane.showMessageDialog(null, "No se ha podido registrar el usuario: " + usuario);
    }
    
    public static boolean validarCorreo(String correo) {
        return Pattern.compile("^(.+)@(.+)$")
          .matcher(correo)
          .matches();
    }
}
