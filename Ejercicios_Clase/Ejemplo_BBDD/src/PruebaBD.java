import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaBD {
	public static void main(String[] args) {
		
		// Carga del Driver de SQLite
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
		}
		///////
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:datos/PruebaBBDD.bd");
			
			// Consultas, busquedas, etc
			
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Programa Terminado");
	}
}
