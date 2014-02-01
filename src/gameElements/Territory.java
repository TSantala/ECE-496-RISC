package gameElements;

import java.util.*;

public class Territory 
{
	List<Unit> unitsOnTerritory;
	List<Territory> neighbors;
	public Territory()
	{
		unitsOnTerritory = new ArrayList<Unit>();
		neighbors = new ArrayList<Territory>();
	}
	public void setNeighbors(List<Territory> nghbrs)
	{
		neighbors = nghbrs;
	}
	public void setUnits(List<Unit> units)
	{
		unitsOnTerritory = units;
	}
}
