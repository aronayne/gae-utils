package com.strategy;
import java.util.Map;


public class Context {
 
    private ParseStrategy strategy;

    public Context(ParseStrategy strategy) {
        this.strategy = strategy;
    }
 
    public Map<String , String> executeStrategy() {
        return strategy.execute();
    }
    
}
