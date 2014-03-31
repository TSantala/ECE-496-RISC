package gui;

import gameElements.Unit;

import java.util.Collection;

import javax.swing.JFrame;

public class UnitUpgradeDialog extends UnitListComponent{

	private static final long serialVersionUID = 1L;

	public UnitUpgradeDialog(Collection<Unit> units, GameGUI gui,
			JFrame container, String buttonLabel) {
		super(units, gui, container, buttonLabel);
	}

	@Override
	protected void sendUnits(Collection<Unit> u) {
		myGUI.addUnitUpgrade(u);
	}

}
