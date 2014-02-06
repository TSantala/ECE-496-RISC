package gui;

import gameElements.Territory;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneLayout;

public class SideInfoPane extends JTabbedPane
{
    public SideInfoPane()
    {
        setPreferredSize(new Dimension(300, 500));
    }
    public void makeTab(List<Territory> tInfo)
    {
        this.removeAll();
        if(tInfo != null)
        {
            JScrollPane pane = new JScrollPane(new ScrollSidePane(tInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Territory", pane);
        }
    }
}
