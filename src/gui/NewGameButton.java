package gui;

import gameElements.AttackCommand;
import gameElements.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class NewGameButton extends JButton{
	private LobbyPane myLobby;

	public NewGameButton(LobbyPane LP) {
		super("Start New Game");
		myLobby = LP;
		this.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) 
            {
                myLobby.NewGame();
            }
        });
	}
	

}
