package es.deusto.prog3.examen202401ord.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.deusto.prog3.examen202401ord.datos.Frase;
import es.deusto.prog3.examen202401ord.datos.PreguntaRespuesta;
import es.deusto.prog3.examen202401ord.logica.GestorUsuarios;
import es.deusto.prog3.examen202401ord.main.Main;

public class VentanaAdmin extends JFrame{
	private GestorUsuarios gestorUsuarios;
	private JPanel pOeste, pCentro, pSur;
	private JTable tabla;
	
	public VentanaAdmin(GestorUsuarios gu) {
		gestorUsuarios = gu;
		setBounds(200, 300, 600, 400);
		
		pOeste = new JPanel();
		pCentro = new JPanel();
		pSur = new JPanel();
		
		getContentPane().add(pOeste, BorderLayout.WEST);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		
		/*Creación de la tabla*/
		tabla = new JTable(new ModeloTabla(null));
		JScrollPane scroll = new JScrollPane(tabla);
		pCentro.add(scroll);
		cargarTabla();
		setVisible(true);
	}

	private void cargarTabla() {
		ArrayList<Frase> frases = gestorUsuarios.getBd().leerFrases(null, "");
		ArrayList<PreguntaRespuesta> lista = new ArrayList<>();
		for(int i=0;i<frases.size();i++) {
			Frase f = frases.get(i);
			if(!f.getEmisor().equals(Main.NOMBRE_CHAT)) {
				PreguntaRespuesta pr = new PreguntaRespuesta(f.getTexto(), frases.get(i+1).getTexto());
				lista.add(pr);
			}
		}
		ModeloTabla mt = new ModeloTabla(lista);
		tabla.setModel(mt);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
