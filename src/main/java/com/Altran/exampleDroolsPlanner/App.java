package com.Altran.exampleDroolsPlanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.planner.config.constructionheuristic.ConstructionHeuristicSolverPhaseConfig;
import org.drools.planner.config.localsearch.LocalSearchSolverPhaseConfig;
import org.drools.planner.config.localsearch.decider.acceptor.AcceptorConfig;
import org.drools.planner.config.phase.SolverPhaseConfig;
import org.drools.planner.config.score.director.ScoreDirectorFactoryConfig;
import org.drools.planner.config.solver.SolverConfig;
import org.drools.planner.config.termination.TerminationConfig;
import org.drools.planner.core.Solver;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {    
    	//RULEBASE
    	String path = 
    			"D:\\workspace\\example_DroolsPlanner_01\\exampleDroolsPlanner\\ressources\\nDigitsScoreRules.drl";
    	InputStream input = null;
		try {
			input = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	PackageBuilder builder = new PackageBuilder();
    	try {
			builder.addPackageFromDrl(new InputStreamReader(input));
		} catch (DroolsParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    	ruleBase.addPackage(builder.getPackage());
    	//RULEBASE
    	
        SolverConfig solverConfig = new SolverConfig();
        List<SolverPhaseConfig> solverPhaseConfigList = new ArrayList<SolverPhaseConfig>();

        //solverConfig.setEnvironmentMode(EnvironmentMode.TRACE);
        //<solutionClass>
        solverConfig.setSolutionClass(NDigits.class);
        //</solutionClass>

        //<planningEntityClass>
        Set<Class<?>> planningEntityClassSet = new HashSet<Class<?>>();
        planningEntityClassSet.add(Digit.class);
        solverConfig.setPlanningEntityClassSet(planningEntityClassSet);
        //</planningEntityClass>

        //<scoreDirectorFactory>
        ScoreDirectorFactoryConfig scoreDirectorFactoryConfig = solverConfig.getScoreDirectorFactoryConfig();
        scoreDirectorFactoryConfig.setScoreDefinitionType(ScoreDirectorFactoryConfig.ScoreDefinitionType.SIMPLE);
               scoreDirectorFactoryConfig.setRuleBase(ruleBase);
        solverConfig.setScoreDirectorFactoryConfig(scoreDirectorFactoryConfig);
        //</scoreDirectorFactory>
        
        //<termination>
        TerminationConfig terminationConfig = solverConfig.getTerminationConfig();
        String scoreAttained = "0";
		terminationConfig.setScoreAttained(scoreAttained );
        solverConfig.setTerminationConfig(terminationConfig);
        //</termination>

        //<constructionHeuristic>
        ConstructionHeuristicSolverPhaseConfig constructionHeuristicSolverPhaseConfig
                = new ConstructionHeuristicSolverPhaseConfig();
        constructionHeuristicSolverPhaseConfig.setTerminationConfig(terminationConfig);
		constructionHeuristicSolverPhaseConfig.setConstructionHeuristicType(
				ConstructionHeuristicSolverPhaseConfig.ConstructionHeuristicType.FIRST_FIT);
        solverPhaseConfigList.add(constructionHeuristicSolverPhaseConfig);
        //</constructionHeuristic>

        //<localSearch>
        LocalSearchSolverPhaseConfig localSearchSolverPhaseConfig = new LocalSearchSolverPhaseConfig();
	        //<selector>
		        //<moveFactoryClass>
				localSearchSolverPhaseConfig.getSelectorConfig().setMoveFactoryClass(ValueChangeMoveFactory.class);
				//</moveFactoryClass>
			//</selector>
			//<acceptor>
			AcceptorConfig acceptorConfig = new AcceptorConfig();
			Integer moveTabuSize = 7;
			acceptorConfig.setMoveTabuSize(moveTabuSize);
			localSearchSolverPhaseConfig.setAcceptorConfig(acceptorConfig);
			//</acceptor>
        solverPhaseConfigList.add(localSearchSolverPhaseConfig);
        //</localSearch>

        
        //<constructionHeuristic>
        //<localSearch><selector><acceptor><forager></localSearch>
        solverConfig.setSolverPhaseConfigList(solverPhaseConfigList);
        
        Solver solver = solverConfig.buildSolver();

        // Load a Sudoku 9*9 initialized with 26 digits
        NDigits unsolved81Digits = new NDigitsGenerator().createNDigits(81);

        // Solve the problem
        solver.setPlanningProblem(unsolved81Digits);
        solver.solve();
        NDigits solved81Digits = (NDigits) solver.getBestSolution();

        // Display the result
        System.out.println("\nSolved sudoku:\n" + toDisplayString(solved81Digits));
    }
    
    public static String toDisplayString(NDigits nDigits) {
        StringBuilder displayString = new StringBuilder();
        int n = nDigits.getN();
    	double d = Math.sqrt(n);
    	int racN = (int) d;//racN=9 si n=81
        Digit[][] tab = new Digit[9][9];
        for(Digit digit : nDigits.getDigitList()){
        	tab[digit.getColumnIndex()][digit.getRowIndex()] = digit;
        }
    	displayString.append("\nI---------------I" +
    			"---------------I" +
    			"--------------I\nI");
        for(int i = 0; i < racN; i++){
        	for(int j = 0; j < racN; j++){
                if((j+1) == 9 && (i+1) == 9){
                	//we are at the end of the grid
                	displayString.append(" | " + tab[j][i].getValueValue()+" |I");
                }
                else if((i+1) % 3 == 0 && (j+1) % 9 == 0){
                	//we change of row and horizontal block
                	displayString.append(" | " + tab[j][i].getValueValue()+" |I\nI---------------I" +
                			"---------------I" +
                			"--------------I\nI");
                }
                else if ((j+1) % 9 == 0){
                	//we change of row
                	displayString.append(" | " + tab[j][i].getValueValue()+" |I\nI");
                }
                else if ((j+1) % 3 == 0){
                	//we change of vertical block
                	displayString.append(" | " + tab[j][i].getValueValue()+" | I");
                }
                else{ displayString.append(" | " + tab[j][i].getValueValue()); }
        	}
        }
    	displayString.append("\nI---------------I" +
    			"---------------I" +
    			"--------------I\n");
        return displayString.toString();
    }
}
