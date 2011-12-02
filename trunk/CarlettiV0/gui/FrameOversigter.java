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
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

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
	private JTable table;
	private JTabbedPane tabbedPane;
	private JLabel lblOversigtOverDrageringshallen;
	private JPanel panelDrageringshal;
	private JScrollPane scrollPane;
	private JButton btnAnnuller;
	private JButton btnSePalleoversigt;
	private JPanel panelFaerdigvarer;
	private JPanel panelKasserede;
	private JPanel panelPaller;

	public FrameOversigter(MainFrame mainFrame) {
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		table = new JTable();
		scrollPane.setViewportView(table);
		panelDrageringshal.setLayout(gl_panelDrageringshal);
		
		panelFaerdigvarer = new JPanel();
		tabbedPane.addTab("F\u00E6rdigvarelager", null, panelFaerdigvarer, null);
		
		panelKasserede = new JPanel();
		tabbedPane.addTab("Kasserede varer", null, panelKasserede, null);
		
		panelPaller = new JPanel();
		tabbedPane.addTab("Paller", null, panelPaller, null);
		this.mainFrame = mainFrame;



	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}
}
