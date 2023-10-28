package ejercicio3;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaPedido extends JDialog implements ActionListener{
	
	private MenuRestaurante owner;
	private JButton botAcept;
	private JButton botDiscard;
	private JLabel lblTotal;
	private JPanel panEntrante;
	private JPanel panPlatoPrinc;
	private JPanel panBotones;
	private final static String IMG="img/wine.jpg";
	private GestorXML gestorxml;
	
	
	
	//Constructor
	public VentanaPedido(MenuRestaurante owner) {
		super(owner);
		this.setTitle("Your order");
		this.owner = owner;
		this.setModal(true);
		gestorxml = new GestorXML("pedidos.xml");
		
		//Dibujar
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		panEntrante = new JPanel();
		panEntrante.setLayout(new FlowLayout());
		panEntrante.add(new JLabel(owner.getEntrante().getNombre()+" "+owner.getEntrante().getPrecio()+"€"));
		String img = IMG;
		if(owner.getEntrante().getImagen()!=null)
			img = owner.getEntrante().getImagen();
		panEntrante.add(new JLabel(redim(img, 80, 80)));
		this.getContentPane().add(panEntrante);
		
		panPlatoPrinc = new JPanel();
		panPlatoPrinc.setLayout(new FlowLayout());
		panPlatoPrinc.add(new JLabel(owner.getPrincipal().getNombre()+" "+owner.getPrincipal().getPrecio()+"€"));
		img = IMG;
		if(owner.getPrincipal().getImagen()!=null)
			img = owner.getPrincipal().getImagen();
		panPlatoPrinc.add(new JLabel(redim(img, 80, 80)));
		this.getContentPane().add(panPlatoPrinc);
		
		ArrayList<Plato> lstAdicionales = owner.getPlatosAdicionales();
		String src="";
		double totalAdicional = 0;
		for(int i = 0; i<lstAdicionales.size(); i++) {
			src+= lstAdicionales.get(i).getNombre()+"("+lstAdicionales.get(i).getPrecio()+"€)\t";
			totalAdicional+=lstAdicionales.get(i).getPrecio();
		}
		src+= ": "+totalAdicional+"€";
		if(totalAdicional!=0)
			this.getContentPane().add(new JLabel(src));
		lblTotal = new JLabel("YOUR TOTAL PRICE :"+(owner.getEntrante().getPrecio()+owner.getPrincipal().getPrecio()+totalAdicional)+"€");
		lblTotal.setFont(new Font("Arial", Font.BOLD, 15));
		this.getContentPane().add(lblTotal);
		
		panBotones = new JPanel();
		panBotones.setLayout(new FlowLayout());
		botAcept = new JButton("ACEPT");
		botDiscard = new JButton("DISCARD");
		panBotones.add(botAcept);
		panBotones.add(botDiscard);
		this.getContentPane().add(panBotones);
		
		//Eventos
		botAcept.addActionListener(this);
		botDiscard.addActionListener(this);
		
		setSize(300,600);
		setVisible(false);
	}
	
	//Metodo redim
	private static ImageIcon redim (String fichImag, int ancho, int alto){
		ImageIcon imIcon=new ImageIcon(fichImag);
		Image im=imIcon.getImage();
		Image im2= im.getScaledInstance(ancho, alto, 0);
		return new ImageIcon(im2);
	}

	//Metodo actionPerformed
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==botDiscard)
			this.dispose();
		else {
			gestorxml.anadirPedido(owner.getEntrante().getNombre(), owner.getPrincipal().getNombre(), owner.getPlatosAdicionales());
			this.dispose();
		}
		
	}
}
