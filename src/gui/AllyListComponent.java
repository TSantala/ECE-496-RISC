package gui;

import gameElements.Player;

import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class AllyListComponent extends PlayerListComponent {

	public AllyListComponent(Collection<Player> players, GameGUI gui,
			JFrame container, String buttonLabel) {
		super(players, gui, container, buttonLabel);
	}
	
	@Override
	public void sendPlayer(Player p){
		myGUI.breakAlliance(p);
	}

}
