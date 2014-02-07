package gui;

import gameElements.MoveCommand;
import gameElements.Territory;
import gameElements.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

public class MoveButton extends JButton {
	
	public MoveButton(final GameGUI gui, final Territory from, final Territory to, final List<Unit> units){
		super("Order Move");
		this.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0) {
				gui.addCommand(new MoveCommand(gui.getPlayer(),from,to,units));
			}
		});
	}

}
