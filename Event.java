import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

class Event extends JFrame implements ActionListener{
  Font thead;
  //UtilDateModel model;
  //JDatePanelImpl datePanel;
  //JDatePickerImpl datePicker;
  JLabel header, ename_label, evenue_label, edate_label, eduration_label;
  JTextField ename_text, evenue_text, edate_text, eduration_text;
  JButton add_event, reset;
  
  public Event(){
	
	thead = new Font("Times New Roman", Font.BOLD, 30);
	
	header = new JLabel("Event Details");
	header.setFont(thead);
	
	//model = new UtilDateModel();
	//datePanel = new JDatePanelImpl(model);
	//datePicker = new JDatePickerImpl(datePanel);
	
	ename_label = new JLabel("Enter Event Name:");
	evenue_label = new JLabel("Enter Event Venue:");
	edate_label = new JLabel("Enter Event Date:");
	eduration_label = new JLabel("Enter Event Duration:");
	
	ename_text = new JTextField(20);
	evenue_text = new JTextField(20);
	edate_text = new JTextField(20);
	eduration_text = new JTextField(20);
	
	add_event = new JButton("Add Event");
	reset = new JButton("Reset");
	add_event.addActionListener(this);
	reset.addActionListener(this);
	add(header);
	add(ename_label);
	add(ename_text);
	add(evenue_label);
	add(evenue_text);
	add(edate_label);
	add(edate_text);
	//add(datePicker);
	add(eduration_label);
	add(eduration_text);
	
	add(add_event);
	add(reset);
	
	setVisible(true);
	setSize(290,350);
	setLayout(new FlowLayout());
        setTitle("Event Details");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void actionPerformed(ActionEvent e)
  {
   Connection con=null;
	
	if(e.getSource()==add_event)
	{
	try
	{
		Class.forName("com.mysql.jdbc.Driver");//driver name
        con=DriverManager.getConnection("jdbc:mysql://localhost/seminar","root","");//url, username, password
		//JOptionPane.showMessageDialog(this,"Connection ok...");
		String ename = ename_text.getText();
		String edate = edate_text.getText();
		String evenue = evenue_text.getText();
		String eduration = eduration_text.getText();
		int dur = Integer.parseInt(eduration);
		//System.out.println(ename);
		//System.out.println(edate);
		//System.out.println(evenue);
		//System.out.println(dur);
		Date dt=Date.valueOf(edate);
		System.out.println(dt);
		PreparedStatement st = con.prepareStatement("INSERT INTO event(ename, evenue, edate, eduration) VALUES (?,?,?,?)");
		st.setString(1,ename);
		st.setString(2,evenue);
		st.setDate(3,dt);
		st.setInt(4,dur);
		int x=st.executeUpdate();
		if(x>0) 
		JOptionPane.showMessageDialog(this,"Event Generated ...");
	}catch(Exception q)
	{
	JOptionPane.showMessageDialog(this,"Exception Here...");
	}
	
	}  
  }
  public static void main(String args[]){
	new Event();  
  }
}