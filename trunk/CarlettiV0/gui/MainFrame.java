package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultRowSorter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import model.Delbehandling;
import model.Dragering;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import model.Toerring;
import service.Service;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;

/**
 * @author nijntje
 * 
 */
public class MainFrame extends JFrame implements Observer, Subject {

	private JTable table;
	private Controller controller;
	private RowFilter<Object, Object> tomPladsFilter;
	private DefaultTableModel dm;
	private Object[][] data;
	private String[] columnNames;
	private JCheckBox chckbxVisTommePladser;
	private JButton btnVisPalle;
	private JButton btnPlacrPalle;
	private JPanel panel_2;
	private JButton btnTilfoejNyMellemvare;
	private JPanel panel2;
	private JScrollPane scrollPane;
	private JPanel panel;
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
	public SubFramePalleOversigt subFramePalleOversigt;
	private SubFrameAdminPalle subframeAdminPalle;
	private SubFrameAdminProdukttype subFrameAdminProdukt;
	private SubFrameAdminMellemlagerPlads subFrameAdminMellemlagerPlads;
	private SubFramePlacerPalle subFramePlacerPalle;
	public Object subFrameAdminBehandling;
	private JMenu mnGaaTil;
	private JMenuItem mntmOversigtOverDragringshal;
	private JMenuItem mntmOversigtOverFrdigvarer;
	private JMenuItem mntmOversigtOverMellemvarelager;
	private JMenuItem mntmOversigtOverKasseredevarer;

	private MainFrame() {
		this.observers = new ArrayList<Observer>();
		getContentPane().setBackground(Color.PINK);
		controller = new Controller();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mellemvarelager - Oversigt");
		this.setLocation(0, 0);
		this.setSize(800, 600);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 711, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 39, 50, 321, 21, 68, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 1.0, 0.0,
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
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel2.add(scrollPane, gbc_scrollPane);

		columnNames = new String[] { "Plads#", "Palle#", "Produkttype",
				"Delbehandling", "Antal", "min", "Resterende tid", "max" };
		data = Service.getInstance()
				.generateViewDataMellemlagerOversigt3Tider();
		dm = new DefaultTableModel(data, columnNames);

		tomPladsFilter = new RowFilter<Object, Object>() {
			@Override
			public boolean include(
					Entry<? extends Object, ? extends Object> entry) {
				return (entry.getValue(1) != null);
			}
		};

		table = new JTable(dm);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(controller);
		((DefaultRowSorter<DefaultTableModel, Integer>) table.getRowSorter())
				.setRowFilter(tomPladsFilter);

		setColumnWidths();

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

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(panel, gbc_panel);

		menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("CheckBox.select"));
		setJMenuBar(menuBar);

		JMenu mnAdministrr = new JMenu("Administr\u00E9r");
		mnAdministrr.setMnemonic('a');
		mnAdministrr.setBackground(UIManager.getColor("CheckBox.select"));
		menuBar.add(mnAdministrr);

		mntmPaller = new JMenuItem("Paller");
		mntmPaller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminPalle.getInstance().setVisible(true);
			}
		});

		mntmBehandlinger = new JMenuItem("Behandlinger");
		mntmBehandlinger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminBehandling.getInstance().setVisible(true);
			}
		});
		mnAdministrr.add(mntmBehandlinger);
		mnAdministrr.add(mntmPaller);

		mntmProdukttyper = new JMenuItem("Produkttyper");
		mntmProdukttyper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminProdukttype.getInstance().setVisible(true);
			}
		});
		mnAdministrr.add(mntmProdukttyper);

		mntmMellemlagerpladser = new JMenuItem("Mellemlagerpladser");
		mntmMellemlagerpladser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SubFrameAdminMellemlagerPlads.getInstance().setVisible(true);
			}
		});
		mnAdministrr.add(mntmMellemlagerpladser);

		mnGaaTil = new JMenu("Gaa til\u2026");
		mnGaaTil.setBackground(UIManager.getColor("Button.select"));
		menuBar.add(mnGaaTil);

		mntmOversigtOverDragringshal = new JMenuItem(
				"Oversigt over drag\u00E9ringshal");
		mnGaaTil.add(mntmOversigtOverDragringshal);

		mntmOversigtOverFrdigvarer = new JMenuItem(
				"Oversigt over f\u00E6rdigvarelager");
		mnGaaTil.add(mntmOversigtOverFrdigvarer);

		mntmOversigtOverKasseredevarer = new JMenuItem(
				"Oversigt over kasseredevarer");
		mnGaaTil.add(mntmOversigtOverKasseredevarer);

		mntmOversigtOverMellemvarelager = new JMenuItem(
				"Oversigt over mellemvarelager");
		mnGaaTil.add(mntmOversigtOverMellemvarelager);

		// -----------------------------------------------//
	}

	public static MainFrame getInstance() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}

	private void setColumnWidths() {
		TableColumn column = null;
		for (int i = 0; i < 8; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 1) {
				column.setPreferredWidth(75);// Placering og Palle
				column.setMinWidth(70);
				column.setMaxWidth(85);
			}
			if (i == 4) {
				column.setPreferredWidth(40); // Antal
				column.setMinWidth(35);
				column.setMaxWidth(50);
			} else if (i == 5 || i == 6 || i == 7) {
				column.setPreferredWidth(85);// Resterende tid
				column.setMinWidth(80);
				column.setMaxWidth(90);
			} else {
				column.setPreferredWidth(100);

			}
		}

	}

	private class Controller implements ItemListener, ActionListener,
			ListSelectionListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (chckbxVisTommePladser.isSelected()) {
				((DefaultRowSorter<DefaultTableModel, Integer>) table
						.getRowSorter()).setRowFilter(null);
			} else {
				((DefaultRowSorter<DefaultTableModel, Integer>) table
						.getRowSorter()).setRowFilter(tomPladsFilter);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == table.getSelectionModel()) {
				btnVisPalle.setEnabled(false);
				btnDrageringMange.setEnabled(false);
				btnKassrMange.setEnabled(false);
				btnTilFrdigvarelagerMange.setEnabled(false);
				if (table.getSelectedRowCount() > 0
						&& table.getValueAt(table.getSelectedRow(), 0) != null) {
					if (table.getValueAt(table.getSelectedRow(), 1) != null) { // Der
																				// skal
																				// stå
																				// en
																				// palle
																				// for
																				// at
																				// en
																				// palle
																				// kan
																				// vises
						btnVisPalle.setEnabled(true);

						if (table.getValueAt(table.getSelectedRow(), 2) != null) { // Der
																					// skal
																					// være
																					// en
																					// mellemvare,
																					// dvs.
																					// der
																					// skal
																					// stå
																					// en
																					// produkttype,
																					// før
																					// noget
																					// kan
																					// kasseres
							btnKassrMange.setEnabled(true);

							int row = table.getSelectedRow();
							Delbehandling delbehandling = (Delbehandling) table
									.getModel().getValueAt(row, 3);
							if (delbehandling != null) { // Der skal være en
															// igangværende
															// delbehandling før
															// man kan sætte det
															// næste trin i
															// gang.
								if (Service.getInstance()
										.erNaesteDelbehandling(delbehandling,
												Dragering.class)) { // Den næste
																	// delbehandling
																	// skal være
																	// af typen
																	// dragering,
																	// for at
																	// denne må
																	// sættes i
																	// gang
									btnDrageringMange.setEnabled(true);
								} else if (Service.getInstance()
										.erNaesteDelbehandling(delbehandling,
												null)) { // Mellemvaren må kun
															// sendes til
															// færdigvarelageret
															// hvis der ikke er
															// flere
															// delbehandlinger i
															// behandlingen
									btnTilFrdigvarelagerMange.setEnabled(true);
								}
							}
						}
					}
				}
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
			} else {
				Palle palle = (Palle) table.getValueAt(table.getSelectedRow(),
						1);
				if (e.getSource() == btnVisPalle) {

					MainFrame.this.subFramePalleOversigt = new SubFramePalleOversigt(
							MainFrame.this, palle);
					MainFrame.this.subFramePalleOversigt.setVisible(true);
					subFramePalleOversigt.registerObserver(MainFrame.this);
					MainFrame.this.registerObserver(subFramePalleOversigt);
					if (subFrameTilfoejMellemvarer != null) {
						subFramePalleOversigt
								.registerObserver(subFrameTilfoejMellemvarer);
						subFrameTilfoejMellemvarer
								.registerObserver(subFramePalleOversigt);
					}
				} else if (e.getSource() == btnDrageringMange) {
					if (table.getSelectedRowCount() == 0) {
						palle.startDelbehandling(null, Dragering.class);
					}

					else {
						int row = table.getSelectedRow();
						Produkttype produkttype = (Produkttype) table
								.getValueAt(row, 2);
						Delbehandling delbehandling = (Delbehandling) table
								.getValueAt(row, 3);
						Service.getInstance().sendTilNaesteDelbehandling(
								produkttype, delbehandling, palle,
								Dragering.class, null);
					}
				}

				else if (e.getSource() == btnTilTrringMange) {
					if (table.getSelectedRowCount() == 0) {
						Service.getInstance().sendTilNaesteDelbehandling(null,
								palle, Toerring.class, null);
					}

					else {
						int row = table.getSelectedRow();
						Produkttype produkttype = (Produkttype) table
								.getValueAt(row, 2);
						Delbehandling delbehandling = (Delbehandling) table
								.getValueAt(row, 3);
						Service.getInstance().sendTilNaesteDelbehandling(
								produkttype, delbehandling, palle,
								Toerring.class, null);
					}
				}

				else if (e.getSource() == btnTilFrdigvarelagerMange) {
					if (table.getSelectedRowCount() == 0) {
						Service.getInstance().sendTilFærdigvareLager(null,
								palle);
					} else {
						int row = table.getSelectedRow();
						Produkttype produkttype = (Produkttype) table
								.getValueAt(row, 2);
						Delbehandling delbehandling = (Delbehandling) table
								.getValueAt(row, 3);
						Service.getInstance().sendTilFærdigvareLager(
								produkttype, delbehandling, palle, null);
					}
				}

				else if (e.getSource() == btnKassrMange) {
					if (table.getSelectedRowCount() == 0) {
						Service.getInstance().kasserMellemvarer(null, palle);
					} else {
						int row = table.getSelectedRow();
						Produkttype produkttype = (Produkttype) table
								.getValueAt(row, 2);
						Delbehandling delbehandling = (Delbehandling) table
								.getValueAt(row, 3);
						Service.getInstance().kasserMellemvarer(produkttype,
								delbehandling, palle);
					}
				}

				update();
				notifyObservers();
			}
		}

	}

	@Override
	public void update() {
		dm.setDataVector(Service.getInstance()
				.generateViewDataMellemlagerOversigt3Tider(), columnNames);
		setColumnWidths();
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
