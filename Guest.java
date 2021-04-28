import java.io.*;
import java.awt.*;
import javax.swing.*;

class Guest extends JFrame{
	
  Font thead;
  JLabel head, gname_label, ggender_label, gexpert_label, gcompany_label, gphoto_label, gevent_label;
  JTextField gname_text, gexpert_text, gcompany_text;
  JComboBox<String> event_combo;
  JRadioButton male, female;
  JButton add_guest, reset;
 
  public Guest(){
   
	thead = new Font("Times New Roman", Font.BOLD, 30);
	
    head = new JLabel("Guest's Details");
	head.setFont(thead);
	
    gname_label = new JLabel("Enter Guest's Name:");
    ggender_label = new JLabel("Enter Guest's Gender:");
    gexpert_label = new JLabel("Enter Guest's Expertise:");
    gcompany_label = new JLabel("Enter Guest's Company:");
    gphoto_label = new JLabel("Upload Guest's Photo:");
	gevent_label = new JLabel("Select Event:");
	
	String []events = {"Test1", "Test2", "Test3", "Test4"};
   
    male = new JRadioButton("Male");
    female = new JRadioButton("Female");
   
    gname_text = new JTextField(20);
    gexpert_text = new JTextField(20);
    gcompany_text = new JTextField(20);
	
	event_combo = new JComboBox<>(events);
   
    add_guest = new JButton("Add Guest");
    reset = new JButton("Reset Data");
   
    add(head);
   
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
   
    add(add_guest);
    add(reset);
	
	setVisible(true);
	setSize(300,350);
	setLayout(new FlowLayout());
    setTitle("Guest Details");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
 
  public static void main(String args[]){
    new Guest();
  }
}