import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;

class Guest extends JFrame implements ActionListener{
	
  Font thead;
  JLabel head, gname_label, ggender_label, gexpert_label, gcompany_label, gphoto_label, gevent_label;
  JTextField gname_text, gexpert_text, gcompany_text;


  JComboBox<String> event_combo;
  JRadioButton male, female;
  JButton add_guest, reset;
  Connection conn;
  String events[]=null;
  public Guest(){
   		try{
			Class.forName("com.mysql.jdbc.Driver");//driver name
			conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar","root","");//url, username, password
			//JOptionPane.showMessageDialog(this,"Connection ok...");
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery("Select * from event");
			
			thead = new Font("Times New Roman", Font.BOLD, 30);
	
			head = new JLabel("Guest's Details");
			head.setFont(thead);
	
			gname_label = new JLabel("Enter Guest's Name:");
			ggender_label = new JLabel("Enter Guest's Gender:");
			gexpert_label = new JLabel("Enter Guest's Expertise:");
			gcompany_label = new JLabel("Enter Guest's Company:");
			gphoto_label = new JLabel("Upload Guest's Photo:");
			gevent_label = new JLabel("Select Event:");
			int i=0;
			ArrayList <String>arr=new ArrayList<String>();
			while(rs.next()){
				arr.add(rs.getString(2));
				i++;
			}
			System.out.println(arr);
   
    male = new JRadioButton("Male");
    female = new JRadioButton("Female");
   
    gname_text = new JTextField(20);
    gexpert_text = new JTextField(20);
    gcompany_text = new JTextField(20);
	String str[]=new String[arr.size()];
	Iterator itr=arr.iterator();
	i=0;
	while(itr.hasNext())
	{
	str[i]=(String)itr.next();
	i++;
    }
	event_combo = new JComboBox<>(str);
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
	} catch(Exception ee){
		System.out.println(ee);
	}
	setVisible(true);
	setSize(300,350);
	setLayout(new FlowLayout());
    setTitle("Guest Details");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void actionPerformed(ActionEvent a)
  {
	  if(a.getSource()==add_guest)
	  {
		  try
		  {
		  String  gen=null;
		  if(male.isSelected())
		  gen="Male";
		  else
		  gen="Female";
		  } catch(Exception eee){
				System.out.println(eee);
			}
	  }
  }
  public static void main(String args[]){
    new Guest();
  }
}