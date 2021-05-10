import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

class Event extends JFrame implements ActionListener{
	Font thead;
	JLabel header, ename_label, evenue_label, edate_label, eduration_label, image_label;
	JTextField ename_text, evenue_text, edate_text, eduration_text;
	JButton add_event, reset, select_date, insert_photo;
	FileDialog fd;
	File f;
  
	public Event(){
	
		thead = new Font("Times New Roman", Font.BOLD, 30);
	
		header = new JLabel("Event Details");
		header.setFont(thead);
	
		ename_label = new JLabel("Enter Event Name:");
		ename_text = new JTextField(20);
		
		evenue_label = new JLabel("Enter Event Venue:");
		evenue_text = new JTextField(20);
		
		edate_label = new JLabel("Enter Event Date:");
		select_date = new JButton("Select Event Date");
		edate_text = new JTextField(20);
		
		eduration_label = new JLabel("Enter Event Duration: (in Hours)");
		eduration_text = new JTextField(20);
		
		add_event = new JButton("Add Event");
		
		reset = new JButton("Reset");
		
		insert_photo = new JButton("Insert Photo");
		
		image_label = new JLabel();
		
		add(header);
		
		add(ename_label);
		add(ename_text);
		
		add(evenue_label);
		add(evenue_text);
		
		add(edate_label);
		add(select_date);
		select_date.addActionListener(this);
		add(edate_text);
	
		add(eduration_label);
		add(eduration_text);
	
		add(add_event);
		add_event.addActionListener(this);
		
		add(reset);
		reset.addActionListener(this);
		
		add(insert_photo);
		insert_photo.addActionListener(this);
		
		add(image_label);
		
		setSize(290,650);
		setLayout(new FlowLayout());
		setTitle("Event Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		Connection conn = null;
	
		if(e.getSource() == select_date){
			//System.out.println("Calling DatePicker");
			edate_text.setText(new DatePicker(this).setPickedDate());
		}
	
		if(e.getSource() == add_event){
			try{
				Class.forName("com.mysql.jdbc.Driver");//driver name
				conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar","root","");//url, username, password
				//JOptionPane.showMessageDialog(this,"Connection ok...");
				String ename = ename_text.getText();
				String edate = edate_text.getText();
				String evenue = evenue_text.getText();
				String eduration = eduration_text.getText();
				int dur = Integer.parseInt(eduration);
				FileInputStream eposter = new FileInputStream(f);
				
				Date dt=Date.valueOf(edate);
				//System.out.println(dt);
				PreparedStatement st = conn.prepareStatement("INSERT INTO event(ename, evenue, edate, eduration, eposter) VALUES (?,?,?,?, ?)");
				st.setString(1,ename);
				st.setString(2,evenue);
				st.setDate(3,dt);
				st.setInt(4,dur);
				st.setBinaryStream(5,(InputStream)eposter, (long)f.length());
				int x = st.executeUpdate();
				if(x > 0){
					JOptionPane.showMessageDialog(this,"Event Added!");
				}
			}catch(Exception q){
				JOptionPane.showMessageDialog(this,"Exception Here...");
				System.out.println(q);
			}
		}
		
		if(e.getSource() == reset){
			ename_text.setText("");
			edate_text.setText("");
			evenue_text.setText("");
			eduration_text.setText("");
		}
		
		if(e.getSource() == insert_photo){
			fd = new FileDialog(this, "Open File");
			fd.setVisible(true);
			
			f = new File(fd.getDirectory()+fd.getFile());
			
			ImageIcon poster = new ImageIcon(fd.getDirectory()+fd.getFile());
			image_label.setIcon(poster);
		}
	}
	
	public static void main(String args[]){
		new Event();  
	}
}