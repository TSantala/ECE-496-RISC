package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameGraphic extends JPanel{
	private GameGUI myGUI;

	public GameGraphic(GameGUI gameGUI) {
		myGUI = gameGUI;
		this.setSize(600, 400);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, 30, 30);
		g2d.drawOval(0, 50, 30, 30);		
		g2d.fillRect(50, 0, 30, 30);
		g2d.drawRect(50, 50, 30, 30);

	}
}
