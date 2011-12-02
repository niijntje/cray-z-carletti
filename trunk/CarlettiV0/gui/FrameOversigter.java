package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import service.Service;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;

import model.Palle;
import java.awt.Font;

public class FrameOversigter extends JFrame {

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
	private JButton btnAnnuller;
	private JButton btnSePalleoversigt;
	private JPanel panelFaerdigvarer;
	private JPanel panelKasserede;
	private JPanel panelPaller;
	private DefaultTableModel tableDrageringshalModel;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JList listFaerdigvarer;
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

	public FrameOversigter(MainFrame mainFrame) {
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 550, 512);
		contentPane = new JPanel();
		contentPane.setForeground(Color.PINK);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		panelDrageringshal = new JPanel();
		tabbedPane.addTab("Drag\u00E9ringshal", null, panelDrageringshal, null);
		
		lblOversigtOverDrageringshallen = new JLabel("Oversigt over drageringshallen");
		
		scrollPane = new JScrollPane();
		
		JPanel panelDrageringshalKnapper = new JPanel();
		GroupLayout gl_panelDrageringshal = new GroupLayout(panelDrageringshal);
		gl_panelDrageringshal.setHorizontalGroup(
			gl_panelDrageringshal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDrageringshal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDrageringshal.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOversigtOverDrageringshallen)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelDrageringshalKnapper, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDrageringshal.setVerticalGroup(
			gl_panelDrageringshal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDrageringshal.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblOversigtOverDrageringshallen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelDrageringshalKnapper, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
		);
		
		btnAnnuller = new JButton("Annuller");
		panelDrageringshalKnapper.add(btnAnnuller);
		
		btnSePalleoversigt = new JButton("Se palleoversigt");
		panelDrageringshalKnapper.add(btnSePalleoversigt);
		
		columnNames = new String[] { "Palle#", "Produkttype", "Resterende tid"};
		data = Service.getInstance()
				.generateViewDataDrageringshal();
		tableDrageringshalModel = new DefaultTableModel(data, columnNames);
		tableDrageringshal = new JTable(tableDrageringshalModel);
		scrollPane.setViewportView(tableDrageringshal);
		panelDrageringshal.setLayout(gl_panelDrageringshal);
		
		
		panelFaerdigvarer = new JPanel();
		tabbedPane.addTab("F\u00E6rdigvarelager", null, panelFaerdigvarer, null);
		
		scrollPane_2 = new JScrollPane();
		
		lblOversigtOverFrdigvarelager = new JLabel("Oversigt over f\u00E6rdigvarelager");
		GroupLayout gl_panelFaerdigvarer = new GroupLayout(panelFaerdigvarer);
		gl_panelFaerdigvarer.setHorizontalGroup(
			gl_panelFaerdigvarer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFaerdigvarer.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFaerdigvarer.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
						.addComponent(lblOversigtOverFrdigvarelager))
					.addContainerGap())
		);
		gl_panelFaerdigvarer.setVerticalGroup(
			gl_panelFaerdigvarer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFaerdigvarer.createSequentialGroup()
					.addGap(40)
					.addComponent(lblOversigtOverFrdigvarelager)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(58, Short.MAX_VALUE))
		);
		
		listFaerdigvarer = new JList();
		scrollPane_2.setColumnHeaderView(listFaerdigvarer);
		panelFaerdigvarer.setLayout(gl_panelFaerdigvarer);
		listFaerdigvarer.setListData(Service.getInstance().getFaerdigvarer().toArray());
		panelKasserede = new JPanel();
		tabbedPane.addTab("Kasserede varer", null, panelKasserede, null);
		
		scrollPane_1 = new JScrollPane();
		
		lblKasseredeVarer = new JLabel("Oversigt over kasserede varer");
		GroupLayout gl_panelKasserede = new GroupLayout(panelKasserede);
		gl_panelKasserede.setHorizontalGroup(
			gl_panelKasserede.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelKasserede.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelKasserede.createParallelGroup(Alignment.LEADING)
						.addComponent(lblKasseredeVarer)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelKasserede.setVerticalGroup(
			gl_panelKasserede.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelKasserede.createSequentialGroup()
					.addGap(41)
					.addComponent(lblKasseredeVarer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		data = Service.getInstance().generateViewDataKasseredeVarer();
		columnNames = new String[]{"Bakkestregkode", "Produkttype", "Sidste delbehandling"};
		tabelKasseredeModel = new DefaultTableModel(data, columnNames);
		
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
			FrameOversigter.this.mainFrame.subFramePalleOversigt = new SubFramePalleOversigt(getMainFrame(), palle);
			FrameOversigter.this.mainFrame.subFramePalleOversigt.setVisible(true);
			FrameOversigter.this.mainFrame.registerObserver(FrameOversigter.this.mainFrame.subFramePalleOversigt);
			}
		});
		
		lblVisPalleoversigt = new JLabel("Vis palleoversigt");
		
		lblMarkrEnPalle = new JLabel("Mark\u00E9r en palle og tryk \"Vis palle\" for at se detaljer");
		lblMarkrEnPalle.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		GroupLayout gl_panelPaller = new GroupLayout(panelPaller);
		gl_panelPaller.setHorizontalGroup(
			gl_panelPaller.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPaller.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_panelPaller.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOversigtOverPaller)
						.addGroup(gl_panelPaller.createSequentialGroup()
							.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panelPaller.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVisPalleoversigt)
								.addGroup(gl_panelPaller.createParallelGroup(Alignment.TRAILING)
									.addComponent(btnVisPalle)
									.addComponent(lblMarkrEnPalle)))))
					.addContainerGap(119, Short.MAX_VALUE))
		);
		gl_panelPaller.setVerticalGroup(
			gl_panelPaller.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPaller.createSequentialGroup()
					.addGap(31)
					.addComponent(lblOversigtOverPaller)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelPaller.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelPaller.createSequentialGroup()
							.addComponent(lblVisPalleoversigt)
							.addGap(7)
							.addComponent(lblMarkrEnPalle)
							.addGap(9)
							.addComponent(btnVisPalle)))
					.addContainerGap(34, Short.MAX_VALUE))
		);
		
		listPaller = new JList();
		scrollPane_3.setViewportView(listPaller);
		panelPaller.setLayout(gl_panelPaller);
		this.mainFrame = mainFrame;
		listPaller.setListData(Service.getInstance().getPaller().toArray());


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
}
