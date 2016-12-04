package client.gui;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.lang.reflect.InvocationTargetException;
import client.gui.FreeGUI;

@SuppressWarnings("serial")
public class Stopwatch extends JFrame implements ActionListener, Runnable
{
	private long startTime;
    private final static SimpleDateFormat timerFormat = new SimpleDateFormat("h : mm : ss : SS");
    private final JButton startStopButton= new JButton("Start/stop");
    private Thread updater;
    private boolean isRunning= false;
    
    public Stopwatch()
    {
        startStopButton.addActionListener(this);
        getContentPane().add(startStopButton);
        setSize(100,50);
        setVisible(true);
    }
    
    private final Runnable displayUpdater= new Runnable()
    {
    	public void run(){displayElapsedTime(System.currentTimeMillis() - Stopwatch.this.startTime);}
    };
    
   
    
    /**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}


	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	/**
	 * @return the updater
	 */
	public Thread getUpdater() {
		return updater;
	}


	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(Thread updater) {
		this.updater = updater;
	}


	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}


	/**
	 * @param isRunning the isRunning to set
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}


	/**
	 * @return the timerformat
	 */
	public static SimpleDateFormat getTimerformat() {
		return timerFormat;
	}


	/**
	 * @return the startStopButton
	 */
	public JButton getStartStopButton() {
		return startStopButton;
	}


	/**
	 * @return the displayUpdater
	 */
	public Runnable getDisplayUpdater() {
		return displayUpdater;
	}


	public void actionPerformed(ActionEvent ae)
    {
    	if(isRunning)
        {
            long elapsed= System.currentTimeMillis() - startTime;
            
            isRunning= false;
            
            try
            {
                updater.join();
                // Wait for updater to finish
            }
            catch(InterruptedException ie)
            {}
            
            displayElapsedTime(elapsed);// Display the end-result
        }
        else
        {
        	startTime= System.currentTimeMillis();
            isRunning= true;
            updater= new Thread(this);
            updater.start();
        }
    }//end of actionPerformed method
    
    
    public void displayElapsedTime(long elapsedTime)
    {
        FreeGUI.setTime(timerFormat.format(new java.util.Date(elapsedTime)));
    }//end of displayElapsedTime method
    
    public void run()
    {
    	try
    	{
    		while(isRunning)
            {
                SwingUtilities.invokeAndWait(displayUpdater);
                Thread.sleep(50);
            }
        }
        catch(InvocationTargetException ite)
        {
            ite.printStackTrace(System.err);
            // Should never happen!
        }
        catch(InterruptedException ie) {}// Ignore and return!
    }//end of run method
    
    /*public static void main(String[] arg)
    {
        new Stopwatch().addWindowListener(new WindowAdapter()
        {
        	public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }*/	
}
