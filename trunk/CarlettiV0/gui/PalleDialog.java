/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import service.Service;

import model.Palle;

import com.sun.codemodel.internal.JLabel;

/**
 * @author nijntje
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

	public PalleDialog(JFrame owner, String title){
		super(owner);
		controller = new Controller();
		this.setTitle(title);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocation(200, 200);
		this.setSize(200, 220);
		getContentPane().setLayout(null);
		this.setModal(true);



		txfStregkode = new JTextField();
		getContentPane().add(txfStregkode);
		txfStregkode.setToolTipText("Indtast eller scan pallens stregkode");
		txfStregkode.setLocation(15, 83);
		txfStregkode.setSize(150, 25);

		btnOk = new JButton("Ok");
		getContentPane().add(btnOk);
		btnOk.setLocation(15, 145);
		btnOk.setSize(70, 25);
		btnOk.addActionListener(controller);

		btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel);
		btnCancel.setLocation(95, 145);
		btnCancel.setSize(70, 25);
		btnCancel.addActionListener(controller);
	}

	public boolean isOKed() {
		return this.closedByOk;
	}

	public Palle getPalle(){
		return this.palle;
	}

	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnOk) {
				stregkode = txfStregkode.getText();
				palle = Service.getInstance().soegPalle(stregkode);

				if (palle == null) {
					SubFrameAdminPalle opretPalleFrame = new SubFrameAdminPalle();
					opretPalleFrame.setPalleStregkodeTekst(txfStregkode.getText());
					opretPalleFrame.setVisible(true);
				}
				else {
					closedByOk = true;
					PalleDialog.this.setVisible(false);
				}
			}
			
			else if (e.getSource() == btnCancel) {
				closedByOk = false;
				PalleDialog.this.setVisible(false);
			}


		}

	}

}
