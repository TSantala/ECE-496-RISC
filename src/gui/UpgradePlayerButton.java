package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class UpgradePlayerButton extends JButton{

	private static final long serialVersionUID = 1L;
	private GameGUI myGUI;

	public UpgradePlayerButton(GameGUI gui) {
		super("Tech Up!");
		myGUI = gui;
		this.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) 
            {
                myGUI.addPlayerUpgrade();
            }
        });
	}
	

}
