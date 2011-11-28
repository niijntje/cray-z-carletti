package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.MellemlagerPlads;
import model.Palle;
import service.ObjectCreater;
import service.Service;
/**
 * 
 * @author Design: Cederdorff, Funktionalitet: Mads
 *
 */
public class SubFramePlacerPalle extends JFrame implements Observer {
	private Subject mainframe;
	private JTextField txtPladsstregkode;
	private JTextField txtpallestregkode;
	private Controller controller = new Controller();
	private JButton btnOk, btnAnnuller;

	public SubFramePlacerPalle(MainFrame mainframe) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Placer palle på mellemvarelager");
		this.setLocation(200, 200);
		this.setSize(320, 300);

		this.mainframe = mainframe;
		getContentPane().setLayout(null);

		JLabel lblScanStregkode = new JLabel("Scan eller indtast stregkode");
		lblScanStregkode.setBounds(6, 6, 308, 16);
		getContentPane().add(lblScanStregkode);

		JPanel panel = new JPanel();
		panel.setBounds(6, 240, 308, 32);
		getContentPane().add(panel);

		JButton btnAnnuller = new JButton("Annuller");
		btnAnnuller.addActionListener(controller);
		panel.add(btnAnnuller);

		btnOk = new JButton("OK");
		btnOk.addActionListener(controller);
		panel.add(btnOk);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 34, 308, 32);
		getContentPane().add(panel_1);

		JLabel lblPalle = new JLabel("Palle");
		lblPalle.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel_1.add(lblPalle);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 65, 308, 37);
		getContentPane().add(panel_2);

		JLabel label = new JLabel("#");
		panel_2.add(label);

		txtpallestregkode = new JTextField();
		txtpallestregkode.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		txtpallestregkode.setText("pallestregkode");
		panel_2.add(txtpallestregkode);
		txtpallestregkode.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(6, 122, 308, 32);
		getContentPane().add(panel_3);

		JLabel lblPlads = new JLabel("Plads");
		lblPlads.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel_3.add(lblPlads);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(6, 153, 308, 37);
		getContentPane().add(panel_4);

		JLabel label_1 = new JLabel("#");
		panel_4.add(label_1);

		txtPladsstregkode = new JTextField();
		txtPladsstregkode.setText("pladsstregkode");
		txtPladsstregkode.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		txtPladsstregkode.setColumns(10);
		panel_4.add(txtPladsstregkode);
	}

	private class Controller implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnOk) {
				ObjectCreater.getInstance().createSomeObjects();
				// Validering.validerStregkode(txtPladsstregkode.getText());
				// Validering.validerStregkode(txtpallestregkode.getText());
				Palle palle = Service.getInstance().soegPalle(
						txtpallestregkode.getText());
				MellemlagerPlads mellemlagerPlads = Service.getInstance()
						.soegMellemlagerPlads(txtPladsstregkode.getText());

				if (mellemlagerPlads == null || palle == null) {
					throw new RuntimeException(
							"Palle eller mellemlagerplads findes ikke");
				} else {
					Service.getInstance().getPaller().remove(palle);
					Service.getInstance().placerPalleMellemvarelager(palle,
							mellemlagerPlads);
					SubFramePlacerPalle.this.setVisible(false);
				}

			} else if (e.getSource() == btnAnnuller) {
				update();
				SubFramePlacerPalle.this.setVisible(false);
			}
		}
	}

	@Override
	public void update() {
		txtPladsstregkode.setText("");
		txtpallestregkode.setText("");
	}

}
