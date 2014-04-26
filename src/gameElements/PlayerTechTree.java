package gameElements;

import java.util.HashMap;
import java.util.Map;

public class PlayerTechTree {
	
	Map<Integer,PlayerTechInfo> techTree = new HashMap<Integer,PlayerTechInfo>();
	
	public PlayerTechTree(){
		techTree.put(0,new PlayerTechInfo("Infantry",20));
		techTree.put(1,new PlayerTechInfo("Automatic Weapons",50));
		techTree.put(2,new PlayerTechInfo("Rocket Launchers",80));
		techTree.put(3,new PlayerTechInfo("Tanks",120));
		techTree.put(4,new PlayerTechInfo("Improved Tanks",150));
		techTree.put(5,new PlayerTechInfo("Fighter Planes",180));
		techTree.put(6,new PlayerTechInfo("Nukes",-1));
	}
	
	public String getUnitType(int i){
		return techTree.get(i).getType();
	}
	
	public int getCost(int i){
		return techTree.get(i).getCost();
	}

}
