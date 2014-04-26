package gameElements;

import java.io.Serializable;

public class InterceptorCommand extends Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterceptorCommand(Territory target, Player p){
		myTerritoryTo = target;
		myPlayer = p;
	}
		
	@Override
	public void enact(GameModel sg) {
		sg.buyInterceptor(myTerritoryTo, myPlayer);
	}
	
	public String toString(){
		String temp  = "Build interceptor on: " +  myTerritoryTo.getID()+"\n";
		return temp;
	}

}