package gameElements;

import java.io.Serializable;

import server.ServerPlayer;

public class HiddenTerritory extends Territory implements Serializable, GameConstants 
{

	private static final long serialVersionUID = 8L;

	public HiddenTerritory(String id)
	{
		super(id);
		this.setOwner(new Player(new ServerPlayer("???", Double.toString(Math.random()))));
	}
	
	@Override
	public String getUnitInfo(){
		return "???";
	}
}
