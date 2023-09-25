package AdventureDemo;

import java.util.ArrayList;

public class eneTest {

    public static void main(String[] args) {
        class mob {
            int x;
            int y;
            int eneIndex;
            mob(int x, int y, int eneIndex) {
                this.x = x;
                this.y = y;
                this.eneIndex = eneIndex;
            }
        }
        class encounterTable {
            int mobIndex;
            int weight;
            encounterTable(int mobIndex, int weight) {
                this.mobIndex = mobIndex;
                this.weight = weight;
            }
        }
        
        ArrayList<enemy> enemies = new ArrayList();
        ArrayList<ArrayList<mob>> mobs = new ArrayList();
        ArrayList<ArrayList<encounterTable>> encTables = new ArrayList();
        
        
    }
    
}
