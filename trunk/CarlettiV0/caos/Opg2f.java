package caos;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import com.apple.dnssd.TXTRecord;

public class Opg2f extends JFrame {

	private JPanel contentPane;
	private JLabel lblDeKasseredeMellemvarer;
	private JButton btnRydOp;

	/**
	 * Create the frame.
	 */
	public Opg2f() {
		setTitle("Ryd op");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 197, 175);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnRydOp = new JButton("Ryd op");
		btnRydOp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rydOp();
				lblDeKasseredeMellemvarer.setVisible(true);
			}
		});
		
		JTextPane txtpnTrykrydOp = new JTextPane();
		txtpnTrykrydOp.setBackground(Color.PINK);
		txtpnTrykrydOp.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		txtpnTrykrydOp.setText("Tryk \"Ryd op\" for at slette kasserede mellemvarer i databasen.\nDer kaldes en stored procedure gemt i databasen.");
		
		lblDeKasseredeMellemvarer = new JLabel("Kasserede mellemvarer slettet");
		lblDeKasseredeMellemvarer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblDeKasseredeMellemvarer.setVisible(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnTrykrydOp, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(45)
							.addComponent(btnRydOp)))
					.addContainerGap(2, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(120, Short.MAX_VALUE)
					.addComponent(lblDeKasseredeMellemvarer)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnTrykrydOp, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnRydOp)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblDeKasseredeMellemvarer))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void rydOp(){
		String sql = "execute RydOp";
		try {
	      ConnectionHandler.getInstance().getConnection().createStatement().execute(sql);
      }
      catch (SQLException e) {
	      e.printStackTrace();
      }
	}
}
