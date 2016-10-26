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

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DFSTree implements DFSTreeIntf {
	int depth = -1;
	
	// data structure for candidates at each move level
	ArrayList< TreeMap<Double, Integer> > candidates;
	// float is the huristic score of the board
	// Integer is the ID of the board
	// array index is the move level, level 0 is the initial board

	public DFSTree() {
		candidates = new ArrayList< TreeMap<Double, Integer> >();
		incDepth();
	}
	
	@Override
	public int getDepth() {
		return depth;
	}
	
	@Override
	public void incDepth() {
		candidates.add( new TreeMap<Double, Integer>() );
		depth++;
	}
	
	@Override
	public void decDepth() {
		candidates.remove(candidates.size() - 1);
		depth--;
	}

	@Override
	public Integer popBestNode() {
		Map.Entry<Double, Integer> infoEntry;

		// pick the highest score
		infoEntry = candidates.get(depth).lastEntry();
		if (infoEntry == null)
			return null;
		
		Integer Id = infoEntry.getValue();
		candidates.get(depth).remove(infoEntry.getKey());
		return Id;
	}
	
	@Override
	public void addNode(int id, double score) {
		double small = .000000001;
		while (candidates.get(depth).containsKey(score)) {
			score = score - small;
		}
		
		candidates.get(depth).put(score, id);
	}
}
