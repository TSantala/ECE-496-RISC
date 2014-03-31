package gui;

import gameElements.Unit;

import java.util.Collection;

import javax.swing.JFrame;

public class SpyDialog extends UnitListComponent{

	private static final long serialVersionUID = 1L;

	public SpyDialog(Collection<Unit> units, GameGUI gui,
			JFrame container, String buttonLabel) {
		super(units, gui, container, buttonLabel);
	}

	@Override
	protected void sendUnits(Collection<Unit> u) {
		myGUI.addSpyCommand(u);
	}

}
