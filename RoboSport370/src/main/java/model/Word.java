package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Word {
    /**
     * A regular expression {@link String} that will trigger this word.
     */
    private String trigger;
    /**
     * The {@link ForthExecuter} that is execute when this word is triggered.
     */
    private ForthExecuter executer;
    /**
     * An array of matches to the regular expression after it is triggered.
     */
    private String[] match;

    /**
     * Creates a new {@link Word}
     * @param trigger
     *              A regular expression {@link String} that will trigger this word
     * @param executer
     *              the {@link ForthExecuter} that will execute this word when it is triggered.
     */
    public Word(final String trigger, final ForthExecuter executer){
        throw new NotImplementedException();
    }

    /**
     * Checks if a {@link String} will trigger this word
     * @param string
     *              The {@link String} to check
     * @return  {@code true} if the string is a trigger,
     *          {@code false} otherwise.
     */
    public boolean isTrigger(final String string) {
        throw new NotImplementedException();
    }

    /**
     * Executes this words {@link ForthExecuter}
     */
    public void execute(){
        throw new NotImplementedException();
    }
}

