package com.Altran.exampleDroolsPlanner;


import org.apache.commons.lang.builder.CompareToBuilder;
import org.drools.planner.api.domain.entity.PlanningEntityDifficultyWeightFactory;
import org.drools.planner.core.solution.Solution;

public class DigitDifficultyWeightFactory implements PlanningEntityDifficultyWeightFactory {

    public Comparable createDifficultyWeight(Solution solution, Object planningEntity) {
        NDigits nDigits = (NDigits) solution;
        Digit digit = (Digit) planningEntity;
        int distanceFromMiddle = calculateDistanceFromMiddle(nDigits.getN(), digit.getColumnIndex());
        return new DigitDifficultyWeight(digit, distanceFromMiddle);
    }

    private static int calculateDistanceFromMiddle(int n, int columnIndex) {
        int middle = n / 2;
        int distanceFromMiddle = Math.abs(columnIndex - middle);
        if ((n % 2 == 0) && (columnIndex < middle)) {
            distanceFromMiddle--;
        }
        return distanceFromMiddle;
    }

    public static class DigitDifficultyWeight implements Comparable<DigitDifficultyWeight> {

        private final Digit digit;
        private final int distanceFromMiddle;

        public DigitDifficultyWeight(Digit digit, int distanceFromMiddle) {
            this.digit = digit;
            this.distanceFromMiddle = distanceFromMiddle;
        }

        public int compareTo(DigitDifficultyWeight other) {
            return new CompareToBuilder()
                    // The more difficult queens have a lower distance to the middle
                    .append(other.distanceFromMiddle, distanceFromMiddle) // Decreasing
                    .append(digit.getColumnIndex(), other.digit.getColumnIndex())
                    .toComparison();
        }

    }

}
