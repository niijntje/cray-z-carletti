/**
 * 
 */
package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import model.Palle;

import service.Service;
import javax.swing.JTable;


/**
 * @author nijntje
 *
 */
public class SubFramePalleOversigt extends JFrame implements Observer{
	
	private Palle palle;
	private MainFrame mainFrame;
	private DefaultComboBoxModel cBoxModel = new DefaultComboBoxModel();
	private JTable table;
	
	
	public SubFramePalleOversigt(MainFrame mainFrame, Palle palle){
		this.mainFrame = mainFrame;
		this.palle = palle;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Oversigt over palle");
		this.setLocation(400, 200);
		this.setSize(420, 420);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 122, 0, 150, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 24, 77, 49, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblPalle = new JLabel("Palle");
		GridBagConstraints gbc_lblPalle = new GridBagConstraints();
		gbc_lblPalle.anchor = GridBagConstraints.WEST;
		gbc_lblPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalle.gridx = 1;
		gbc_lblPalle.gridy = 1;
		getContentPane().add(lblPalle, gbc_lblPalle);
		
		JLabel label = new JLabel("#"+Service.getStregkode(palle));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		getContentPane().add(label, gbc_label);
		
		JLabel lblProdukttype = new JLabel("Antal bk.: ");
		GridBagConstraints gbc_lblProdukttype = new GridBagConstraints();
		gbc_lblProdukttype.insets = new Insets(0, 0, 5, 5);
		gbc_lblProdukttype.anchor = GridBagConstraints.WEST;
		gbc_lblProdukttype.gridx = 1;
		gbc_lblProdukttype.gridy = 2;
		getContentPane().add(lblProdukttype, gbc_lblProdukttype);
		cBoxModel.addElement(Service.getInstance().getProdukttyper());
		
		JLabel lblPget = new JLabel(""+palle.getMellemvarer().size());
		GridBagConstraints gbc_lblPget = new GridBagConstraints();
		gbc_lblPget.anchor = GridBagConstraints.WEST;
		gbc_lblPget.insets = new Insets(0, 0, 5, 5);
		gbc_lblPget.gridx = 2;
		gbc_lblPget.gridy = 2;
		getContentPane().add(lblPget, gbc_lblPget);
		
		JLabel lblMellemvarerPPalle = new JLabel("Mellemvaregrupper p\u00E5 palle:");
		lblMellemvarerPPalle.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		GridBagConstraints gbc_lblMellemvarerPPalle = new GridBagConstraints();
		gbc_lblMellemvarerPPalle.gridwidth = 4;
		gbc_lblMellemvarerPPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblMellemvarerPPalle.gridx = 1;
		gbc_lblMellemvarerPPalle.gridy = 3;
		getContentPane().add(lblMellemvarerPPalle, gbc_lblMellemvarerPPalle);
		
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 4;
		getContentPane().add(table, gbc_table);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	
}
