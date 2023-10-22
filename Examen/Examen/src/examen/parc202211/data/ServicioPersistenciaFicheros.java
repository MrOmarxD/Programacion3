package examen.parc202211.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Gestor de persistencia basado en memoria (mapas de usuarios, categorías y fotos) y guardado en fichero binario
 */
public class ServicioPersistenciaFicheros implements ServicioPersistenciaDeustoBeReal {
	
	private Logger logger;
	private TreeMap<String,Usuario> mapaUsuarios;
	private HashMap<Integer,Categoria> mapaCategorias;
	private HashMap<String,ArrayList<Foto>> mapaFotos;
	private int mayorCodigoFoto = -1;
	private String nombreFichero;
	private String rutaFotos;

	/** Crea un servicio de persistencia basado en memoria y guardado en fichero local
	 */
	public ServicioPersistenciaFicheros() {
	}
	
	@SuppressWarnings("unchecked")
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
		mapaUsuarios = new TreeMap<String,Usuario>();
		mapaCategorias = new HashMap<Integer,Categoria>();
		this.nombreFichero = nombrePersistencia;
		File fic = new File( nombreFichero );
		if (!fic.exists()) {
			return true;  // Se crea el sistema de persistencia pero no hay datos previos - se inicia correctamente aunque vacío
		}
		try {
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream( fic ) );
			mapaUsuarios = (TreeMap<String,Usuario>) ois.readObject();
			mapaCategorias = (HashMap<Integer,Categoria>) ois.readObject();
			mapaFotos = (HashMap<String,ArrayList<Foto>>) ois.readObject();
			mayorCodigoFoto = (int) ois.readObject();
			ois.close();
		} catch (Exception e) {
			log( Level.SEVERE, "No se ha podido cargar el fichero de persistencia " + nombrePersistencia, e );
			return false;
		}
		return true;
	}

	@Override
	public boolean initDatosTest(String nombrePersistencia, String rutaFotos, String configPersistencia) {
		this.rutaFotos = rutaFotos;
		try { // Crear carpeta si no existe
			File fic = new File(rutaFotos);
			if (!fic.exists()) {
				Files.createDirectory( (fic).toPath() );
			}
		} catch (IOException ex) {
			log( Level.SEVERE, "Ruta de fotos " + rutaFotos + " no se ha podido respaldar en el servicio de persistencia", ex );
		}
		this.nombreFichero = nombrePersistencia;
		mapaUsuarios = new TreeMap<String,Usuario>();
		Usuario usuario1 = new Usuario( 1, 8, "leidiNana" );
		Usuario usuario2 = new Usuario( 2, 10, "Ronaldus" );
		Usuario usuario3 = new Usuario( 3, 1, "Iway" );
		mapaUsuarios.put( usuario1.getNick(), usuario1 );
		mapaUsuarios.put( usuario2.getNick(), usuario2 );
		mapaUsuarios.put( usuario3.getNick(), usuario3 );

		mapaCategorias = new HashMap<Integer,Categoria>();
		Categoria cat1 = new Categoria( 1, "Jollibus" );
		Categoria cat2 = new Categoria( 2, "Bar Neveu" );
		Categoria cat3 = new Categoria( 3, "Tuich" );
		mapaCategorias.put( cat1.getCodigo(), cat1 );
		mapaCategorias.put( cat2.getCodigo(), cat2 );
		mapaCategorias.put( cat3.getCodigo(), cat3 );
		
		mapaFotos = new HashMap<>();
		insertarFoto( new Foto( 1, usuario1, cat1, 1667584129570L, "res/examen/parc202211/init/gaga1.jpg" ) );
		insertarFoto( new Foto( 4, usuario1, cat1, 1667587122356L, "res/examen/parc202211/init/gaga2.jpg" ) );
		insertarFoto( new Foto( 5, usuario1, cat2, 1667587800124L, "res/examen/parc202211/init/gaga3.jpg" ) );
		insertarFoto( new Foto( 2, usuario2, cat2, 1667585246284L, "res/examen/parc202211/init/ronaldo1.jpg" ) );
		insertarFoto( new Foto( 3, usuario2, cat1, 1667586034277L, "res/examen/parc202211/init/ronaldo2.jpg" ) );
		insertarFoto( new Foto( 6, usuario2, cat3, 1667588396722L, "res/examen/parc202211/init/ronaldo3.jpg" ) );
		insertarFoto( new Foto( 7, usuario2, cat3, 1667588745237L, "res/examen/parc202211/init/ronaldo4.jpg" ) );
		insertarFoto( new Foto( 8, usuario3, cat3, 1667589099523L, "res/examen/parc202211/init/ibai1.jpg" ) );
		insertarFoto( new Foto( 9, usuario3, cat3, 1667589481672L, "res/examen/parc202211/init/ibai2.jpg" ) );
		return true;
	}

	@Override
	public void close() {
		try {
			File fic = new File( nombreFichero );
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( fic ) );
			oos.writeObject( mapaUsuarios );
			oos.writeObject( mapaCategorias );
			oos.writeObject( mapaFotos );
			oos.writeObject( mayorCodigoFoto );
			oos.close();
		} catch (Exception e) {
			log( Level.SEVERE, "No se ha podido guardar el fichero de persistencia " + nombreFichero, e );
		}
	}

	@Override
	public int getNumeroUsuarios() {
		return mapaUsuarios.size();
	}

	@Override
	public int getNumeroFotos() {
		int numFotos = 0;
		for (ArrayList<Foto> lista : mapaFotos.values()) {
			numFotos += lista.size();
		}
		return numFotos;
	}
	
	@Override
	public int getMayorCodigoFoto() {
		return mayorCodigoFoto;
	}
	
	@Override
	public Usuario buscarUsuarioPorNick(String nick) {
		return mapaUsuarios.get( nick );
	}

	@Override
	public Categoria buscarCategoriaPorCodigo( int codigo ) {
		return mapaCategorias.get( codigo );
	}
	
	@Override
	public List<Foto> buscarFotosDeUsuario( String nickUsuario ) {
		List<Foto> ret = new ArrayList<Foto>();
		ArrayList<Foto> lFotos = mapaFotos.get( nickUsuario );
		if (lFotos != null) {
			ret.addAll( lFotos );  // Devolvemos una copia para que no se pueda modificar la lista de fotos interna
		}
		return ret;
	}

	@Override
	public List<Foto> buscarFotosEntreFechas( Date fecha1, Date fecha2 ) {
		List<Foto> ret = new ArrayList<Foto>();
		for (ArrayList<Foto> lFotos : mapaFotos.values()) {
			for (Foto foto : lFotos) {
				if (fecha1.getTime()<=foto.getFecha() && fecha2.getTime()>=foto.getFecha()) {
					ret.add( foto );
				}
			}
		}
		return ret;
	}

	
	@Override
	public boolean insertarUsuario( Usuario usuario ) {
		if (mapaUsuarios.get( usuario.getNick() )!=null) {
			log( Level.WARNING, "Inserción de usuario incorrecta (ya existe): " + usuario, null );
			return false;
		}
		mapaUsuarios.put( usuario.getNick(), usuario );
		log( Level.INFO, "Inserción de usuario correcta: " + usuario, null );
		return true;
	}

	@Override
	public boolean insertarCategoria( Categoria categoria ) {
		if (mapaCategorias.get( categoria.getCodigo() )!=null) {
			log( Level.WARNING, "Inserción de categoría incorrecta (ya existe): " + categoria, null );
			return false;
		}
		mapaCategorias.put( categoria.getCodigo(), categoria );
		log( Level.INFO, "Inserción de categoría correcta: " + categoria, null );
		return true;
	}
	
	@Override
	public Set<Categoria> buscarCategoriasDeUsuario( String nickUsuario ) {
		Set<Categoria> ret = new TreeSet<Categoria>();
		ArrayList<Foto> lFotos = mapaFotos.get( nickUsuario );
		if (lFotos != null) {  // Hay fotos: procesarlas
			for (Foto foto : lFotos) {
				ret.add( foto.getCategoria() );
			}
		}
		return ret;
	}
	
	@Override
	public boolean insertarFoto( Foto foto ) {
		mapaFotos.putIfAbsent( foto.getUsuario().getNick(), new ArrayList<Foto>() );
		if (mapaFotos.get( foto.getUsuario().getNick() ).contains( foto ) ) {
			log( Level.WARNING, "Inserción de foto incorrecta: " + foto, null );
			return false;
		}
		mapaFotos.get( foto.getUsuario().getNick() ).add( foto );
		if (mayorCodigoFoto < foto.getCodigo()) {
			mayorCodigoFoto = foto.getCodigo();
		}
		foto.getUsuario().setNumFotos( foto.getUsuario().getNumFotos() + 1 );
		foto.getCategoria().setNumFotos( foto.getCategoria().getNumFotos() + 1 );
		actualizaFotoSiProcede( foto );
		log( Level.INFO, "Inserción de foto correcta: " + foto, null );
		return true;
	}
	
		// Actualiza la foto cambiando la ruta si no está en la ruta de almacenamiento definida
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
	public boolean actualizarFoto( Foto foto ) {
		actualizaFotoSiProcede( foto );
		log( Level.INFO, "Actualización de foto correcta: " + foto, null );
		return true;
	}
	
	@Override
	public List<Usuario> buscarUsuarios() {
		List<Usuario> ret = new ArrayList<Usuario>();
		for (Usuario usuario : mapaUsuarios.values()) {
			ret.add( usuario );
		}
		return ret;
	}
	

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	// Método local para loggear (si no se asigna un logger externo, se asigna uno local)
	private void log( Level level, String msg, Throwable excepcion ) {
		if (logger==null) {  // Logger por defecto local:
			logger = Logger.getLogger( "persistencia-ficheros" );  // Nombre del logger
			logger.setLevel( Level.ALL );  // Loguea todos los niveles
			try {
				logger.addHandler( new FileHandler( "persistencia-ficheros-log.xml", true ) );  // Y saca el log a fichero xml
			} catch (Exception e) {
				logger.log( Level.SEVERE, "No se pudo crear fichero de log", e );
			}
		}
		if (excepcion==null)
			logger.log( level, msg );
		else
			logger.log( level, msg, excepcion );
	}

}
