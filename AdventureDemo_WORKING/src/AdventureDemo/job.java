package AdventureDemo;

//i.e. character class, like Fighter, Wizard, Paladin, etc

import java.util.ArrayList;
import java.util.HashMap;

public class job extends HashMap {
    private String name;
    private final HashMap<String, Grade> stats = new HashMap<>();
    
    //generator
    
    public static job jobGen() {
        job j = new job();
        
        //TODO: procedural generation of stats, per Hylics
        String[] s = {"HP", "MP", "STR", "DEX", "CON", "INT", "WIS", "GUM"};
        
        for (String string : s) {
            j.stats.put(string, gradeGen());
        }
        j.setName(j.nameGen());
        
        return j;
    }
    
    private String nameGen() {
        String n = "";
        int a, b;
        boolean stop = false;
        
        //TODO: make 'stats' reference 's'
        Grade[] stats = {getGrade("HP"), getGrade("MP"), getGrade("STR"), getGrade("DEX"),
            getGrade("CON"), getGrade("INT"), getGrade("WIS"), getGrade("GUM")};
        boolean[] grades = {false, false, false, false, false, false, false, false};
        Grade[] gradeRef = {Grade.X, Grade.S, Grade.A, Grade.B, Grade.C, Grade.D, Grade.E};
        
    // String arrays to choose from - in future, import from external file
        String[][] adj = {{"red", "blue", "yellow", "green", "orange", "violet"},
            {"white", "black", "grey", "pale", "dark"},
            {"silver", "gold"}};
        String[][] noun = {
            {/*melee*/"fighter", "warrior", "hunter", "soldier", "monk"},
            {/*agile*/"rogue", "thief", "scoudrel", "assassin", "scout"},
            {/*tank*/"armor", "tough", "defender", "guard", "keeper", "steward"},
            {/*arcane*/"mage", "sorcerer", "warlock", "witch", "wizard", "magus"},
            {/*divine*/"priest", "cleric", "cultist", "acolyte", "scion", "envoy"},
            {/*initiate*/"champion", "believer", "advocate", "partisan", "proponent", "adherent"}            
        };
        String[][] loc = {
            {"the dark", "the light", "the caverns", "the woods", "the seas",
                "the desert", "the wastes", "the mountains", "the deep", "the unknown", "the rock", "the earth",
                "the swamp", "rumor", "the plains"},
            {"secrets", "the void", "kyne", "nage", "the infinite", "the past", "the future", "hell", "heaven",
                "limbo", "the sky", "the sun", "the moon", "time", "legend"}};
        
    // Pick noun / archetype
        //first, find highest grade stat(s) and store in 'grades'
        for (Grade g : gradeRef) {
            for (int j = 0; j < stats.length; j++) {
                if (g == stats[j]) {
                    grades[j] = true;
                    stop = true;
                }
            }
            if (stop) {
                break;
            }
        }
        //get indices of high stats in 'grades'
        ArrayList<Integer> ref = new ArrayList();
        for (int i = 0; i < grades.length; i++) {
            if (grades[i]) {
                ref.add(i);
            }
        }
        //if there are multiple high stats, pick two unique ones
        int[] ind;
        if (ref.size()>1) {
            ind = utility.indexSelect(ref.size(), 2);
        }
        else {
            ind = new int[] {0};
        }
        //pick noun(s) based on stat(s) and format accordingly
        for (int i = 0; i < ind.length; i++) {
            //first IF only hyphenates on 2nd iteration
            if (i==1) {
                n+="-";
            }
            //if HP is high stat, pick name based on random physical stat
            if (ref.get(ind[i])==0) {
                a = (int) (Math.random()*3); //indices 0-2
                b = (int) (Math.random()*noun[a].length);
                n += noun[a][b];
            }
            //if MP is high stat, pick name based on random mental stat
            else if (ref.get(ind[i])==1) {
                a = (int) (Math.random()*3)+3; //indices 3-5
                b = (int) (Math.random()*noun[a].length);
                n += noun[a][b];
            }
            //else, pick from specific stat
            else {
                b = (int) (Math.random()*noun[ref.get(ind[i])-2].length);
                n += noun[ref.get(ind[i])-2][b];
            }
        }
    // Add adj
        // 50% chance to get adjective
        if (1==(int)(Math.random()*2)) {
            // better GRA = cooler adj subset
            a=0;
            double d = (Math.random()*10) + (getGRA()-14);
            if (d>5 && d<=10) {
                a=1;
            }
            if (d>10) {
                a=2;
            }
            n = adj[a][(int)(Math.random()*adj[a].length)]+" "+n;
        }
    // Add loc
        // 50% chance to get locative
        if (1==(int)(Math.random()*2)) {
            // better GRA = cooler loc subset
            a=0;
            double d = (Math.random()*10) + (getGRA()-14);
            if (d>8) {
                a=1;
            }
            n += " of "+loc[a][(int)(Math.random()*loc[a].length)];
        }
        
        return n;
    }
    
    //setters

    private void setName(String name) {
        this.name = name;
    }

    private void setStat(String s, Grade g) {
        this.stats.put(s, g);
    }
    
    //getters

    public String getName() {
        return name;
    }
    
    public Grade getGrade(String s) {
        return stats.get(s);
    }
    
    //other methods
    
    public void toConsole() {
        System.out.println(getName());
        System.out.println("GRA=" + getGRA());
        System.out.println("HP: " + getGrade("HP")+", "+toVal("HP"));
        System.out.println("MP: " + getGrade("MP")+", "+toVal("MP"));
        System.out.println("STR: " + getGrade("STR")+", "+toVal("STR"));
        System.out.println("DEX: " + getGrade("DEX")+", "+toVal("DEX"));
        System.out.println("CON: " + getGrade("CON")+", "+toVal("CON"));
        System.out.println("INT: " + getGrade("INT")+", "+toVal("INT"));
        System.out.println("WIS: " + getGrade("WIS")+", "+toVal("WIS"));
        System.out.println("GUM: " + getGrade("GUM")+", "+toVal("GUM"));
    }
    
    public static void toConsole(ArrayList<job> jobList) {
        int n=0;
        String[] string = {"HP", "MP", "STR", "DEX", "CON", "INT", "WIS", "GUM"};
        System.out.println();
        for (job j : jobList) {
            n++;
            System.out.println(n+" - "+j.getName());
        }
        System.out.println();
        n=0;
        for (job j : jobList) {
            n++;
            System.out.print("\t"+n);
        }
        System.out.println("\n");
        System.out.print("GRA\t");
        for (job j : jobList) {
            System.out.print(j.getGRA()+"\t");
        }
        System.out.println();
        for (String s : string) {
            System.out.print(s+"\t");
            for (job j : jobList) {
                System.out.print(j.getGrade(s)+"\t");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
    
    public double getGRA() {
        double d=0;
        
        d += (double)(toVal("HP")+1)/2;
        d += (double)(toVal("MP")+1)/2;
        d += (double)(toVal("STR")+1)/2;
        d += (double)(toVal("DEX")+1)/2;
        d += (double)(toVal("CON")+1)/2;
        d += (double)(toVal("INT")+1)/2;
        d += (double)(toVal("WIS")+1)/2;
        d += (double)(toVal("GUM")+1)/2;
        
        return d;
    }
    
    public static Grade gradeGen() {
        Grade g;
        int r = (int) (Math.random()*100);
        
        if (r<0) {
            g=Grade.X;
        }
        else if (r==0) {
            g=Grade.S;
        }
        else if (r>0 && r<=3) {
            g=Grade.A;
        }
        else if (r>3 && r<=9) {
            g=Grade.B;
        }
        else if (r>9 && r<=42) {
            g=Grade.C;
        }
        else if (r>42 && r<=75) {
            g=Grade.D;
        }
        else {
            g=Grade.E;
        }
        
        return g;
    }
    
    public int toVal(Grade g) {
        int i =0;
        switch (g) {
            case X:
                i=12;
                break;
            case S:
                i=10;
                break;
            case A:
                i=8;
                break;
            case B:
                i=6;
                break;
            case C:
                i=4;
                break;
            case D:
                i=2;
                break;
            case E:
                i=1;
                break;
        }
        return i;
    }
    
    public int toVal(String s) {
        return toVal(getGrade(s));
    }
}
