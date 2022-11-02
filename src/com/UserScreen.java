package com;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.math.BigInteger;
public class UserScreen extends JFrame{
	JPanel p1,p2;
	JLabel title,l1,l2;
	JButton b1,b2;
	Font f1;
	JTextField tf1;
	JComboBox c1;
public UserScreen(){
	super("Welcome Doctor");
	
	f1 = new Font("Courier New",Font.BOLD,16);
	p1 = new JPanel();
	p1.setBackground(new Color(204, 110, 155));
	title = new JLabel("<HTML><BODY><CENTER>View Share Data Screen</CENTER></BODY></HTML>".toUpperCase());
	title.setForeground(Color.white);
	title.setFont(new Font("Times New ROMAN",Font.PLAIN,17));
	p1.add(title);

	f1 = new Font("Courier New",Font.BOLD,14);
	p2 = new JPanel(); 
	p2.setLayout(null);

	l1 = new JLabel("Patient ID");
	l1.setFont(f1);
	l1.setBounds(10,30,150,30);
	p2.add(l1);
	tf1 = new JTextField();
	tf1.setFont(f1);
	tf1.setBounds(160,30,100,30);
	p2.add(tf1);

	l2 = new JLabel("Access All");
	l2.setFont(f1);
	l2.setBounds(10,80,150,30);
	p2.add(l2);
	c1 = new JComboBox();
	c1.setFont(f1);
	c1.setBounds(160,80,100,30);
	p2.add(c1);
	c1.addItem("All");
	
	b1 = new JButton("Get Records");
	b1.setFont(f1);
	b1.setBounds(10,130,140,30);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			getRecords();
		}
	});

	b2 = new JButton("Exit");
	b2.setFont(f1);
	b2.setBounds(170,130,140,30);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
		}
	});
	

	getContentPane().add(p2,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.NORTH);
}
public void getRecords(){
	String pid = tf1.getText();
	String type = c1.getSelectedItem().toString().trim();
	
	try{
		ViewShare vs = new ViewShare();
		Socket socket=new Socket("localhost",1111);
		ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		Object req[]={"Access",pid};
		out.writeObject(req);
		out.flush();
		Object res[]=(Object[])in.readObject();
		String arr[] = (String[])res[0];
		for(int i=0;i<arr.length;i++){
			String temp[] = arr[i].split(",");
			BigInteger bp = HomomorphicDecryption.Decryption(new BigInteger(temp[1]));
			BigInteger hr = HomomorphicDecryption.Decryption(new BigInteger(temp[2]));
			Object row[] = {temp[0],bp,hr,temp[3]};
			vs.dtm.addRow(row);
		}
		vs.setVisible(true);
		vs.setSize(800,600);
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
