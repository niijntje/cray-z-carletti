/**
 * CREATEDATABASE
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 
 * @author Rasmus Cederdorff
 * 
 */
public class CreateDatabase {
	/**
	 * Denne metode opretter en database Det er vigtigt at der gives de rigtige
	 * data med: - hvor driveren kan hentes til at kommunikere med databasen -
	 * (IP-)adressen hvor databasen skal ligge - brugernavn - adgangskode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// connection to MSSQL
			Connection myConnection;
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
			"jdbc:jtds:sqlserver://10.77.44.212/master", "sa", "hemmeligtPassword");
//			"jdbc:jtds:sqlserver://192.168.1.106/master", "sa", "hemmeligtPassword");

			Statement stmt = myConnection.createStatement();

//			 stmt.executeUpdate("DROP DATABASE CarlettiLageringssytem"); //
			 stmt.executeUpdate("DROP DATABASE CarlettiLageringssystem"); //
			// udkommenteringen kan fjernes, hvis man ¿nsker at fjerne og
			// oprette en ny database
			stmt.executeUpdate("CREATE DATABASE CarlettiLageringssystem");


		} catch (Exception e) {
			System.out.println("Error connecting to database:  "
					+ e.getMessage());
		}
	}
}
