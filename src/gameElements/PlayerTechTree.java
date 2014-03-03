package gameElements;

import java.util.HashMap;
import java.util.Map;

public class PlayerTechTree {
	
	Map<Integer,PlayerTechInfo> techTree = new HashMap<Integer,PlayerTechInfo>();
	
	public PlayerTechTree(){
		techTree.put(0,new PlayerTechInfo("Infantry",0));
		techTree.put(1,new PlayerTechInfo("Automatic Weapons",20));
		techTree.put(2,new PlayerTechInfo("Rocket Launchers",50));
		techTree.put(3,new PlayerTechInfo("Tanks",80));
		techTree.put(4,new PlayerTechInfo("Improved Tanks",120));
		techTree.put(5,new PlayerTechInfo("Fighter Planes",150));
		techTree.put(6, new PlayerTechInfo("LIMIT",-1));
	}
	
	public String getUnitType(int i){
		return techTree.get(i).getType();
	}
	
	public int getCost(int i){
		return techTree.get(i).getCost();
	}

}
