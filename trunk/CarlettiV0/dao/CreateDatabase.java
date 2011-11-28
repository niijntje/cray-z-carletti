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
	public static void main(String[] args) {
		try {
			// connection to MSSQL
			Connection myConnection;
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.211.55.3/master", "sa", "01");

			Statement stmt = myConnection.createStatement();
			stmt.executeUpdate("DROP DATABASE CarlettiLageringssytem");
			stmt.executeUpdate("CREATE DATABASE CarlettiLageringssytem");

			System.out.println("Database recreated!");

		} catch (Exception e) {
			System.out.println("Error connecting to database:  "
					+ e.getMessage());
		}
	}
}
