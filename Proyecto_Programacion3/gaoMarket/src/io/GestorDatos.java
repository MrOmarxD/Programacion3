package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.*;
import domain.Producto.Estado;

public class GestorDatos {
	protected GestorMarket gestor;
	
	public void guardarProductos(String ficheroCaracteristicas) {
		try {
			PrintWriter pw = new PrintWriter(ficheroCaracteristicas);
		
			pw.println(ficheroCaracteristicas);
		
			for (Producto productos : gestor.getProductos()) {
				pw.println(productos.getId() +";"+ productos.getNombre() +";"+ productos.getImagen() +";"+  
						productos.getPrecio()+" €" +";"+ productos.getCantidad()+";"+ productos.getTipoProducto()+";"+ 
						productos.getEstado()+";"+ productos.getDescuento()+";");
			}
		
			pw.close();
		
		} catch (FileNotFoundException e) {
			System.err.println("Error al guardar datos en CSV.");
		}
	}
	
	
	public static List<Producto> cargarProductos() {
		List<Producto> lista = new ArrayList<>();
		
		//T1a - Carga de eventos desde eventos.csv
		try {
			Scanner sc = new Scanner(new FileReader("resources/datos/productos.csv"));
			while(sc.hasNext()) {
				String linea = sc.nextLine();
				String [] partes = linea.split(",");
				int id = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				String imagen = partes[2];
				double precio = Double.parseDouble(partes[3]);
				int cantidad = Integer.parseInt(partes[4]);
				TipoProducto tipoProducto = TipoProducto.valueOf(partes[5]);
				
				if (tipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento.valueOf(partes[6]);
				} else if (tipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
					TipoHigieneYBelleza.valueOf(partes[6]);
				} else if (tipoProducto == TipoProducto.LIMPIEZA) {
					TipoLimpieza.valueOf(partes[6]);
				} else {
					throw new IllegalArgumentException("Tipo de producto no reconocido.");
				}
				
				Estado estado = Estado.valueOf(partes[7]);
				int descuento = Integer.parseInt(partes[8]);
				
				Producto p = new Producto(id, nombre, imagen, precio, cantidad, tipoProducto, tipoProducto, estado, descuento);
				lista.add(p);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lista;
		
	}

}
