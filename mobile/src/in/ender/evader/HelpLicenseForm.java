/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 * @author Ender
 */
class HelpLicenseForm extends Form
{

	private Controller ct = null;

	public HelpLicenseForm(Controller ct)
	{
		super(Constants.get(Constants.helpLicense));
		this.ct = ct;

		this.addCommand(Parameters.CMD_CONFIRM);
		this.setCommandListener(ct);

		StringItem help = new StringItem(Constants.get(Constants.help) + "\n", Constants.get(Constants.helpContent));
		//this.append(Constants.get(Constants.helpContent));
		//help.setText(Constants.get(Constants.helpContent));
		//help.setFont(ct.getSettingRecord().getFont());
		help.setLayout(Item.LAYOUT_LEFT | Item.LAYOUT_VCENTER);
		this.append(help);
		StringItem license = new StringItem(Constants.get(Constants.license) + "\n", Constants.get(Constants.licenseContent));
		//this.append(Constants.get(Constants.licenseContent));
		//license.setFont(ct.getSettingRecord().getFont());
		license.setLayout(Item.LAYOUT_CENTER | Item.LAYOUT_VCENTER);
		this.append(license);

	}

}
