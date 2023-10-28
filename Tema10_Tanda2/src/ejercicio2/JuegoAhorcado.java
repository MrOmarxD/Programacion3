package ejercicio2;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JuegoAhorcado extends JFrame implements ActionListener{
	
	private JButton botJugar;
	private JButton[] botLetras;
	private JLabel lblPalEnCurso;
	private JLabel[] lblImaX;
	private DialogoNumLetras venDialogoNumLetras;
	private Ahorcado ahorcado;
	private JPanel panX;
	
	//Constructor
	public JuegoAhorcado() {
		
		venDialogoNumLetras = new DialogoNumLetras(this);
		
		dibujar();
		eventos();
		
		setSize(600,400);
		setVisible(true);
	}
	
	//Dibujar
	private void dibujar() {
		this.setLayout(null);
		
		//BotJugar
		botJugar = new JButton("JUGAR");
		botJugar.setBounds(260, 10, 80, 30);
		this.getContentPane().add(botJugar);
		
		//lblPalEnCurso
		lblPalEnCurso = new JLabel();
		lblPalEnCurso.setBounds(250, 300, 100, 30);
		this.getContentPane().add(lblPalEnCurso);
		
		//Panel imagenes X
		panX = new JPanel();
		panX.setLayout(new FlowLayout());
		panX.setBounds(10, 50, 590, 80);
		this.getContentPane().add(panX);
		
		//botLetras
		botLetras = new JButton['Z'-'A'+1];
		JPanel panGrid = new JPanel();
		panGrid.setLayout(new GridLayout(2, botLetras.length));
		for (int i = 0; i<botLetras.length; i++) {
			char letra = (char) ('A'+i);
			botLetras[i] = new JButton(letra+"");
			panGrid.add(botLetras[i]);
		}
		panGrid.setBounds(0, 350, 600, 50);
		this.getContentPane().add(panGrid);
	}
	
	//Eventos
	private void eventos() {
		botJugar.addActionListener(this);
		for(int i = 0; i<botLetras.length; i++) {
			botLetras[i].addActionListener(new EscuchadorBotLetras(i));
		}
	}
	//Class EscuchadorBotLetras
	class EscuchadorBotLetras implements ActionListener{
		private int i;
		
		public EscuchadorBotLetras(int i) {
			this.i = i;
		}

		public void actionPerformed(ActionEvent e) {
			if(ahorcado.tirar(botLetras[i].getText().toLowerCase()))
				lblPalEnCurso.setText(ahorcado.respuestaToBigString());
			else
				lblImaX[ahorcado.getNumIntentos()-1].setVisible(true);
			if(ahorcado.completo()) {
				JOptionPane.showMessageDialog(null, "Enhorabuena. Palabra "+ahorcado.getPalAdivinar().toUpperCase()+" acertada");
				ahorcado.anidirAcierto();
				restablecer();
			}
			if(ahorcado.getMaxIntentos()==ahorcado.getNumIntentos()) {
				JOptionPane.showMessageDialog(null, "Se acabaron tus vidas. Palabra: "+ahorcado.getPalAdivinar().toUpperCase());
				restablecer();
			}
		}
	}
	
	//Metodo actionPerformed
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==botJugar) {
			venDialogoNumLetras.setVisible(true);
			if(venDialogoNumLetras.getNumLetras()==-1)
				ahorcado = new Ahorcado();
			else
				ahorcado = new Ahorcado(venDialogoNumLetras.getNumLetras());
			lblPalEnCurso.setText(ahorcado.respuestaToBigString());
			lblImaX = new JLabel[ahorcado.getMaxIntentos()];
			int x = 10;
			for(int i = 0; i<lblImaX.length; i++) {
				lblImaX[i] = new JLabel(redim("imgX.jpg",40,40));
				lblImaX[i].setVisible(false);
				lblImaX[i].setBounds(x, 50, 40, 40);
				panX.add(lblImaX[i]);
				x+=45;
			}
			botJugar.setEnabled(false);
			panX.revalidate();
			panX.repaint();
		}
	}
	
	//Metodo restablecer
	public void restablecer() {
		panX.removeAll();
		lblPalEnCurso.setText("");
		botJugar.setEnabled(true);
	}
	
	//Metodo redim
	private static ImageIcon redim (String fichImag, int ancho, int alto){
		ImageIcon imIcon=new ImageIcon(fichImag);
		Image im=imIcon.getImage();
		Image im2= im.getScaledInstance(ancho, alto, 0);
		return new ImageIcon(im2);
	}
		
	//Main
	public static void main(String[] args) {
		new JuegoAhorcado();
	}
	
}
