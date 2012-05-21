package com.Altran.exampleDroolsPlanner;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.core.move.Move;
import org.drools.planner.core.score.director.ScoreDirector;

public class ValueChangeMove implements Move {
	
	private Digit digit;
	private Value toValue;

	public ValueChangeMove(Digit digit, Value toValue) {
		this.digit = digit;
		this.toValue = toValue;
	}

	public boolean isMoveDoable(ScoreDirector scoreDirector) {
		//If digit.isFixed then the value is not doable because it is an initialized value,
		//And if the current value is equal to the destination value the move is not doable,
		//And the destination value can not be "0".
		return (!digit.isFixed() 
				&& !ObjectUtils.equals(digit.getValueValue(), toValue.getValue()) 
				&& toValue.getValue() != 0);
	}

	public Move createUndoMove(ScoreDirector scoreDirector) {
		return new ValueChangeMove(digit, digit.getValue());
	}

	public void doMove(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(digit, "value"); // before changes are made
        digit.setValue(toValue);
        scoreDirector.afterVariableChanged(digit, "value"); // after changes are made

	}

	public Collection<? extends Object> getPlanningEntities() {
        return Collections.singletonList(digit);
	}

	public Collection<? extends Object> getPlanningValues() {
        return Collections.singletonList(toValue);
	}
	
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ValueChangeMove) {
            ValueChangeMove other = (ValueChangeMove) o;
            return new EqualsBuilder()
                    .append(digit, other.digit)
                    .append(toValue, other.toValue)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(digit)
                .append(toValue)
                .toHashCode();
    }

    public String toString() {
        return digit + " => " + toValue;
    }

}
