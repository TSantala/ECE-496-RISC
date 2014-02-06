package gameElements;

import java.util.ArrayList;
import java.util.List;

public class CommandList {
	
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

}
