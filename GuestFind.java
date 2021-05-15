import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

class GuestFind extends JFrame implements ActionListener{
	
	Font thead;
	JLabel head, gid_label1, gid_label2, gname_label, ggender_label, gexpert_label, gcompany_label, gevent_label, image_label;
	JTextField gname_text, gexpert_text, gcompany_text;
	JComboBox<String> event_combo;
	JRadioButton male, female;
	JButton search_guest, update_guest, reset, insert_photo;
	FileDialog fd;
	File f;
	
	Connection conn;
	String events[];
	
	public GuestFind(){
		
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
		
		gid_label1 = new JLabel("Guest's Id:");
		gid_label2 = new JLabel();
		
		gname_label = new JLabel("Guest's Name:");
		gname_text = new JTextField(20);
		
		ggender_label = new JLabel("Guest's Gender:");
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		
		gexpert_label = new JLabel("Guest's Expertise:");
		gexpert_text = new JTextField(20);
		
		gcompany_label = new JLabel("Guest's Company:");
		gcompany_text = new JTextField(20);
		
		
		gevent_label = new JLabel("Select Event:");
		event_combo = new JComboBox<>(events);
		
		search_guest = new JButton("Search Guest");
		
		update_guest = new JButton("Update Guest");
		
		reset = new JButton("Reset Data");
		
		insert_photo = new JButton("Insert Photo");
		
		image_label = new JLabel();
	   
		add(head);
	   
		add(gid_label1);
		add(gid_label2);
	   
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
		
		add(search_guest);
		search_guest.addActionListener(this);
		
		update_guest.addActionListener(this);
		add(update_guest);
		
		add(reset);
		reset.addActionListener(this);
		
		add(insert_photo);
		insert_photo.addActionListener(this);
		
		add(image_label);
		
		setVisible(true);
		setSize(270,650);
		setLayout(new FlowLayout());
		setTitle("Guest Details");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent event){
		
		if(event.getSource() == search_guest){
			String gnames = gname_text.getText();
			
			try{
				PreparedStatement pst6 = conn.prepareStatement("SELECT * FROM guest WHERE gname = ?");
				pst6.setString(1, gnames);
				
				ResultSet st6 = pst6.executeQuery();
				
				if(st6.next()){
					gid_label2.setText(st6.getString(1));
					gname_text.setText(st6.getString(2));
					String tgender = st6.getString(3);
					if(tgender.equals("Male")){
						male.setSelected(true);
						female.setSelected(false);
					} else {
						female.setSelected(true);
						male.setSelected(false);
					}
					gexpert_text.setText(st6.getString(4));
					gcompany_text.setText(st6.getString(5));
					event_combo.setSelectedIndex(st6.getInt(6) - 1);
					Blob b = st6.getBlob(7);
					ImageIcon i = new ImageIcon(b.getBytes(1,(int)b.length()));
					image_label.setIcon(i);
					
					JOptionPane.showMessageDialog(this,"Guest Fetched!");
					
				} else {
					JOptionPane.showMessageDialog(this,"Guest Not Found!");
				}
				
			} catch(Exception error2){
				JOptionPane.showMessageDialog(this,"Exception Here...");
				System.out.println(error2);
			}
		}
		
		if(event.getSource() == update_guest){
			//JOptionPane.showMessageDialog(this,"Add Guest Clicked!");
			String gids = gid_label2.getText();
			int gidi = Integer.parseInt(gids);
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
				FileInputStream gphoto = new FileInputStream(f);
				
				Statement st2 = conn.createStatement();
				
				ResultSet rs2 = st2.executeQuery("SELECT eid FROM `event` WHERE ename = '"+ gevent +"'");
				//System.out.println(rs2);
				int geventid = 0;
				while(rs2.next()){
					geventid = rs2.getInt(1);
				}
				
				PreparedStatement pst1 = conn.prepareStatement("UPDATE guest SET gname = ?, ggender = ?, gexpert = ?, gcompany = ?, event = ?, gphoto = ? WHERE gid = ?");
				pst1.setString(1, gname);
				pst1.setString(2, gen);
				pst1.setString(3, gexpert);
				pst1.setString(4, gcompany);
				pst1.setInt(5, geventid);
				pst1.setBinaryStream(6, (InputStream)gphoto, (long)f.length());
				pst1.setInt(7, gidi);
				
				int exe = pst1.executeUpdate();
				if(exe > 0){
					JOptionPane.showMessageDialog(this,"Guest Updated!");
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
		
		if(event.getSource() == insert_photo){
			fd = new FileDialog(this, "Open File");
			fd.setVisible(true);
			
			f = new File(fd.getDirectory()+fd.getFile());
			
			ImageIcon poster = new ImageIcon(fd.getDirectory()+fd.getFile());
			image_label.setIcon(poster);
		}
	}
	
	public static void main(String []args){
		new GuestFind();
	}
}