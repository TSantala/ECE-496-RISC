package gui;
/*
 * Very similar to Attack button...refactor soon
 */
import gameElements.MoveCommand;
import gameElements.Territory;
import gameElements.Unit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AttackButton extends JButton 
{
	public AttackButton(final GameGUI gui)
	{
	    super("Order Attack");
	    this.addActionListener(new ActionListener()
	    {
	        public void actionPerformed (ActionEvent arg0) 
	        {
            	if(gui.getLeftClick()==null) return;

            	//Create and set up the window.
            	JFrame frame = new JFrame("Attack");
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            	//Create and set up the content pane.
            	JComponent newContentPane = new AttackDialog(gui.getLeftClick().getUnits(),gui,frame,"Attack!");
            	newContentPane.setOpaque(true); //content panes must be opaque
            	frame.setContentPane(newContentPane);

            	//Display the window.
            	frame.pack();
            	frame.setVisible(true);
		}
	    });
	}

}
