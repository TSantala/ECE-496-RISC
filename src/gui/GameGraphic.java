package gui;

import gameElements.AddUnitCommand;
import gameElements.GameState;
import gameElements.GameModel;
import gameElements.Territory;
import gameElements.Unit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameGraphic extends JPanel{
	private GameGUI myGUI;
	private List<MapTerritory> myTerritories = new ArrayList<MapTerritory>();
	private GameState myGame;
	private final int DEFAULT_RADIUS = 50;
	private final int DEFAULT_MAP_RADIUS = 100;
	private final Point MAP_CENTER = new Point(300,200);
	private boolean initialization = false;
	private int startUnits;
	
	private BufferedImage map;

	public GameGraphic(GameGUI gameGUI, GameState game) {
		myGUI = gameGUI;
		myGame = game;
		myGUI.updatePlayerInfo();
//		try {
//			map = ImageIO.read(new File("../Images/Risk Map.jpg"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		int i = 0;
		int n = myGame.getMap().getTerritories().size();
		for (Territory t : myGame.getMap().getTerritories()){
			myTerritories.add(new MapTerritory((int)(Math.sin(i*2*Math.PI/n)*DEFAULT_MAP_RADIUS + MAP_CENTER.x),
					(int)(Math.cos(i*2*Math.PI/n)*DEFAULT_MAP_RADIUS + MAP_CENTER.y), DEFAULT_RADIUS, t));
			i++;
		}
		this.setSize(600, 400);
		//this.addMouseListener(new MapMouseListener(this));
	}

	@Override
	public void paintComponent(Graphics g) {
		//paint map
	    super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//g2d.drawImage(map, 1, 1, this);
		
		for (MapTerritory t : myTerritories){
			g2d.setColor(t.getColor());
			g2d.fillOval(t.getCenter().x - t.getRadius()/2, t.getCenter().y- t.getRadius()/2, t.getRadius(), t.getRadius());
			g2d.setColor(Color.black);
			g2d.drawString(""+t.getTerritory().getUnits().size(), t.getCenter().x+25, t.getCenter().y+25);
			g2d.drawString(t.getTerritory().getOwner().getName(), t.getCenter().x+25, t.getCenter().y-25);
		}
		
		for (MapTerritory from : myTerritories){
			for (MapTerritory to : myTerritories){
				if (from.getTerritory().getNeighbors().contains(to.getTerritory())){
					g2d.drawLine(from.getCenter().x, from.getCenter().y, to.getCenter().x, to.getCenter().y);
				}
			}
		}

	}

	public void processClick(Point p, boolean leftClick){
		//System.out.println("Mouse at: (" + p.x +", " + p.y + ").");
		for(MapTerritory mt : myTerritories){
			if(mt.isWithin(p) && !initialization){
				if(leftClick){
					mt.setColorLeftClick();
					myGUI.updateTerritoryInfo(mt.getTerritory());
					myGUI.setLeftClick(mt.getTerritory());
				}
				else{
					mt.setColorRightClick();
					myGUI.setRightClick(mt.getTerritory());
				}
			}
			else if (mt.isWithin(p) && initialization){
				// TO-DO: Fix unit IDs on initialization!
				if(leftClick && mt.getTerritory().getOwner().getPlayer().equals(myGUI.getPlayer().getPlayer())){
					mt.getTerritory().addUnit(new Unit(1,mt.getTerritory().getOwner()));
					startUnits--;
				}
				else if (!leftClick && mt.getTerritory().getOwner().getPlayer().equals(myGUI.getPlayer().getPlayer())){
					if (mt.getTerritory().getUnits().size() > 0){
						mt.getTerritory().removeUnit(mt.getTerritory().getUnits().get(0));
						startUnits++;
					}
				}
				if (startUnits == 0){
					this.endInitialization();
				}
			}
			else{
				mt.restoreColor(leftClick);
			}
		}
		this.revalidate();
	    myGUI.revalidate();
		this.repaint();		// this seems to be causing weird graphics bugs at random.
		myGUI.repaint();
	}

	public void assignUnits() {
		initialization = true;
		startUnits = myGame.startUnits();
	}

	public void endInitialization(){
		initialization = false;
		// TODO This is where we need to see how many units were assigned to which territory
		// and send this information to the server.
		for (Territory t : myGame.getMap().getTerritories()){
			if (t.getOwner().getPlayer().equals(myGUI.getPlayer().getPlayer())){
				myGUI.addCommand(new AddUnitCommand(t.getOwner(),t,t.getUnits()));
			}
		}
	}
}
