package gui;

import gameElements.Command;
import gameElements.CommandList;
import gameElements.GameState;
import gameElements.Player;
import gameElements.Territory;
import gameElements.Unit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import server.ObjectClient;
import server.ServerConstants;
import server.TextMessage;

/*
 * This is the class that is called when the game begins- has to be passed number of players 
 */
public class GameGUI extends JFrame implements ServerConstants {

	private JTextField input;
	private JTextArea output;
	private JTextArea territoryInfo;
	private JTextArea commandInfo;
	private JScrollPane scrollingOutput;
	private JPanel mainPane;
	private ObjectClient myClient;
	private JFrame myFrame;

	private JPanel dummyPanel;
	private GameGraphic myGameGraphic;
	private GameState myGame;
	private Player myPlayer;

	private Territory leftClick;		// maybe hue territory color with blue?
	private Territory rightClick;		// maybe hue territory color with red?
	private List<Unit> selectedUnits = new ArrayList<Unit>();

	private JButton myCommitButton = new CommitButton(this);

	private CommandList myCommandList = new CommandList();

	public GameGUI(ObjectClient client){
		myClient = client;
		myGame = myClient.getGameState();
		System.out.println("2");
	}

	public void updateGameState(GameState gs){
		if (myGame == null){
			myClient.printMessage("STARTING THE GAME!");
			this.beginGame(gs);
		}
		else {
			mainPane.remove(myGameGraphic);
			//System.out.println(gs.getPlayer("timo").getUnits().size());
			myGameGraphic = new GameGraphic(this, gs);
			myGameGraphic.revalidate();
			myGameGraphic.repaint();
			mainPane.add(myGameGraphic,BorderLayout.CENTER);
			myCommitButton.setEnabled(true);
			mainPane.revalidate();
			mainPane.repaint();
		}
		myGame = gs;
	}

	public void run() {
		System.out.println("3");

		myFrame = new JFrame("RISC");
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

		System.out.println("4.5: ln 93 in GameGUI");

		//GameGraphic game = new GameGraphic(this,myGame);
		dummyPanel = new JPanel();
		mainPane.add(dummyPanel,BorderLayout.CENTER);

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
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BorderLayout());
		buttonPane.add(new MoveButton(this),BorderLayout.NORTH);
		buttonPane.add(new AttackButton(this),BorderLayout.SOUTH);

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
		commandInfo.setText(cmds);
	}

	public void sendCommandList(){
		// pop up an "Are you sure?" message maybe?	
		myClient.sendMessage(myCommandList);
		myCommandList.getCommands().clear();
		commandInfo.setText("");;
		myCommitButton.setEnabled(false);
	}

	public void updateTerritoryInfo(Territory t){
		territoryInfo.setText("Territory owner = "+t.getOwner().getName()+"  \n"+
				"  Number of Units = "+t.getUnits().size()+"  \n");
	}

	public Player getPlayer(){
		return myPlayer;
	}
	
	public void setPlayer(String player){
		myPlayer = myGame.getPlayer(player);
		myFrame.setTitle("RISC - " + player);
	}

	public void beginGame(GameState gs){
		mainPane.remove(dummyPanel);
		myGameGraphic = new GameGraphic(this, gs);
		mainPane.add(myGameGraphic,BorderLayout.CENTER);
		mainPane.revalidate();
		mainPane.repaint();
	}
	
	public GameGraphic getGameGraphic(){
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
}
