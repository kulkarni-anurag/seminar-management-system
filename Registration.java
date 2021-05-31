import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Registration extends JFrame implements ActionListener, ItemListener{
	
	Font head,comfont;
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
		comfont = new Font("Times New Roman", Font.PLAIN, 18);
		
		header = new JLabel("Event Registration");
		header.setFont(head);
		
		event_label = new JLabel("Select Event:");
		branch_label = new JLabel("Select Branch:");
		year_label = new JLabel("Select Year:");
		grno_label = new JLabel("Select Gr No.:");
		event_label.setFont(comfont);
		branch_label.setFont(comfont);
		year_label.setFont(comfont);
		grno_label.setFont(comfont);
		event_label.setFont(comfont);
		String []branches = {"Computer","IT","Instrumentation","Mechanical"};
		String []years = {"FY", "SY", "TY", "B.Tech"};
		
		branch_select = new JComboBox<>(branches);
		year_select = new JComboBox<>(years);
		event_select = new JComboBox<>(events);
		grno_select = new JComboBox<>(grnos);
		branch_select.setFont(comfont);
		year_select.setFont(comfont);
		event_select.setFont(comfont);
		grno_select.setFont(comfont);
		branch_select.setFont(comfont);
		register = new JButton("Register");
		register.setFont(comfont);
		setLayout(null);
		header.setBounds(100,20,300,100);		
		add(header);
		event_label.setBounds(10,80,300,50);
		add(event_label);
		event_select.setBounds(10,120,300,40);
		add(event_select);
		branch_label.setBounds(10,170,300,40);
		add(branch_label);
		branch_select.setBounds(10,210,300,40);
		add(branch_select);
		branch_select.addItemListener(this);
		year_label.setBounds(10,270,300,40);
		add(year_label);
		year_select.setBounds(10,310,300,40);
		add(year_select);
		year_select.addItemListener(this);
		grno_label.setBounds(10,360,300,40);
		add(grno_label);
		grno_select.setBounds(10,400,300,40);
		add(grno_select);
		register.setBounds(100,470,100,40);
		add(register);
		register.addActionListener(this);
		

		setTitle("Event Registration");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,600);
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