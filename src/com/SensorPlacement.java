package com;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;
public class SensorPlacement{
	static int size=40;
	static Network g;
public static void randomNodes(ArrayList<String> nodedetails,Network nodes){
	g=nodes;
	int x = getXPosition(10,800);
	int y = getYPosition(200,600);
	if(nodedetails.size() == 5){
		for(int i=0;i<nodedetails.size();i++){
			String name = nodedetails.get(i);
			Circle c = new Circle(new Point(x, y),size);
			c.setNode(name);
			g.circles.add(c);
			x = getXPosition(10,800);
			y = getYPosition(200,600);
		}
	}else{
		for(int i=0;i<nodedetails.size();i++){
			String name = nodedetails.get(i);
			Circle c = new Circle(new Point(x, y),size);
			c.setNode(name);
			g.circles.add(c);
			x = getXPosition(10,800);
			y = getYPosition(200,600);
		}
	}
}
public static int getXPosition(int start,int end){
	Random rn = new Random();
	int range = end - start + 1;
	return rn.nextInt(range) + start;
}
public static int getYPosition(int start,int end){
	Random rn = new Random();
	int range = end - start + 1;
	return rn.nextInt(range) + start;
}
}