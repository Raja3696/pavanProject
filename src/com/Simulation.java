package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileWriter;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.util.Random;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import org.jfree.ui.RefineryUtilities;
public class Simulation extends JFrame{
	Network node;
	JPanel p1,p2;
	int left,top;
	ArrayList<String> nd;
	Font f1;
	JLabel l1;
	JButton b1,b2,b3,b4,b5,b6;
	SendHealthData send[];
	ViewRecord vr;
	static int total;
	static int similar;
public Simulation(ArrayList<String> n){
	super("Simulation Screen");
	nd = n;
	
	f1 = new Font("Times New Roman",Font.BOLD,14);
	node = new Network(30);
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	p1.add(node,BorderLayout.CENTER);
	p1.setBackground(Color.black);
	getContentPane().add(p1,BorderLayout.CENTER);
	
	p2 = new JPanel();

	b1 = new JButton("Start Simulation");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			vr = new ViewRecord();
			vr.setVisible(true);
			vr.setSize(800,600);
	
			Runnable r = new Runnable(){
				public void run(){
					send = new SendHealthData[node.circles.size()];
					for(int i=0;i<node.circles.size();i++){
						SendHealthData shd = new SendHealthData(node,l1,i,vr);
						shd.setFlag(true);
						send[i] = shd;
					}
				}
			};
			new Thread(r).start();
		}
	});

	b2 = new JButton("Stop Simulation");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			for(int i=0;i<send.length;i++){
				send[i].setFlag(false);
			}
		}
	});

	b3 = new JButton("View Similar Sharing");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			similarShare();
		}
	});

	b4 = new JButton("View Doctor Share");
	b4.setFont(f1);
	p2.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Login login = new Login();
			login.setVisible(true);
			login.setSize(400,300);
			login.setLocationRelativeTo(null);
			login.setResizable(false);
		}
	});

	b5 = new JButton("User Reputation Graph");
	b5.setFont(f1);
	p2.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("User Reputation Graph");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	b6 = new JButton("Exit");
	b6.setFont(f1);
	p2.add(b6);
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	});

	p2.setPreferredSize(new Dimension(600,40));
	l1 = new JLabel();
	l1.setFont(f1);
	p2.add(l1);

	getContentPane().add(p2,BorderLayout.SOUTH);
	left = getInsets().left;
    top = getInsets().top;
	node.setPosition(left,top);
	SensorPlacement.randomNodes(nd,node);
	node.option=0;
	node.repaint();
}
public void similarShare(){
	String id = JOptionPane.showInputDialog(this,"Enter your patient id");
	if(id != null){
		try{
			Socket socket=new Socket("localhost",1111);
			ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			Object req[]={"similarid"};
			out.writeObject(req);
			out.flush();
			Object res[]=(Object[])in.readObject();
			String ids[] = (String[])res[0];
			String disease[] = (String[])res[1];
			String disease_name = "none";
			total = ids.length;
			for(int i=0;i<ids.length;i++){
				if(ids[i].equals(id)){
					disease_name = disease[i].toLowerCase().trim();
					break;
				}
			}
			ViewSimilar vs = new ViewSimilar();
			for(int i=0;i<ids.length;i++){
				if(!ids[i].equals(id)){
					double similarity = Score.getScore(disease_name,disease[i].toLowerCase().trim());
					String level = "none";
					if(similarity < 0.2)
						level = "Bad";
					else if(similarity >= 0.2 && similarity < 0.6)
						level = "Average";
					else if(similarity >= 0.6 && similarity <= 1.0)
						level = "Good";
					if(level.equals("Good")){
						String arr[] = getAddress(ids[i]);
						Object row[] = {id,ids[i],similarity,level,disease_name,disease[i],arr[0],arr[1],arr[2]};
						vs.dtm.addRow(row);
						similar = similar + 1;
					}else{
						Object row[] = {id,ids[i],similarity,level,disease_name,disease[i],"-","-","-"};
						vs.dtm.addRow(row);
					}
				}
			}
			vs.setVisible(true);
			vs.setSize(800,400);
		}catch(Exception e){
			e.printStackTrace();
		}
	}else{
		JOptionPane.showInputDialog(this,"Patient id must be enter");
	}
}
public String[] getAddress(String pid)throws Exception{
	Socket socket=new Socket("localhost",1111);
	ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
	ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
	Object req[]={"getaddress",pid};
	out.writeObject(req);
	out.flush();
	Object res[]=(Object[])in.readObject();
	String arr[] = (String[])res[0];
	return arr;
}
}