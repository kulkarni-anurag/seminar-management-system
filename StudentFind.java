import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class StudentFind extends JFrame implements ActionListener{
	Font head;
	JLabel header, name_label, grno_label, branch_label, year_label, image_label;
	JTextField name_text, grno_text;
	JComboBox<String> branch_select, year_select;
	JButton search_student, add_stud, reset_stud, insert_photo;
	FileDialog fd;
	File f;
	
	public StudentFind(){
		
		head = new Font("Times New Roman", Font.BOLD, 30);
		
		header = new JLabel("Student Details");
		header.setFont(head);
		
		name_label = new JLabel("Name of Student:");
		name_text = new JTextField(20);
		
		grno_label = new JLabel("Gr. No. of Student:");
		grno_text = new JTextField(20);
		
		branch_label = new JLabel("Select Branch:");
		String []branches = {"Computer","IT","Instrumentation","Mechanical"};
		branch_select = new JComboBox<>(branches);
		
		year_label = new JLabel("Select Year:");
		String []years = {"FY", "SY", "TY", "B.Tech"};
		year_select = new JComboBox<>(years);
		
		search_student = new JButton("Search Student");
		
		add_stud = new JButton("Update Student");
		
		reset_stud = new JButton("Reset");
		
		insert_photo = new JButton("Insert Photo");
		
		image_label = new JLabel();
		
		add(header);
		
		add(grno_label);
		add(grno_text);
		
		add(name_label);
		add(name_text);
		
		add(branch_label);
		add(branch_select);	
		
		add(year_label);
		add(year_select);
		
		add(search_student);
		search_student.addActionListener(this);
		
		add(add_stud);
		add_stud.addActionListener(this);
		
		add(reset_stud);
		reset_stud.addActionListener(this);
		
		add(insert_photo);
		insert_photo.addActionListener(this);
		
		add(image_label);
		
		setLayout(new FlowLayout());
		setTitle("Student Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(270,600);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		Connection conn = null;
		
		if(e.getSource() == search_student){
			String sgrnos = grno_text.getText();
			int igrnoi = Integer.parseInt(sgrnos);
			System.out.println(igrnoi);
			
			try{
				PreparedStatement pst7 = conn.prepareStatement("SELECT * FROM student WHERE gr_no = ?");
				pst7.setInt(1, igrnoi);
				
				ResultSet st7 = pst7.executeQuery();
				
				System.out.println(st7);
				
				if(st7.next()){
					
					grno_text.setText(st7.getString(1));
					name_text.setText(st7.getString(2));
					Blob b = st7.getBlob(5);
					ImageIcon i = new ImageIcon(b.getBytes(1,(int)b.length()));
					image_label.setIcon(i);
					
					JOptionPane.showMessageDialog(this,"Student Fetched!");
					
				} else {
					JOptionPane.showMessageDialog(this,"Student Not Found!");
				}
				
			} catch(Exception error4){
				JOptionPane.showMessageDialog(this,"Exception Here...");
				System.out.println(error4);
			}
		}
		
		if(e.getSource() == add_stud){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar", "root", "");
				String sname = name_text.getText();
				String sgrno = grno_text.getText();
				int grno = Integer.parseInt(sgrno);
				String sbranch = (String)branch_select.getSelectedItem();
				String syear = (String)year_select.getSelectedItem();
				FileInputStream sphoto = new FileInputStream(f);
				
				PreparedStatement st = conn.prepareStatement("INSERT INTO student(gr_no, sname, sbranch, syear, sphoto) VALUES (?,?,?,?,?)");
				st.setInt(1, grno);
				st.setString(2, sname);
				st.setString(3, sbranch);
				st.setString(4, syear);
				st.setBinaryStream(5, (InputStream)sphoto, (long)f.length());
				
				int x = st.executeUpdate();
				if(x > 0){
					JOptionPane.showMessageDialog(this,"Student Added!");
				}
				
			} catch (Exception q){
				JOptionPane.showMessageDialog(this,"Exception Here...");
			}
		}
		
		if(e.getSource() == reset_stud){
			//JOptionPane.showMessageDialog(this, "Reseting Student Data!");
			name_text.setText("");
			grno_text.setText("");
		}
		
		if(e.getSource() == insert_photo){
			fd = new FileDialog(this, "Open File");
			fd.setVisible(true);
			
			f = new File(fd.getDirectory()+fd.getFile());
			
			ImageIcon poster = new ImageIcon(fd.getDirectory()+fd.getFile());
			image_label.setIcon(poster);
		}
	}
	
	public static void main(String[] args){
		new StudentFind();
	}
}