package bd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Empleado;
import domain.Producto;
import domain.TipoAlimento;
import domain.TipoHigieneYBelleza;
import domain.TipoLimpieza;
import domain.TipoProducto;
import domain.Usuario;
import domain.Producto.Estado;

public class GestorBD {
	private Connection conn;
	//public static final String DB_PATH = "resources/db/GAOmarket.db";
	public static final String DB_PROPERTIES_PATH = "conf/configuracion.properties";
	public static String nombreBD;
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	
	public GestorBD() {
		configuracionBD();
		connect();
	}
	
	private void configuracionBD() {
		Properties properties = new Properties();
		try  {
			FileInputStream input = new FileInputStream(DB_PROPERTIES_PATH);
			properties.load(input);

			nombreBD = properties.getProperty("nombreBD");
		
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "No se ha podido encontrar el fichero de Base de Datos");
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error cargando las propiedades de la base de Datos");
		}
		
	}
	
	
	
	public void connect(){
		try {
			Class.forName("org.sqlite.JDBC");
			//conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
			conn = DriverManager.getConnection("jdbc:sqlite:resources/db/" + nombreBD);
			Statement statement = conn.createStatement();
			String sent = "CREATE TABLE IF NOT EXISTS producto (id INTEGER PRIMARY KEY, nombre varchar(100), imagen varchar(100), precio double, cantidad int, tipoProducto varchar(100), categoria varchar(100), estado varchar(100), descuento int);";
			statement.executeUpdate( sent );
			sent = "CREATE TABLE IF NOT EXISTS usuario (nombre varchar(100), apellidos varchar(100), nomUsuario varchar(100), numTelefono int, correoElectronico varchar(100), contrasenya varchar(100));";
			statement.executeUpdate( sent );
			sent = "CREATE TABLE IF NOT EXISTS empleado (nombre varchar(100), apellidos varchar(100), nomUsuario varchar(100), numTelefono int, correoElectronico varchar(100), contrasenya varchar(100), dni varchar(9));";
			statement.executeUpdate( sent );
			sent = "CREATE TABLE IF NOT EXISTS compra (usuario varchar(100), fecha date, total double);";
			statement.executeUpdate( sent );
			
			anyadirAdmin();
			
		} catch (ClassNotFoundException e) {
			logger.log(Level.WARNING, "No se ha podido cargar el driver de la base de datos");
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Error conectando a la BD", e);
		}
	}
	public void anyadirAdmin() {
		Empleado empleado = new Empleado("admin", "admin", "admin", 000000000, "admin@gaomarket.com", "admin", "46373459P");
		guardarEmpleado(empleado);
	}
	
	public void disconnect(){
		try {
			conn.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Error cerrando la conexión con la BD", e);
		}
	}
	
	public void cargarMapaTipoProducto(String nomfich) {
		
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
					categoria  = TipoAlimento.valueOf(partes[6]);
					p.setCategoria(categoria);
					volcarCSV(p);
					
				} else if (tipoProducto == TipoProducto.HIGIENE_Y_BELLEZA){
					categoria  = TipoHigieneYBelleza.valueOf(partes[6]);
					p.setCategoria(categoria);
					volcarCSV(p);
					
				} else {
					categoria = TipoLimpieza.valueOf(partes[6]);
					p.setCategoria(categoria);
					volcarCSV(p);
				}
				

			}
			sc.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Error al volcar datos .csv en la base de datos");
		}
			
	}
	
	public void volcarCSV(Producto p) {
	    try {
	        // Establecer la conexión y desactivar autocommit
	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:resources/db/" + nombreBD)) {
	            conn.setAutoCommit(false);  // Desactivar el modo de autocommit
	            
	            // Comprobar si el producto ya existe
	            if (!existeProductoEnBD(p.getId())) {
	                // Utilizar una sentencia preparada para evitar la inyección SQL
	                String sql = "INSERT INTO producto (id, nombre, imagen, precio, cantidad, tipoProducto, categoria, estado, descuento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	                    // Establecer los parámetros para la sentencia preparada
	                    preparedStatement.setInt(1, p.getId());
	                    preparedStatement.setString(2, p.getNombre());
	                    preparedStatement.setString(3, p.getImagen());
	                    preparedStatement.setDouble(4, p.getPrecio());
	                    preparedStatement.setInt(5, p.getCantidad());
	                    preparedStatement.setString(6, p.getTipoProducto().toString());
	                    preparedStatement.setString(7, p.getCategoria().toString());
	                    preparedStatement.setString(8, p.getEstado().toString());
	                    preparedStatement.setInt(9, p.getDescuento());

	                    // Ejecutar la actualización
	                    preparedStatement.executeUpdate();
	                }
	                
	                conn.commit();  // Realizar el commit para aplicar los cambios
	            }
	        }
	    } catch (SQLException e) {
	        logger.log(Level.WARNING, "Error conectando a la base de datos o ejecutando SQL", e);
	        try {
	            conn.rollback();  // Deshacer la transacción en caso de error
	        } catch (SQLException rollbackException) {
	            logger.log(Level.WARNING, "Error al deshacer la transacción", rollbackException);
	        }
	    }
	}

	private boolean existeProductoEnBD(int id) throws SQLException {
		String sql = "SELECT id FROM producto WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	
	public ResultSet realizarQuery(String sql, Statement st) {
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			logger.log(Level.WARNING, "No se ha podido realizar la consulta " + sql);
		}
		return rs;
	}
	
	private static final String capitalize(String str) {
		if (str == null || str.length() == 0) return str;

	    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	private static final String lower(String str) {
	    return str.toLowerCase();
	}
	
	//// Consultas de Usuarios
	
	// Metodo que busca un usuario en la BD por su nomUsuario y contraseña
	public Usuario verificarCredenciales(String nomUsuario, String password) { 
		Usuario u = null;
    	String sql = "SELECT * FROM usuario WHERE nomUsuario = ? AND contrasenya = ?";

		 try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, nomUsuario);
	        ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) { 
            	u = new Usuario(rs.getString("nombre"), rs.getString("apellidos"), rs.getString("nomUsuario"), rs.getInt("numTelefono"), rs.getString("correoElectronico"), rs.getString("contrasenya"));
            }
            rs.close();
            ps.close();
         }
         catch (Exception e)  {
        	 logger.log(Level.WARNING, "Error en verificarCredenciales(Usuario, password): " + e);
         } 
		 return u;
	}
	
	// Metodo que busca un usuario en la BD por su nomUsuario
	public Usuario buscarUsuario(String usuario) {
		Usuario u = new Usuario();
        String sql = "SELECT * FROM usuario WHERE nomUsuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();        
            while (rs.next()) {
            	String nombre = capitalize(rs.getString("nombre"));
            	String apellidos = capitalize(rs.getString("apellidos"));
            	String nomUsuario = rs.getString("nomUsuario");
            	int numTelefono = rs.getInt("numTelefono");
            	String correoElectronico = lower(rs.getString("correoElectronico"));
            	String contrasenya = rs.getString("contrasenya");
		        u = new Usuario(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya);
            }
            rs.close();
            ps.close();
         }
	     catch (Exception e)  {
	    	 logger.log(Level.WARNING, "Error en buscarUsuario(usuario): " + e);
	     } 
		 return u;
	}
	
	// Metodo que verifica si existe un usuario en la BD por su nomUsuario
	public boolean existeUsuario(String nomUsuario) {
		boolean existe = false;
        String sql = "SELECT * FROM usuario WHERE nomUsuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, nomUsuario);
            ResultSet rs = ps.executeQuery();        
            if (rs.next()) existe = true;
            rs.close();
            ps.close();
         }
	     catch (Exception e)  {
	    	 logger.log(Level.WARNING, "Error en existeUsuario(Usuario): " + e);
	     } 
		 return existe;
	}
		
	//Metodo para introducir un nuevo usuario en la base de datos
	public boolean guardarUsuario(Usuario u) {
		if(existeUsuario(u.getNomUsuario()) || existeEmpleado(u.getNomUsuario()))
			return false;
	    boolean guardado = false;
	    String sql =
	      "INSERT INTO usuario(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya) "
	      + "VALUES(?, ?, ?, ?, ?, ?)";
	    
		try (PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, capitalize(u.getNombre()));
			ps.setString(2, capitalize(u.getApellidos()));
			ps.setString(3, u.getNomUsuario());
			ps.setInt(4, u.getNumTelefono());
			ps.setString(5, lower(u.getCorreoElectronico()));
			ps.setString(6, u.getContrasenya());
			ps.executeUpdate();
			guardado = true;
			
			ps.close();
		} catch (SQLException ex) {
			logger.log(Level.WARNING, "Error en metodo guardarCliente: " + ex);
		}
	
	    return guardado;
	}
	
	//Metodo que elimina un usuario pasado su nomUsuario
	public boolean borrarUsuario(String nomUsuario){
		String sql = "DELETE FROM usuario WHERE nomUsuario = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
	       	ps.setString(1, nomUsuario); 
	       	ps.executeUpdate();
	       	ps.close();
	       	return true;
	   	} catch (SQLException ex) {
	   		logger.log(Level.WARNING, "Error en metodo borrarUsuario: " + ex);
	       	return false;
	   	}
	}
	
	// Metodo que devuelve todos los usuarios
	public List<Usuario> listarUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "select nombre, apellidos, nomUsuario, numTelefono, correoElectronico, "
				+ "contrasenya from usuario;";
	    try (Statement st = conn.createStatement()){
            ResultSet rs = realizarQuery(sql, st);
            while (rs.next()) {
            	String nombre = capitalize(rs.getString("nombre"));
            	String apellidos = capitalize(rs.getString("apellidos"));
            	String nomUsuario = rs.getString("nomUsuario");
            	int numTelefono = rs.getInt("numTelefono");
            	String correoElectronico = lower(rs.getString("correoElectronico"));
            	String contrasenya = rs.getString("contrasenya");
		        usuarios.add(new Usuario(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya));
	      }
	      rs.close();
	      st.close();
	    } catch (SQLException e) {
	    	logger.log(Level.WARNING, "Error en metodo listarUsuarios: " + e);
	    }
	    return usuarios;
	}
	
	////Consultas de Empleados
	
	// Metodo que busca un empleado en la BD por su nomUsuario y contraseña
	public Empleado verificarCredencialesEmpleado(String nomUsuario, String password) { 
		Empleado emp = null;
    	String sql = "SELECT * FROM empleado WHERE nomUsuario = ? AND contrasenya = ?";

		 try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, nomUsuario);
	        ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) { 
            	emp = new Empleado(rs.getString("nombre"), rs.getString("apellidos"), rs.getString("nomUsuario"), rs.getInt("numTelefono"), rs.getString("correoElectronico"), rs.getString("contrasenya"), rs.getString("dni"));
            }
            rs.close();
            ps.close();
         }
         catch (Exception e)  {
        	 logger.log(Level.WARNING, "Error en verificarCredencialesEmpleado(Usuario, password): " + e);
         } 
		 return emp;
	}
	
	// Metodo que busca un empleado en la BD por su nomUsuario
	public Empleado buscarEmpleado(String usuario) {
		Empleado emp = new Empleado();
        String sql = "SELECT * FROM empleado WHERE nomUsuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();        
            while (rs.next()) {
            	String nombre = capitalize(rs.getString("nombre"));
            	String apellidos = capitalize(rs.getString("apellidos"));
            	String nomUsuario = rs.getString("nomUsuario");
            	int numTelefono = rs.getInt("numTelefono");
            	String correoElectronico = lower(rs.getString("correoElectronico"));
            	String contrasenya = rs.getString("contrasenya");
            	String dni = rs.getString("dni");
		        emp = new Empleado(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya, dni);
            }
            rs.close();
            ps.close();
         }
	     catch (Exception e)  {
	    	 logger.log(Level.WARNING, "Error en buscarEmpleado(usuario): " + e);
	     } 
		 return emp;
	}
	
	// Metodo que busca un empleado en la BD por su nomUsuario
	public boolean existeEmpleado(String nomUsuario) {
		boolean existe = false;
       String sql = "SELECT * FROM empleado WHERE nomUsuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, nomUsuario);
           ResultSet rs = ps.executeQuery();        
           if (rs.next()) existe = true;
           rs.close();
           ps.close();
        }
	     catch (Exception e)  {
	    	 logger.log(Level.WARNING, "Error en existeEmpleado(Empleado): " + e);
	     } 
		 return existe;
	}
	
	//Metodo para introducir un nuevo empleado en la base de datos
	public boolean guardarEmpleado(Empleado e) {
		if(existeEmpleado(e.getNomUsuario()) || existeUsuario(e.getNomUsuario()))
			return false;
	    boolean guardado = false;
	    String sql =
	      "INSERT INTO empleado(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya, dni) "
	      + "VALUES(?, ?, ?, ?, ?, ?, ?)";
	    
		try (PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, capitalize(e.getNombre()));
			ps.setString(2, capitalize(e.getApellidos()));
			ps.setString(3, e.getNomUsuario());
			ps.setInt(4, e.getNumTelefono());
			ps.setString(5, lower(e.getCorreoElectronico()));
			ps.setString(6, e.getContrasenya());
			ps.setString(7, e.getDni());
			ps.executeUpdate();
			guardado = true;
			
			ps.close();
		} catch (SQLException ex) {
			logger.log(Level.WARNING, "Error en metodo guardarEmpleado: " + ex);
		}
	
	    return guardado;
	}
	
	//Metodo que elimina un empleado pasado su nomUsuario
	public boolean borrarEmpleado(String nomUsuario){
		String sql = "DELETE FROM empleado WHERE nomUsuario = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
	       	ps.setString(1, nomUsuario); 
	       	ps.executeUpdate();
	       	ps.close();
	       	return true;
	   	} catch (SQLException ex) {
	   		logger.log(Level.WARNING, "Error en metodo borrarEmpleado: " + ex);
	       	return false;
	   	}
	}
	
	// Metodo que devuelve todos los empleados
	public List<Empleado> listarEmpleados() {
		List<Empleado> empleado = new ArrayList<>();
		String sql = "select nombre, apellidos, nomUsuario, numTelefono, correoElectronico, "
				+ "contrasenya, dni from empleado;";
	    try (Statement st = conn.createStatement()){
           ResultSet rs = realizarQuery(sql, st);
           while (rs.next()) {
        	   String nombre = capitalize(rs.getString("nombre"));
        	   String apellidos = capitalize(rs.getString("apellidos"));
        	   String nomUsuario = rs.getString("nomUsuario");
        	   int numTelefono = rs.getInt("numTelefono");
        	   String correoElectronico = lower(rs.getString("correoElectronico"));
        	   String contrasenya = rs.getString("contrasenya");
        	   String dni = rs.getString("dni");
        	   empleado.add(new Empleado(nombre, apellidos, nomUsuario, numTelefono, correoElectronico, contrasenya, dni));
	      }
	      rs.close();
	      st.close();
	    } catch (SQLException e) {
	    	logger.log(Level.WARNING, "Error en metodo listarEmpleados: " + e);
	    }
	    return empleado;
	}
	
	////Consultas de Productos
	
	public boolean anyadirProducto(Producto p) {
		boolean anyadir = false;
		if(buscarProducto(p.getNombre()) == null) {
			return anyadir;
		}
		
		String sql = "INSERT INTO producto "
				+ "(nombre, imagen, precio, cantidad, tipoProducto, categoria, estado, descuento) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, p.getNombre());
	        ps.setString(2, p.getImagen());
	        ps.setDouble(3, p.getPrecio());
	        ps.setInt(4, p.getCantidad());
	        ps.setString(5, p.getTipoProducto().toString());
	        ps.setString(6, p.getCategoria().toString());
	        ps.setString(7, p.getEstado().toString());
	        ps.setInt(8, p.getDescuento());
			
	        ps.executeUpdate();
	        anyadir = true;
	        ps.close();
		
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Error en metodo anyadirProducto: " + e);
			anyadir = false;
		}
		return anyadir;
	}
	
	// Metodo para eliminar productos
	public boolean borrarProducto(int id){
		String sql = "DELETE FROM producto WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
	       	ps.setInt(1, id); 
	       	ps.executeUpdate();
	       	ps.close();
	       	return true;
	   	} catch (SQLException ex) {
	   		logger.log(Level.WARNING, "Error en metodo borrarProducto: " + ex);
	       	return false;
	   	}
	}
	
	
	//Metodo que devuelve todos los productos
	public List<Producto> listarProductos() {
		List<Producto> productos = new ArrayList<>();
		String sql = "SELECT id, nombre, imagen, precio, cantidad, tipoProducto, categoria, estado, descuento "
				+ "FROM Producto";
		try (Statement st = conn.createStatement()){
			ResultSet rs = realizarQuery(sql, st);
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = capitalize(rs.getString("nombre"));
				String imagen = rs.getString("imagen");
				Double precio = rs.getDouble("precio");
				int cantidad = rs.getInt("cantidad");
				String tipoProducto = rs.getString("tipoProducto");
				String categoria = rs.getString("categoria");
				String estadoStr = rs.getString("estado");
				Estado estado = Estado.valueOf(estadoStr);
				int descuento = rs.getInt("descuento");
				
				Producto p = new Producto();
				p.setId(id);
				p.setNombre(nombre);
				p.setImagen(imagen);
				p.setPrecio(precio);
				p.setCantidad(cantidad);

				TipoProducto enumTipoProducto = TipoProducto.valueOf(tipoProducto);
				p.setTipoProducto(enumTipoProducto);

				if (enumTipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento enumTipoA = TipoAlimento.valueOf(categoria);
					p.setCategoria(enumTipoA);
				}
	            
				if (enumTipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
					TipoHigieneYBelleza enumTipoHYB = TipoHigieneYBelleza.valueOf(categoria);
					p.setCategoria(enumTipoHYB);
				}
	            
				if (enumTipoProducto == TipoProducto.LIMPIEZA) {
					TipoLimpieza enumTipoL = TipoLimpieza.valueOf(categoria);
					p.setCategoria(enumTipoL);
				}

				p.setEstado(estado);
				p.setDescuento(descuento);

				productos.add(p);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Error en metodo listarProductos: " + e);
		}
		return productos;
	}
	
	public List<Producto> listarProductosConDescuento() {
		List<Producto> productosConDescuento = new ArrayList<>();

		List<Producto> todosLosProductos = listarProductos();
	    
		for (Producto producto : todosLosProductos) {
			if (producto.getDescuento() > 0) {
				productosConDescuento.add(producto);
			}
		}
		return productosConDescuento;
	}
	
	// Metodo que devuelve todos los productos de una categoria
	public List<Producto> listarProductosPorCategoria(String categoria) {
		List<Producto> productos = new ArrayList<>();
		String sql = "SELECT id, nombre, imagen, precio, cantidad, tipoProducto, categoria, estado, descuento "
				+ "FROM Producto WHERE categoria = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = capitalize(rs.getString("nombre"));
				String imagen = rs.getString("imagen");
				Double precio = rs.getDouble("precio");
				int cantidad = rs.getInt("cantidad");
				String tipoProducto = rs.getString("tipoProducto");
				String cat = rs.getString("categoria");
				String estadoStr = rs.getString("estado");
				Estado estado = Estado.valueOf(estadoStr);
				int descuento = rs.getInt("descuento");

				Producto p = new Producto();
				p.setId(id);
				p.setNombre(nombre);
				p.setImagen(imagen);
				p.setPrecio(precio);
				p.setCantidad(cantidad);

				TipoProducto enumTipoProducto = TipoProducto.valueOf(tipoProducto);
				p.setTipoProducto(enumTipoProducto);

				if (enumTipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento enumTipoA = TipoAlimento.valueOf(cat);
					p.setCategoria(enumTipoA);
				}
	            
				if (enumTipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
					TipoHigieneYBelleza enumTipoHYB = TipoHigieneYBelleza.valueOf(cat);
					p.setCategoria(enumTipoHYB);
				}
	            
				if (enumTipoProducto == TipoProducto.LIMPIEZA) {
					TipoLimpieza enumTipoL = TipoLimpieza.valueOf(cat);
					p.setCategoria(enumTipoL);
				}

				p.setEstado(estado);
				p.setDescuento(descuento);

				productos.add(p);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Error en metodo listarProductosPorCategoria: " + e);
		}
		return productos;
	}
	
	// Metodo que busca un alimento en la BD
	public Producto buscarProducto(String nomb) {
		Producto p = new Producto();
        String sql = "SELECT * FROM producto WHERE nombre = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){
	        ps.setString(1, nomb);
            ResultSet rs = ps.executeQuery();        
            while (rs.next()) {
            	int id = rs.getInt("id");
            	String nombre = capitalize(rs.getString("nombre"));
				String imagen = rs.getString("imagen");
				Double precio = rs.getDouble("precio");
				int cantidad = rs.getInt("cantidad");
				String tipoProducto = rs.getString("tipoProducto");
				String categoria = rs.getString("categoria");
				String estadoStr = rs.getString("estado");
				Estado estado = Estado.valueOf(estadoStr);
				int descuento = rs.getInt("descuento");
				p.setId(id);
				p.setNombre(nombre);
				p.setImagen(imagen);
				p.setPrecio(precio);
				p.setCantidad(cantidad);

				TipoProducto enumTipoProducto = TipoProducto.valueOf(tipoProducto);
				p.setTipoProducto(enumTipoProducto);

				if (enumTipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento enumTipoA = TipoAlimento.valueOf(categoria);
					p.setCategoria(enumTipoA);
				}
	            
				if (enumTipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
					TipoHigieneYBelleza enumTipoHYB = TipoHigieneYBelleza.valueOf(categoria);
					p.setCategoria(enumTipoHYB);
				}
	            
				if (enumTipoProducto == TipoProducto.LIMPIEZA) {
					TipoLimpieza enumTipoL = TipoLimpieza.valueOf(categoria);
					p.setCategoria(enumTipoL);
				}

				p.setEstado(estado);
				p.setDescuento(descuento);
            }
            rs.close();
            ps.close();
         }
	     catch (Exception e)  {
	    	 logger.log(Level.WARNING, "Error en buscarAlimento(nombre): " + e);
	     } 
		 return p;
	}
	
	// Metodo que devuelve todos los productos de un tipo
	public List<Producto> listarProductosPorTipo(TipoProducto tipoProducto) {
		List<Producto> productos = new ArrayList<>();
		String sql = "SELECT id, nombre, imagen, precio, cantidad, tipoProducto, categoria, estado, descuento "
				+ "FROM Producto WHERE tipoProducto = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, tipoProducto.toString());
            ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = capitalize(rs.getString("nombre"));
				String imagen = rs.getString("imagen");
				Double precio = rs.getDouble("precio");
				int cantidad = rs.getInt("cantidad");
				String tipo = rs.getString("tipoProducto");
				String cat = rs.getString("categoria");
				String estadoStr = rs.getString("estado");
				Estado estado = Estado.valueOf(estadoStr);
				int descuento = rs.getInt("descuento");

				Producto p = new Producto();
				p.setId(id);
				p.setNombre(nombre);
				p.setImagen(imagen);
				p.setPrecio(precio);
				p.setCantidad(cantidad);

				TipoProducto enumTipoProducto = TipoProducto.valueOf(tipo);
				p.setTipoProducto(enumTipoProducto);

				if (enumTipoProducto == TipoProducto.ALIMENTO) {
					TipoAlimento enumTipoA = TipoAlimento.valueOf(cat);
					p.setCategoria(enumTipoA);
				}
	            
				if (enumTipoProducto == TipoProducto.HIGIENE_Y_BELLEZA) {
					TipoHigieneYBelleza enumTipoHYB = TipoHigieneYBelleza.valueOf(cat);
					p.setCategoria(enumTipoHYB);
				}
	            
				if (enumTipoProducto == TipoProducto.LIMPIEZA) {
					TipoLimpieza enumTipoL = TipoLimpieza.valueOf(cat);
					p.setCategoria(enumTipoL);
				}

				p.setEstado(estado);
				p.setDescuento(descuento);

				productos.add(p);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Error en metodo listarProductosPorTipo: " + e);
		}
		return productos;
	}
	
	//Metodo que al realizar una compra, reste la cantidad al stock de un producto
	public void realizarCompra(String nombre, int cantidad) {
		String sql = "UPDATE producto SET cantidad = ? WHERE nombre = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)){
	      ps.setInt(1, cantidad);
	      ps.setString(2, nombre);
	      ps.executeUpdate();
	      ps.close();
	    } catch (SQLException e) {
	    	logger.log(Level.WARNING, "Error en metodo realizarCompra: " + e);
	    }
	}
	
	//Metodo que crea una compra pasandole el nombre de Usuario que hace la compra y el total de esta
	public void crearCompra(String usuario, double total) {
		String sql = "INSERT INTO compra (usuario, fecha, total) VALUES (?, ?, ?);";
	    try (PreparedStatement ps = conn.prepareStatement(sql)){
	    	ps.setString(1, usuario);
	      ps.setDate(2, new Date(System.currentTimeMillis()));
	      ps.setDouble(3, total);
	      ps.executeUpdate();
	      ps.close();
	    } catch (SQLException e) {
	    	logger.log(Level.WARNING, "Error en metodo crearCompra: " + e);
	    }
	}
}
