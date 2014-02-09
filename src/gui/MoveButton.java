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
	
	public MoveButton(final GameGUI gui, final Territory from, final Territory to, final List<Unit> units)
	{
	    super("Order Move");
	    this.addActionListener(new ActionListener()
	    {
	        public void actionPerformed (ActionEvent arg0) 
	        {
	            List<Unit> unitsSend = new ArrayList<Unit>();
	            List<Unit> updatedUnits = gui.getPlayer().getUnits(); 
		    String s = (String)JOptionPane.showInputDialog( gui,"Please put in how many units you want to move:\n",
			                                                   "JohnTimoVinceMovingSwag", 
			                                                   JOptionPane.PLAIN_MESSAGE,
			                                                   getIcon(),
			                                                   null,
			                                                   "1");
		    int numUnits = Integer.parseInt(s);
		    //error checking should be done by server...but can check here too
		    for (int i = 0; i < updatedUnits.size(); i++)
		    {
		        if(numUnits > 0)
		        {
		            unitsSend.add(updatedUnits.get(i));
		            numUnits--;
		        }
		    }
		    gui.addCommand(new MoveCommand(gui.getPlayer(),gui.getLeftClick(),gui.getRightClick(),unitsSend));
		}
	    });
	}

}
