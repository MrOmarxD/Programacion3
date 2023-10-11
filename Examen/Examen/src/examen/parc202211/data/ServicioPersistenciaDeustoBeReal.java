package examen.parc202211.data;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/** Comportamiento generalizado de servicio de persistencia del sistema Deusto BeReal
 * Podrá ser implementado con ficheros, con bases de datos, o de cualquier otro modo
 */
public interface ServicioPersistenciaDeustoBeReal {
	
	/** Inicializa el servicio de persistencia, abriendo las conexiones y recursos necesarios
	 * @param nombrePersistencia	Nombre interno de respaldo de almacenamiento de datos
	 * @param rutaFotos	Ruta de carpeta donde se almacenan las fotos del servicio
	 * @param configPersistencia	Información de configuración (si procede), dependiente del tipo de gestor de persistencia
	 * @return	true si se inicia correctamente, false en caso contrario
	 */
	public boolean init( String nombrePersistencia, String rutaFotos, String configPersistencia );
	
	/** Inicializa el servicio de persistencia con datos de prueba, sin cargarlos del sistema, abriendo las conexiones y recursos necesarios
	 * @param nombrePersistencia	Nombre interno de respaldo de almacenamiento de datos  (no se usa para cargar)
	 * @param rutaFotos	Ruta de carpeta donde se almacenan las fotos del servicio
	 * @param configPersistencia	Información de configuración (si procede), dependiente del tipo de gestor de persistencia
	 * @return	true si se inicia correctamente, false en caso contrario
	 */
	public boolean initDatosTest( String nombrePersistencia, String rutaFotos, String configPersistencia );
	
	/** Cierra el servicio de persistencia y todas las conexiones y recursos que pudieran haber sido necesarias. A partir de este momento no se puede utilizar para recuperar datos
	 */
	public void close();

	/** Devuelve el número de usuarios almacenados en el servicio
	 * @return	Número de usuarios
	 */
	public int getNumeroUsuarios();

	/** Devuelve el número de fotos almacenadas en el servicio
	 * @return	Número de fotos
	 */
	public int getNumeroFotos();

	/** Devuelve el código mayor de foto almacenada en el servicio
	 * @return
	 */
	public int getMayorCodigoFoto();

	/** Busca un usuario dado su nick
	 * @param nick	Nick de usuario a buscar
	 * @return	Usuario con ese nick (exacto), null si no se encuentra
	 */
	public Usuario buscarUsuarioPorNick( String nick );

	/** Busca un grupo dado su código
	 * @param codigo	Código a buscar
	 * @return	Grupo con ese código, null si no se encuentra
	 */
	public Categoria buscarCategoriaPorCodigo( int codigo );

	/** Recupera todas las fotos de un usuario dado
	 * @param nickUsuario	Nick del usuario a buscar
	 * @return	Lista de todas las fotos del servicio de ese usuario (vacía si no hay ninguno)
	 */
	public List<Foto> buscarFotosDeUsuario( String nickUsuario );

	/** Busca fotos entre dos fechas dadas
	 * @param fecha1	Fecha-hora inicial, expresada en milisegundos transcurridos desde el 1/1/1970
	 * @param fecha2	Fecha-hora final, expresada en milisegundos transcurridos desde el 1/1/1970
	 * @return	Lista de todas las fotos cuya fecha está entre esas dos dadas (ambas inclusive)
	 */
	public List<Foto> buscarFotosEntreFechas( Date fecha1, Date fecha2 );
	
	/** Añade una nueva foto al servicio de persistencia. Si procede, modifica la ruta de fichero de la foto, de acuerdo al servicio de persistencia
	 * @param foto	Foto a añadir. Atención: el nombre de fichero debe ser un nombre de fichero único para cada foto
	 * @return	true si se ha añadido correctamente, false en caso contrario
	 */
	public boolean insertarFoto( Foto foto );

	/** Añade un nuevo usuario al servicio de persistencia
	 * @param usuario	Usuario a añadir
	 * @return	true si se ha añadido correctamente, false en caso contrario
	 */
	public boolean insertarUsuario( Usuario usuario );

	/** Añade un nuevo grupo al servicio de persistencia
	 * @param usuario	Grupo a añadir
	 * @return	true si se ha añadido correctamente, false en caso contrario
	 */
	public boolean insertarCategoria( Categoria grupo );
	
	/** Busca las categorías en las que ha publicado fotos un usuario dado
	 * @param nickUsuario	Nick de usuario del que buscar
	 * @return	Conjunto de todas las categorías en las que ese usuario haya publicado alguna foto (da igual cuántas: solo una vez cada categoría) 
	 */
	public Set<Categoria> buscarCategoriasDeUsuario( String nickUsuario );

	/** Modifica los datos de una foto existente. Si procede, modifica la ruta de fichero de la foto, de acuerdo al servicio de persistencia
	 * El fichero correspondiente a la foto debe ser de uno de los tres formatos: GIF, JPG, PNG
	 * @param foto	Foto a modificar (no se puede modificar el código). Atención: el nombre de fichero debe ser un nombre de fichero único para cada foto
	 * @return	true si se ha modificado correctamente, false en caso contrario
	 */
	public boolean actualizarFoto( Foto foto );

	/** Recupera todos los usuarios del sistema
	 * @return	Lista de todos los usuarios
	 */
	public List<Usuario> buscarUsuarios();

	/** Asigna un logger ya creado para que se haga log de las operaciones de actualización de datos. Si no se asigna, cada clase que implemente el interfaz definirá su propio logger por defecto
	 * @param logger	Logger ya creado
	 */
	public void setLogger( Logger logger );
	
}
