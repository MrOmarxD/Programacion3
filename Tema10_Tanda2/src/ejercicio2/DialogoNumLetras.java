package ejercicio2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoNumLetras extends JDialog{

	private JuegoAhorcado owner;
	private JTextField txtNumLetras;
	private JButton botAceptar;
	private JButton botCancelar;
	private int numLetras;
	
	//Constructor
	public DialogoNumLetras(JuegoAhorcado owner) {
		super(owner);
		this.owner = owner;
		this.setModal(true);
		
		//Dibujar
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(new JLabel("Elija número de letras:"));
		txtNumLetras = new JTextField();
		this.getContentPane().add(txtNumLetras);
		JPanel panBot = new JPanel();
		panBot.setLayout(new FlowLayout());
		botAceptar = new JButton("ACEPTAR");
		botCancelar = new JButton("CANCELAR");
		panBot.add(botAceptar);
		panBot.add(botCancelar);
		this.getContentPane().add(panBot);
		
		//Eventos
		botAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setVisible(false);
					numLetras = Integer.parseInt(txtNumLetras.getText());
				}
				catch(NumberFormatException e1) {
					numLetras = -1;
				}
				finally{
					txtNumLetras.setText("");
				}
			}
		});
		
		botCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				numLetras = -1;
				txtNumLetras.setText("");
			}
		});
		
		pack();
		setVisible(false);
	}
	
	//get/set
	public int getNumLetras() {
		return numLetras;
	}
}
