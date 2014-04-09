package gameElements;

import java.io.Serializable;
import java.util.List;

import server.ServerPlayer;

public class HiddenTerritory extends Territory 
{

	private static final long serialVersionUID = 8L;

	public HiddenTerritory(String id)
	{
		super(id);
		this.setOwner(new Player(new ServerPlayer("???", Double.toString(Math.random()))));
	}
	
	public HiddenTerritory(String id, List<Territory> territories){
		this(id);
		myNeighbors = territories;
	}
	
	@Override
	public String getUnitInfo(){
		return "???";
	}
}
