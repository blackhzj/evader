/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

/**
 * @author Ender
 */
class AboutForm extends Form implements CommandListener
{

	private Controller ct = null;


	public AboutForm(Controller ct)
	{
		super(Constants.get(Constants.about));
		this.ct = ct;

		this.addCommand(Parameters.CMD_CONFIRM);
		this.setCommandListener(this);


		this.append(Constants.get(Constants.whatyoureads) + ct.getBook().getTitle() + Constants.get(Constants.whatyoureade)
				+ ct.getBook().getChapterNames()[ct.getReadingCanvas().getNowChapter() - 1] + Constants.get(Constants.whatyoureadchp));
		this.append("\n" + Constants.get(Constants.whatyoureadps) + ct.getReadingCanvas().getPercent() + Constants.get(Constants.whatyoureadpe));

		this.append("\n" + Constants.get(Constants.whatyoureadchcs) + ct.getBook().getChapterLengths()[ct.getReadingCanvas().getNowChapter() - 1]
				+ Constants.get(Constants.whatyoureadchce));
		this.append("\n" + Constants.get(Constants.programer));
		this.append("\n" + Constants.get(Constants.version));

	}

	public void commandAction(Command arg0, Displayable arg1)
	{
		if(arg0.equals(Parameters.CMD_CONFIRM))
		{
			ct.setNowDisplaying(ct.getReadingCanvas());
		}
	}

}
