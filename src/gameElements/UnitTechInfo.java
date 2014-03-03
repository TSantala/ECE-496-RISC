package gameElements;

public class UnitTechInfo extends PlayerTechInfo {
	
	private int combatBonus;
	
	public UnitTechInfo(String type, int c, int cB){
		super(type,c);
		combatBonus = cB;
	}

	
	public int getBonus(){
		return combatBonus;
	}

}
