package client.cli;

import static java.lang.System.out;

import java.util.Scanner;

import playingCards.StdCard;
import engine.Engine;
import utils.*;

/**
 * Freecell command-line interface.
 * @author Casey
 * @version 1.2
 */
public class CLI {
	
	// static variables
	private static boolean debug = false;
	private static final Scanner scan = new Scanner(System.in);
	private static final String dbgStr = "[debug]";
	
	private static String prompt;
	
	/**
	 * Main prompt.
	 */
	public static void prompt() {
		
		out.println("<<<<<<<<< Freecell CLI v0.4 >>>>>>>>>");
		out.println("'help' for commands, 'exit' any time.");
		out.println("-------------------------------------");
		out.println();
		
		// prompt loop
		boolean cont = true;
		do {

			prompt = "freecell> ";
			if (debug) prompt = dbgStr + prompt;
			out.print(prompt);
			String in = scan.nextLine().toLowerCase();
			
			switch(in) {
			
			case ("exit") 	: cont = false;
				break;
			case ("help") 	: printHelp();
				break;
			case ("test") 	: Tester.enter();
				break;
			case ("path")	: out.println(SysUtils.getPath()); 
				break;
			case ("debug") 	: toggleDebug();
				break;
			case ("uni")	: StdCard.toggleUni();
				break;
			case ("gui")	:  
				Engine.start(true); 
				out.println();
				break;
			case ("cli")	:
				Engine.start(false);
				out.println();
				break;
			case ("show")	: out.println("Only available in game.");
				break;
			case ("new")	: out.println("Only available in game.");
				break;
			case ("cls") 	: formFeed();
				break; 
			case ("cred")	: credz();
				break;
			default 		:  
				out.println("Invalid Command.  Type 'help' for a list of commands.");
			}
		} while (cont);
		
		scan.close();
	}
	
	public static String inGame(String s) {

		// prompt loop
		boolean cont = true;
		do {
			
			prompt = "freecell.play." + s + "> ";
			if (debug) prompt = dbgStr + prompt;
			out.print(prompt);
			String in = scan.nextLine().toLowerCase();
			
			switch(in) {
			
			case ("exit") 	: SysUtils.exitDoor("\n~ groovyLlama devteam, fall 2016 ~\n");
				break;
			case ("help") 	: printHelp();
				break;
			case ("test") 	: Tester.enter();
				break;
			case ("path")	: out.println(SysUtils.getPath()); 
				break;
			case ("debug") 	: toggleDebug();
				break;
			case ("uni")	: StdCard.toggleUni();
				break;
			case ("gui")	: out.println("Not available in this mode.");
				break;
			case ("cli")	: out.println("Already in game.");
				break;
			case ("show")	: Engine.printSnapshot();
				break;
			case ("new")	: Engine.newDeal();
				break;
			case ("cls") 	: formFeed();
				break; 
			case ("cred")	: credz();
				break;
			default 		:
				
				if (in.matches("[a-p]")) {
					in = in.toLowerCase();
					return in;
				}
				
				out.println("Invalid Command.  Type 'help' for a list of commands.");
			}
		} while(cont);
		return "somethin's jacked";
	}
	
	/**
	 * Prints commands.
	 */
	private static void printHelp() {
		
		out.println();
		out.println("Available Commands: ");
		out.println("----------------------------------------------");
		out.println("help\tPrints this menu");
		out.println("test\tRuns unit tests");
		out.println("path\tPrints working directory");
		out.println("debug\tToggles debug mode");
		out.println("uni\tToggles unicode characters");
		out.println("gui\tStarts game in GUI");
		out.println("cli\tStarts game in CLI");
		out.println("show\tReprints current board");
		out.println("new\tResets game with a new deal");
		out.println("cls\tClear screen");
		out.println("cred\tPrints credits");
		out.println("exit\tExits current prompt");
		out.println();
		cliInstructions();
	}

	/**
	 * CLI game instructions.
	 */
	public static void cliInstructions() {
		
		out.println("CLI Freecell Instructions:");
		out.println("--------------------------------------------------------");
		out.println("Cell positions are referenced by the top letters.");
		out.println("Pile positions are referenced by the bottom letters.");
		out.println("Cards inserted into cells will fill the next open cell.");
		out.println("\nBlack: Spades & Clubs     |\t Red: Hearts & Diamonds");
		out.println();
	}
	
	/**
	 * Prints project credits.
	 */
	private static void credz() {
		
		out.println();
		out.println(".-----.----.-----.-----.--.--.--.--.");
		out.println("|  _  |   _|  _  |  _  |  |  |  |  |");
		out.println("|___  |__| |_____|_____|\\___/|___  |");
		out.println("|_____|                      |_____|");
		out.println(" ___    __");
		out.println("|   |  |  .---.-.--------.---.-.");
		out.println("|.  |  |  |  _  |        |  _  |");
		out.println("|.  |__|__|___._|__|__|__|___._|");
		out.println("|:      |");
		out.println("|::.. . |	DevTeam");
		out.println("`-------'");
		out.println();
		out.println("Casey Murphy 		- Lead Ninja");
		out.println("Brandon Wharton 	- Jedi Master");
		out.println("Ryan Whytsell 		- GUI Wizard");
		out.println("Gordon Finnie 		- Guard Dawg");
		out.println();
	}
	
	/**
	 * Prints a form feed to screen.  Scrolls up specified lines.
	 * Amount is specified with FFSZ constant.
	 */
	private static void formFeed() {
		
		final int FFSZ = 50;
		
		for (int i = 0; i < FFSZ; i++) out.println();
	}
	
	/**
	 * Toggles master debug switch in Debugger.
	 */
	private static void toggleDebug() {	
		debug = !debug;
		Debugger.masterToggleDebug();
	}
}
