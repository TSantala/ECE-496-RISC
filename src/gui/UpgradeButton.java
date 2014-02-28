package gui;

import gameElements.AttackCommand;
import gameElements.Territory;
import gameElements.Unit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class UpgradeButton extends JButton {

    public UpgradeButton(final GameGUI gui) 
    {
        super("Order Upgrade");
        this.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) 
            {
//                List<Unit> unitsSend = new ArrayList<Unit>();
//
//                String s = (String)JOptionPane.showInputDialog( gui,"Please put in how many units you want to attack with:\n",
//                                                                       "UPGRADIN++", 
//                                                                       JOptionPane.PLAIN_MESSAGE,
//                                                                       getIcon(),
//                                                                       null,
//                                                                       "1");
//                
//                JList list = new JList(gui.getPlayer().getUnits());
//                s = ListDialog.showDialog(gui.getContentPane());
//               
//                int numUnits = Integer.parseInt(s);
//                //error checking should be done by server...but can check here too
//                    for (int i = 0; i < numUnits; i++)  
//                    {
//                        if(i<gui.getLeftClick().getUnits().size())
//                                unitsSend.add(gui.getLeftClick().getUnits().get(i));
//                    }
//                gui.addCommand(new AttackCommand(gui.getPlayer(),gui.getLeftClick(),gui.getRightClick(),unitsSend));
            }
        });
    }

}
