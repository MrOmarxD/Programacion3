package gui;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicioSesion extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField cajaCorreo;
    private JPasswordField cajaContrasena;

    public VentanaInicioSesion() {
        super("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 180);

        // Crear componentes
        JLabel correoLabel = new JLabel("Correo electrónico:");
        cajaCorreo = new JTextField(40);
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        cajaContrasena = new JPasswordField(15);
        JButton botonIniciarSesion = new JButton("Iniciar Sesión");
        JButton botonRegistrar = new JButton("Registrarse");

        // Agregar ActionListener al botón de inicio de sesión
        botonIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = cajaCorreo.getText();
                char[] contrasena = cajaContrasena.getPassword();
                
                // Verifica las credenciales
                if (verificarCredenciales(correo, new String(contrasena))) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión fallido");
                }
                
                // Limpia los campos después del intento de inicio de sesión
                cajaContrasena.setText("");
            }
        });
        
        // Agregar ActionListener al botón de registrarse
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRegistro ventanaRegistro = new VentanaRegistro();
                ventanaRegistro.setVisible(true);
            }
        });

        // Crear un panel para organizar los componentes
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        panel.add(correoLabel);
        panel.add(cajaCorreo);
        panel.add(contrasenaLabel);
        panel.add(cajaContrasena);
        panel.add(botonIniciarSesion);
        panel.add(botonRegistrar);
        
        this.add(panel);
    }

    // Método para verificar las credenciales
    private boolean verificarCredenciales(String correo, String contrasena) {
    	
        return correo.equals("correo") && contrasena.equals("contrasena");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaInicioSesion ventana = new VentanaInicioSesion();
                ventana.setVisible(true);
            }
        });
    }
}
