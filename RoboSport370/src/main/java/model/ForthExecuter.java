package model;


import java.util.HashMap;

@FunctionalInterface
public interface ForthExecuter {
    void executeForth(HashMap<String, String> userDefinedWords, String... matches);
}
