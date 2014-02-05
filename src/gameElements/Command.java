package gameElements;

public abstract class Command 
{
	protected String type;
	protected String message;
	public Command()
	{
	    
	}
	
	public abstract void enact(ServerGame sg);
}
