/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultRowSorter;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.Delbehandling;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;

import service.Service;
import service.Varighed;

import javax.swing.JList;

/**
 * @author nijntje
 *
 */
public class FarveTestOversigt extends JFrame{
	private JTable table;
	private String[] columnNames;
	private Object[][] data;
	private Palle palle;
	private DefaultTableModel dm;
	private DefaultListModel dlm;
//	private Controller controller;
	public FarveTestOversigt(Palle palle) {
		this.palle = palle;
		this.setSize(700, 600);
		this.setLocation(300, 50);
		JPanel panel = new JPanel();

		JLabel lblMellemvarerFarvetest = new JLabel("Mellemvarer - farvetest");

		JPanel panel_1 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(217)
						.addComponent(lblMellemvarerFarvetest)
						.addContainerGap(336, Short.MAX_VALUE))
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(20)
						.addComponent(lblMellemvarerFarvetest)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(123, Short.MAX_VALUE))
				);

		JScrollPane scrollPane_1 = new JScrollPane();

		JList list = new JList();
		dlm = new DefaultListModel();
		ArrayList<Mellemvare> mellemvarer = Service.getInstance().getMellemvarer(palle);
		for (Mellemvare m : mellemvarer) {
			dlm.addElement(m);
		}
		list = new JList(dlm);
		list.setCellRenderer(new DefaultListCellRenderer(){

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus){
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				Mellemvare m = (Mellemvare) value;
				if (m != null && !isSelected){
					setBackground(FarveKoder.getFarve(m));
				}
				return c;
			}
		});

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane_1.setViewportView(list);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(125)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(123, Short.MAX_VALUE))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(5)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(26, Short.MAX_VALUE))
				);
		panel_1.setLayout(gl_panel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(300, 200);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		columnNames = new String[] { "Produkttype", "Delbehandling", "Antal",
		"Resterende tid" };
		data = Service.getInstance()
				.generateViewDataTestOversigt(palle);
		dm = new DefaultTableModel(data, columnNames);
		table = new JTable( dm )
		{
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				if (column==3){
					((JLabel) renderer).setHorizontalAlignment(SwingConstants.RIGHT);
				}
				else ((JLabel) renderer).setHorizontalAlignment(SwingConstants.LEFT);
				Component c = super.prepareRenderer(renderer, row, column);
				Produkttype pt = (Produkttype) this.getValueAt(row, 0);
				Delbehandling d = (Delbehandling) this.getValueAt(row, 1);
				if (pt!=null && d!=null){
					Mellemvare m = Service.getInstance().getMellemvarerAfSammeType(FarveTestOversigt.this.palle, pt, d).get(0);
					Color color = FarveKoder.getFarve(m);
					c.setBackground(color);

				}
				return c;
			}
		};

		//---------------Nedenstående benyttes hvis man ønsker at pågældende søjle skal sorteres vha.
		//---------------det bagvedliggende Object's compareTo()-metode
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dm);
		Comparator<Varighed> comparator = new Comparator<Varighed>(){
			@Override
			public int compare(Varighed o1, Varighed o2) {
				if (o1 != null && o2 != null){
					return o1.compareTo(o2);
				}
				else if (o1 != null){
					return 1;
				}
				else if (o2 != null){
					return -1;
				}
				else return 0;
			}	
		};
		sorter.setComparator(3, comparator);
		table.setRowSorter(sorter);

		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(123)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(123, Short.MAX_VALUE))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(5)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(306, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

//		this.controller = new Controller();
//		Timer t = new Timer(1000, controller);
//		t.start();
	}

//	private class Controller implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			MainFrame.getInstance().update();
//			MainFrame.getInstance().notifyObservers();
//		}
//
//	}
}
