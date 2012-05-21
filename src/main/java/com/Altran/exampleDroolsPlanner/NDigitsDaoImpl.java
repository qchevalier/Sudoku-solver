package com.Altran.exampleDroolsPlanner;

import org.drools.planner.examples.common.persistence.XStreamSolutionDaoImpl;

public class NDigitsDaoImpl extends XStreamSolutionDaoImpl {

    public NDigitsDaoImpl() {
        super("ndigits", NDigits.class);
    }

}