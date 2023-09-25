package AdventureDemo;

import java.util.ArrayList;

public class option {
    private String name = "";
    private String regex;
    private screen nextView;
    private Action action;
    private boolean hasNextView = false;
    private boolean hasAction = false;
    private boolean exitView = false;
    
    //CONSTRUCTORS
    
    public option(String regex) {
        this.regex = regex;
        this.action = (s) -> System.err.println(s+" -> Action not set!");
    }
    
    public option(String name, String regex) {
        this.name = name;
        this.regex = regex;
        this.action = (s) -> System.err.println(s+" -> Action not set!");
    }
    
    public option(String name, String regex, Action action) {
        this.name = name;
        this.regex = regex;
        this.setAction(action);
    }

    public option(String name, String regex, screen nextView) {
        this.name = name;
        this.regex = regex;
        this.action = (s) -> System.err.println(s+" -> Action not set!");
        this.setNextView(nextView);
    }
    
    public option(String name, String regex, boolean exitView) {
        this.name = name;
        this.regex = regex;
        this.action = (s) -> System.err.println(s+" -> Action not set!");
        this.exitView = exitView;
    }

    //GETTERS

    public String getName() {
        return name;
    }
    
    public String getRegex() {
        return regex;
    }

    public screen getNextView() {
        return nextView;
    }

    public boolean HasNextView() {
        return hasNextView;
    }

    public boolean HasAction() {
        return hasAction;
    }

    public boolean HasExitView() {
        return exitView;
    }
    
    //SETTERS

    public void setName(String name) {
        this.name = name;
    }
    
    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setNextView(screen nextView) {
        this.nextView = nextView;
        this.hasNextView = true;
    }

    public void setAction(Action action) {
        this.action = action;
        this.hasAction = true;
    }

    public void setExitView(boolean exitView) {
        this.exitView = exitView;
    }
    
    //OTHER METHODS
    
    public static String getRegEx(ArrayList<option> options) {
        String string = "";
        string = options.stream().map((option) -> (option.regex + "|")).reduce(string, String::concat);
        string = string.substring(0, string.length()-1);
        return string;
    }
    
    public void runAction(String s) {
        this.action.run(s);
    }
    
    public interface Action {
        void run(String str);
    }
}
