package es.deusto.prog3.examen202401ord.datos;

public class PreguntaRespuesta {
	private String pregunta;
	private String respuesta;
	public PreguntaRespuesta() {
		super();
	}
	public PreguntaRespuesta(String pregunta, String respuesta) {
		super();
		this.pregunta = pregunta;
		this.respuesta = respuesta;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	@Override
	public String toString() {
		return "PreguntaRespuesta [pregunta=" + pregunta + ", respuesta=" + respuesta + "]";
	}
	
	
}
