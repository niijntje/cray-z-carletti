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

import model.Mellemvare;
import service.Service;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;


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

	public MainFrameTabelVersion() {
		getContentPane().setBackground(Color.PINK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mellemvarelager - Oversigt");
		this.setLocation(200, 100);
		this.setSize(800, 600);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 29, 711, 0 };
		gridBagLayout.rowHeights = new int[] { 39, 321, 21, 68, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);



		// --------------------Panel 2--------------------//
		JPanel panel2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		getContentPane().add(panel2, gbc_panel_2);

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


		Box horizontalBox_3 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_3 = new GridBagConstraints();
		gbc_horizontalBox_3.fill = GridBagConstraints.BOTH;
		gbc_horizontalBox_3.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalBox_3.gridx = 2;
		gbc_horizontalBox_3.gridy = 2;
		getContentPane().add(horizontalBox_3, gbc_horizontalBox_3);

		// -----------------------------------------------//
	}
	

	private class Controller implements ItemListener { // ,ListSelectionListener, ActionListener

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (chckbxVisTommePladser.isSelected()){
				((DefaultRowSorter<DefaultTableModel, Integer>) table1.getRowSorter()).setRowFilter(null);
				System.out.println("Vis tomme pladser");
			}
			else {
				((DefaultRowSorter<DefaultTableModel, Integer>) table1.getRowSorter()).setRowFilter(tomPladsFilter);
				System.out.println("Vis ikke tomme pladser");
			}
//			((DefaultRowSorter<DefaultTableModel, Integer>) table1.getRowSorter());
		}



	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
