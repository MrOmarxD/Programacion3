package examen.parc202211.data;

import java.io.Serializable;
import java.util.Date;

/** Clase de datos de cada foto sacada en el sistema DeustoBeReal
 */
public class Foto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private Usuario usuario;
	private Categoria categoria;
	private long fecha;
	private String rutaFoto;

	/** Constructor de foto
	 * @param codigo	Código (único) de foto
	 * @param usuario	Usuario que hace la foto
	 * @param categoria	Categoría en la que se publica la foto
	 * @param fecha	Fecha de obtención de la foto
	 * @param rutaFoto	Ruta de la foto del producto (compatible con el servicio de persistencia)
	 */
	public Foto(int codigo, Usuario usuario, Categoria categoria, long fecha, String rutaFoto) {
		this.codigo = codigo;
		this.usuario = usuario;
		this.categoria = categoria;
		this.fecha = fecha;
		this.rutaFoto = rutaFoto;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public long getFecha() {
		return fecha;
	}
	/** Devuelve la fecha de la foto en un objeto Date
	 */
	public Date getFechaEnDate() {
		return new Date( fecha );
	}
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	public String getRutaFoto() {
		return rutaFoto;
	}
	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Foto) {
			return ((Foto)obj).codigo == codigo;  // Igualdad a igualdad de identificador único
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "F" + codigo + " de " + usuario + " en " + categoria;
	}
	
}
