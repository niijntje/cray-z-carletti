package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

/**
 * 
 * @author Cederdorff
 * 
 */
public class SubFrameTilfoejMellemvarer extends JFrame implements Observer {
	private MainFrame mainFrame;
	private JTextField txtPallestregkode;
	private JTextField txtBakkestregkode;
	private JComboBox cBox;
	private JList list;

	public SubFrameTilfoejMellemvarer(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Tilfoej mellemvarer til palle");
		this.setLocation(400, 200);
		this.setSize(420, 420);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 0, 20, 150, 0, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 0, 0, 0, 154, 49, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0,
				1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblPalle = new JLabel("Palle");
		GridBagConstraints gbc_lblPalle = new GridBagConstraints();
		gbc_lblPalle.anchor = GridBagConstraints.WEST;
		gbc_lblPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalle.gridx = 1;
		gbc_lblPalle.gridy = 1;
		getContentPane().add(lblPalle, gbc_lblPalle);

		JLabel label = new JLabel("#");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		getContentPane().add(label, gbc_label);

		txtPallestregkode = new JTextField();
		GridBagConstraints gbc_txtPallestregkode = new GridBagConstraints();
		gbc_txtPallestregkode.gridwidth = 2;
		gbc_txtPallestregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPallestregkode.insets = new Insets(0, 0, 5, 5);
		gbc_txtPallestregkode.gridx = 3;
		gbc_txtPallestregkode.gridy = 1;
		getContentPane().add(txtPallestregkode, gbc_txtPallestregkode);
		txtPallestregkode.setColumns(10);

		JLabel lblProdukttype = new JLabel("Produkttype");
		GridBagConstraints gbc_lblProdukttype = new GridBagConstraints();
		gbc_lblProdukttype.insets = new Insets(0, 0, 5, 5);
		gbc_lblProdukttype.anchor = GridBagConstraints.WEST;
		gbc_lblProdukttype.gridx = 1;
		gbc_lblProdukttype.gridy = 2;
		getContentPane().add(lblProdukttype, gbc_lblProdukttype);

		cBox = new JComboBox(Service.getInstance().getProdukttyper().toArray());
		cBox.setLocation(200, 10);
		cBox.setSize(150, 30);
		GridBagConstraints gbc_cBox = new GridBagConstraints();
		gbc_cBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox.gridwidth = 2;
		gbc_cBox.insets = new Insets(0, 0, 5, 5);
		gbc_cBox.gridx = 3;
		gbc_cBox.gridy = 2;
		getContentPane().add(cBox, gbc_cBox);

		JLabel lblBakkestregkode = new JLabel("Bakkestregkode");
		GridBagConstraints gbc_lblBakkestregkode = new GridBagConstraints();
		gbc_lblBakkestregkode.anchor = GridBagConstraints.WEST;
		gbc_lblBakkestregkode.insets = new Insets(0, 0, 5, 5);
		gbc_lblBakkestregkode.gridx = 1;
		gbc_lblBakkestregkode.gridy = 3;
		getContentPane().add(lblBakkestregkode, gbc_lblBakkestregkode);

		JLabel label_1 = new JLabel("#");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 3;
		getContentPane().add(label_1, gbc_label_1);

		txtBakkestregkode = new JTextField();
		GridBagConstraints gbc_txtBakkestregkode = new GridBagConstraints();
		gbc_txtBakkestregkode.insets = new Insets(0, 0, 5, 5);
		gbc_txtBakkestregkode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBakkestregkode.gridx = 3;
		gbc_txtBakkestregkode.gridy = 3;
		getContentPane().add(txtBakkestregkode, gbc_txtBakkestregkode);
		txtBakkestregkode.setColumns(10);

		JButton btnTilfoej = new JButton("Tilfoej");
		btnTilfoej.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Palle palle = Service.getInstance().soegPalle(
						txtPallestregkode.getText());

				if (palle != null) {
					Service.getInstance().opretMellemvare(
							txtBakkestregkode.getText(),
							(Produkttype) cBox.getSelectedItem(), palle);
					list.setListData(palle.getMellemvarer().toArray());
					System.out.println(palle.getMellemvarer());
				}

				System.out.println(Service.getInstance().getMellemvarer());

			}
		});
		GridBagConstraints gbc_btnTilfoej = new GridBagConstraints();
		gbc_btnTilfoej.insets = new Insets(0, 0, 5, 5);
		gbc_btnTilfoej.gridx = 4;
		gbc_btnTilfoej.gridy = 3;
		getContentPane().add(btnTilfoej, gbc_btnTilfoej);

		JLabel lblMellemvarerPPalle = new JLabel("Mellemvarer p\u00E5 palle");
		lblMellemvarerPPalle
				.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		GridBagConstraints gbc_lblMellemvarerPPalle = new GridBagConstraints();
		gbc_lblMellemvarerPPalle.gridwidth = 4;
		gbc_lblMellemvarerPPalle.insets = new Insets(0, 0, 5, 5);
		gbc_lblMellemvarerPPalle.gridx = 1;
		gbc_lblMellemvarerPPalle.gridy = 4;
		getContentPane().add(lblMellemvarerPPalle, gbc_lblMellemvarerPPalle);

		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 4;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 5;
		getContentPane().add(list, gbc_list);

		JButton btnFjern = new JButton("Fjern");
		btnFjern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mellemvare mellemvare = (Mellemvare) list.getSelectedValue();
				Palle palle = Service.getInstance().soegPalle(
						txtPallestregkode.getText());
				if (mellemvare != null && palle != null) {
					palle.removeMellemvare(mellemvare);
					list.setListData(palle.getMellemvarer().toArray());
					System.out.println(palle.getMellemvarer());
				}
			}
		});
		GridBagConstraints gbc_btnFjern = new GridBagConstraints();
		gbc_btnFjern.insets = new Insets(0, 0, 0, 5);
		gbc_btnFjern.gridx = 4;
		gbc_btnFjern.gridy = 6;
		getContentPane().add(btnFjern, gbc_btnFjern);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
