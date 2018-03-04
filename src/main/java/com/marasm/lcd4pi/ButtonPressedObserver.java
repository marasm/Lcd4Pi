/*
 
 */
package com.marasm.lcd4pi;

import java.util.LinkedList;
import java.util.List;

import com.marasm.logger.AppLogger;


public class ButtonPressedObserver {
	private volatile boolean isRunning = false;
	private final List<ButtonListener> buttonListeners = new LinkedList<ButtonListener>();
	private final LCD lcd;
	private final long [] buttonDownTimes = new long[Button.values().length];
  private Thread thread;
  private long sleepCounter = 0;
  private static final long DISPLAY_SLEEP_THRESHOLD = 4000;
	
	private class ButtonChecker implements Runnable {
		@Override
		public void run() {
			while (isRunning) 
			{
				try 
				{
					for (Button button : Button.values()) 
					{
						if (button.isButtonPressed(lcd.buttonsPressedBitmask())) 
						{
							if (buttonDownTimes[button.getPin()] != 0) 
							{
								continue;
							}
							buttonDownTimes[button.getPin()] = System.currentTimeMillis();
						} 
						else if (buttonDownTimes[button.getPin()] != 0) 
						{
							if ((System.currentTimeMillis() - buttonDownTimes[button.getPin()]) > 15) 
							{
							  AppLogger.debug("Waking display");
							  sleepCounter = 0;
							  lcd.setBacklight(Color.ON);;
								fireNotification(button);
							}
							buttonDownTimes[button.getPin()] = 0;
						}
					}
					sleepCounter++;
					if (lcd.getBacklight() != Color.OFF &&
					  sleepCounter > DISPLAY_SLEEP_THRESHOLD) //~60 seconds
					{
					  AppLogger.debug("Putting display to sleep");
					  lcd.setBacklight(Color.OFF);;
					}
					if (sleepCounter > Long.MAX_VALUE - 100)
					{
					  sleepCounter = DISPLAY_SLEEP_THRESHOLD;
					}
				} 
				catch (Exception e) 
				{
					AppLogger.error(	"Could not get buttons bitmask!", e);
				}
				sleep(15);
				Thread.yield();
			}
		}

		private void sleep(int time) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				AppLogger.error(	"Could not get buttons bitmask!", e);
			}			
		}

		private void fireNotification(Button trackedButton) {
			ButtonListener[] listeners;
			synchronized (buttonListeners) {
				listeners = buttonListeners
						.toArray(new ButtonListener[buttonListeners.size()]);
			}
			for (ButtonListener l : listeners) {
				l.onButtonPressed(trackedButton);
			}
		}

	}

	public ButtonPressedObserver(LCD lcd) {
		this.lcd = lcd;
	}

	public void removeButtonListener(ButtonListener l) {
		synchronized (buttonListeners) {
			buttonListeners.remove(l);
			if (buttonListeners.isEmpty()) {
				stopButtonMonitor();
			}
		}
	}

	private void stopButtonMonitor() {
		isRunning = false;
	}

	public Thread addButtonListener(ButtonListener l) {
		synchronized (buttonListeners) 
		{
			buttonListeners.add(l);
			return startButtonMonitor();
		}
	}

	private Thread startButtonMonitor() {
	  if (thread == null)
	    thread = new Thread(new ButtonChecker(), "Button Checker");
	  if (!isRunning)
	  {
	    isRunning = true;
	    thread.setDaemon(true);
	    thread.start();
	  }
		return thread;
	}
}
