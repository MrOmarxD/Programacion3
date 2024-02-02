package gui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.GestorMarket;
import domain.Producto;
import domain.Producto.Estado;
import domain.TipoAlimento;
import domain.TipoHigieneYBelleza;
import domain.TipoLimpieza;
import domain.TipoProducto;

public class CadenaProductos {
	
	// ESTA CLASE TIENE QUE IR A GESTION DATOS PARA VOLCAR EL CSV AL MAPA, Y LOS MAPAS CON getMapa() y el cargarMapa() tienen que ir al GestorMarket.
	protected static Map<TipoProducto, Set<Producto>> mapaTipoProducto = new TreeMap<>();
	protected static Map<Enum<?>, List<Producto>> mapaCategoriaProducto = new HashMap<>();
	private static Logger logger = Logger.getLogger(CadenaProductos.class.getName());
	protected static List<Producto> productos;
	protected static GestorMarket gestor;

	public static Map<TipoProducto, Set<Producto>> getMapaTipoProducto() {
		return mapaTipoProducto;
	}
	
	public static Map<Enum<?>, List<Producto>> getMapaCategoriaProducto() {
		return mapaCategoriaProducto;
	}
	 
	public static List<Producto> obtenerListaTipoProductos(TipoProducto tipoProducto){
		List<Producto> l = new ArrayList<>();
		for(Producto p: mapaTipoProducto.get(tipoProducto)) {
			l.add(p);
		}
		return l;
	}
	
	public static List<Producto> obtenerListaCategoriaProductos(Enum<?> categoria){
		List<Producto> l = new ArrayList<>();
		for(Producto p: mapaCategoriaProducto.get(categoria)) {
			l.add(p);
		}
		return l;
	}
	
	public static void cargarMapaTipoProducto(String nomfich) {
		try {
			Scanner sc = new Scanner(new FileReader(nomfich));
			sc.nextLine();
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(";");
				int id = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				String imagen = partes[2];
				int cantidad = Integer.parseInt(partes[3]);
				Double precio = Double.parseDouble(partes[4]);
				TipoProducto tipoProducto = TipoProducto.valueOf(partes[5]);				
				Estado estado = Estado.valueOf(partes[7]);
				int descuento = Integer.parseInt(partes[8]);
				Producto p = new Producto();
				p.setId(id);
				p.setNombre(nombre);
				p.setImagen(imagen);
				p.setPrecio(precio);
				p.setCantidad(cantidad);
				p.setTipoProducto(tipoProducto);
				p.setEstado(estado);
				p.setDescuento(descuento);
				
				Enum<?> categoria;
				
				if (tipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento tipoAlimento  = TipoAlimento.valueOf(partes[6]);
					p.setCategoria(tipoAlimento);
					categoria = tipoAlimento;
					
				} else if (tipoProducto == TipoProducto.HIGIENE_Y_BELLEZA){
					TipoHigieneYBelleza tipoHigiene  = TipoHigieneYBelleza.valueOf(partes[6]);
					p.setCategoria(tipoHigiene );
					categoria = tipoHigiene;
					
				} else {
					TipoLimpieza tipoLimpieza  = TipoLimpieza.valueOf(partes[6]);
					p.setCategoria(tipoLimpieza );
					categoria = tipoLimpieza;
				}
				
				if (!mapaTipoProducto.containsKey(tipoProducto)) {
					mapaTipoProducto.put(tipoProducto, new TreeSet<>());
				}
				mapaTipoProducto.get(tipoProducto).add(p);

				
				if (!mapaCategoriaProducto.containsKey(categoria)) {
					mapaCategoriaProducto.put(categoria, new ArrayList<>());
				}
				mapaCategoriaProducto.get(categoria).add(p);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Error al cargar datos .csv");
		}
	}
	
	public static void cargarProductos(String nomfich) {
		try {
			Scanner sc = new Scanner(new FileReader(nomfich));
			sc.nextLine();
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(";");
				int id = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				String imagen = partes[2];
				int cantidad = Integer.parseInt(partes[3]);
				Double precio = Double.parseDouble(partes[4]);
				TipoProducto tipoProducto = TipoProducto.valueOf(partes[5]);				
				Estado estado = Estado.valueOf(partes[7]);
				int descuento = Integer.parseInt(partes[8]);
				
				productos = new ArrayList<Producto>();
				
				Producto p = new Producto();
				p.setId(id);
				p.setNombre(nombre);
				p.setImagen(imagen);
				p.setPrecio(precio);
				p.setCantidad(cantidad);
				p.setTipoProducto(tipoProducto);
				p.setEstado(estado);
				p.setDescuento(descuento);
				
				
				if (tipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento tipoAlimento  = TipoAlimento.valueOf(partes[6]);
					p.setCategoria(tipoAlimento);
					
				} else if (tipoProducto == TipoProducto.HIGIENE_Y_BELLEZA){
					TipoHigieneYBelleza tipoHigiene  = TipoHigieneYBelleza.valueOf(partes[6]);
					p.setCategoria(tipoHigiene );
					
				} else {
					TipoLimpieza tipoLimpieza  = TipoLimpieza.valueOf(partes[6]);
					p.setCategoria(tipoLimpieza );
				}
				
				
				productos.add(p);
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Error al cargar datos .csv");
		}
	}
	
	public static void guardarProductos(String nomfich, List<Producto> productos1) {
        try (PrintWriter writer = new PrintWriter(nomfich)) {
        	writer.println("Id;Nombre;Imagen;Cantidad;Precio;TipoProducto;Categoria;Estado;Descuento");
        	
            for (Producto producto : productos1) {
                writer.println(
                        producto.getId() + ";" +
                        producto.getNombre() + ";" +
                        producto.getImagen() + ";" +
                        producto.getCantidad() + ";" +
                        producto.getPrecio() + ";" +
                        producto.getTipoProducto() + ";" +
                        producto.getCategoria() + ";" +
                        producto.getEstado() + ";" +
                        producto.getDescuento()
                );
            }
            writer.close();

            logger.log(Level.INFO, "Datos guardados CSV exitosamente");
        } catch (IOException e) {
        	logger.log(Level.WARNING, "Error al cargar datos CSV");
        }
    }
	
	
	
}