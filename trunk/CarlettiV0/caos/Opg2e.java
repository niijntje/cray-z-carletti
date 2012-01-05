/**
 * CAOS - Opg. 2e
 */
package caos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * 
 * @author Mads Dahl Jensen
 *
 */
public class Opg2e extends JFrame
{
	private JTextField textField;
	private JTextField txfError;
	public Opg2e() {
		getContentPane().setLayout(null);
		setSize(700, 500);
		
		textField = new JTextField();
		textField.setBounds(12, 29, 116, 22);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		txfError = new JTextField();
		txfError.setForeground(Color.RED);
		txfError.setEditable(false);
		txfError.setBounds(12, 353, 548, 22);
		getContentPane().add(txfError);
		txfError.setColumns(10);
		
		final JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
			}
		});
		list.setBounds(287, 31, 116, 289);
		getContentPane().add(list);
		try {
//	      Class.forName("net.sourceforge.jtds.jdbc.Driver");
//	      Connection connection = DriverManager
//	      		.getConnection(
//	      				"jdbc:jtds:sqlserver://Mads-Pc/CarlettiLageringssytem",
//	      				"ADMIN", "test");
			
			Connection connection = ConnectionHandler.getInstance().getConnection();
			
	      Statement stmt = connection.createStatement();
	      ResultSet res = stmt.executeQuery("Select STREGKODE FROM PALLE");

			ArrayList<String> data = new ArrayList<String>();
			int i = 0;
	      while(res.next())
	      {
	      		data.add(res.getString(1)); 
	      }
	      list.setListData(data.toArray());
	      connection.close();
	      
      }
//      catch (ClassNotFoundException e1) {
//	      e1.printStackTrace();
//      }
      catch (SQLException e1) {
      	e1.printStackTrace();
      }
		
		final JList list_1 = new JList();
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
			}
		});
		list_1.setBounds(415, 29, 116, 289);
		getContentPane().add(list_1);
		
		try {
//	      Class.forName("net.sourceforge.jtds.jdbc.Driver");
//	      Connection connection = DriverManager
//	      		.getConnection(
//	      				"jdbc:jtds:sqlserver://Mads-Pc/CarlettiLageringssytem",
//	      				"ADMIN", "test");
			Connection connection = ConnectionHandler.getInstance().getConnection();
	       
	      Statement stmt = connection.createStatement();
	      ResultSet res1 = stmt.executeQuery("SELECT NAVN FROM PRODUKTTYPE");
	      
	      

	      ArrayList<String> dataTemp = new ArrayList<String>();
	      int i = 1;
	         while(res1.next())
	         {
	         	dataTemp.add(res1.getString(1));
	         	i++;
	         }

	    
	      list_1.setListData(dataTemp.toArray());
	      connection.close();
      }

      catch (SQLException e) {
      	
      	e.printStackTrace();

      }
     
		
		
		JLabel lblPalle = new JLabel("Palle:");
		lblPalle.setBounds(287, 16, 56, 16);
		getContentPane().add(lblPalle);
		
		JLabel lblProdukttype = new JLabel("Produkttype");
		lblProdukttype.setBounds(415, 16, 72, 16);
		getContentPane().add(lblProdukttype);
		
		JLabel lblStregkode = new JLabel("Stregkode");
		lblStregkode.setBounds(12, 16, 77, 16);
		getContentPane().add(lblStregkode);
		
		JButton btnIndst = new JButton("Inds\u00E6t");
		btnIndst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
//	            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//	            Connection connection = DriverManager
//	            		.getConnection(
//	            				"jdbc:jtds:sqlserver://Mads-Pc/CarlettiLageringssytem",
//	            				"ADMIN", "test");
					Connection connection = ConnectionHandler.getInstance().getConnection();
	            Statement stmt = connection.createStatement();
	            
	            String stregkode = textField.getText().trim();
	            String palle = (String) list.getSelectedValue();
	            String produkttype =(String) list_1.getSelectedValue();
	            
	            if(palle == null)
	            {
	            	throw new RuntimeException("Ingen palle valgt");
	            }
	            if(stregkode == null)
	            {
	            	throw new RuntimeException("ingen stregkode valgt");
	            }
	            if(produkttype==null)
	            {
	            	throw new RuntimeException("Ingen produkttype valgt");
	            }
	            
	            stmt.executeUpdate("INSERT INTO MELLEMVARE (BAKKESTREGKODE, STATUS, TESTMODE, " +
	            		"IGANGVAERENDEDELBEHANDLING_ID, PALLE_STREGKODE, PRODUKTTYPE_NAVN) VALUES " +
	            		"('"+stregkode+"','UNDERBEHANDLING','1',"
	            		+"'1', '"+palle+"' ,'"+produkttype+"');");
	            connection.close();
	            setVisible(false);
            }
            catch (SQLException e) 
            {
            	txfError.setText("SQLException, dette skyldes, at du forsøger at oprette en bakke som findes i forvejen");
            }
            catch(RuntimeException e)
            {
            	txfError.setText(e.getMessage());
            }
			}
		});
		btnIndst.setBounds(12, 293, 97, 25);
		getContentPane().add(btnIndst);
		

	}
}