/**
 * CAOS - Opg. 2 - ConnectionHandler
 */
package caos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Rita Holst Jacobsen
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

	public Connection getConnection() throws SQLException{
		if (myConnection == null || myConnection.isClosed()){
			if (myConnection==null){
//				System.out.println("Opening NEW connection - null");	//<--Benyttet i opg. 2a til at sikre, at
																						//samme Connection blev brugt hele vejen igennem.
			}
			else if (myConnection.isClosed()){
//				System.out.println("Opening NEW connection - closed");//<--Som ovenfor
			}
			try {
				// connection to MSSQL
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				myConnection = DriverManager.getConnection(
						"jdbc:jtds:sqlserver://10.77.44.80/CarlettiLageringssytem", "sa", "hemmeligtPassword");
			} catch (Exception e) {
				System.out.println("Error connecting to database:  "
						+ e.getMessage());
				if (myConnection!=null){
					myConnection.close();
				}
			}
		}
		return myConnection;
	}

	public ResultSet getSQLResult(String sqlQuery) throws SQLException{
		ResultSet resultSet = null;
		Statement stmt = getConnection().createStatement();
		resultSet = stmt.executeQuery(sqlQuery);
		return resultSet;
	}

	public void closeConnection() throws SQLException{
		if (myConnection != null){
			myConnection.close();
		}
	}



}
