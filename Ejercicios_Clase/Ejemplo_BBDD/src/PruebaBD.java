import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaBD {
	public static void main(String[] args) {
		
		// Carga del Driver de SQLite
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
		}
		///////
		Float valor = 5.0f;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:datos/PruebaBBDD.bd");
			Statement st = conn.createStatement();
			
			// Consultas, busquedas, etc
			/*ResultSet rs = st.executeQuery("SELECT * FROM usuario Where cartera > " + valor);
			while (rs.next()) {
				//Se puede hacer - int id = rs.getInt(1); (Empieza por 1, no por 0)
				
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				Float cartera = rs.getFloat("cartera");
				System.out.format("%d - %s %s, %.2f€%n", id, nombre, apellido, cartera);
			}
			rs.close();*/
			PreparedStatement ps1 =  conn.prepareStatement("SELECT * FROM usuario Where cartera > ?");
			ps1.setFloat(1, valor);
			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {				
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				Float cartera = rs.getFloat("cartera");
				System.out.format("%d - %s %s, %.2f€%n", id, nombre, apellido, cartera);
			}
			rs.close();
			
			///////
			//Insertar filas en las tablas.
			
			/*String nombre = "nombre 1";
			String apellido = "apellido 1";
			float cartera = 20.34f;
			
			String insertSQL = "INSERT INTO Usuario (nombre, apellido, cartera) VALUES (?, ?, ?)";
			PreparedStatement ps =  conn.prepareStatement(insertSQL);
			ps.setString(1, nombre);
			ps.setString(2, apellido);
			ps.setFloat(3, cartera);
			int rows = ps.executeUpdate();
			System.out.println("Se ha actualizado - " + rows + " filas");*/
			
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// INSERT INTO Usuario (id, nombre, apellido, cartera) VALUES(3, "Luis", "Rojo", 20.3)
		
		System.out.println("Programa Terminado");
	}
}
