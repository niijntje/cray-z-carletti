/**
 * CAOS - Opg. 2 - CaosFrame
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
import java.awt.Color;

/**
 * @author Rita Holst Jacobsen
 *
 */
public class CaosFrame extends JFrame {
	private Controller controller;
	private JButton btnOpga;
	private JButton btnOpgb;
	private JButton btnOpgc;
	private JButton btnOpgd;
	private JButton btnOpge;
	private JButton btnOpgf;
	private JButton btnOpgg;


	public CaosFrame() {
		getContentPane().setBackground(Color.PINK);
		setTitle("CAOS - Opg. 2");
		this.setSize(200, 300);
		this.controller = new Controller();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
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
		
		btnOpga = new JButton("Opg 2a");
		btnOpga.addActionListener(controller);
		
		btnOpgb = new JButton("Opg 2b");
		btnOpgb.addActionListener(controller);
		
		btnOpgc = new JButton("Opg 2c");
		btnOpgc.addActionListener(controller);
		
		btnOpgd = new JButton("Opg 2d");
		btnOpgd.addActionListener(controller);
		
		btnOpge = new JButton("Opg 2e");
		btnOpge.addActionListener(controller);
		
		btnOpgf = new JButton("Opg 2f");
		btnOpgf.addActionListener(controller);
		
		btnOpgg = new JButton("Opg 2g");
		btnOpgg.addActionListener(controller);
		
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
			
			if (e.getSource() == btnOpga){
				Opg2a opg2a;
				Opg2aFrame opg2aFrame;
				try {
					opg2a = new Opg2a();
	            opg2aFrame = new Opg2aFrame(opg2a);
	            opg2aFrame.setVisible(true);
            }
            catch (SQLException e1) {
	            e1.printStackTrace();
            }
			}
			else if (e.getSource() == btnOpgb){
				Opg2b opg2b;
            try {
	            opg2b = new Opg2b();
	            opg2b.setVisible(true);
            }
            catch (SQLException e1) {
	            e1.printStackTrace();
            }
				
			}
			else if(e.getSource() == btnOpgc){
				Opg2c opg2c;
            try {
	            opg2c = new Opg2c();
	            opg2c.setVisible(true);
            }
            catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
            }
            
			}
//			else if (e.getSource() == btnOpgd){
//				try {
//	            Opg2d opg2d = new Opg2d();
//	            opg2d.setVisible(true);
//            }
//            catch (SQLException e1) {
//	            e1.printStackTrace();
//            }
//			}
				
//				else if (e.getSource() == btnOpge){
//					try {
//		            Opg2e opg2e = new Opg2e();
//		            opg2e.setVisible(true);
//	            }
//	            catch (SQLException e1) {
//		            e1.printStackTrace();
//	            }
//				}


			else if(e.getSource() == btnOpgf){
				Opg2f opg2f = new Opg2f();
				opg2f.setVisible(true);
				
			}
			
			else if (e.getSource() == btnOpgg){
				try {
	            Opg2g opg2g = new Opg2g();
	            opg2g.setVisible(true);
            }
            catch (SQLException e1) {
	            e1.printStackTrace();
            }
			}
	      
	      
      }
		
	}

}
