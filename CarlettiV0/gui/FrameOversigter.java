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

import model.Palle;
import service.Service;

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
	private JPanel panel;
	private JTable table;
	private JButton btnAnnuller;
	private JButton btnSePalle;
	private MainFrame mainFrame;

	public FrameOversigter(MainFrame mainFrame) {
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 512);
		contentPane = new JPanel();
		contentPane.setForeground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.mainFrame = mainFrame;

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addComponent(tabbedPane, Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE));

		data = Service.getInstance().generateViewDataMellemlagerOversigt();
		columnNames = new String[] { "Plads#", "Palle#", "Produkttype",
				"Delbehandling", "Antal", "Varighed" };
		drageringModel = new DefaultTableModel(data, columnNames);

		panel = new JPanel();
		tabbedPane.addTab("Drag\u00E9ringshal", null, panel, null);

		table = new JTable();

		btnAnnuller = new JButton("Annuller");
		btnAnnuller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnSePalle = new JButton("Se oversigt over palle");
		btnSePalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				Palle palle = (Palle) table.getValueAt(0, row);
				SubFramePalleOversigt palleOversigt = new SubFramePalleOversigt(
						getMainFrame(), palle);
				palleOversigt.setVisible(true);
			}
		});

		JLabel lblOversigOverDragringshallen = new JLabel(
				"Oversigt over drag\u00E9ringshallen");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap(122, Short.MAX_VALUE)
								.addComponent(btnAnnuller,
										GroupLayout.PREFERRED_SIZE, 97,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSePalle).addGap(116))
				.addGroup(
						Alignment.LEADING,
						gl_panel.createSequentialGroup().addContainerGap()
								.addComponent(lblOversigOverDragringshallen)
								.addContainerGap(320, Short.MAX_VALUE))
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 519,
						Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(23)
								.addComponent(lblOversigOverDragringshallen)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(table,
										GroupLayout.PREFERRED_SIZE, 295,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED,
										30, Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														btnAnnuller,
														GroupLayout.PREFERRED_SIZE,
														25,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														btnSePalle,
														GroupLayout.PREFERRED_SIZE,
														25,
														GroupLayout.PREFERRED_SIZE))
								.addGap(33)));
		panel.setLayout(gl_panel);

		faerdigvarerModel = new DefaultTableModel();

		faerdigvarer = new JTable();
		tabbedPane.addTab("Faerdigvarelager", null, faerdigvarer, null);

		kasseredeModel = new DefaultTableModel();
		kasserede = new JTable();
		tabbedPane.addTab("Kasserede varer", null, kasserede, null);

		paller = new JTable();
		tabbedPane.addTab("Paller", null, paller, null);
		contentPane.setLayout(gl_contentPane);

	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

}
