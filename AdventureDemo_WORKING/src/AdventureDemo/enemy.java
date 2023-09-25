package AdventureDemo;

import java.util.ArrayList;
import java.util.HashMap;

public class enemy {
    private String name = "";
    private HashMap<String, Integer> stats = new HashMap();
    private ArrayList<Action> special = new ArrayList();
    private char[][] view;
    
    final String[] s = {"HP", "currentHP", "MP", "currentMP", "ATK", "DEF", "SPD", "SOUL", "HD"};
    
    
    //CONSTRUCTORS
    public enemy() {
        for (String string : s) {
            this.stats.put(string, 0);
        }
    }
    
    public enemy(String name, int h, int w){
        this.name = name;
        for (String string : s) {
            this.stats.put(string, 0);
        }
        this.view = new char[h][w];
        this.genLook();
    }
    
    /*public enemy(String name, ArrayList<Action> special){
        this.name = name;
        this.special = special;
        for (String string : s) {
            this.stats.put(string, 0);
        }
    }*/
    
    //GETTERS
    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }
    
    public int getStat(String s) {
        return this.stats.get(s);
    }

    public ArrayList<Action> getSpecial() {
        return special;
    }
    
    public char[][] getView() {
        return view;
    }
    
    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setStats(HashMap<String, Integer> stats) {
        this.stats = stats;
    }
    
    public void setStat(String s, int i) {
        this.stats.put(s, i);
    }

    public void setSpecial(ArrayList<Action> special) {
        this.special = special;
    }
    
    //GENERATORS
    public static enemy eneGen() {
        enemy e = new enemy();
        //TODO: finish this!!!
        return e;
    }
    
    private String nameGen() {
        String name = "";
        
        String[][] archetype = {{"orc", "viper", "imp", "elf", "tortoise", "werewolf", "centaur", "mantis",
            "serpent", "slime", "kobold", "troglodyte", "worm", "treant", "flytrap", "goblin", "thief",
            "soldier", "mage", "warlock", "yeti", "ghost", "octopus", "lion", "gremlin", "dwarf", "eagle",
            "wisp", "spider", "hunter", "spectre", "beast", "druid"},
            {"dragon", "roc", "terror", "shadow", "demon", "ooze", "homunculus", "mimic", "elemental",
            "dragon", "pirate", "wyrm", "vampire", "necromancer", "witch", "golem", "tentacle",
            "carnifex", "servant", "scion", "knight", "paladin", "cultist", "unicorn", "chimera", "kraken",
            "angel", "sphere", "councilor", "judge"}};
        String[][] adj = {{"black", "white", "red", "blue", "yellow", "green", "shuffling",
            "friendly", "shambling", "common", "reviled", "vicious", "old", "young", "dying", "frightened",
            "diminutive", "lesser", "spirited", "hungry", "starved", "swift", "crippled", "sunken", "forsaken",
            "slumbering"},
            {"dorceless", "violet", "amber", "menacing", "radiant", "storied", "famed", "zealous", "grey",
            "greater", "dazzling", "vile", "holy", "corrupted", "towering", "sylvan", "imperial", "flayed",
            "redeemed", "damned", "undead", "mechanical", "royal", "infernal", "exalted", "ancient"}};
            //faceless, nameless, forgotten, last, first, infinite, finite, sloppy, damp
        String[][] loc = {{"the dark", "the light", "the caverns", "the woods", "the seas",
            "the desert", "the wastes", "the mountains", "the deep", "the unknown", "the rock", "the earth",
            "the swamp", "rumor", "the plains"},
            {"secrets", "the void", "kyne", "nage", "the infinite", "the past", "the future", "hell", "heaven",
            "limbo", "the sky", "the sun", "the moon", "time", "legend"}};
        
        //TODO: finish this!!!
        
        return name;
    }
    
    private void genLook() {
        // reworked this as a void method, 7-12-23
        final char[] characterSet= {
            /*'\u25c8', '\u25c9', '\u25cc', '\u25cd', '\u25d0', '\u25d1', '\u25d2',
            '\u25d3', '\u25d4', '\u25d5', '\u25d7', '\u25db', '\u25dc', '\u25dd',
            '\u25de', '\u25df', '\u25e0', '\u25e1', '\u25e6', '\u25e7', '\u25e8',
            '\u25e9', '\u25ea', '\u25eb', '\u25ec', '\u25ed', '\u25ee',*/
            '+', '-', '=', 'd', 'b', 'p', 'q', 'o', 'w', 'x', 'v',
            'W', 'T', 'Y', 'U', 'I', 'O', 'A', 'H', 'X', 'V', 'M',
            '~', '!', '@', '#', '^', '*', '(', ')', '|', ':', ';',
            '"', '\'', '.', ',', '_'
        };
        int h = this.view.length;
        int w = this.view[0].length;
        
        char[][] monster = new char[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int x = (int) (Math.random() * characterSet.length);
                //System.out.print(x+" ");
                monster[i][j] = characterSet[x];
            }
            //System.out.println("");
        }
        //mirror horizontal?
        if (Math.random()<0.8) {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w/2; j++) {
                    switch (monster[i][j]) {
                        case 'd':
                            monster[i][w-j-1] = 'b';
                            break;
                        case 'b':
                            monster[i][w-j-1] = 'd';
                            break;
                        case 'p':
                            monster[i][w-j-1] = 'q';
                            break;
                        case 'q':
                            monster[i][w-j-1] = 'p';
                            break;
                        case '(':
                            monster[i][w-j-1] = ')';
                            break;
                        case ')':
                            monster[i][w-j-1] = '(';
                            break;
                        default:
                            monster[i][w-j-1] = monster[i][j];
                            break;
                    }
                }
            }
        }
        //mirror vertical?
        if (Math.random()<0.5) {
            for (int i = 0; i < h/2; i++) {
                for (int j = 0; j < w; j++) {
                    switch (monster[i][j]) {
                        case 'd':
                            monster[h-i-1][j] = 'q';
                            break;
                        case 'b':
                            monster[h-i-1][j] = 'p';
                            break;
                        case 'p':
                            monster[h-i-1][j] = 'b';
                            break;
                        case 'q':
                            monster[h-i-1][j] = 'd';
                            break;
                        case 'M':
                            monster[h-i-1][j] = 'W';
                            break;
                        case 'W':
                            monster[h-i-1][j] = 'M';
                            break;
                        case 'v':
                            monster[h-i-1][j] = '^';
                            break;
                        case '^':
                            monster[h-i-1][j] = 'v';
                            break;
                        default:
                            monster[h-i-1][j] = monster[i][j];
                            break;
                    }
                }
            }
        }
        
        this.view = monster;
    }
    
    //OTHER METHODS
    
    public interface Action {
        void run(String str);
    }
}
