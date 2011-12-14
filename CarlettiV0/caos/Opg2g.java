/**
 * CAOS - Opg. 2g
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;

import model.Delbehandling.DelbehandlingsType;
import model.Mellemvare;

import service.Service;

/**
 * @author Rita Holst Jacobsen
 *
 */
public class Opg2g extends JFrame {
	private JTable tableMellemvarelager;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnOpdater;
	private JPanel panel_1;
	private JTable tableFejlbehandlinger;
	private JScrollPane scrollPane_1;
	private JLabel lblFejlbehandlinger;
	private Controller controller;
	private DefaultTableModel dmFejlbehandlinger;
	private Object[][] resultsFejlbehandlinger;
	private String[] columnsFejlbehandlinger;
	private String[] columnsMellemvarelager;
	private Object[][] resultsMellemvarelager;
	private DefaultTableModel dmMellemvareLager;

	public Opg2g() throws SQLException {
		this.setTitle("Triggers og fejlbehandlinger (Opg. 2g)");
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 714);

		this.controller = new Controller();

		panel = new JPanel();

		JLabel lblMellemvarer = new JLabel("Mellemvarer");
		lblMellemvarer.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

		textArea = new JTextArea();

		textArea.setText("Opret en trigger, der hver gang en mellemvare flyttes fra mellemvarelageret \n" +
				"checker om maksimumstørretiden er overskredet og i dette tilfælde skriver en note om det \n" +
				"i en særlig fejltabel (en tabel I selv opretter til dette formål). \n" +
				"Aftest denne.");

		JLabel lblOpgavea = new JLabel("Opgave 2g");

		btnOpdater = new JButton("Opdater");
		btnOpdater.addActionListener(controller);

		panel_1 = new JPanel();

		lblFejlbehandlinger = new JLabel("Fejlbehandlinger");
		lblFejlbehandlinger.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.RED);
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
												.addGap(12)
												.addComponent(panel, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(265)
														.addComponent(btnOpdater)
														.addContainerGap(270, Short.MAX_VALUE))
														.addGroup(groupLayout.createSequentialGroup()
																.addContainerGap()
																.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
																.addGap(3))
																.addGroup(groupLayout.createSequentialGroup()
																		.addGap(276)
																		.addComponent(lblFejlbehandlinger)
																		.addContainerGap(282, Short.MAX_VALUE))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addGap(23)
																				.addComponent(separator, GroupLayout.PREFERRED_SIZE, 637, GroupLayout.PREFERRED_SIZE)
																				.addContainerGap(30, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblOpgavea)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblMellemvarer)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnOpdater)
						.addGap(18)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(5)
						.addComponent(lblFejlbehandlinger)
						.addGap(18)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		columnsFejlbehandlinger = new String[] {"Bakke#", "Delbehandling", "Timer","Minutter", "Fejltekst"};
		resultsFejlbehandlinger = this.showFejlbehandlinger();
		dmFejlbehandlinger = new DefaultTableModel(resultsFejlbehandlinger, columnsFejlbehandlinger);

		tableFejlbehandlinger = new JTable(dmFejlbehandlinger);
		scrollPane_1.setViewportView(tableFejlbehandlinger);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(14)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 648, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(19, Short.MAX_VALUE))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(59, Short.MAX_VALUE))
				);
		panel_1.setLayout(gl_panel_1);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().setLayout(groupLayout);

		columnsMellemvarelager = new String[] {"Bakke#", "Produkttype","Delbehandling","Dage","Timer","Minutter"};
		resultsMellemvarelager = this.showMellemvareData();
		dmMellemvareLager = new DefaultTableModel(resultsMellemvarelager, columnsMellemvarelager);

		tableMellemvarelager = new JTable(dmMellemvareLager);
		scrollPane.setViewportView(tableMellemvarelager);
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

	public Object[][] showFejlbehandlinger() throws SQLException{
		String fejlbehandlingsQuery = "select * from fejlBehandlinger";

		Object[][] results = new Object[0][0];	//<--Initieres, så der findes noget at returnere selvom det mislykkes at hente fra databasen.
		
      try {
	      ResultSet res = ConnectionHandler.getInstance().getSQLResult(fejlbehandlingsQuery);
	      ArrayList<Object[]> resultArrays = new ArrayList<Object[]>();

	      while (res.next()){
	      	Object[] resultLine = new Object[5];
	      	resultLine[0] = res.getString("bakkestregkode");
	      	resultLine[1] = res.getString("delbehandling");
	      	resultLine[2] = res.getString("tidOverskredetTimer");
	      	resultLine[3] = res.getString("tidOverskredetMinutter");
	      	resultLine[4] = res.getString("fejlTekst");
	      	resultArrays.add(resultLine);
	      }

	      results = new Object[resultArrays.size()][5];
	      for (int i = 0; i < resultArrays.size(); i++){
	      	results[i] = resultArrays.get(i);
	      }

	      ConnectionHandler.getInstance().closeConnection();
      }
      catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
		return results;
	}

	private class Controller implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnOpdater){
				String stregkode = "";
				if (tableMellemvarelager.getSelectedRowCount()>0){
					stregkode = (String) tableMellemvarelager.getValueAt(tableMellemvarelager.getSelectedRow(), 0);
					System.out.println(stregkode);
				}
//				Mellemvare m = Service.getInstance().soegMellemvare(stregkode);
//				if (m!=null){
//					try {
//						Service.getInstance().sendTilNaesteDelbehandling(m, null, DelbehandlingsType.DRAGERING, null, null);
//					}
//					catch (Exception e2) {
//						System.out.println("'Send til næste delbehandling' mislykkedes!");
//						e2.printStackTrace();
//					}
//				}
//				else {
//					System.out.println("Der findes ikke en mellemvare i databasen med den valgte bakkestregkode");
//				}
				try {
					dmMellemvareLager.setDataVector(showMellemvareData(), columnsMellemvarelager);
				}
				catch (SQLException e1) {
					System.out.println("Mellemvaretabel ikke opdateret!!");
					e1.printStackTrace();
				}
				try {
					dmFejlbehandlinger.setDataVector(showFejlbehandlinger(), columnsFejlbehandlinger);
				}
				catch (SQLException e1) {
					System.out.println("Fejlbehandlingstabel ikke opdateret!!");
					e1.printStackTrace();
				}

			}

		}

	}
}
