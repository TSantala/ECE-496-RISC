package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommitButton extends JButton{
	public CommitButton(final GameGUI gui){
		super("Commit");
		this.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0) {
				gui.sendCommandList();
			}
		});
	}
}
