package main;

import server.ObjectClient;
import server.ObjectServer;

public class Main {

	public static void main(String[] args){
		

		ObjectClient myClient1 = new ObjectClient();
		myClient1.start();

//		TESTING PROPER FUNCTIONALITY OF PRIORITY UNIT LISTS.
//		Unit u1 = new Unit(0); u1.setTechLevel(1);
//		Unit u2 = new Unit(1); u2.setTechLevel(5);
//		Unit u3 = new Unit(2); u3.setTechLevel(3);
//		Unit u4 = new Unit(3); u4.setTechLevel(4);
//		Territory t = new Territory(0);
//		t.addUnit(u1); t.addUnit(u2); t.addUnit(u3); t.addUnit(u4);
//		
//		for(Unit u : t.getUnits()){
//			System.out.println(u.getID());
//		}
		
		/*ObjectClient myClient2 = new ObjectClient();
		myClient2.start();*/
	}
}
