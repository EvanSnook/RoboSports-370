package controller;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import model.Word;

public class ForthInterpreter {
    /** A list of pre-defined {@code Word}'s */
    private ArrayList<Word> words;
    /** The stack the {@code ForthInterpreter} will alter */
    private Stack<String> stack;

    /** Instantiate a new {@code ForthInterpreter} */
    public ForthInterpreter() {
        throw new NotImplementedException();
    }

    /**
     * Recursively call execute on each line in the {@code commandString}
     *
     * <p>Takes a {@code commandString} and isolates the next {@code Word}
     * and performs the {@code Word}'s {@code ForthExecuter}.
     *
     * @param commandString
     *              command(s) to send to the {@code ForthInterpreter}
     * @param userDefined
     *              user defined words for replacement in the {@code commandString}
     */
    public void execute(final String commandString, final HashMap<String, String> userDefined){
        throw new NotImplementedException();
    }

    /**
     * Instantiates all pre-defined words for the {@code ForthInterpreter}.
     * @return the list of {@code Word}'s
     */
    private ArrayList<Word> initWords(){
        throw new NotImplementedException();
    }

    public Stack<String> getStack(){
        throw new NotImplementedException();
    }
}
