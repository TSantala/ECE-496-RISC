package gui;

import gameElements.AttackCommand;
import gameElements.Command;
import gameElements.CommandList;
import gameElements.GameConstants;
import gameElements.GameInfo;
import gameElements.GameState;
import gameElements.MoveCommand;
import gameElements.Player;
import gameElements.SpyCommand;
import gameElements.Territory;
import gameElements.Unit;
import gameElements.UpgradePlayerCommand;
import gameElements.UpgradeUnitCommand;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private GameState myGame;
	private Player myPlayer;

	private Territory leftClick;		// maybe hue territory color with blue?
	private Territory rightClick;		// maybe hue territory color with red?
	//private List<Unit> selectedUnits = new ArrayList<Unit>();
	
	private JButton myCommitButton = new CommitButton(this);
	private JButton myPlayerButton = new UpgradePlayerButton(this);

	private CommandList myCommandList = new CommandList();
	
	private boolean isInitializing = true;

	public GameGUI(ObjectClient client){
		myClient = client;
		myGame = myClient.getGameState();
	}

	public void updateGameState(GameState gs){
		if (myGame == null){
			myClient.printMessage("STARTING THE GAME!");
			this.beginGame(gs);
		}
		else {
			mainPane.remove(myGameGraphic);
			myGameGraphic = new EnhancedGameGraphic(this, gs);
			myGameGraphic.revalidate();
			myGameGraphic.repaint();
			mainPane.add(myGameGraphic,BorderLayout.CENTER);
			myCommitButton.setEnabled(true);
			myPlayerButton.setEnabled(true);
			mainPane.revalidate();
			mainPane.repaint();
		}
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
		buttonPane.add(new MoveButton(this));
		buttonPane.add(new AttackButton(this));
		buttonPane.add(new SpyButton(this));
		buttonPane.add(new UpgradeUnitButton(this));
		buttonPane.add(myPlayerButton);
		buttonPane.add(new LeaveButton(this));

		rightPane.add(buttonPane,BorderLayout.SOUTH);

		mainPane.add(rightPane,BorderLayout.EAST);
		mainPane.add(leftPane, BorderLayout.WEST);
	
		System.out.println("6");
		pane.add(bottomPane,BorderLayout.SOUTH);
		pane.add(mainPane,BorderLayout.CENTER);

		myFrame.setVisible(true);
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

	public void updateTerritoryInfo(Territory t){
		territoryInfo.setText("  TERRITORY "+t.getID()+" INFORMATION\n" +
				"  Territory owner = "+t.getOwner().getName()+", Level: "+t.getOwner().getTechLevel()+"\n"+
				"  Number of Units = "+t.getUnits().size()+"  \n"+
				t.getUnitInfo()+
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
				"  Level: "+p.getTechLevel()+" = "+PLAYER_TECH_TREE.getUnitType(p.getTechLevel()));
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
		mainPane.add(myGameGraphic,BorderLayout.CENTER);
		mainPane.revalidate();
		mainPane.repaint();
	}
	
	public EnhancedGameGraphic getGameGraphic(){
		return myGameGraphic;
	}

	public Territory getLeftClick()
	{
	    return leftClick;
	}
	public void setLeftClick(Territory t)
	{
	    leftClick = t;
	}
	public Territory getRightClick()
	{
	    return rightClick;
	}
	public void setRightClick(Territory t)
	{
	    rightClick = t;
	}

	public void updateGameInfo(Collection<GameInfo> myUpdate) {
		lobbyPane.updateGames(myUpdate);
	}

	public void leaveGame() {
		mainPane.remove(myGameGraphic);
		mainPane.add(lobbyPane, BorderLayout.CENTER);
		mainPane.repaint();
		myGame = null;
		myClient.sendMessage(new LeaveRequest());
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
}
