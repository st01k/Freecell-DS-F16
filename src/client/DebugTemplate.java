package client;

import static java.lang.System.out;

/**
 * Code needed to implement global debugging system
 * in the text-based UI.  Add the debug variable,
 * toString, toggleDebug, and an appropriate unit
 * test into each class you create.  Add proper debug
 * print statements to track the flow and logic of 
 * your class.
 * 
 * Use javadocs statements to document each class.
 * 
 * NOTE: You'll need the above import statement
 * to use 'out.println' statements.
 * @author groovyLlama
 * @version 1.0
 */
public class DebugTemplate {

	// add me
	private static boolean debug = false;
	
	/**
	 * Don't mind these...
	 */
	DebugTemplate() {}
	DebugTemplate(String in) {}
	
	/**
	 * Returns well-formatted object string.
	 * Preferably a one-liner.
	 */
	@Override
	public String toString() {
		
		return "";
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		
		debug = !debug;
	}
	
	public void callUsingThese() {
		
		// at the beginning of the method
		if (debug) out.println("---packageName.ClassName.methodName--- ");
		
		// where needed, something that shows var results or whatev is needed
		//if (debug) out.println(opd1+" "+op+" "+opd2+" = "+stk.peek());
	}
	
	//----------------- Use whichever test unit best applies ------------------
	
	/**
	 * Unit test with loop.
	 * Test with multiple cases.
	 * Specify action being taken then verify it.
	 */
	public static void unitTestWithLoop() {
		
		out.println("-------------------- Testing DebugTemplate Class:\n");
		
		DebugTemplate[] testSequence = {
				
				new DebugTemplate(),
				new DebugTemplate("whatever applies"),
				new DebugTemplate("to make test objects")
		};
		
		for (DebugTemplate d : testSequence) {
		
			out.println("Input: " + d.toString());
			out.println("some method");
			out.println("test all methods");
			out.println();
		}
		
   	out.println("-------------------- DebugTemplate Unit Test Complete.\n");
	}
	
	/**
	 * Unit test without loop.
	 * Specify action being taken then verify it.
	 * Test with multiple cases if possible.
	 */
	public static void unitTestWithoutLoop() {
		
		out.println("-------------------- Testing StackRefBased Class:\n");
		
		out.println("Creating New Reference Based Stack...");
		//StackRefBased<Integer> srb = new StackRefBased<Integer>();
		//out.println("Stack empty? " + srb.isEmpty());
		
		//Integer[] testSequence = {1,0,-1,9,93,25960,9};
		
		out.println("\nPushing items onto stack...");
		//for (Integer i : testSequence) srb.push(i);
		//out.println("Stack empty? " + srb.isEmpty());
		//out.println(srb);
		//out.println("Number of items in stack: " + srb.size());
		
		out.println("\nPopping top item...");
		//out.println("Popped: " + srb.pop());
		//out.println(srb);
		//out.println("Number of items in stack: " + srb.size());
		
		out.println("\nPeeking at top item...");
		//out.println("Top item: " + srb.peek());
		//out.println(srb);
		
		out.println("\nPopping all items...");
		//srb.popAll();
		//out.println("Stack empty? " + srb.isEmpty());
		//out.println(srb);
		
		out.println();
    	out.println("-------------------- StackRefBased Unit Test Complete.\n");
	}
}
