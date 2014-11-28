/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.TextField;

/**
 * @author Ender
 */
class PasswordForm extends Form
{

	private Controller ct = null;
	TextField tf = null;

	public PasswordForm(Controller ct)
	{
		super(Constants.get(Constants.password));
		this.ct = ct;

		Spacer sp = new Spacer(this.getWidth(), this.getHeight() / 3);
		tf = new TextField(Constants.get(Constants.inputPassword), "", Parameters.passwordLength, TextField.PASSWORD);
		tf.setInitialInputMode("IS_LATIN");
		this.append(sp);
		this.append(tf);

		this.addCommand(Parameters.CMD_CONFIRM);
		this.addCommand(Parameters.CMD_EXIT);
		this.setCommandListener(ct);

	}

	public String getPassword()
	{
		return tf.getString().trim();
	}

}
