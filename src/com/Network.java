package com;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;
public class Network extends JComponent{
	String col="empty";
	public int option=0;
	public ArrayList<Circle> circles = new ArrayList<Circle>();
	float dash1[] = {10.0f};
	BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash1, 0.0f);
	BasicStroke rect=new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1f,new float[] {2f},0f);
	int left,top;
	int size;
	Circle sender;
	String choosen;
public void setSender(Circle sender,String choosen){
	this.sender = sender;
	this.choosen = choosen;
}
public void setPosition(int l,int r){
	left = l;
	top = r;
}
public Network(int size) {
	super.setBackground(Color.black);
	this.size = size;
    this.setBackground(Color.black);
}
public ArrayList<Circle> getList(){
	return circles;
}
public void removeAll(){
	option=0;
	circles.clear();
	col="empty";
	repaint();
}
public void paintComponent(Graphics g1){
	super.paintComponent(g1);
	GradientPaint gradient = new GradientPaint(0, 0, Color.green, 175, 175, Color.yellow,true); 
	Graphics2D g = (Graphics2D)g1;
	g.setPaint(gradient);
	Rectangle2D rectangle = new Rectangle2D.Double(200,10,100,50);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Cloudlet1",220,30);

	rectangle = new Rectangle2D.Double(400,10,100,50);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Cloudlet2",420,30);

	rectangle = new Rectangle2D.Double(600,10,100,50);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Cloudlet3",620,30);
	if(option == 0){
		for(int i=0;i<circles.size();i++){
			Circle circle = circles.get(i);
			if(circle.getNode() != null){
				circle.draw(g,"fill");
				g.drawString(circle.getNode(),circle.x+10,circle.y+50);
			}
		}
		g.setPaint(gradient);
	}
	if(option == 1){
		for(int i=0;i<circles.size();i++){
			Circle circle = circles.get(i);
			if(circle.getNode() != null){
				circle.draw(g,"fill");
				g.drawString(circle.getNode(),circle.x+10,circle.y+50);
			}
		}
		if(choosen.equals("first"))
			g.drawLine(sender.getX()+10,sender.getY()+10,240,50);
		if(choosen.equals("second"))
			g.drawLine(sender.getX()+10,sender.getY()+10,440,50);
		if(choosen.equals("third"))
			g.drawLine(sender.getX()+10,sender.getY()+10,640,50);
		g.setPaint(gradient);
	}
}
}