import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

class Main extends JFrame implements ActionListener{
	
	Font thead;
	JLabel header;
	JMenu event, guest, student, registration, attendance;
	JMenuItem create_event, find_event, create_guest, find_guest, create_student, find_student, create_registration, find_registration, add_attendance;
	
	public Main(){
		
		thead = new Font("Times New Roman", Font.BOLD, 33);
	
		header = new JLabel("Welcome to Seminar Management System!");
		header.setFont(thead);
		
		JMenuBar mb = new JMenuBar();
		
		event = new JMenu("Event Details");
		
		guest = new JMenu("Guest Details");
		
		student = new JMenu("Student Details");
		
		registration = new JMenu("Registrations");
		
		attendance = new JMenu("Attendance");
		
		create_event = new JMenuItem("Add Event");
		
		find_event = new JMenuItem("Search Event");
		
		create_guest = new JMenuItem("Add Guest");
		
		find_guest = new JMenuItem("Search Guest");
		
		create_student = new JMenuItem("Add Student");
		
		find_student = new JMenuItem("Search Student");
		
		create_registration = new JMenuItem("Register");
		
		find_registration = new JMenuItem("Search Registrations");
		
		add_attendance = new JMenuItem("Mark Attendance");
		
		event.add(create_event);
		create_event.addActionListener(this);
		event.add(find_event);
		find_event.addActionListener(this);
		
		guest.add(create_guest);
		create_guest.addActionListener(this);
		guest.add(find_guest);
		find_guest.addActionListener(this);
		
		student.add(create_student);
		create_student.addActionListener(this);
		student.add(find_student);
		find_student.addActionListener(this);
		
		registration.add(create_registration);
		create_registration.addActionListener(this);
		registration.add(find_registration);
		find_registration.addActionListener(this);
		
		attendance.add(add_attendance);
		add_attendance.addActionListener(this);
		
		mb.add(event);
		mb.add(guest);
		mb.add(student);
		mb.add(registration);
		mb.add(attendance);
		
		setJMenuBar(mb);
		
		add(header);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		
		setSize(screenWidth,screenHeight);
		setLayout(new FlowLayout());
		setTitle("Seminar Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent onclick){
		if(onclick.getSource() == create_event){
			new Event();
		}
		
		if(onclick.getSource() == find_event){
			new EventFind();
		}
		
		if(onclick.getSource() == create_guest){
			new Guest();
		}
		
		if(onclick.getSource() == find_guest){
			new GuestFind();
		}
		
		if(onclick.getSource() == create_student){
			new Student();
		}
		
		if(onclick.getSource() == find_student){
			new StudentFind();
		}
		
		if(onclick.getSource() == create_registration){
			new Registration();
		}
		
		if(onclick.getSource() == add_attendance){
			new Attendance();
		}
		
		if(onclick.getSource() == find_registration){
			new RegistrationList();
		}
	}
	
	public static void main(String []args){
		new Main();
	}
}