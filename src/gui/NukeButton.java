package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NukeButton extends JButton{
	public NukeButton(final GameGUI gui){
		super("Launch Nuke");
		this.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0) {
				gui.addNukeCommand();
			}
		});
	}
}
