package com.Altran.exampleDroolsPlanner;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.drools.planner.examples.common.domain.AbstractPersistable;

@XStreamAlias("Block")
public class Block extends AbstractPersistable {

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "block" + index;
    }

}