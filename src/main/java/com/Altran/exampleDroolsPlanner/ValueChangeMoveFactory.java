package com.Altran.exampleDroolsPlanner;

import java.util.ArrayList;
import java.util.List;

import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.CachedMoveFactory;
import org.drools.planner.core.solution.Solution;

public class ValueChangeMoveFactory extends CachedMoveFactory{

	@Override
	public List<Move> createCachedMoveList(Solution solution) {
        NDigits nDigits = (NDigits) solution;
        List<Move> moveList = new ArrayList<Move>();
        for (Digit digit : nDigits.getDigitList()) {
            for (Value toValue : nDigits.getValueList()) {
                moveList.add(new ValueChangeMove(digit, toValue));
            }
        }
        return moveList;
	}

}
