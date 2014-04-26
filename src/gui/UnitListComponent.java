
package gui;

import gameElements.Unit;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public abstract class UnitListComponent extends JPanel
implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<Unit> list;
	private DefaultListModel<Unit> listModel;
	protected GameGUI myGUI;
	private JFrame myContainer;

	private String upgradeString;

	public UnitListComponent(Collection<Unit> units, GameGUI gui, JFrame container, String buttonLabel) {
		super(new BorderLayout());
		upgradeString = buttonLabel;
		myGUI = gui;
		myContainer = container;
		listModel = new DefaultListModel<Unit>();
		int index = 0;
		for(Unit u : units){
			listModel.add(index++, u);
		}

		//Create the list and put it in a scroll pane.
		list = new JList<Unit>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		JButton upgradeButton = new JButton(upgradeString);
		upgradeButton.setActionCommand(upgradeString);
		upgradeButton.addActionListener(new FireListener());

		add(listScrollPane, BorderLayout.CENTER);
		add(upgradeButton, BorderLayout.PAGE_END);
	}

	class FireListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			sendUnits(list.getSelectedValuesList());
			myContainer.dispose();
			
		}
	}
	
	
	protected abstract void sendUnits(Collection<Unit> u);

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
	}
}