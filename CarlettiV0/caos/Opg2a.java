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
 * @author nijntje
 *
 */
public class Opg2a {
	
	public Opg2a(){
		
	}
	
	public void sendMellemvareTilDragering(Mellemvare m) throws SQLException{
		String bakkestregkode = m.getBakkestregkode();
		
		Connection con = ConnectionHandler.getInstance().getConnection();
		Statement stmt = con.createStatement();
		ResultSet igangvaerendeDelbehandlingRS = stmt.executeQuery("select M.IGANGVAERENDEDELBEHANDLING_ID as DelbehandlingsID from MELLEMVARE M where M.BAKKESTREGKODE = "+bakkestregkode);
		igangvaerendeDelbehandlingRS.first();
		String igangvaerendeDelbehandlingID = igangvaerendeDelbehandlingRS.getString("DelbehandlingsID");
		ResultSet naesteDelbehandlingRS = stmt.executeQuery("select D.NEXTDELBEHANDLING_ID as NextID from DELBEHANDLING D where D.ID="+igangvaerendeDelbehandlingID);
		String naesteDelbehandlingID = igangvaerendeDelbehandlingRS.getString("NextID");
		stmt.executeUpdate("update MELLEMVARE M set IGANGVAERENDEDELBEHANDLING ="+naesteDelbehandlingID+" where M.BAKKESTREGKODE="+bakkestregkode);
		stmt.executeUpdate("insert into tidspunkter getdate()");

	}

}
