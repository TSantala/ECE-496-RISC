package gui;

import gameElements.AttackCommand;
import gameElements.Territory;
import gameElements.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

public class AttackButton extends JButton {
	
	public AttackButton(final GameGUI gui, final Territory from, final Territory to, final List<Unit> units){
		super("Order Attack");
		this.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0) {
				gui.addCommand(new AttackCommand(gui.getPlayer(),from,to,units));
			}
		});
	}

}
