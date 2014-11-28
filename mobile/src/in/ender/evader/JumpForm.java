/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.*;

/**
 * @author Ender
 */
class JumpForm extends Form implements ItemStateListener
{

	private Controller ct = null;

	private Gauge g = null;
	private StringItem s = null;

	public JumpForm(Controller c, int index)
	{
		super(Constants.get(Constants.jump));
		this.ct = c;
		s = new StringItem(Constants.get(Constants.jump) + ":\t", index + "%");
		g = new Gauge("", true, 100, index);
		this.append(s);
		this.append(g);

		this.addCommand(Parameters.CMD_CONFIRM);
		this.addCommand(Parameters.CMD_BACK);
		this.setCommandListener(ct);
		this.setItemStateListener(this);

	}

	public void itemStateChanged(Item item)
	{
		if(item == g)
		{
			s.setText(g.getValue() + "%");
		}
	}

	public int getPercent()
	{
		return g.getValue();
	}

}
