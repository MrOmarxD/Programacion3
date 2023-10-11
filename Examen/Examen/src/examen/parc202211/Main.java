package examen.parc202211;

import java.io.File;
import javax.swing.JOptionPane;
import examen.parc202211.data.ServicioPersistenciaBD;
import examen.parc202211.data.ServicioPersistenciaFicheros;
import examen.parc202211.data.ServicioPersistenciaDeustoBeReal;
import examen.parc202211.gui.VentanaDeustoBeReal;

/** Clase principal de examen implementada con dos alternativas (seleccionables al ejecutar):
 *   1. Ficheros 
 *   2. Base de datos
 */
public class Main {

	public static void main(String[] args) {
		ServicioPersistenciaDeustoBeReal servicio = null;
		String[] modos = {
			"Memoria con ficheros",
			"Base de datos local"
		};
		String resp = (String) JOptionPane.showInputDialog( null, "Selecciona modo de gestión de persistencia", "Modo de trabajo", JOptionPane.QUESTION_MESSAGE, null, modos, "Memoria con ficheros" );
		
		if (resp.equals(modos[0])) {
			// Caso 1 - gestor de persistencia en memoria con guardado en ficheros
			servicio = new ServicioPersistenciaFicheros();
			servicio.init( "res/examen/parc202211/ejecucion/deustoBeReal.dat", "res/examen/parc202211/ejecucion", "" );
			if (servicio.getNumeroUsuarios() == 0) {
				servicio.initDatosTest( "res/examen/parc202211/ejecucion/deustoBeReal.dat", "res/examen/parc202211/ejecucion", "" );
			}
		} else if (resp.equals(modos[1])) {
			// Caso 2 - gestor de persistencia en base de datos local
			servicio = new ServicioPersistenciaBD();
			File fileBD = new File( "res/examen/parc202211/bd/deustoBeReal.db" );
			if (fileBD.exists()) {
				servicio.init( "res/examen/parc202211/bd/deustoBeReal.db", "res/examen/parc202211/ejecucion", "" );
			} else {
				servicio.initDatosTest( "res/examen/parc202211/bd/deustoBeReal.db", "res/examen/parc202211/ejecucion", "" );
			}
		}
		
		VentanaDeustoBeReal ventana = new VentanaDeustoBeReal( servicio );
		ventana.setVisible( true );
	}

}
