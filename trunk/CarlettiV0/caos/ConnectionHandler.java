package caos;

/**
 * 
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author nijntje
 *
 */
public class ConnectionHandler {
	
	private Connection myConnection;
	private static ConnectionHandler uniqueInstance;
	
	private ConnectionHandler(){

	}
	
	public static ConnectionHandler getInstance(){
		if (uniqueInstance == null){
			uniqueInstance = new ConnectionHandler();
		}
		return uniqueInstance;
	}
	
	public Connection getConnection(){
		Connection myConnection = null;
		try {
			// connection to MSSQL
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
//					"jdbc:jtds:sqlserver://192.168.1.106/master", "sa", "hemmeligtPassword");

//			"jdbc:jtds:sqlserver://10.77.44.115/master", "sa", "hemmeligtPassword");
//      "jdbc:jtds:sqlserver://10.77.43.184/CarlettiLageringssytem", "sa", "hemmeligtPassword");
//          "jdbc:jtds:sqlserver://192.168.1.106/CarlettiLageringssytem", "sa", "hemmeligtPassword");

			"jdbc:jtds:sqlserver://10.211.55.3/CarlettiLageringssystem", "sa", "01");

			

		} catch (Exception e) {
			System.out.println("Error connecting to database:  "
					+ e.getMessage());
		}
		return myConnection;
	}
	
	public ResultSet getSQLResult(String sqlQuery){
		
		ResultSet resultSet = null;
		try {
			// connection to MSSQL
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
			"jdbc:jtds:sqlserver://10.211.55.3/CarlettiLageringssystem", "sa", "01");
//			"jdbc:jtds:sqlserver://192.168.1.106/master", "sa", "hemmeligtPassword");

			Statement stmt = myConnection.createStatement();

			resultSet = stmt.executeQuery(sqlQuery);
			
			

		} catch (Exception e) {
			System.out.println("Error connecting to database:  "
					+ e.getMessage());
		}

		return resultSet;
	}
	
	public void closeConnection() throws SQLException{
		myConnection.close();
	}
	
	

}
