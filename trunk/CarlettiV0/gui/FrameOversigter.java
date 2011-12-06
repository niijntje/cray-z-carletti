package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import model.Delbehandling;
import model.Delbehandling.DelbehandlingsType;
import model.MellemlagerPlads;
import model.Palle;
import model.Produkttype;
import service.Service;

public class FrameOversigter extends JFrame implements Observer, Subject {

	private JPanel contentPane;
	private JTable faerdigvarer;
	private JTable kasserede;
	private DefaultTableModel drageringModel;
	private DefaultTableModel faerdigvarerModel;
	private DefaultTableModel kasseredeModel;
	private String[] columnNames;
	private Object[][] data;
	private JTable paller;
	private JPanel panelDragering;
	private MainFrame mainFrame;
	private JTable tableDrageringshal;
	private JTabbedPane tabbedPane;
	private JLabel lblOversigtOverDrageringshallen;
	private JPanel panelDrageringshal;
	private JScrollPane scrollPane;
	private JButton btnSePalleoversigt;
	private JPanel panelFaerdigvarer;
	private JPanel panelKasserede;
	private JPanel panelPaller;
	private DefaultTableModel tableDrageringshalModel;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JLabel lblOversigtOverFrdigvarelager;
	private JLabel lblOversigtOverPaller;
	private JScrollPane scrollPane_3;
	private JList listPaller;
	private JLabel lblKasseredeVarer;
	private JButton btnVisPalle;
	private JLabel lblVisPalleoversigt;
	private JLabel lblMarkrEnPalle;
	private JTable tableKasseredeVarer;
	private DefaultTableModel tabelKasseredeModel;
	private JTable tableFaerdigvarelager;
	private DefaultTableModel tableFaerdigModel;
	private String[] columnNamesDrageringsTable;
	private Object[][] dataDrageringshal;
	private String[] columnNamesFaerdig;
	private Object[][] dataFaerdigvarer;
	private Object[][] dataKasserede;
	private String[] columnNamesKasserede;
	private static FrameOversigter frameOversigter;
	private JPanel panel_2;
	private ArrayList<Observer>observers;

	private FrameOversigter(MainFrame mainFrame) {
		mainFrame.registerObserver(this);
		this.observers = new ArrayList<Observer>();
		registerObserver(mainFrame);
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 550, 512);
		contentPane = new JPanel();
		contentPane.setForeground(Color.PINK);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setTitle("Oversigter");

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		panelDrageringshal = new JPanel();
		tabbedPane.addTab("Drag\u00E9ringshal", null, panelDrageringshal, null);

		lblOversigtOverDrageringshallen = new JLabel(
				"Oversigt over drageringshallen");

		scrollPane = new JScrollPane();

		JPanel panelDrageringshalKnapper = new JPanel();
		GroupLayout gl_panelDrageringshal = new GroupLayout(panelDrageringshal);
		gl_panelDrageringshal
				.setHorizontalGroup(gl_panelDrageringshal
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelDrageringshal
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelDrageringshal
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPane,
																GroupLayout.PREFERRED_SIZE,
																516,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panelDrageringshalKnapper,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																517,
																Short.MAX_VALUE)
														.addComponent(
																lblOversigtOverDrageringshallen))
										.addContainerGap()));
		gl_panelDrageringshal.setVerticalGroup(gl_panelDrageringshal
				.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panelDrageringshal
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblOversigtOverDrageringshallen)
								.addGap(18)
								.addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 333,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelDrageringshalKnapper,
										GroupLayout.PREFERRED_SIZE, 66,
										GroupLayout.PREFERRED_SIZE)));

		btnSePalleoversigt = new JButton("Se palleoversigt");
		btnSePalleoversigt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableDrageringshal
						.convertRowIndexToModel(tableDrageringshal
								.getSelectedRow());
				Palle palle = (Palle) tableDrageringshalModel
						.getValueAt(row, 0);
				if (palle != null) {
					FrameOversigter.this.mainFrame.subFramePalleOversigt = new SubFramePalleOversigt(
							FrameOversigter.this.mainFrame, palle);
					FrameOversigter.this.mainFrame.subFramePalleOversigt
							.update();
					FrameOversigter.this.mainFrame.subFramePalleOversigt
							.setVisible(true);
					FrameOversigter.this.mainFrame.subFramePalleOversigt
							.registerObserver(FrameOversigter.this.mainFrame);
					FrameOversigter.this.mainFrame
							.registerObserver(FrameOversigter.this.mainFrame.subFramePalleOversigt);
				}

			}
		});
		panelDrageringshalKnapper.add(btnSePalleoversigt);

		JButton btnSendTilTrring = new JButton("Send til t\u00F8rring");
		btnSendTilTrring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableDrageringshal
						.convertRowIndexToModel(tableDrageringshal
								.getSelectedRow());
				Palle palle = (Palle) tableDrageringshalModel.getValueAt(row, 0);
				Produkttype produkttype = (Produkttype) tableDrageringshalModel.getValueAt(row, 1);
				Delbehandling delbehandling = (Delbehandling) tableDrageringshalModel.getValueAt(row, 2);

				Palle nyPalle = askForPalle(palle);
				MellemlagerPlads nyMellemlagerPlads  = null;
				if(nyPalle != null){
					 nyMellemlagerPlads = askForPlacering(nyPalle);
				} else{
					nyMellemlagerPlads = askForPlacering(palle);
				}

				Service.getInstance().sendTilNaesteDelbehandling(produkttype, delbehandling, palle, DelbehandlingsType.TOERRING, nyPalle, nyMellemlagerPlads);
				update();
				notifyObservers();
			}
		});
		panelDrageringshalKnapper.add(btnSendTilTrring);

		columnNamesDrageringsTable = new String[] { "Palle#", "Produkttype",
				"Delbehandling", "Antal", "Resterende tid" };
		dataDrageringshal = Service.getInstance()
				.generateViewDataDrageringshal();
		tableDrageringshalModel = new DefaultTableModel(dataDrageringshal,
				columnNamesDrageringsTable);
		tableDrageringshal = new JTable(tableDrageringshalModel);
		scrollPane.setViewportView(tableDrageringshal);
		panelDrageringshal.setLayout(gl_panelDrageringshal);

		panelFaerdigvarer = new JPanel();
		tabbedPane
				.addTab("F\u00E6rdigvarelager", null, panelFaerdigvarer, null);

		scrollPane_2 = new JScrollPane();

		lblOversigtOverFrdigvarelager = new JLabel(
				"Oversigt over f\u00E6rdigvarelager");

		JPanel panel = new JPanel();
		GroupLayout gl_panelFaerdigvarer = new GroupLayout(panelFaerdigvarer);
		gl_panelFaerdigvarer
				.setHorizontalGroup(gl_panelFaerdigvarer
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFaerdigvarer
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelFaerdigvarer
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPane_2,
																GroupLayout.DEFAULT_SIZE,
																517,
																Short.MAX_VALUE)
														.addComponent(
																panel,
																GroupLayout.PREFERRED_SIZE,
																517,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblOversigtOverFrdigvarelager))
										.addContainerGap()));
		gl_panelFaerdigvarer.setVerticalGroup(gl_panelFaerdigvarer
				.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panelFaerdigvarer
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblOversigtOverFrdigvarelager)
								.addGap(18)
								.addComponent(scrollPane_2,
										GroupLayout.DEFAULT_SIZE, 332,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel,
										GroupLayout.PREFERRED_SIZE, 66,
										GroupLayout.PREFERRED_SIZE)));

		columnNamesFaerdig = new String[] { "Bakkestregkode", "Produkttype",
				"Mellemvarestatus" };
		dataFaerdigvarer = Service.getInstance().generateViewFaerdigvarer();

		tableFaerdigModel = new DefaultTableModel(dataFaerdigvarer,
				columnNamesFaerdig);

		tableFaerdigvarelager = new JTable(tableFaerdigModel);
		scrollPane_2.setViewportView(tableFaerdigvarelager);
		panelFaerdigvarer.setLayout(gl_panelFaerdigvarer);
		panelKasserede = new JPanel();
		tabbedPane.addTab("Kasserede varer", null, panelKasserede, null);

		scrollPane_1 = new JScrollPane();

		lblKasseredeVarer = new JLabel("Oversigt over kasserede varer");

		JPanel panel_1 = new JPanel();
		GroupLayout gl_panelKasserede = new GroupLayout(panelKasserede);
		gl_panelKasserede
				.setHorizontalGroup(gl_panelKasserede
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelKasserede
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelKasserede
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane_1,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																517,
																Short.MAX_VALUE)
														.addGroup(
																gl_panelKasserede
																		.createParallelGroup(
																				Alignment.LEADING)
																		.addComponent(
																				lblKasseredeVarer)
																		.addComponent(
																				panel_1,
																				GroupLayout.PREFERRED_SIZE,
																				517,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_panelKasserede.setVerticalGroup(gl_panelKasserede
				.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panelKasserede
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblKasseredeVarer)
								.addGap(18)
								.addComponent(scrollPane_1,
										GroupLayout.DEFAULT_SIZE, 332,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_1,
										GroupLayout.PREFERRED_SIZE, 66,
										GroupLayout.PREFERRED_SIZE)));
		dataKasserede = Service.getInstance().generateViewDataKasseredeVarer();
		columnNamesKasserede = new String[] { "Bakkestregkode", "Produkttype",
				"Mellemvarestatus" };
		tabelKasseredeModel = new DefaultTableModel(dataKasserede,
				columnNamesKasserede);

		tableKasseredeVarer = new JTable(tabelKasseredeModel);
		scrollPane_1.setViewportView(tableKasseredeVarer);
		panelKasserede.setLayout(gl_panelKasserede);
		panelPaller = new JPanel();
		tabbedPane.addTab("Paller", null, panelPaller, null);

		lblOversigtOverPaller = new JLabel("Oversigt over paller");

		scrollPane_3 = new JScrollPane();

		btnVisPalle = new JButton("Vis palle");
		btnVisPalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Palle palle = (Palle) listPaller.getSelectedValue();
				FrameOversigter.this.mainFrame.subFramePalleOversigt = new SubFramePalleOversigt(
						getMainFrame(), palle);
				FrameOversigter.this.mainFrame.subFramePalleOversigt
						.setVisible(true);
				FrameOversigter.this.mainFrame
						.registerObserver(FrameOversigter.this.mainFrame.subFramePalleOversigt);
			}
		});

		lblVisPalleoversigt = new JLabel("Vis palleoversigt");

		lblMarkrEnPalle = new JLabel(
				"Mark\u00E9r en palle og tryk \"Vis palle\" for at se detaljer");
		lblMarkrEnPalle.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		panel_2 = new JPanel();

		listPaller = new JList();
		listPaller.setListData(Service.getInstance().getPaller().toArray());
		GroupLayout gl_panelPaller = new GroupLayout(panelPaller);
		gl_panelPaller
				.setHorizontalGroup(gl_panelPaller
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelPaller
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelPaller
														.createParallelGroup(
																Alignment.TRAILING,
																false)
														.addGroup(
																gl_panelPaller
																		.createSequentialGroup()
																		.addComponent(
																				listPaller,
																				GroupLayout.PREFERRED_SIZE,
																				149,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				scrollPane_3,
																				0,
																				0,
																				Short.MAX_VALUE))
														.addComponent(
																lblOversigtOverPaller,
																Alignment.LEADING))
										.addGap(18)
										.addGroup(
												gl_panelPaller
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblVisPalleoversigt)
														.addGroup(
																gl_panelPaller
																		.createSequentialGroup()
																		.addGap(1)
																		.addGroup(
																				gl_panelPaller
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								btnVisPalle)
																						.addComponent(
																								lblMarkrEnPalle))))
										.addContainerGap(82, Short.MAX_VALUE))
						.addGroup(
								Alignment.TRAILING,
								gl_panelPaller
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(panel_2,
												GroupLayout.PREFERRED_SIZE,
												517, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		gl_panelPaller
				.setVerticalGroup(gl_panelPaller
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelPaller
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblOversigtOverPaller)
										.addGroup(
												gl_panelPaller
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelPaller
																		.createSequentialGroup()
																		.addGap(40)
																		.addComponent(
																				scrollPane_3,
																				GroupLayout.PREFERRED_SIZE,
																				190,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panelPaller
																		.createSequentialGroup()
																		.addGap(27)
																		.addGroup(
																				gl_panelPaller
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelPaller
																										.createSequentialGroup()
																										.addComponent(
																												lblVisPalleoversigt)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panelPaller
																														.createSequentialGroup()
																														.addComponent(
																																lblMarkrEnPalle)
																														.addPreferredGap(
																																ComponentPlacement.RELATED)
																														.addComponent(
																																btnVisPalle)))
																						.addComponent(
																								listPaller,
																								GroupLayout.DEFAULT_SIZE,
																								323,
																								Short.MAX_VALUE))))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(panel_2,
												GroupLayout.PREFERRED_SIZE, 66,
												GroupLayout.PREFERRED_SIZE)));
		panelPaller.setLayout(gl_panelPaller);
		this.mainFrame = mainFrame;

	}

	public static FrameOversigter getInstance(MainFrame mainFrame) {
		if (frameOversigter == null) {
			frameOversigter = new FrameOversigter(mainFrame);
		}
		return frameOversigter;
	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * 
	 * @return
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	private Palle askForPalle(Palle oprindeligPalle){
		Palle nyPalle = null;
		if (!Service.getInstance().alleVarerErEns(oprindeligPalle)){
			PalleDialog palleDialog = new PalleDialog(this, "Vælg ny palle", "Kun nogle mellemvarer\nplukkes fra pallen.\n\nAngiv ny palle til disse:");
			palleDialog.setVisible(true);
			if (palleDialog.isOKed()){
				nyPalle = palleDialog.getPalle();
			}
			palleDialog.dispose();
		}
		return nyPalle;
	}

	private MellemlagerPlads askForPlacering(Palle palle){
		MellemlagerPlads nyMellemlagerPlads = Service.getInstance().getMellemlagerPlads(palle);
		if (nyMellemlagerPlads==null){
			PlaceringsDialog placeringsDialog = new PlaceringsDialog(this, "Vælg ny placering", "Den valgte palle \ner endnu ikke placeret på mellemlageret.\n\nAngiv en ny placering:");
			placeringsDialog.setVisible(true);
			if (placeringsDialog.isOKed()){
				nyMellemlagerPlads = placeringsDialog.getMellemlagerPlads();
			}
			placeringsDialog.dispose();
		}
		return nyMellemlagerPlads;

	}
	
	@Override
	public void update() {
		tableDrageringshalModel.setDataVector(Service.getInstance()
				.generateViewDataDrageringshal(), columnNamesDrageringsTable);
		tableFaerdigModel.setDataVector(Service.getInstance()
				.generateViewFaerdigvarer(), columnNamesFaerdig);
		tabelKasseredeModel.setDataVector(Service.getInstance()
				.generateViewDataKasseredeVarer(), columnNamesKasserede);
		listPaller.setListData(Service.getInstance().getPaller().toArray());

	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers){
			o.update();
		}
		
	}
}
