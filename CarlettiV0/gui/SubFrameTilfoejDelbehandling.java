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
import service.Validering;
/**
 * 
 * @author cederdorff
 *
 */
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
	private JLabel lblMinTid;
	private JLabel lblIdealTid;
	private JLabel lblMaxTid;

	public SubFrameTilfoejDelbehandling(Behandling behandling,
			SubFrameAdminBehandling subFrameBehandling) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 240, 370);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.behandling = behandling;
		this.setTitle("Tilfoej delbehandlinger");
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
					lblMinTid.setText("Tid");
					txtIdeal.setEditable(false);
					txtIdeal.setEnabled(false);
					txtmaxTid.setEditable(false);
					txtmaxTid.setEnabled(false);
					lblMaxTid.setEnabled(false);
					lblIdealTid.setEnabled(false);
				} else if (cboxmodel.getSelectedItem() == "Toerring") {

					txtIdeal.setEditable(true);
					txtIdeal.setEnabled(true);
					txtmaxTid.setEditable(true);
					txtmaxTid.setEnabled(true);
					lblMaxTid.setEnabled(true);
					lblIdealTid.setEnabled(true);
				}
			}
		});
		JLabel lblVarighed = new JLabel("Varighed");
		lblVarighed.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		txtMin = new JTextField();
		txtMin.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtMin.setHorizontalAlignment(SwingConstants.CENTER);
		txtMin.setText("DD:HH:MM");
		txtMin.setColumns(10);

		txtIdeal = new JTextField();
		txtIdeal.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtIdeal.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdeal.setText("DD:HH:MM");
		txtIdeal.setColumns(10);

		txtmaxTid = new JTextField();
		txtmaxTid.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtmaxTid.setHorizontalAlignment(SwingConstants.CENTER);
		txtmaxTid.setText("DD:HH:MM");
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
					Integer index = -1;
					if (!txtIndex.getText().equals("")){
						index = Integer.parseInt(txtIndex.getText());
					}
					long varighed = Validering.varighedStringTilMillisekunder(txtMin.getText());
					Service.getInstance().opretDragering(txtNavn.getText(), getBehandling(), varighed, index);
				}
				if (cboxmodel.getSelectedItem() == "Toerring") {
					long minTid = Validering.varighedStringTilMillisekunder(txtMin.getText());
					long idealTid = Validering.varighedStringTilMillisekunder(txtIdeal.getText());
					long maxTid = Validering.varighedStringTilMillisekunder(txtmaxTid.getText());
					int index = -1;
					try{
						index = Integer.parseInt(txtIndex.getText().trim());
					}
					catch (NumberFormatException e) {
						if (txtIndex.getText().equals("")){
							//Do nothing -1 is used as the index.
						}
						else {
							System.out.println("Advarsel: "+txtIndex.getText()+" er ikke et gyldigt heltal.\nIndex "+index+" benyttes");
						}			
					}
					Service.getInstance().opretToerring(txtNavn.getText(), getBehandling(), minTid, idealTid, maxTid, index);
				}
				notifyObservers();
				SubFrameTilfoejDelbehandling.this.setVisible(false);
			}
		});

		lblNavn = new JLabel("Navn:");
		lblNavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtNavn = new JTextField();
		txtNavn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtNavn.setColumns(10);

		lblIndex = new JLabel("Index:");
		lblIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		txtIndex = new JTextField();
		txtIndex.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtIndex.setColumns(10);

		lblMinTid = new JLabel("Min. tid:");
		lblMinTid.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		lblIdealTid = new JLabel("Idealtid:");
		lblIdealTid.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		lblMaxTid = new JLabel("Max. tid:");
		lblMaxTid.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTilfoejDelbehandling)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblBehandling)
												.addComponent(lblType)
												.addComponent(lblIndex)
												.addComponent(lblNavn)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addGap(6)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(lblMaxTid)
																.addComponent(lblIdealTid, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblMinTid))))
																.addGap(18)
																.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
																				.addComponent(txtNavn, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
																				.addComponent(txtBehandling, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
																				.addComponent(comboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(txtIndex, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
																				.addComponent(btnTilfoej)
																				.addComponent(txtIdeal, 0, 0, Short.MAX_VALUE)
																				.addComponent(txtMin, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
																				.addComponent(txtmaxTid, 0, 0, Short.MAX_VALUE)))
																				.addComponent(lblVarighed))
																				.addContainerGap(27, Short.MAX_VALUE))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblTilfoejDelbehandling)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBehandling)
								.addComponent(txtBehandling, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtNavn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNavn, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblType))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtIndex, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblIndex))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblVarighed)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
																.addComponent(txtMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblMinTid))
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
																		.addComponent(txtIdeal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblIdealTid, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblMaxTid, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
																				.addComponent(txtmaxTid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																				.addGap(30)
																				.addComponent(btnTilfoej)
																				.addContainerGap(16, Short.MAX_VALUE))
				);
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
