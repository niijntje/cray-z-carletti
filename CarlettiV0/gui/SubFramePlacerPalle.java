package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.DropMode;

import model.MellemlagerPlads;
import model.Palle;

import service.Service;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;


public class SubFramePlacerPalle extends JFrame implements Observer {
	private Subject mainframe;
	private JTextField txtPladsstregkode;
	private JTextField txtpallestregkode;
	private JLabel lblFejlPallestrekodeFindes;

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
		panel.add(btnAnnuller);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
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

		lblFejlPallestrekodeFindes = new JLabel("");
		lblFejlPallestrekodeFindes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFejlPallestrekodeFindes.setForeground(Color.RED);
		lblFejlPallestrekodeFindes.setFont(new Font("Lucida Grande",
				Font.ITALIC, 11));
		lblFejlPallestrekodeFindes.setBounds(6, 202, 308, 16);
		getContentPane().add(lblFejlPallestrekodeFindes);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
