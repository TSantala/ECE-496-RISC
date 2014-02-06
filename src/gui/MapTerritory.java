package gui;

import gameElements.Territory;

import java.awt.*;

public class MapTerritory {
	private Point myCenter;
	private int myRadius;
	private Territory myTerritory;

	public MapTerritory(int x, int y, int radius, Territory t) {
		myCenter = new Point(x,y);
		myRadius = radius;
		myTerritory = t;
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
		if ( Math.pow(Math.pow((p.x - myCenter.x),2) + Math.pow((p.y - myCenter.y),2),.5) > myRadius){
			return false;
		}
		return true;
	}

}
