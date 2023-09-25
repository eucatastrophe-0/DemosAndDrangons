package AdventureDemo;

import java.util.ArrayList;
import java.util.Scanner;

class utility {
    static int[] indexSelect (int r, int n) {
        // picks n unique values from 0 to r and returns them as an int[]
        int[] returnVal = new int[n];
        int counter;

        //validate arguments
        if (n > r) {
            System.err.println("Error: argument 1 cannot be less than "
                    + "argument 2");
            System.exit(0);
        }

        //begin method...
        for (int i = 0; i < n; i++) {
            counter = 0;
            while (counter != 1) {
                counter = 0;
                //pick random int, range of 0 - r,
                //      & assign it to position i of returnVal
                returnVal[i] = (int) (Math.random() * r);
                //for loop cycles through each item that has been assigned
                //      & counts how many match the value at 'i'
                for (int j = 0; j <= i; j++) {
                    if (returnVal[i] == returnVal[j]) {
                        counter++;
                    }
                }
                //IF there is only one value that matches, exit while loop
                //      i.e. the new value is UNIQUE
            }
        }
        return returnVal;
    }
    
    static int diceRoll(int numDice, int dValue) {
        //this method simulates an arbitrary dice roll in xdy format
        //where x is the # of dice and and y is the # of faces per die
        //  i.e. 2d6 = rolling two 6 sided dice
        //       1d20 = one 20 sided die
        int value = 0;
        for (int j = 0; j < numDice; j++) {
                value += (int) (Math.random() * dValue) + 1;
            }
            
        return value;
    }
    
    static int diceRoll (int numRolls, int numDice, int dValue) {
        //as above, in x*ydz format
        int value = 0;
        for (int i = 0; i < numRolls; i++) {
            value += diceRoll(numDice, dValue);
        }
        return value;
    }
    
    static void screenUpdate(char[][] scrn) {
        // NOTE: char[height][width]
        // TODO: make relative to screenSize; possibly just delete this IF?
        if ((scrn.length>35)||(scrn[0].length>75)) {
            System.out.println("ERROR - SCREEN OOB");
        }
        else {
            for (int i = 0; i < scrn.length; i++) {
                for (int j = 0; j < scrn[i].length; j++) {
                    System.out.print(scrn[i][j] + " ");
                    if (j == scrn[i].length - 1) {
                        System.out.print("\n");
                    }
                }
            }
        }
    }
    
    static void updateDisplay (ArrayList<screen> screenList) {
        char[][] display = new char[screenList.get(0).getView().length][screenList.get(0).getView()[0].length]; //max size = 35x75
        screenList.forEach((screen) -> {
            for (int i = 0; i < (screen.view.length); i++) {
                for (int j = 0; j < (screen.view[i].length); j++)
                {
                    display[i+screen.offset[0]][j+screen.offset[1]] = screen.view[i][j];
                }
            }
        });
        System.out.println();
        screenUpdate(display);
    }
    
    public static void gameLoop(ArrayList<screen> display) {
        Scanner scan = new Scanner(System.in);
        boolean loop = true;
        
        //begin interactive display loop
        while (loop) {
            //draw display
            updateDisplay(display);
            screen topScreen = display.get(display.size()-1);
            
            //prompt input
            System.out.print(">> ");
            String input = scan.nextLine().toLowerCase();
            
            //validate input per option regex & go to next iteration if invalid
            if (!input.matches(option.getRegEx(topScreen.getActions())+"|p")) {
            }

            //parse input against options and execute function
            else if (input.matches(option.getRegEx(topScreen.getActions()))) {
                for (option opt : topScreen.getActions()) {
                    if (input.matches(opt.getRegex()) && opt.HasNextView()) {
                        display.add(opt.getNextView());
                    }
                    if (input.matches(opt.getRegex()) && opt.HasAction()) {
                        opt.runAction(input);
                    }
                    if (input.matches(opt.getRegex()) && opt.HasExitView()) {
                        display.remove(display.size()-1);
                    }
                }
            }
            //if input was valid & NOT an action key, quit the loop
            //  (only valid input that is not an action is 'p')
            else {
                loop = false;
            }
        }
    }
    
    static char[][] ovrwrldGen(int height, int width) {
        //int height = 34;
        //int width = 75;
        char[][] overworld = new char[height][width];
        int x,y;
        int mod;
        
        //one obstacle per 25 overworld tiles
        int obstacles = (int) height*width/25;
        
        //initialize array
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                overworld[j][i] = '.';
            }
        }
        //add obstacles - x left to right, y top to bottom
        for (int i = 0; i < obstacles; i++) {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
            overworld[y][x] = 'X';
            
            //sloppy but functional code
            //checks four cells around obstacle - up, down, left, right
            // if in bounds, each has a 50% chance to become an obstacle 'X'
            for (int j = 0; j < 2; j++) {
                mod = 2*j-1;
                for (int k = 0; k < 2; k++) {
                    if (k==0) { //check x
                        if (x+mod>=0 && x+mod<=(width-1)) {
                            if ((int) (Math.random() * 2)==0) {
                                //System.out.println(x+"+"+mod+","+y);
                                overworld[y][x+mod] = 'X';
                            }
                        }
                    }
                    else { //check y
                        if (y+mod>=0 && y+mod<=(height-1)) {
                            if ((int) (Math.random() * 2)==0) {
                                //System.out.println(x+","+y+"+"+mod);
                                overworld[y+mod][x] = 'X';
                            }
                        }
                    }
                }
            }
        }
        return overworld;
    }
    
    static char[][] dungeonGen(int width, int height, int rooms) {
        //TODO: path/corridor generation
        char[][] dungeon = new char[height][width];
        int heightMax = dungeon.length;
        int widthMax = dungeon[0].length;
        int falseCount = 0;
        int roomCount = 0;
        int hPrime,wPrime; // start coords
        int h,w;           // side lengths
        boolean overwrite;
        
        //initialize array
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dungeon[j][i] = '.';
            }
        }
        
        while (falseCount < 500 && roomCount < rooms) {
            overwrite = false;
            //start coordinates
            hPrime = (int) (Math.random() * heightMax);
            wPrime = (int) (Math.random() * widthMax);
            //Room Size
            //  first, initialize h&w to start coords...
            h = hPrime;
            w = wPrime;
            //  Room size is determined by a 4dN virtual dice roll.
            //      Number of dice rolled is 4 b/c min room size is 4x4 wall, 2x2 floor
            //          >> floor of w or h of 1 is a PATH
            //      Die size, dN, is calculated such that the average of 4 rolls
            //          is heightMax/4 or widthMax/4
            for (int i = 0; i < 4; i++) {
                h += (int) (Math.random() * ((heightMax/8)-1)) + 1;
            }
            for (int i = 0; i < 4; i++) {
                w += (int) (Math.random() * ((widthMax/8)-1)) + 1;
            }
            
            // check for OOB error (i.e. wall drawn outside of valid indices)
            if (h>heightMax-1 || w>widthMax-1) {
                falseCount++;
                /*System.out.println("OOB - " + falseCount);*/
                continue;
            }
            
            // Check for overlapping walls (i.e. overwrites)
            // NOTE: as this checks first across rows then across columns,
            //  the algorithm is biased toward 'tall' rather than 'wide' rooms.
            //  could correct this by having second method that checks columns first
            //  and then implementing a 50:50 chance of going to one method or the other
            for (int i = hPrime; i < h; i++) { //row
                for (int j = wPrime; j < w; j++) { //column
                    if (dungeon[i][j] != '.') {
                        falseCount++;
                        /*System.out.println("overwrite - " + falseCount);*/
                        overwrite = true;
                        break;
                    }
                }
            }            
            
            // If overwrite detected, exit loop (i.e. do not add room)
            if (overwrite == true) {
                continue;
            }
            
            // With tests passed, add room to dungeon array
            /*System.out.println("drawing @ " + w + ", " + h + "...");*/
            for (int i = hPrime; i < h; i++) { //row
                for (int j = wPrime; j < w; j++) { //column
                    if (i==hPrime || i==h-1 || j==wPrime || j==w-1) {
                        if ((i==hPrime && j==wPrime)||(i==hPrime && j==w-1)
                                ||(i==h-1 && j==wPrime)||(i==h-1 && j==w-1)) {
                            dungeon[i][j] = '\u220E'; //corners
                        }
                        else {
                            dungeon[i][j] = '\u220E'; //walls
                        }
                    }
                    else {
                        dungeon[i][j] = ' '; //floor
                    }
                }
            }
            roomCount++;
            // path drawer goes here
        }
        return dungeon;
    }
    
    static void combat(ArrayList<screen> mainDisplay, ArrayList<character> characters, ArrayList<enemy> enemies) {
        //the combat screen can support a max of 13 3x3 enemies, or an attack menu size of 14 options (including EXIT)
        //in addition, it supports a max of 5 characters, and will break if their names are too long
        
        //~~~~~~~~~TO ADD:  reactive rendering of enemies and charStats widths/displayed names
        
        //sets size of Combat window relative to size of mainDisplay
        int[] combatFrameSize = {mainDisplay.get(0).getView().length-4, mainDisplay.get(0).getView()[0].length-4};
        
        if (enemies.size()>13) {
            System.err.println("ENEMIES OOB: "+enemies.size());
            System.exit(0);
        }
        if (characters.size()>5) {
            System.err.println("CHARACTERS OOB: "+characters.size());
            System.exit(0);
        }
        
        screen combatFrame = new screen(box(combatFrameSize[0], combatFrameSize[1], '\u220e', '\u2225', '=', ' '), 2, 2, new ArrayList<>());
        mainDisplay.add(combatFrame);
        
        //add character stat box (top of battle view)
        for (int i = 0; i < characters.size(); i++) {
            screen charStats = new screen(box(7, 15, '\u220e', '\u2225', '=', ' '), 2, 2+(i*14), new ArrayList<>());
            String[] charInfo = {characters.get(i).getName(),
                "HP "+characters.get(i).getStat("currentHP"), "MP "+characters.get(i).getStat("currentMP")};
            for (int j = 0; j < charInfo.length; j++) {
                char[] temp = charInfo[j].toCharArray();
                for (int k = 0; k < temp.length; k++) {
                    charStats.view[j+2][k+2] = temp[k];
                }
            }
            mainDisplay.add(charStats);
        }
        
        //add enemies (middle of battle view)
        for (int i = 0; i < enemies.size(); i++) {
            screen enemyPic = new screen(enemies.get(i).getView());
            enemyPic.setOffset(new int[] {15, 5+(i*5)});
            mainDisplay.add(enemyPic);
        }
        
        //add menu (bottom left of battle view)
        screen menuFrame = new screen(box(8, 20, '\u220e', '\u2225', '=', ' '), 24, 2, new option[] {
            new option("ATTACK", "1", new screen(15, 7, new ArrayList<>())),
            new option("ITEM", "2" /**/),
            new option("CAST", "3" /**/),
            new option("RUN", "4", (s) -> {
                for (int i = 0; i < (characters.size()+enemies.size()+2); i++) {
                    mainDisplay.remove(mainDisplay.size()-1);
                }
            })
        });
        //add menu text
        String[] combatChoices = {"1-ATTACK", "2-ITEM", "3-CAST", "4-RUN"};
        for (int i = 0; i < combatChoices.length; i++) {
            char[] temp = combatChoices[i].toCharArray();
            for (int j = 0; j < temp.length; j++) {
                menuFrame.view[i+2][j+3] = temp[j];
            }
        }
        //remove 'exit' added by constructor
        menuFrame.actions.get(0).getNextView().actions.remove(0);
        //populate ATTACK menu with enemies
        for (int i = 0; i < enemies.size(); i++) {
            option Opt = new option(enemies.get(i).getName(), (""+(i+1)), (s) ->{
                        mainDisplay.remove(mainDisplay.size()-1);
                        //put attack here!
                    });
            menuFrame.actions.get(0).getNextView().actions.add(Opt);
        }
        //call menuGen to rebuild menu & add to mainDisplay
        menuFrame.actions.get(0).getNextView().menuGen();
        mainDisplay.add(menuFrame);
        
        //add combatloop, logic to check victory, enemy ai
        
        //at end of combat, remove upper 'counter' screens from mainDisplay
    }
    
    static char[][] box(int h, int w, char crnr, char vert, char horz, char bkgd) {
        char[][] box = new char[h][w];
        for (int i = 0; i < h; i++) { //row
            for (int j = 0; j < w; j++) { //column
                if (i==0 || i==h-1 || j==0 || j==w-1) {
                    if ((i==0 && j==0)||(i==0 && j==w-1)
                            ||(i==h-1 && j==0)||(i==h-1 && j==w-1)) {
                        box[i][j] = crnr; //corners
                    }
                    else if ((j==0 || j==w-1) && !(i==0 || i==h-1)) {
                        box[i][j] = vert; //vertical border
                    }
                    else {
                        box[i][j] = horz; //horizonal border
                    }
                }
                else {
                    box[i][j] = bkgd; //background
                }
            }
        }
        return box;
    }
}

enum Grade {
    X, S, A, B, C, D, E
}