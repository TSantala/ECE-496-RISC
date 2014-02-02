package gameElements;

import java.util.*;

public class Player 
{
	private String name;
	private List<Unit> myUnits;
	private TerritoryGroup myTerritories;
	//private List<Order> previousOrders;
	public Player()
	{
		name = "johnandtimo";
		setMyUnits(new ArrayList<Unit>());
		setMyTerritories(new TerritoryGroup());
	}
	public void setName(String s)
	{
		name = s;
	}
	public String getName()
	{
		return name;
	}
        public List<Unit> getMyUnits () {
            return myUnits;
        }
        public void setMyUnits (List<Unit> myUnits) {
            this.myUnits = myUnits;
        }
        public TerritoryGroup getMyTerritories () {
            return myTerritories;
        }
        public void setMyTerritories (TerritoryGroup myTerritories) {
            this.myTerritories = myTerritories;
        }
}
