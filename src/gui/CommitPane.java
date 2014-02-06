package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CommitPane extends JPanel 
{
    public CommitPane()
    {
        JButton commit = new JButton("Commit");
        commit.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent arg0) {
                //myObjectClient.sendMessage(cur command list);
            }
            
        });
    }
}
