package ejercicio2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.management.Attribute;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class GestorXML {
	
	private String nomFichXML;
	private Document doc;
	
	//Constructor
	public GestorXML(String nomFich) {
		this.nomFichXML = nomFich;
			
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(nomFich);
		} catch (JDOMException e) {
			System.out.println("Documento XML corrupto");
		} catch (IOException e) {
			System.out.println("Documento XML no existe");
		}
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

	//Metodo palabraAleatoria
	public String palabraAleatoria(int numLetras) {
		String palabra="";
		Element eAhorcado = doc.getRootElement();
		for(Element eGrupo: eAhorcado.getChildren()) {
			if(eGrupo.getAttributeValue("numletras").equals(numLetras+"")) {
				List<Element> pal = eGrupo.getChildren();
				palabra = pal.get((int) (Math.random()*pal.size())).getText();
			}
		}
		return palabra;
	}
	
	//Metodo anidirAcierto
	public void anidirAcierto(int grupo, String palabra) {
		Element eAhorcado = doc.getRootElement();
		for(Element eGrupo: eAhorcado.getChildren()) {
			if(eGrupo.getAttributeValue("numletras").equals(grupo+"")) {
				for(Element ePal: eGrupo.getChildren()) {
					if(ePal.getText().equals(palabra)) {
						String numAciertos = 1+"";
						if(ePal.getAttribute("aciertos")!=null)
							numAciertos = (Integer.parseInt(numAciertos)+1)+"";
						ePal.setAttribute("aciertos", numAciertos);
					}
				}
			}
		}
		grabar();
	}
}
