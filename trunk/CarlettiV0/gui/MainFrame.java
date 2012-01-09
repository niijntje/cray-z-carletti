/**
 * MAINFRAME
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultRowSorter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.RowSorter.SortKey;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.Delbehandling;
import model.Delbehandling.DelbehandlingsType;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import service.Service;
import service.Varighed;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 * MainFrame viser en oversigt over mellemlageret med al nødvendig information
 * og den oftest brugte funktionalitet.
 * 
 * @author Rita Holst Jacobsen: Tabelopsætning og funktionalitet via controller
 * @author Rasmus Cederdorff: Layout og funktionalitet defineret i constructor.
 * 
 */
public class MainFrame extends JFrame implements Observer, Subject {

	private JTable table;
	private Controller controller;
	private RowFilter<Object, Object> tomPladsFilter;
	private JCheckBox chckbxVisTommePladser;
	private JButton btnVisPalle;
	private JButton btnPlacrPalle;
	private JPanel panel_2;
	private JButton btnTilfoejNyMellemvare;
	private JPanel panel2;
	private JScrollPane scrollPane;
	private JMenuBar menuBar;
	private JMenuItem mntmPaller;
	private JMenuItem mntmProdukttyper;
	private JMenuItem mntmMellemlagerpladser;
	private JButton btnDrageringMange;
	private JButton btnTilTrringMange;
	private JButton btnTilFrdigvarelagerMange;
	private JButton btnKassrMange;
	private ArrayList<Observer> observers;
	public SubFrameAdminBehandling subFrameBehandlinger;
	private JMenuItem mntmBehandlinger;
	private static MainFrame mainFrame;

	private SubFrameTilfoejMellemvarer subFrameTilfoejMellemvarer;
	public SubFramePalleOversigt subFramePalleOversigt;	//Hmm, ikke gennemtænkt - der kan jo sagtens være flere...
	private SubFramePlacerPalle subFramePlacerPalle;
	public Object subFrameAdminBehandling;
	private JMenu mnOversigter;
	private JMenuItem mntmOversigtOverDragringshal;
	private JMenuItem mntmOversigtOverFrdigvarer;
	private JMenuItem mntmOversigtOverKasseredevarer;
	private JMenuItem mntmPaller_1;
	private String[] columnNames3;
	private Object[][] data3;
	private DefaultTableModel dm3;
	private Timer timer;
	private TimeController timecontroller;

	private MainFrame() {
		this.observers = new ArrayList<Observer>();
		getContentPane().setBackground(Color.PINK);
		controller = new Controller();
		timecontroller = new TimeController();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mellemvarelager - Oversigt");
		this.setLocation(0, 0);
		this.setSize(800, 600);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 711, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 39, 50, 321, 21, 35, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		getContentPane().add(panel_2, gbc_panel_2);

		btnPlacrPalle = new JButton("Plac\u00E9r palle");
		btnPlacrPalle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				subFramePlacerPalle = new SubFramePlacerPalle(MainFrame.this);
				subFramePlacerPalle.setVisible(true);
			}
		});

		panel_2.add(btnPlacrPalle);

		btnTilfoejNyMellemvare = new JButton("Tilfoej ny mellemvare");
		btnTilfoejNyMellemvare.addActionListener(controller);
		panel_2.add(btnTilfoejNyMellemvare);

		panel2 = new JPanel();
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

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel2.add(scrollPane, gbc_scrollPane);

		columnNames3 = new String[] { "Plads#", "Palle#", "Produkttype",
				"Delbehandling", "Antal", "Tid til min-tid",
				"Tid til ideal-tid", "Tid til max-tid" };
		data3 = Service.getInstance()
				.generateViewDataMellemlagerOversigt3Tider();
		dm3 = new DefaultTableModel(data3, columnNames3){
			@Override
			public Class<?> getColumnClass(int columnIndex) {
			   if (columnIndex > 4){
			   	return Varighed.class;
			   }
			   else{
			   	return super.getColumnClass(columnIndex);
			   }
			}
		};
		table = new JTable(dm3)
		{
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				if (column >4){
					((JLabel) renderer).setHorizontalAlignment(SwingConstants.RIGHT);
				}
				else ((JLabel) renderer).setHorizontalAlignment(SwingConstants.LEFT);
				
				Component c = super.prepareRenderer(renderer, row, column);
				
				if (this.getSelectedRow()!=row){
					c.setBackground(Color.white);
					Palle p = (Palle) table.getValueAt(row, 1);
					Produkttype pt = (Produkttype) table.getValueAt(row, 2);
					Delbehandling d = (Delbehandling) table.getValueAt(row, 3);
					if (p != null && pt != null && d != null){
						ArrayList<Mellemvare> mellemvarer = Service.getInstance().getMellemvarerAfSammeType(p, pt, d);
						if (mellemvarer.size()>0){
							Mellemvare m = mellemvarer.get(0);
							Color color = FarveKoder.getFarve(m);
							c.setBackground(color);
						}
					}
				}
				return c;
			}
		};

		
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
//				table.setAutoCreateRowSorter(true);
		//---------------Nedenstående benyttes hvis man ønsker at pågældende søjle skal sorteres vha.
		//---------------det bagvedliggende Object's compareTo()-metode
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dm3);
		Comparator<Varighed> comparatorVarighed = new Comparator<Varighed>(){
			@Override
         public int compare(Varighed o1, Varighed o2) {
	         if (o1 != null){
	         	return o1.compareTo(o2);
	         }
	         else if (o2 != null){
	         	return -1*o2.compareTo(o1);
	         }
	         else return 0;
         }	
		};
		
		table.setAutoCreateColumnsFromModel(false);

		sorter.setComparator(5, comparatorVarighed);
		sorter.setComparator(6, comparatorVarighed);
		sorter.setComparator(7, comparatorVarighed);
		sorter.setSortsOnUpdates(false);

		tomPladsFilter = new RowFilter<Object, Object>() {
			@Override
			public boolean include(
					Entry<? extends Object, ? extends Object> entry) {
				return (entry.getValue(1) != null);
			}
		};
		sorter.setRowFilter(tomPladsFilter);
		
		table.setRowSorter(sorter);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(controller);

		setColumnWidths(3);

		Box horizontalBox_3 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_3 = new GridBagConstraints();
		gbc_horizontalBox_3.fill = GridBagConstraints.BOTH;
		gbc_horizontalBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox_3.gridx = 1;
		gbc_horizontalBox_3.gridy = 3;
		getContentPane().add(horizontalBox_3, gbc_horizontalBox_3);

		JPanel panel_1 = new JPanel();
		horizontalBox_3.add(panel_1);

		btnDrageringMange = new JButton("Til dragering");
		btnDrageringMange.addActionListener(controller);

		btnVisPalle = new JButton("Vis palle");
		panel_1.add(btnVisPalle);
		btnVisPalle.setEnabled(false);
		btnVisPalle.addActionListener(controller);
		panel_1.add(btnDrageringMange);

		btnTilFrdigvarelagerMange = new JButton("Til f\u00E6rdigvarelager");
		btnTilFrdigvarelagerMange.addActionListener(controller);
		panel_1.add(btnTilFrdigvarelagerMange);

		btnKassrMange = new JButton("Kass\u00E9r");
		btnKassrMange.addActionListener(controller);
		panel_1.add(btnKassrMange);

		// --Skal først kunne klikkes når der er valgt en række med en palle--//
		btnDrageringMange.setEnabled(false);
		btnTilFrdigvarelagerMange.setEnabled(false);
		btnKassrMange.setEnabled(false);

		menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("CheckBox.select"));
		setJMenuBar(menuBar);

		JMenu mnAdministrr = new JMenu("Administr\u00E9r");
		mnAdministrr.setMnemonic('a');
		mnAdministrr.setBackground(UIManager.getColor("CheckBox.select"));
		menuBar.add(mnAdministrr);

		mntmPaller = new JMenuItem("Paller");
		mntmPaller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminPalle.getInstance().setVisible(true);
			}
		});

		mntmBehandlinger = new JMenuItem("Behandlinger");
		mntmBehandlinger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminBehandling.getInstance(mainFrame).setVisible(true);
			}
		});
		mnAdministrr.add(mntmBehandlinger);
		mnAdministrr.add(mntmPaller);

		mntmProdukttyper = new JMenuItem("Produkttyper");
		mntmProdukttyper.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminProdukttype.getInstance().setVisible(true);
			}
		});
		mnAdministrr.add(mntmProdukttyper);

		mntmMellemlagerpladser = new JMenuItem("Mellemlagerpladser");
		mntmMellemlagerpladser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminMellemlagerPlads.getInstance(MainFrame.this)
				.setVisible(true);
			}
		});
		mnAdministrr.add(mntmMellemlagerpladser);

		mnOversigter = new JMenu("Oversigter");
		mnOversigter.setMnemonic('o');
		mnOversigter.setBackground(UIManager.getColor("Button.select"));
		menuBar.add(mnOversigter);

		mntmOversigtOverDragringshal = new JMenuItem("Drag\u00E9ringshal");
		mntmOversigtOverDragringshal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameOversigter.getInstance(mainFrame).setVisible(true);
				FrameOversigter.getInstance(mainFrame).getTabbedPane()
				.setSelectedIndex(0);
			}
		});
		mnOversigter.add(mntmOversigtOverDragringshal);

		mntmOversigtOverFrdigvarer = new JMenuItem("F\u00E6rdigvarelager");
		mntmOversigtOverFrdigvarer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameOversigter.getInstance(mainFrame).setVisible(true);
				FrameOversigter.getInstance(mainFrame).getTabbedPane()
				.setSelectedIndex(1);
			}
		});
		mnOversigter.add(mntmOversigtOverFrdigvarer);

		mntmOversigtOverKasseredevarer = new JMenuItem("Kasserede varer");
		mntmOversigtOverKasseredevarer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameOversigter.getInstance(mainFrame).setVisible(true);
				FrameOversigter.getInstance(mainFrame).getTabbedPane()
				.setSelectedIndex(2);
			}
		});
		mnOversigter.add(mntmOversigtOverKasseredevarer);

		mntmPaller_1 = new JMenuItem("Paller");
		mntmPaller_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameOversigter.getInstance(mainFrame).setVisible(true);
				FrameOversigter.getInstance(mainFrame).getTabbedPane()
				.setSelectedIndex(3);
			}
		});
		mnOversigter.add(mntmPaller_1);

		// -----------------------------------------------//
		
		timer = new Timer(1000, timecontroller);
		timer.start();
	}

	/**
	 * Singleton - metode der returnerer MainFrame og sikrer, at der kun
	 * bliver oprettet en instans af klassen
	 * @return
	 */
	public static MainFrame getInstance() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}

	/**
	 * Justerer søjlebredden og kaldes hver gang tabellen er blevet opdateret.
	 * @param antalTider 
	 * 			Kan både anvendes ved visning af 1 og 3 resterende tider
	 */
	private void setColumnWidths(int antalTider) {
		TableColumn column = null;
		for (int i = 0; i < 5 + antalTider; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 1) {
				column.setPreferredWidth(55);// Placering og Palle
				column.setMinWidth(50);
				column.setMaxWidth(55);
			}
			if (i == 4) {
				column.setPreferredWidth(40); // Antal
				column.setMinWidth(35);
				column.setMaxWidth(50);
			} else if (i >= 5) {
				column.setPreferredWidth(100);// Resterende tid
				column.setMinWidth(90);
				column.setMaxWidth(100);
			}

			else {
				column.setPreferredWidth(100);
			}
		}
	}

	/**
	 * Kaldes hvor brugerens valg kan udløse behov for at søge efter en ny palle. Dette sker dog
	 * kun hvis der er mere end én slags varer på pallen - ellers returneres null.
	 * 
	 * @param palle
	 * @return En 'ny' palle
	 * 
	 * @author Rita Holst Jacobsen
	 */
	private Palle askForPalle(Palle palle) {
		Palle nyPalle = null;
		if (!Service.getInstance().alleVarerErEns(palle)) {
			PalleDialog palleDialog = new PalleDialog(this, "Vælg ny palle",
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
	 * Kaldes hvor brugerens valg udløser behov for at søge efter en mellemlagerplads, der kan knyttes
	 * til den aktuelle palle (ny eller oprindelig). Dette sker derfor kun når mellemvarer sendes til
	 * tørring, og kun hvis den anvendte palle ikke allerede er knyttet til en mellemlagerplads.
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
					"Vælg ny placering",
					"Den valgte palle \ner endnu ikke placeret på mellemlageret.\n\nAngiv en ny placering:");
			placeringsDialog.setVisible(true);
			if (placeringsDialog.isOKed()) {
				nyMellemlagerPlads = placeringsDialog.getMellemlagerPlads();
			}
			placeringsDialog.dispose();
		}
		return nyMellemlagerPlads;

	}

	/**
	 * @author Rita Holst Jacobsen
	 *
	 */
	private class Controller implements ItemListener, ActionListener,
	ListSelectionListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (chckbxVisTommePladser.isSelected()) {
				((DefaultRowSorter<DefaultTableModel, Integer>) table.getRowSorter()).setRowFilter(null);
			} else {
				((DefaultRowSorter<DefaultTableModel, Integer>) table.getRowSorter()).setRowFilter(tomPladsFilter);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// Her afgøres hvilke knapper der må være klikbare hver gang en ny række vælges. 
			// Disse tjek foretages derfor ikke i actionPerformed-metoden.
			if (e.getSource() == table.getSelectionModel()) {
				btnVisPalle.setEnabled(false);
				btnDrageringMange.setEnabled(false);
				btnKassrMange.setEnabled(false);
				btnTilFrdigvarelagerMange.setEnabled(false);
				if (table.getSelectedRowCount() > 0
						&& table.getModel().getValueAt(table.getSelectedRow(),
								0) != null) {
					int row = table.convertRowIndexToModel(table
							.getSelectedRow()); 	// <---VIGTIGT! - Da vi sorterer/filtrerer, er tabellens og modellens
					// række-indicer ofte ikke de samme!
					MellemlagerPlads mellemlagerPlads = (MellemlagerPlads) table.getModel().getValueAt(row, 0);
					Palle palle = null;
					Produkttype produkttype = null;
					Delbehandling delbehandling = null;

					if (table.getModel().getValueAt(row, 1) != null) { // Der skal stå en palle for at en palle kan vises
						palle = (Palle) table.getModel().getValueAt(row, 1);
						btnVisPalle.setEnabled(true);
						if (table.getModel().getValueAt(row, 2) != null) { // Der skal være en mellemvare, dvs. der 
							//skal stå en produkttype før noget kan kasseres
							produkttype = (Produkttype) table.getModel().getValueAt(row, 2);
							btnKassrMange.setEnabled(true);
							// Der skal være en igangværende delbehandling før man kan sætte det næste trin i gang.
							// (Men delbehandling er null for færdigvarer og kasserede varer)
							if (table.getModel().getValueAt(row, 3) != null) {
								delbehandling = (Delbehandling) table.getModel().getValueAt(row, 3);
								// Den næste delbehandling skal være af typen dragering for at en dragering må sættes i gang.
								if (Service.getInstance().naesteDelbehandlingGyldig(palle,produkttype, delbehandling,
										DelbehandlingsType.DRAGERING)) { 
									btnDrageringMange.setEnabled(true);
								} 
								else if ((Service.getInstance().naesteDelbehandlingGyldig(palle, produkttype, 
										delbehandling, null))) { // Mellemvaren må kun sendes til færdigvarelageret hvis 
									//der ikke er flere delbehandlinger i behandlingen
									btnTilFrdigvarelagerMange.setEnabled(true);
								}
							}
						}
					}
				}
				notifyObservers();
				Service.getInstance().opdaterDatabase();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnTilfoejNyMellemvare) {
				subFrameTilfoejMellemvarer = new SubFrameTilfoejMellemvarer(
						MainFrame.this);
				subFrameTilfoejMellemvarer.setVisible(true);
				subFrameTilfoejMellemvarer.registerObserver(MainFrame.this);
				registerObserver(subFrameTilfoejMellemvarer);
				if (subFramePalleOversigt != null) {
					subFramePalleOversigt
					.registerObserver(subFrameTilfoejMellemvarer);
					subFrameTilfoejMellemvarer
					.registerObserver(subFramePalleOversigt);
				}
			} else if (table.getSelectedRowCount()>0){
				int row = table.convertRowIndexToModel(table.getSelectedRow()); // <---VIGTIGT!
				// - Da vi sorterer/filtrerer, er tabellens og
				//modellens række-indicer oftest ikke de samme"
				Palle palle = (Palle) table.getModel().getValueAt(row, 1);

				if (e.getSource() == btnVisPalle) {
					subFramePalleOversigt = new SubFramePalleOversigt(MainFrame.this, palle);
					subFramePalleOversigt.setVisible(true);
					if (subFrameTilfoejMellemvarer != null) {
						subFramePalleOversigt.registerObserver(subFrameTilfoejMellemvarer);
						subFrameTilfoejMellemvarer.registerObserver(subFramePalleOversigt);

					}
				} 
				else { // Nedenfor er alle de knapper, der udfører handlinger på paller/mellemvarer
					Produkttype produkttype = (Produkttype) table.getModel().getValueAt(row, 2);
					Delbehandling delbehandling = (Delbehandling) table.getModel().getValueAt(row, 3);
					Palle nyPalle = null;
					if (!Service.getInstance().alleVarerErEns(palle)) {
						nyPalle = askForPalle(palle);
					}
					if (e.getSource() == btnDrageringMange) {
						Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle,
								DelbehandlingsType.DRAGERING, nyPalle, null);
					}
					else if (e.getSource() == btnTilTrringMange) {
						MellemlagerPlads nyMellemlagerPlads = null;
						if (nyPalle != null && Service.getInstance().getMellemlagerPlads(nyPalle) == null) {
							nyMellemlagerPlads = askForPlacering(nyPalle);
						} 
						else {
							nyMellemlagerPlads = askForPlacering(palle);
						}
						Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle,
								DelbehandlingsType.TOERRING, null, null);
					}

					else if (e.getSource() == btnTilFrdigvarelagerMange) {
						Service.getInstance().sendTilFaerdigvareLager(
								produkttype, delbehandling, palle, nyPalle);
					}

					else if (e.getSource() == btnKassrMange) {
						Service.getInstance().kasserMellemvarer(produkttype, delbehandling, palle);
					}
				}
				update();
				notifyObservers();
			}
		}
	}
	
	private class TimeController implements ActionListener{

		@Override
      public void actionPerformed(ActionEvent e) {
			if (e.getSource()==timer){
				int selectedRow = table.getSelectedRow();
				update();
				setTableSelection(selectedRow);
			}
      }	
	}
	
	public void setTableSelection(int row){
		if (row >-1){
			table.setRowSelectionInterval(row, row);
		}
	}

	@Override
	public void update() {
		List<? extends SortKey> rs = table.getRowSorter().getSortKeys();

		dm3.setDataVector(Service.getInstance().generateViewDataMellemlagerOversigt3Tider(), columnNames3);
		
		setColumnWidths(3);
		table.getRowSorter().setSortKeys(rs);
	}
	


	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
}
