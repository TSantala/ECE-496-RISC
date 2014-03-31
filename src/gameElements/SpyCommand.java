package gameElements;
/*
 * Currently used for initialization
 * Will be used for add unit phase in implementation 2
 */
import java.io.Serializable;
import java.util.List;

public class SpyCommand extends Command implements Serializable {

        private static final long serialVersionUID = 1L;

        public SpyCommand(List<Unit> l){
                myUnits = l;
        }
    
        public String toString(){
            String temp  = "Toggle spy status for " +  myUnits.size() + " units.\n";
            return temp;
        }
        
        @Override
        public void enact(GameModel sg) {
                sg.makeSpies(myUnits);
        }
}