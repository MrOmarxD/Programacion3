package examen.parc202211.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.*;

/** Gestor de persistencia basado en base de datos local
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class ServicioPersistenciaBD implements ServicioPersistenciaDeustoBeReal {

	private Logger logger = null;

	private Connection connection;
	private Statement statement;
	private static Exception lastError = null;  // Información de último error SQL ocurrido
	private String rutaFotos;

	// Para optimizar la carga de base de datos, según se van cargando usuarios y categorías se van guardando en memoria (no haría falta, podrían cargarse en cada operación)
	private TreeMap<String,Usuario> mapaUsuarios;
	private HashMap<Integer,Categoria> mapaCategorias;
	
	/** Crea un servicio de persistencia basado en BD local
	 */
	public ServicioPersistenciaBD() {
		mapaUsuarios = new TreeMap<>();
		mapaCategorias = new HashMap<>();
	}
	
	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombrePersistencia	Nombre de fichero de la base de datos
	 * @param configPersistencia	Configuración de conexión a base de datos (previa al nombre de base de datos) si procede
	 * @return	true si se inicia correctamente, false en caso contrario
	 */
	@Override
	public boolean init(String nombrePersistencia, String rutaFotos, String configPersistencia) {
		this.rutaFotos = rutaFotos;
		try { // Crear carpeta si no existe
			File fic = new File(rutaFotos);
			if (!fic.exists()) {
				Files.createDirectory( (fic).toPath() );
			}
		} catch (IOException ex) {
			log( Level.SEVERE, "Ruta de fotos " + rutaFotos + " no se ha podido respaldar en el servicio de persistencia", ex );
		}
		try {
		    Class.forName("org.sqlite.JDBC");
		    connection = DriverManager.getConnection("jdbc:sqlite:" + configPersistencia + nombrePersistencia );
			log( Level.INFO, "Conectada base de datos " + nombrePersistencia, null );
			statement = connection.createStatement();
			statement.setQueryTimeout( 10 );
			statement.executeUpdate("create table if not exists usuario (codigo integer, nivel integer, nick string, numFotos integer)");
			statement.executeUpdate("create table if not exists categoria (codigo integer, nombre string, numFotos integer)");
			statement.executeUpdate("create table if not exists foto (codigo integer, usuario string, categoria integer, fecha bigint, rutaFoto string)");
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			connection = null;
			log( Level.SEVERE, "Error en conexión de base de datos " + nombrePersistencia, e );
			return false;
		}
	}
	
	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombrePersistencia	Nombre de fichero de la base de datos
	 * @param configPersistencia	Configuración de conexión a base de datos (previa al nombre de base de datos) si procede
	 * @return	true si se inicia correctamente, false en caso contrario
	 */
	@Override
	public boolean initDatosTest(String nombrePersistencia, String rutaFotos, String configPersistencia) {
		boolean ret = init( nombrePersistencia, rutaFotos, configPersistencia );
		if (ret) {
			try {
				statement.executeUpdate("drop table usuario");
				statement.executeUpdate("create table usuario (codigo integer, nivel integer, nick string, numFotos integer)");
				statement.executeUpdate("drop table categoria");
				statement.executeUpdate("create table categoria (codigo integer, nombre string, numFotos integer)");
				statement.executeUpdate("drop table foto");
				statement.executeUpdate("create table foto (codigo integer, usuario string, categoria integer, fecha bigint, rutaFoto string)");
				Usuario usuario1 = new Usuario( 1, 8, "leidiNana" );
				Usuario usuario2 = new Usuario( 2, 10, "Ronaldus" );
				Usuario usuario3 = new Usuario( 3, 1, "Iway" );
				insertarUsuario( usuario1 );
				insertarUsuario( usuario2 );
				insertarUsuario( usuario3 );
				Categoria cat1 = new Categoria( 1, "Jollibus" );
				Categoria cat2 = new Categoria( 2, "Bar Neveu" );
				Categoria cat3 = new Categoria( 3, "Tuich" );
				insertarCategoria( cat1 );
				insertarCategoria( cat2 );
				insertarCategoria( cat3 );
				insertarFoto( new Foto( 1, usuario1, cat1, 1667584129570L, "res/examen/parc202211/init/gaga1.jpg" ) );
				insertarFoto( new Foto( 4, usuario1, cat1, 1667587122356L, "res/examen/parc202211/init/gaga2.jpg" ) );
				insertarFoto( new Foto( 5, usuario1, cat2, 1667587800124L, "res/examen/parc202211/init/gaga3.jpg" ) );
				insertarFoto( new Foto( 2, usuario2, cat2, 1667585246284L, "res/examen/parc202211/init/ronaldo1.jpg" ) );
				insertarFoto( new Foto( 3, usuario2, cat1, 1667586034277L, "res/examen/parc202211/init/ronaldo2.jpg" ) );
				insertarFoto( new Foto( 6, usuario2, cat3, 1667588396722L, "res/examen/parc202211/init/ronaldo3.jpg" ) );
				insertarFoto( new Foto( 7, usuario2, cat3, 1667588745237L, "res/examen/parc202211/init/ronaldo4.jpg" ) );
				insertarFoto( new Foto( 8, usuario3, cat3, 1667589099523L, "res/examen/parc202211/init/ibai1.jpg" ) );
				insertarFoto( new Foto( 9, usuario3, cat3, 1667589481672L, "res/examen/parc202211/init/ibai2.jpg" ) );
				log( Level.INFO, "Creada base de datos con datos de prueba " + nombrePersistencia, null );
			} catch (SQLException e) {
				lastError = e;
				connection = null;
				log( Level.SEVERE, "Error en manipulación de base de datos de test " + nombrePersistencia, e );
				return false;
			}
		}
		return ret;
	}

	@Override
	public void close() {
		try {
			if (statement!=null) statement.close();
			if (connection!=null) connection.close();
			log( Level.INFO, "Cierre de base de datos", null );
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en cierre de base de datos", e );
			e.printStackTrace();
		}
	}

	@Override
	public int getNumeroUsuarios() {
		try {
			String sql = "select count(*) from usuario";
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en manipulación de base de datos", e );
			return 0;
		}
	}

	@Override
	public int getNumeroFotos() {
		try {
			String sql = "select count(*) from foto";
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en manipulación de base de datos", e );
			return 0;
		}
	}
	
	@Override
	public int getMayorCodigoFoto() {
		int ret = -1;
		String sql = "select * from foto order by codigo desc limit 1";  // Consulta de fotos ordenada en descendente cogiendo solo el primero
		try {
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			if (rs.next()) { // Leer el resultset y poner el id
				ret = rs.getInt( "codigo" );
			}
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en búsqueda de base de datos: " + sql, e );
		}
		return ret;
	}
	
	@Override
	public Usuario buscarUsuarioPorNick(String nick) {
		String sql = "select * from usuario where nick = '" + nick + "'";
		try {
			// Ahorro de tiempo - si está en el mapa no se busca en bd
			if (mapaUsuarios.containsKey( nick )) {
				return mapaUsuarios.get( nick );
			}
			// Si no lo leemos de base de datos
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			if (rs.next()) {
				Usuario usuario = new Usuario(
					rs.getInt("codigo"), rs.getInt("nivel"), rs.getString("nick"), rs.getInt("numFotos") );
				rs.close();
				mapaUsuarios.put( usuario.getNick(), usuario );
				return usuario;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en búsqueda de base de datos: " + sql, e );
			return null;
		}
	}

	@Override
	public Categoria buscarCategoriaPorCodigo( int codigo ) {
		String sql = "select * from categoria where codigo = " + codigo + "";
		try {
			// Ahorro de tiempo - si está en el mapa no se busca en bd
			if (mapaCategorias.containsKey( codigo )) {
				return mapaCategorias.get( codigo );
			}
			// Si no lo leemos de base de datos
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			if (rs.next()) {
				Categoria cat = new Categoria(
					rs.getInt("codigo"), rs.getString("nombre"), rs.getInt("numFotos") );
				rs.close();
				mapaCategorias.put( cat.getCodigo(), cat );
				return cat;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en búsqueda de base de datos: " + sql, e );
			return null;
		}
	}
	
	@Override
	public List<Foto> buscarFotosDeUsuario( String nickUsuario ) {
		ArrayList<Foto> l = new ArrayList<>();
		String sql = "select * from foto where usuario = '" + nickUsuario + "'";
		try {
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				long fecha = rs.getLong("fecha");
				String ruta = rs.getString("rutaFoto");
				Usuario usuario = buscarUsuarioPorNick( nickUsuario );
				int codCat = rs.getInt("categoria");
				Categoria categoria = buscarCategoriaPorCodigo( codCat );
				Foto foto = new Foto( codigo, usuario, categoria, fecha, ruta );
				l.add( foto );
			}
			rs.close();
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en búsqueda de base de datos: " + sql, e );
		}
		return l;
	}

	@Override
	public List<Foto> buscarFotosEntreFechas( Date fecha1, Date fecha2 ) {
		ArrayList<Foto> l = new ArrayList<>();
		try {
			String sql = "select * from foto where fecha >= " + fecha1.getTime() + " and fecha <= " + fecha2.getTime();
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			while (rs.next()) {
				String nickUsuario = rs.getString("usuario");
				Usuario usuario = buscarUsuarioPorNick( nickUsuario );
				int codCategoria = rs.getInt("categoria");
				Categoria categoria = buscarCategoriaPorCodigo( codCategoria );
				Foto foto = new Foto( rs.getInt("codigo"), usuario, categoria, rs.getLong("fecha"), rs.getString("rutaFoto") );
				l.add( foto );
			}
			rs.close();
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en búsqueda de base de datos", e );
		}
		return l;
	}

	@Override
	public boolean insertarUsuario( Usuario usuario ) {
		if (mapaUsuarios.get( usuario.getNick() )!=null) {
			log( Level.WARNING, "Inserción de usuario incorrecta (ya existe): " + usuario, null );
			return true;
		}
		String sentSQL = "";
		try {
			sentSQL = "insert into usuario (codigo, nivel, nick, numFotos) values (" +
					usuario.getCodigo() + ", " +
					usuario.getNivel() + ", " +
					"'" + secu(usuario.getNick()) + "', " +
					usuario.getNumFotos() +
					")";
			log( Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null );
			int val = statement.executeUpdate( sentSQL );
			log( Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
				log( Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null );
				return false;  
			}
			mapaUsuarios.put( usuario.getNick(), usuario );
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e );
			lastError = e;
			return false;
		}
	}

	@Override
	public boolean insertarCategoria( Categoria categoria ) {
		if (mapaCategorias.get( categoria.getCodigo() )!=null) {
			log( Level.WARNING, "Inserción de categoría incorrecta (ya existe): " + categoria, null );
			return true;
		}
		String sentSQL = "";
		try {
			sentSQL = "insert into categoria (codigo, nombre, numFotos) values (" +
					categoria.getCodigo() + ", " +
					"'" + secu(categoria.getNombre()) + "', " +
					categoria.getNumFotos() +
					")";
			log( Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null );
			int val = statement.executeUpdate( sentSQL );
			log( Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
				log( Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null );
				return false;  
			}
			mapaCategorias.put( categoria.getCodigo(), categoria );
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e );
			lastError = e;
			return false;
		}
	}

	// T2: Buscar categorías de cada usuario
	// PROGRAMA ESTA TAREA ACCEDIENDO DIRECTAMENTE A LA BASE DE DATOS, SIN USAR NINGUN OTRO DE LOS MÉTODOS YA PROGRAMADOS
	// (Ten en cuenta que no se puede mantener más de un resultset abierto en cada statement: para hacer resultsets anidados hay que usar statements diferentes)
	@Override
	public Set<Categoria> buscarCategoriasDeUsuario( String nickUsuario ) {
		Set<Categoria> ret = new TreeSet<Categoria>();
		// T2
		return ret;
	}
	
	@Override
	public boolean insertarFoto(Foto foto) {
		String sentSQL = "";
		try {
			actualizaFotoSiProcede( foto );
			sentSQL = "insert into foto (codigo, usuario, categoria, fecha, rutaFoto) values (" +
					foto.getCodigo() + ", " +
					"'" + secu(foto.getUsuario().getNick()) + "', " +
					foto.getCategoria().getCodigo() + ", " +
					foto.getFecha() + ", " +
					"'" + secu(foto.getRutaFoto()) + "'" +
					")";
			log( Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null );
			int val = statement.executeUpdate( sentSQL );
			sentSQL = "update usuario set numFotos = numFotos+1 where nick = '" + foto.getUsuario().getNick() + "';";
			log( Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null );
			statement.executeUpdate( sentSQL );
			if (mapaUsuarios.containsKey( foto.getUsuario().getNick() )) {
				mapaUsuarios.get( foto.getUsuario().getNick() ).incNumFotos();
			}
			sentSQL = "update categoria set numFotos = numFotos+1 where codigo = " + foto.getCategoria().getCodigo() + ";";
			log( Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null );
			statement.executeUpdate( sentSQL );
			if (mapaCategorias.containsKey( foto.getCategoria().getCodigo() )) {
				mapaCategorias.get( foto.getCategoria().getCodigo() ).incNumFotos();
			}
			log( Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
				log( Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e );
			lastError = e;
			return false;
		}
	}

		private void actualizaFotoSiProcede( Foto foto ) {
			if (!foto.getRutaFoto().toLowerCase().startsWith( rutaFotos.toLowerCase() )) {
				// Si la foto está en otra ruta, se copia a la ruta de todas las fotos
				File fOrigen = new File( foto.getRutaFoto() );
				if (!fOrigen.exists()) {
					log( Level.SEVERE, "Fichero " + foto.getRutaFoto() + " no encontrado al intentar guardar foto " + foto, null );
					return;
				}
				try {
					String nuevoPath = rutaFotos + "/" + fOrigen.getName();
					Files.copy( fOrigen.toPath(), (new File( nuevoPath )).toPath(), StandardCopyOption.REPLACE_EXISTING );
					foto.setRutaFoto( nuevoPath );
				} catch (IOException e) {
					log( Level.SEVERE, "Fichero " + foto.getRutaFoto() + " no se ha podido respaldar en el servicio de persistencia para la foto " + foto, e );
				}
			}
		}
	
	@Override
	public List<Usuario> buscarUsuarios() {
		mapaUsuarios.clear();
		List<Usuario> l = new ArrayList<>();
		String sql = "select * from usuario";
		try {
			log( Level.INFO, "Lanzada consulta a base de datos: " + sql, null );
			ResultSet rs = statement.executeQuery( sql );
			while (rs.next()) {
				Usuario usuario = new Usuario( rs.getInt("codigo"), rs.getInt("nivel"), rs.getString("nick"), rs.getInt("numFotos") );
				mapaUsuarios.put( usuario.getNick(), usuario );
				l.add( usuario );
			}
			rs.close();
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en búsqueda de base de datos: " + sql, e );
		}
		return l;
	}
	
	@Override
	public boolean actualizarFoto(Foto foto) {
		actualizaFotoSiProcede( foto );
		log( Level.INFO, "Actualización de foto correcta: " + foto, null );
		return true;
	}
		
	@Override
	public void setLogger( Logger logger ) {
		this.logger = logger;
	}

	
	// Métodos privados

	// Devuelve el string "securizado" para volcarlo en SQL:
	// Mantiene solo los caracteres seguros en español y sustituye ' por ''
	private static String secu( String string ) {
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZñÑáéíóúüÁÉÍÓÚÚ.,:;-_(){}[]-+*=<>'\"¿?¡!&%$@#/\\0123456789 ".indexOf(c)>=0) ret.append(c);
		}
		return ret.toString().replaceAll( "'", "''" );
	}	

	// Logging
	
	// Método local para loggear (si no se asigna un logger externo, se asigna uno local)
	private void log( Level level, String msg, Throwable excepcion ) {
		if (logger==null) {  // Logger por defecto local:
			logger = Logger.getLogger( "BD-local" );  // Nombre del logger
			logger.setLevel( Level.ALL );  // Loguea todos los niveles
			try {
				logger.addHandler( new FileHandler( "bd.log.xml", true ) );  // Y saca el log a fichero xml
			} catch (Exception e) {
				logger.log( Level.SEVERE, "No se pudo crear fichero de log", e );
			}
		}
		if (excepcion==null)
			logger.log( level, msg );
		else
			logger.log( level, msg, excepcion );
	}

	/** Devuelve la información de excepción del último error producido por cualquiera 
	 * de los métodos de gestión de base de datos
	 */
	public Exception getLastError() {
		return lastError;
	}

}
