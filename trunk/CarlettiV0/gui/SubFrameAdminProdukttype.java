package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import service.Service;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import model.Behandling;
import model.Produkttype;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * 
 * @author cederdorff
 * 
 */
public class SubFrameAdminProdukttype extends JFrame {

	private JPanel contentPane;
	private JTextField txtIndtastnavn;
	private JTextField txtBeskrivelse;
	private JList list;
	private JComboBox comboBox, comboBoxRediger;
	private JTextField txtNavnRediger;
	private JTextField txtBeskrivRediger;
	private static SubFrameAdminProdukttype adminProdukttype;

	private SubFrameAdminProdukttype() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 410, 485);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblProdukttyper = new JLabel("Produkttyper");

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblOpretNyProdukttype = new JLabel("Opret ny produkttype");

		JLabel lblIndtastNavnBeskrivelse = new JLabel(
				"Udfyld nedest\u00E5ende og v\u00E6lg opret");
		lblIndtastNavnBeskrivelse.setFont(new Font("Lucida Grande",
				Font.ITALIC, 11));

		JLabel lblNavn = new JLabel("Navn:");
		lblNavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtIndtastnavn = new JTextField();
		txtIndtastnavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtIndtastnavn.setColumns(10);

		JLabel lblBeskrivelse = new JLabel("Beskrivelse:");
		lblBeskrivelse.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtBeskrivelse = new JTextField();
		txtBeskrivelse.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtBeskrivelse.setColumns(10);

		JLabel lblBehandling = new JLabel("Behandling:");
		lblBehandling.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		comboBox = new JComboBox(Service.getInstance().getBehandlinger()
				.toArray());
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		JButton btnOpret = new JButton("Opret");
		btnOpret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String navn = txtIndtastnavn.getText();
				String beskrivelse = txtBeskrivelse.getText();
				if (txtIndtastnavn != null && txtBeskrivelse != null) {
					Service.getInstance().opretProdukttype(navn, beskrivelse,
							(Behandling) comboBox.getSelectedItem());
					list.setListData(Service.getInstance().getProdukttyper()
							.toArray());

				}
			}
		});

		JLabel lblRedigrProdukttype = new JLabel("Redig\u00E9r produkttype");

		JLabel lblMarkrProdukttypeFor = new JLabel(
				"Mark\u00E9r produkttype for at redigere");
		lblMarkrProdukttypeFor.setFont(new Font("Lucida Grande", Font.ITALIC,
				11));

		JLabel lblNavnRediger = new JLabel("Navn:");
		lblNavnRediger.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		JLabel lblBeskrivelseRediger = new JLabel("Beskrivelse:");
		lblBeskrivelseRediger
				.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		JLabel lblBeskrivRediger = new JLabel("Behandling:");
		lblBeskrivRediger.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtNavnRediger = new JTextField();
		txtNavnRediger.setEditable(false);
		txtNavnRediger.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtNavnRediger.setColumns(10);

		txtBeskrivRediger = new JTextField();
		txtBeskrivRediger.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtBeskrivRediger.setColumns(10);

		comboBoxRediger = new JComboBox(Service.getInstance().getBehandlinger().toArray());
		comboBoxRediger.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		JButton btnGem = new JButton("Gem");
		btnGem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Produkttype produkttype = (Produkttype) list.getSelectedValue();
				Service.getInstance().redigerProdukttype(produkttype, txtBeskrivRediger.getText(), (Behandling) comboBoxRediger.getSelectedItem());
			}
		});

		JLabel lblSletProdukttype = new JLabel("Slet Produkttype");

		JLabel lblMarkrProdukttypeOg = new JLabel(
				"Mark\u00E9r og v\u00E6lg slet");
		lblMarkrProdukttypeOg
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		JButton btnSlet = new JButton("Slet");
		btnSlet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedValue() != null) {
					Service.getInstance().removeProdukttype(
							(Produkttype) list.getSelectedValue());
					list.setListData(Service.getInstance().getProdukttyper()
							.toArray());

				}
			}
		});
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
														.addComponent(
																lblProdukttyper)
														.addGroup(
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblMarkrProdukttypeOg)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				72,
																				Short.MAX_VALUE)
																		.addComponent(
																				comboBoxRediger,
																				GroupLayout.PREFERRED_SIZE,
																				192,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																scrollPane,
																																GroupLayout.PREFERRED_SIZE,
																																158,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblSletProdukttype))
																										.addPreferredGap(
																												ComponentPlacement.RELATED))
																						.addComponent(
																								btnSlet))
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGap(18)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																lblOpretNyProdukttype)
																														.addComponent(
																																lblIndtastNavnBeskrivelse)
																														.addComponent(
																																lblNavn)
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addGroup(
																																				gl_contentPane
																																						.createParallelGroup(
																																								Alignment.LEADING)
																																						.addComponent(
																																								lblBeskrivelse)
																																						.addComponent(
																																								lblBehandling))
																																		.addGap(18)
																																		.addGroup(
																																				gl_contentPane
																																						.createParallelGroup(
																																								Alignment.LEADING)
																																						.addComponent(
																																								txtIndtastnavn,
																																								Alignment.TRAILING,
																																								GroupLayout.DEFAULT_SIZE,
																																								112,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								txtBeskrivelse,
																																								GroupLayout.DEFAULT_SIZE,
																																								112,
																																								Short.MAX_VALUE)))
																														.addComponent(
																																comboBox,
																																0,
																																192,
																																Short.MAX_VALUE)
																														.addComponent(
																																lblRedigrProdukttype)
																														.addGroup(
																																gl_contentPane
																																		.createParallelGroup(
																																				Alignment.TRAILING,
																																				false)
																																		.addGroup(
																																				gl_contentPane
																																						.createSequentialGroup()
																																						.addComponent(
																																								lblNavnRediger,
																																								GroupLayout.PREFERRED_SIZE,
																																								30,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addPreferredGap(
																																								ComponentPlacement.RELATED,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								txtNavnRediger,
																																								GroupLayout.PREFERRED_SIZE,
																																								112,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addComponent(
																																				lblMarkrProdukttypeFor,
																																				Alignment.LEADING)
																																		.addGroup(
																																				Alignment.LEADING,
																																				gl_contentPane
																																						.createSequentialGroup()
																																						.addComponent(
																																								lblBeskrivelseRediger,
																																								GroupLayout.PREFERRED_SIZE,
																																								62,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addGap(18)
																																						.addComponent(
																																								txtBeskrivRediger,
																																								GroupLayout.PREFERRED_SIZE,
																																								112,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addComponent(
																																				lblBeskrivRediger,
																																				Alignment.LEADING,
																																				GroupLayout.PREFERRED_SIZE,
																																				62,
																																				GroupLayout.PREFERRED_SIZE))))
																						.addComponent(
																								btnOpret)
																						.addComponent(
																								btnGem,
																								GroupLayout.PREFERRED_SIZE,
																								79,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblProdukttyper)
										.addPreferredGap(
												ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE,
																false)
														.addComponent(
																scrollPane,
																GroupLayout.PREFERRED_SIZE,
																321,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGap(22)
																										.addComponent(
																												lblIndtastNavnBeskrivelse)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblNavn)
																														.addComponent(
																																txtIndtastnavn,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)))
																						.addComponent(
																								lblOpretNyProdukttype))
																		.addGap(8)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								txtBeskrivelse,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblBeskrivelse))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblBehandling)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				btnOpret)
																		.addGap(18)
																		.addComponent(
																				lblRedigrProdukttype)
																		.addGap(5)
																		.addComponent(
																				lblMarkrProdukttypeFor)
																		.addGap(7)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								txtNavnRediger,
																								GroupLayout.PREFERRED_SIZE,
																								26,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblNavnRediger,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblBeskrivelseRediger,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txtBeskrivRediger,
																								GroupLayout.PREFERRED_SIZE,
																								26,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(9)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblBeskrivRediger,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblSletProdukttype))
																		.addGap(5)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								comboBoxRediger,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblMarkrProdukttypeOg))
																		.addGap(11)))
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(btnGem)
														.addComponent(btnSlet))
										.addGap(183)));

		list = new JList();
		list.setListData(Service.getInstance().getProdukttyper().toArray());
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() != null) {
					Produkttype produkttype = (Produkttype) list
							.getSelectedValue();
					txtNavnRediger.setText(produkttype.getNavn());
					txtBeskrivRediger.setText(produkttype.getBeskrivelse());
					comboBoxRediger.setSelectedItem(produkttype.getBehandling());
				}

			}
		});
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);

	}
	public static SubFrameAdminProdukttype getInstance(){
		if(adminProdukttype == null){
			adminProdukttype = new SubFrameAdminProdukttype();
		}
		return adminProdukttype;
	}
}
