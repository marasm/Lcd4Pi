package com.marasm.lcd4pi;

import java.io.IOException;

import com.marasm.lcd4pi.RealLCD.Direction;
import com.marasm.logger.AppLogger;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.system.SystemInfo;

public interface LCD {

	void setText(String s);

	void setText(int row, String string);

	void setCursorPosition(int row, int column);

	void stop();

	void clear();

	void home();

	void setCursorEnabled(boolean enable);

	boolean isCursorEnabled();

	void setDisplayEnabled(boolean enable);

	boolean isDisplayEnabled();

	void setBlinkEnabled(boolean enable);

	boolean isBlinkEnabled();

	void setBacklight(Color color);

	Color getBacklight();

	void scrollDisplay(Direction direction);

	void setTextFlowDirection(Direction direction);

	void setAutoScrollEnabled(boolean enable);

	boolean isAutoScrollEnabled();

	int buttonsPressedBitmask();
	
	
	public static boolean isRunningOnPi()
	{
	  String hardware = null;
    try
    {
      hardware = SystemInfo.getHardware();
      AppLogger.debug("Hardware: " + hardware);
    }
    catch(Exception e)
    {
      AppLogger.warn("Error getting hardware! Assuming that this is not a PI");
    }
    return hardware != null && hardware.contains("BCM"); //BCM2708...
	}
	
	
	public static LCD getInstance() throws IOException, UnsupportedBusNumberException
	{
	  if (isRunningOnPi())
	    return new RealLCD();
	  else
	    return new MockupLCD();
	}
	
	public static LCD getInstance(int inBus, int inAddr) 
	  throws IOException, UnsupportedBusNumberException
	{
	  if (isRunningOnPi())
	    return new RealLCD(inBus, inAddr);
	  else
	    return new MockupLCD(inBus, inAddr);
	}

}