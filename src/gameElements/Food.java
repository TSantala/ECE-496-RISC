package gameElements;

public class Food extends Resource {

	public Food(int amount){
		super(amount);
	}

	@Override
	public void harvest(Player p){
		p.adjustResource(this);
	}
	
}
