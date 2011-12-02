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
	private JTabbedPane tabbedPane;
	private JTable table;

	public FrameOversigter(MainFrame mainFrame) {
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 512);
		contentPane = new JPanel();
		contentPane.setForeground(Color.PINK);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Drag\u00E9ringshal", null, panel, null);
		
		JLabel lblOversigtOverDrageringshallen = new JLabel("Oversigt over drageringshallen");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOversigtOverDrageringshallen)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblOversigtOverDrageringshallen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("F\u00E6rdigvarelager", null, panel_1, null);
		this.mainFrame = mainFrame;



	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}
}
