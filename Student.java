import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Student extends JFrame implements ActionListener{
	Font head;
	JLabel header, name_label, grno_label, branch_label, year_label, image_label;
	JTextField name_text, grno_text;
	JComboBox<String> branch_select, year_select;
	JButton add_stud, reset_stud, insert_photo;
	FileDialog fd;
	File f;
	
	public Student(){
		
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
		
		add_stud = new JButton("Add Student");
		
		reset_stud = new JButton("Reset");
		
		insert_photo = new JButton("Insert Photo");
		
		image_label = new JLabel();
		
		add(header);
		
		add(name_label);
		add(name_text);
		
		add(grno_label);
		add(grno_text);
		
		add(branch_label);
		add(branch_select);	
		
		add(year_label);
		add(year_select);
		
		add(add_stud);
		add_stud.addActionListener(this);
		
		add(reset_stud);
		reset_stud.addActionListener(this);
		
		add(insert_photo);
		insert_photo.addActionListener(this);
		
		add(image_label);
		
		setLayout(new FlowLayout());
		setTitle("Student Details");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(270,600);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		Connection conn = null;
		
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
		new Student();
	}
}