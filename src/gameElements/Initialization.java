package gameElements;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

/*
 * This is the class that is called when the game begins- has to be passed number of players 
 */
public class Initialization implements Runnable
{
	private String userInput;
	@Override
	public void run() {
		JFrame f = new JFrame("Initialization phase");
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		Container pane = f.getContentPane();
		
		final JTextField output = new JTextField(40);
		output.setText("INITIALIZING RIGHT NOW");
		
		final JTextField input = new JTextField(40);
		
		JButton b1 = new JButton("Add stuff here");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userInput = input.getText();
				output.setText(userInput);
			}
		});
		
//		String s = (String)JOptionPane.showInputDialog(
//                f,
//                "Please enter # of players:\n"
//                ,
//                JOptionPane.PLAIN_MESSAGE,
//                icon,
//                null,
//                "ham");
//
//		//If a string was returned, say so.
//		if ((s != null) && (s.length() > 0)) {
//			setLabel("Green eggs and... " + s + "!");
//			return;
//		}
		
		pane.add(b1,BorderLayout.PAGE_START);
		pane.add(input,BorderLayout.PAGE_END);
		pane.add(output, BorderLayout.CENTER);
	
		f.pack();
		f.setVisible(true);
		
	}
//	public Initialization() //just trying to get something down...
//	{
//		int numPlayers = 2;
//		int numStartingUnits = 3;
//		int numTerritories = 2;
//		GameMap map = new GameMap();
//		List<Territory> tlist = new ArrayList<Territory>();
//		Group g1 = new Group();
//		Group g2 = new Group();
//		for (int i = 0; i < numPlayers*numTerritories; i++) //want groups to be evenly distributed
//		{
//			Territory temp = new Territory();
//			tlist.add(temp);
//			if (i==0 || i == 2)
//				g1.addTerritory(temp);
//		}
//		
//	}

}
