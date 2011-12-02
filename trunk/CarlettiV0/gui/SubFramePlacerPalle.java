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
import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * @author Design: Cederdorff, Funktionalitet: Mads
 * 
 */
public class SubFramePlacerPalle extends JFrame implements Observer, Subject {
	private JTextField txtPladsstregkode;
	private JTextField txtpallestregkode;
	private Controller controller = new Controller();
	private JButton btnOk, btnAnnuller;

	private ArrayList<Observer> observers;
	private JLabel lblPalleEllerPlads;

	public SubFramePlacerPalle(MainFrame mainFrame) {
		getContentPane().setBackground(Color.PINK);
		this.observers = new ArrayList<Observer>();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Placer palle på mellemvarelager");
		this.setLocation(200, 200);
		this.setSize(320, 300);
		getContentPane().setLayout(null);
		registerObserver(mainFrame);

		JLabel lblScanStregkode = new JLabel("Scan eller indtast stregkode");
		lblScanStregkode.setBounds(6, 6, 308, 16);
		getContentPane().add(lblScanStregkode);

		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setBounds(6, 240, 308, 32);
		getContentPane().add(panel);

		JButton btnAnnuller = new JButton("Annuller");
		btnAnnuller.addActionListener(controller);
		panel.add(btnAnnuller);

		btnOk = new JButton("OK");
		btnOk.addActionListener(controller);
		panel.add(btnOk);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		panel_1.setBounds(6, 34, 308, 32);
		getContentPane().add(panel_1);

		JLabel lblPalle = new JLabel("Palle");
		lblPalle.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel_1.add(lblPalle);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.PINK);
		panel_2.setBounds(6, 65, 308, 37);
		getContentPane().add(panel_2);

		JLabel label = new JLabel("#");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		panel_2.add(label);

		txtpallestregkode = new JTextField();
		txtpallestregkode.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		txtpallestregkode.setText("pallestregkode");
		panel_2.add(txtpallestregkode);
		txtpallestregkode.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.PINK);
		panel_3.setBounds(6, 122, 308, 32);
		getContentPane().add(panel_3);

		JLabel lblPlads = new JLabel("Plads");
		lblPlads.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel_3.add(lblPlads);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.PINK);
		panel_4.setBounds(6, 153, 308, 37);
		getContentPane().add(panel_4);

		JLabel label_1 = new JLabel("#");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		panel_4.add(label_1);

		txtPladsstregkode = new JTextField();
		txtPladsstregkode.setText("pladsstregkode");
		txtPladsstregkode.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		txtPladsstregkode.setColumns(10);
		panel_4.add(txtPladsstregkode);

		lblPalleEllerPlads = new JLabel("palle eller plads eksisterer ikke");
		lblPalleEllerPlads.setForeground(Color.RED);
		lblPalleEllerPlads.setFont(new Font("Lucida Grande", Font.ITALIC, 10));
		lblPalleEllerPlads.setBounds(154, 212, 160, 16);
		getContentPane().add(lblPalleEllerPlads);
		lblPalleEllerPlads.setVisible(false);
	}

	public void setPalleStregkodeTekst(String stregkode) {
		txtpallestregkode.setText(stregkode);
	}

	private class Controller implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnOk) {
				Palle palle = Service.getInstance().soegPalle(
						txtpallestregkode.getText());
				MellemlagerPlads mellemlagerPlads = Service.getInstance()
						.soegMellemlagerPlads(txtPladsstregkode.getText());
				System.out.println(palle);
				System.out.println(mellemlagerPlads);
				if (mellemlagerPlads == null || palle == null) {
					lblPalleEllerPlads.setVisible(true);
				} else {
					Service.getInstance().placerPalleMellemvarelager(palle,
							mellemlagerPlads);
					SubFramePlacerPalle.this.setVisible(false);
				}

			} else if (e.getSource() == btnAnnuller) {
				update();
				SubFramePlacerPalle.this.setVisible(false);
			}
			System.out.println("hej");
			notifyObservers();
		}
	}

	@Override
	public void update() {
		txtPladsstregkode.setText("");
		txtpallestregkode.setText("");
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
