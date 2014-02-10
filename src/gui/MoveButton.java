package gui;

import gameElements.MoveCommand;
import gameElements.Territory;
import gameElements.Unit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MoveButton extends JButton 
{
	
	public MoveButton(final GameGUI gui)
	{
	    super("Order Move");
	    this.addActionListener(new ActionListener()
	    {
	        public void actionPerformed (ActionEvent arg0) 
	        {
	           
	        List<Unit> unitsSend = new ArrayList<Unit>();

		    String s = (String)JOptionPane.showInputDialog(gui,"Please put in how many units you want to move:\n",
			                                                   "JohnTimoVinceMovingSwag", 
			                                                   JOptionPane.PLAIN_MESSAGE,
			                                                   getIcon(),
			                                                   null,
			                                                   "1");
		    int numUnits = Integer.parseInt(s);
		    //error checking should be done by server...but can check here too
		    for (int i = 0; i < numUnits; i++)
		    {
		        if(i<gui.getLeftClick().getUnits().size())
		        	unitsSend.add(gui.getLeftClick().getUnits().get(i));
		    }
		    MoveCommand test = new MoveCommand(gui.getPlayer(),gui.getLeftClick(),gui.getRightClick(),unitsSend);
		    gui.addCommand(test);
		}
	    });
	}

}
