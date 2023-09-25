package AdventureDemo;

//11-18-22: copied bulk of code from hero.java, edited HEAVILY
//12-18-22: refactored stats as HashMap, incl. related methods

import static AdventureDemo.utility.box;
import java.util.ArrayList;
import java.util.HashMap;

public class character {
    private String name;
    private job job;
    private ArrayList<item> inventory = new ArrayList<>();
    private HashMap<String, Integer> stats = new HashMap<>();
    
    final String[] s = {"HP", "MP", "STR", "DEX", "CON", "INT", "WIS", "GUM",
        "currentHP", "currentMP", "ATK", "DEF", "SPD", "SOUL", "XP", "LVL", "HD", "GOLD"};
    
    //CONSTRUCTOR
    public character(String n, job j) {
        for (String string : s) {
            this.stats.put(string, 0);
        }
        setName(n);
        setJob(j);
        lvlUp(j);
        lvlUp(j);
        lvlUp(j);
        setStat("LVL", 1);
    }
    
    //GETTERS
    
    public int getStat(String s) {
        return stats.get(s);
    }

    public String getName() {
        return name;
    }

    public job getJob() {
        return job;
    }
    
    //SETTERS

    public void setStat(String s, int i) {
        this.stats.put(s, i);
    }
    
    public void setName(String n) {
        this.name = n;
    }

    public void setJob(job job) {
        this.job = job;
    }
    
    //OTHER METHODS
    
    @Override
    public String toString() {
        String[] s = {"HP", "MP", "STR", "DEX", "CON", "INT", "WIS", "GUM"};
        String tostring = "Name: "+this.getName()
                +"\nLevel "+this.getStat("LVL")+" "+this.getJob().getName();
        for (String string : s) {
            tostring += "\n" + string + ": " + this.getStat(string);
        }
        return tostring;
    }
    
    public void modStat(String s, int i) {
        this.setStat(s, this.getStat(s)+i);
    }
    
    public void restore() {
        restoreHP();
        restoreMP();
    }
    public void restoreHP() {
        //  TODO
    }
    public void restoreMP() {
        //  TODO
    }
    
    public void lvlUp(job j) {
        String[] s = {"HP", "MP", "STR", "DEX", "CON", "INT", "WIS", "GUM"};
        this.modStat("LVL", 1);
        this.modStat("HD", 1);
        for (String string : s) {
            this.modStat(string, (int) (Math.random()*j.toVal(string)) +1);
        }
    }
    
    public char[][] viewStats() {
        int w = 15;
        int name = Math.max(getName().length(), getJob().getName().length());
        if (name>9) {
            w = name+6;
        }
        char[][] view = box(17, w, '+', '|', '-', ' ');
        
        String[] charInfo = {
            " "+getName(),
            " "+getJob().getName(),
            "",
            " HP "+getStat("currentHP")+"/"+getStat("HP"),
            " MP "+getStat("currentMP")+"/"+getStat("MP"),
            "STR "+getStat("STR"),
            "DEX "+getStat("DEX"),
            "CON "+getStat("CON"),
            "INT "+getStat("INT"),
            "WIS "+getStat("WIS"),
            "GUM "+getStat("GUM"),
            "",
            "1 EXIT"
        };
        
        for (int j = 0; j < charInfo.length; j++) {
            char[] temp = charInfo[j].toCharArray();
            for (int k = 0; k < temp.length; k++) {
                view[j+2][k+2] = temp[k];
            }
        }
        
        return view;
    }
}
