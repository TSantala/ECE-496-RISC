package gameElements;

import java.io.Serializable;
import java.util.*;

public class GameMap implements Serializable 
{
	private static final long serialVersionUID = 4L;

	private List<Territory> myTerritories = new ArrayList<Territory>();
	private int myTerritoryCount=0;
	
	public GameMap(){
		Territory Alabama = new Territory("Alabama");
		Territory Alaska = new Territory("Alaska");
		Territory Arizona = new Territory("Arizona");
		Territory Arkansas = new Territory("Arkansas");
		Territory California = new Territory("California");
		Territory Colorado  = new Territory("Colorado");
		Territory Florida = new Territory("Florida");
		Territory Georgia = new Territory("Georgia");
		Territory Hawaii = new Territory("Hawaii");
		Territory Idaho = new Territory("Idaho");
		Territory Illinois = new Territory("Illinois");
		Territory Indiana = new Territory("Indiana");
		Territory Iowa = new Territory("Iowa");
		Territory Kansas = new Territory("Kansas");
		Territory Kentucky = new Territory("Kentucky");
		Territory Louisiana = new Territory("Louisiana");
		Territory MA = new Territory("MA");
		Territory Maine = new Territory("Maine");
		Territory MD = new Territory("MD");
		Territory Michigan = new Territory("Michigan");
		Territory Minnesota = new Territory("Minnesota");
		Territory Mississippi = new Territory("Mississippi");
		Territory Missouri = new Territory("Missouri");
		Territory Montana = new Territory("Montana");
		Territory Nebraska = new Territory("Nebraska");
		Territory Nevada = new Territory("Nevada");
		Territory New_Hampshire = new Territory("New_Hampshire");
		Territory New_Jersey = new Territory("New_Jersey");
		Territory New_Mexico = new Territory("New_Mexico");
		Territory New_York = new Territory("New_York");
		Territory North_Carolina = new Territory("North_Carolina");
		Territory North_Dakota = new Territory("North_Dakota");
		Territory Ohio = new Territory("Ohio");
		Territory Oklahoma = new Territory("Oklahoma");
		Territory Oregon = new Territory("Oregon");
		Territory Pennsylvania = new Territory("Pennsylvania");
		Territory South_Carolina = new Territory("South_Carolina");
		Territory South_Dakota = new Territory("South_Dakota");
		Territory Tennessee = new Territory("Tennessee");
		Territory Texas = new Territory("Texas");
		Territory Utah = new Territory("Utah");
		Territory Vermont = new Territory("Vermont");
		Territory Virginia = new Territory("Virginia");
		Territory Washington = new Territory("Washington");
		Territory West_Virginia = new Territory("West_Virginia");
		Territory Wisconsin = new Territory("Wisconsin");
		Territory Wyoming = new Territory("Wyoming");
		
		Alabama.addNeighbor(Mississippi);
		Alabama.addNeighbor(Tennessee);
		Alabama.addNeighbor(Georgia);
		Alabama.addNeighbor(Florida);
		
		Alaska.addNeighbor(Washington);
		
		Arizona.addNeighbor(California);
		Arizona.addNeighbor(Nevada);
		Arizona.addNeighbor(Utah);
		Arizona.addNeighbor(New_Mexico);
		
		Arkansas.addNeighbor(Oklahoma);
		Arkansas.addNeighbor(Texas);
		Arkansas.addNeighbor(Louisiana);
		Arkansas.addNeighbor(Missouri);
		
		California.addNeighbor(Arizona);
		California.addNeighbor(Nevada);
		California.addNeighbor(Hawaii);
		California.addNeighbor(Oregon);
		
		Colorado.addNeighbor(New_Mexico);
		Colorado.addNeighbor(Wyoming);
		Colorado.addNeighbor(Kansas);
		Colorado.addNeighbor(Oklahoma);
		Colorado.addNeighbor(Nebraska);
		
		Florida.addNeighbor(Alabama);
		Florida.addNeighbor(Georgia);
		
		Georgia.addNeighbor(Alabama);
		Georgia.addNeighbor(Florida);
		Georgia.addNeighbor(South_Carolina);
		
		Hawaii.addNeighbor(California);
		
		Idaho.addNeighbor(Washington);
		Idaho.addNeighbor(Oregon);
		Idaho.addNeighbor(Nevada);
		Idaho.addNeighbor(Utah);
		Idaho.addNeighbor(Montana);
		
		Illinois.addNeighbor(Wisconsin);
		Illinois.addNeighbor(Indiana);
		
		Indiana.addNeighbor(Illinois);
		Indiana.addNeighbor(Michigan);
		Indiana.addNeighbor(Ohio);
		
		Iowa.addNeighbor(Missouri);
		Iowa.addNeighbor(Nebraska);
		Iowa.addNeighbor(South_Dakota);
		Iowa.addNeighbor(Minnesota);
		
		Kansas.addNeighbor(Oklahoma);
		Kansas.addNeighbor(Colorado);
		Kansas.addNeighbor(Nebraska);
		Kansas.addNeighbor(Missouri);
		
		Kentucky.addNeighbor(Tennessee);
		Kentucky.addNeighbor(West_Virginia);
		
		Louisiana.addNeighbor(Texas);
		Louisiana.addNeighbor(Arkansas);
		Louisiana.addNeighbor(Mississippi);
		
		Maine.addNeighbor(New_Hampshire);
		
		MD.addNeighbor(Virginia);
		MD.addNeighbor(Pennsylvania);
		MD.addNeighbor(New_Jersey);
		
		MA.addNeighbor(New_York);
		MA.addNeighbor(New_Hampshire);
		MA.addNeighbor(Vermont);
		
		Michigan.addNeighbor(Wisconsin);
		Michigan.addNeighbor(Indiana);
		Michigan.addNeighbor(Ohio);
		
		Minnesota.addNeighbor(Wisconsin);
		Minnesota.addNeighbor(Iowa);
		Minnesota.addNeighbor(South_Dakota);
		Minnesota.addNeighbor(North_Dakota);
		
		Mississippi.addNeighbor(Louisiana);
		Mississippi.addNeighbor(Alabama);
		Mississippi.addNeighbor(Tennessee);
		
		Missouri.addNeighbor(Arkansas);
		Missouri.addNeighbor(Oklahoma);
		Missouri.addNeighbor(Kansas);
		Missouri.addNeighbor(Nebraska);
		Missouri.addNeighbor(Iowa);
		
		Montana.addNeighbor(Wyoming);
		Montana.addNeighbor(South_Dakota);
		Montana.addNeighbor(North_Dakota);
		Montana.addNeighbor(Idaho);
		
		Nebraska.addNeighbor(Kansas);
		Nebraska.addNeighbor(Colorado);
		Nebraska.addNeighbor(Wyoming);
		Nebraska.addNeighbor(South_Dakota);
		Nebraska.addNeighbor(Iowa);
		Nebraska.addNeighbor(Missouri);
		
		Nevada.addNeighbor(Utah);
		Nevada.addNeighbor(Arizona);
		Nevada.addNeighbor(California);
		Nevada.addNeighbor(Oregon);
		Nevada.addNeighbor(Idaho);
		
		New_Hampshire.addNeighbor(Maine);
		New_Hampshire.addNeighbor(MA);
		New_Hampshire.addNeighbor(Vermont);
		
		New_Jersey.addNeighbor(MD);
		New_Jersey.addNeighbor(Pennsylvania);
		New_Jersey.addNeighbor(New_York);
		
		New_Mexico.addNeighbor(Texas);
		New_Mexico.addNeighbor(Colorado);
		New_Mexico.addNeighbor(Arizona);
		New_Mexico.addNeighbor(Oklahoma);
		
		New_York.addNeighbor(Pennsylvania);
		New_York.addNeighbor(New_Jersey);
		New_York.addNeighbor(MA);
		New_York.addNeighbor(Vermont);
		
		North_Carolina.addNeighbor(South_Carolina);
		North_Carolina.addNeighbor(Virginia);
		
		North_Dakota.addNeighbor(Minnesota);
		North_Dakota.addNeighbor(South_Dakota);
		North_Dakota.addNeighbor(Montana);
		
		Ohio.addNeighbor(Michigan);
		Ohio.addNeighbor(Indiana);
		Ohio.addNeighbor(Pennsylvania);
		
		Oklahoma.addNeighbor(Arkansas);
		Oklahoma.addNeighbor(Texas);
		Oklahoma.addNeighbor(New_Mexico);
		Oklahoma.addNeighbor(Colorado);
		Oklahoma.addNeighbor(Kansas);
		Oklahoma.addNeighbor(Missouri);
		
		Oregon.addNeighbor(Washington);
		Oregon.addNeighbor(Idaho);
		Oregon.addNeighbor(Nevada);
		Oregon.addNeighbor(California);
		
		Pennsylvania.addNeighbor(New_York);
		Pennsylvania.addNeighbor(Ohio);
		Pennsylvania.addNeighbor(West_Virginia);
		Pennsylvania.addNeighbor(MD);
		Pennsylvania.addNeighbor(New_Jersey);
		
		South_Carolina.addNeighbor(Georgia);
		South_Carolina.addNeighbor(North_Carolina);
		
		South_Dakota.addNeighbor(Wyoming);
		South_Dakota.addNeighbor(Nebraska);
		South_Dakota.addNeighbor(Iowa);
		South_Dakota.addNeighbor(Minnesota);
		South_Dakota.addNeighbor(North_Dakota);
		South_Dakota.addNeighbor(Montana);
		
		Tennessee.addNeighbor(Mississippi);
		Tennessee.addNeighbor(Alabama);
		Tennessee.addNeighbor(Kentucky);
		
		Texas.addNeighbor(New_Mexico);
		Texas.addNeighbor(Oklahoma);
		Texas.addNeighbor(Arkansas);
		Texas.addNeighbor(Louisiana);
		
		Utah.addNeighbor(Idaho);
		Utah.addNeighbor(Nevada);
		Utah.addNeighbor(Arizona);
		
		Vermont.addNeighbor(New_York);
		Vermont.addNeighbor(MA);
		Vermont.addNeighbor(New_Hampshire);
		
		Virginia.addNeighbor(North_Carolina);
		Virginia.addNeighbor(MD);
		
		Washington.addNeighbor(Idaho);
		Washington.addNeighbor(Alaska);
		Washington.addNeighbor(Oregon);
		
		West_Virginia.addNeighbor(Kentucky);
		West_Virginia.addNeighbor(Pennsylvania);
		
		Wisconsin.addNeighbor(Minnesota);
		Wisconsin.addNeighbor(Michigan);
		Wisconsin.addNeighbor(Illinois);
		
		Wyoming.addNeighbor(Montana);
		Wyoming.addNeighbor(Colorado);
		Wyoming.addNeighbor(Nebraska);
		Wyoming.addNeighbor(South_Dakota);
		
		myTerritories.add(Alabama);
		myTerritories.add(Alaska);
		myTerritories.add(Arizona);
		myTerritories.add(Arkansas);
		myTerritories.add(California);
		myTerritories.add(Colorado);
		myTerritories.add(Florida);
		myTerritories.add(Georgia);
		myTerritories.add(Hawaii);
		myTerritories.add(Idaho);
		myTerritories.add(Illinois);
		myTerritories.add(Indiana);
		myTerritories.add(Iowa);
		myTerritories.add(Kansas);
		myTerritories.add(Kentucky);
		myTerritories.add(Louisiana);
		myTerritories.add(Maine);
		myTerritories.add(MA);
		myTerritories.add(MD);
		myTerritories.add(Michigan);
		myTerritories.add(Minnesota);
		myTerritories.add(Mississippi);
		myTerritories.add(Missouri);
		myTerritories.add(Montana);
		myTerritories.add(Nebraska);
		myTerritories.add(Nevada);
		myTerritories.add(New_Hampshire);
		myTerritories.add(New_Jersey);
		myTerritories.add(New_Mexico);
		myTerritories.add(New_York);
		myTerritories.add(North_Carolina);
		myTerritories.add(North_Dakota);
		myTerritories.add(Ohio);
		myTerritories.add(Oklahoma);
		myTerritories.add(Oregon);
		myTerritories.add(Pennsylvania);
		myTerritories.add(South_Carolina);
		myTerritories.add(South_Dakota);
		myTerritories.add(Tennessee);
		myTerritories.add(Texas);
		myTerritories.add(Utah);
		myTerritories.add(Vermont);
		myTerritories.add(Virginia);
		myTerritories.add(Washington);
		myTerritories.add(West_Virginia);
		myTerritories.add(Wisconsin);
		myTerritories.add(Wyoming);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
		myTerritories.add(Alabama);
	}

	public GameMap(int num)		// for now, just create a 'circle' with each connected to numeric neighbors and last to first
	{
		while(myTerritoryCount<num){
			Territory newT = this.createTerritory();
			myTerritories.add(newT);
		}
		for(int i = 0; i<num-1; i++){
			myTerritories.get(i).addNeighbor(myTerritories.get(i+1));
			myTerritories.get(i+1).addNeighbor(myTerritories.get(i));
		}
		myTerritories.get(num-1).addNeighbor(myTerritories.get(0));
		myTerritories.get(0).addNeighbor(myTerritories.get(num-1));
	}

	public Territory createTerritory(){
		myTerritoryCount++;
		return new Territory(Integer.toString(myTerritoryCount));
	}

	public GameMap(List<Territory> map){
		myTerritories = map;
	}

	public boolean hasPath(Territory from, Territory to, Player p){
		List<Territory> pt = p.getTerritories();
		if(!pt.contains(from) || !pt.contains(to))
			return false;
		List<Territory> visited = new ArrayList<Territory>();
		Stack<Territory> stack = new Stack<Territory>();
		stack.add(from);
		while(!stack.isEmpty()){
			Territory t = stack.pop();
			if(t.getID() == to.getID())
				return true;
			visited.add(t);
			for(Territory n : t.getNeighbors()){
				if(!visited.contains(n) && pt.contains(n))
					stack.add(n);
			}
		}
		return false;
	}

	public boolean canAttack(Territory from, Territory to, Player p){
		if(!p.containsTerritory(from))
			return false;
		if(p.containsTerritory(to))
			return false;
		return from.getNeighbors().contains(to);
	}

	public Territory getTerritory(String id){
		for(Territory t : myTerritories){
			if(t.getID().equals(id))
				return t;
		}
		System.out.println("Get territory returned null!! In GameMap");
		return null;
	}

	public List<Territory> getTerritories(){
		return myTerritories;
	}

	public GameMap clone(){
		List<Territory> clonedTerritories = new ArrayList<Territory>();
		for(Territory t : myTerritories){
			clonedTerritories.add(t.clone());
		}
		GameMap toReturn = new GameMap(clonedTerritories);
		for(Territory t : clonedTerritories){
			Territory original = this.getTerritory(t.getID());
			for(Territory n : original.getNeighbors())
				t.addNeighbor(toReturn.getTerritory(n.getID()));
		}
		return toReturn;
	}

}
