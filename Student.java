import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Student extends JFrame{
	Font head;
	JLabel header, name_label, grno_label, branch_label, year_label, event_label;
	JTextField name_text, grno_text;
	JComboBox<String> branch_select, year_select, event_select;
	JButton add_stud, reset_stud;
	
	public Student(){
		
		head = new Font("Times New Roman", Font.BOLD, 30);
		
		header = new JLabel("Student Details");
		header.setFont(head);
		name_label = new JLabel("Name of Student:");
		grno_label = new JLabel("Gr. No. of Student:");
		branch_label = new JLabel("Select Branch:");
		year_label = new JLabel("Select Year:");
		event_label = new JLabel("Select Event:");
		
		name_text = new JTextField(20);
		grno_text = new JTextField(20);
		
		String []branches = {"Computer","IT","Instrumentation","Chemical", "Mechanical", "Indus&Prod"};
		String []years = {"FY", "SY", "TY", "B.Tech"};
		String []events = {"Test1", "Test2", "Test3", "Test4"};
		
		branch_select = new JComboBox<>(branches);
		year_select = new JComboBox<>(years);
		event_select = new JComboBox<>(events);
		
		add_stud = new JButton("Add Student");
		reset_stud = new JButton("Reset");
		
		add(header);
		add(name_label);
		add(name_text);
		add(grno_label);
		add(grno_text);
		add(branch_label);
		add(branch_select);	
		add(year_label);
		add(year_select);
		add(event_label);
		add(event_select);
		add(add_stud);
		add(reset_stud);
		
		setLayout(new FlowLayout());
		setTitle("Student Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(270,350);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new Student();
	}
}