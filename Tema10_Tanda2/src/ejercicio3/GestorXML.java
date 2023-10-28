package ejercicio3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
	
	//Metodo damePlatos
	public ArrayList<Plato> damePlatos(String tipo) {
		ArrayList<Plato> lstPlatos = new  ArrayList<Plato>();
		Element ePlatos = doc.getRootElement();
		for(Element ePlato: ePlatos.getChildren()) {
			if(ePlato.getAttributeValue("tipo").equals(tipo)) {
				Plato p;
				String nombre = ePlato.getAttributeValue("nombre");
				double precio = Double.parseDouble(ePlato.getChildText("precio"));
				if(ePlato.getChild("imagen")==null) {
					p = new Plato(nombre, tipo, precio);
				}
				else {
					p = new Plato(nombre, tipo, precio, ePlato.getChildText("imagen"));
				}
				lstPlatos.add(p);
			}
		}
		return lstPlatos;
	}

	//Metodo anadirPedido
	public void anadirPedido(String entrante, String principal, ArrayList<Plato> lstAdicionales) {
		GregorianCalendar calendar= new GregorianCalendar();
		int dia= calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int mes= calendar.get(GregorianCalendar. MONTH);
		String fecha = dia+"-"+mes;
		Element ePedidos = buscarPedidoFecha(fecha);
		if(ePedidos==null) {
			ePedidos = new Element("pedidos");
			ePedidos.setAttribute("fecha", fecha);
			Element eRaiz = doc.getRootElement();
			eRaiz.addContent(ePedidos);
		}
		Element ePedido = new Element("pedido");
		Element ePrimero = new Element("primero");
		ePrimero.addContent(entrante);
		ePedido.addContent(ePrimero);
		Element ePrincipal = new Element("principal");
		ePrincipal.addContent(principal);
		ePedido.addContent(ePrincipal);
		if(lstAdicionales.size()!=0) {
			Element eAdicionales = new Element("adicionales");
			for(int i = 0; i<lstAdicionales.size(); i++) {
				Element eAdional = new Element("adicional");
				eAdional.addContent(lstAdicionales.get(i).getNombre());
				eAdicionales.addContent(eAdional);
			}
			ePedido.addContent(eAdicionales);
		}
		ePedidos.addContent(ePedido);
		grabar();
	}
	
	//Metodo buscarPedidoFecha
	private Element buscarPedidoFecha(String fecha) {
		
		Element eRaiz = doc.getRootElement();
		List<Element> lstPedidos= eRaiz.getChildren();
		for (int i = 0; i<lstPedidos.size(); i++) {
			Element ePedido = lstPedidos.get(i);
			if(ePedido.getAttributeValue("fecha").equals(fecha))
				return ePedido;
		}
		return null;
	}
}
