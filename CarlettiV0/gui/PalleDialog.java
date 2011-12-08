/**
 * PALLEDIALOG
 */
package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import model.Palle;
import service.Service;
import java.awt.Color;

/**
 * Benyttes af andre gui-klasser, når der er behov for at brugeren vælger en ny palle
 * 
 * @author Rita Holst Jacobsen
 * 
 */
public class PalleDialog extends JDialog {

	private Controller controller;
	public JTextField txfStregkode;
	public JButton btnOk;
	public JButton btnCancel;
	private JLabel lblStregkode;
	private Palle palle;
	private boolean closedByOk = false;
	private String stregkode = "";
	private JTextArea txtrForklaring;
	private JButton btnOpretNy;

	public PalleDialog(JFrame owner, String title, String forklaring) {
		super(owner);
		getContentPane().setBackground(Color.PINK);
		setAlwaysOnTop(true);
		controller = new Controller();
		this.setTitle(title);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocation(200, 200);
		this.setSize(200, 250);
		getContentPane().setLayout(null);
		this.setModal(true);

		txfStregkode = new JTextField();
		getContentPane().add(txfStregkode);
		txfStregkode.setToolTipText("Indtast eller scan pallens stregkode");
		txfStregkode.setLocation(15, 88);
		txfStregkode.setSize(150, 25);

		btnOk = new JButton("Ok");
		getContentPane().add(btnOk);
		btnOk.setLocation(15, 181);
		btnOk.setSize(70, 25);
		btnOk.addActionListener(controller);

		btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel);
		btnCancel.setLocation(95, 181);
		btnCancel.setSize(70, 25);

		txtrForklaring = new JTextArea();
		txtrForklaring.setLineWrap(true);
		txtrForklaring.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txtrForklaring.setBackground(UIManager.getColor("CheckBox.background"));
		txtrForklaring.setEditable(false);
		txtrForklaring.setText(forklaring);
		txtrForklaring.setBounds(15, 13, 150, 63);
		getContentPane().add(txtrForklaring);

		btnOpretNy = new JButton("Opret ny");
		btnOpretNy.setEnabled(false);
		btnOpretNy.addActionListener(controller);
		btnOpretNy.setBounds(35, 125, 117, 29);
		getContentPane().add(btnOpretNy);

		btnCancel.addActionListener(controller);
	}

	public boolean isOKed() {
		return this.closedByOk;
	}

	public Palle getPalle() {
		return this.palle;
	}

	private class Controller implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnOk) {
				stregkode = txfStregkode.getText();
				palle = Service.getInstance().soegPalle(stregkode);

				if (palle == null) {
					txtrForklaring
							.setText("Der findes ikke en palle med den angivne stregkode.\nOpret en ny?");
					btnOpretNy.setEnabled(true);
				} else {
					closedByOk = true;
					PalleDialog.this.setVisible(false);
				}
			}

			else if (e.getSource() == btnCancel) {
				closedByOk = false;
				PalleDialog.this.setVisible(false);
			}

			else if (e.getSource() == btnOpretNy) {
				stregkode = txfStregkode.getText();
				Service.getInstance().opretPalle(stregkode);
				palle = Service.getInstance().soegPalle(stregkode);
				closedByOk = true;
				PalleDialog.this.setVisible(false);
			}

		}

	}
}
