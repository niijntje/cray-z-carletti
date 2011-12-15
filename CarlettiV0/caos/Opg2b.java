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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Rita Holst Jacobsen
 *
 */
public class Opg2b extends JFrame {
	private JTable table;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnOpdater;
	private ActionListener controller;
	private DefaultTableModel dm;
	private Object[][] results;
	private String[] columns;

	public Opg2b() throws SQLException {
		this.setTitle("Mellemvarer (Opg. 2b)");
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 714);

		this.controller = new Controller();
		panel = new JPanel();
		
		JLabel lblMellemvarer = new JLabel("Mellemvarer");
		lblMellemvarer.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		textArea = new JTextArea();
		
		textArea.setText("Programmet skal kunne udskrive en liste over mellemvarerne på mellemvarelageret.\n" +
				"Listen skal indeholde en oplysning om hvor tæt mellemvaren er på at overskride \nmaksimumtørretiden.\n" +
				"Listen skal endvidere være sorteret efter denne oplysning, så de varer, der er tættest\n" +
				"på maksimumtørretiden kommer først.\n" +
				"I skal udføre så meget som muligt af beregningerne i SQL.");
		
		JLabel lblOpgavea = new JLabel("Opgave 2b");
		
		btnOpdater = new JButton("Opdater");
		btnOpdater.addActionListener(controller);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(307)
					.addComponent(lblOpgavea)
					.addContainerGap(316, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(54, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(290)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnOpdater)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblMellemvarer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(303))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblOpgavea)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblMellemvarer)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnOpdater)
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE))
		);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().setLayout(groupLayout);
		
		columns = new String[] {"Bakke#", "Produkttype","Delbehandling","Dage","Timer","Minutter"};
		results = this.showMellemvareData();
		dm = new DefaultTableModel(results, columns);
		
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
		String mellemvareQuery = 
				"select M.BAKKESTREGKODE as Bakke#, M.PRODUKTTYPE_NAVN as Produkttype, D.NAVN as Delbehandling, " +
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

		while (res.next()){
			Object[] resultLine = new Object[6];
			resultLine[0] = res.getString("BAKKE#");
			resultLine[1] = res.getString("Produkttype");
			resultLine[2] = res.getString("Delbehandling");
			resultLine[3] = res.getString("days");
			resultLine[4] = res.getString("hours");
			resultLine[5] = res.getString("minutes");
			resultArrays.add(resultLine);
		}

		Object[][] results = new Object[resultArrays.size()][6];
		for (int i = 0; i < resultArrays.size(); i++){
			results[i] = resultArrays.get(i);
		}
		
		ConnectionHandler.getInstance().closeConnection();
				return results;
	}
	
	private class Controller implements ActionListener {

		@Override
      public void actionPerformed(ActionEvent e) {
	      if (e.getSource()==btnOpdater){
	      	try {
	            dm.setDataVector(showMellemvareData(), columns);
            }
            catch (SQLException e1) {
	            e1.printStackTrace();
            }
	      }
	      
      }
		
	}
}
