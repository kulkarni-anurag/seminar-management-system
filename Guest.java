import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

class Guest extends JFrame implements ActionListener{
	
	Font thead,comfont;
	JLabel head, gname_label, gemail_label, ggender_label, gexpert_label, gcompany_label, gevent_label, image_label;
	JTextField gname_text, gemail_text, gexpert_text, gcompany_text;
	JComboBox<String> event_combo;
	JRadioButton male, female;
	JButton add_guest, reset, insert_photo;
	FileDialog fd;
	File f;
	
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
		setLayout(null);
		
		thead = new Font("Times New Roman", Font.BOLD, 30);
		comfont = new Font("Times New Roman", Font.BOLD, 18);
		head = new JLabel("Guest's Details");
		head.setBounds(220,8,300,100);
		head.setFont(thead);
		
		gname_label = new JLabel("Enter Guest's Name:");
		gname_text = new JTextField(20);
		gname_label.setFont(comfont);
		gname_text.setFont(comfont);
		gname_label.setBounds(10,80,300,50);
		gname_text.setBounds(10,120,300,40);
		
		gemail_label = new JLabel("Enter Guest's Email:");
		gemail_text = new JTextField(20);
		gemail_label.setFont(comfont);
		gemail_text.setFont(comfont);
		gemail_label.setBounds(10,170,300,40);
		gemail_text.setBounds(10,210,300,40);

		ggender_label = new JLabel("Select Guest's Gender:");
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		ggender_label.setFont(comfont);
		male.setFont(comfont);
		female.setFont(comfont);		
		ggender_label.setBounds(10,260,300,40);
		male.setBounds(10,300,100,40);
		female.setBounds(120,300,200,40);

		gexpert_label = new JLabel("Enter Guest's Expertise:");
		gexpert_text = new JTextField(20);
		gexpert_label.setFont(comfont);
		gexpert_text.setFont(comfont);		
		gexpert_label.setBounds(10,350,300,40);
		gexpert_text.setBounds(10,390,300,40);

		gcompany_label = new JLabel("Enter Guest's Company:");
		gcompany_text = new JTextField(20);
		gcompany_label.setFont(comfont);
		gcompany_text.setFont(comfont);		
		gcompany_label.setBounds(10,450,300,40);
		gcompany_text.setBounds(10,490,300,40);
		
		gevent_label = new JLabel("Select Event:");
		event_combo = new JComboBox<>(events);
		gevent_label.setFont(comfont);
		event_combo.setFont(comfont);		
		gevent_label.setBounds(10,550,100,40);
		event_combo.setBounds(10,590,300,40);

		add_guest = new JButton("Add Guest");
		add_guest.setBounds(10,650,120,40);		
		reset = new JButton("Reset Data");
		reset.setBounds(140,650,120,40);		
		insert_photo = new JButton("Insert Photo");
		insert_photo.setBounds(270,650,140,40);
		add_guest.setFont(comfont);
		reset.setFont(comfont);
		insert_photo.setFont(comfont);		
		image_label = new JLabel();
		image_label.setBounds(400,100,250,250);
	   
		add(head);
	   
		add(gname_label);
		add(gname_text);
		
		add(gemail_label);
		add(gemail_text);
	   
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
		
		add(insert_photo);
		insert_photo.addActionListener(this);	
		add(image_label);
		
		setVisible(true);
		setSize(700,750);

		setTitle("Guest Details");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == add_guest){
			//JOptionPane.showMessageDialog(this,"Add Guest Clicked!");
			String gname = gname_text.getText();
			String gemail = gemail_text.getText();
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
				FileInputStream gphoto = new FileInputStream(f);
				
				Statement st2 = conn.createStatement();
				
				ResultSet rs2 = st2.executeQuery("SELECT eid FROM `event` WHERE ename = '"+ gevent +"'");
				//System.out.println(rs2);
				int geventid = 0;
				while(rs2.next()){
					geventid = rs2.getInt(1);
				}
				
				PreparedStatement pst1 = conn.prepareStatement("INSERT INTO guest(gname, gemail, ggender, gexpert, gcompany, event, gphoto) VALUES (?,?,?,?,?,?,?)");
				pst1.setString(1, gname);
				pst1.setString(2, gemail);
				pst1.setString(3, gen);
				pst1.setString(4, gexpert);
				pst1.setString(5, gcompany);
				pst1.setInt(6, geventid);
				pst1.setBinaryStream(7, (InputStream)gphoto, (long)f.length());
				
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
			gemail_text.setText("");
			gexpert_text.setText("");
			gcompany_text.setText("");
		}
		
		if(event.getSource() == insert_photo){
			fd = new FileDialog(this, "Open File");
			fd.setVisible(true);
			
			f = new File(fd.getDirectory()+fd.getFile());
			
			ImageIcon poster = new ImageIcon(fd.getDirectory()+fd.getFile());
			image_label.setIcon(poster);
		}
	}
	
	public static void main(String []args){
		new Guest();
	}
}