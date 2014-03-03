package gameElements;

import java.util.HashMap;
import java.util.Map;

public class UnitTechTree {
	
	Map<Integer,UnitTechInfo> techTree = new HashMap<Integer,UnitTechInfo>();
	
	public UnitTechTree(){
		techTree.put(0,new UnitTechInfo("Infantry",0,0));
		techTree.put(1,new UnitTechInfo("Automatic Weapons",3,1));
		techTree.put(2,new UnitTechInfo("Rocket Launchers",8,3));
		techTree.put(3,new UnitTechInfo("Tanks",19,6));
		techTree.put(4,new UnitTechInfo("Improved Tanks",25,12));
		techTree.put(5,new UnitTechInfo("Fighter Planes",35,15));
		techTree.put(6, new UnitTechInfo("LIMI",-1,-1));
	}
	
	public int getCombatBonus(int i){
		return techTree.get(i).getBonus();
	}
	
	public String getUnitType(int i){
		return techTree.get(i).getType();
	}
	
	public int getCost(int i){
		return techTree.get(i).getCost();
	}
	
}
