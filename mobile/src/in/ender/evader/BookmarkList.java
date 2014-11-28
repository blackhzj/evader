/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.List;

/**
 * @author Ender
 */
class BookmarkList extends List
{

	private Controller ct = null;

	public static final Command CMD_ADDBOOKMARK = new Command(Constants.get(Constants.addBookmark), Command.SCREEN, 3);
	public static final Command CMD_DELETEBOOKMARK = new Command(Constants.get(Constants.deleteBookmark), Command.SCREEN, 4);
	public static final Command CMD_JUMPTOBOOKMARK = new Command(Constants.get(Constants.jumpToBookmark), Command.SCREEN, 5);

	public BookmarkList(Controller ct, String[] args)
	{
		super(Constants.get(Constants.bookmark), List.IMPLICIT, args, null);
		this.ct = ct;

		this.addCommand(Parameters.CMD_BACK);
		this.addCommand(BookmarkList.CMD_ADDBOOKMARK);
		this.addCommand(BookmarkList.CMD_DELETEBOOKMARK);
		this.addCommand(BookmarkList.CMD_JUMPTOBOOKMARK);
		this.setCommandListener(ct);

	}


}
