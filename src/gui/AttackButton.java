package gui;

import gameElements.AttackCommand;
import gameElements.Territory;
import gameElements.Unit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AttackButton extends JButton {

    public AttackButton(final GameGUI gui, final Territory from, final Territory to, final List<Unit> units)
    {
        super("Order Attack");
        this.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) 
            {
                List<Unit> unitsSend = new ArrayList<Unit>();
                String s = (String)JOptionPane.showInputDialog( gui,"Please put in how many units you want to attack with:\n",
                                                                       "ATTACK SWAG", 
                                                                       JOptionPane.PLAIN_MESSAGE,
                                                                       getIcon(),
                                                                       null,
                                                                       "1");
                int numUnits = Integer.parseInt(s);
                //error checking should be done by server...but can check here too
                for (int i = 0; i < units.size(); i++)
                {
                    if(numUnits > 0)
                    {
                        unitsSend.add(units.get(i));
                        numUnits--;
                    }
                }
                gui.addCommand(new AttackCommand(gui.getPlayer(),from,to,unitsSend));
            }
        });
    }

}
