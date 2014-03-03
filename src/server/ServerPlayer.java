package server;

import java.io.Serializable;

public class ServerPlayer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String myName, myPass;

	public ServerPlayer(String name, String pass) {
		myName = name;
		myPass = pass;
	}
	
	public String getName(){
		return myName;
	}
	
	public boolean equals(ServerPlayer other){
		if(!this.myName.equals(other.myName))
			return false;
		return this.myPass.equals(other.myPass);
	}
	
	@Override
	public boolean equals(Object other){
		ServerPlayer o = (ServerPlayer) other;
		if(!this.myName.equals(o.myName))
			return false;
		return this.myPass.equals(o.myPass);
	}
	
	@Override
	public int hashCode(){
		return myName.hashCode() + myPass.hashCode()*100;
	}

}
