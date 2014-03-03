package gameElements;

public class Technology extends Resource {
	
	public Technology(int amount){
		super(amount);
	}
	
	@Override
	public void harvest(Player p){
		p.adjustResource(this);
	}

}
