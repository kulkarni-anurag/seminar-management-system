import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

class Guest extends JFrame implements ActionListener{
	
	Font thead;
	JLabel head, gname_label, ggender_label, gexpert_label, gcompany_label, gphoto_label, gevent_label;
	JTextField gname_text, gexpert_text, gcompany_text;
	JComboBox<String> event_combo;
	JRadioButton male, female;
	JButton add_guest, reset;
	
	Connection conn;
	String events[];
	
	public Guest(){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar", "root", "");
			
			Statement st = conn.createStatement();
			ResultSet rs1 = st.executeQuery("SELECT * FROM event");
			
			ArrayList<String> addEvent = new ArrayList<String>();
			
			while(rs1.next()){
				addEvent.add(rs1.getString(2));
			}
			
			events = new String[addEvent.size()];
			
			Iterator itr = addEvent.iterator();
			
			int i = 0;
			while(itr.hasNext()){
				events[i]=(String)itr.next();
				i++;
			}
			
		} catch(Exception error1){
			JOptionPane.showMessageDialog(this,"Error: "+error1);
		}
		
		thead = new Font("Times New Roman", Font.BOLD, 30);
	
		head = new JLabel("Guest's Details");
		head.setFont(thead);
		
		gname_label = new JLabel("Enter Guest's Name:");
		gname_text = new JTextField(20);
		
		ggender_label = new JLabel("Enter Guest's Gender:");
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		
		gexpert_label = new JLabel("Enter Guest's Expertise:");
		gexpert_text = new JTextField(20);
		
		gcompany_label = new JLabel("Enter Guest's Company:");
		gcompany_text = new JTextField(20);
		
		gphoto_label = new JLabel("Upload Guest's Photo:");
		
		
		gevent_label = new JLabel("Select Event:");
		event_combo = new JComboBox<>(events);
		
		add_guest = new JButton("Add Guest");
		reset = new JButton("Reset Data");
	   
		add(head);
	   
		add(gname_label);
		add(gname_text);
	   
		add(ggender_label);
	   
		ButtonGroup gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
	   
		add(male);
		add(female);
	   
		add(gexpert_label);
		add(gexpert_text);
	   
		add(gcompany_label);
		add(gcompany_text);
		
		add(gevent_label);
		add(event_combo);
		
		add_guest.addActionListener(this);
		add(add_guest);
		
		add(reset);
		reset.addActionListener(this);
		
		setVisible(true);
		setSize(300,350);
		setLayout(new FlowLayout());
		setTitle("Guest Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == add_guest){
			//JOptionPane.showMessageDialog(this,"Add Guest Clicked!");
			String gname = gname_text.getText();
			String gexpert = gexpert_text.getText();
			String gcompany = gcompany_text.getText();
			String gevent = (String)event_combo.getSelectedItem();
			//System.out.println(gevent);
			String gen = null;
			if(male.isSelected()){
				gen="Male";
			} else{
				gen="Female";
			}
			
			try{
				Statement st2 = conn.createStatement();
				
				ResultSet rs2 = st2.executeQuery("SELECT eid FROM `event` WHERE ename = '"+ gevent +"'");
				//System.out.println(rs2);
				int geventid = 0;
				while(rs2.next()){
					geventid = rs2.getInt(1);
				}
				
				PreparedStatement pst1 = conn.prepareStatement("INSERT INTO guest(gname, ggender, gexpert, gcompany, event) VALUES (?,?,?,?,?)");
				pst1.setString(1, gname);
				pst1.setString(2, gen);
				pst1.setString(3, gexpert);
				pst1.setString(4, gcompany);
				pst1.setInt(5, geventid);
				
				int exe = pst1.executeUpdate();
				if(exe > 0){
					JOptionPane.showMessageDialog(this,"Guest Added!");
				}
				
			} catch (Exception error2){
				JOptionPane.showMessageDialog(this,"Error!");
				System.out.println(error2);
			}
			
		}
		
		if(event.getSource() == reset){
			gname_text.setText("");
			gexpert_text.setText("");
			gcompany_text.setText("");
		}
	}
	
	public static void main(String []args){
		new Guest();
	}
}