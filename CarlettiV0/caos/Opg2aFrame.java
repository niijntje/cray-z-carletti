/**
 * CAOS - Opg. 2a
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JSeparator;

import model.Delbehandling.DelbehandlingsType;
import model.Mellemvare;

import service.Service;
import javax.swing.JCheckBox;

/**
 * @author Rita Holst Jacobsen
 *
 */
public class Opg2aFrame extends JFrame {
	private JTable tableMellemOgDrageringsvarer;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel panel_1;
	private JTable tableTidspunkter;
	private JScrollPane scrollPane_1;
	private JLabel lblFejlbehandlinger;
	private Controller controller;
	private DefaultTableModel dmTidspunkter;
	private Object[][] resultsTidspunkter;
	private String[] columnsTidspunkter;
	private String[] columnsMellemOgDrageringsvarer;
	private Object[][] resultsMellemOgDrageringsvarer;
	private DefaultTableModel dmMellemOgDrageringsvarer;
	private JButton btnStartRead;
	private JButton btnStartWrite;
	private Opg2a opg2a;
	private JCheckBox chckbxSamtidighedskontrol;

	public Opg2aFrame(Opg2a opg2a) throws SQLException {
		this.setTitle("Samtidighedskontrol (Opg. 2a)");
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 714);

		this.opg2a = opg2a;
		this.controller = new Controller();
		panel = new JPanel();

		JLabel lblMellemvarer = new JLabel("Mellem- og drageringsvarer");
		lblMellemvarer.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

		textArea = new JTextArea();

		textArea.setText("Programmet skal køre en transaktion, der ved påbegyndelse af ny delbehandling\n" +
				"markerer en eventuel tidligere delbehandling som afsluttet, og opretter den nye\n" +
				"delbehandling. Der skal være samtidighedskontrol, så transaktionen virker, når\n" +
				"flere udgaver af programmet kører på samme tid. I skal teste, at samtidighedskontrollen\n" +
				"fungerer og I skal i rapporten kort forklare hvorledes samtidighedstesten er lavet.");

		JLabel lblOpgavea = new JLabel("Opgave 2a");

		panel_1 = new JPanel();

		lblFejlbehandlinger = new JLabel("Tidspunkter-tabellen");
		lblFejlbehandlinger.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.RED);

		btnStartRead = new JButton("Start read");
		btnStartRead.addActionListener(controller);
		btnStartWrite = new JButton("Start write");
		btnStartWrite.addActionListener(controller);

		chckbxSamtidighedskontrol = new JCheckBox("Samtidighedskontrol");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(307)
						.addComponent(lblOpgavea)
						.addContainerGap(317, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(53)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(54, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(261)
										.addComponent(lblFejlbehandlinger, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
										.addGap(297))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(232)
												.addComponent(lblMellemvarer)
												.addContainerGap(401, Short.MAX_VALUE))
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(23)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addGap(177)
																		.addComponent(btnStartRead)
																		.addGap(28)
																		.addComponent(btnStartWrite)
																		.addGap(58)
																		.addComponent(chckbxSamtidighedskontrol))
																		.addComponent(separator, GroupLayout.PREFERRED_SIZE, 637, GroupLayout.PREFERRED_SIZE))
																		.addContainerGap(63, Short.MAX_VALUE))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addContainerGap()
																				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 670, GroupLayout.PREFERRED_SIZE)
																				.addContainerGap(178, Short.MAX_VALUE))
																				.addGroup(groupLayout.createSequentialGroup()
																						.addGap(14)
																						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
																						.addGap(40))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblOpgavea)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblMellemvarer)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxSamtidighedskontrol)
								.addComponent(btnStartRead)
								.addComponent(btnStartWrite))
								.addGap(18)
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(lblFejlbehandlinger)
								.addGap(18)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(12, Short.MAX_VALUE))
				);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//-------------------------------------Tidspunkter-------------------------------------//
		columnsTidspunkter = new String[] {"Bakke#", "Tidspunkt"};
		resultsTidspunkter = this.showTidspunkter();
		dmTidspunkter = new DefaultTableModel(resultsTidspunkter, columnsTidspunkter);
		tableTidspunkter = new JTable(dmTidspunkter);
		//--------------------------------------------------------------------------------------//	
		
		scrollPane_1.setViewportView(tableTidspunkter);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(14)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
						.addContainerGap())
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(16, Short.MAX_VALUE))
				);
		panel_1.setLayout(gl_panel_1);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().setLayout(groupLayout);

		//------------------------------Mellem- og drageringsvarer------------------------------//
		columnsMellemOgDrageringsvarer = new String[] {"Bakke#", "Produkttype","Delbehandling", "Næste"};
		resultsMellemOgDrageringsvarer = this.showMellemOgDrageringsvareData();
		dmMellemOgDrageringsvarer = new DefaultTableModel(resultsMellemOgDrageringsvarer, columnsMellemOgDrageringsvarer);
		tableMellemOgDrageringsvarer = new JTable(dmMellemOgDrageringsvarer);
		//--------------------------------------------------------------------------------------//		

		scrollPane.setViewportView(tableMellemOgDrageringsvarer);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(198, Short.MAX_VALUE))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
						.addContainerGap(8, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE))
				);
		panel.setLayout(gl_panel);

	}

	public Object[][] showMellemOgDrageringsvareData() throws SQLException{
		String mellemvareQuery = "select M.BAKKESTREGKODE as Bakke#, M.PRODUKTTYPE_NAVN as Produkttype, D.NAVN as Delbehandling, " +
				"NEXTD.NAVN as Naeste "+
				"FROM MELLEMVARE M " +
				"INNER JOIN DELBEHANDLING D ON M.IGANGVAERENDEDELBEHANDLING_ID=D.ID " +
				"LEFT JOIN DELBEHANDLING NEXTD ON D.NEXTDELBEHANDLING_ID=NEXTD.ID "+
				"GROUP BY M.BAKKESTREGKODE, D.BEHANDLING_NAVN, D.MAXVARIGHED, M.PRODUKTTYPE_NAVN, D.NAVN, NEXTD.NAVN " +
				"order by Bakke#";

		ResultSet res = ConnectionHandler.getInstance().getSQLResult(mellemvareQuery);
		ArrayList<Object[]> resultArrays = new ArrayList<Object[]>();


		while (res.next()){
			Object[] resultLine = new Object[4];
			resultLine[0] = res.getString("BAKKE#");
			resultLine[1] = res.getString("Produkttype");
			resultLine[2] = res.getString("Delbehandling");
			resultLine[3] = res.getString("Naeste");
			resultArrays.add(resultLine);

		}

		Object[][] results = new Object[resultArrays.size()][4];
		for (int i = 0; i < resultArrays.size(); i++){
			results[i] = resultArrays.get(i);
		}

		ConnectionHandler.getInstance().closeConnection();
		return results;
	}

	public Object[][] showTidspunkter() throws SQLException{
		String fejlbehandlingsQuery = "select * from tidspunkter order by tidspunkter DESC";

		Object[][] results = new Object[0][0];	//<--Initieres, så der findes noget at returnere selvom det mislykkes at hente fra databasen.

		try {
			ResultSet res = ConnectionHandler.getInstance().getSQLResult(fejlbehandlingsQuery);
			ArrayList<Object[]> resultArrays = new ArrayList<Object[]>();

			while (res.next()){
				Object[] resultLine = new Object[2];
				resultLine[0] = res.getString("Mellemvare_BAKKESTREGKODE");
				resultLine[1] = res.getString("TIDSPUNKTER");
				resultArrays.add(resultLine);
			}

			results = new Object[resultArrays.size()][2];
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
			if (tableMellemOgDrageringsvarer.getSelectedRowCount()>0){
				int row = tableMellemOgDrageringsvarer.getSelectedRow();
				String bakkestregkode = (String) tableMellemOgDrageringsvarer.getValueAt(row, 0);

				if (e.getSource()==btnStartRead){
					if (!chckbxSamtidighedskontrol.isSelected()) {
						try {
							opg2a.sendMellemvareTilDrageringA(bakkestregkode);
						}
						catch (SQLException e1) {
							System.out.println(bakkestregkode + ": Handling mislykkedes");
							e1.printStackTrace();
						}
					}
					else {
						try {
							opg2a.sendMellemvareTilDrageringMedSamtidighedskontrolA(bakkestregkode);
						}
						catch (SQLException e1) {
							System.out.println(bakkestregkode + ": Handling mislykkedes (samtidighedskontrol)");
							e1.printStackTrace();
						}

					}
				}
				else if (e.getSource()==btnStartWrite){
					if (!chckbxSamtidighedskontrol.isSelected()) {
	               try {
		               opg2a.sendMellemvareTilDrageringB(true);
	               }
						catch (SQLException e1) {
							System.out.println(bakkestregkode + ": Handling mislykkedes");
							e1.printStackTrace();
	               }
               }
					else {
						try {
							opg2a.sendMellemvareTilDrageringMedSamtidighedskontrolB();
						}
						catch (SQLException e1) {
							System.out.println(bakkestregkode + ": Handling mislykkedes (samtidighedskontrol)");
							e1.printStackTrace();
						}

					}

					try {
						dmMellemOgDrageringsvarer.setDataVector(showMellemOgDrageringsvareData(), columnsMellemOgDrageringsvarer);
					}
					catch (SQLException e1) {
						System.out.println("Mellem- og drageringsvare-tabel ikke opdateret");
						e1.printStackTrace();
					}
					try {
						dmTidspunkter.setDataVector(showTidspunkter(), columnsTidspunkter);
					}
					catch (SQLException e1) {
						System.out.println("Tidspunkter-tabel ikke opdateret");
						e1.printStackTrace();
					}
				}
			}
		}


	}
}
