package gameElements;


public class DiplomacyCommand extends Command {

	private Player otherPlayer;
	private boolean allies;
	
	public DiplomacyCommand(Player from, Player to, boolean a){
		myPlayer = from;
		otherPlayer = to;
		allies = a;
	}
	
	@Override
	public void enact(GameModel sg) {
		// handled in server commandlist loop.
	}
	
	public Player getOtherPlayer(){
		return otherPlayer;
	}
	
	public boolean nowAlly(){
		return allies;
	}
	
    public String toString(){
    	String status = allies ? " allies" : " enemies";
        String temp  = "Make " +  myPlayer.getName() + " and " + otherPlayer.getName()+ status + "\n";
        return temp;
    }

}
