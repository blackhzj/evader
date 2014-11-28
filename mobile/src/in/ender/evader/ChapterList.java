package in.ender.evader;


import javax.microedition.lcdui.List;

class ChapterList extends List
{

	private Controller ct = null;
	private String[] li = null;

	public ChapterList(Controller ct, String[] c)
	{
		super(Constants.get(Constants.chapter), List.IMPLICIT, c, null);
		this.ct = ct;
		this.li = c;

		//this.addCommand(Constants.CMD_READ);
		this.setSelectCommand(Parameters.CMD_READ);
		this.addCommand(Parameters.CMD_BACK);
		this.setCommandListener(ct);

	}

}
