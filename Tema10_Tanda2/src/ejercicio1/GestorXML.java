package ejercicio1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class GestorXML {

	private String nomFichXML;
	private Document doc;
	final String[] MEDICOS= {"Dr Saez", "Dra Artea", "Dr Cabeza", "Dra Kholn"};
	
	//Constructor
	public GestorXML(String nomFich) {
		this.nomFichXML = nomFich;
		
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(nomFich);
		} catch (JDOMException e) {
			System.out.println("Documento XML corrupto");
		} catch (IOException e) {
			crearDe0();
		}
	}
	
	//Metodo crearDe0
	public void crearDe0() {
		doc = new Document();
		Element eConsultas = new Element("consultas");
		doc.setRootElement(eConsultas);
		
		for(int i = 0; i<MEDICOS.length; i++) {
			Element eMedico = new Element("medico");
			eMedico.setAttribute(new Attribute("nombre", MEDICOS[i]));
			eConsultas.addContent(eMedico);
		}
		grabar();
	}
	
	//Metodo grabar
	private void grabar() {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			outputter.output(doc, new FileWriter(nomFichXML));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Metodo leerDoctores
	public String[] leerDoctores() {
		Element eConsultas = doc.getRootElement();
		String[] arr = new String[eConsultas.getChildren().size()];
		
		Iterator<Element> it = eConsultas.getChildren().iterator();
		int i = 0;
		while(it.hasNext()) {
			Element eMedico = it.next();
			arr[i] = eMedico.getAttributeValue("nombre");
			i++;
		}
		return arr;
	}
	
	//Metodo aniadirConsulta
	public void aniadirConsulta(String medico, String hora, String paciente) {
		Element eConsulta = new Element("consulta");
		Element eHora= new Element("hora");
		eHora.addContent(hora);
		Element ePaciente = new Element("paciente");
		ePaciente.addContent(paciente);
		eConsulta.addContent(eHora);
		eConsulta.addContent(ePaciente);
		Element eConsultas = doc.getRootElement();
		for(Element eMedico: eConsultas.getChildren()) {
			if(eMedico.getAttributeValue("nombre").equals(medico))
				eMedico.addContent(eConsulta);
		}
		grabar();
	}
	
	//Metodo horaOcupada
	public boolean horaOcupada(String medico, String hora) {
		Element eConsultas = doc.getRootElement();
		for(Element eMedico: eConsultas.getChildren()) {
			if(eMedico.getAttributeValue("nombre").equals(medico)) {
				List<Element> LstConsultasMedico = eMedico.getChildren();
				for(Element eConsulta: LstConsultasMedico) {
					if(eConsulta.getChildText("hora").equals(hora))
						return true;
				}
			}
		}
		return false;
	}
	
	//Main
	public static void main(String[] args) {
		GestorXML gx = new GestorXML("consultas.xml");
		gx.crearDe0();
	}
}
