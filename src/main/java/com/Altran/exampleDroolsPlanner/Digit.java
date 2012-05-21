package com.Altran.exampleDroolsPlanner;

import java.util.List;

import org.drools.planner.api.domain.entity.PlanningEntity;
import org.drools.planner.api.domain.variable.PlanningVariable;
import org.drools.planner.api.domain.variable.ValueRange;
import org.drools.planner.api.domain.variable.ValueRangeType;
import org.drools.planner.examples.common.domain.AbstractPersistable;

//(difficultyWeightFactoryClass = DigitDifficultyWeightFactory.class)
@PlanningEntity
public class Digit extends AbstractPersistable {

	
    private Column column;
    private Row row;
    private Block block;
    private boolean isFixed = false;
    
    // Planning variables: changes during planning, between score calculations.
    private Value value;
    
    public boolean isFixed() {
        return isFixed;
    }

    public void setInitialisedValue(boolean isFixed) {
        this.isFixed = isFixed;
    }
    
    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }
  //(strengthWeightFactoryClass = ValueStrengthWeightFactory.class)
    @PlanningVariable
    @ValueRange(type = ValueRangeType.FROM_PLANNING_ENTITY_PROPERTY, planningEntityProperty = "possibleValue")
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    
    public List<Value> getPossibleValue(){
    	return value.getPossibleValue();
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getBlockIndex() {
        return block.getIndex();
    }
    
    public int getColumnIndex() {
        return column.getIndex();
    }

    public int getRowIndex() {
        return row.getIndex();
    }
    
    public int getValueValue() {
        if (value == null) {
            return 0;
        }
        return value.getValue();
    }

    public Digit clone() {
    	Digit clone = new Digit();
        clone.id = id;
        clone.value = value;
        clone.column = column;
        clone.row = row;
        clone.block = block;
        return clone;
    }

    @Override
    public String toString() {
        return column + "@" + row + "@" + block + "@" + value;
    }

}
