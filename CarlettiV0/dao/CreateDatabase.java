package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 
 * @author Cederdorff
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
					"jdbc:jtds:sqlserver://10.211.55.3/master", "sa", "01");

			Statement stmt = myConnection.createStatement();
			// stmt.executeUpdate("DROP DATABASE CarlettiLageringssytem"); //
			// udkommenteringen kan fjernes, hvis man ¿nsker at fjerne og
			// oprette en ny database
			stmt.executeUpdate("CREATE DATABASE CarlettiLageringssytem");

			System.out.println("Database recreated!");

		} catch (Exception e) {
			System.out.println("Error connecting to database:  "
					+ e.getMessage());
		}
	}
}
