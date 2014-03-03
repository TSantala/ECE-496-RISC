package gameElements;

public class Technology extends Resource {
	
	public Technology(){
		super();
	}
	
	@Override
	public void harvest(Player p){
		p.adjustResource(this);
	}

}
