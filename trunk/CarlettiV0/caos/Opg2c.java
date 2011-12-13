package caos;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;

/**
 * 
 * @author Rasmus Cederdorff
 * 
 */
public class Opg2c extends JFrame {

	private JPanel contentPane;
	private JTextField txtMintrretid;
	private JTextField txtMaxtrretid;
	private JTable table;
	private DefaultTableModel tableModel;
	private Object[][] data;
	private JLabel lblValgteMellemvare;
	private JLabel lblvalgtmellemvare;

	public Opg2c() {
		setTitle("Samlede t\u00F8rretider");
		setBounds(100, 100, 436, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		new ArrayList<String>();

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblMellevarer = new JLabel("Mellevarer");

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
		txtpnVlgEnMellemvare.setBackground(Color.PINK);
		txtpnVlgEnMellemvare.setEditable(false);
		txtpnVlgEnMellemvare.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		txtpnVlgEnMellemvare
		      .setText("Klik p\u00E5 en mellemvare i tabellen for at se de samlede t\u00F8rretider, mellemvaren befinder sig p\u00E5 mellevarelageret");

		lblValgteMellemvare = new JLabel("Valgte mellemvare:");
		lblValgteMellemvare.setFont(new Font("Lucida Grande", Font.BOLD, 11));

		lblvalgtmellemvare = new JLabel("");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
		      gl_contentPane
		            .createSequentialGroup()
		            .addContainerGap()
		            .addGroup(
		                  gl_contentPane
		                        .createParallelGroup(Alignment.LEADING)
		                        .addGroup(
		                              gl_contentPane
		                                    .createSequentialGroup()
		                                    .addGroup(
		                                          gl_contentPane
		                                                .createParallelGroup(Alignment.LEADING)
		                                                .addComponent(txtpnVlgEnMellemvare, GroupLayout.PREFERRED_SIZE, 205,
		                                                      GroupLayout.PREFERRED_SIZE)
		                                                .addGroup(
		                                                      gl_contentPane.createSequentialGroup()
		                                                            .addComponent(lblValgteMellemvare).addGap(18)
		                                                            .addComponent(lblvalgtmellemvare)))
		                                    .addGap(47)
		                                    .addGroup(
		                                          gl_contentPane.createParallelGroup(Alignment.LEADING)
		                                                .addComponent(txtMaxtrretid, 120, 120, 120)
		                                                .addComponent(lblSamletMaxtrretid)
		                                                .addComponent(txtMintrretid, 120, 120, 120)
		                                                .addComponent(lblSamletMintrretid))).addComponent(lblMellevarer)
		                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 413, GroupLayout.PREFERRED_SIZE))
		            .addContainerGap(7, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
		      gl_contentPane
		            .createSequentialGroup()
		            .addContainerGap()
		            .addComponent(lblMellevarer)
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
		            .addGroup(
		                  gl_contentPane
		                        .createParallelGroup(Alignment.LEADING, false)
		                        .addGroup(
		                              gl_contentPane
		                                    .createSequentialGroup()
		                                    .addGap(18)
		                                    .addComponent(lblSamletMintrretid)
		                                    .addPreferredGap(ComponentPlacement.RELATED)
		                                    .addComponent(txtMintrretid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
		                                          GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
		                                    .addComponent(lblSamletMaxtrretid))
		                        .addGroup(gl_contentPane.createSequentialGroup().addGap(18).addComponent(txtpnVlgEnMellemvare)))
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addGroup(
		                  gl_contentPane
		                        .createParallelGroup(Alignment.BASELINE)
		                        .addComponent(txtMaxtrretid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
		                              GroupLayout.PREFERRED_SIZE).addComponent(lblValgteMellemvare)
		                        .addComponent(lblvalgtmellemvare)).addGap(19)));

		data = getAlleMellemvarer();
		String[] columnNames = { "Bakkestregkode", "Produkttype", "Status" };
		tableModel = new DefaultTableModel(data, columnNames);
		table = new JTable(tableModel);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				String bakkestregkode = (String) table.getModel().getValueAt(row, 0);
				if (bakkestregkode != null) {
					samledeVarigheder(bakkestregkode);
					lblvalgtmellemvare.setText(bakkestregkode);
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Metoden opretter forbindelse til databasen og tr¾kker data ud - bakkestregkode fra Mellemvare-tabellen Data returneres i en
	 * ArrayList
	 * 
	 * @return
	 */
	public Object[][] getAlleMellemvarer() {
		ResultSet res = ConnectionHandler.getInstance().getSQLResult("select * from Mellemvare");
		ArrayList<Object[]> resultArrays = new ArrayList<Object[]>();
		Object[][] results = null;
		try {
			while (res.next()) {
				Object[] resultLine = new Object[3];
				resultLine[0] = res.getString("BAKKESTREGKODE");
				resultLine[1] = res.getString(6);
				resultLine[2] = res.getString(2);
				resultArrays.add(resultLine);

			}
			results = new Object[resultArrays.size()][6];
			for (int i = 0; i < resultArrays.size(); i++) {
				results[i] = resultArrays.get(i);
			}
			ConnectionHandler.getInstance().closeConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * Metoden opretter forbindelse til databasen. Samlet mint¿rretid og maxt¿rretid beregnes pŒ den givne mellemvare tekstfelterne
	 * txtMintrrtid og txtMaxtrretid opdateres
	 * 
	 * @param bakkestregkode
	 */
	public void samledeVarigheder(String bakkestregkode) {
		try {
			String sqlQuery = "select SUM(d.MINVARIGHED) as SamletMinVarighed, SUM(d.MAXVARIGHED) as SamletMaxVarighed "
			      + "from MELLEMVARE m, PRODUKTTYPE p, BEHANDLING b, BEHANDLING_DELBEHANDLING bd, DELBEHANDLING d "
			      + "where m.BAKKESTREGKODE = " + bakkestregkode + "" + "and m.PRODUKTTYPE_NAVN = p.NAVN "
			      + "and p.BEHANDLING_NAVN = b.NAVN " + "and b.NAVN = bd.Behandling_NAVN " + "and bd.delbehandlinger_ID = d.ID "
			      + "and d.DELBEHANDLINGSTYPE = 'TOERRING'";
			ResultSet res = ConnectionHandler.getInstance().getSQLResult(sqlQuery);

			while (res.next()) {
				int sumMin = res.getInt(1);
				int sumMax = res.getInt(2);
				txtMintrretid.setText(millisekunderTildato(sumMin));
				txtMaxtrretid.setText(millisekunderTildato(sumMax));

			}
		}
		catch (Exception e) {
			System.out.println("error:  " + e.getMessage());
		}

	}

	/**
	 * Denne metode omskriver millisekunder til dage, timer og minutter og returnerer dette.
	 * 
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
