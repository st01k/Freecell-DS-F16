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

package mikejyg.freecellsolver.solver;

import mikejyg.utilities.PrimitiveWrappers.Int;

/** 
 * 2 solvers run in parallel
 */
public class FCDualSolver implements FCSolverIntf {

	SolverThread st[] = null;
	Int mailbox;
	Int statuses[];
	FreecellSolver fcSolvers[];
	Int start[];
	int outstandingThreads;
	
	// the thread that provided result
	private int resultThread;
	
	int solverResult;
	
////////////////////////////////////////////////////////////////
	
	public FCDualSolver(FreecellSolver fcs) {
		// init threads
		mailbox = new Int();
		fcSolvers = new FreecellSolver[2];
		st = new SolverThread[2];
		statuses = new Int[2];
		start = new Int[2];
		outstandingThreads = 0;

		fcSolvers[0] = fcs;

		fcSolvers[0].setHeuristic(FreecellSolver.HEURISTIC_CAUTIOUS);

		fcSolvers[1] = new FreecellSolver(fcSolvers[0]);
		fcSolvers[1].setHeuristic(FreecellSolver.HEURISTIC_AGGRESSIVE);

		int token = 0x01;
		for (int i=0; i<2; i++) {
			start[i] = new Int();
			statuses[i] = new Int();
			st[i] = new SolverThread(fcSolvers[i], start[i], mailbox, i, token, statuses[i]);
			st[i].start();
			token <<= 1;
		}
	}
	
	public class SolverThread extends Thread {
		FreecellSolver fcSolver;
		FreecellBoard board;
		Int mailbox;
		int threadId;
		int token;
		Int status;
		Int start;
		boolean quit = false;
		
		// mailbox is shared, token should be bit map unique among threads
		SolverThread(FreecellSolver fcSolver, Int start, Int mailbox, int threadId, int token, 
				Int status) {
			this.fcSolver = fcSolver;
			this.mailbox = mailbox;
			this.threadId = threadId;
			this.token = token;
			this.status = status;
			this.start = start;
		}
		
		void setHand(FreecellBoard board) {
			this.board = board;
		}
		
		@Override
		public void run() {
			int k;
			do {
				synchronized(start) {
					if (start.getI()==0)
						try {
							start.wait();
						} catch (InterruptedException e1) {
							if (!quit)
								System.out.println("InterruptedException : " + e1.getMessage() + " at thread : " + threadId);
							return;
						}

					start.setI(0);
				}

				try {
					k = fcSolver.solve(board);
					status.setI(k);
//					System.out.println("thread " + threadId + " completed");
				} catch (OutOfMemoryError e) {
					fcSolver.clear();
					status.setI(OUT_OF_MEMORY_ERROR);
//					System.out.println("OutOfMemoryError : " + e.getMessage() + " at thread : " + threadId);
				}

				synchronized(mailbox) {
					mailbox.setI( mailbox.getI() | token );
					mailbox.notifyAll();
				}
			} while (!quit);
		}
	}

	/**
	 * note that the argument fcs is only used the first time solve() is called
	 */
	@Override
	public int solve(FreecellBoard board) {
		
		solverResult = ERROR;
	
		fcSolvers[0].setNonEquivalentThreshold(1);
		fcSolvers[1].setNonEquivalentThreshold(1);
		
		int k = solveParallel(board);
		
		if ( k == UNSOLVABLE ) {
			System.out.println("unsolvable - disable equivalent board compare and try again...");
			// unsolvable, disable nonEqThres
			fcSolvers[0].setNonEquivalentThreshold(13);
			fcSolvers[1].setNonEquivalentThreshold(13);
			k = solveParallel(board);
		}
		
		return k;
	};
	
	/**
	 * launch two solvers with different heuristics, see which one finish first
	 * 
	 * note: java interrupt is not asynchronous? need RTSJ ?
	 * use manual flag polling instead
	 * 
	 * @param board
	 * @return
	 * @throws InterruptedException 
	 */
	public int solveParallel(FreecellBoard board) {
		int i, token;
		
		int k;
		
		// release memory
		for (i=0; i<2; i++)
			fcSolvers[i].clear();
		
		// launching threads
		mailbox.setI(0);
		for (i=0; i<2; i++) {
			fcSolvers[i].setStop(false);
			st[i].setHand(board);
			synchronized(start[i]) {
				start[i].setI(1);
				start[i].notifyAll();
			}
			outstandingThreads++;
		}
		
		resultThread = -1;
		do {
			synchronized(mailbox) {
				k = mailbox.getI();
				if (k == 0)
					try {
						mailbox.wait();
					} catch (InterruptedException e) {
						throw new Error("InterruptedException : " + e.getMessage());
					}
				k = mailbox.getI();
				// check results
				if ( k != 0 ) {
					token = 0x01;
					for (i=0; i<2; i++) {
						if ( (k & token) != 0 ) {
							mailbox.setI( k & ~token);
							outstandingThreads--;
							break;
						}
						token <<= 1;
					}
					
					k = statuses[i].getI();
					
					// here we pick a better result among the two
					
					if (resultThread == -1) {
						// this is the first result we get, use it
						solverResult = k;
						resultThread = i;

						if (solverResult == SOLVED)
							// got best result, stop all other threads
							stopAllThreads(i);
						
					} else {
						// we need to see if we get a better result this time
						if (solverResult == SOLVED)
							continue;

						if (k==SOLVED) {
							// this is a better result
							solverResult = k;
							resultThread = i;
							
							stopAllThreads(i);
							
						} else if ( solverResult != UNSOLVABLE && k == UNSOLVABLE ) {
							// unsolvable is a better result than the rest
							resultThread = i;
							solverResult = k;
						}
					} 
				} else
					System.out.println("error : wait on mailbox succeeded, but mailbox is 0; wait again;");
			}
		} while (outstandingThreads!=0);
		
		if (solverResult==OUT_OF_MEMORY_ERROR)
			throw new OutOfMemoryError("parallelSolve - unable");
		
//		System.out.println("result by thread : " + resultThread);
		
		return solverResult;
	}

	private void stopAllThreads(int exception) {
		for (int j=0; j<2; j++) {
			if (j!=exception)
				fcSolvers[j].setStop(true);
		}
	}
	
	public void shutdown() {
		if (st!=null) {
			for (int i=0; i<2; i++) {
				st[i].quit = true;
				st[i].interrupt();
			}
		}
	}

	@Override
	public int getSolverResult() {
		return fcSolvers[resultThread].getSolverResult();
	}

	@Override
	public String toReport() {
		String str = "result by thread : " + resultThread + "\n";
		str += "thread 0 : " + fcSolvers[0].toReport() + "\n";
		str += "thread 1 : " + fcSolvers[1].toReport();
		return str;
	}

}
