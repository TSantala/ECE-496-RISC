package gameElements;

abstract class Resource {
	
	protected int myAmount;
	
	abstract void harvest(Player p);
	
	public void increment(int amount){
		myAmount+=amount;
	}
	
	public int getAmount(){
		return myAmount;
	}

}
