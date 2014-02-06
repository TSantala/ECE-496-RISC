package gui;

import gameElements.Territory;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.*;

public class ScrollSidePane extends JPanel implements Scrollable
{
    public ScrollSidePane(List<Territory> tlist)
    {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < tlist.size(); i++)
        {
            info.append(tlist.get(i));
        }
        JLabel txt = new JLabel(info.toString(), JLabel.LEFT);
        add(txt);
    }
    @Override
    public Dimension getPreferredScrollableViewportSize () {
        return null;
    }

    @Override
    public int getScrollableBlockIncrement (Rectangle arg0, int arg1, int arg2) {
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportHeight () {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportWidth () {
        return true;
    }

    @Override
    public int getScrollableUnitIncrement (Rectangle arg0, int arg1, int arg2) {
        return 15;
    }

}
