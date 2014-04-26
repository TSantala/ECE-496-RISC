package gui;
/*
 * Very similar to Attack button...refactor soon
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AllianceButton extends JButton 
{
	public AllianceButton(final GameGUI gui)
	{
	    super("Request Alliance");
	    this.addActionListener(new ActionListener()
	    {
	        public void actionPerformed (ActionEvent arg0) 
	        {
            	//Create and set up the window.
            	JFrame frame = new JFrame("Request Alliance");
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            	//Create and set up the content pane.
            	JComponent newContentPane = new PlayerListComponent(gui.getOtherPlayers(),gui,frame,"Select ally!");
            	newContentPane.setOpaque(true); //content panes must be opaque
            	frame.setContentPane(newContentPane);

            	//Display the window.
            	frame.pack();
            	frame.setVisible(true);
		}
	    });
	}

}
