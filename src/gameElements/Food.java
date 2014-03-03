package gameElements;

public class Food extends Resource {

	public Food(){
		super();
	}

	@Override
	public void harvest(Player p){
		p.adjustResource(this);
	}
	
}
