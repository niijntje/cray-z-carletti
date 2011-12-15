/**
 * CAOS - Opg. 2a
 */
package caos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Mellemvare;



/**
 * @author Rita Holst Jacobsen
 *
 */
public class Opg2a {

	private String igangvaerendeDelbehandlingID;
	private String naesteDelbehandlingID;
	private String bakkestregkode;

	public Opg2a(){

	}

	public void sendMellemvareTilDrageringA(String bakkestregkode) throws SQLException{
		this.bakkestregkode = bakkestregkode;
		ResultSet igangvaerendeDelbehandlingRS = ConnectionHandler.getInstance().getSQLResult(
				"select M.IGANGVAERENDEDELBEHANDLING_ID as DelbehandlingsID from MELLEMVARE M where M.BAKKESTREGKODE = "+bakkestregkode);
		igangvaerendeDelbehandlingID = "";
		while (igangvaerendeDelbehandlingRS.next()){
			igangvaerendeDelbehandlingID = igangvaerendeDelbehandlingRS.getString(1);
		}
		ResultSet naesteDelbehandlingRS = ConnectionHandler.getInstance().getSQLResult(
				"select D.NEXTDELBEHANDLING_ID as NextID from DELBEHANDLING D where D.ID="+igangvaerendeDelbehandlingID);
		naesteDelbehandlingID = "";
		while (naesteDelbehandlingRS.next()){
			naesteDelbehandlingID = naesteDelbehandlingRS.getString(1);
		}
	}

	public void sendMellemvareTilDrageringB(boolean closeWhenDone) throws SQLException{
		try {
			Statement stmt = ConnectionHandler.getInstance().getConnection().createStatement();
			stmt.executeUpdate("update MELLEMVARE set IGANGVAERENDEDELBEHANDLING_ID ="
					+naesteDelbehandlingID+" where BAKKESTREGKODE="+bakkestregkode);
			stmt.executeUpdate("insert into tidspunkter (Mellemvare_BAKKESTREGKODE, TIDSPUNKTER) values ('"+bakkestregkode+"', GETDATE())");
			if (naesteDelbehandlingID==null){
				stmt.executeUpdate("update MELLEMVARE set STATUS = 'FAERDIG' where BAKKESTREGKODE='"+bakkestregkode+"'");

			}
			if (closeWhenDone){
				ConnectionHandler.getInstance().closeConnection();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			ConnectionHandler.getInstance().closeConnection();
			System.out.println("Connection closed prematurely");
		}
	}

	public void sendMellemvareTilDrageringMedSamtidighedskontrolA(String bakkestregkode2) throws SQLException{
		try {
			ConnectionHandler.getInstance().getConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			ConnectionHandler.getInstance().getConnection().setAutoCommit(false);
			sendMellemvareTilDrageringA(bakkestregkode2);
		}
		catch (SQLException e){
			ConnectionHandler.getInstance().getConnection().rollback();
			System.out.println("A med samtidighedskontrol rolled back");
		}
		//NB: Her lukkes vores Connection IKKE!
	}
	public void sendMellemvareTilDrageringMedSamtidighedskontrolB() throws SQLException{
		try {
			sendMellemvareTilDrageringB(false);
			ConnectionHandler.getInstance().getConnection().commit();
		}
		catch (SQLException e){
			System.out.println("B med samtidighedskontrol mislykket");
			System.out.println(e.getMessage());
		}
		try {
			ConnectionHandler.getInstance().getConnection().close();
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}


}
