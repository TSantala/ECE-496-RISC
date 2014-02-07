package gui;

import gameElements.GameState;
import gameElements.GameModel;
import gameElements.Territory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GameGraphic extends JPanel{
	private GameGUI myGUI;
	private List<MapTerritory> myTerritories = new ArrayList<MapTerritory>();
	private GameState myGame;
	private final int DEFAULT_RADIUS = 50;
	private final int DEFAULT_MAP_RADIUS = 100;
	private final Point MAP_CENTER = new Point(500,300);

	public GameGraphic(GameGUI gameGUI, GameState game) {
		myGUI = gameGUI;
		myGame = game;
		int i = 0;
		int n = myGame.getMap().getTerritories().size();
		for (Territory t : myGame.getMap().getTerritories()){
			myTerritories.add(new MapTerritory((int)(Math.sin(i*2*Math.PI/n)*DEFAULT_MAP_RADIUS + MAP_CENTER.x),
					(int)(Math.cos(i*2*Math.PI/n)*DEFAULT_MAP_RADIUS + MAP_CENTER.y), DEFAULT_RADIUS, t));
			i++;
		}
		this.setSize(600, 400);
		this.addMouseListener(new MapMouseListener(this));
	}

	@Override
	public void paint(Graphics g) {
		//paint map
		Graphics2D g2d = (Graphics2D) g;
		for (MapTerritory t : myTerritories){
			g2d.setColor(t.getColor());
			g2d.fillOval(t.getCenter().x, t.getCenter().y, t.getRadius(), t.getRadius());
		}

	}

	public void processClick(Point p, boolean leftClick){
		System.out.println("Mouse at: (" + p.x +", " + p.y + ").");
		p.x-=25; p.y-=25;
		for(MapTerritory mt : myTerritories){
			if(mt.isWithin(p)){
				if(leftClick){
					mt.setColorLeftClick();
					myGUI.updateTerritoryInfo(mt.getTerritory());
				}
				else{
					mt.setColorRightClick();
				}
			}
			else{
				mt.restoreColor(leftClick);
			}
		}
		this.repaint();		// this seems to be causing weird graphics bugs at random.
	}

}
