package gui;

import gameElements.AllianceRequest;
import gameElements.AttackCommand;
import gameElements.Command;
import gameElements.CommandList;
import gameElements.DiplomacyCommand;
import gameElements.GameConstants;
import gameElements.GameInfo;
import gameElements.GameState;
import gameElements.InterceptorCommand;
import gameElements.MoveCommand;
import gameElements.NukeCommand;
import gameElements.Player;
import gameElements.SpyCommand;
import gameElements.Territory;
import gameElements.Unit;
import gameElements.UpgradePlayerCommand;
import gameElements.UpgradeUnitCommand;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import server.LeaveRequest;
import server.ObjectClient;
import server.ServerConstants;
import server.ServerPlayer;
import server.TextMessage;

/*
 * This is the class that is called when the game begins- has to be passed number of players 
 */
public class GameGUI extends JFrame implements ServerConstants, GameConstants {

	private JTextField input;
	private JTextArea output;
	private JTextArea territoryInfo;
	private JTextArea commandInfo;
	private JTextArea playerInfo;
	private JScrollPane scrollingOutput;
	private JPanel mainPane;
	private ObjectClient myClient;
	private JFrame myFrame;

	private LobbyPane lobbyPane;
	private EnhancedGameGraphic myGameGraphic;
	private JScrollPane scrollingGameGraphic;
	private GameState myGame;
	private Player myPlayer;

	private Territory leftClick;		// maybe hue territory color with blue?
	private Territory rightClick;		// maybe hue territory color with red?
	//private List<Unit> selectedUnits = new ArrayList<Unit>();

	private JButton myCommitButton = new CommitButton(this);
	private JButton myPlayerButton = new UpgradePlayerButton(this);
	private JButton myAttackButton = new AttackButton(this);
	private JButton myMoveButton = new MoveButton(this);
	private JButton myUpgradeButton = new UpgradeUnitButton(this);
	private JButton mySpyButton = new SpyButton(this);
	private JButton myLeaveButton = new LeaveButton(this);
	private JButton myNukeButton = new NukeButton(this);
	private JButton myInterceptorButton = new InterceptorButton(this);
	private JButton myAllianceButton = new AllianceButton(this);
	private JButton myBreakButton = new BreakAllianceButton(this);

	private CommandList myCommandList = new CommandList();

	private boolean isInitializing = true;

	private ImageBase myImages;
	
	private Clip clip;

	public GameGUI(ObjectClient client){
		myClient = client;
		myGame = myClient.getGameState();
		myImages = new ImageBase();
	}

	public void updateGameState(GameState gs){
		myCommandList = new CommandList();
		if (myGame == null){
			myClient.printMessage("STARTING THE GAME!");
			this.beginGame(gs);
		}
		else {
			mainPane.remove(scrollingGameGraphic);
			myGameGraphic = new EnhancedGameGraphic(this, gs);
			myGameGraphic.revalidate();
			myGameGraphic.repaint();
			scrollingGameGraphic = new JScrollPane(myGameGraphic);
			mainPane.add(scrollingGameGraphic,BorderLayout.CENTER);
			myCommitButton.setEnabled(true);
			myPlayerButton.setEnabled(true);
			mainPane.revalidate();
			mainPane.repaint();
		}
		leftClick=null;
		rightClick=null;
		this.checkAvailableButtons();
		myGame = gs;
		this.updatePlayerInfo();
	}

	public void run() {
		System.out.println("3");

		myFrame = new JFrame("RISC");
		//myFrame.setSize(1600, 1080);
		myFrame.setSize(800, 600);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLayout(new BorderLayout());
		Container pane = myFrame.getContentPane();

		System.out.println("4");

		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BorderLayout());

		JPanel textPane = new JPanel();
		textPane.setLayout(new BorderLayout());

		output = new JTextArea(5, 40);
		output.setEditable(false);
		scrollingOutput = new JScrollPane(output);
		textPane.add(scrollingOutput,BorderLayout.CENTER);

		input = new JTextField(40);
		input.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				myClient.sendMessage(new TextMessage(input.getText()));
				input.setText("");
			}			
		});
		textPane.add(input,BorderLayout.SOUTH);
		bottomPane.add(textPane,BorderLayout.CENTER);
		bottomPane.add(myCommitButton,BorderLayout.EAST);

		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());

		//GameGraphic game = new GameGraphic(this,myGame);
		lobbyPane = new LobbyPane(myClient);
		mainPane.add(lobbyPane,BorderLayout.CENTER);

		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BorderLayout());
		territoryInfo = new JTextArea(7, 7);
		territoryInfo.setEditable(false);
		rightPane.add(territoryInfo,BorderLayout.CENTER);

		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BorderLayout());
		commandInfo = new JTextArea(7,7);
		commandInfo.setEditable(false);
		leftPane.add(commandInfo, BorderLayout.CENTER);
		playerInfo = new JTextArea(7,7);
		playerInfo.setEditable(false);
		leftPane.add(playerInfo, BorderLayout.NORTH);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.add(myMoveButton);
		buttonPane.add(myAttackButton);
		buttonPane.add(mySpyButton);
		buttonPane.add(myUpgradeButton);
		buttonPane.add(myNukeButton);
		buttonPane.add(myInterceptorButton);
		buttonPane.add(myAllianceButton);
		buttonPane.add(myBreakButton);
		buttonPane.add(myPlayerButton);
		buttonPane.add(myLeaveButton);

		rightPane.add(buttonPane,BorderLayout.SOUTH);

		mainPane.add(rightPane,BorderLayout.EAST);
		mainPane.add(leftPane, BorderLayout.WEST);

		pane.add(bottomPane,BorderLayout.SOUTH);
		pane.add(mainPane,BorderLayout.CENTER);

		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		myFrame.setMaximizedBounds(e.getMaximumWindowBounds());

		myFrame.setExtendedState( myFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		myFrame.setVisible(true);
		this.setButtons(false);
		this.setOtherButtons(false);
	}

	public void printMessage(String s){
		output.setText(output.getText() + "\n" + s);
		output.revalidate();
		output.setCaretPosition(output.getDocument().getLength());
	}

	public void addCommand(Command c){
		myCommandList.addCommand(c);

		String cmds = myCommandList.toString();
		commandInfo.setText("  COMMAND LIST INFORMATION\n"+cmds);
	}

	public void sendCommandList(){
		myClient.sendMessage(myCommandList);
		myCommandList.getCommands().clear();
		commandInfo.setText("");;
		myCommitButton.setEnabled(false);
	}

	public void updateTerritoryInfo(){
		if(leftClick==null)return;
		territoryInfo.setText("  TERRITORY "+leftClick.getID()+" INFORMATION\n" +
				"  Territory owner = "+leftClick.getOwner().getName()+", Level: "+leftClick.getOwner().getTechLevel()+"\n"+
				"  Number of Units = "+leftClick.getUnits().size()+"  \n"+
				leftClick.getUnitInfo()+
				"  Interceptors = "+leftClick.hasInterceptors()+"  \n"+
				"  Food Collection Rate = 10\n"+
				"  Tech Collection Rate = 10\n");
	}

	public void updatePlayerInfo(){
		if(myPlayer==null)return;
		if(myGame==null)return;
		Player p = myGame.getPlayer(myPlayer.getName());
		if(p==null)return;
		playerInfo.setText("  PLAYER INFORMATION\n"+
				"  Player: "+p.getName()+"\n"+
				"  Food = "+p.getFoodAmount()+"\n"+
				"  Technology = "+p.getTechAmount()+"\n"+
				"  Level: "+p.getTechLevel()+" = "+PLAYER_TECH_TREE.getUnitType(p.getTechLevel())+"\n"+
				"  Allies: "+p.getAlliesString());
	}


	public Player getPlayer(){
		return myPlayer;
	}

	public void setPlayer(ServerPlayer sp){
		myPlayer = new Player(sp);
		myFrame.setTitle("RISC - " + sp.getName());
	}

	public void beginGame(GameState gs){
		mainPane.remove(lobbyPane);
		myGameGraphic = new EnhancedGameGraphic(this, gs);
		scrollingGameGraphic = new JScrollPane(myGameGraphic);
		mainPane.add(scrollingGameGraphic,BorderLayout.CENTER);
		mainPane.revalidate();
		mainPane.repaint();
		this.setOtherButtons(true);
		
		try {
			File soundfile = new File("src/Age of Mythology Soundtrack Part 1.wav");
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundfile);
			clip.open(ais);
			clip.loop(Integer.MAX_VALUE);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void leaveGame() {
		territoryInfo.setText("");
		playerInfo.setText("");
		mainPane.remove(scrollingGameGraphic);
		mainPane.add(lobbyPane, BorderLayout.CENTER);
		mainPane.repaint();
		myGame = null;
		myClient.sendMessage(new LeaveRequest());
		this.setButtons(false);
		this.setOtherButtons(false);
		clip.close();
	}
	
	public EnhancedGameGraphic getGameGraphic(){
		return myGameGraphic;
	}

	public Territory getLeftClick(){
		return leftClick;
	}

	public void setLeftClick(Territory t){
		leftClick = t;
		this.checkAvailableButtons();
	}

	public Territory getRightClick(){
		return rightClick;
	}

	public void setRightClick(Territory t){
		rightClick = t;
		this.checkAvailableButtons();
	}

	public void updateGameInfo(Collection<GameInfo> myUpdate) {
		lobbyPane.updateGames(myUpdate);
	}


	public void addUnitUpgrade(Collection<Unit> u) {
		if(!validateCommand(u))
			return;
		System.out.println("GOT HERE!!! " + u.toString());
		List<Unit> toSend = new ArrayList<Unit>();
		toSend.addAll(u);
		this.addCommand(new UpgradeUnitCommand(toSend));		
	}

	public void addPlayerUpgrade(){
		this.addCommand(new UpgradePlayerCommand(myGame.getPlayer(myPlayer.getName())));
		myPlayerButton.setEnabled(false);
	}

	public void addMoveCommand(Collection<Unit> u){
		if(!validateCommand(u))
			return;
		List<Unit> toSend = new ArrayList<Unit>();
		toSend.addAll(u);
		this.addCommand(new MoveCommand(myGame.getPlayer(myPlayer.getName()),this.getLeftClick(),this.getRightClick(),toSend));
	}

	public void addAttackCommand(Collection<Unit> u){
		if(!validateCommand(u))
			return;
		List<Unit> toSend = new ArrayList<Unit>();
		toSend.addAll(u);
		this.addCommand(new AttackCommand(myGame.getPlayer(myPlayer.getName()),this.getLeftClick(),this.getRightClick(),toSend));
	}

	public void addSpyCommand(Collection<Unit> u){
		for (Unit unit : u){
			if (!unit.getOwner().getPlayer().equals(this.getPlayer().getPlayer())){
				System.out.println("tried to use a unit that does not belong to you!");
				return;
			}
		}
		List<Unit> toSend = new ArrayList<Unit>();
		toSend.addAll(u);
		this.addCommand(new SpyCommand(toSend));
	}

	public boolean validateCommand(Collection<Unit> u){
		for (Unit unit : u){
			if (!unit.getOwner().getPlayer().equals(this.getPlayer().getPlayer())){
				System.out.println("tried to use a unit that does not belong to you!");
				return false;
			}
		}
		return true;
	}

	public boolean isInit(){
		return isInitializing;
	}

	public void endInit(){
		isInitializing = false;
		myCommitButton.setEnabled(false);
		myGameGraphic.endInitialization();
	}
	
	public BufferedImage getImage(String s){
		return myImages.getImage(s);
	}

	public void checkAvailableButtons(){
		
		if(myPlayer!=null && myGame!=null){
			Player p = myGame.getPlayer(myPlayer.getName());
			if(p.getTechLevel()==6)
				myPlayerButton.setEnabled(false);
		}

		if(leftClick==null){
			this.setButtons(false);
			return;
		}
		this.setButtons(true);
		if(!leftClick.hasUnitHere(myPlayer)){
			myAttackButton.setEnabled(false);
			myMoveButton.setEnabled(false);
			myUpgradeButton.setEnabled(false);
			mySpyButton.setEnabled(false);
		}
		if (leftClick.getOwner() == null)
			System.out.println("YOU FOUND THE NULL!");
		if(!leftClick.getOwner().equals(myPlayer))
			myInterceptorButton.setEnabled(false);
		if(rightClick==null){
			myAttackButton.setEnabled(false);
			myMoveButton.setEnabled(false);
			myNukeButton.setEnabled(false);
		}
	}

	private void setButtons(boolean onOff) {
		myAttackButton.setEnabled(onOff);
		myMoveButton.setEnabled(onOff);
		myUpgradeButton.setEnabled(onOff);
		mySpyButton.setEnabled(onOff);
		myInterceptorButton.setEnabled(onOff);
		if(myPlayer!=null && myGame!=null){
			Player p = myGame.getPlayer(myPlayer.getName());
			if(p.isNukeReady())
				myNukeButton.setEnabled(onOff);
		}
		else
			myNukeButton.setEnabled(false);
	}

	private void setOtherButtons(boolean onOff){
		myPlayerButton.setEnabled(onOff);
		myLeaveButton.setEnabled(onOff);
		myAllianceButton.setEnabled(onOff);
		myBreakButton.setEnabled(onOff);
	}

	public Collection<Unit> getLeftClickUnits() {
		List<Unit> toReturn = new ArrayList<Unit>();
		for(Unit u : leftClick.getUnits()){
			if(u.getOwner().getName().equals(myPlayer.getName()))
				toReturn.add(u);
		}
		return toReturn;
	}

	public void addNukeCommand() {
		if(rightClick==null)return;
		this.addCommand(new NukeCommand(rightClick,myPlayer));
	}

	public void addInterceptorCommand() {
		if(leftClick==null)return;
		this.addCommand(new InterceptorCommand(leftClick,myPlayer));		
	}
	
	public void sendAllianceRequest(Player other){
		myClient.sendMessage(new AllianceRequest(myPlayer, other));
	}

	public void proposeRequest(Player from, Player to) {
		int reply = JOptionPane.showConfirmDialog(null, "Do you accept an alliance with "+from.getName()+"?",
				"ALLIANCE REQUESTED!",  JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			myClient.sendMessage(new TextMessage("/w "+from.getName()+" I welcome you as my ally!"));
		    this.addCommand(new DiplomacyCommand(from,to,true));
		}
		else {
			myClient.sendMessage(new TextMessage("/w "+from.getName()+" Hahaha! I will never fight alongside you!"));
		}
	}
	
	public Collection<Player> getOtherPlayers(){
		Collection<Player> toReturn = new ArrayList<Player>();
		Collection<Player> allies = this.getAllies();
		for(Player p : myGame.getPlayers()){
			if(!p.equals(myPlayer) && !allies.contains(p))
				toReturn.add(p);
		}
		return toReturn;
	}

	public Collection<Player> getAllies() {
		Collection<Player> toReturn = new ArrayList<Player>();
		for(Player p : myGame.getPlayers()){
			if(p.isAlly(myPlayer))
				toReturn.add(p);
		}
		return toReturn;
	}

	public void breakAlliance(Player p) {
		this.addCommand(new DiplomacyCommand(myPlayer,p,false));
	}

}
