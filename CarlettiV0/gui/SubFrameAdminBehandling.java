package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import com.apple.dnssd.TXTRecord;

import service.Service;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import model.Behandling;

public class SubFrameAdminBehandling extends JFrame implements Observer{

	private JPanel contentPane;
	private JTextField txtNavn;
	private JList listBehandlinger, listDelbehandlinger;
	private SubFrameTilfoejDelbehandling subFrameTilfoejDelb;
	private Behandling behandling;

	/**
	 * Create the frame.
	 */
	public SubFrameAdminBehandling() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 445);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblBehandlinger = new JLabel("Behandlinger");

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblOpretNyBehandling = new JLabel("Opret ny behandling");

		JLabel lblIndtastNavnTilfoej = new JLabel(
				"Indtast navn tilfoej delbehandlinger");
		lblIndtastNavnTilfoej
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		JLabel lblNavn = new JLabel("Navn:");
		lblNavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtNavn = new JTextField();
		txtNavn.setColumns(10);

		JButton btnOpret = new JButton("Opret");
		btnOpret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtNavn.getText() != null) {
					Service.getInstance().opretBehandling(txtNavn.getText());
					listBehandlinger.setListData(Service.getInstance().getBehandlinger().toArray());
				}
			}
		});

		JLabel lblRedigrDelbehandlinger = new JLabel(
				"Redig\u00E9r delbehandlinger");

		JLabel lblOpretSletEller = new JLabel(
				"Tilfoej eller slet delbehandlinger");
		lblOpretSletEller.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		JScrollPane scrollPane_1 = new JScrollPane();

		JButton btnSlet = new JButton("Slet");
		btnSlet.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JButton btnTilfoej = new JButton("Tilfoej");
		btnTilfoej.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				behandling = (Behandling) listBehandlinger.getSelectedValue();
				if (behandling != null) {
					subFrameTilfoejDelb = new SubFrameTilfoejDelbehandling(behandling, SubFrameAdminBehandling.this);
					subFrameTilfoejDelb.setVisible(true);
				}

			}
		});
		btnTilfoej.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JButton btnSletBehandling = new JButton("Slet");
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
																lblBehandlinger)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								btnSletBehandling)
																						.addComponent(
																								scrollPane,
																								GroupLayout.PREFERRED_SIZE,
																								145,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(18)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblOpretSletEller)
																						.addComponent(
																								lblRedigrDelbehandlinger)
																						.addComponent(
																								lblOpretNyBehandling)
																						.addGroup(
																								gl_contentPane
																										.createParallelGroup(
																												Alignment.LEADING,
																												false)
																										.addGroup(
																												gl_contentPane
																														.createSequentialGroup()
																														.addComponent(
																																lblNavn)
																														.addPreferredGap(
																																ComponentPlacement.RELATED,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																txtNavn,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addComponent(
																												lblIndtastNavnTilfoej)
																										.addComponent(
																												btnOpret,
																												Alignment.TRAILING))
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																scrollPane_1,
																																GroupLayout.DEFAULT_SIZE,
																																214,
																																Short.MAX_VALUE)
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addGap(74)
																																		.addComponent(
																																				btnTilfoej,
																																				0,
																																				0,
																																				Short.MAX_VALUE)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				btnSlet,
																																				0,
																																				0,
																																				Short.MAX_VALUE)))))))
										.addContainerGap(27,
												GroupLayout.PREFERRED_SIZE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblBehandlinger)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblOpretNyBehandling)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblIndtastNavnTilfoej)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblNavn)
																						.addComponent(
																								txtNavn,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnOpret)
																		.addGap(19)
																		.addComponent(
																				lblRedigrDelbehandlinger)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblOpretSletEller)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				scrollPane_1,
																				GroupLayout.PREFERRED_SIZE,
																				98,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				scrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				229,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnSletBehandling)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(btnSlet)
														.addComponent(
																btnTilfoej))
										.addGap(86)));

		listDelbehandlinger = new JList();
		scrollPane_1.setViewportView(listDelbehandlinger);

		listBehandlinger = new JList();
		listBehandlinger.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				Behandling behandling = (Behandling) listBehandlinger
						.getSelectedValue();
				listDelbehandlinger.setListData(behandling.getDelbehandlinger()
						.toArray());
			}
		});
		scrollPane.setViewportView(listBehandlinger);
		contentPane.setLayout(gl_contentPane);
		listBehandlinger.setListData(Service.getInstance().getBehandlinger()
				.toArray());
	}

	@Override
	public void update() {
		listDelbehandlinger.setListData(behandling.getDelbehandlinger().toArray());
		
	}
}
