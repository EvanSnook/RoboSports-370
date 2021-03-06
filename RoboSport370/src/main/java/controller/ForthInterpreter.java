package controller;

import sun.plugin.dom.exception.InvalidStateException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;

import model.ForthExecuter;
import model.HexNode;
import model.HexNodeIterator;
import model.Robot;
import model.RobotAI;
import model.Team;
import model.Word;
import model.enums.RobotType;
import model.enums.TeamColour;

public class ForthInterpreter {
    /**
     * A list of pre-defined {@link Word}'s
     */
    private ArrayList<Word> words;

    /**
     * The stack the {@link ForthInterpreter} will alter
     */
    private Stack<String> stack;

    private GameMaster gameMaster;
    private RobotAI currentInitRobot;

    /**
     * Instantiate a new {@link ForthInterpreter}
     */
    public ForthInterpreter(GameMaster gameMaster) {
        this.words = initWords();
        this.stack = new Stack<>();
        this.gameMaster = gameMaster;
        initRobots();
    }

    private void initRobots(){
        gameMaster.getGame().getTeams().stream()
                .filter(Team::isAI)
                .forEach(t -> {
                    currentInitRobot = (RobotAI) t.getScout();
                    this.execute(currentInitRobot.getCode());
                    currentInitRobot = (RobotAI) t.getSniper();
                    this.execute(currentInitRobot.getCode());
                    currentInitRobot = (RobotAI) t.getTank();
                    this.execute(currentInitRobot.getCode());
                });
    }

    /**
     * Recursively call execute on each line in the {@code commandString}
     * <p>
     * Takes a {@code commandString} and isolates the next {@link Word} and performs the
     * {@link Word}'s {@link ForthExecuter}.
     *
     * @param commandString command(s) to send to the {@link ForthInterpreter}
     */
    public void execute(String commandString) {
        commandString = commandString.replaceAll(" pop ", " . ");
        // Remove starting spaces
        commandString = commandString.replaceFirst(" *", "");
        // Remove comments
        String finalCommandString = commandString.replaceAll("\\(.*?\\)", "");


        Optional<Word> w = words.stream()
                .filter(word -> word.isTrigger(finalCommandString))
                .findFirst();

        String[] splitCommand = finalCommandString.split(" ");
        String nextCommand;

        if (w.isPresent()) { // If a word is defined
            w.get().execute();
            nextCommand = finalCommandString.replaceFirst(w.get().getTrigger(), "");
        } else if(isVariable(splitCommand[0])){ // If the next word is a Variable
            // Push the word on the stack
            stack.push(splitCommand[0]);

            String[] ignoreFirstElement = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);
            nextCommand = String.join(" ", (CharSequence[]) ignoreFirstElement);
        } else if(isDefinedWord(splitCommand[0])){ // If the next word is User Defined
            String userWord = getCurrentRobot().getUserDefinedWords().get(splitCommand[0]);
            execute(userWord);

            String[] ignoreFirstElement = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);
            nextCommand = String.join(" ", (CharSequence[]) ignoreFirstElement);
        } else { // If we reached the end or there is an error
            if (!finalCommandString.isEmpty())
                System.err.println("Parsing Error: " + finalCommandString);
            return;
        }


        // Remove excessive spaces between words
//        nextCommand = finalCommandString.replaceFirst(w.get().getTrigger() + " *", "");
        if(nextCommand != null)
            execute(nextCommand);
    }

    private boolean isVariable(String s){
        return getCurrentRobot().getUserDefinedVariables().containsKey(s);
    }

    private boolean isDefinedWord(String s){
        return getCurrentRobot().getUserDefinedWords().containsKey(s);
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
        Word literalInteger = new Word("((\\+|-)?[0-9]{1,})", matches -> {
            stack.push(matches[1]);
        });

        /*
         * Strings: written as sequences of characters (including whitespace) initiated
         * by the special token ." and ended by the next "
         */
        Word literalString = new Word("\\.\"([^\"]*)\"", matches -> {
            stack.push(matches[1]);
        });

        /*
         * Booleans: written as the special tokens, true and false
         */
        Word literalBoolean = new Word("(true|false)", matches -> {
            stack.push(matches[1]);
        });

        result.add(literalInteger);
        result.add(literalString);
        result.add(literalBoolean);
        //</editor-fold>

        //<editor-fold desc="Defining Words">
        Word definingWord = new Word(": ((.*?) )(.*?);", matches -> {
            getCurrentRobot().getUserDefinedWords().put(matches[2], matches[3]);
        });

        // TODO detect when the definedwords are being used

        result.add(definingWord);
        //</editor-fold>

        //<editor-fold desc="Comments">
        Word comment = new Word("\\(.*\\)", matches -> {
            // Do nothing with comments
        });

        result.add(comment);
        //</editor-fold>

        //<editor-fold desc="Stack Words">

        // drop ( v -- ) —remove the value at the top of the stack
        Word stackDrop = new Word("drop", matches -> {
            if (verifyStack('v'))
                stack.pop();
        });

        // dup ( v -- v v ) —duplicate the value at the top of the stack
        Word stackDup = new Word("dup", matches -> {
            if (verifyStack('v'))
                stack.push(stack.peek());
        });

        // swap ( v2 v1 -- v2 v1 ) —swap the two values at the top of the stack
        Word stackSwap = new Word("swap", matches -> {
            if (verifyStack('v', 'v')) {
                String v1 = stack.pop();
                String v2 = stack.pop();
                stack.push(v1);
                stack.push(v2);
            }
        });

        // rot ( v3 v2 v1 -- v3 v1 v2 ) —rotate the top three stack elements
        Word stackRot = new Word("rot", matches -> {
            if (verifyStack('v', 'v', 'v')) {
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
        Word stackAdd = new Word("\\+", matches -> {
            if (verifyStack('i', 'i')) {
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
        Word stackSubtract = new Word("-", matches -> {
            if (verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                int subtractOutput = i2 - i1;
                stack.push(String.valueOf(subtractOutput));
            }
        });

        /*
         * * ( i i -- i ) —multiply the two top integers, pushing their product on the stack
         */
        Word stackMultiply = new Word("\\*", matches -> {
            if (verifyStack('i', 'i')) {
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
        Word stackMod = new Word("/mod", matches -> {
            if (verifyStack('i', 'i')) {
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
        Word compareLT = new Word("<", matches -> {
            if (verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 < i1));
            }
        });

        // <= ( i2 i1 -- b ) —i2 is not more than i1
        Word compareLTE = new Word("<=", matches -> {
            if (verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 <= i1));
            }
        });

        // = ( v2 v1 -- b ) —v2 equals v1
        Word compareE = new Word("=", matches -> {
            String v1 = stack.pop();
            String v2 = stack.pop();
            stack.push(String.valueOf(v1.equals(v2)));
        });

        // <> ( v2 v1 -- b ) —v2 is different from v1
        Word compareNE = new Word("<>", matches -> {
            String v1 = stack.pop();
            String v2 = stack.pop();
            stack.push(String.valueOf(!v1.equals(v2)));
        });

        // => ( i2 i1 -- b ) —i2 is at least i1
        Word compareGTE = new Word("=>", matches -> {
            if (verifyStack('i', 'i')) {
                int i1 = Integer.valueOf(stack.pop());
                int i2 = Integer.valueOf(stack.pop());
                stack.push(String.valueOf(i2 >= i1));
            }
        });

        // > ( i2 i1 -- b ) —i2 is more than i1
        Word compareGT = new Word(">", matches -> {
            if (verifyStack('i', 'i')) {
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
        Word logicAnd = new Word("and", matches -> {
            if (verifyStack('b', 'b')) {
                boolean b1 = Boolean.valueOf(stack.pop());
                boolean b2 = Boolean.valueOf(stack.pop());
                stack.push(String.valueOf(b1 == b2));
            }
        });

        // or ( b b -- b ) —true if either boolean is true, false otherwise
        Word logicOr = new Word("or", matches -> {
            if (verifyStack('b', 'b')) {
                boolean b1 = Boolean.valueOf(stack.pop());
                boolean b2 = Boolean.valueOf(stack.pop());
                stack.push(String.valueOf(b1 != b2));
            }
        });

        // invert ( b -- b ) —invert the given boolean
        Word logicInvert = new Word("invert", matches -> {
            if (verifyStack('b')) {
                boolean b = Boolean.valueOf(stack.pop());
                stack.push(String.valueOf(!b));
            }
        });

        // if .. else .. then
        Word logicIfElseThen = new Word("if (.*?) else (.*?) then", matches -> {
            if (verifyStack('b')) {
                boolean boolCheck = Boolean.valueOf(stack.pop());
                if (boolCheck)
                    execute(matches[1]);
                else
                    execute(matches[2]);
            }
        });

        result.add(logicAnd);
        result.add(logicOr);
        result.add(logicInvert);
        result.add(logicIfElseThen);
        //</editor-fold>

        //<editor-fold desc="Loops">
        // ( iStart iEnd -- ) do BODY loop
        Word loopFor = new Word("do( .* )loop( ;)?", matches -> {
            if (verifyStack('i', 'i')) {
                int iEnd = Integer.valueOf(stack.pop());
                int iStart = Integer.valueOf(stack.pop());
                String loopBody = matches[1];

                for (int i = iStart; i <= iEnd; i++) {
                    execute(nestedForLoop(loopBody, i).trim());
                }
            }
        });

        // begin BODY until
        Word loopWhile = new Word("begin (.*) until", matches -> {
            String loopBody = matches[1];
            do {
                execute(loopBody);
                if (stack.empty() || !verifyStack('b'))
                    throw new InvalidStateException("No boolean found on stack for begin .. until loop!");
            } while (Boolean.valueOf(stack.pop()));
        });

        result.add(loopFor);
        result.add(loopWhile);
        //</editor-fold>

        //<editor-fold desc="Variables">
        Word variableDefine = new Word("variable ([^ ]{1,})", (matches -> {
            getCurrentRobot().getUserDefinedVariables().put(matches[1], "0");
        }));

        Word variableUsage = new Word("(\\?|!)", (matches -> {
            String action = matches[1];

            if(action.equals("!")){
                // Push new value
                if(verifyStack('v', 'l')){
                    String newValue = stack.pop();
                    String variable = stack.pop();

                    getCurrentRobot().getUserDefinedVariables().put(variable, newValue);
                }
            } else if(action.equals("?")){
                // Pop new value
                if(verifyStack('l')) {
                    String variable = stack.pop();
                    stack.push(getCurrentRobot().getUserDefinedVariables().get(variable));
                }
            }
        }));

        result.add(variableDefine);
        result.add(variableUsage);
        //</editor-fold>

        //<editor-fold desc="Miscellanea">
        Word miscPrint = new Word("\\.", (matches -> {
            if (verifyStack('v'))
                System.out.println(stack.pop());
        }));

        Word miscRandom = new Word("random", (matches -> {
            if (verifyStack('i')) {
                int i1 = Integer.valueOf(stack.pop()) + 1;
                int random = new Random().nextInt(i1);
                stack.push(String.valueOf(random));
            }
        }));

        Word miscSemicolon = new Word(";", (matches -> {
            // Do nuffin
        }));

        result.add(miscPrint);
        result.add(miscRandom);
        result.add(miscSemicolon);
        //</editor-fold>

        //<editor-fold desc="Robot Status">
        Word robotHealth = new Word("health", matches -> {
            int maxHealth = gameMaster.getCurrentRobot().getMaxHealth();
            stack.push(String.valueOf(maxHealth));
        });

        Word robotHealthLeft = new Word("healthLeft", matches -> {
            int health = gameMaster.getCurrentRobot().getHealth();
            stack.push(String.valueOf(health));
        });

        Word robotMoves = new Word("moves", matches -> {
            int maxMoves = gameMaster.getCurrentRobot().getMaxMove();
            stack.push(String.valueOf(maxMoves));
        });

        Word robotMovesLeft = new Word("movesLeft", matches -> {
            int remainingMoves = gameMaster.getCurrentRobot().getRemainingMoves();
            stack.push(String.valueOf(remainingMoves));
        });

        Word robotAttack = new Word("attack", matches -> {
            int damage = gameMaster.getCurrentRobot().getDamage();
            stack.push(String.valueOf(damage));
        });

        Word robotRange = new Word("range", matches -> {
            int range = gameMaster.getCurrentRobot().getRange();
            stack.push(String.valueOf(range));
        });

        Word robotTeam = new Word("team", matches -> {
            TeamColour team = gameMaster.getCurrentRobot().getColour();
            stack.push(String.valueOf(team));
        });

        Word robotType = new Word("type", matches -> {
            RobotType type = gameMaster.getCurrentRobot().getType();
            stack.push(String.valueOf(type));
        });

        result.add(robotHealth);
        result.add(robotHealthLeft);
        result.add(robotMoves);
        result.add(robotMovesLeft);
        result.add(robotAttack);
        result.add(robotRange);
        result.add(robotTeam);
        result.add(robotType);
        //</editor-fold>

        //<editor-fold desc="Robot Actions">
        // Turn 1 to the right
        Word robotTurn = new Word("turn!", matches -> {
            gameMaster.setRobotFacing(
                    (getCurrentRobot().getFacing() + 1) % 6
            );
        });

        // Move forwa
        // rd 1 space
        Word robotMove = new Word("move!", matches -> {
            gameMaster.robotMove();
        });


        // shoot! ( id ir -- ) —fires the robot’s weapon at the space which is
        // at distance ir and direction id
        Word robotShoot = new Word("shoot!", matches -> {
            if (verifyStack('i', 'i')) {
                // Distance
                int ir = Integer.valueOf(stack.pop());
                // Index
                int id = Integer.valueOf(stack.pop());

                HexNodeIterator iter =
                        new HexNodeIterator(
                                getCurrentRobot().getPosition(), getCurrentRobot().getFacing());

                iter.go(ir, id);

                gameMaster.robotShoot(iter.getCurrentNode());
            }
        });

        // check! ( i -- s ) pops a given direction, and pushes a string describing
        // the adjacent space in that direction
        Word robotCheck = new Word("check!", matches -> {
            if (verifyStack('i')) {
                int i = Integer.valueOf(stack.pop());

                RobotAI robot = getCurrentRobot();
                int globalFacing = (i + robot.getFacing()) % 6;

                HexNode checkPosition = robot.getPosition().get(globalFacing);

                if (!checkPosition.canContainRobots())
                    stack.push("OUT OF BOUNDS");
                else if (!checkPosition.isEmpty())
                    stack.push("OCCUPIED");
                else
                    stack.push("EMPTY");
            }
        });

        Word robotScan = new Word("scan!", matches -> {
            int range = getCurrentRobot().getRange();
            int count = 0;

            HexNodeIterator iterator = new HexNodeIterator(getCurrentRobot().getPosition());

            while (iterator.hasNext() && iterator.getCurrentLayer() <= range) {
                HexNode current = iterator.next();

                if (!current.isEmpty())
                    count += current.getRobots().size();
            }

            count--; // remove yourself

            stack.push(String.valueOf(count));
        });

        Word robotIdentify = new Word("identify!", matches -> {
            if (verifyStack('i')) {
                int i1 = Integer.valueOf(stack.pop());
                Robot robot = null;

                HexNodeIterator iterator = new HexNodeIterator(getCurrentRobot().getPosition());

                int cur = -1;
                while (iterator.hasNext() && robot == null) {
                    HexNode current = iterator.next();

                    if (!current.canContainRobots())
                        continue;

                    for (Robot r : current.getRobots()) {
                        if(!r.equals(getCurrentRobot()))
                            cur++;
                        if (cur == i1) {
                            robot = r;
                            break;
                        }
                    }
                }

                if (robot == null) {
                    System.err.println("Could not identify robot at specified index");
                    return;
                }

                stack.push(String.valueOf(robot.getHealth()));
                stack.push(String.valueOf(iterator.getCurrentIndex()));
                stack.push(String.valueOf(iterator.getCurrentLayer()));
                stack.push(robot.getColour().toString());
            }
        });

        result.add(robotShoot);
        result.add(robotTurn);
        result.add(robotMove);
        result.add(robotCheck);
        result.add(robotScan);
        result.add(robotIdentify);
        //</editor-fold>

        return result;
    }

    /**
     * Get the current robot making their turn
     *
     * @return the current robot
     */
    private RobotAI getCurrentRobot() {
        if(gameMaster.getCurrentRobot() == null){
            return currentInitRobot;
        }
        return (RobotAI) gameMaster.getCurrentRobot();
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
     * @param types array of types that
     * @return true if all of the types match up
     */
    public boolean verifyStack(final char... types) {
        if (types.length > this.stack.size())
            return false;

        @SuppressWarnings("unchecked")
        Stack<String> temp = (Stack<String>) this.stack.clone();

        for (char current : types) {
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
                        return getCurrentRobot().getUserDefinedVariables().containsKey(temp.pop());
                    default:
                        throw new InvalidParameterException(current + " is not a valid type");
                }
            } catch (EmptyStackException | InvalidParameterException | NumberFormatException ex) {
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
     * @param s        the body of a for loop
     * @param interval the current interval of the for loop
     * @return a {@link String} with all of the I's replaced
     */
    private String nestedForLoop(String s, int interval) {
        StringBuilder result = new StringBuilder();
        String replaceWith = String.valueOf(interval);

        ArrayList<Integer> doLocations = new ArrayList<>();
        ArrayList<Integer> loopLocations = new ArrayList<>();

        // Get indexes where every "do" is located
        {
            String temp = s;
            while (temp.matches(".*? do .*")) {
                int index = temp.indexOf(" do ") + 1; // Start of do word
                temp = temp.replaceFirst(" do ", " -- ");
                doLocations.add(index);
            }
        }

        // Get indexes where every "loop" is located
        {
            String temp = s;
            while (temp.matches(".*? loop .*")) {
                int index = temp.indexOf(" loop ") + 5; // End of loop word
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
                if (doLocations.contains(i))
                    innerLoopLayer++;
                else if (loopLocations.contains(i))
                    innerLoopLayer--;

                loopLayersAtIndex[i] = innerLoopLayer;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            if (loopLayersAtIndex[i] == 0) {
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
