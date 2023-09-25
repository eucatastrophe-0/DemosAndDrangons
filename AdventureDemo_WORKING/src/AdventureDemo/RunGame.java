package AdventureDemo;

import static AdventureDemo.utility.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
    DEVLOG
        2/4/23 - bug found - player can spawn trapped in obstacles
                possible fixes:
                    -keep obstacles from forming at spawn location
                    -move spawn location if obstacles detected
        2/5/23 - added stats menu.  Brute force workaround implemented to make view
        2/7/23 - stat view uses more general method now
        2/18/23 - incorporated combat routine (still no actual combat, but view is there)
        7/12/23 - modified enemy.genLook() to be a more general void method
                    and began work to incorporate title screen into mainDisplay
        8/19/23 - screenSize now copied from screenSizeDefault;
                    combatFrame sized relative to mainDisplay
        9/12/23 - made overworldGen() more general - encapsulated 'utility' method with 'screen' method
        9/23/23 - added input validation to jobChoice
*/

public class RunGame {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] screenSizeDefault = {34, 75};
        int[] screenSize = new int[2];
        System.arraycopy(screenSizeDefault, 0, screenSize, 0, screenSize.length);
        
        ArrayList<screen> mainDisplay = new ArrayList();
        /*screen titleScreen = new screen(
                utility.box(screenSize[0], screenSize[1], '~', '~', '~', ' ')
        );
        ArrayList<option> titleScreenActions = new ArrayList();
        titleScreenActions.add(new option())  << TODO: FINISH THIS!*/
        
        //boilerplate intro screen & character select
        System.out.println(" ______   _______  _______  _______  _______      __      ______   _______  _______  _______  _______  _        _______ \n" +
            "(  __  \\ (  ____ \\(       )(  ___  )(  ____ \\    /__\\    (  __  \\ (  ____ )(  ___  )(  ____ \\(  ___  )( (    /|(  ____ \\\n" +
            "| (  \\  )| (    \\/| () () || (   ) || (    \\/   ( \\/ )   | (  \\  )| (    )|| (   ) || (    \\/| (   ) ||  \\  ( || (    \\/\n" +
            "| |   ) || (__    | || || || |   | || (_____     \\  /    | |   ) || (____)|| (___) || |      | |   | ||   \\ | || (_____ \n" +
            "| |   | ||  __)   | |(_)| || |   | |(_____  )    /  \\/\\  | |   | ||     __)|  ___  || | ____ | |   | || (\\ \\) |(_____  )\n" +
            "| |   ) || (      | |   | || |   | |      ) |   / /\\  /  | |   ) || (\\ (   | (   ) || | \\_  )| |   | || | \\   |      ) |\n" +
            "| (__/  )| (____/\\| )   ( || (___) |/\\____) |  (  \\/  \\  | (__/  )| ) \\ \\__| )   ( || (___) || (___) || )  \\  |/\\____) |\n" +
            "(______/ (_______/|/     \\|(_______)\\_______)   \\___/\\/  (______/ |/   \\__/|/     \\|(_______)(_______)|/    )_)\\_______)\n" +
            "                                                                                                                        \t\tv3.0 ALPHA");
        System.out.print("\n\n\n\n\tEnter your name: ");
        String name = scan.nextLine();
        
        System.out.println();
        ArrayList<job> jobList = new ArrayList();
        for (int i = 0; i < 6; i++) {
            job j = job.jobGen();
            jobList.add(j);
        }
        job.toConsole(jobList);
        
        //Validate input for job choice
        int jobChoice = -1;
        boolean jobLoop = true;
        while (jobLoop) {
            System.out.print("\n\tSelect your class [1-"+jobList.size()+"]: ");
        
            int inputChoice;
            String tryChoice = scan.nextLine();
            try {
                inputChoice = Integer.parseInt(tryChoice);
                if (inputChoice>0 && inputChoice<=6) {
                    jobChoice = inputChoice;
                    jobLoop = false;
                }
                else {
                    System.out.println("Try again...\n");
                }
                
            } catch (Exception e) {
                System.out.println("Try again...\n");
            }
        }
        
        ArrayList<character> c = new ArrayList();
        character MAIN = new character(name, jobList.get(jobChoice-1));
        c.add(MAIN);
        
        System.out.println("\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"+MAIN.toString());
        System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\tReady? [y] ");
        scan.next();
        
    //Build World:
        //generate overworld map and insert into main display
        screen overWorld = screen.overworldGen(screenSize[0], screenSize[1]);
        mainDisplay.add(overWorld);
        
        //assemble enemy roster for overworld
        //TODO: generate roster and store in file
        ArrayList<enemy> enemies = new ArrayList();
        enemies.add(new enemy("Slime", 3, 3));
        enemies.add(new enemy("Dragon", 4, 6));
                //experimental shit I made at Roo 23...
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
        ArrayList<ArrayList<mob>> mobs = new ArrayList();
        mobs.add(new ArrayList<>());
        mobs.get(0).add(new mob(1, 4, 0));
        mobs.add(new ArrayList<>());
        mobs.get(1).add(new mob(1, 1, 1));
        
        ArrayList<ArrayList<encounterTable>> encTables = new ArrayList();
        encTables.add(new ArrayList<>());
        encTables.get(0).add(new encounterTable(0, 2));
        encTables.add(new ArrayList<>());
        encTables.get(1).add(new encounterTable(1, 1));
        
        
    //Build player character look, actions, & menus
        //player character view & position
        char[][] playerChar = new char[1][1];
        playerChar[0][0] = '@';
        screen player1 = new screen(playerChar);
        player1.setOffset(new int[] {(int)(screenSize[0]/2), (int)(screenSize[1]/2)});
                /*TODO: CHECK FOR SPAWN OBSTACLES*/
        //Initialize list ofactions
        ArrayList<option> playerActions = new ArrayList();
        //First - build main menu
        playerActions.add(new option("","q", new screen(5, 5, new option[]{
            new option("STATS","1", new screen(MAIN.viewStats(), 8, 8, new option[]{
                new option("EXIT", "1", true)
            })),
            new option("ITEMS","2",new screen(8, 8, new option[]{})),
            new option("OPTIONS","3",new screen(8, 8, new option[]{}))
        })));
        //Second - build movement
        //      note - random encounters are a consequence of overworld/dungeon movement,
        //             so their logic and triggers are built here
        playerActions.add(new option("[wasd]"));
        playerActions.get(1).setAction(
                (s) -> {
                    int[] position = new int[] {player1.getOffset()[0], player1.getOffset()[1]};
                    switch (s) {
                        case "w":
                            position[0]--;
                            if (position[0]<0) {
                                position[0]=0;
                            }
                            break;
                        case "a":
                            position[1]--;
                            if (position[1]<0) {
                                position[1]=0;
                            }
                            break;
                        case "s":
                            position[0]++;
                            if (position[0]>(mainDisplay.get(0).getView().length-1)) {
                                position[0]=(mainDisplay.get(0).getView().length-1);
                            }
                            break;
                        case "d":
                            position[1]++;
                            if (position[1]>(mainDisplay.get(0).getView()[0].length-1)) {
                                position[1]=(mainDisplay.get(0).getView()[0].length-1);
                            }
                            break;
                    }
                //Movement Conditionals:
                    //collision
                    if (mainDisplay.get(0).getView()[position[0]][position[1]]=='.'){
                        player1.setOffset(position);
                    }
                    //combat
                    if (Math.random()<0.2) {
                        
                        ArrayList<enemy> e = new ArrayList();
                        int encounterPick = utility.diceRoll(1, 3);
                        if (encounterPick==1) {
                            e.add(enemies.get(1));
                        } else {
                            for (int i = 0; i < utility.diceRoll(1, 4); i++) {
                                e.add(enemies.get(0));
                            }
                        }
                        utility.combat(mainDisplay, c, e);
                        //utility.combat(maindisplay, c, zone.encounter);
                    }
                }
        );
        //Third - object/NPC interaction
                /*TODO*/
        //Finally, add actions to player character
        player1.setActions(playerActions);
        
        //Add player to screen stack
        mainDisplay.add(player1);
        //run game
        gameLoop(mainDisplay);
    }
}