package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import service.Service;
import java.awt.Color;

/**
 * 
 * @author Cederdorff
 * 
 */
public class SubFrameTilfoejMellemvarer extends JFrame implements Observer, Subject {
	private MainFrame mainFrame;
	private JTextField txtPallestregkode;
	private JTextField txtBakkestregkode;
	private JComboBox cBox;
	private JList list;
	private ArrayList<Observer> observers;
	private JButton btnFjern;
	private JLabel lblMellemvarerPPalle;
	private JButton btnTilfoej;
	private JLabel label_1;
	private JLabel lblBakkestregkode;
	private JLabel lblProdukttype;
	private JLabel label;
	private JLabel lblPalle;
	private Palle aktuelPalle;
	private JLabel lblIndtastStregkoderFor;

	public SubFrameTilfoejMellemvarer(MainFrame mainFrame) {
		getContentPane().setBackground(Color.PINK);
		this.mainFrame = mainFrame;
		this.observers = new ArrayList<Observer>();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Tilfoej mellemvarer til palle");
		this.setLocation(400, 200);
		this.setSize(440, 420);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 20, 150, 0, 30, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 0, 0, 0, 0, 154, 49, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0,
				1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		
		lblIndtastStregkoderFor = new JLabel("Indtast stregkoder for palle og bakke. V\u00E6lg produkttype og tryk tilfoej");
		lblIndtastStregkoderFor.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		GridBagConstraints gbc_lblIndtastStregkoderFor = new GridBagConstraints();
		gbc_lblIndtastStregkoderFor.anchor = GridBagConstraints.WEST;
		gbc_lblIndtastStregkoderFor.gridwidth = 4;
		gbc_lblIndtastStregkoderFor.insets = new Insets(0, 0, 5, 5);
		gbc_lblIndtastStregkoderFor.gridx = 1;
		gbc_lblIndtastStregkoderFor.gridy = 1;
		getContentPane().add(lblIndtastStregkoderFor, gbc_lblIndtastStregkoderFor);

		lblPalle = new JLabel("Palle");
		lblPalle.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GridBagConstraints gbc_lblPalle = new GridBagConstraints();
		gbc_lblPalle.anchor = GridBagConstraints.WEST;
		gbc_lblPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalle.gridx = 1;
		gbc_lblPalle.gridy = 2;
		getContentPane().add(lblPalle, gbc_lblPalle);

		label = new JLabel("#");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 2;
		getContentPane().add(label, gbc_label);

		txtPallestregkode = new JTextField();
		GridBagConstraints gbc_txtPallestregkode = new GridBagConstraints();
		gbc_txtPallestregkode.gridwidth = 2;
		gbc_txtPallestregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPallestregkode.insets = new Insets(0, 0, 5, 5);
		gbc_txtPallestregkode.gridx = 3;
		gbc_txtPallestregkode.gridy = 2;
		getContentPane().add(txtPallestregkode, gbc_txtPallestregkode);
		txtPallestregkode.setColumns(10);

		lblProdukttype = new JLabel("Produkttype");
		lblProdukttype.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GridBagConstraints gbc_lblProdukttype = new GridBagConstraints();
		gbc_lblProdukttype.insets = new Insets(0, 0, 5, 5);
		gbc_lblProdukttype.anchor = GridBagConstraints.WEST;
		gbc_lblProdukttype.gridx = 1;
		gbc_lblProdukttype.gridy = 3;
		getContentPane().add(lblProdukttype, gbc_lblProdukttype);

		cBox = new JComboBox(Service.getInstance().getProdukttyper().toArray());
		cBox.setLocation(200, 10);
		cBox.setSize(150, 30);
		GridBagConstraints gbc_cBox = new GridBagConstraints();
		gbc_cBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox.gridwidth = 2;
		gbc_cBox.insets = new Insets(0, 0, 5, 5);
		gbc_cBox.gridx = 3;
		gbc_cBox.gridy = 3;
		getContentPane().add(cBox, gbc_cBox);

		lblBakkestregkode = new JLabel("Bakkestregkode");
		lblBakkestregkode.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GridBagConstraints gbc_lblBakkestregkode = new GridBagConstraints();
		gbc_lblBakkestregkode.anchor = GridBagConstraints.WEST;
		gbc_lblBakkestregkode.insets = new Insets(0, 0, 5, 5);
		gbc_lblBakkestregkode.gridx = 1;
		gbc_lblBakkestregkode.gridy = 4;
		getContentPane().add(lblBakkestregkode, gbc_lblBakkestregkode);

		label_1 = new JLabel("#");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 4;
		getContentPane().add(label_1, gbc_label_1);

		txtBakkestregkode = new JTextField();
		GridBagConstraints gbc_txtBakkestregkode = new GridBagConstraints();
		gbc_txtBakkestregkode.insets = new Insets(0, 0, 5, 5);
		gbc_txtBakkestregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBakkestregkode.gridx = 3;
		gbc_txtBakkestregkode.gridy = 4;
		getContentPane().add(txtBakkestregkode, gbc_txtBakkestregkode);
		txtBakkestregkode.setColumns(10);

		btnTilfoej = new JButton("Tilfoej");
		btnTilfoej.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Palle palle = Service.getInstance().soegPalle(
						txtPallestregkode.getText());
				aktuelPalle = palle;
				if (palle != null) {
					Service.getInstance().opretMellemvare(
							txtBakkestregkode.getText(),
							(Produkttype) cBox.getSelectedItem(), palle);
				}
				else {
					SubFrameAdminPalle opretPalleFrame = new SubFrameAdminPalle();
					opretPalleFrame.setPalleStregkodeTekst(txtPallestregkode.getText());
					opretPalleFrame.setVisible(true);
				}
				update();
				notifyObservers();
			}
		});
		GridBagConstraints gbc_btnTilfoej = new GridBagConstraints();
		gbc_btnTilfoej.insets = new Insets(0, 0, 5, 5);
		gbc_btnTilfoej.gridx = 4;
		gbc_btnTilfoej.gridy = 4;
		getContentPane().add(btnTilfoej, gbc_btnTilfoej);

		lblMellemvarerPPalle = new JLabel("Mellemvarer p\u00E5 palle");
		lblMellemvarerPPalle
		.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		GridBagConstraints gbc_lblMellemvarerPPalle = new GridBagConstraints();
		gbc_lblMellemvarerPPalle.gridwidth = 4;
		gbc_lblMellemvarerPPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblMellemvarerPPalle.gridx = 1;
		gbc_lblMellemvarerPPalle.gridy = 5;
		getContentPane().add(lblMellemvarerPPalle, gbc_lblMellemvarerPPalle);

		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 4;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 6;
		getContentPane().add(list, gbc_list);

		btnFjern = new JButton("Fjern");
		btnFjern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mellemvare mellemvare = (Mellemvare) list.getSelectedValue();
				Palle palle = Service.getInstance().soegPalle(
						txtPallestregkode.getText());
				aktuelPalle = palle;
				if (mellemvare != null && palle != null) {
					palle.removeMellemvare(mellemvare);
				}
				update();
				notifyObservers();
			}
		});
		GridBagConstraints gbc_btnFjern = new GridBagConstraints();
		gbc_btnFjern.insets = new Insets(0, 0, 0, 5);
		gbc_btnFjern.gridx = 4;
		gbc_btnFjern.gridy = 7;
		getContentPane().add(btnFjern, gbc_btnFjern);

	}

	@Override
	public void update() {
		if (aktuelPalle!=null){
			list.setListData(Service.getInstance().getMellemvarer(aktuelPalle).toArray());
		}
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);		
	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);		
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers){
			o.update();
		}
	}

}
