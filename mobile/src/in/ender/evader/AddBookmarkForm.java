/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 * @author Ender
 */
class AddBookmarkForm extends Form
{

	private Controller ct = null;

	private TextField tf = null;

	public AddBookmarkForm(Controller ct, long cu, int ch, String na)
	{
		super(Constants.get(Constants.addBookmark));
		this.ct = ct;

		this.append(new StringItem(Constants.get(Constants.bookmarkPosition), "" + cu));
		this.append(new StringItem(Constants.get(Constants.bookmarkChapter), "" + ch));
		tf = new TextField(Constants.get(Constants.bookmarkTitle), na, Parameters.bookmarkTitleLength, TextField.ANY);
		this.append(tf);

		this.addCommand(Parameters.CMD_CONFIRM);
		this.addCommand(Parameters.CMD_BACK);
		this.setCommandListener(ct);

	}

	public String getTextField()
	{
		return tf.getString();
	}
}
