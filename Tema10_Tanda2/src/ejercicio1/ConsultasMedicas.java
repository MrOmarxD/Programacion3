package ejercicio1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ConsultasMedicas extends JFrame{

	private JPanel panHora;
	private JPanel panDoctor;
	private JPanel panPaciente;
	private JTextField txtNombre;
	private JComboBox<String> cmbDoctores;
	private JScrollPane scrollHoras;
	private JList<HoraConsulta> lstHoras;
	private JButton botAniadir;
	private GestorXML gestorxml;
	private String[] lstDoctores;
	private VentanaResetear venRest;
	
	//Constructor
	public ConsultasMedicas() {
		super("Consultas médicas");
		
		gestorxml = new GestorXML("consultas.xml");
		venRest = new VentanaResetear(this);
		
		dibujar();
		eventos();
		
		setSize(400,400);
		setVisible(true);
	}
	
	//Metodo dibujar
	private void dibujar() {
		this.setLayout(null);
		
		
		//Zona horas
		panHora = new JPanel();
		panHora.setLayout(new BoxLayout(panHora, BoxLayout.Y_AXIS));
		panHora.add(new JLabel("Elija una o más horas"));
		HoraConsulta[] arrHoras = new HoraConsulta[(15-9)*2+2];
		for(int i = 9, indice = 0; i<=15 ; i++, indice++) {
			HoraConsulta hora1 = new HoraConsulta(i, 0);
			arrHoras[indice] = hora1;
			indice++;
			HoraConsulta hora2 = new HoraConsulta(i, 30);
			arrHoras[indice] = hora2;
		}
		lstHoras = new JList<HoraConsulta>(arrHoras);
		scrollHoras = new JScrollPane(lstHoras);
		panHora.add(scrollHoras);
		panHora.setBounds(10, 10, 125, 250);
		this.getContentPane().add(panHora);
		
		//Zona Doctor
		panPaciente = new JPanel();
		panPaciente.setLayout(new BoxLayout(panPaciente, BoxLayout.Y_AXIS));
		panPaciente.add(new JLabel("Nombre paciente:"));
		txtNombre = new JTextField();
		panPaciente.add(txtNombre);
		panPaciente.setBounds(300, 280, 125, 50);
		this.getContentPane().add(panPaciente);
		
		//Zona Paciente
		panDoctor = new JPanel();
		panDoctor.setLayout(new BoxLayout(panDoctor, BoxLayout.Y_AXIS));
		panDoctor.add(new JLabel("Elija médico"));
		lstDoctores = gestorxml.leerDoctores();
		cmbDoctores = new JComboBox<String>(lstDoctores);
		panDoctor.add(cmbDoctores);
		panDoctor.setBounds(300, 10, 125, 50);
		this.getContentPane().add(panDoctor);
		
		//Boton aniadir
		botAniadir = new JButton("AÑADIR");
		botAniadir.setBounds(100,350,100,40);
		this.getContentPane().add(botAniadir);
	}
	
	//Metodo eventos
	private void eventos() {
		botAniadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!txtNombre.getText().equals("") && lstHoras.getSelectedValue()!=null) {
					if(gestorxml.horaOcupada((String)cmbDoctores.getSelectedItem(), lstHoras.getSelectedValue().toString())) {
						JOptionPane.showMessageDialog(null, "CONSULTA NO ASIGNADA. "+(String)cmbDoctores.getSelectedItem()+" ya tiene asignadas: "+lstHoras.getSelectedValue().toString());
					}
					else {
						gestorxml.aniadirConsulta((String)cmbDoctores.getSelectedItem(), lstHoras.getSelectedValue().toString(), txtNombre.getText());
						JOptionPane.showMessageDialog(null, "CONSULTA ASIGNADA: "+txtNombre.getText()+" - "+(String)cmbDoctores.getSelectedItem()+"\n"+lstHoras.getSelectedValue().toString());
					}
					txtNombre.setText("");
				}
				else
					System.out.println("Debes de escribir un nombre de paciente y almenos elegir una hora de consulta.");
			}
		});
	}
	
	//get/set
	public GestorXML getGestorxml() {
		return gestorxml;
	}
	
	//Main
	public static void main(String[] args) {
		new ConsultasMedicas();
	}
}
