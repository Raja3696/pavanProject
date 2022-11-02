package com;
import java.util.Random;
import java.util.ArrayList;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
public class SendHealthData extends Thread{
	Network node;
	JLabel l1;
	boolean flag;
	int i;
	ViewRecord vr;
public void setFlag(boolean flag){
	this.flag = flag;
}
public boolean getFlag(){
	return flag;
}
public SendHealthData(Network node,JLabel l1,int i,ViewRecord vr){
	this.node = node;
	this.l1 = l1;
	this.i = i;
	this.vr = vr;
	start();
}
public double getDistance(int n1x,int n1y,int n2x,int n2y) {
	int dx = (n1x - n2x) * (n1x - n2x);
	int dy = (n1y - n2y) * (n1y - n2y);
	int total = dx + dy; 
	return Math.sqrt(total);
}
public void run(){
	String choosen = "none";
	try{
		while(getFlag()){
			Circle temp = node.circles.get(i);
			double cloudlet1 = getDistance(200,10,temp.getX(),temp.getY());
			double cloudlet2 = getDistance(400,10,temp.getX(),temp.getY());
			double cloudlet3 = getDistance(600,10,temp.getX(),temp.getY());
			if(cloudlet1 < cloudlet2 && cloudlet1 < cloudlet3)
				choosen = "first";
			else if(cloudlet2 < cloudlet3)
				choosen = "second";
			else
				choosen = "third";
			int bp = getBP();
			int rate = getHeartRate();
			BigInteger m1 = new BigInteger(Integer.toString(bp));
			BigInteger m2 = new BigInteger("1");

			BigInteger m3 = new BigInteger(Integer.toString(rate));

			BigInteger enc_bp = HomomorphicEncryption.Encryption(m1);
			BigInteger enbp = enc_bp.modPow(m2,HomomorphicEncryption.nsquare);

			BigInteger enc_hr = HomomorphicEncryption.Encryption(m3);
			BigInteger enhr = enc_hr.modPow(m2,HomomorphicEncryption.nsquare);
			
			Object row[] = {temp.getNode(),bp+"",enbp.toString(),rate+"",enhr.toString(),"-"};
			vr.dtm.addRow(row);
			
			Socket socket = new Socket("localhost",1111);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			Object req[] = {"request",temp.getNode(),enbp.toString().trim(),enhr.toString().trim()};
			out.writeObject(req);
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object res[] = (Object[])in.readObject();
			String msg = (String)res[0];

			l1.setText(temp.getNode()+" BP : "+bp+" Heart Rate : "+rate);
				
			for(int j=0;j<5;j++){
				node.setSender(temp,choosen);
				node.option=1;
				node.repaint();
				sleep(500);
				node.option=0;
				node.repaint();
				sleep(500);
			}
			node.option=0;
			node.repaint();
			sleep(1000);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public int getBodyTemperature(){
	Random rn = new Random();
	int range = 41 - 36 + 1;
	return rn.nextInt(range) + 36;
}
public int getBP(){
	Random rn = new Random();
	int range = 150 - 80 + 1;
	return rn.nextInt(range) + 80;
}
public int getHeartRate(){
	Random rn = new Random();
	int range = 100 - 60 + 1;
	return rn.nextInt(range) + 60;
}
}