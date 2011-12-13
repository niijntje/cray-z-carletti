package caos;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;

/**
 * 
 * @author Rasmus Cederdorff
 * 
 */
public class Opg2c extends JFrame {

	private JPanel contentPane;
	private JTextField txtMintrretid;
	private JTextField txtMaxtrretid;
	private JList list;

	public Opg2c() {
		setTitle("Samlede t\u00F8rretider");
		setBounds(100, 100, 420, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		new ArrayList<String>();

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblMellevarer = new JLabel("Mellevarer (bakkestrekode):");

		JLabel lblSamletMintrretid = new JLabel("Samlet minT\u00F8rretid:");
		lblSamletMintrretid.setFont(new Font("Lucida Grande", Font.BOLD, 11));

		txtMintrretid = new JTextField();
		txtMintrretid.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtMintrretid.setEditable(false);
		txtMintrretid.setText("minT\u00F8rretid");
		txtMintrretid.setColumns(10);

		JLabel lblSamletMaxtrretid = new JLabel("Samlet maxT\u00F8rretid:");
		lblSamletMaxtrretid.setFont(new Font("Lucida Grande", Font.BOLD, 11));

		txtMaxtrretid = new JTextField();
		txtMaxtrretid.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtMaxtrretid.setEditable(false);
		txtMaxtrretid.setText("maxT\u00F8rretid");
		txtMaxtrretid.setColumns(10);

		JTextPane txtpnVlgEnMellemvare = new JTextPane();
		txtpnVlgEnMellemvare.setBackground(Color.WHITE);
		txtpnVlgEnMellemvare.setEditable(false);
		txtpnVlgEnMellemvare
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		txtpnVlgEnMellemvare
				.setText("V\u00E6lg en mellemvare p\u00E5 listen for at se de samlede t\u00F8rretider, mellemvaren befinder sig p\u00E5 mellevarelageret");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				scrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				182,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								txtpnVlgEnMellemvare,
																								GroupLayout.PREFERRED_SIZE,
																								205,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGap(6)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																lblSamletMintrretid)
																														.addComponent(
																																lblSamletMaxtrretid)
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addGap(6)
																																		.addComponent(
																																				txtMaxtrretid))
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addGap(6)
																																		.addComponent(
																																				txtMintrretid)))
																										.addGap(73))))
														.addComponent(
																lblMellevarer))
										.addGap(15)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblMellevarer)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(12)
																		.addComponent(
																				txtpnVlgEnMellemvare,
																				GroupLayout.PREFERRED_SIZE,
																				51,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				lblSamletMintrretid)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				txtMintrretid,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(12)
																		.addComponent(
																				lblSamletMaxtrretid)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				txtMaxtrretid,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				scrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				225,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(15, Short.MAX_VALUE)));

		list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {

				samledeVarigheder(list.getSelectedValue().toString());
			}
		});
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
		list.setListData(getAlleMellemvarer().toArray());
	}

	/**
	 * Metoden opretter forbindelse til databasen og trækker data ud -
	 * bakkestregkode fra Mellemvare-tabellen Data returneres i en ArrayList
	 * 
	 * @return
	 */
	public ArrayList<String> getAlleMellemvarer() {
		ArrayList<String> listdata = new ArrayList<String>();
		 ResultSet res = ConnectionHandler.getInstance().getSQLResult("select bakkestregkode from Mellemvare");

			try {
	         while (res.next()) {
	         	listdata.add(res.getString("BAKKESTREGKODE"));
	         }
	         
	      	ConnectionHandler.getInstance().closeConnection();
         }
	
         catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
         }
		
		return listdata;
	}

	/**
	 * Metoden opretter forbindelse til databasen. Samlet mintørretid og
	 * maxtørretid beregnes på den givne mellemvare tekstfelterne txtMintrrtid
	 * og txtMaxtrretid opdateres
	 * 
	 * @param bakkestregkode
	 */
	public void samledeVarigheder(String bakkestregkode) {
		try {
			String sqlQuery = "select SUM(d.MINVARIGHED) as SamletMinVarighed, SUM(d.MAXVARIGHED) as SamletMaxVarighed "
					+ "from MELLEMVARE m, PRODUKTTYPE p, BEHANDLING b, BEHANDLING_DELBEHANDLING bd, DELBEHANDLING d "
					+ "where m.BAKKESTREGKODE = "
					+ bakkestregkode
					+ ""
					+ "and m.PRODUKTTYPE_NAVN = p.NAVN "
					+ "and p.BEHANDLING_NAVN = b.NAVN "
					+ "and b.NAVN = bd.Behandling_NAVN "
					+ "and bd.delbehandlinger_ID = d.ID "
					+ "and d.DELBEHANDLINGSTYPE = 'TOERRING'";
			ResultSet res = ConnectionHandler.getInstance().getSQLResult(sqlQuery);

			while (res.next()) {
				int sumMin = res.getInt(1);
				int sumMax = res.getInt(2);
				txtMintrretid.setText(millisekunderTildato(sumMin));
				txtMaxtrretid.setText(millisekunderTildato(sumMax));

			}
		} catch (Exception e) {
			System.out.println("error:  " + e.getMessage());
		}

	}

	/**
	 * Denne metode omskriver millisekunder til dage, timer og minutter
	 * og returnerer dette. 
	 * @param tid
	 * @return
	 */
	public static String millisekunderTildato(long tid) {
		int dage;
		int timer;
		int minutter;

		dage = (int) tid / 86400000;
		timer = (int) (tid - (86400000 * dage)) / 3600000;
		minutter = (int) (tid - 86400000 * dage - 3600000 * timer) / 60000;

		return dage + "d " + timer + "t " + minutter + "m";
	}
}
