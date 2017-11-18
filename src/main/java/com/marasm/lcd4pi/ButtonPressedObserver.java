/*
 
 */
package com.marasm.lcd4pi;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ButtonPressedObserver {
	private volatile boolean isRunning = false;
	private final List<ButtonListener> buttonListeners = new LinkedList<ButtonListener>();
	private final LCD lcd;
	private final long [] buttonDownTimes = new long[Button.values().length];
	
	private class ButtonChecker implements Runnable {
		@Override
		public void run() {
			while (isRunning) {
				try {
					for (Button button : Button.values()) {
						if (button.isButtonPressed(lcd.buttonsPressedBitmask())) {
							if (buttonDownTimes[button.getPin()] != 0) {
								continue;
							}
							buttonDownTimes[button.getPin()] = System.currentTimeMillis();
						} else if (buttonDownTimes[button.getPin()] != 0) {
							if ((System.currentTimeMillis() - buttonDownTimes[button.getPin()]) > 15) {
								fireNotification(button);
							}
							buttonDownTimes[button.getPin()] = 0;
						}
					}
				} catch (IOException e) {
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
							"Could not get buttons bitmask!", e);
				}
				sleep(15);
				Thread.yield();
			}
		}

		private void sleep(int time) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Could not get buttons bitmask!", e);
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

	public void addButtonListener(ButtonListener l) {
		synchronized (buttonListeners) {
			if (buttonListeners.isEmpty()) {
				startButtonMonitor();
			}
			buttonListeners.add(l);
		}
	}

	private void startButtonMonitor() {
		isRunning = true;
		Thread t = new Thread(new ButtonChecker(), "Button Checker");
		t.setDaemon(true);
		t.start();
	}
}
