package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SingleSelectionModel;

import model.Palle;

import service.Service;

import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collections;

import javax.swing.JTextField;
import java.awt.Color;

/**
 * 
 * @author Rasmus Cederdorff
 * 
 */
public class SubFrameAdminPalle extends JFrame {

	private JPanel contentPane;
	private JList list;
	private JTextField txtstregkodeOpret;
	private static SubFrameAdminPalle adminPalle;

	private SubFrameAdminPalle() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 390, 315);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle("Administrér paller");
		setContentPane(contentPane);

		JLabel lblPaller = new JLabel("Paller");

		JButton btnSlet = new JButton("Slet");
		btnSlet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Palle palle = (Palle) list.getSelectedValue();
				if (palle != null) {
					Service.getInstance().removePalle(palle);
					list.setListData(Service.getInstance().getPaller()
							.toArray());
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblOpretNyPalle = new JLabel("Opret ny palle");

		JLabel lblIndtastStregkodeOg = new JLabel(
				"Indtast stregkode og v\u00E6lg opret");
		lblIndtastStregkodeOg
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		JLabel lblStregkodeOpret = new JLabel("Stregkode:");
		lblStregkodeOpret.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtstregkodeOpret = new JTextField();
		txtstregkodeOpret.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtstregkodeOpret.setText("#stregkode");
		txtstregkodeOpret.setColumns(10);

		JButton btnOpret = new JButton("Opret");
		btnOpret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String stregkode = txtstregkodeOpret.getText();
				if (stregkode != null
						&& Service.getInstance().soegPalle(stregkode) == null) {
					Service.getInstance().opretPalle(stregkode);
					list.setListData(Service.getInstance().getPaller()
							.toArray());
				}

			}
		});

		JLabel lblSletPalle = new JLabel("Slet palle");

		JLabel lblMarkrEnPalle = new JLabel(
				"Mark\u00E9r en palle og v\u00E6lg slet");
		lblMarkrEnPalle.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblPaller)
														.addComponent(
																scrollPane,
																GroupLayout.PREFERRED_SIZE,
																159,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblOpretNyPalle)
														.addComponent(
																lblIndtastStregkodeOg)
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.TRAILING)
																		.addComponent(
																				btnOpret)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblSletPalle)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblStregkodeOpret)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												txtstregkodeOpret,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								lblMarkrEnPalle))
																		.addComponent(
																				btnSlet)))
										.addGap(136)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblPaller)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblOpretNyPalle)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblIndtastStregkodeOg)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblStregkodeOpret)
																						.addComponent(
																								txtstregkodeOpret,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnOpret)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				40,
																				Short.MAX_VALUE)
																		.addComponent(
																				lblSletPalle)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblMarkrEnPalle)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnSlet)
																		.addGap(39))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				scrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				249,
																				Short.MAX_VALUE)
																		.addContainerGap()))));

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
		list.setListData(Service.getInstance().getPaller().toArray());
	}

	public static SubFrameAdminPalle getInstance() {
		if (adminPalle == null) {
			adminPalle = new SubFrameAdminPalle();
		}
		return adminPalle;
	}

	public void setPalleStregkodeTekst(String stregkode) {
		this.txtstregkodeOpret.setText(stregkode);
	}
}
