package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import service.Service;

import model.Palle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import model.Behandling;
import model.Delbehandling;
import model.Dragering;
import model.Drageringshal;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import model.Toerring;
import dao.DAO;
import dao.JpaDao;
import dao.ListDao;

public class MainFrame extends JFrame implements Subject, Observer
{
	private ArrayList<Observer> observers;
	private JList listPaller;
	private JPanel panel1, panel2, panel3;
	private JTable table;
	private JLabel label;
	private JPanel panel;
	private JList list;
	private Controller controller = new Controller();
	private JTextArea txtrDetaljer;
	private SubFrameAdminPalle subFrameAdminPalle;

	public MainFrame()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("");
		this.setLocation(20, 20);
		this.setSize(669, 608);
		getContentPane().setLayout(null);
		
		getContentPane().setBackground(Color.PINK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mellemvarelager - Oversigt");
		this.setLocation(200, 100);
		this.setSize(800, 600);
		getContentPane().setLayout(null);

		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setBounds(0, 0, 0, 0);
		getContentPane().add(verticalBox_1);

		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(0, 0, 0, 0);
		getContentPane().add(verticalBox);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(0, 0, 0, 0);
		getContentPane().add(horizontalBox);

		String[] columnNames = {"Plads#", "Palle#", "Produkttype", "Delbehandling", "Antal","Resterende tid" };
		Object[][] data = Service.getInstance().generateViewDataMellemlagerOversigt();
		DefaultTableModel dm = new DefaultTableModel(data, columnNames);
		
		JTable table1 = new JTable(dm);
		table1.setAutoCreateRowSorter(true);
		
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
		
		//		// --------------------Panel 1--------------------//
		//		panel1 = new JPanel();
		//		panel1.setOpaque(true);
		//		panel1.setBounds(5, 5, 100, 50);
		//
		//		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		//		gbc_panel1.fill = GridBagConstraints.HORIZONTAL;
		//		gbc_panel1.insets = new Insets(0, 0, 5, 5);
		//		gbc_panel1.anchor = GridBagConstraints.NORTH;
		//		gbc_panel1.gridwidth = 2;
		//		gbc_panel1.gridx = 1;
		//		gbc_panel1.gridy = 2;
		//		getContentPane().add(panel1, gbc_panel1);
		//
		//		GridBagLayout gbl_panel1 = new GridBagLayout();
		//		gbl_panel1.columnWidths = new int[] { 43, 71, 212, 0 };
		//		gbl_panel1.rowHeights = new int[] { 29, 9, 0 };
		//		gbl_panel1.columnWeights = new double[] { 0.0, 0.0, 0.0,
		//				Double.MIN_VALUE };
		//		gbl_panel1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		//		panel1.setLayout(gbl_panel1);
		//
		//		JLabel lblPalle = new JLabel("Palle");
		//		lblPalle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		//		GridBagConstraints gbc_lblPalle = new GridBagConstraints();
		//		gbc_lblPalle.gridwidth = 2;
		//		gbc_lblPalle.anchor = GridBagConstraints.NORTHWEST;
		//		gbc_lblPalle.insets = new Insets(0, 0, 5, 5);
		//		gbc_lblPalle.gridx = 0;
		//		gbc_lblPalle.gridy = 0;
		//		panel1.add(lblPalle, gbc_lblPalle);
		//
		//		JLabel lblStregkode = new JLabel("# ");
		//		lblStregkode.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		//		lblStregkode.setToolTipText("Stregkode");
		//		GridBagConstraints gbc_lblStregkode = new GridBagConstraints();
		//		gbc_lblStregkode.insets = new Insets(0, 0, 5, 0);
		//		gbc_lblStregkode.anchor = GridBagConstraints.NORTH;
		//		gbc_lblStregkode.fill = GridBagConstraints.HORIZONTAL;
		//		gbc_lblStregkode.gridx = 2;
		//		gbc_lblStregkode.gridy = 0;
		//		panel1.add(lblStregkode, gbc_lblStregkode);
		//
		//		JLabel lblAntalBakker = new JLabel("Antal bakker:");
		//		GridBagConstraints gbc_lblAntalBakker = new GridBagConstraints();
		//		gbc_lblAntalBakker.gridwidth = 2;
		//		gbc_lblAntalBakker.anchor = GridBagConstraints.WEST;
		//		gbc_lblAntalBakker.insets = new Insets(0, 0, 0, 5);
		//		gbc_lblAntalBakker.gridx = 0;
		//		gbc_lblAntalBakker.gridy = 1;
		//		panel1.add(lblAntalBakker, gbc_lblAntalBakker);
		//
		//		JLabel label_1 = new JLabel(" stk.");
		//		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		//		gbc_label_1.anchor = GridBagConstraints.WEST;
		//		gbc_label_1.gridx = 2;
		//		gbc_label_1.gridy = 1;
		//		panel1.add(label_1, gbc_label_1);
		//
		//		Box horizontalBox_2 = Box.createHorizontalBox();
		//		GridBagConstraints gbc_horizontalBox_2 = new GridBagConstraints();
		//		gbc_horizontalBox_2.insets = new Insets(0, 0, 5, 0);
		//		gbc_horizontalBox_2.gridx = 5;
		//		gbc_horizontalBox_2.gridy = 2;
		//		getContentPane().add(horizontalBox_2, gbc_horizontalBox_2);
		//
		//		Component horizontalStrut = Box.createHorizontalStrut(20);
		//		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		//		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		//		gbc_horizontalStrut.gridx = 0;
		//		gbc_horizontalStrut.gridy = 3;
		//		getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		//		// -----------------------------------------------//
		
				// --------------------Panel 2--------------------//
				JPanel panel1_1 = new JPanel();
				panel1_1.setBounds(12, 29, 758, 487);
				getContentPane().add(panel1_1);
				
						GridBagLayout gbl_panel1_1 = new GridBagLayout();
						gbl_panel1_1.columnWidths = new int[] { 0, 300, 0, 0 };
						gbl_panel1_1.rowHeights = new int[] { 0, 70 };
						gbl_panel1_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE,
								0.0 };
						gbl_panel1_1.rowWeights = new double[] { 0.0, 0.0 };
						panel1_1.setLayout(gbl_panel1_1);
						
						//		JLabel lblOverblik = new JLabel("Overblik");
						//		GridBagConstraints gbc_lblOverblik = new GridBagConstraints();
						//		gbc_lblOverblik.gridwidth = 2;
						//		gbc_lblOverblik.anchor = GridBagConstraints.WEST;
						//		gbc_lblOverblik.insets = new Insets(0, 0, 5, 5);
						//		gbc_lblOverblik.gridx = 0;
						//		gbc_lblOverblik.gridy = 0;
						//		panel1.add(lblOverblik, gbc_lblOverblik);
						
								JScrollPane scrollPane = new JScrollPane();
								GridBagConstraints gbc_scrollPane = new GridBagConstraints();
								gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
								gbc_scrollPane.fill = GridBagConstraints.BOTH;
								gbc_scrollPane.gridwidth = 4;
								gbc_scrollPane.gridx = 0;
								gbc_scrollPane.gridy = 1;
								panel1_1.add(scrollPane, gbc_scrollPane);
								//		DefaultTableModel dm = new DefaultTableModel();
								
								scrollPane.setViewportView(table1);


		Box horizontalBox_3 = Box.createHorizontalBox();
		horizontalBox_3.setBounds(0, 0, 0, 0);
		getContentPane().add(horizontalBox_3);

		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setBounds(0, 0, 0, 0);
		getContentPane().add(horizontalBox_1);


		JButton btnPlacerPalle = new JButton("Plac\u00E9r palle");
		btnPlacerPalle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
//				SubFramePlacerPalle placerPalle = new SubFramePlacerPalle();
//				placerPalle.setVisible(true);
			}
		});
		btnPlacerPalle.setBounds(153, 0, 99, 25);
		getContentPane().add(btnPlacerPalle);

		JButton btnSendTilDragering = new JButton("Send til Drag.");
		btnSendTilDragering.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Service.getInstance().sendPalleTilDragering((Palle)listPaller.getSelectedValue());
			}
		});
		btnSendTilDragering.setBounds(256, 0, 111, 25);
		getContentPane().add(btnSendTilDragering);

		JButton btnSendTilFærdigvare = new JButton("Send til f\u00E6rdigv.");
		btnSendTilFærdigvare.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				
				//Service.getInstance().sendPalleTilFærdigvare((Palle)listPaller.getSelectedValue());
			}
		});
		btnSendTilFærdigvare.setBounds(372, 0, 127, 25);
		getContentPane().add(btnSendTilFærdigvare);

		JButton btnKasserMellemvarer = new JButton("Kasser Mellemvarer");
		btnKasserMellemvarer.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				
			}
		});
		btnKasserMellemvarer.setBounds(504, 0, 145, 25);
		getContentPane().add(btnKasserMellemvarer);

		JButton btnNyPalle = new JButton("Tilføj mellemvare");
		btnNyPalle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
//				SubFrameTilfoejMellemvarer tilfoejMellemvare = new SubFrameTilfoejMellemvarer();
//				tilfoejMellemvare.setVisible(true);
			}
		});
		btnNyPalle.setBounds(654, 0, 133, 25);
		getContentPane().add(btnNyPalle);
	
		
		JSeparator separator = new JSeparator();
		separator.setBounds(792, 12, 1, 1);
		getContentPane().add(separator);
		
		listPaller = new JList();
		listPaller.setBounds(0, 0, 0, 0);
		getContentPane().add(listPaller);
		listPaller.setListData(Service.getInstance().getPaller().toArray());
		listPaller.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnPalleOversigt = new JButton("Palle oversigt");
		btnPalleOversigt.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
//				SubFramePalleOversigt palleOversigt = new SubFramePalleOversigt((Palle)listPaller.getSelectedValue());
//				palleOversigt.setVisible(true);
			}
		});
		btnPalleOversigt.setBounds(803, 0, 109, 25);
		getContentPane().add(btnPalleOversigt);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAdministrer = new JMenu("Administrer");
		menuBar.add(mnAdministrer);

		JMenuItem mntmPaller = new JMenuItem("Paller");
		mnAdministrer.add(mntmPaller);
	}
	

	@Override
	public void registerObserver(Observer o)
	{
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o)
	{
		observers.remove(o);

	}

	@Override
	public void notifyObserver()
	{
		for (int i = 0; i < observers.size(); i++)
		{
			observers.get(i).update();
		}
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
