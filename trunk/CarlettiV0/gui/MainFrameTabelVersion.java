package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.DefaultRowSorter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import model.Mellemvare;
import service.Service;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;


/**
 * @author nijntje
 * 
 */
public class MainFrameTabelVersion extends JFrame implements Observer {


	private JPanel panel1;
	private JTable table1;
	private JList list;
	private Controller controller = new Controller();
	private JTextArea txtrDetaljer;
	private RowFilter<Object, Object> tomPladsFilter, aktueltFilter;
//	public boolean chckbxVisTommePladser;
	private DefaultTableModel dm;
	private Object[][] datas;
	private String[] columnNames;
	private JCheckBox chckbxVisTommePladser;
	private SubFrameAdminPalle subframeAdminPalle;
	private SubFrameAdminProdukttype subFrameAdminProdukt;
	private SubFrameAdminMellemlagerPlads subFrameAdminMellemlagerPlads;
	private SubFramePlacerPalle subFramePlacerPalle;

	public MainFrameTabelVersion() {
		getContentPane().setBackground(Color.PINK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mellemvarelager - Oversigt");
		this.setLocation(200, 100);
		this.setSize(800, 600);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 711, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 39, 0, 321, 21, 68, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		getContentPane().add(panel_2, gbc_panel_2);
		
		JButton btnPlacrPalle = new JButton("Plac\u00E9r palle");
		btnPlacrPalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subFramePlacerPalle = new SubFramePlacerPalle();
				subFramePlacerPalle.setVisible(true);
			}
		});
		panel_2.add(btnPlacrPalle);
		
		JButton btnTilfoejNyMellemvare = new JButton("Tilfoej ny mellemvare");
		panel_2.add(btnTilfoejNyMellemvare);



		// --------------------Panel 2--------------------//
		JPanel panel2 = new JPanel();
		GridBagConstraints panel3 = new GridBagConstraints();
		panel3.insets = new Insets(0, 0, 5, 5);
		panel3.fill = GridBagConstraints.BOTH;
		panel3.gridx = 1;
		panel3.gridy = 2;
		getContentPane().add(panel2, panel3);

		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[] { -168, 300, 0, 0 };
		gbl_panel2.rowHeights = new int[] { 27, 332 };
		gbl_panel2.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE,
				0.0 };
		gbl_panel2.rowWeights = new double[] { 0.0, 0.0 };
		panel2.setLayout(gbl_panel2);
		
		chckbxVisTommePladser = new JCheckBox("Vis tomme pladser");
		chckbxVisTommePladser.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_chckbxVisTommePladser = new GridBagConstraints();
		gbc_chckbxVisTommePladser.fill = GridBagConstraints.BOTH;
		gbc_chckbxVisTommePladser.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chckbxVisTommePladser.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVisTommePladser.gridx = 0;
		gbc_chckbxVisTommePladser.gridy = 0;
		panel2.add(chckbxVisTommePladser, gbc_chckbxVisTommePladser);
		chckbxVisTommePladser.addItemListener(controller);
		

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel2.add(scrollPane, gbc_scrollPane);

		columnNames = new String[] {"Plads#", "Palle#", "Produkttype", "Delbehandling", "Antal","Resterende tid" };
		datas = Service.getInstance().generateViewDataMellemlagerOversigt();
		dm = new DefaultTableModel(datas, columnNames);

		tomPladsFilter = new RowFilter<Object, Object>() {
			@Override
			public boolean include(Entry<? extends Object, ? extends Object> entry) {
		        return (entry.getValue(1) != null);
		      }
		    };

		table1 = new JTable(dm);
		table1.setFillsViewportHeight(true);
		scrollPane.setViewportView(table1);
		table1.setAutoCreateRowSorter(true);
		((DefaultRowSorter<DefaultTableModel, Integer>) table1.getRowSorter()).setRowFilter(tomPladsFilter);
		
		TableColumn column = null;
		for (int i = 0; i < 6; i++) {
		    column = table1.getColumnModel().getColumn(i);
		    if (i == 0 || i == 1){
		    	column.setPreferredWidth(75);//Placering og Palle
		    	column.setMinWidth(70);
		    	column.setMaxWidth(85);
		    }
		    if (i == 4) {
		        column.setPreferredWidth(40); //Antal
		        column.setMinWidth(35);
		        column.setMaxWidth(50);
		    } 
		    else if (i == 5){
		    	column.setPreferredWidth(85);//Resterende tid
		    	column.setMinWidth(80);
		    	column.setMaxWidth(90);
		    }
		    else {
		        column.setPreferredWidth(100);

		    }
		}
//		//table1.getColorModel()
//		TableRow row = null;
//		for (int i = 0; i < table1.getRowCount(); i++){
//			if(table1.getValueAt(i, 5)){
//				
//			}
//		}


		Box horizontalBox_3 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_3 = new GridBagConstraints();
		gbc_horizontalBox_3.fill = GridBagConstraints.BOTH;
		gbc_horizontalBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_3.gridx = 1;
		gbc_horizontalBox_3.gridy = 3;
		getContentPane().add(horizontalBox_3, gbc_horizontalBox_3);
		
		JPanel panel_1 = new JPanel();
		horizontalBox_3.add(panel_1);
		
		JButton btnSendTilDragring = new JButton("Send til drag\u00E9ring");
		panel_1.add(btnSendTilDragring);
		
		JButton btnSendTilFaerdigvarelager = new JButton("Send til faerdigvarelager");
		panel_1.add(btnSendTilFaerdigvarelager);
		
		JButton btnKassr = new JButton("Kass\u00E9r");
		panel_1.add(btnKassr);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(panel, gbc_panel);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAdministrr = new JMenu("Administr\u00E9r");
		menuBar.add(mnAdministrr);
		
		JMenuItem mntmPaller = new JMenuItem("Paller");
		mntmPaller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subframeAdminPalle = new SubFrameAdminPalle();
				subframeAdminPalle.setVisible(true);
				
			}
		});
		mnAdministrr.add(mntmPaller);
		
		JMenuItem mntmProdukttyper = new JMenuItem("Produkttyper");
		mntmProdukttyper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subFrameAdminProdukt = new SubFrameAdminProdukttype();
				subFrameAdminProdukt.setVisible(true);
			}
		});
		mnAdministrr.add(mntmProdukttyper);
		
		JMenuItem mntmMellemlagerpladser = new JMenuItem("Mellemlagerpladser");
		mntmMellemlagerpladser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subFrameAdminMellemlagerPlads = new SubFrameAdminMellemlagerPlads();
				subFrameAdminMellemlagerPlads.setVisible(true);
			}
		});
		mnAdministrr.add(mntmMellemlagerpladser);

		// -----------------------------------------------//
	}
	

	private class Controller implements ItemListener { // ,ListSelectionListener, ActionListener

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (chckbxVisTommePladser.isSelected()){
				((DefaultRowSorter<DefaultTableModel, Integer>) table1.getRowSorter()).setRowFilter(null);
			}
			else {
				((DefaultRowSorter<DefaultTableModel, Integer>) table1.getRowSorter()).setRowFilter(tomPladsFilter);
			}
		}



	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
