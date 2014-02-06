package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapMouseListener implements MouseListener{
	private GameGraphic myGame;

	public MapMouseListener(GameGraphic game) {
		myGame = game;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		myGame.processClick(myGame.getMousePosition());
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
