import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

class RegistrationList extends JFrame implements ActionListener{
	Font thead,comfont;
	JLabel header, ename_label;
	JComboBox<String> event_combo;
	JButton search_registration;
	JTable table;
	String []title = {"GR NO.", "Name", "Email", "Attendance"};
	JScrollPane tablepane;
	
	Connection conn;
	String events[];
  
	public RegistrationList(){
		setLayout(null);
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar", "root", "");
			comfont = new Font("Times New Roman", Font.BOLD, 18);	
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
	
		header = new JLabel("Registration Details");
		header.setFont(thead);
	
		ename_label = new JLabel("Select Event Name:");
		event_combo = new JComboBox<>(events);
		
		search_registration = new JButton("Search Registrations");
		
		header.setBounds(240,10,300,100);
		
		ename_label.setFont(comfont);
		event_combo.setFont(comfont);
		
		ename_label.setBounds(10,80,300,50);
		event_combo.setBounds(10,120,300,40);
		
		search_registration.setFont(comfont);
		search_registration.setBounds(410,118,200,42);
		
		add(header);
		
		add(ename_label);
		add(event_combo);
		
		add(search_registration);
		search_registration.addActionListener(this);
		
		setSize(700,650);
		setTitle("Registration Details");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == search_registration){
			
			String enames = (String)event_combo.getSelectedItem();
			
			try{
				PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM event WHERE ename = ?");
				pst2.setString(1, enames);
				
				ResultSet st2 = pst2.executeQuery();
				
				int eid = 0;
				int rowcount=0;
				Vector v = new Vector();
				
				if(st2.next()){
					eid = st2.getInt(1);
				}
				
				//System.out.println(eid);
				
				PreparedStatement pst3 = conn.prepareStatement("SELECT st.gr_no, st.sname, st.semail, rg.attendance FROM student st, registration rg WHERE st.gr_no = rg.grno AND rg.event = ?");
				pst3.setInt(1, eid);
				
				ResultSet st3 = pst3.executeQuery();
				
				int grnos = 0;
				String names = "";
				
				/*while(st3.next()){
					grnos = st3.getInt(1);
					names = st3.getString(2);
					
					v.addElement(rs.getString(i));
				}*/
				
				while(st3.next()){
					rowcount++;
					for(int i=1; i<=4 ;i++){
						v.addElement(st3.getString(i));
					}
                }
				
				String row[][]=new String[rowcount][4];
				
				int rc=0;
				while(!v.isEmpty() && rc<rowcount){
					for(int l=1;l<=4;l++){
						row[rc][l-1]=(String)v.get(rc*4+l-1);
					}
					rc++;
				}
				   table = new JTable(row,title);
				   tablepane = new JScrollPane(table);
				   tablepane.setBounds(80,270,500,200);
				   add(tablepane);
				
				//System.out.println(grnos);
				//System.out.println(names);
				
			} catch(Exception error3){
				System.out.println(error3);
			}
		}
	}
	
	public static void main(String args[]){
		new RegistrationList();  
	}
}