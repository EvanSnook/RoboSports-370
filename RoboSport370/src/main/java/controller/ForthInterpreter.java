package controller;

import model.Word;
import model.ForthExecuter;
import org.omg.CORBA.DynAnyPackage.Invalid;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Stream;


public class ForthInterpreter {
    /**
     * A list of pre-defined {@link Word}'s
     */
    private ArrayList<Word> words;
    /**
     * The stack the {@link ForthInterpreter} will alter
     */
    private Stack<String> stack;

    /**
     * Instantiate a new {@link ForthInterpreter}
     */
    public ForthInterpreter() {
        this.words = null;//initWords();
        this.stack = new Stack<>();
    }

    /**
     * Recursively call execute on each line in the {@code commandString}
     * <p>
     * Takes a {@code commandString} and isolates the next {@link Word} and performs the
     * {@link Word}'s {@link ForthExecuter}.
     *
     * @param commandString
     *              command(s) to send to the {@link ForthInterpreter}
     * @param userDefined
     *              user defined words for replacement in the {@code commandString}
     */
    public void execute(final String commandString, final HashMap<String, String> userDefined) {
        throw new NotImplementedException();
    }

    /**
     * Instantiates all pre-defined words for the {@link ForthInterpreter}.
     *
     * @return the list of {@link Word}s
     */
    private ArrayList<Word> initWords() {
        ArrayList<Word> result = new ArrayList<>();

        //<editor-fold desc="Literal Words">
        Word literalInteger = new Word("(^(\\+|-)[0-9]*)", matches -> {
            stack.push(matches[0]);
        });

        Word literalString = new Word("\\.\"([^\"]*)\"", matches -> {
            stack.push(matches[0]);
        });

        Word literalBoolean = new Word("(true|false)", matches -> {
            stack.push(matches[0]);
        });

        result.add(literalInteger);
        result.add(literalString);
        result.add(literalBoolean);
        //</editor-fold>

        //<editor-fold desc="Comments">
        Word comment = new Word("\\([\\)", matches -> {
            // Do nothing with comments
        });

        result.add(comment);
        //</editor-fold>

        //<editor-fold desc="Stack Words">
        Word stackDrop = new Word("drop", matches -> {
            if(verifyStack('v'))
                stack.pop();
        });

        Word stackDup = new Word("dup", matches -> {
            if(verifyStack('v'))
                stack.push(stack.peek());
        });

        Word stackSwap = new Word("swap", matches -> {
            if(verifyStack('v', 'v')){
                String v1 = stack.pop();
                String v2 = stack.pop();
                stack.push(v1);
                stack.push(v2);
            }
        });

        Word stackRot = new Word("rot", matches -> {
            if(verifyStack('v', 'v', 'v')){
                String v1 = stack.pop();
                String v2 = stack.pop();
                String v3 = stack.pop();
                stack.push(v3);
                stack.push(v1);
                stack.push(v2);
            }
        });

        result.add(stackDrop);
        result.add(stackDup);
        result.add(stackSwap);
        result.add(stackRot);
        //</editor-fold>

        throw new NotImplementedException();
    }

    /**
     * Verify the types of the items on the stack starting at the top.
     * <p>
     * {@code types} are as follows... <br>
     * {@code i} - integer <br>
     * {@code s} - string <br>
     * {@code b} - boolean <br>
     * {@code v} - any <br>
     * {@code l} - location
     *
     * @param types
     *              array of types that
     * @return true if all of the types match up
     */
    public boolean verifyStack(final char ... types){
        if(types.length > this.stack.size())
            return false;

        @SuppressWarnings("unchecked")
        Stack<String> temp = (Stack<String>) this.stack.clone();

        for(char current : types){
            try {
                switch (current) {
                    case 'i':
                        Integer.parseInt(temp.pop());
                        break;
                    case 's':
                        // TODO unsure how to check this
                        break;
                    case 'b':
                        Boolean.parseBoolean(temp.pop());
                        break;
                    case 'v':
                        temp.pop(); // Throws EmptyStackException if there is no item
                        break;
                    case 'l':
                        // TODO unsure how to check this
                        break;
                    default:
                        throw new InvalidParameterException(current + " is not a valid type");
                }
            } catch (EmptyStackException | InvalidParameterException | NumberFormatException ex){
                return false;
            }
        }

        return true;
    }

    public Stack<String> getStack() {
        return this.stack;
    }
}
