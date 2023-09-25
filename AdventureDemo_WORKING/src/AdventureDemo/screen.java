package AdventureDemo;

import java.util.ArrayList;
import java.util.Arrays;

public class screen {
    char[][] view;
    int[] offset;
    ArrayList<option> actions = new ArrayList();

    //CONSTRUCTORS
    
    public screen(char[][] view) {
        //manual view, no offset, no options
        this.view = view;
        this.offset = new int[] {0, 0};
    }
    
    public screen(char[][] view, ArrayList<option> actions) {
        //manual view, no offset, yes options
        this.view = view;
        this.offset = new int[] {0, 0};
        this.actions = actions;
    }
    
    public screen(char[][] view, int[] offset, ArrayList<option> actions) {
        //manual view, offset as array, yes options
        this.view = view;
        this.offset = offset;
        this.actions = actions;
    }
    
    public screen(char[][] view, int h, int w, ArrayList<option> actions) {
        //manual view, offset as ints, yes options
        this.view = view;
        this.offset = new int[] {h, w};
        this.actions = actions;
    }
    
    public screen(int h, int w, ArrayList<option> actions) {
        //generated view, offset as ints, yes options
        this.offset = new int[] {h, w};
        this.actions = actions;
        menuGen();
    }
    
    public screen(int h, int w, option[] optArray) {
        //generated view, offset as array, options as array
        this.offset = new int[] {h, w};
        this.actions.addAll(Arrays.asList(optArray));
        menuGen();
    }
    
    public screen(char[][] view, int h, int w, option[] optArray) {
        //manual view, offset as ints, options as array
        //combat+stats menu special
        this.view = view;
        this.offset = new int[] {h, w};
        this.actions.addAll(Arrays.asList(optArray));
    }
    
    //GETTERS

    public char[][] getView() {
        return view;
    }
    
    public int[] getOffset() {
        return offset;
    }

    public ArrayList<option> getActions() {
        return actions;
    }
    
    //SETTERS

    public void setView(char[][] view) {
        this.view = view;
    }

    public void setOffset(int[] offset) {
        this.offset = offset;
    }

    public void setActions(ArrayList<option> actions) {
        this.actions = actions;
    }
    
    //OTHER METHODS
    
    public void menuGen() {
        int h = 11;
        int w = 16;
        char[][] tempView;
        
        //adjust menu height for long lists
        if (this.actions.size()>6) {
            h = this.actions.size()+5;
        }
        //adjust menu width for long names
        String longName = "";
        for (option o : this.actions) {
            if (o.getName().length()>longName.length()) {
                longName = o.getName();
            }
        }
        if (longName.length()>10) {
            w = longName.length()+6;
        }
        
        //add EXIT
        actions.add(new option("EXIT", String.valueOf(actions.size()+1), true));
        
        //initialize menu
        tempView = utility.box(h, w, '+', '|', '-', ' ');
        
        //add option names & numbers
        for (int i = 0; i < this.actions.size(); i++) {
            String tempName = (i+1) + " " + this.actions.get(i).getName();
            char[] tempNameArray = tempName.toCharArray();
            for (int j = 0; j < tempNameArray.length; j++) {
                tempView[i+2][j+2] = tempNameArray[j];
            }
        }
        
        this.setView(tempView);
    }
    
    public static screen overworldGen(int h, int w) {
        return new screen(utility.ovrwrldGen(h, w));
    }
}