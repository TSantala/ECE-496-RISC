package gui;

import gameElements.Territory;

import java.awt.*;

public class MapTerritory {
	private Point myCenter;
	private int myRadius;
	private Territory myTerritory;
	private Color myColor;

	public MapTerritory(int x, int y, int radius, Territory t) {
		myCenter = new Point(x,y);
		myRadius = radius;
		myTerritory = t;
		myColor = Color.red;
	}
	
	public Point getCenter(){
		return myCenter;
	}
	
	public int getRadius(){
		return myRadius;
	}
	
	public Territory getTerritory(){
		return myTerritory;
	}
	
	public boolean isWithin(Point p){
		System.out.println(myCenter.x + " " + myCenter.y);
		if ( Math.pow(Math.pow((p.x - myCenter.x),2) + Math.pow((p.y - myCenter.y),2),.5) < myRadius){
			return true;
		}
		return false;
	}
	
	public Color getColor(){
		return myColor;
	}
	
	public void restoreColor(boolean leftClick){
		if(myColor == Color.green && !leftClick || myColor == Color.blue && leftClick) return;
		myColor = Color.red;
	}
	
	public void setColorLeftClick(){
		myColor = Color.green;
	}
	
	public void setColorRightClick(){
		myColor = Color.blue;
	}

}
