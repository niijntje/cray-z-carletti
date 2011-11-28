/**
 * 
 */
package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import model.Mellemvare;
import model.Palle;

import service.Service;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextArea;

/**
 * @author nijntje
 * 
 */
public class SubFramePalleOversigt extends JFrame implements Observer {

	private Palle palle;
	private MainFrame mainFrame;
	private JPanel panel1, panel2, panel3;
	private JTable table;
	private JLabel label;
	private JPanel panel;
	private JList list;
	private Controller controller = new Controller();
	private JTextArea txtrDetaljer;

	public SubFramePalleOversigt(MainFrame mainFrame, Palle palle) {
		getContentPane().setBackground(Color.PINK);
		this.mainFrame = mainFrame;
		this.palle = palle;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Oversigt over palle");
		this.setLocation(400, 100);
		this.setSize(420, 600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 203, 187, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 35, 103, 169, 14, 68, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		Box verticalBox_1 = Box.createVerticalBox();
		GridBagConstraints gbc_verticalBox_1 = new GridBagConstraints();
		gbc_verticalBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalBox_1.gridx = 2;
		gbc_verticalBox_1.gridy = 0;
		getContentPane().add(verticalBox_1, gbc_verticalBox_1);

		Box verticalBox = Box.createVerticalBox();
		GridBagConstraints gbc_verticalBox = new GridBagConstraints();
		gbc_verticalBox.insets = new Insets(0, 0, 5, 5);
		gbc_verticalBox.gridx = 1;
		gbc_verticalBox.gridy = 1;
		getContentPane().add(verticalBox, gbc_verticalBox);

		Box horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox.gridx = 0;
		gbc_horizontalBox.gridy = 2;
		getContentPane().add(horizontalBox, gbc_horizontalBox);

		// --------------------Panel 1--------------------//
		panel1 = new JPanel();
		panel1.setOpaque(true);
		panel1.setBounds(5, 5, 100, 50);

		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel1.insets = new Insets(0, 0, 5, 5);
		gbc_panel1.anchor = GridBagConstraints.NORTH;
		gbc_panel1.gridwidth = 2;
		gbc_panel1.gridx = 1;
		gbc_panel1.gridy = 2;
		getContentPane().add(panel1, gbc_panel1);

		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[] { 43, 71, 212, 0 };
		gbl_panel1.rowHeights = new int[] { 29, 9, 0 };
		gbl_panel1.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel1.setLayout(gbl_panel1);

		JLabel lblPalle = new JLabel("Palle");
		lblPalle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblPalle = new GridBagConstraints();
		gbc_lblPalle.gridwidth = 2;
		gbc_lblPalle.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalle.gridx = 0;
		gbc_lblPalle.gridy = 0;
		panel1.add(lblPalle, gbc_lblPalle);

		JLabel lblStregkode = new JLabel("# "
				+ Service.getInstance().getStregkode(palle));
		lblStregkode.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblStregkode.setToolTipText("Stregkode");
		GridBagConstraints gbc_lblStregkode = new GridBagConstraints();
		gbc_lblStregkode.insets = new Insets(0, 0, 5, 0);
		gbc_lblStregkode.anchor = GridBagConstraints.NORTH;
		gbc_lblStregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStregkode.gridx = 2;
		gbc_lblStregkode.gridy = 0;
		panel1.add(lblStregkode, gbc_lblStregkode);

		JLabel lblAntalBakker = new JLabel("Antal bakker:");
		GridBagConstraints gbc_lblAntalBakker = new GridBagConstraints();
		gbc_lblAntalBakker.gridwidth = 2;
		gbc_lblAntalBakker.anchor = GridBagConstraints.WEST;
		gbc_lblAntalBakker.insets = new Insets(0, 0, 0, 5);
		gbc_lblAntalBakker.gridx = 0;
		gbc_lblAntalBakker.gridy = 1;
		panel1.add(lblAntalBakker, gbc_lblAntalBakker);

		JLabel label_1 = new JLabel(Service.getInstance().getMellemvarer(palle)
				.size()
				+ " stk.");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 1;
		panel1.add(label_1, gbc_label_1);

		Box horizontalBox_2 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_2 = new GridBagConstraints();
		gbc_horizontalBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalBox_2.gridx = 5;
		gbc_horizontalBox_2.gridy = 2;
		getContentPane().add(horizontalBox_2, gbc_horizontalBox_2);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 3;
		getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		// -----------------------------------------------//

		// --------------------Panel 2--------------------//
		JPanel panel2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 3;
		getContentPane().add(panel2, gbc_panel_2);

		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[] { 0, 300, 0, 0 };
		gbl_panel2.rowHeights = new int[] { 0, 70 };
		gbl_panel2.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE,
				0.0 };
		gbl_panel2.rowWeights = new double[] { 0.0, 0.0 };
		panel2.setLayout(gbl_panel2);

		JLabel lblOverblik = new JLabel("Overblik");
		GridBagConstraints gbc_lblOverblik = new GridBagConstraints();
		gbc_lblOverblik.gridwidth = 2;
		gbc_lblOverblik.anchor = GridBagConstraints.WEST;
		gbc_lblOverblik.insets = new Insets(0, 0, 5, 5);
		gbc_lblOverblik.gridx = 0;
		gbc_lblOverblik.gridy = 0;
		panel2.add(lblOverblik, gbc_lblOverblik);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel2.add(scrollPane, gbc_scrollPane);

		String[] columnNames = { "Produkttype", "Delbehandling", "Antal",
				"Resterende tid" };
		Object[][] data = Service.getInstance()
				.generateViewDataProdukttypeDelbehandlingAntalTid(palle);
		DefaultTableModel dm = new DefaultTableModel(data, columnNames);

		JTable table1 = new JTable(dm);
		scrollPane.setViewportView(table1);
		table1.setAutoCreateRowSorter(true);

		Box horizontalBox_3 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_3 = new GridBagConstraints();
		gbc_horizontalBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_3.gridx = 4;
		gbc_horizontalBox_3.gridy = 3;
		getContentPane().add(horizontalBox_3, gbc_horizontalBox_3);

		Box horizontalBox_1 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_1 = new GridBagConstraints();
		gbc_horizontalBox_1.fill = GridBagConstraints.VERTICAL;
		gbc_horizontalBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_1.gridx = 0;
		gbc_horizontalBox_1.gridy = 4;
		getContentPane().add(horizontalBox_1, gbc_horizontalBox_1);

		// -----------------------------------------------//

		// --------------------Panel 3--------------------//
		JPanel panel3_1 = new JPanel();
		GridBagConstraints gbc_panel3_1 = new GridBagConstraints();
		gbc_panel3_1.gridheight = 3;
		gbc_panel3_1.fill = GridBagConstraints.BOTH;
		gbc_panel3_1.gridwidth = 2;
		gbc_panel3_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel3_1.gridx = 1;
		gbc_panel3_1.gridy = 4;
		getContentPane().add(panel3_1, gbc_panel3_1);
		GridBagLayout gbl_panel3_1 = new GridBagLayout();
		gbl_panel3_1.columnWidths = new int[] { 375, 0 };
		gbl_panel3_1.rowHeights = new int[] { 0, 147, 19, 0, 199, 0 };
		gbl_panel3_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel3_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panel3_1.setLayout(gbl_panel3_1);

		JLabel lblDetaljer = new JLabel("Bakker:");
		GridBagConstraints gbc_lblDetaljer = new GridBagConstraints();
		gbc_lblDetaljer.fill = GridBagConstraints.VERTICAL;
		gbc_lblDetaljer.anchor = GridBagConstraints.WEST;
		gbc_lblDetaljer.insets = new Insets(0, 0, 5, 0);
		gbc_lblDetaljer.gridx = 0;
		gbc_lblDetaljer.gridy = 0;
		panel3_1.add(lblDetaljer, gbc_lblDetaljer);

		DefaultListModel dlm = new DefaultListModel();
		ArrayList<Mellemvare> mellemvarer = Service.getInstance()
				.getMellemvarer(palle);
		for (Mellemvare m : mellemvarer) {
			dlm.addElement(m);
		}
		list = new JList(dlm);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(controller);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		panel3_1.add(scrollPane_1, gbc_scrollPane_1);
		scrollPane_1.setViewportView(list);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		panel3_1.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 375, 0 };
		gbl_panel_1.rowHeights = new int[] { 5, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblDetaljer_1 = new JLabel("Detaljer:");
		lblDetaljer_1.setToolTipText("V\u00E6lg en bakke for at se detaljer");
		GridBagConstraints gbc_lblDetaljer_1 = new GridBagConstraints();
		gbc_lblDetaljer_1.anchor = GridBagConstraints.WEST;
		gbc_lblDetaljer_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblDetaljer_1.gridx = 0;
		gbc_lblDetaljer_1.gridy = 3;
		panel3_1.add(lblDetaljer_1, gbc_lblDetaljer_1);

		txtrDetaljer = new JTextArea();
		txtrDetaljer.setToolTipText("V\u00E6lg en bakke for at se detaljer");
		txtrDetaljer.setText("");
		GridBagConstraints gbc_txtrDetaljer = new GridBagConstraints();
		gbc_txtrDetaljer.fill = GridBagConstraints.BOTH;
		gbc_txtrDetaljer.gridx = 0;
		gbc_txtrDetaljer.gridy = 4;
		panel3_1.add(txtrDetaljer, gbc_txtrDetaljer);
		// -----------------------------------------------//

		Box horizontalBox_4 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_4 = new GridBagConstraints();
		gbc_horizontalBox_4.fill = GridBagConstraints.VERTICAL;
		gbc_horizontalBox_4.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_4.gridx = 3;
		gbc_horizontalBox_4.gridy = 4;
		getContentPane().add(horizontalBox_4, gbc_horizontalBox_4);

	}

	private class Controller implements ListSelectionListener { // ,ActionListener,ItemListener

		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void itemStateChanged(ItemEvent e) {
		// // TODO Auto-generated method stub
		//
		// }

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == list) {
				Mellemvare m = (Mellemvare) list.getSelectedValue();
				String mellemvareInfo = Service.getInstance()
						.getMellemvareInfo(m);
				txtrDetaljer.setText(mellemvareInfo);
			}

		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
