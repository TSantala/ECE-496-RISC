package gameElements;

import java.io.Serializable;

abstract class Tradeable implements Serializable
{
    public Tradeable()
    {
        
    }
    abstract void trade();
}
