package com.Altran.exampleDroolsPlanner;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.drools.planner.examples.common.domain.AbstractPersistable;

@XStreamAlias("Value")
public class Value extends AbstractPersistable {

    private int value;
    private List<Value> possibleValue;

    public Value(int value) {
    	this.value = value;
	}

	public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "value" + value;
    }

    public List<Value> getPossibleValue(){
    	possibleValue = new ArrayList<Value>();
    	for (int i = 1; i < 10; i++){
    		possibleValue.add(new Value(i));
    	}
    	return possibleValue;
    }

}
