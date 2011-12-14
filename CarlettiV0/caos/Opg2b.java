/**
 * CAOS - Opg. 2b
 */
package caos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * @author Rita Holst Jacobsen
 *
 */
public class Opg2b extends JFrame {
	private JTable table;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea;

	public Opg2b() throws SQLException {
		this.setTitle("Mellemvarer (Opg. 2b)");
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 714);

		panel = new JPanel();
		
		JLabel lblMellemvarer = new JLabel("Mellemvarer");
		lblMellemvarer.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		textArea = new JTextArea();
		
		textArea.setText("Programmet skal kunne udskrive en liste over mellemvarerne paÃä mellemvarelageret.\n" +
				"Listen skal indeholde en oplysning om hvor t√¶t mellemvaren er paÃä at overskride \nmaksimumt√∏rretiden.\n" +
				"Listen skal endvidere v√¶re sorteret efter denne oplysning, saÃä de varer, der er t√¶ttest\n" +
				"paÃä maksimumt√∏rretiden kommer f√∏rst.\n" +
				"I skal udf√∏re saÃä meget som muligt af beregningerne i SQL.");
		
		JLabel lblOpgavea = new JLabel("Opgave 2b");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(307)
					.addComponent(lblOpgavea)
					.addContainerGap(316, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(54, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(290)
					.addComponent(lblMellemvarer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(303))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblOpgavea)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMellemvarer)
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE))
		);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().setLayout(groupLayout);
		
		String[] columns = {"Bakke#", "Produkttype","Delbehandling","Dage","Timer","Minutter"};
		Object[][] results = this.showMellemvareData();
		DefaultTableModel dm = new DefaultTableModel(results, columns);
		
		table = new JTable(dm);
		scrollPane.setViewportView(table);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
					.addGap(20))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(315, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

	}
	
	public Object[][] showMellemvareData() throws SQLException{
		String mellemvareQuery = "select M.BAKKESTREGKODE as Bakke#, M.PRODUKTTYPE_NAVN as Produkttype, D.NAVN as Delbehandling, " +
				"datediff(minute, getdate(), DATEADD (MILLISECOND, D.MAXVARIGHED, MAX(CAST (T.tidspunkter AS DATETIME))))/(60*24) as days, " +
				"datediff(minute, getdate(), DATEADD (MILLISECOND, D.MAXVARIGHED, MAX(CAST (T.tidspunkter AS DATETIME))))%(60*24)/60 as hours," +
				"datediff(minute, getdate(), DATEADD (MILLISECOND, D.MAXVARIGHED, MAX(CAST (T.tidspunkter AS DATETIME))))%(60*24)%60 as minutes " +
				"FROM tidspunkter T " +
				"INNER JOIN MELLEMVARE M ON M.BAKKESTREGKODE = T.Mellemvare_BAKKESTREGKODE " +
				"INNER JOIN DELBEHANDLING D ON M.IGANGVAERENDEDELBEHANDLING_ID=D.ID " +
				"WHERE D.DELBEHANDLINGSTYPE = 'TOERRING' " +
				"GROUP BY M.BAKKESTREGKODE, D.BEHANDLING_NAVN, D.MAXVARIGHED, M.PRODUKTTYPE_NAVN,D.NAVN " +
				"order by days, hours, minutes";
		
		ResultSet res = ConnectionHandler.getInstance().getSQLResult(mellemvareQuery);
		ArrayList<Object[]> resultArrays = new ArrayList<Object[]>();

//		String mellemvareInfo = "Bakke#\tProdukttype\t\tDelbehandling\t\tDage\tTimer\tMinutter\n\n";

		while (res.next()){
			Object[] resultLine = new Object[6];
			resultLine[0] = res.getString("BAKKE#");
			resultLine[1] = res.getString("Produkttype");
			resultLine[2] = res.getString("Delbehandling");
			resultLine[3] = res.getString("days");
			resultLine[4] = res.getString("hours");
			resultLine[5] = res.getString("minutes");
			resultArrays.add(resultLine);

//			String mellemvareLinje = "";
//			mellemvareLinje += res.getString("BAKKE#") + "\t";
//			mellemvareLinje += res.getString("Produkttype") + "\t\t";
//			mellemvareLinje += res.getString("Delbehandling") + "\t\t";
//			mellemvareLinje += res.getString("days") + "\t";
//			mellemvareLinje += res.getString("hours") + "\t";
//			mellemvareLinje += res.getString("minutes") + "\t";
//			mellemvareInfo += mellemvareLinje +"\n";
		}

		Object[][] results = new Object[resultArrays.size()][6];
		for (int i = 0; i < resultArrays.size(); i++){
			results[i] = resultArrays.get(i);
		}
//		System.out.println(mellemvareInfo);
		
		ConnectionHandler.getInstance().closeConnection();
				return results;
	}
}
