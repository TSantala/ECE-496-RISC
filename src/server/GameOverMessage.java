package server;

import java.io.Serializable;

public class GameOverMessage extends Message implements Serializable
{
    public GameOverMessage()
    {
    
    }
    
    @Override
    public boolean sendMessageToServer (ObjectServer os) {
        //shouldn't ever be called
        return true;
    }

    @Override
    public void sendMessageToClient (ObjectClient oc) {
        //shouldn't be called
        
    }

}
