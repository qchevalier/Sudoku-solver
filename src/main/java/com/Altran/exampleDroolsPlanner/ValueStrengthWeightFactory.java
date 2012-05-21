package com.Altran.exampleDroolsPlanner;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.drools.planner.api.domain.variable.PlanningValueStrengthWeightFactory;
import org.drools.planner.core.solution.Solution;

public class ValueStrengthWeightFactory implements PlanningValueStrengthWeightFactory {

    public Comparable createStrengthWeight(Solution solution, Object planningValue) {
        NDigits nDigits = (NDigits) solution;
        Value value = (Value) planningValue;
        int distanceFromMiddle = calculateDistanceFromMiddle(nDigits.getN(), value.getValue());
        return new ValueStrengthWeight(value, distanceFromMiddle);
    }

    private static int calculateDistanceFromMiddle(int n, int columnIndex) {
        int middle = n / 2;
        int distanceFromMiddle = Math.abs(columnIndex - middle);
        if ((n % 2 == 0) && (columnIndex < middle)) {
            distanceFromMiddle--;
        }
        return distanceFromMiddle;
    }

    public static class ValueStrengthWeight implements Comparable<ValueStrengthWeight> {

        private final Value value;
        private final int distanceFromMiddle;

        public ValueStrengthWeight(Value value, int distanceFromMiddle) {
            this.value = value;
            this.distanceFromMiddle = distanceFromMiddle;
        }

        public int compareTo(ValueStrengthWeight other) {
            return new CompareToBuilder()
                    // The stronger rows have a lower distance to the middle
                    .append(other.distanceFromMiddle, distanceFromMiddle) // Decreasing (but this is debatable)
                    .append(value.getValue(), other.value.getValue())
                    .toComparison();
        }

    }

}
