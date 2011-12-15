package caos;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SingleSelectionModel;

import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
/**
 * 
 * @author Mads Dahl Jensen
 *
 */
public class MellemVareAfPrrodukttype extends JFrame
{
	private JTable table;
	private String[] data;
	

	public MellemVareAfPrrodukttype(String [][] data, String[] headers)
	{
		
		table = new JTable(data, headers);
		table.setBounds(12, 154, 829, 488);
		getContentPane().add(table);
		
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 889, 590);
		
		JLabel lblBakkeStregkode = new JLabel("BakkeStregkode");
		lblBakkeStregkode.setBounds(12, 137, 100, 16);
		getContentPane().add(lblBakkeStregkode);

		JLabel lblIgangværendeDelbehandling = new JLabel(
				"Igangv\u00E6rende delbehandling");
		lblIgangværendeDelbehandling.setBounds(224, 137, 176, 16);
		getContentPane().add(lblIgangværendeDelbehandling);

		JLabel lblPallestregkode = new JLabel("Pallestregkode");
		lblPallestregkode.setBounds(436, 137, 100, 16);
		getContentPane().add(lblPallestregkode);

		JLabel lblProdukttype = new JLabel("Produkttype");
		lblProdukttype.setBounds(648, 137, 100, 16);
		getContentPane().add(lblProdukttype);
		
	}
	
}
