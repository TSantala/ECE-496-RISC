package gui;

import gameElements.AttackCommand;
import gameElements.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class LeaveButton extends JButton{
	private GameGUI myGUI;

	public LeaveButton(GameGUI gui) {
		super("Leave Game");
		myGUI = gui;
		this.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) 
            {
                myGUI.leaveGame();
            }
        });
	}
	

}
