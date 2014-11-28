package in.ender.evader;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;


class InfoForm extends Form
{

	private Controller ct = null;

	public InfoForm(Controller ct, String t, String a, String c, String e, String n)
	{
		super(Constants.get(Constants.info));
		this.ct = ct;
		StringItem ti = new StringItem(Constants.get(Constants.title), t);
		StringItem au = new StringItem(Constants.get(Constants.author), a);
		StringItem cr = new StringItem(Constants.get(Constants.creator), c);
		StringItem em = new StringItem(Constants.get(Constants.email), e);
		StringItem no = new StringItem(Constants.get(Constants.notes), n);
		this.append(ti);
		this.append(au);
		this.append(cr);
		this.append(em);
		this.append(no);
		this.addCommand(Parameters.CMD_READ);
		this.addCommand(Parameters.CMD_EXIT);
		this.setCommandListener(ct);
	}


}
