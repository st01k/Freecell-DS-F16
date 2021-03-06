package client.gui;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import client.gui.FreeGUI;

/**
 * Creates a stopwatch that displays elapsed time at 1 second intervals.
 * @author groovyLlama devteam
 * @version 0.1
 */
public class Stopwatch {
	
	// static constants
	//TODO add hours when fixed
	private final static SimpleDateFormat timerFormat = new SimpleDateFormat("mm:ss");
	
	// static variables
	private long startTime;
	private Timer timer;
    
    public Stopwatch() {
    	
    	startTime = System.currentTimeMillis();
    	timer = new Timer();
    	
    	TimerTask timerTask = new TimerTask() {
    		
    		public void run() {
    			
    			//FIXME hours when displayed are off...
    			displayElapsedTime(System.currentTimeMillis() - startTime);
    		}
    	};
    	
    	int delay = 1000;
    	int period = 1000;
    	
    	timer.scheduleAtFixedRate(timerTask, delay, period);
    }
    
    /**
     * Returns the time that this stopwatch was started.
     * @return time watch was started
     */
    public long getStartTime() {
    	
    	return startTime;
    }
    
    /**
     * Displays elapsed time in GUI.
     * @param elapsedTime time elapsed
     */
    private static void displayElapsedTime(long elapsedTime) {
    	
        FreeGUI.setTime(timerFormat.format(elapsedTime));
    }
    
    /**
     * Stops stopwatch.
     */
    public void stop() {
    	
    	timer.cancel();
    }
    
    /**
     * Resets elapsed time.
     */
    public void reset() {
    	
    	startTime = System.currentTimeMillis();
    }
}
