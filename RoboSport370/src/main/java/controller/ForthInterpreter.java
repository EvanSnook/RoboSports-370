package controller;

import sun.plugin.dom.exception.InvalidStateException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import model.ForthExecuter;
import model.Word;

public class ForthInterpreter {

    public static void main(String[] args) {
        HashMap<String, String> definedWords = new HashMap<>();

        ForthInterpreter interpreter = new ForthInterpreter();
        interpreter.execute("1 3 do 1 2 do I loop I loop", definedWords);

        System.out.println("stack: " + interpreter.getStack());
        System.out.println("definedWords: " + definedWords);
    }

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
        this.words = initWords();
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
    public void execute(final String commandString, HashMap<String, String> userDefined) {
        Optional<Word> w = words.stream()
                .filter(word -> word.isTrigger(commandString))
                .findFirst();

        if(!w.isPresent()) {
            if(!commandString.isEmpty())
                System.err.println("Parsing Error: " + commandString);
            return;
        }

        w.get().execute(userDefined);

        String nextCommand = commandString.replaceFirst(w.get().getTrigger() + " *", "");
        execute(nextCommand, userDefined);
    }

    /**
     * Instantiates all pre-defined words for the {@link ForthInterpreter}.
     *
     * @return the list of {@link Word}s
     */
    private ArrayList<Word> initWords() {
        ArrayList<Word> result = new ArrayList<>();

        //<editor-fold desc="Literal Words">

        /*
         * Integers: written as decimal numbers (with or without a leading sign (+ or -)
         */
        Word literalInteger = new Word("((\\+|-)?[0-9]{1,})", (userDefined, matches) -> {
            stack.push(matches[1]);
        });

        /*
         * Strings: written as sequences of characters (including whitespace) initiated
         * by the special token ." and ended by the next "
         */
        Word literalString = new Word("\\.\"([^\"]*)\"", (userDefined, matches) -> {
            stack.push(matches[1]);
        });

        /*
         * Booleans: written as the special tokens, true and false
         */
        Word literalBoolean = new Word("(true|false)", (userDefined, matches) -> {
            stack.push(matches[1]);
        });

        result.add(literalInteger);
        result.add(literalString);
        result.add(literalBoolean);
        //</editor-fold>

        //<editor-fold desc="Defining Words">
        Word definingWord = new Word(": ((.*?) )(.*?) ;", (userDefined, matches) -> {
            userDefined.put(matches[2], matches[3]);
        });

        // TODO detect when the definedwords are being used

        result.add(definingWord);
        //</editor-fold>

        //<editor-fold desc="Comments">
        Word comment = new Word("\\(.*\\)", (userDefined, matches) -> {
            // Do nothing with comments
        });

        result.add(comment);
        //</editor-fold>

        //<editor-fold desc="Stack Words">

        // drop ( v -- ) —remove the value at the top of the stack
        Word stackDrop = new Word("drop", (userDefined, matches) -> {
            if(verifyStack('v'))
                stack.pop();
        });

        // dup ( v -- v v ) —duplicate the value at the top of the stack
        Word stackDup = new Word("dup", (userDefined, matches) -> {
            if(verifyStack('v'))
                stack.push(stack.peek());
        });

        // swap ( v2 v1 -- v2 v1 ) —swap the two values at the top of the stack
        Word stackSwap = new Word("swap", (userDefined, matches) -> {
            if(verifyStack('v', 'v')){
                String v1 = stack.pop();
                String v2 = stack.pop();
                stack.push(v1);
                stack.push(v2);
            }
        });

        // rot ( v3 v2 v1 -- v3 v1 v2 ) —rotate the top three stack elements
        Word stackRot = new Word("rot", (userDefined, matches) -> {
            if(verifyStack('v', 'v', 'v')){
                String v1 = stack.pop();
                String v2 = stack.pop();
                String v3 = stack.pop();
                stack.push(v2);
                stack.push(v1);
                stack.push(v3);
            }
        });

        result.add(stackDrop);
        result.add(stackDup);
        result.add(stackSwap);
        result.add(stackRot);
        //</editor-fold>

        //<editor-fold desc="Arithmetic">

        /*
         * + ( i i -- i) —add the two integers, pushing their sum on the stack
         */
        Word stackAdd = new Word("\\+", (userDefined, matches) -> {
            if(verifyStack('i', 'i')){
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                int addOutput = i1 + i2;
                stack.push(String.valueOf(addOutput));
            }
        });

        /*
         * - ( i2 i1 -- i ) —subtract the top integer from the next, pushing their
         * difference (i2-i1) on the stack
         */
        Word stackSubtract = new Word("-", (userDefined, matches) -> {
            if(verifyStack('i', 'i')){
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                int subtractOutput = i2 - i1;
                stack.push(String.valueOf(subtractOutput));
            }
        });

        /*
         * * ( i i -- i ) —multiply the two top integers, pushing their product on the stack
         */
        Word stackMultiply = new Word("\\*", (userDefined, matches) -> {
            if(verifyStack('i', 'i')){
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                int multiplyOutput = i2 * i1;
                stack.push(String.valueOf(multiplyOutput));
            }
        });

        /*
         * /mod ( iv ie -- iq ir) —divide the top integer into the next, pushing
         * the remainder and quotient
         */
        Word stackMod = new Word("/mod", (userDefined, matches) -> {
            if(verifyStack('i', 'i')){
                int ie = Integer.valueOf(stack.pop());
                int iv = Integer.valueOf(stack.pop());
                int iq = Math.floorDiv(iv, ie);
                int ir = iv % ie;
                stack.push(String.valueOf(ir));
                stack.push(String.valueOf(iq));
            }
        });

        result.add(stackAdd);
        result.add(stackSubtract);
        result.add(stackMultiply);
        result.add(stackMod);
        //</editor-fold>

        //<editor-fold desc="Comparison">
        // < ( i2 i1 -- b ) —i2 is less than i1
        Word compareLT = new Word("<", (userDefined, matches) -> {
            if(verifyStack('i', 'i')){
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 < i1));
            }
        });

        // <= ( i2 i1 -- b ) —i2 is not more than i1
        Word compareLTE = new Word("<=", (userDefined, matches) -> {
            if(verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 <= i1));
            }
        });

        // = ( v2 v1 -- b ) —v2 equals v1
        Word compareE = new Word("=", (userDefined, matches) -> {
            String v1 = stack.pop();
            String v2 = stack.pop();
            stack.push(String.valueOf(v1.equals(v2)));
        });

        // <> ( v2 v1 -- b ) —v2 is different from v1
        Word compareNE = new Word("<>", (userDefined, matches) -> {
            String v1 = stack.pop();
            String v2 = stack.pop();
            stack.push(String.valueOf(!v1.equals(v2)));
        });

        // => ( i2 i1 -- b ) —i2 is at least i1
        Word compareGTE = new Word("=>", (userDefined, matches) -> {
            if(verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 >= i1));
            }
        });

        // > ( i2 i1 -- b ) —i2 is more than i1
        Word compareGT = new Word(">", (userDefined, matches) -> {
            if(verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 > i1));
            }
        });

        result.add(compareLT);
        result.add(compareLTE);
        result.add(compareE);
        result.add(compareNE);
        result.add(compareGTE);
        result.add(compareGT);
        //</editor-fold>

        //<editor-fold desc="Logic and Control">

        // and ( b b -- b ) —false if either boolean is false, true otherwise
        Word logicAnd = new Word("and", (userDefined, matches) -> {
            if(verifyStack('b', 'b')){
                boolean b1 = Boolean.valueOf(stack.pop());
                boolean b2 = Boolean.valueOf(stack.pop());
                stack.push(String.valueOf(b1 == b2));
            }
        });

        // or ( b b -- b ) —true if either boolean is true, false otherwise
        Word logicOr = new Word("or", (userDefined, matches) -> {
            if(verifyStack('b', 'b')){
                boolean b1 = Boolean.valueOf(stack.pop());
                boolean b2 = Boolean.valueOf(stack.pop());
                stack.push(String.valueOf(b1 != b2));
            }
        });

        // invert ( b -- b ) —invert the given boolean
        Word logicInvert = new Word("invert", (userDefined, matches) -> {
            if(verifyStack('b')){
                boolean b = Boolean.valueOf(stack.pop());
                stack.push(String.valueOf(!b));
            }
        });

        // if .. else .. then
        Word logicIfElseThen = new Word("if (.*?) else (.*?) then", (userDefined, matches) -> {
            if(verifyStack('b')){
                boolean boolCheck = Boolean.valueOf(stack.pop());
                Arrays.stream(matches).forEach(System.out::println);
                if(boolCheck)
                    execute(matches[1], userDefined);
                else
                    execute(matches[2], userDefined);
            }
        });

        result.add(logicAnd);
        result.add(logicOr);
        result.add(logicInvert);
        result.add(logicIfElseThen);
        //</editor-fold>

        //<editor-fold desc="Loops">
        // ( iStart iEnd -- ) do BODY loop
        Word loopFor = new Word("do( .* )loop( ;)?", (userDefined, matches) -> {
            if(verifyStack('i', 'i')){
                int iEnd = Integer.valueOf(stack.pop());
                int iStart = Integer.valueOf(stack.pop());
                String loopBody = matches[1];

                for(int i=iStart; i<=iEnd; i++){
                    execute(nestedForLoop(loopBody, i).trim(), userDefined);
                }
            }
        });

        // begin BODY until
        Word loopWhile = new Word("begin (.*) until", (userDefined, matches) -> {
            String loopBody = matches[1];
            do{
                execute(loopBody, null);
                if(stack.empty() || !verifyStack('b'))
                    throw new InvalidStateException("No boolean found on stack for begin .. until loop!");
            } while(Boolean.valueOf(stack.pop()));
        });

        result.add(loopFor);
        result.add(loopWhile);
        //</editor-fold>

        //<editor-fold desc="Variables">
        Word variable = new Word("variable (.*) ;", ((userDefinedWords, matches) -> {
            // TODO forth variables
            // ? Add variable hashmap to RobotAI & pass RobotAI instead of userDefinedWords
        }));
        //</editor-fold>

        //<editor-fold desc="Miscellanea">
        Word miscPrint = new Word("\\.", ((userDefinedWords, matches) -> {
            if(verifyStack('v'))
                System.out.println(stack.pop());
        }));

        Word miscRandom = new Word("random", ((userDefinedWords, matches) -> {
            if(verifyStack('i')) {
                int i1 = Integer.valueOf(stack.pop()) + 1;
                int random = new Random().nextInt(i1);
                stack.push(String.valueOf(random));
            }
        }));

        result.add(miscPrint);
        result.add(miscRandom);
        //</editor-fold>

        return result;
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
                        // Everythings a string! ha
                        System.err.println("verifyStack always true for strings");
                        break;
                    case 'b':
                        Boolean.parseBoolean(temp.pop());
                        break;
                    case 'v':
                        temp.pop(); // Throws EmptyStackException if there is no item
                        break;
                    case 'l':
                        System.err.println("verifyStack always true for locations");
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

    /**
     * A helper function for {@code do ... loop}s in forth
     * <p>
     * This function takes in the body of a loop and replaces all of the
     * occurrences of {@code I} in the string, leaving any I's that are in
     * nested loops within the body.
     *
     * @param s
     *              the body of a for loop
     * @param interval
     *              the current interval of the for loop
     * @return a {@link String} with all of the I's replaced
     */
    private String nestedForLoop(String s, int interval){
        StringBuilder result = new StringBuilder();
        String replaceWith = String.valueOf(interval);

        ArrayList<Integer> doLocations = new ArrayList<>();
        ArrayList<Integer> loopLocations = new ArrayList<>();

        // Get indexes where every "do" is located
        {
            String temp = s;
            while(temp.matches(".*? do .*")){
                int index = temp.indexOf(" do ")+1; // Start of do word
                temp = temp.replaceFirst(" do ", " -- ");
                doLocations.add(index);
            }
        }

        // Get indexes where every "loop" is located
        {
            String temp = s;
            while(temp.matches(".*? loop .*")){
                int index = temp.indexOf(" loop ")+5; // End of loop word
                temp = temp.replaceFirst(" loop ", " ---- ");
                loopLocations.add(index);
            }
        }

        // Layer of the loops at a given index
        // If the layer is 0 it is not in an inner loop
        int loopLayersAtIndex[] = new int[s.length()];
        {
            int innerLoopLayer = 0;

            for (int i = 0; i < s.length(); i++) {
                if(doLocations.contains(i))
                    innerLoopLayer++;
                else if(loopLocations.contains(i))
                    innerLoopLayer--;

                loopLayersAtIndex[i] = innerLoopLayer;
            }
        }

        for(int i=0; i<s.length(); i++) {
            if (loopLayersAtIndex[i] == 0){
                if (s.charAt(i) == 'I') {
                    result.append(replaceWith);
                    continue;
                }
            }
            result.append(s.charAt(i));
        }

        return result.toString();
    }

    public Stack<String> getStack() {
        return this.stack;
    }
}
