package gui;

import gameElements.GameState;
import gameElements.ServerGame;
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
	private final int DEFAULT_RADIUS = 30;
	private final int DEFAULT_MAP_RADIUS = 100;
	private final Point MAP_CENTER = new Point(300,200);

	public GameGraphic(GameGUI gameGUI, GameState game) {
		myGUI = gameGUI;
		myGame = game;
		int i = 0;
		int n = myGame.getMap().getTerritories().size();
		for (Territory t : myGame.getMap().getTerritories()){
			myTerritories.add(new MapTerritory((int)Math.sin(i*360/n)*DEFAULT_MAP_RADIUS + MAP_CENTER.x,
					(int)Math.cos(i*360/n)*DEFAULT_MAP_RADIUS + MAP_CENTER.y, DEFAULT_RADIUS, t));
			i++;
		}
		this.setSize(600, 400);
		this.addMouseListener(new MapMouseListener(this));
	}

	@Override
	public void paint(Graphics g) {
		//paint map
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		for (MapTerritory t : myTerritories){
			g2d.fillOval(t.getCenter().x - t.getRadius(), t.getCenter().y - t.getRadius(), 
					t.getCenter().x + t.getRadius(), t.getCenter().y + t.getRadius());
		}

	}

	public void processClick(Point p){

	}

}
