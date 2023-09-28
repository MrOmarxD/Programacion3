package ejercicio3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MenuRestaurante extends JFrame{

	private JPanel panEntrantes;
	private JPanel panPlatoPrinc;
	private JPanel panPlaAdic;
	private JButton botOrder;
	private JRadioButton[] botEntrantes;
	private JRadioButton[] botPlatoPrinc;
	private JCheckBox[] checkAdicional;
	private ButtonGroup bgEntrantes;
	private ButtonGroup bgPlatoPrinc;
	private GestorXML gestorxml;
	private ArrayList<Plato> lstPlaAdic;
	private ArrayList<Plato> lstPlatoPrinc;
	private ArrayList<Plato> lstEntrantes;
	private JLabel lblPrecioEntrante;
	private JLabel lblPrecioPlatoPrinc;
	private JLabel lblPrecioPlaAdic;
	private Plato entrante;
	private Plato principal;
	private ArrayList<Plato> platosAdicionales = new ArrayList<Plato>();
	private VentanaPedido venPedido;
	private MenuRestaurante menuRestaurante= this;
	
	//Constructor
	public MenuRestaurante() {
		this.setLayout(null);
		
		gestorxml = new GestorXML("platos.xml");
		
		dibujar();
		eventos();
		
		setSize(600,300);
		setVisible(true);
	}
	
	//Dibujar
	private void dibujar() {
		
		//Panel Entrantes
		panEntrantes = new JPanel();
		panEntrantes.setLayout(new BoxLayout(panEntrantes, BoxLayout.Y_AXIS));
		panEntrantes.setBounds(0, 0, 160, 200);
		panEntrantes.add(new JLabel("CHOOSE FIRST COURSE"));
		bgEntrantes = new ButtonGroup();
		lstEntrantes = gestorxml.damePlatos("entrante");
		botEntrantes = new JRadioButton[lstEntrantes.size()];
		for(int i = 0; i<lstEntrantes.size(); i++) {
			botEntrantes[i] = new JRadioButton(lstEntrantes.get(i).toString());
			bgEntrantes.add(botEntrantes[i]);
			panEntrantes.add(botEntrantes[i]);
		}
		lblPrecioEntrante = new JLabel();
		panEntrantes.add(lblPrecioEntrante);
		
		//Panel platos principales
		panPlatoPrinc = new JPanel();
		panPlatoPrinc.setLayout(new BoxLayout(panPlatoPrinc, BoxLayout.Y_AXIS));
		panPlatoPrinc.setBounds(220, 0, 160, 200);
		panPlatoPrinc.add(new JLabel("CHOOSE MAIN COURSE"));
		bgPlatoPrinc = new ButtonGroup();
		lstPlatoPrinc = gestorxml.damePlatos("principal");
		botPlatoPrinc = new JRadioButton[lstPlatoPrinc.size()];
		for(int i = 0; i<lstPlatoPrinc.size(); i++) {
			botPlatoPrinc[i] = new JRadioButton(lstPlatoPrinc.get(i).toString());
			bgPlatoPrinc.add(botPlatoPrinc[i]);
			panPlatoPrinc.add(botPlatoPrinc[i]);
		}
		lblPrecioPlatoPrinc = new JLabel();
		panPlatoPrinc.add(lblPrecioPlatoPrinc);
		
		
		//Panel platos adicionales
		panPlaAdic = new JPanel();
		panPlaAdic.setLayout(new BoxLayout(panPlaAdic, BoxLayout.Y_AXIS));
		panPlaAdic.setBounds(430, 0, 160, 200);
		panPlaAdic.add(new JLabel("SIDE DISHES (extra price)"));
		lstPlaAdic = gestorxml.damePlatos("adicional");
		checkAdicional = new JCheckBox[lstPlaAdic.size()];
		for(int i = 0; i<lstPlaAdic.size(); i++) {
			checkAdicional[i] = new JCheckBox(lstPlaAdic.get(i).toString());
			panPlaAdic.add(checkAdicional[i]);
		}
		lblPrecioPlaAdic = new JLabel();
		panPlaAdic.add(lblPrecioPlaAdic);
		
		//Boton order
		botOrder = new JButton("ORDER");
		botOrder.setBounds(270, 250, 80, 40);
		
		this.getContentPane().add(panEntrantes);
		this.getContentPane().add(panPlatoPrinc);
		this.getContentPane().add(panPlaAdic);
		this.getContentPane().add(botOrder);
	}
	
	//Metodo entranteElegido
	private boolean entranteElegido() {
		for(int i = 0; i<botEntrantes.length; i++) {
			if(botEntrantes[i].isSelected())
				return true;
		}
		return false;
	}
	
	//Metodo entranteElegido
	private boolean platoPrincElegido() {
		for(int i = 0; i<botPlatoPrinc.length; i++) {
			if(botPlatoPrinc[i].isSelected())
				return true;
		}
		return false;
	}
	
	//Eventos
	private void eventos() {
		for(int i = 0; i<botEntrantes.length; i++) {
			botEntrantes[i].addActionListener(new EscuchadorPrimerPlato(i));
		}
		
		for(int i = 0; i<botPlatoPrinc.length; i++) {
			botPlatoPrinc[i].addActionListener(new EscuchadorPlatoPrincipal(i));
		}
		
		for(int i = 0; i<checkAdicional.length; i++) {
			checkAdicional[i].addActionListener(new EscuchadorPlatoAdicional());
		}
		
		botOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!entranteElegido() || !platoPrincElegido())
					JOptionPane.showMessageDialog(null, "Debes elegir entrante y principal. Los adicionales son opcionales");
				else {
					venPedido = new VentanaPedido(menuRestaurante);
					venPedido.setVisible(true);
				}
			}
		});
	}
	
	//Classes escuchadoras 
	class EscuchadorPrimerPlato implements ActionListener{
		private int indice;
		
		public EscuchadorPrimerPlato(int indice) {
			this.indice = indice;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String nombre = botEntrantes[indice].getText();
			for(int i = 0; i<lstEntrantes.size(); i++) {
				if(lstEntrantes.get(i).getNombre().equals(nombre)) {
					entrante = lstEntrantes.get(i);
					lblPrecioEntrante.setText("PRICE: "+entrante.getPrecio()+"€");
					 i= lstEntrantes.size();
				}
			}
		}
		
	}
	
	class EscuchadorPlatoPrincipal implements ActionListener{
		private int indice;
		
		public EscuchadorPlatoPrincipal(int indice) {
			this.indice = indice;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String nombre = botPlatoPrinc[indice].getText();
			for(int i = 0; i<lstPlatoPrinc.size(); i++) {
				if(lstPlatoPrinc.get(i).getNombre().equals(nombre)) {
					principal = lstPlatoPrinc.get(i);
					lblPrecioPlatoPrinc.setText("PRICE: "+principal.getPrecio()+"€");
					 i= lstPlatoPrinc.size();
				}
			}
		}
		
	}
	
	class EscuchadorPlatoAdicional implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			platosAdicionales.clear();
			lblPrecioPlaAdic.setText("");
			for(int j = 0; j<checkAdicional.length; j++) {
				if(checkAdicional[j].isSelected()) {
					String nombre = checkAdicional[j].getText();
					double precio= 0;
					for(int i = 0; i<lstPlaAdic.size(); i++) {
						if(lstPlaAdic.get(i).getNombre().equals(nombre)) {
							platosAdicionales.add(lstPlaAdic.get(i));
							precio =lstPlaAdic.get(i).getPrecio();
							 i = lstPlaAdic.size();
						}
					}
					lblPrecioPlaAdic.setText(lblPrecioPlaAdic.getText()+" + "+precio+"€");
				}
			}
			if(lblPrecioPlaAdic.getText().length()>2)
				lblPrecioPlaAdic.setText(lblPrecioPlaAdic.getText().substring(2));
		}	
	}
	
	//get/set
	public Plato getEntrante() {
		return entrante;
	}

	public Plato getPrincipal() {
		return principal;
	}
	public ArrayList<Plato> getPlatosAdicionales() {
		return platosAdicionales;
	}

	//Main
	public static void main(String[] args) {
		new MenuRestaurante();
	}
}
