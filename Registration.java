import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Registration extends JFrame implements ActionListener, ItemListener{
	
	Font head;
	JLabel header, event_label, branch_label, year_label, grno_label;
	JComboBox<String> event_select, branch_select, year_select, grno_select;
	JButton register;
	Connection conn;
	String []events;
	String []grnos;
	
	public Registration(){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar", "root", "");
			
			Statement st = conn.createStatement();
			ResultSet rs1 = st.executeQuery("SELECT * FROM event");
			
			Statement st3 = conn.createStatement();
			ResultSet rs3 = st3.executeQuery("SELECT * FROM student WHERE sbranch='Computer' AND syear='FY'");
			
			ArrayList<String> addEvent = new ArrayList<String>();
			ArrayList<String> addNewGr = new ArrayList<String>();
			
			while(rs1.next()){
				addEvent.add(rs1.getString(2));
			}
			
			while(rs3.next()){
				addNewGr.add(rs3.getString(1));
			}
			
			events = new String[addEvent.size()];
			grnos = new String[addNewGr.size()];
			
			Iterator itr = addEvent.iterator();
			Iterator itr2 = addNewGr.iterator();
			
			int i = 0;
			while(itr.hasNext()){
				events[i]=(String)itr.next();
				i++;
			}
			
			int j = 0;
			while(itr2.hasNext()){
				grnos[j] = (String)itr2.next();
				j++;
			}
			
		} catch(Exception error){
			JOptionPane.showMessageDialog(this,"Error: "+error);
		}
		
		head = new Font("Times New Roman", Font.BOLD, 30);
		
		header = new JLabel("Event Registration");
		header.setFont(head);
		
		event_label = new JLabel("Select Event:");
		branch_label = new JLabel("Select Branch:");
		year_label = new JLabel("Select Year:");
		grno_label = new JLabel("Select Gr No.:");
		
		String []branches = {"Computer","IT","Instrumentation","Mechanical"};
		String []years = {"FY", "SY", "TY", "B.Tech"};
		
		branch_select = new JComboBox<>(branches);
		year_select = new JComboBox<>(years);
		event_select = new JComboBox<>(events);
		grno_select = new JComboBox<>(grnos);
		
		register = new JButton("Register");
		
		add(header);
		
		add(event_label);
		add(event_select);
		
		add(branch_label);
		add(branch_select);
		branch_select.addItemListener(this);
		
		add(year_label);
		add(year_select);
		year_select.addItemListener(this);
		
		add(grno_label);
		add(grno_select);
		
		add(register);
		register.addActionListener(this);
		
		setLayout(new FlowLayout());
		setTitle("Event Registration");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(270,350);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == register){
			//JOptionPane.showMessageDialog(this,"Register Clicked!");
			String seminar = (String)event_select.getSelectedItem();
			String sgrno = (String)grno_select.getSelectedItem();
			int newsgrno = Integer.parseInt(sgrno);
			
			try{
				
				Statement st4 = conn.createStatement();
				
				ResultSet rs4 = st4.executeQuery("SELECT eid FROM `event` WHERE ename = '"+ seminar +"'");
				int geventid = 0;
				while(rs4.next()){
					geventid = rs4.getInt(1);
				}
				
				PreparedStatement pst1 = conn.prepareStatement("INSERT INTO registration(grno, event) VALUES (?,?)");
				pst1.setInt(1, newsgrno);
				pst1.setInt(2, geventid);
				
				int exe = pst1.executeUpdate();
				if(exe > 0){
					JOptionPane.showMessageDialog(this,"Registration Successful!");
				}
			} catch (Exception error2){
				JOptionPane.showMessageDialog(this,"Error!");
				System.out.println(error2);
			}
		}
	}
	
	public void itemStateChanged(ItemEvent event) {
		
		if(event.getStateChange() == ItemEvent.SELECTED){
			//System.out.println(event.getItem());
			String branch = (String)branch_select.getSelectedItem();
			String year = (String)year_select.getSelectedItem();
			
			try{
				//System.out.println("Coming...");
				Statement st1 = conn.createStatement();
				ResultSet rs2 = st1.executeQuery("SELECT * FROM student WHERE sbranch='"+ branch +"' AND syear='"+year+"'");
				
				grno_select.removeAllItems();
				int k = 0;
				while(rs2.next()){
					grno_select.insertItemAt(rs2.getString(1), k);
					//System.out.println(rs2.getInt(1) + " " + rs2.getString(2));
					k++;
				}
				
			} catch(Exception error1){
				System.out.println(error1);
			}
			
		}
		
    }
	
	public static void main(String []args){
		new Registration();
	}
}