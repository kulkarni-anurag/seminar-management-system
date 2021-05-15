import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

class EventFind extends JFrame implements ActionListener{
	Font thead;
	JLabel header, ename_label, evenue_label, edate_label, eduration_label, image_label;
	JTextField ename_text, evenue_text, edate_text, eduration_text;
	JComboBox<String> event_combo;
	JButton search_event, update_event, reset, select_date, insert_photo;
	FileDialog fd;
	File f;
	
	Connection conn;
	String events[];
  
	public EventFind(){
		
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
	
		header = new JLabel("Event Details");
		header.setFont(thead);
	
		ename_label = new JLabel("Select Event Name:");
		event_combo = new JComboBox<>(events);
		ename_text = new JTextField(20);
		
		evenue_label = new JLabel("Event Venue:");
		evenue_text = new JTextField(20);
		
		edate_label = new JLabel("Event Date:");
		select_date = new JButton("Select Event Date");
		edate_text = new JTextField(20);
		
		eduration_label = new JLabel("Event Duration: (in Hours)");
		eduration_text = new JTextField(20);
		
		search_event = new JButton("Search Event");
		
		update_event = new JButton("Update Event");
		
		reset = new JButton("Reset");
		
		insert_photo = new JButton("Insert Photo");
		
		image_label = new JLabel();
		
		add(header);
		
		add(ename_label);
		add(event_combo);
		add(ename_text);
		
		add(evenue_label);
		add(evenue_text);
		
		add(edate_label);
		add(select_date);
		select_date.addActionListener(this);
		add(edate_text);
	
		add(eduration_label);
		add(eduration_text);
		
		add(search_event);
		search_event.addActionListener(this);
	
		add(update_event);
		update_event.addActionListener(this);
		
		add(reset);
		reset.addActionListener(this);
		
		add(insert_photo);
		insert_photo.addActionListener(this);
		
		add(image_label);
		
		setSize(290,650);
		setLayout(new FlowLayout());
		setTitle("Event Details");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
	
		if(e.getSource() == select_date){
			//System.out.println("Calling DatePicker");
			edate_text.setText(new DatePicker(this).setPickedDate());
		}
		
		if(e.getSource() == search_event){
			
			String enames = (String)event_combo.getSelectedItem();
			
			try{
				PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM event WHERE ename = ?");
				pst2.setString(1, enames);
				
				ResultSet st2 = pst2.executeQuery();
				
				if(st2.next()){
					ename_text.setText(st2.getString(2));
					evenue_text.setText(st2.getString(3));
					edate_text.setText(st2.getString(4));
					eduration_text.setText(st2.getString(5));
					Blob b = st2.getBlob(6);
					ImageIcon i = new ImageIcon(b.getBytes(1,(int)b.length()));
					image_label.setIcon(i);
					
					JOptionPane.showMessageDialog(this,"Event Fetched!");
					
				} else {
					JOptionPane.showMessageDialog(this,"Event Not Found!");
				}
				
			} catch(Exception error2){
				JOptionPane.showMessageDialog(this,"Exception Here...");
				System.out.println(error2);
			}
		}
	
		if(e.getSource() == update_event){
			try{
				
				String selectEvent = (String)event_combo.getSelectedItem();
				String ename = ename_text.getText();
				String edate = edate_text.getText();
				String evenue = evenue_text.getText();
				String eduration = eduration_text.getText();
				int dur = Integer.parseInt(eduration);
				FileInputStream eposter = new FileInputStream(f);
				
				Date dt=Date.valueOf(edate);
				//System.out.println(dt);
				PreparedStatement st = conn.prepareStatement("UPDATE event SET ename = ?, evenue = ?, edate = ?, eduration = ?, eposter = ? WHERE ename = ?");
				st.setString(1,ename);
				st.setString(2,evenue);
				st.setDate(3,dt);
				st.setInt(4,dur);
				st.setBinaryStream(5,(InputStream)eposter, (long)f.length());
				st.setString(6,selectEvent);
				int x = st.executeUpdate();
				if(x > 0){
					JOptionPane.showMessageDialog(this,"Event Updated!");
					
					event_combo.removeAllItems();
					
					Statement st5 = conn.createStatement();
					ResultSet rs5 = st5.executeQuery("SELECT * FROM event");
					
					int k = 0;
					while(rs5.next()){
						event_combo.insertItemAt(rs5.getString(2), k);
						k++;
					}
					
				}
			}catch(Exception q){
				JOptionPane.showMessageDialog(this,"Exception Here...");
				System.out.println(q);
			}
		}
		
		if(e.getSource() == reset){
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
		new EventFind();  
	}
}