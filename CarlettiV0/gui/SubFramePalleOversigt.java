/**
 * SUBFRAMEPALLEOVERSIGT
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.table.TableColumn;

import model.Delbehandling;
import model.Delbehandling.DelbehandlingsType;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import service.Service;
import service.Varighed;

/**
 * Klassens form�l er at vise brugeren alle relevante informationer om en palle og de mellemvarer, der 
 * befinder sig p� den, samt at give mulighed for at foretage valg som at sende mellemvarer til n�ste
 * delbehandling, til f�rdigvarelageret eller kassere den.
 * 
 * @author Rita Holst Jacobsen
 * 
 */
public class SubFramePalleOversigt extends JFrame implements Observer, Subject {

	private Palle palle;

	private JPanel panel1;
	private JTable table;
	private JList list;
	private Controller controller;
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

	private TimeController timeController;

	private Timer timer;

	public SubFramePalleOversigt(MainFrame mainFrame, Palle palle) {
		this.observers = new ArrayList<Observer>();
		this.registerObserver(mainFrame);
		mainFrame.registerObserver(this);
		controller = new Controller();
		timeController = new TimeController();
		
		getContentPane().setBackground(Color.PINK);
		this.palle = palle;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Oversigt over palle");
		this.setLocation(1000, 0);
		this.setSize(550, 800);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 504, 0, -7, 0, -23, 0 };
		gridBagLayout.rowHeights = new int[] { 17, 0, 35, 103, 50, 195, 226,
				52, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 0.0, Double.MIN_VALUE };
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

		textrPlacering = new JTextArea(Service.getInstance()
				.getPallePlaceringsString(palle));
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
		data = Service.getInstance().generateViewDataProdukttypeDelbehandlingAntalTid(palle);
		dm = new DefaultTableModel(data, columnNames){
			@Override
			public Class<?> getColumnClass(int columnIndex) {
			   if (columnIndex == 3){
			   	return Varighed.class;
			   }
			   else{
			   	return super.getColumnClass(columnIndex);
			   }
			}
		};

		table = new JTable( dm )
		{
			
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				if (column ==3){
					((JLabel) renderer).setHorizontalAlignment(SwingConstants.RIGHT);
				}
				else ((JLabel) renderer).setHorizontalAlignment(SwingConstants.LEFT);
				
				Component c = super.prepareRenderer(renderer, row, column);
				if (this.getValueAt(row, column)!=null && this.getSelectedRow()!=row){
					Produkttype pt = (Produkttype) table.getModel().getValueAt(row, 0);
					Delbehandling d = (Delbehandling) table.getModel().getValueAt(row, 1);
					Mellemvare m = Service.getInstance().getMellemvarerAfSammeType(SubFramePalleOversigt.this.palle, pt, d).get(0);
					Color color = FarveKoder.getFarve(m);
					c.setBackground(color);
				}
				return c;
			}
		};
		scrollPane.setViewportView(table);
		table.setAutoCreateColumnsFromModel(false);
//		table.setAutoCreateRowSorter(true);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dm);
//		sorter.
		table.setRowSorter(sorter);
		
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
		
		list.setCellRenderer(new DefaultListCellRenderer(){

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus){
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				Mellemvare m = (Mellemvare) value;
				if (m != null && !isSelected){
					setBackground(FarveKoder.getFarve(m));
				}
				return c;
			}
		});


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

		update();
		timer = new Timer(1000, timeController);

	}

	/**
	 * Kaldes hver gang tabellens data opdateres
	 */
	public void setColumnWidth() {
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 2) {
				column.setPreferredWidth(30); // Antal
			} else if (i == 3) {
				column.setPreferredWidth(80);// Resterende tid
			} else {
				column.setPreferredWidth(100);
			}
		}
	}

	/**
	 * Kaldes hvor brugerens valg kan udl�se behov for at s�ge efter en ny palle. Dette sker dog
	 * kun hvis der er mere end �n slags varer p� pallen - ellers returneres null.
	 * 
	 * @param palle
	 * @return En 'ny' palle
	 * 
	 * @author Rita Holst Jacobsen
	 */
	private Palle askForPalle() {
		Palle nyPalle = null;
		if (!Service.getInstance().alleVarerErEns(palle)) {
			PalleDialog palleDialog = new PalleDialog(this, "V�lg ny palle",
					"Kun nogle mellemvarer\nplukkes fra pallen.\n\nAngiv ny palle til disse:");
			palleDialog.setVisible(true);
			if (palleDialog.isOKed()) {
				nyPalle = palleDialog.getPalle();
			}
			palleDialog.dispose();
		}
		return nyPalle;
	}

	/**
	 * Kaldes hvor brugerens valg udl�ser behov for at s�ge efter en mellemlagerplads, der kan knyttes
	 * til den aktuelle palle (ny eller oprindelig). Dette sker derfor kun n�r mellemvarer sendes til
	 * t�rring, og kun hvis den anvendte palle ikke allerede er knyttet til en mellemlagerplads.
	 * 
	 * @param palle
	 * @return En 'ny' mellemlagerplads
	 * 
	 * @author Rita Holst Jacobsen
	 */
	private MellemlagerPlads askForPlacering(Palle palle) {
		MellemlagerPlads nyMellemlagerPlads = Service.getInstance()
				.getMellemlagerPlads(palle);
		if (nyMellemlagerPlads == null) {
			PlaceringsDialog placeringsDialog = new PlaceringsDialog(
					this,
					"V�lg ny placering",
					"Den valgte palle \ner endnu ikke placeret p� mellemlageret.\n\nAngiv en ny placering:");
			placeringsDialog.setVisible(true);
			if (placeringsDialog.isOKed()) {
				nyMellemlagerPlads = placeringsDialog.getMellemlagerPlads();
			}
			placeringsDialog.dispose();
		}
		return nyMellemlagerPlads;

	}

	private class Controller implements ListSelectionListener, ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int row = 0;
			Produkttype produkttype = null;
			Delbehandling delbehandling = null;
			if (table.getSelectedRowCount() != 0) {
				row = table.convertRowIndexToModel(table.getSelectedRow());
				produkttype = (Produkttype) table.getModel().getValueAt(row, 0);
				delbehandling = (Delbehandling) table.getModel().getValueAt(
						row, 1);
			}

			Mellemvare mellemvare = null;
			if (list.getSelectedIndex() >= 0) {
				mellemvare = (Mellemvare) list.getSelectedValue();
			}
			if (e.getSource() == btnDrageringMange) {
				if (table.getSelectedRowCount() == 0) { // Ingen er valgt, s� alle varer p� pallen 
					//skal sendes afsted
					Service.getInstance().sendTilNaesteDelbehandling(null,
							palle, DelbehandlingsType.DRAGERING, null, null);
				} else {
					Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle,
							DelbehandlingsType.DRAGERING, askForPalle(), null);
				}
			}

			else if (e.getSource() == btnDrageringEn) {
				Palle nyPalle = null;
				if (Service.getInstance().getMellemvarer(palle).size() > 1) {
					nyPalle = askForPalle();
				}
				Service.getInstance().sendTilNaesteDelbehandling(mellemvare,palle, DelbehandlingsType.DRAGERING, 
						nyPalle, null);
			}
			else if (e.getSource() == btnTilTrringMange) {
				if (table.getSelectedRowCount() == 0) { // Ingen er valgt, s�calle varer p� pallen
					// skal sendes afsted
					MellemlagerPlads nyMellemlagerPlads = null;
					if (Service.getInstance().getMellemlagerPlads(palle) == null) {
						nyMellemlagerPlads = askForPlacering(palle);
					}
					Service.getInstance().sendTilNaesteDelbehandling(null,
							palle, DelbehandlingsType.TOERRING, null,
							nyMellemlagerPlads);
				} else { 																// En gruppe er valgt
					Palle nyPalle = null;
					if (!Service.getInstance().alleVarerErEns(palle)) { 	// .. og den er m�ske den eneste tilbagev�rende
						nyPalle = askForPalle();
					}
					MellemlagerPlads nyMellemlagerPlads = null;
					if (nyPalle != null && Service.getInstance().getMellemlagerPlads(nyPalle) == null) {
						nyMellemlagerPlads = askForPlacering(nyPalle);
					}
					Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle,
							DelbehandlingsType.TOERRING, nyPalle,nyMellemlagerPlads);
				}
			}
			else if (e.getSource() == btnTilTrringEn) {
				Palle nyPalle = null;
				if (Service.getInstance().getMellemvarer(palle).size() > 1) {
					nyPalle = askForPalle();
				}
				MellemlagerPlads nyMellemlagerPlads = null;
				if (nyPalle != null && Service.getInstance().getMellemlagerPlads(nyPalle) == null) {
					nyMellemlagerPlads = askForPlacering(nyPalle);
				}
				Service.getInstance().sendTilNaesteDelbehandling(mellemvare,
						palle, DelbehandlingsType.TOERRING, nyPalle,
						nyMellemlagerPlads);

			}

			else if (e.getSource() == btnTilFrdigvarelagerMange) {
				if (table.getSelectedRowCount() == 0) { // Ingen er valgt, s� alle varer p� pallen		
					// skal sendes afsted
					Service.getInstance().sendTilFaerdigvareLager(null, palle,
							null);
				} else {
					Palle nyPalle = null;
					if (!Service.getInstance().alleVarerErEns(palle)) {
						nyPalle = askForPalle();
					}
					Service.getInstance().sendTilFaerdigvareLager(produkttype, delbehandling, palle, nyPalle);
				}
			}

			else if (e.getSource() == btnTilFrdigvarelagerEn) {
				Palle nyPalle = null;
				if (Service.getInstance().getMellemvarer(palle).size() > 1) {
					nyPalle = askForPalle();
				}
				Service.getInstance().sendTilFaerdigvareLager(mellemvare, palle, nyPalle);
			}
			else if (e.getSource() == btnKassrMange) {
				if (table.getSelectedRowCount() == 0) {
					Service.getInstance().kasserMellemvarer(null, palle);
				} else {
					Service.getInstance().kasserMellemvarer(produkttype,
							delbehandling, palle);
				}
			}

			else if (e.getSource() == btnKasserEn) {
				Service.getInstance().kasserMellemvarer(mellemvare, palle);
			}
			update();
			notifyObservers();
		}
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {

				if (e.getSource() == table.getSelectionModel()) {
					btnDrageringMange.setEnabled(false);
					btnTilTrringMange.setEnabled(false);
					btnTilFrdigvarelagerMange.setEnabled(false);
					btnKassrMange.setEnabled(false);
					if (table.getSelectedRowCount() > 0) {
						int row = table.convertRowIndexToModel(table
								.getSelectedRow()); // <--VIGTIGT!!!
						if (table.getModel().getValueAt(row, 0) != null) {
							Produkttype produkttype = (Produkttype) table
									.getModel().getValueAt(row, 0);
							btnKassrMange.setEnabled(true);
							if (table.getModel().getValueAt(row, 1) != null) { // Der er ikke n�dvendigvis en 
								// delbehandling i gang p� en mellemvare
								Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 1);
								if (Service.getInstance().naesteDelbehandlingGyldig(palle, produkttype, delbehandling,
										DelbehandlingsType.DRAGERING)) {
									btnDrageringMange.setEnabled(true);
								} 
								else if (Service.getInstance().naesteDelbehandlingGyldig(palle,produkttype, 
										delbehandling, DelbehandlingsType.TOERRING)) {
									btnTilTrringMange.setEnabled(true);
								} 
								else if (Service.getInstance().naesteDelbehandlingGyldig(palle,produkttype,
										delbehandling, null)) {
									btnTilFrdigvarelagerMange.setEnabled(true);
								}
							}
						}

					} else { // Ingen r�kker er valgt - klikbarhed afh�nger af pallen
						if (Service.getInstance().getMellemvarer(palle).size() > 0) {
							btnKassrMange.setEnabled(true);
						}
						if (Service.getInstance().naesteDelbehandlingGyldig(palle, null, null,
								DelbehandlingsType.DRAGERING)) {
							btnDrageringMange.setEnabled(true);
						} 
						else if (Service.getInstance().naesteDelbehandlingGyldig(palle, null, null,
								DelbehandlingsType.TOERRING)) {
							btnTilTrringMange.setEnabled(true);
						} 
						else if (Service.getInstance().naesteDelbehandlingGyldig(palle, null, null, null)) {
							btnTilFrdigvarelagerMange.setEnabled(true);
						}
					}
				} else if (e.getSource() == list) {
					btnDrageringEn.setEnabled(false);
					btnTilTrringEn.setEnabled(false);
					btnTilFrdigvarelagerEn.setEnabled(false);
					btnKasserEn.setEnabled(false);
					Mellemvare m = (Mellemvare) list.getSelectedValue();
					if (m != null) {
						String mellemvareInfo = Service.getInstance()
								.getMellemvareInfo(m);
						txtrDetaljer.setText(mellemvareInfo);

						if (list.getSelectedIndex() >= 0) {
							btnKasserEn.setEnabled(true);
						}
						if (Service.getInstance().naesteDelbehandlingGyldig(m, DelbehandlingsType.DRAGERING)) {
							btnDrageringEn.setEnabled(true);
						} 
						else if (Service.getInstance().naesteDelbehandlingGyldig(m,DelbehandlingsType.TOERRING)) {
							btnTilTrringEn.setEnabled(true);
						} else if (Service.getInstance().naesteDelbehandlingGyldig(m, null)) {
							btnTilFrdigvarelagerEn.setEnabled(true);
						}
					}
				}
			}
		}
	}
	
	private class TimeController implements ActionListener{

		@Override
      public void actionPerformed(ActionEvent e) {
			if (e.getSource()==timer){
				int selectedTableRow = table.getSelectedRow();
				int selectedListRow = list.getSelectedIndex();
				update();
				if (selectedTableRow>-1){
					table.setRowSelectionInterval(selectedTableRow, selectedTableRow);	
				}
				if (selectedListRow > -1){
					list.setSelectedIndex(selectedListRow);
				}
			}

	      
      }
		
	}


	@Override
	public void update() {
		list.setListData(Service.getInstance().getMellemvarer(palle).toArray());
		dm.setDataVector(Service.getInstance().generateViewDataProdukttypeDelbehandlingAntalTid(palle),
				columnNames);
		setColumnWidth();
		textrPlacering.setText(Service.getInstance().getPallePlaceringsString(palle));
		txtrDetaljer.setText(Service.getInstance().getMellemvareInfo(null));

		// --N�r der ikke er valgt en tabelr�kke, skal man stadig kunne udf�re
		// en gyldig handling p� hele pallen, hvis alle varer er ens - hvilket
		// Service afg�r--//
		if (table.getSelectedRowCount() == 0) {
			if (Service.getInstance().naesteDelbehandlingGyldig(palle, null,
					null, DelbehandlingsType.DRAGERING)) {
				btnDrageringMange.setEnabled(true);
			}
			if (Service.getInstance().naesteDelbehandlingGyldig(palle, null,
					null, DelbehandlingsType.TOERRING)) {
				btnTilTrringMange.setEnabled(true);
			}
			if (Service.getInstance().naesteDelbehandlingGyldig(palle, null,
					null, null)) {
				btnTilFrdigvarelagerMange.setEnabled(true);
			}
			if (Service.getInstance().getMellemvarer(palle).size() > 0) {
				btnKassrMange.setEnabled(true);
			}
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
		for (Observer o : observers) {
			o.update();
		}

	}
}
