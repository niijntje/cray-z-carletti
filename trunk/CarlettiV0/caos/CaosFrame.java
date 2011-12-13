/**
 * 
 */
package caos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author Rita Holst Jacobsen
 *
 */
public class CaosFrame extends JFrame {
	private JButton btnOpgb;
	private Controller controller;

	public CaosFrame() {
		setTitle("CAOS - Opg. 2");
		this.setSize(200, 300);
		this.controller = new Controller();
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(213, Short.MAX_VALUE))
		);
		
		JButton btnOpga = new JButton("Opg 2a");
		
		btnOpgb = new JButton("Opg 2b");
		btnOpgb.addActionListener(controller);
		
		JButton btnOpgc = new JButton("Opg 2c");
		
		JButton btnOpgd = new JButton("Opg 2d");
		
		JButton btnOpge = new JButton("Opg 2e");
		
		JButton btnOpgf = new JButton("Opg 2f");
		
		JButton btnOpgg = new JButton("Opg 2g");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnOpga)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(btnOpgc)
							.addComponent(btnOpgb)
							.addComponent(btnOpgd)
							.addComponent(btnOpge)
							.addComponent(btnOpgf)
							.addComponent(btnOpgg)))
					.addContainerGap(196, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(btnOpga)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpgb)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpgc)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpgd)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpge)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpgf)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpgg)
					.addContainerGap(10, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
	
	private class Controller implements ActionListener {

		@Override
      public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btnOpgb){
				Opg2b opg2b;
            try {
	            opg2b = new Opg2b();
	            opg2b.setVisible(true);
            }
            catch (SQLException e1) {
	            e1.printStackTrace();
            }
				
			}
	      
	      
      }
		
	}

}
