package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import model.MellemlagerPlads;
import service.Service;
/**
 * 
 * @author Rasmus Cederdorff
 *
 */
public class SubFrameAdminMellemlagerPlads extends JFrame implements Subject {

	private JPanel contentPane;
	private JTextField txtstregkode;
	private JList list;
	private JCheckBox chckbxPallePlaceretP;
	private static SubFrameAdminMellemlagerPlads adminMellemlagerPlads;
	private ArrayList<Observer> observers;
	private MainFrame mainFrame;

	private SubFrameAdminMellemlagerPlads(MainFrame mainFrame) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setTitle("Administrér mellemlagerpladser");
		this.observers = new ArrayList<Observer>();
		registerObserver(mainFrame);

		JLabel lblMellemlagerpladser = new JLabel("Mellemlagerpladser");

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblOpretEnPlads = new JLabel("Opret mellemlagerplads");

		JLabel lblIndtastStregkodeOg = new JLabel(
				"Indtast stregkode og v\u00E6lg opret");
		lblIndtastStregkodeOg
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		JLabel lblStregkode = new JLabel("Stregkode:");
		lblStregkode.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtstregkode = new JTextField();
		txtstregkode.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		txtstregkode.setText("#stregkode");
		txtstregkode.setColumns(10);

		JButton btnOpret = new JButton("Opret");
		btnOpret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtstregkode.getText() != null) {
					Service.getInstance().opretMellemlagerPlads(
							txtstregkode.getText());
					list.setListData(Service.getInstance().getPladser()
							.toArray());
					notifyObservers();
				}
			}
		});

		JLabel lblSletMellemlagerplads = new JLabel("Slet mellemlagerplads");

		JLabel lblMarkrMellemlagerpladsOg = new JLabel(
				"Mark\u00E9r mellemlagerplads og v\u00E6lg slet");
		lblMarkrMellemlagerpladsOg.setFont(new Font("Lucida Grande",
				Font.ITALIC, 11));

		JButton btnSlet = new JButton("Slet");
		btnSlet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MellemlagerPlads plads = (MellemlagerPlads) list
						.getSelectedValue();
				if (plads != null && plads.getPalle() == null) {
					Service.getInstance().removeMellemlagerPlads(
							(MellemlagerPlads) list.getSelectedValue());
					list.setListData(Service.getInstance().getPladser()
							.toArray());
					notifyObservers();
				}
			}
		});

		chckbxPallePlaceretP = new JCheckBox("Palle placeret p\u00E5 plads");
		chckbxPallePlaceretP.setEnabled(false);
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
																lblMellemlagerpladser)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				scrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				148,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblIndtastStregkodeOg)
																						.addComponent(
																								lblOpretEnPlads)
																						.addGroup(
																								gl_contentPane
																										.createParallelGroup(
																												Alignment.LEADING,
																												false)
																										.addGroup(
																												gl_contentPane
																														.createSequentialGroup()
																														.addComponent(
																																lblStregkode)
																														.addPreferredGap(
																																ComponentPlacement.RELATED,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																txtstregkode,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.TRAILING)
																														.addComponent(
																																btnOpret)
																														.addComponent(
																																btnSlet)
																														.addGroup(
																																gl_contentPane
																																		.createParallelGroup(
																																				Alignment.LEADING)
																																		.addComponent(
																																				lblSletMellemlagerplads)
																																		.addComponent(
																																				lblMarkrMellemlagerpladsOg)
																																		.addComponent(
																																				chckbxPallePlaceretP)))))))
										.addContainerGap(17, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblMellemlagerpladser)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																scrollPane,
																GroupLayout.PREFERRED_SIZE,
																234,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblOpretEnPlads)
																		.addGap(4)
																		.addComponent(
																				lblIndtastStregkodeOg)
																		.addGap(8)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblStregkode)
																						.addComponent(
																								txtstregkode,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(10)
																		.addComponent(
																				btnOpret)
																		.addGap(20)
																		.addComponent(
																				lblSletMellemlagerplads)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblMarkrMellemlagerpladsOg)
																		.addGap(8)
																		.addComponent(
																				chckbxPallePlaceretP)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				11,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnSlet)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		list = new JList(Service.getInstance().getPladser().toArray());
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() != null) {
					MellemlagerPlads plads = (MellemlagerPlads) list
							.getSelectedValue();
					if (plads.getPalle() != null) {
						chckbxPallePlaceretP.setSelected(true);
					} else {
						chckbxPallePlaceretP.setSelected(false);
					}
				}

			}
		});
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
	}

	public static SubFrameAdminMellemlagerPlads getInstance(MainFrame mainFrame) {
		if (adminMellemlagerPlads == null) {
			adminMellemlagerPlads = new SubFrameAdminMellemlagerPlads(mainFrame);
		}
		return adminMellemlagerPlads;
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
