package gui;

import gameElements.GameInfo;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import server.JoinRequest;
import server.NewGameRequest;
import server.ObjectClient;

public class LobbyPane extends JPanel{
	
	private Collection<GameInfo> myGames = new ArrayList<GameInfo>();	
	private ObjectClient myClient;
	private List gameList = new List(16, false);

	public LobbyPane(ObjectClient oc) {
		this.setSize(600,400);
		this.setLayout(new BorderLayout());
		myClient = oc;
		this.add(new Label("Abilities:"));
		this.add(gameList,BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(new JoinButton(this), BorderLayout.NORTH);
		buttonPanel.add(new NewGameButton(this), BorderLayout.SOUTH);
		this.add(buttonPanel, BorderLayout.EAST);
	}
	
	public void updateGames(Collection<GameInfo> games){
		myGames.clear();
		myGames.addAll(games);
		gameList.removeAll();
		for (GameInfo info : games){
			if (info.getOriginalPlayers().contains(myClient.getGUI().getPlayer())){
				gameList.add(info.toString() + " You are part of this game.");
			}
			else{
				gameList.add(info.toString());
			}
		}
		gameList.revalidate();
	}

	public void joinGame() {
		int game = gameList.getSelectedIndex();
		if (game != -1)
			myClient.sendMessage(new JoinRequest(game));
	}

	public void NewGame() {
		int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("You are beginning a new game.  How many players?" ,"2"));
		String gameName = JOptionPane.showInputDialog("What should your game be called?" ,"Default");
		myClient.sendMessage(new NewGameRequest(numPlayers, gameName));
	}

}
