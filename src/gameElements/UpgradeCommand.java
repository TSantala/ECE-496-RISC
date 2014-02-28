package gameElements;
/*
 * Currently used for initialization
 * Will be used for add unit phase in implementation 2
 */
import java.io.Serializable;
import java.util.List;

public class UpgradeCommand extends Command implements Serializable {

        private static final long serialVersionUID = 1L;

        public UpgradeCommand(){
                //testing...
        }
        
        public UpgradeCommand(Player p, Territory from, List<Unit> units){
            myPlayer = p;
            myTerritoryFrom = from;
            myUnits = units;
        }
    
        public String toString(){
            String temp  = "Add " +  myUnits.size() + " units to : " + myTerritoryFrom.getID()+ "\n";
            return temp;
        }
        
        @Override
        public void enact(GameModel sg) {
                //sg.placeUnits(myPlayer, myTerritoryFrom, myUnits);
        }
}