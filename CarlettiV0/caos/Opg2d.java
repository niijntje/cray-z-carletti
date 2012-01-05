/**
 * CAOS - Opg. 2d
 */
package caos;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JLabel;

/**
 * 
 * @author Mads Dahl Jensen
 *
 */
public class Opg2d extends JFrame {
	private JTextField textField;
	private String[] headers = { "Bakkestregkode", "Igangv¾r. delbehand.", "Pallestregkode", "Produkttype" };
	private String[][] data;

	public Opg2d() {

		getContentPane().setLayout(null);
		setSize(400, 300);

		textField = new JTextField();
		textField.setBounds(156, 93, 116, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int colCount;
					int rowCount = 0;
					String[] headersTemp;

					// Class.forName("net.sourceforge.jtds.jdbc.Driver");
					// Connection connection = DriverManager
					// .getConnection(
					// "jdbc:jtds:sqlserver://Mads-Pc/CarlettiLageringssytem",
					// "ADMIN", "test");

					Connection connection = ConnectionHandler.getInstance().getConnection();
					Statement stmt = connection.createStatement();

					String temp = textField.getText();
					ResultSet res = stmt.executeQuery("SELECT m.BAKKESTREGKODE, m.IGANGVAERENDEDELBEHANDLING_ID, "
					      + "m.PALLE_STREGKODE, m.PRODUKTTYPE_NAVN " + "FROM MELLEMVARE m " + "WHERE m.PRODUKTTYPE_NAVN ='" + temp
					      + "'");
					Statement stmt2 = connection.createStatement();
					ResultSet res2 = stmt2.executeQuery("SELECT m.BAKKESTREGKODE,"
					      + " m.IGANGVAERENDEDELBEHANDLING_ID, m.PALLE_STREGKODE," + " m.PRODUKTTYPE_NAVN " + "FROM MELLEMVARE m "
					      + "WHERE m.PRODUKTTYPE_NAVN ='" + temp + "'");
					ResultSetMetaData meta = res.getMetaData();
					while (res2.next()) {
						rowCount++;
					}
					colCount = meta.getColumnCount();

					String[][] dataTemp = new String[rowCount + 1][colCount + 1];

					int i = 0;
					while (res.next()) {
						dataTemp[i][0] = res.getString(1);
						dataTemp[i][1] = res.getString(2);
						dataTemp[i][2] = res.getString(3);
						dataTemp[i][3] = res.getString(4);
						i++;

					}
					data = dataTemp;

					if (res != null)
						res.close();
					if (stmt != null)
						stmt.close();
					if (res2 != null)
						res2.close();
					if (stmt2 != null)
						stmt2.close();
					if (connection != null)
						connection.close();

					MellemVareAfPrrodukttype m = new MellemVareAfPrrodukttype(data, headers);
					m.setVisible(true);
				}
				catch (SQLException err) {
					err.printStackTrace();
				}

			}
		});
		btnOk.setBounds(166, 128, 97, 25);
		getContentPane().add(btnOk);

		JLabel lblNewLabel = new JLabel("Skriv navnet p\u00E5 den \u00F8nskede produkttype");
		lblNewLabel.setBounds(100, 48, 273, 16);
		getContentPane().add(lblNewLabel);

	}
}
