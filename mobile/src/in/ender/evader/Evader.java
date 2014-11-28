package in.ender.evader;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Evader extends MIDlet
{

	private boolean start = false;
	private Display display = null;
	private Controller ct = null;

	public Evader()
	{

	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException
	{
		ct.saveSetting();
		// TODO Auto-generated method stub

	}

	protected void pauseApp()
	{
		System.out.println("PauseApp");
		ct.getMonitor().pause();
	}

	protected void startApp() throws MIDletStateChangeException
	{
		if(display == null)
		{
			display = Display.getDisplay(this);
		}
		if(!start)
		{
			ct = new Controller(this);
			start = true;
		}
		display.setCurrent(ct.getDisplayable());
		if(ct.getDisplayable() instanceof ReadingCanvas)
		{
			ct.getMonitor().go();
		}

	}

	public void setDisplayable()
	{
		display.setCurrent(ct.getDisplayable());
	}

	public void showAlert(Alert alert, Displayable nextDisplayable)
	{
		display.setCurrent(alert, nextDisplayable);
	}

	public void light()
	{
		display.flashBacklight(Parameters.lightDuration);
	}
	
	/*public void exitMIDlet() throws MIDletStateChangeException {
		this.destroyApp(false);
		this.notifyDestroyed();
	}*/

}
