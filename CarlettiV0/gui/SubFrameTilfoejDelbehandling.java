package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.Behandling;
import service.Service;

public class SubFrameTilfoejDelbehandling extends JFrame implements Subject {

	private JPanel contentPane;
	private JTextField txtMin;
	private JTextField txtIdeal;
	private JTextField txtmaxTid;
	private JComboBox comboBox;
	private DefaultComboBoxModel cboxmodel;
	private JTextField txtBehandling;
	private JButton btnTilfoej;
	private Behandling behandling;
	private JLabel lblNavn;
	private JTextField txtNavn;
	private JLabel lblIndex;
	private JTextField txtIndex;
	private ArrayList<Observer> observers;

	public SubFrameTilfoejDelbehandling(Behandling behandling,
			SubFrameAdminBehandling subFrameBehandling) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 240, 373);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.behandling = behandling;
		this.observers = new ArrayList<Observer>();
		this.registerObserver(subFrameBehandling);
		JLabel lblTilfoejDelbehandling = new JLabel("Tilfoej delbehandling");

		JLabel lblBehandling = new JLabel("Behandling: ");
		lblBehandling.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		cboxmodel = new DefaultComboBoxModel();
		cboxmodel.addElement("Toerring");
		cboxmodel.addElement("Dragéring");
		comboBox = new JComboBox(cboxmodel);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (cboxmodel.getSelectedItem() == "Dragéring") {
					txtMin.setText("tid");
					txtIdeal.setEditable(false);
					txtIdeal.setEnabled(false);
					txtmaxTid.setEditable(false);
					txtmaxTid.setEnabled(false);
				} else if (cboxmodel.getSelectedItem() == "Toerring") {
					txtMin.setText("minTid");
					txtIdeal.setEditable(true);
					txtIdeal.setEnabled(true);
					txtmaxTid.setEditable(true);
					txtmaxTid.setEnabled(true);
				}
			}
		});
		JLabel lblVarighed = new JLabel("Varighed:");
		lblVarighed.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtMin = new JTextField();
		txtMin.setHorizontalAlignment(SwingConstants.CENTER);
		txtMin.setText("minTid");
		txtMin.setColumns(10);

		txtIdeal = new JTextField();
		txtIdeal.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdeal.setText("idealTid");
		txtIdeal.setColumns(10);

		txtmaxTid = new JTextField();
		txtmaxTid.setHorizontalAlignment(SwingConstants.CENTER);
		txtmaxTid.setText("maxTid");
		txtmaxTid.setColumns(10);

		txtBehandling = new JTextField();
		txtBehandling.setBackground(UIManager.getColor("CheckBox.background"));
		txtBehandling.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtBehandling.setEditable(false);
		txtBehandling.setColumns(10);
		txtBehandling.setText(behandling.getNavn());

		btnTilfoej = new JButton("Tilfoej");
		btnTilfoej.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cboxmodel.getSelectedItem() == "Dragéring") {
					Service.getInstance().opretDragering(txtNavn.getText(),
							getBehandling(), Long.parseLong(txtMin.getText()),
							Integer.parseInt(txtIndex.getText()));
				}
				if (cboxmodel.getSelectedItem() == "Toerring") {
					Service.getInstance().opretToerring(txtNavn.getText(),
							getBehandling(), Long.parseLong(txtMin.getText()),
							Long.parseLong(txtIdeal.getText()),
							Long.parseLong(txtmaxTid.getText()),
							Integer.parseInt(txtIndex.getText()));
				}
				notifyObservers();
				SubFrameTilfoejDelbehandling.this.setVisible(false);
			}
		});

		lblNavn = new JLabel("Navn:");
		lblNavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtNavn = new JTextField();
		txtNavn.setColumns(10);

		lblIndex = new JLabel("Index:");
		lblIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtIndex = new JTextField();
		txtIndex.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
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
																		.addContainerGap()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblTilfoejDelbehandling)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																lblBehandling)
																														.addComponent(
																																lblType)
																														.addComponent(
																																lblIndex)
																														.addComponent(
																																lblNavn))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.TRAILING,
																																false)
																														.addComponent(
																																txtNavn,
																																Alignment.LEADING,
																																0,
																																0,
																																Short.MAX_VALUE)
																														.addComponent(
																																txtBehandling,
																																Alignment.LEADING,
																																GroupLayout.DEFAULT_SIZE,
																																126,
																																Short.MAX_VALUE)
																														.addComponent(
																																comboBox,
																																Alignment.LEADING,
																																0,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																btnTilfoej)
																														.addComponent(
																																txtIndex,
																																Alignment.LEADING,
																																GroupLayout.DEFAULT_SIZE,
																																49,
																																Short.MAX_VALUE)))))
														.addGroup(
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(20)
																		.addComponent(
																				lblVarighed)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				35,
																				Short.MAX_VALUE)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								txtmaxTid,
																								Alignment.TRAILING,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addComponent(
																								txtIdeal,
																								Alignment.TRAILING,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addComponent(
																								txtMin,
																								Alignment.TRAILING,
																								GroupLayout.DEFAULT_SIZE,
																								97,
																								Short.MAX_VALUE))))
										.addGap(130)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblTilfoejDelbehandling)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblBehandling)
														.addComponent(
																txtBehandling,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																txtNavn,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblNavn,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																comboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblType))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																txtIndex,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblIndex))
										.addGap(6)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblVarighed)
														.addComponent(
																txtMin,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(txtIdeal,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(txtmaxTid,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(btnTilfoej)
										.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * @return the behandling
	 */
	public Behandling getBehandling() {
		return behandling;
	}

	/**
	 * @param behandling
	 *            the behandling to set
	 */
	public void setBehandling(Behandling behandling) {
		this.behandling = behandling;
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
