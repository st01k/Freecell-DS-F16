/**
 * Copyright (C) 2010 Junyang Gu <mikejyg@gmail.com>
 * 
 * This file is part of FreecellJSolver.
 *
 * FreecellJSolver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FreecellJSolver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FreecellJSolver.  If not, see <http://www.gnu.org/licenses/>.
 */

package mikejyg.freecellsolver.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import mikejyg.freecellsolver.solver.FCDualSolver;
import mikejyg.freecellsolver.solver.FCMultiSolver;
import mikejyg.freecellsolver.solver.FCSolverIntf;
import mikejyg.freecellsolver.solver.FreecellBoard;
import mikejyg.freecellsolver.solver.FreecellSolver;
import mikejyg.freecellsolver.solver.MSDeck;
import mikejyg.freecellsolver.solver.PlayingCardDeck;

/**
 * the main program class
 *  
 */
public class FCSolve
{
	////////////////////
	// output settings
	static boolean printResult = false;

	boolean solveAutoCfg = true;	// default
	// if any option is specified, it will be turned off

	int iterations = 100;
	long seed = 1;
	int nonEqThres = 1;	// default
	
	boolean readFromFile = false;
	Scanner scanner = null;
	boolean readBoard = false;
	
	// use 2 solver thread (with different heuristic) to get to the quickest answer
	boolean solveDual = false;
	
	FreecellSolver freecellSolver = new FreecellSolver();

	FCSolverIntf fcSolver;
	
/////////////////////////////////////////////////////
	
	public static void main(String args[]) throws IOException, FreecellSolver.ParseException, FreecellBoard.ParseException, FreecellSolver.StoppedException, InterruptedException 
	{
		FCSolve solver = new FCSolve();
		
		// parsing command line
		int idx = 0;
		while (idx < args.length) {
            if (args[idx].equals("-f")) {
            	idx++;
            	solver.readFromFile = true;

            	// reading board numbers from a file
            	solver.scanner=new Scanner(new File(args[idx]));
            	break;
            } else if (args[idx].equals("--range")) {
            	idx++;
            	solver.seed = Long.parseLong(args[idx++]);
            	solver.iterations = (int)(Long.parseLong(args[idx++]) - solver.seed + 1);
    			System.out.println("start at " + solver.seed + ", " + solver.iterations + " iterations.");
            	break;
            } else if (args[idx].equals("--nonEqThres")) {
            	idx++;
            	solver.solveAutoCfg = false;
            	solver.nonEqThres = Integer.parseInt(args[idx++]);
            } else if (args[idx].equals("--aggressive")) {
            	idx++;
            	solver.solveAutoCfg = false;
            	solver.freecellSolver.setHeuristic(FreecellSolver.HEURISTIC_AGGRESSIVE);
            } else if (args[idx].equals("--cautious")) {
            	idx++;
            	solver.solveAutoCfg = false;
            	solver.freecellSolver.setHeuristic(FreecellSolver.HEURISTIC_CAUTIOUS);
            } else if (args[idx].equals("--random")) {
            	idx++;
            	solver.solveAutoCfg = false;
            	solver.freecellSolver.setHeuristic(FreecellSolver.HEURISTIC_RANDOM);
            } else if (args[idx].equals("--useBoardRevisions")) {
            	idx++;
            	solver.freecellSolver.setUseBoardRevisions(true);
            } else if (args[idx].equals("--usePropertiesFile")) {
            	idx++;
            	solver.solveAutoCfg = false;
                solver.freecellSolver.loadProperties();
            } else if (args[idx].equals("-h")) {
            	printHelp();
            	System.exit(1);
            } else if (args[idx].equals("--printResult")) {
            	idx++;
            	printResult = true;
            } else if (args[idx].equals("-b")) {
            	// read board from stdin
            	solver.iterations = 1;
            	solver.readBoard = true;
            	break;
            } else if (args[idx].equals("--autoCfg")) {
            	idx++;
            	solver.solveAutoCfg = true;
            } else if (args[idx].equals("--dual")) {
            	idx++;
            	solver.solveAutoCfg = false;
            	solver.solveDual = true;
            } else if (args[idx].equals("--freeCells")) {
            	idx++;
            	FreecellBoard.numberOfFreeCells = Integer.parseInt(args[idx++]);
            } else if (args[idx].equals("--columns")) {
            	idx++;
            	FreecellBoard.numberOfColumns = Integer.parseInt(args[idx++]);
            } else {
            	solver.iterations = 1;
            	solver.seed = Long.parseLong(args[idx]);
            	break;
            }
        }
		
		solver.solve();
	}
	
	private void solve() throws FileNotFoundException, IOException, FreecellBoard.ParseException, FreecellSolver.StoppedException, InterruptedException {
		freecellSolver.setNonEquivalentThreshold(nonEqThres);
		
		long timeUsedMost=0;
		long timeUsedMostSeed = seed;
		int i=0;
		
		if (solveAutoCfg) {
			System.out.println("using auto config solve");
			fcSolver = new FCMultiSolver(freecellSolver);
		} else if (solveDual) {
			System.out.println("using dual parallel solve");
			fcSolver = new FCDualSolver(freecellSolver);
		} else {
			// use it directly
			fcSolver = freecellSolver;
			freecellSolver.printOptions();
		}
		
		System.out.println();
		
		while (readFromFile || i<iterations) {
			if (readFromFile) {
				if (scanner.hasNextInt())
					seed=scanner.nextInt();
				else
					break;
			}
			
			FreecellBoard hand;
			
			if (readBoard) {
				hand = FreecellBoard.parse(System.in);
			} else {
				System.out.println("solving seed : " + seed);

				// get a deck of cards
				//			PlayingCardDeck singleDeck=new PlayingCardDeck();
				PlayingCardDeck singleDeck = new MSDeck();
				singleDeck.setSeed(seed);
				singleDeck.shuffle();

				// deal it onto the FreecellSolver board
				hand = new FreecellBoard();
				hand.deal(singleDeck);
			}
			
			// print it out
			if (iterations==1) {
				System.out.println(hand.toString());
				System.out.println();
			}
			
			long startTime = System.currentTimeMillis();
			long timeUsed;
			
			// solve it
			try {
				fcSolver.solve(hand);
			} catch (OutOfMemoryError e) {
				
				if (!solveDual)
					freecellSolver.clear();

				System.out.println("OutOfMemoryError : " + e.getMessage());
			}
			
			if (printResult) {
				freecellSolver.printResult();
				System.out.println();
			}
		
			System.out.println(fcSolver.toReport());
			
			timeUsed = System.currentTimeMillis() - startTime;
			System.out.println("miliseconds used : " + timeUsed );
			
			if (iterations != 1) {
				if (timeUsed > timeUsedMost) {
					timeUsedMost = timeUsed;
					timeUsedMostSeed = seed;
					System.out.println();
					System.out.println("time used most : " + timeUsedMost + " at seed : " + timeUsedMostSeed);
				}
			}
			
			seed++;
			i++;
			
			System.out.println();
		}
		freecellSolver.storeProperties("FreecellSolver-last_used.properties");
		
		if (solveDual)
			((FCDualSolver) fcSolver).shutdown();
	}

	private static void printHelp() {
		System.out.println("arguments : [options] game_specification");
		System.out.println("\tnote : default option is auto config solve");
		String optionsStr = "options :\n"
			+ "\t--nonEqThres threshold\t\tspecify non-equivalent card threshold; the default is 1;\n"
			+ "\t--aggressive\t\tuse aggressive heuristic\n"
			+ "\t--cautious\t\tuse cautious heuristic\n"
			+ "\t--random\t\tuse random DFS(depth first scan)\n"
			+ "\t--usePropertiesFile\t\tuse properties specified in the file FreecellSolver.properties\n"
			+ "\t--printResult\t\tprint moves of the result\n"
			+ "\t--autoCfg\t\tlet the solver try different options automatically; this is the default;"
			+ " any latter one of the --nonEqThres, --aggressive --cautious and --usePropertiesFile options will disable this;"
			+ "\t--dual\t\tlaunching two solvers in parallel\n"
			+ "\t--freeCells number_of_free_cells\t\tdefault is 4\n"
			+ "\t--columns number_of_columns\t\tdefault is 8\n"
			+ "\t--useBoardRevisions\t\tuse a board revision system to prevent unnecessary moves\n"
				;
		String gameSpecStr = "game_specification :\n"
			+ "\t-f games_file_name\t\tsolve games specified by numbers in the file\n"
			+ "\t--range start end\t\tsolve all games of a specified range\n"
			+ "\t-b\t\tread a board from input\n"
			+ "\tgame_number\t\tsolve the game of a specified number\n"
			;
		System.out.println(optionsStr);
		System.out.println(gameSpecStr);
	}
	
}
