/**
 * SUBFRAMEADMINBEHANDLING
 */
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Behandling;
import model.Delbehandling;
import service.Service;
/**
 * 
 * @author Rasmus Cederdorff
 *
 */
/**
 * Til administration af behandlinger
 * 
 * @author Rasmus Cederdorff
 * 
 */

public class SubFrameAdminBehandling extends JFrame implements Observer,
		Subject {

	private JPanel contentPane;
	private JTextField txtNavn;
	private JList listBehandlinger, listDelbehandlinger;
	private SubFrameTilfoejDelbehandling subFrameTilfoejDelb;
	private Behandling behandling;
	private static SubFrameAdminBehandling adminBehandling;
	private ArrayList<Observer> observers;
	private JLabel lblBehandlinger;
	private JScrollPane scrollPane;
	private JLabel lblOpretNyBehandling;
	private JLabel lblIndtastNavnTilfoej;
	private JLabel lblRedigrDelbehandlinger;
	private JLabel lblOpretSletEller;
	private JScrollPane scrollPane_1;
	private JButton btnSletDelbehandling;
	private JButton btnTilfoej;
	private JButton btnSletBehandling;
	private JButton btnOpret;
	private JLabel lblNavn;

	private SubFrameAdminBehandling(MainFrame mainFrame) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 390, 375);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setTitle("Administrér behandlinger");
		this.observers = new ArrayList<Observer>();
		registerObserver(SubFrameAdminProdukttype.getInstance());

		lblBehandlinger = new JLabel("Behandlinger");

		scrollPane = new JScrollPane();

		lblOpretNyBehandling = new JLabel("Opret ny behandling");

		lblIndtastNavnTilfoej = new JLabel(
				"Indtast navn tilfoej delbehandlinger");
		lblIndtastNavnTilfoej
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		lblNavn = new JLabel("Navn:");
		lblNavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtNavn = new JTextField();
		txtNavn.setColumns(10);

		btnOpret = new JButton("Opret");
		btnOpret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtNavn.getText() != null) {
					Service.getInstance().opretBehandling(txtNavn.getText());
					listBehandlinger.setListData(Service.getInstance()
							.getBehandlinger().toArray());
				}
				notifyObservers();
			}
		});

		lblRedigrDelbehandlinger = new JLabel("Redig\u00E9r delbehandlinger");
		lblOpretSletEller = new JLabel("Tilfoej eller slet delbehandlinger");
		lblOpretSletEller.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		scrollPane_1 = new JScrollPane();

		btnSletDelbehandling = new JButton("Slet");
		btnSletDelbehandling.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Delbehandling delbehandling = (Delbehandling) listDelbehandlinger
						.getSelectedValue();
				if (delbehandling != null) {
					((Behandling) listBehandlinger.getSelectedValue())
							.removeDelbehandling(delbehandling);
					update();
				}
				notifyObservers();
			}
		});
		btnSletDelbehandling.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		btnTilfoej = new JButton("Tilfoej");
		btnTilfoej.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				behandling = (Behandling) listBehandlinger.getSelectedValue();
				if (behandling != null) {
					subFrameTilfoejDelb = new SubFrameTilfoejDelbehandling(
							behandling, SubFrameAdminBehandling.this);
					subFrameTilfoejDelb.setVisible(true);
				}

			}
		});
		btnTilfoej.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		btnSletBehandling = new JButton("Slet behandling");
		btnSletBehandling.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (listBehandlinger.getSelectedValue() != null) {
					Service.getInstance().removeBehandling(
							(Behandling) listBehandlinger.getSelectedValue());
					listBehandlinger.setListData(Service.getInstance()
							.getBehandlinger().toArray());
				}
				notifyObservers();
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
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								btnSletBehandling,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								scrollPane,
																								GroupLayout.DEFAULT_SIZE,
																								145,
																								Short.MAX_VALUE))
																		.addGap(18)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblOpretNyBehandling)
																						.addGroup(
																								gl_contentPane
																										.createParallelGroup(
																												Alignment.LEADING)
																										.addComponent(
																												lblOpretSletEller)
																										.addComponent(
																												lblRedigrDelbehandlinger)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.TRAILING)
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addComponent(
																																				btnTilfoej)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				btnSletDelbehandling,
																																				GroupLayout.PREFERRED_SIZE,
																																				55,
																																				GroupLayout.PREFERRED_SIZE))
																														.addComponent(
																																scrollPane_1,
																																Alignment.LEADING,
																																0,
																																0,
																																Short.MAX_VALUE)
																														.addGroup(
																																Alignment.LEADING,
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
																																lblIndtastNavnTilfoej,
																																Alignment.LEADING,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																btnOpret))))
																		.addGap(52))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblBehandlinger)
																		.addContainerGap(
																				323,
																				Short.MAX_VALUE)))));
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
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
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
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								btnSletDelbehandling)
																						.addComponent(
																								btnTilfoej)))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				scrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				229,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnSletBehandling)))
										.addGap(35)));

		listDelbehandlinger = new JList();
		scrollPane_1.setViewportView(listDelbehandlinger);

		listBehandlinger = new JList();
		listBehandlinger.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Behandling behandling = (Behandling) listBehandlinger
						.getSelectedValue();
				if (behandling != null) {
					listDelbehandlinger.setListData(behandling
							.getDelbehandlinger().toArray());
				}

			}
		});
		scrollPane.setViewportView(listBehandlinger);
		contentPane.setLayout(gl_contentPane);
		listBehandlinger.setListData(Service.getInstance().getBehandlinger()
				.toArray());
	}

	public static SubFrameAdminBehandling getInstance(MainFrame mainFrame) {
		if (adminBehandling == null) {
			adminBehandling = new SubFrameAdminBehandling(mainFrame);
		}
		return adminBehandling;
	}

	@Override
	public void update() {
		listDelbehandlinger.setListData(behandling.getDelbehandlinger()
				.toArray());
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);

	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}

	}
}
