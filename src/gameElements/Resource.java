package gameElements;

import java.io.Serializable;

abstract class Resource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected int myAmount;
	
	public Resource(int amount){
		myAmount = amount;
	}
	
	abstract void harvest(Player p);
	
	public void increment(int amount){
		myAmount+=amount;
	}
	
	public int getAmount(){
		return myAmount;
	}

}
