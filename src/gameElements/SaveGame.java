package gameElements;

import java.io.Serializable;

public class SaveGame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameInfo myInfo;
	private GameState myGame;

	public SaveGame(GameInfo info, GameState game) {
		myInfo = info;
		myGame = game;
	}
	
	public GameInfo getInfo(){
		return myInfo;
	}
	
	public GameState getState(){
		return myGame;
	}

}
