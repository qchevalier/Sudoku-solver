package com.Altran.exampleDroolsPlanner;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.drools.planner.examples.common.app.LoggingMain;
import org.drools.planner.examples.common.persistence.SolutionDao;

public class NDigitsGenerator extends LoggingMain {

    private static final File outputDir = new File("data/ndigits/unsolved/");

    protected SolutionDao solutionDao;

    public static void main(String[] args) {
        new NDigitsGenerator().generate();
    }

    public void generate() {
        solutionDao = new NDigitsDaoImpl();
        writeNDigits(81);

    }

    private void writeNDigits(int n) {
        String outputFileName = "unsolvedNDigits" + (n < 10 ? "0" : "") + n + ".xml";
        File outputFile = new File(outputDir, outputFileName);
        NDigits nDigits = createNDigits(n);
        solutionDao.writeSolution(nDigits, outputFile);
    }

    public NDigits createNDigits(int n) {
    	double d = Math.sqrt(n);
    	int racN = (int) d;//racN=9 if n=81
        NDigits nDigits = new NDigits();
        nDigits.setId(0L);
        nDigits.setN(n);
        
        //COLUMN
        List<Column> columnList = new ArrayList<Column>(racN);
        for (int i = 0; i < racN; i++) {
            Column column = new Column();
            column.setId((long) i);
            column.setIndex(i);
            columnList.add(column);
        }
        nDigits.setColumnList(columnList);
        
        //ROW
        List<Row> rowList = new ArrayList<Row>(racN);
        for (int i = 0; i < racN; i++) {
            Row row = new Row();
            row.setId((long) i);
            row.setIndex(i);
            rowList.add(row);
        }
        nDigits.setRowList(rowList);
        
        //BLOCK
        List<Block> blockList = new ArrayList<Block>(racN);
        for (int i = 0; i < racN; i++) {
            Block block = new Block();
            block.setId((long) i);
            block.setIndex(i);
            blockList.add(block);
        }
        nDigits.setBlockList(blockList);
        
        //DIGIT
        List<Digit> digitList = new ArrayList<Digit>(racN);
        List<Value> valueList = new ArrayList<Value>(n);
        long id = 0;
        int b = 1;//block id
        Value[][] initTab = initTabDemoniac();
        for (Column column : columnList) {
            for (Row row : rowList) {
            	Digit digit = new Digit();
            	digit.setId(id);
                id++;
                digit.setColumn(column);
                digit.setRow(row);
                
                //BLOCK
                digit.setBlock(blockList.get(b-1));
                if((column.getIndex()+1) == 9 && (row.getIndex()+1) == 9){
                	//we are at the end of the grid, then we keep b as 9.
                }
                else if((column.getIndex()+1) % 3 == 0 && (row.getIndex()+1) % 9 == 0){
                	//we change of column and vertical block
                	b++;
                }
                else if ((row.getIndex()+1) % 9 == 0){
                	//we change of column
                	b-=2;
                }
                else if ((row.getIndex()+1) % 3 == 0){
                	//we change of horizontal block
                	b++;
                }
                else{/*we keep the current block*/}

                valueList.add(initTab[column.getIndex()][row.getIndex()]);
                digit.setValue(initTab[column.getIndex()][row.getIndex()]);
                if(initTab[column.getIndex()][row.getIndex()].getValue() != 0){
                	//the digit is marked as initialized value
                	digit.setInitialisedValue(true);
                }
                digitList.add(digit);
            }
        }
        nDigits.setValueList(valueList);
        nDigits.setDigitList(digitList);
        
        System.out.println("movesize :"+nDigits.moveSize());
        logger.info("NDigits with {} digits.", nDigits.getN());
        BigInteger possibleSolutionSize = BigInteger.valueOf(nDigits.getN()).pow(nDigits.getN());
        String flooredPossibleSolutionSize = "10^" + (possibleSolutionSize.toString().length() - 1);
        logger.info("NDigits with flooredPossibleSolutionSize ({}) and possibleSolutionSize ({}).",
                flooredPossibleSolutionSize, possibleSolutionSize);
        
        return nDigits;
    }
    
    private Value[][] initTab(){
    	Value[][] initValue = new Value[9][9];
    	for(int i =0; i < 9; i++){//column
    		for(int j = 0; j < 9; j++){//row
    			initValue[i][j] = new Value(0);
    		}
    	}
    	initValue[0][0] = new Value(2);
    	initValue[1][0] = new Value(3);
    	initValue[5][0] = new Value(5);
    	initValue[3][1] = new Value(3);
    	initValue[4][1] = new Value(6);
    	initValue[6][1] = new Value(4);
    	initValue[2][2] = new Value(5);
    	initValue[0][3] = new Value(6);
    	initValue[5][3] = new Value(8);
    	initValue[3][4] = new Value(1);
    	initValue[7][4] = new Value(3);
    	initValue[1][5] = new Value(1);
    	initValue[3][5] = new Value(4);
    	initValue[5][5] = new Value(3);
    	initValue[6][5] = new Value(9);
    	initValue[0][6] = new Value(1);
    	initValue[4][6] = new Value(8);
    	initValue[5][6] = new Value(2);
    	initValue[1][7] = new Value(2);
    	initValue[2][7] = new Value(4);
    	initValue[6][7] = new Value(7);
    	initValue[7][7] = new Value(9);
    	initValue[4][8] = new Value(9);
    	initValue[6][8] = new Value(1);
    	initValue[7][8] = new Value(8);
    	
    	
		return initValue;
    }
    
    private Value[][] initTabDemoniac(){
    	Value[][] initValue = new Value[9][9];
    	for(int i =0; i < 9; i++){//column
    		for(int j = 0; j < 9; j++){//row
    			initValue[i][j] = new Value(0);
    		}
    	}
    	initValue[0][0] = new Value(1);
    	initValue[5][0] = new Value(7);
    	initValue[7][0] = new Value(9);
    	initValue[1][1] = new Value(3);
    	initValue[4][1] = new Value(2);
    	initValue[8][1] = new Value(8);
    	initValue[2][2] = new Value(9);
    	initValue[3][2] = new Value(6);
    	initValue[6][2] = new Value(5);
    	initValue[2][3] = new Value(5);
    	initValue[3][3] = new Value(3);
    	initValue[6][3] = new Value(9);
    	initValue[1][4] = new Value(1);
    	initValue[4][4] = new Value(8);
    	initValue[8][4] = new Value(2);
    	initValue[0][5] = new Value(6);
    	initValue[5][5] = new Value(4);
    	initValue[0][6] = new Value(3);
    	initValue[7][6] = new Value(1);
    	initValue[1][7] = new Value(4);
    	initValue[8][7] = new Value(7);
    	initValue[2][8] = new Value(7);
    	initValue[6][8] = new Value(3);
    	
		return initValue;
    }
    
    private Value[][] initTabFromZero(){
    	Value[][] initValue = new Value[9][9];
    	for(int i =0; i < 9; i++){//column
    		for(int j = 0; j < 9; j++){//row
    			initValue[i][j] = new Value(0);
    		}
    	}
		return initValue;
    }

}
