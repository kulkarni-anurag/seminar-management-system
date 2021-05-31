import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class StudentFind extends JFrame implements ActionListener{
	Font head,comfont;
	JLabel header, name_label, grno_label, branch_label, year_label, image_label;
	JTextField name_text, grno_text;
	JComboBox<String> branch_select, year_select;
	JButton search_student, update_stud, reset_stud, insert_photo;
	FileDialog fd;
	File f;
	Connection conn;
	
	public StudentFind(){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/seminar", "root", "");
		} catch (Exception error5){
			System.out.println(error5);
		}
		
		head = new Font("Times New Roman", Font.BOLD, 30);
		comfont = new Font("Times New Roman", Font.BOLD, 18);		
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
		
		update_stud = new JButton("Update Student");
		
		reset_stud = new JButton("Reset");
		
		insert_photo = new JButton("Insert Photo");
		
		image_label = new JLabel();
		header.setBounds(220,8,300,100);   
		name_label.setFont(comfont);
		name_text.setFont(comfont);
		name_label.setBounds(10,80,300,50);
		name_text.setBounds(10,120,300,40);
		grno_label.setFont(comfont);
		grno_text.setFont(comfont);		
		grno_label.setBounds(10,170,300,40);
		grno_text.setBounds(10,210,300,40);
		branch_label.setFont(comfont);
		branch_select.setFont(comfont);
		year_label.setFont(comfont);
		year_select.setFont(comfont);
		branch_label.setBounds(10,260,300,40);
		branch_select.setBounds(10,300,300,40);
		year_label.setBounds(10,360,300,40);
		year_select.setBounds(10,400,300,40);
		
		search_student.setBounds(10,560,170,40);
		reset_stud.setBounds(190,560,150,40);				
		insert_photo.setBounds(350,560,150,40);
		update_stud.setBounds(510,560,170,40);
		search_student.setFont(comfont);
		reset_stud.setFont(comfont);
		update_stud.setFont(comfont);
		insert_photo.setFont(comfont);
		image_label.setBounds(400,140,250,250);	
		
		
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
		
		add(update_stud);
		update_stud.addActionListener(this);
		
		add(reset_stud);
		reset_stud.addActionListener(this);
		
		add(insert_photo);
		insert_photo.addActionListener(this);
		
		add(image_label);
		
		setLayout(null);
		setTitle("Student Details");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,700);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == search_student){
			String sgrnos = grno_text.getText();
			//System.out.println(sgrnos);
			int igrnoi = Integer.parseInt(sgrnos);
			//System.out.println(igrnoi);
			
			try{
				PreparedStatement pst7 = conn.prepareStatement("SELECT * FROM student WHERE gr_no = ?");
				pst7.setInt(1, igrnoi);
				
				ResultSet st7 = pst7.executeQuery();
				//System.out.println("query ok");
				if(st7.next()){
					
					grno_text.setText(st7.getString(1));
					name_text.setText(st7.getString(2));
					String sbranchs = st7.getString(3);
					for(int m=0; m < branch_select.getItemCount(); m++){
						if(sbranchs.equals(branch_select.getItemAt(m))){
							branch_select.setSelectedIndex(m);
							break;
						}
					}
					String syears = st7.getString(4);
					for(int n=0; n < year_select.getItemCount(); n++){
						if(syears.equals(year_select.getItemAt(n))){
							year_select.setSelectedIndex(n);
							break;
						}
					}
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
		
		if(e.getSource() == update_stud){
			try{
				String sname = name_text.getText();
				String sgrno = grno_text.getText();
				int grno = Integer.parseInt(sgrno);
				String sbranch = (String)branch_select.getSelectedItem();
				String syear = (String)year_select.getSelectedItem();
				FileInputStream sphoto = new FileInputStream(f);
				
				PreparedStatement st = conn.prepareStatement("UPDATE student SET sname = ?, sbranch = ?, syear =? , sphoto = ? WHERE gr_no = ?");
				st.setString(1, sname);
				st.setString(2, sbranch);
				st.setString(3, syear);
				st.setBinaryStream(4, (InputStream)sphoto, (long)f.length());
				st.setInt(5, grno);
				
				int x = st.executeUpdate();
				if(x > 0){
					JOptionPane.showMessageDialog(this,"Student Updated!");
				}
				
			} catch (Exception q){
				JOptionPane.showMessageDialog(this,"Exception Here...");
			}
		}
		
		if(e.getSource() == reset_stud){
			//JOptionPane.showMessageDialog(this, "Reseting Student Data!");
			name_text.setText("");
			grno_text.setText("");
			image_label.setText("");
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