package com.Altran.exampleDroolsPlanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty;
import org.drools.planner.core.score.buildin.simple.SimpleScore;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.common.domain.AbstractPersistable;

public class NDigits extends AbstractPersistable implements Solution<SimpleScore> {

    private int n;

    // Problem facts
    private List<Column> columnList;
    private List<Row> rowList;
    private List<Block> blockList;
    private List<Value> valueList;

    // Planning entities
    private List<Digit> digitList;

    private SimpleScore score;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public List<Row> getRowList() {
        return rowList;
    }

    public void setRowList(List<Row> rowList) {
        this.rowList = rowList;
    }
    
    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }
    
    public List<Value> getValueList() {
        return valueList;
    }

    public void setValueList(List<Value> valueList) {
        this.valueList = valueList;
    }

    @PlanningEntityCollectionProperty
    public List<Digit> getDigitList() {
        return digitList;
    }

    //the name of this function "setDigitList" will automatically create a variable named "digitList"
    public void setDigitList(List<Digit> digitList) {
        this.digitList = digitList;
    }

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public Collection<? extends Object> getProblemFacts() {
        List<Object> facts = new ArrayList<Object>();
        facts.addAll(columnList);
        facts.addAll(rowList);
        facts.addAll(blockList);
        facts.addAll(valueList);
        // Do not add the planning entity's (queenList) because that will be done automatically
        return facts;
    }

    /**
     * Clone will only deep copy the {@link #queenList}.
     */
    public NDigits cloneSolution() {
    	NDigits clone = new NDigits();
        clone.id = id;
        clone.n = n;
        clone.columnList = columnList;
        clone.rowList = rowList;
        clone.blockList = blockList;
        clone.valueList = valueList;
        List<Digit> clonedDigitList = new ArrayList<Digit>(digitList.size());
        for (Digit digit : digitList) {
        	clonedDigitList.add(digit.clone());
        }
        clone.digitList = clonedDigitList;
        clone.score = score;
        return clone;
    }
    
    // ************************************************************************
    // TEST methods
    // ************************************************************************    
    
    public double moveSize(){
    	double b1 = 0,b2 = 0,b3 = 0,b4 = 0,b5 = 0,b6 = 0,b7 = 0,b8 = 0,b9 = 0;
    	for(Digit digit : this.getDigitList()){
    		if(!digit.isFixed()){
	    		switch (digit.getBlockIndex()) {
				case 0:
					b1++;
					break;
				case 1:
					b2++;
					break;
				case 2:
					b3++;
					break;
				case 3:
					b4++;
					break;
				case 4:
					b5++;
					break;
				case 5:
					b6++;
					break;
				case 6:
					b7++;
					break;
				case 7:
					b8++;
					break;
				case 8:
					b9++;
					break;
	
				default:
					break;
				}
    		}
    	}
    	/*System.out.println(b1);
    	System.out.println(b2);
    	System.out.println(b3);
    	System.out.println(b4);
    	System.out.println(b5);
    	System.out.println(b6);
    	System.out.println(b7);
    	System.out.println(b8);
    	System.out.println(b9);
    	System.out.println(this.getDigitList().size());
    	System.out.println(b1+b2+b3+b4+b5+b6+b7+b8+b9);
    	System.out.println(factorielle(b1)*
    			factorielle(b2)*
    			factorielle(b3)*
    			factorielle(b4)*
    			factorielle(b5)*
    			factorielle(b6)*
    			factorielle(b7)*
    			factorielle(b8)*
    			factorielle(b9));*/
    	return factorielle(b1)*
    			factorielle(b2)*
    			factorielle(b3)*
    			factorielle(b4)*
    			factorielle(b5)*
    			factorielle(b6)*
    			factorielle(b7)*
    			factorielle(b8)*
    			factorielle(b9);
    }
    
    public static double factorielle(double nb){
    	double res = nb;
    	if(nb != 0){
    	for (double i = nb-1; i > 0 ; --i){
    		res*= i;
    	}
    	return res;
    	}
    	else{
    		return 1;
    	}
    }

}
