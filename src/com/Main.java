package com;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JTextField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
public class Main extends JFrame{
	JPanel p1,p2;
	JLabel title;
	JButton b1;
	Font f1;
	JLabel l1;
	JTextField tf1;
	
public Main(){
	super("Patient Monitoring");
	
	f1 = new Font("Courier New",Font.BOLD,16);
	p1 = new JPanel();
	p1.setBackground(new Color(223,184,126,234));
	title = new JLabel("<HTML><BODY><CENTER>Cloudlet Medical Data Sharing Simulation</CENTER></BODY></HTML>".toUpperCase());
	title.setForeground(Color.blue);
	title.setFont(new Font("Times New ROMAN",Font.PLAIN,17));
	p1.add(title);

	f1 = new Font("Courier New",Font.BOLD,14);
	p2 = new JPanel(); 
	p2.setLayout(null);
	
	b1 = new JButton("Start Simulation");
	b1.setFont(f1);
	b1.setBounds(140,50,300,30);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			readInput();
		}
	});
	

	getContentPane().add(p2,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.NORTH);
}
public void readInput(){
	try{
		HomomorphicEncryption.KeyGeneration();
		HomomorphicDecryption.KeyGeneration();
		Socket socket=new Socket("localhost",1111);
		ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		Object req[]={"getpatients"};
		out.writeObject(req);
		out.flush();
		Object res[]=(Object[])in.readObject();
		String arr[] = (String[])res[0];
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<arr.length;i++){
			list.add(arr[i]);
		}
		Simulation sim = new Simulation(list);
		sim.setExtendedState(JFrame.MAXIMIZED_BOTH);
		sim.setVisible(true);
		setVisible(false);
	}catch(Exception nfe){
		nfe.printStackTrace();
	}
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	Main main = new Main();
	main.setVisible(true);
	main.setSize(600,250);
}
}
