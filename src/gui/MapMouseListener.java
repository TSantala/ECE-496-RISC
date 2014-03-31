package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MapMouseListener implements MouseListener{
	
	private EnhancedGameGraphic myGame;

	public MapMouseListener(EnhancedGameGraphic game) {
		myGame = game;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		myGame.processClick(myGame.getMousePosition(),arg0.getButton() == MouseEvent.BUTTON1);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	   	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
