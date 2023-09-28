package ejercicio1;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaResetear extends JDialog implements ActionListener{

	private JButton botSi;
	private JButton botNo;
	private ConsultasMedicas owner;
	
	public VentanaResetear(ConsultasMedicas owner) {
		super(owner);
		this.owner = owner;
		
		this.setModal(true);
		
		//dibujar 
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.getContentPane().add(new JLabel("¿Deseas resetear fichero de medicos?"));
		botSi = new JButton("SÍ");
		botNo = new JButton("NO");
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		pan.add(botSi);
		pan.add(botNo);
		this.getContentPane().add(pan);
		
		//evento
		botSi.addActionListener(this);
		botNo.addActionListener(this);
		
		pack();
		setVisible(true);
	}

	//Metodo actionPerformed
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==botSi) {
			owner.getGestorxml().crearDe0();
			this.setVisible(false);
		}
		else {
			this.setVisible(false);
		}
	}
}
