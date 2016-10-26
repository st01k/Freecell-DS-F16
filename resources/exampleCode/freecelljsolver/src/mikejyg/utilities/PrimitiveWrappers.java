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

package mikejyg.utilities;

/**
 * wrappers for primitive types, to overcome problems of Java's own wrappers
 */
public class PrimitiveWrappers {

	/**
	 * java's Integer class does not allow setting value without changing reference,
	 * so this class;
	 */
	public static class Int {
		private int i;

		public Int() {
			i=0;
		}
		
		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}

	/**
	 * java cheats on Boolean, so have to use own Bool
	 */
	public static class Bool {
		private boolean b;

		public void setB(boolean b) {
			this.b = b;
		}

		public boolean isB() {
			return b;
		}
		
	}

}
