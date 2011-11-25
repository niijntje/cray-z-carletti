/**
 * 
 */
package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.Mellemvare;
import model.Palle;

import service.Service;
import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import java.awt.Panel;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.FlowLayout;


/**
 * @author nijntje
 *
 */
public class SubFramePalleOversigt extends JFrame implements Observer{

	private Palle palle;
	private MainFrame mainFrame;
	private DefaultComboBoxModel cBoxModel = new DefaultComboBoxModel();
	private JPanel panel1, panel2, panel3;
	private JTable table;
	private JLabel label;
	private JPanel panel;






	public SubFramePalleOversigt(MainFrame mainFrame, Palle palle){
		getContentPane().setBackground(Color.PINK);
		this.mainFrame = mainFrame;
		this.palle = palle;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Oversigt over palle");
		this.setLocation(400, 200);
		this.setSize(420, 720);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{203, 202, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{35, 0, 1, 103, 0, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);


		panel1 = new JPanel();
		panel1.setOpaque(true);
		panel1.setBounds(5, 5, 100, 50);

		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.gridheight = 2;
		gbc_panel1.insets = new Insets(0, 0, 5, 5);
		gbc_panel1.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel1.gridwidth = 2;
		gbc_panel1.gridx = 0;
		gbc_panel1.gridy = 0;
		getContentPane().add(panel1, gbc_panel1);

		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[]{39, 112, 296, 0};
		gbl_panel1.rowHeights = new int[]{29, 9, 0};
		gbl_panel1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel1.setLayout(gbl_panel1);


		JLabel lblPalle = new JLabel("Palle");
		lblPalle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblPalle = new GridBagConstraints();
		gbc_lblPalle.anchor = GridBagConstraints.NORTH;
		gbc_lblPalle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalle.gridx = 1;
		gbc_lblPalle.gridy = 0;
		panel1.add(lblPalle, gbc_lblPalle);

		JLabel lblStregkode = new JLabel("# "+Service.getInstance().getStregkode(palle));
		lblStregkode.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblStregkode.setToolTipText("Stregkode");
		GridBagConstraints gbc_lblStregkode = new GridBagConstraints();
		gbc_lblStregkode.insets = new Insets(0, 0, 5, 0);
		gbc_lblStregkode.anchor = GridBagConstraints.NORTH;
		gbc_lblStregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStregkode.gridx = 2;
		gbc_lblStregkode.gridy = 0;
		panel1.add(lblStregkode, gbc_lblStregkode);

		JLabel lblAntalBakker = new JLabel("Antal bakker:");
		GridBagConstraints gbc_lblAntalBakker = new GridBagConstraints();
		gbc_lblAntalBakker.insets = new Insets(0, 0, 0, 5);
		gbc_lblAntalBakker.gridx = 1;
		gbc_lblAntalBakker.gridy = 1;
		panel1.add(lblAntalBakker, gbc_lblAntalBakker);

		JLabel label_1 = new JLabel(Service.getInstance().getMellemvarer(palle).size()+" stk.");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 1;
		panel1.add(label_1, gbc_label_1);

		JPanel panel2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 2;
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		getContentPane().add(panel2, gbc_panel_2);

		String[] columnNames = {"Produkttype", "Delbehandling", "Antal", "Resterende tid"};
		Object[][] data = Service.getInstance().generateViewDataProdukttypeDelbehandlingAntalTid(palle);
		DefaultTableModel dm = new DefaultTableModel(data, columnNames);
		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[]{300, 0};
		gbl_panel2.rowHeights = new int[]{0, 70};
		gbl_panel2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel2.rowWeights = new double[]{0.0, 0.0};
		panel2.setLayout(gbl_panel2);
		
		JLabel lblOverblik = new JLabel("Overblik");
		GridBagConstraints gbc_lblOverblik = new GridBagConstraints();
		gbc_lblOverblik.anchor = GridBagConstraints.WEST;
		gbc_lblOverblik.insets = new Insets(0, 0, 5, 0);
		gbc_lblOverblik.gridx = 0;
		gbc_lblOverblik.gridy = 0;
		panel2.add(lblOverblik, gbc_lblOverblik);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel2.add(scrollPane, gbc_scrollPane);
		
		JTable table1 = new JTable(dm);
		scrollPane.setViewportView(table1);

		JPanel panel3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 5;
		getContentPane().add(panel3, gbc_panel_3);


//		panel2 = new JPanel();
//		panel2.setBackground(Color.RED);
//		panel2.setOpaque(true);

		//		
		//		JLabel lblOpsummering = new JLabel("Opsummering");
		//		lblOpsummering.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		//		GridBagConstraints gbc_lblOpsummering = new GridBagConstraints();
		//		gbc_lblOpsummering.anchor = GridBagConstraints.WEST;
		//		gbc_lblOpsummering.insets = new Insets(0, 0, 5, 0);
		//		gbc_lblOpsummering.gridx = 1;
		//		gbc_lblOpsummering.gridy = 1;
		//		panel.add(lblOpsummering, gbc_lblOpsummering);

		//		JScrollPane scrollPaneT = new JScrollPane();
		//		scrollPaneT.setOpaque(true);
		//
		//		
		//		GridBagConstraints gbc_scrollPaneT = new GridBagConstraints();
		//		gbc_scrollPaneT.fill = GridBagConstraints.BOTH;
		//		gbc_scrollPaneT.gridwidth = 2;
		//		gbc_scrollPaneT.insets = new Insets(0, 0, 5, 5);
		//		gbc_scrollPaneT.gridx = 0;
		//		gbc_scrollPaneT.gridy = 2;
		//		
		//		panel2.add(scrollPaneT, gbc_scrollPaneT);
		//		
		//		String[] columnNames = {"Produkttype", "Delbehandling", "Antal", "Resterende tid"};
		//		Object[][] data = Service.getInstance().generateViewDataProdukttypeDelbehandlingAntalTid(palle);
		//
		//		table = new JTable(data, columnNames);
		//		table.setFillsViewportHeight(true);
		//		scrollPaneT.add(table);
		//		scrollPaneT.setViewportView(table);

		//
		//		JScrollPane scrollPaneTabel = new JScrollPane(table);
		//		scrollPaneTabel.setViewportView(table);
		//		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		//		gbc_scrollPane.gridwidth = 2;
		//		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		//		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		//		gbc_scrollPane.gridx = 0;
		//		gbc_scrollPane.gridy = 3;
		//		panel.add(scrollPaneTabel, gbc_scrollPane);


		//		JLabel lblDetaljer = new JLabel("Detaljer");
		//		GridBagConstraints gbc_lblDetaljer = new GridBagConstraints();
		//		gbc_lblDetaljer.anchor = GridBagConstraints.WEST;
		//		gbc_lblDetaljer.insets = new Insets(0, 0, 5, 0);
		//		gbc_lblDetaljer.gridx = 1;
		//		gbc_lblDetaljer.gridy = 3;
		//		panel2.add(lblDetaljer, gbc_lblDetaljer);
		//		
		//		JLabel lblMellemvarerPPalle = new JLabel("Mellemvarer p\u00E5 palle");
		//		lblMellemvarerPPalle.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		//		GridBagConstraints gbc_lblMellemvarerPPalle = new GridBagConstraints();
		//		gbc_lblMellemvarerPPalle.anchor = GridBagConstraints.WEST;
		//		gbc_lblMellemvarerPPalle.insets = new Insets(0, 0, 5, 0);
		//		gbc_lblMellemvarerPPalle.gridx = 1;
		//		gbc_lblMellemvarerPPalle.gridy = 4;
		//		panel3.add(lblMellemvarerPPalle, gbc_lblMellemvarerPPalle);
		//		
		//		JList list = new JList();
		//		GridBagConstraints gbc_list = new GridBagConstraints();
		//		gbc_list.insets = new Insets(0, 0, 0, 5);
		//		gbc_list.gridx = 0;
		//		gbc_list.gridy = 5;
		//		panel3.add(list, gbc_list);
		//		
		//		JScrollPane scrollPaneListe = new JScrollPane(list);
		//		GridBagConstraints gbc_scrollPane_Liste = new GridBagConstraints();
		//		gbc_scrollPane_Liste.gridwidth = 2;
		//		gbc_scrollPane_Liste.fill = GridBagConstraints.BOTH;
		//		gbc_scrollPane_Liste.gridx = 0;
		//		gbc_scrollPane_Liste.gridy = 5;
		//		panel3.add(scrollPaneListe, gbc_scrollPane_Liste);
		//		


		//		JScrollPane scrollPane_1 = new JScrollPane();
		//		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		//		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		//		gbc_scrollPane_1.gridwidth = 3;
		//		gbc_scrollPane_1.gridx = 0;
		//		gbc_scrollPane_1.gridy = 6;
		//		panel.add(scrollPane_1, gbc_scrollPane_1);
		//		
		//		JList list_1 = new JList();
		//		scrollPane_1.setViewportView(list_1);
		//		
		//		JList list = new JList();
		//		list.setBounds(172, 307, 1, 1);
		//		getContentPane().add(list);

		//		JPanel panel = new JPanel();
		//		panel.setOpaque(true);
		//		GridBagConstraints gbc_panel = new GridBagConstraints();
		//		gbc_panel.gridwidth = 3;
		//		gbc_panel.insets = new Insets(0, 0, 5, 5);
		//		gbc_panel.fill = GridBagConstraints.BOTH;
		//		gbc_panel.gridx = 1;
		//		gbc_panel.gridy = 3;
		//		getContentPane().add(panel, gbc_panel);


	}


	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
