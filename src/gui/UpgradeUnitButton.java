package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
public class UpgradeUnitButton extends JButton {

	private static final long serialVersionUID = 1L;

	public UpgradeUnitButton(final GameGUI gui) 
    {
        super("Order Upgrade");
        this.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) 
            {
            	if(gui.getLeftClick()==null) return;

            	//Create and set up the window.
            	JFrame frame = new JFrame("Upgrade Units");
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            	//Create and set up the content pane.
            	JComponent newContentPane = new UnitUpgradeDialog(gui.getLeftClickUnits(),gui,frame,"Upgrade Units!");
            	newContentPane.setOpaque(true); //content panes must be opaque
            	frame.setContentPane(newContentPane);

            	//Display the window.
            	frame.pack();
            	frame.setVisible(true);
            }
        });
    }

}
