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

import model.Delbehandling;
import model.Dragering;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import model.Toerring;

import service.Service;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

/**
 * @author nijntje
 * 
 */
public class SubFramePalleOversigt extends JFrame implements Observer, Subject {

	private Palle palle;
	private MainFrame mainFrame;
	private JPanel panel1, panel2, panel3;
	private JTable table;
	private JLabel label;
	private JPanel panel;
	private JList list;
	private Controller controller = new Controller();
	private JTextArea txtrDetaljer;
	private JButton btnDrageringMange;
	private JButton btnTilTrringMange;
	private JButton btnTilFrdigvarelagerMange;
	private JButton btnKassrMange;
	private JButton btnDrageringEn;
	private JButton btnTilTrringEn;
	private JButton btnTilFrdigvarelagerEn;
	private JButton btnKasserEn;
	private DefaultTableModel dm;
	private Object[][] data;
	private String[] columnNames;
	private DefaultListModel dlm;
	private ArrayList<Observer> observers;
	private JTextArea textrPlacering;

	public SubFramePalleOversigt(MainFrame mainFrame, Palle palle) {
		this.observers = new ArrayList<Observer>();
		this.registerObserver(mainFrame);
		getContentPane().setBackground(Color.PINK);
		this.mainFrame = mainFrame;
		this.palle = palle;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Oversigt over palle");
		this.setLocation(1000, 0);
		this.setSize(550, 800);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 504, 0, -7, 0, -23, 0 };
		gridBagLayout.rowHeights = new int[] { 17, 0, 35, 103, 50, 195, 226, 52, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_11 = new GridBagConstraints();
		gbc_rigidArea_11.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_11.gridx = 1;
		gbc_rigidArea_11.gridy = 0;
		getContentPane().add(rigidArea_11, gbc_rigidArea_11);

		Box verticalBox_1 = Box.createVerticalBox();
		GridBagConstraints gbc_verticalBox_1 = new GridBagConstraints();
		gbc_verticalBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalBox_1.gridx = 1;
		gbc_verticalBox_1.gridy = 1;
		getContentPane().add(verticalBox_1, gbc_verticalBox_1);

		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_10 = new GridBagConstraints();
		gbc_rigidArea_10.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_10.gridx = 0;
		gbc_rigidArea_10.gridy = 2;
		getContentPane().add(rigidArea_10, gbc_rigidArea_10);

		// --------------------Panel 1--------------------//
		panel1 = new JPanel();
		panel1.setOpaque(true);
		panel1.setBounds(5, 5, 100, 50);

		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel1.insets = new Insets(0, 0, 5, 5);
		gbc_panel1.anchor = GridBagConstraints.NORTH;
		gbc_panel1.gridx = 1;
		gbc_panel1.gridy = 2;
		getContentPane().add(panel1, gbc_panel1);

		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[] { 43, 71, 212, 0, 0 };
		gbl_panel1.rowHeights = new int[] { 29, 9, 0 };
		gbl_panel1.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel1.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
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
		gbc_lblStregkode.insets = new Insets(0, 0, 5, 5);
		gbc_lblStregkode.anchor = GridBagConstraints.NORTH;
		gbc_lblStregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStregkode.gridx = 2;
		gbc_lblStregkode.gridy = 0;
		panel1.add(lblStregkode, gbc_lblStregkode);

		textrPlacering = new JTextArea(Service.getInstance().getPallePlaceringsString(palle));
		textrPlacering.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.anchor = GridBagConstraints.EAST;
		gbc_textArea.gridheight = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.gridx = 3;
		gbc_textArea.gridy = 0;
		panel1.add(textrPlacering, gbc_textArea);

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
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
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

		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_9 = new GridBagConstraints();
		gbc_rigidArea_9.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_9.gridx = 0;
		gbc_rigidArea_9.gridy = 3;
		getContentPane().add(rigidArea_9, gbc_rigidArea_9);
		// -----------------------------------------------//

		// --------------------Panel 2--------------------//
		JPanel panel2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
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

		columnNames = new String[] { "Produkttype", "Delbehandling", "Antal",
		"Resterende tid" };
		data = Service.getInstance()
				.generateViewDataProdukttypeDelbehandlingAntalTid(palle);
		dm = new DefaultTableModel(data, columnNames);


		table = new JTable(dm);
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(controller);


		setColumnWidth();

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_1.gridx = 2;
		gbc_rigidArea_1.gridy = 3;
		getContentPane().add(rigidArea_1, gbc_rigidArea_1);


		Box horizontalBox_3 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_3 = new GridBagConstraints();
		gbc_horizontalBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_3.gridx = 4;
		gbc_horizontalBox_3.gridy = 3;
		getContentPane().add(horizontalBox_3, gbc_horizontalBox_3);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_21 = new GridBagConstraints();
		gbc_panel_21.insets = new Insets(0, 0, 5, 5);
		gbc_panel_21.fill = GridBagConstraints.BOTH;
		gbc_panel_21.gridx = 1;
		gbc_panel_21.gridy = 4;
		getContentPane().add(panel_2, gbc_panel_21);

		btnDrageringMange = new JButton("Til dragering");
		btnDrageringMange.addActionListener(controller);
		panel_2.add(btnDrageringMange);

		btnTilTrringMange = new JButton("Til t\u00F8rring");
		btnTilTrringMange.addActionListener(controller);
		panel_2.add(btnTilTrringMange);

		btnTilFrdigvarelagerMange = new JButton("Til f\u00E6rdigvarelager");
		btnTilFrdigvarelagerMange.addActionListener(controller);
		panel_2.add(btnTilFrdigvarelagerMange);

		btnKassrMange = new JButton("Kass\u00E9r");
		btnKassrMange.addActionListener(controller);
		panel_2.add(btnKassrMange);

		btnDrageringMange.setEnabled(false);
		btnTilTrringMange.setEnabled(false);
		btnTilFrdigvarelagerMange.setEnabled(false);
		btnKassrMange.setEnabled(false);



		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_3 = new GridBagConstraints();
		gbc_rigidArea_3.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_3.gridx = 2;
		gbc_rigidArea_3.gridy = 4;
		getContentPane().add(rigidArea_3, gbc_rigidArea_3);

		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_6 = new GridBagConstraints();
		gbc_rigidArea_6.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_6.gridx = 0;
		gbc_rigidArea_6.gridy = 5;
		getContentPane().add(rigidArea_6, gbc_rigidArea_6);

		// -----------------------------------------------//

		// --------------------Panel 3--------------------//
		JPanel panel3_1 = new JPanel();
		GridBagConstraints gbc_panel3_1 = new GridBagConstraints();
		gbc_panel3_1.gridheight = 2;
		gbc_panel3_1.fill = GridBagConstraints.BOTH;
		gbc_panel3_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel3_1.gridx = 1;
		gbc_panel3_1.gridy = 5;
		getContentPane().add(panel3_1, gbc_panel3_1);
		GridBagLayout gbl_panel3_1 = new GridBagLayout();
		gbl_panel3_1.columnWidths = new int[] { 0, 375, 0 };
		gbl_panel3_1.rowHeights = new int[] { 0, 147, 19, 0, 199, 0 };
		gbl_panel3_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel3_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panel3_1.setLayout(gbl_panel3_1);

		JLabel lblDetaljer = new JLabel("Bakker:");
		GridBagConstraints gbc_lblDetaljer = new GridBagConstraints();
		gbc_lblDetaljer.gridwidth = 2;
		gbc_lblDetaljer.fill = GridBagConstraints.VERTICAL;
		gbc_lblDetaljer.anchor = GridBagConstraints.WEST;
		gbc_lblDetaljer.insets = new Insets(0, 0, 5, 0);
		gbc_lblDetaljer.gridx = 0;
		gbc_lblDetaljer.gridy = 0;
		panel3_1.add(lblDetaljer, gbc_lblDetaljer);

		dlm = new DefaultListModel();
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
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		panel3_1.add(scrollPane_1, gbc_scrollPane_1);
		scrollPane_1.setViewportView(list);

		JLabel lblDetaljer_1 = new JLabel("Detaljer:");
		lblDetaljer_1.setToolTipText("V\u00E6lg en bakke for at se detaljer");
		GridBagConstraints gbc_lblDetaljer_1 = new GridBagConstraints();
		gbc_lblDetaljer_1.anchor = GridBagConstraints.WEST;
		gbc_lblDetaljer_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblDetaljer_1.gridx = 1;
		gbc_lblDetaljer_1.gridy = 3;
		panel3_1.add(lblDetaljer_1, gbc_lblDetaljer_1);

		txtrDetaljer = new JTextArea();
		txtrDetaljer.setToolTipText("V\u00E6lg en bakke for at se detaljer");
		txtrDetaljer.setText("");
		txtrDetaljer.setEditable(false);
		GridBagConstraints gbc_txtrDetaljer = new GridBagConstraints();
		gbc_txtrDetaljer.fill = GridBagConstraints.BOTH;
		gbc_txtrDetaljer.gridx = 1;
		gbc_txtrDetaljer.gridy = 4;
		panel3_1.add(txtrDetaljer, gbc_txtrDetaljer);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_2.gridx = 2;
		gbc_rigidArea_2.gridy = 5;
		getContentPane().add(rigidArea_2, gbc_rigidArea_2);
		// -----------------------------------------------//

		Box horizontalBox_4 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_4 = new GridBagConstraints();
		gbc_horizontalBox_4.fill = GridBagConstraints.VERTICAL;
		gbc_horizontalBox_4.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_4.gridx = 3;
		gbc_horizontalBox_4.gridy = 5;
		getContentPane().add(horizontalBox_4, gbc_horizontalBox_4);

		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_7 = new GridBagConstraints();
		gbc_rigidArea_7.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_7.gridx = 0;
		gbc_rigidArea_7.gridy = 6;
		getContentPane().add(rigidArea_7, gbc_rigidArea_7);

		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_4 = new GridBagConstraints();
		gbc_rigidArea_4.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_4.gridx = 2;
		gbc_rigidArea_4.gridy = 6;
		getContentPane().add(rigidArea_4, gbc_rigidArea_4);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 7;
		getContentPane().add(panel_3, gbc_panel_3);

		btnDrageringEn = new JButton("Til dragering");
		btnDrageringEn.addActionListener(controller);
		btnDrageringEn.setEnabled(false);
		panel_3.add(btnDrageringEn);

		btnTilTrringEn = new JButton("Til t\u00F8rring");
		btnTilTrringEn.addActionListener(controller);
		btnTilTrringEn.setEnabled(false);
		panel_3.add(btnTilTrringEn);

		btnTilFrdigvarelagerEn = new JButton("Til f\u00E6rdigvarelager");
		btnTilFrdigvarelagerEn.addActionListener(controller);
		btnTilFrdigvarelagerEn.setEnabled(false);
		panel_3.add(btnTilFrdigvarelagerEn);

		btnKasserEn = new JButton("Kass\u00E9r");
		btnKasserEn.addActionListener(controller);
		btnKasserEn.setEnabled(false);
		panel_3.add(btnKasserEn);

		Component rigidArea_12 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_12 = new GridBagConstraints();
		gbc_rigidArea_12.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea_12.gridx = 1;
		gbc_rigidArea_12.gridy = 8;
		getContentPane().add(rigidArea_12, gbc_rigidArea_12);

	}

	public void setColumnWidth(){
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 2) {
				column.setPreferredWidth(30); //Antal
			} 
			else if (i == 3){
				column.setPreferredWidth(80);//Resterende tid
			}
			else {
				column.setPreferredWidth(100);
			}
		}
	}


	private Palle askForPalle(boolean askForPlacering){
		Palle nyPalle = null;
		if (!Service.getInstance().alleVarerErEns(palle)){
			PalleDialog palleDialog = new PalleDialog(this, "V¾lg ny palle", "Kun nogle mellemvarer\nplukkes fra pallen.\n\nAngiv ny palle til disse:");
			palleDialog.setVisible(true);
			if (palleDialog.isOKed()){
				nyPalle = palleDialog.getPalle();
			}
			palleDialog.dispose();
			if (askForPlacering){
				askForPlacering(nyPalle);
			}
		}

		return nyPalle;
	}

	private void askForPlacering(Palle nyPalle){
		if (Service.getInstance().getPalleIkkeIBrug(nyPalle)){
			SubFramePlacerPalle placeringsFrame = new SubFramePlacerPalle(mainFrame);
			placeringsFrame.setPalleStregkodeTekst(Service.getInstance().getStregkode(nyPalle));
			placeringsFrame.setVisible(true);
			placeringsFrame.registerObserver(mainFrame);
		}

	}

	private class Controller implements ListSelectionListener ,ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnDrageringMange){
				if (table.getSelectedRowCount()==0){
					palle.startDelbehandling(null, Dragering.class);
				}

				else {
					int row = table.convertRowIndexToModel(table.getSelectedRow());
					Produkttype produkttype = (Produkttype) table.getModel().getValueAt(row, 0);
					Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 1);
					Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle, Dragering.class, askForPalle(false));
				}
			}

			else if (e.getSource()==btnDrageringEn){
				Mellemvare mellemvare = (Mellemvare) list.getSelectedValue();
				Service.getInstance().sendTilNaesteDelbehandling(mellemvare, palle, Dragering.class, askForPalle(false));
			}

			else if (e.getSource()==btnTilTrringMange){
				if (table.getSelectedRowCount()==0){
					Service.getInstance().sendTilNaesteDelbehandling(null, palle, Toerring.class, askForPalle(true));
				}

				else {
					int row = table.convertRowIndexToModel(table.getSelectedRow());
					Produkttype produkttype = (Produkttype) table.getModel().getValueAt(row, 0);
					Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 1);
					Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle, Toerring.class, askForPalle(true));
				}
			}

			else if (e.getSource()==btnTilTrringEn){
				Mellemvare mellemvare = (Mellemvare) list.getSelectedValue();
				Service.getInstance().sendTilNaesteDelbehandling(mellemvare, palle, Toerring.class, askForPalle(true));
			}

			else if (e.getSource()==btnTilFrdigvarelagerMange){
				if (table.getSelectedRowCount()==0){
					Service.getInstance().sendTilF¾rdigvareLager(null, palle);
				}
				else {
					int row = table.convertRowIndexToModel(table.getSelectedRow());
					Produkttype produkttype = (Produkttype) table.getModel().getValueAt(row, 0);
					Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 1);
					Service.getInstance().sendTilF¾rdigvareLager(produkttype, delbehandling, palle, askForPalle(false));
				}
			}

			else if (e.getSource()==btnTilFrdigvarelagerEn){
				Mellemvare mellemvare = (Mellemvare) list.getSelectedValue();
				Service.getInstance().sendTilF¾rdigvareLager(mellemvare, palle);	
			}

			else if (e.getSource()==btnKassrMange){
				if (table.getSelectedRowCount()==0){
					Service.getInstance().kasserMellemvarer(null, palle);
				}
				else {
					int row = table.convertRowIndexToModel(table.getSelectedRow());
					Produkttype produkttype = (Produkttype) table.getModel().getValueAt(row, 0);
					Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 1);
					Service.getInstance().kasserMellemvarer(produkttype, delbehandling, palle);
				}
			}

			else if (e.getSource()==btnKasserEn){
				Mellemvare mellemvare = (Mellemvare) list.getSelectedValue();
				Service.getInstance().kasserMellemvarer(mellemvare, palle);
			}

			update();
			notifyObservers();

		}

		@Override
		public void valueChanged(ListSelectionEvent e) {

			if (e.getSource()==table.getSelectionModel()){
				btnDrageringMange.setEnabled(false);
				btnTilTrringMange.setEnabled(false);
				btnTilFrdigvarelagerMange.setEnabled(false);
				btnKassrMange.setEnabled(false);
				if (table.getSelectedRowCount()>0 ){
					int row = table.convertRowIndexToModel(table.getSelectedRow());	//<--VIGTIGT!!!
					if(table.getModel().getValueAt(row, 0)!=null){	//Hvis der er en produkttype er der en mellemvare, som kan kasseres
						Produkttype produkttype = (Produkttype) table.getModel().getValueAt(row, 0);
						Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 1);
						btnKassrMange.setEnabled(true);
						if (delbehandling != null){					//Men der er ikke n¿dvendigvis en delbehandling i gang pŒ den mellemvare
							if (Service.getInstance().naesteBehandlingGyldig(palle, produkttype, delbehandling, Dragering.class)){
								btnDrageringMange.setEnabled(true);
							}
							else if (Service.getInstance().naesteBehandlingGyldig(palle, produkttype, delbehandling, Toerring.class)){
								btnTilTrringMange.setEnabled(true);
							}
							else if (Service.getInstance().naesteBehandlingGyldig(palle, produkttype, delbehandling, null)){
								btnTilFrdigvarelagerMange.setEnabled(true);
							}
						}

					}

				}
				else if (e.getSource() == list) {
					btnDrageringEn.setEnabled(false);
					btnTilTrringEn.setEnabled(false);
					btnTilFrdigvarelagerEn.setEnabled(false);
					btnKasserEn.setEnabled(false);
					Mellemvare m = (Mellemvare) list.getSelectedValue();
					if (m != null){
						String mellemvareInfo = Service.getInstance().getMellemvareInfo(m);
						txtrDetaljer.setText(mellemvareInfo);

						if (list.getSelectedIndices().length!=0){
							btnKasserEn.setEnabled(true);
						}
						if (Service.getInstance().naesteBehandlingGyldig(m, Dragering.class)){
							btnDrageringEn.setEnabled(true);
						}
						else if (Service.getInstance().naesteBehandlingGyldig(m, Toerring.class)){
							btnTilTrringEn.setEnabled(true);
						}
						else if (Service.getInstance().naesteBehandlingGyldig(m, null)){
							btnTilFrdigvarelagerEn.setEnabled(true);
						}
					}
				}

			}

		}
	}

	@Override
	public void update() {
		list.setListData(Service.getInstance().getMellemvarer(palle).toArray());
		dm.setDataVector(Service.getInstance().generateViewDataProdukttypeDelbehandlingAntalTid(palle), columnNames);
		setColumnWidth();
		textrPlacering.setText(Service.getInstance().getPallePlaceringsString(palle));
		txtrDetaljer.setText(Service.getInstance().getMellemvareInfo(null));
		
		//--NŒr der ikke er valgt en tabelr¾kke, skal man stadig kunne udf¿re en gyldig handling pŒ hele pallen, hvis alle varer er ens - hvilket Service afg¿r--//
		if (Service.getInstance().naesteBehandlingGyldig(palle, null, null, Dragering.class)){
			btnDrageringMange.setEnabled(true);
		}
		else if (Service.getInstance().naesteBehandlingGyldig(palle, null, null, Toerring.class)){
			btnTilTrringMange.setEnabled(true);
		}
		else if (Service.getInstance().naesteBehandlingGyldig(palle, null, null, null)){
			btnTilFrdigvarelagerMange.setEnabled(true);
		}
		else if (Service.getInstance().getMellemvarer(palle).size()>0){
			btnKassrMange.setEnabled(true);
		}

		
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);

	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers){
			o.update();
		}

	}
}
