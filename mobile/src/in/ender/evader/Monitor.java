/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import java.util.TimerTask;

/**
 * @author Ender
 */
class Monitor extends TimerTask
{

	private Controller ct = null;
	private boolean autoScroll = false;
	private boolean paused = true;
	private int autoScrollSpeed = 1;
	private int j = 0;

	public Monitor(boolean autoScroll, Controller ct)
	{
		this.ct = ct;
		this.autoScroll = autoScroll;
		this.autoScrollSpeed = ct.getSettingRecord().getAutoScrollSpeed();
	}

	public void setAutoScroll(boolean autoScroll)
	{
		this.autoScroll = autoScroll;
	}

	public void pause()
	{
		this.paused = true;
	}

	public void go()
	{
		this.paused = false;
	}

	public boolean isAutoScrolling()
	{
		return autoScroll;
	}

	public void setAutoScrollSpeed(int sp)
	{
		this.autoScrollSpeed = sp;
		j = 0;
	}

	public int getAutoScrollSpeed()
	{
		return autoScrollSpeed;
	}

	public void run()
	{
		if(!paused)
		{
			if(autoScroll)
			{
				//if( j >= autoScrollSpeed ) {
				ct.getReadingCanvas().scroll();
				System.out.println("LALALA. Scrolling...");
				//j = 0;
				//}
				//j++;
			}
		}
		else
		{
			j = 0;
		}
	}

}
