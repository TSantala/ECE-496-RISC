package gui;

import gameElements.AddUnitCommand;
import gameElements.GameState;
import gameElements.Territory;
import gameElements.Unit;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class EnhancedGameGraphic extends JPanel{
	private GameGUI myGUI;
	private List<MapTerritory> myTerritories = new ArrayList<MapTerritory>();
	private GameState myGame;
	private boolean initialization = false;
	private int startUnits;
	private BufferedImage myMap;
	private List<BufferedImage> myImages = new ArrayList<BufferedImage>();
	private BufferedImage myLeft;
	private BufferedImage myRight;

	private Map<Point,String> lookupState = new HashMap<Point,String>();

	public EnhancedGameGraphic(GameGUI gameGUI, GameState game) {
		myGUI = gameGUI;
		myGame = game;
		myGUI.updatePlayerInfo();
		setLookupMap();
		this.setSize(1280, 900);
		BufferedImage in;
		try {
			in = ImageIO.read(new File("src/map/RISC.png"));
			myMap = new BufferedImage(in.getWidth(), in.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = myMap.createGraphics();
			g.drawImage(in, 0, 0, null);
			g.dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to read map file");
		}
		this.addMouseListener(new MapMouseListener(this));
	}

	public void setLookupMap(){
		lookupState.put(new Point(860, 680), "Alabama");
		lookupState.put(new Point(122, 82), "Alaska");
		lookupState.put(new Point(353, 636), "Arizona");
		lookupState.put(new Point(740, 624), "Arkansas");
		lookupState.put(new Point(188, 572), "California");
		lookupState.put(new Point(498, 515), "Colorado");
		lookupState.put(new Point(966, 788), "Florida");
		lookupState.put(new Point(930, 677), "Georgia");
		lookupState.put(new Point(73, 830), "Hawaii");
		lookupState.put(new Point(287, 372), "Idaho");
		lookupState.put(new Point(815, 480), "Illinois");
		lookupState.put(new Point(874, 486), "Indiana");
		lookupState.put(new Point(716, 425), "Iowa");
		lookupState.put(new Point(625, 527), "Kansas");
		lookupState.put(new Point(897, 565), "Kentucky");
		lookupState.put(new Point(742, 714), "Louisiana");
		lookupState.put(new Point(1160, 431), "MA");
		lookupState.put(new Point(1220, 331), "Maine");
		lookupState.put(new Point(1071, 508), "MD");
		lookupState.put(new Point(920, 319), "Michigan");
		lookupState.put(new Point(701, 304), "Minnesota");
		lookupState.put(new Point(802, 678), "Mississippi");
		lookupState.put(new Point(742, 526), "Missouri");
		lookupState.put(new Point(407, 274), "Montana");
		lookupState.put(new Point(594, 444), "Nebraska");
		lookupState.put(new Point(248, 503), "Nevada");
		lookupState.put(new Point(1169, 384), "New_Hampshire");
		lookupState.put(new Point(1108, 477), "New_Jersey");
		lookupState.put(new Point(462, 640), "New_Mexico");
		lookupState.put(new Point(1093, 400), "New_York");
		lookupState.put(new Point(1021, 606), "North_Carolina");
		lookupState.put(new Point(581, 271), "North_Dakota");
		lookupState.put(new Point(938, 478), "Ohio");
		lookupState.put(new Point(646, 605), "Oklahoma");
		lookupState.put(new Point(171, 380), "Oregon");
		lookupState.put(new Point(1046, 460), "Pennsylvania");
		lookupState.put(new Point(982, 648), "South_Carolina");
		lookupState.put(new Point(584, 360), "South_Dakota");
		lookupState.put(new Point(855, 599), "Tennessee");
		lookupState.put(new Point(612, 708), "Texas");
		lookupState.put(new Point(351, 505), "Utah");
		lookupState.put(new Point(1148, 374), "Vermont");
		lookupState.put(new Point(1036, 553), "Virginia");
		lookupState.put(new Point(178, 279), "Washington");
		lookupState.put(new Point(978, 525), "West_Virginia");
		lookupState.put(new Point(797, 348), "Wisconsin");
		lookupState.put(new Point(457, 403), "Wyoming");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(myMap, 0, 0, null);

		for (Territory t : myGame.getMap().getTerritories()){
			if (t.getOwner().getPlayer().equals(myGUI.getPlayer().getPlayer())){
				BufferedImage in;
				try {
					in = ImageIO.read(new File("src/map/"+t.getID()+"-O.png"));
					BufferedImage myImage = new BufferedImage(in.getWidth(), in.getHeight(),BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2d = myImage.createGraphics();
					g2d.drawImage(in, 0, 0, null);
					g2d.dispose();
					g.drawImage(myImage, 0, 0, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Failed to draw player's owned territories");
				}
			}
		}
		for (BufferedImage bi : myImages){
			g.drawImage(bi, 0, 0, null);
		}
		if (myLeft != null)
			g.drawImage(myLeft, 0, 0, null);
		if (myRight != null)
			g.drawImage(myRight, 0, 0, null);
	}

	public void processClick(Point p, boolean leftClick){
		//System.out.println("Mouse at: (" + p.x +", " + p.y + ").");
		Point myPoint = findClosestPoint(p);
		if(myGame.getMap().getTerritory(lookupState.get(myPoint))==null) return;
		System.out.println("I Clicked: "+ lookupState.get(myPoint) + "!");
		
		String state = "src/map/" + lookupState.get(myPoint);

		if (leftClick){
			myGUI.setLeftClick(myGame.getMap().getTerritory(lookupState.get(myPoint)));
			state = state + "-F.png";
		}
		else if(!leftClick){
			myGUI.setRightClick(myGame.getMap().getTerritory(lookupState.get(myPoint)));
			state = state + "-T.png";
		}

		if (leftClick && initialization && startUnits > 0){
			Territory clicked = myGame.getMap().getTerritory(lookupState.get(myPoint));
			if(!clicked.getOwner().getName().equals(myGUI.getPlayer().getName())) return;
			clicked.addUnit(new Unit(1, clicked.getOwner()));
			startUnits--;
		}
		else if (!leftClick && initialization){
			Territory clicked = myGame.getMap().getTerritory(lookupState.get(myPoint));
			if(!clicked.getOwner().getName().equals(myGUI.getPlayer().getName())) return;
			if (!clicked.getUnits().isEmpty()){
				myGame.getMap().getTerritory(lookupState.get(myPoint)).removeUnit(myGame.getMap().getTerritory(lookupState.get(myPoint)).getUnits().get(0));
				startUnits++;
			}
		}
		
		myGUI.updateTerritoryInfo();

		BufferedImage in;
		try {
			in = ImageIO.read(new File(state));
			BufferedImage myImage = new BufferedImage(in.getWidth(), in.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = myImage.createGraphics();
			g.drawImage(in, 0, 0, null);
			g.dispose();
			if (leftClick)
				myLeft = myImage;
			else
				myRight = myImage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to read map file");
		}
		this.revalidate();
		myGUI.revalidate();
		this.repaint();		// this seems to be causing weird graphics bugs at random.
		myGUI.repaint();
	}

	private Point findClosestPoint(Point myP) {

		Point current = new Point(860,680);
		double currentDist = Math.pow(Math.pow(myP.getX() - current.getX(),2) + Math.pow(myP.getY() - current.getY(), 2), .5);
		for(Point p : lookupState.keySet()){
			double tempDist = Math.pow(Math.pow(myP.getX() - p.getX(),2) + Math.pow(myP.getY() - p.getY(), 2), .5);
			if (tempDist < currentDist){
				currentDist = tempDist;
				current = p;
			}
		}
		return current;
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
		myGUI.sendCommandList();
	}
}
