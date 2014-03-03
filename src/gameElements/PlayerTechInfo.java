package gameElements;

public class PlayerTechInfo {

	protected String unitType;
	protected int cost;
	
	public PlayerTechInfo(String type, int c){
		unitType = type;
		cost = c;
	}
	
	public String getType(){
		return unitType;
	}
	
	public int getCost(){
		return cost;
	}
	
}
