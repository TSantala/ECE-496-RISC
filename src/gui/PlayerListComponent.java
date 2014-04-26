
package gui;

import gameElements.Player;
import gameElements.Unit;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public class PlayerListComponent extends JPanel
implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<Player> list;
	private DefaultListModel<Player> listModel;
	protected GameGUI myGUI;
	private JFrame myContainer;

	private String upgradeString;

	public PlayerListComponent(Collection<Player> players, GameGUI gui, JFrame container, String buttonLabel) {
		super(new BorderLayout());
		upgradeString = buttonLabel;
		myGUI = gui;
		myContainer = container;
		listModel = new DefaultListModel<Player>();
		int index = 0;
		for(Player u : players){
			listModel.add(index++, u);
		}

		//Create the list and put it in a scroll pane.
		list = new JList<Player>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

			sendPlayer(list.getSelectedValue());
			myContainer.dispose();
			
		}
	}
	
	
	private void sendPlayer(Player p){
		myGUI.sendAllianceRequest(p);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
	}
}