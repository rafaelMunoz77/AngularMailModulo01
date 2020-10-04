package angularmail.test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

public class Ejemplo01_ConexionBasica {

	/**
	 * 
	 */ 
	public static void main (String args[]) {
		try {
			// A través de la siguiente línea comprobamos si tenemos acceso al driver MySQL, si no fuera así
			// no podemos trabajar con esa BBDD.
			Class.forName("com.mysql.cj.jdbc.Driver"); // Driver Mysql 8. Si fuera versión 5 quitaríamos el paquete ".cj."
		   
			// Necesitamos obtener un acceso a la BBDD, eso se materializa en un objeto de tipo Connection, al cual
			// le tenemos que pasar los parámetros de conexión.
			Connection conexion = (Connection) DriverManager.getConnection ("jdbc:mysql://localhost/angularmail?serverTimezone=Europe/Madrid","angularmail", "1234");
		   
			// Para poder ejecutar una consulta necesitamos utilizar un objeto de tipo Statement
			Statement s = (Statement) conexion.createStatement(); 
			
			// La ejecución de la consulta se realiza a través del objeto Statement y se recibe en forma de objeto
			// de tipo ResultSet, que puede ser navegado para descubrir todos los registros obtenidos por la consulta
			ResultSet rs = s.executeQuery ("select * from usuario");
		   
			// Navegación del objeto ResultSet, cada campo se puede referenciar por su nombre o un número con la posición
			// que ocupa en la tupla, comenzando por el valor 1 (en este caso no comenzamos por el valor 0).
			while (rs.next()) { 
				System.out.println (rs.getInt("id") + " " + rs.getString (2)+ 
						" " + rs.getString(3) + " " + rs.getString(4) + 
						" " + rs.getString(5) + " " + rs.getString("fechaNacimiento")); 
			}
			// Cierre de los elementos
			rs.close();
			s.close();
			conexion.close();
		}
		catch (ClassNotFoundException ex) {
			System.out.println("Imposible acceder al driver Mysql");
			ex.printStackTrace();
		}
		catch (SQLException ex) {
			System.out.println("Error en la ejecución SQL: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
}
