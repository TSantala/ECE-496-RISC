package gameElements;
/*
 * What's sent to server from client every time
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import server.Message;
import server.ObjectClient;
import server.ObjectServer;

public class CommandList extends Message implements Serializable {

	private static final long serialVersionUID = 3L;
	private List<Command> myCommands;
	
	public CommandList(){
		myCommands = new ArrayList<Command>();
	}
	
	public void addCommand(Command c){
		myCommands.add(c);
	}
	
	public void addCommands(List<Command> ls){
		myCommands.addAll(ls);
	}
	
	public void removeCommand(Command c){
		myCommands.remove(c);
	}
	
	public List<Command> getCommands(){
		return myCommands;
	}
	
	public List<Command> getCommands(Class<?> cl){
		List<Command> toReturn = new ArrayList<Command>();
		for(Command c : myCommands){
			if(cl.isInstance(c)){
				toReturn.add(c);
			}
		}
		return toReturn;
	}
	
	public void clear(){
		myCommands.clear();
	}
	
	public String toString()
	{
	    String s = "";
	    for (Command cmd : myCommands)
	    {
	        s = s + cmd.toString() + "\n";
	    }
	    return s;
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		System.out.println("Commandlist sent to server! Contents size: "+myCommands.size());
		os.receiveCommandList(this);
		myCommands.clear();
		return true;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		// should not occur.
		System.out.println("Request for CommandList to send message to Client");
	}

}
