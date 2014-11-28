/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.*;

/**
 * @author Ender
 */
class SearchForm extends Form implements ItemStateListener
{

	private Controller ct = null;
	private String lastSearchString = "";
	private long lastSearchPos = 0;

	private TextField tf = null;
	private Gauge gs = null;
	private StringItem r = null;

	private boolean isSearching = false;

	public static final Command CMD_SEARCH = new Command(Constants.get(Constants.search), Command.SCREEN, 3);
	public static final Command CMD_GOJUMP = new Command(Constants.get(Constants.gojump), Command.OK, 4);
	public static final Command CMD_SEARCHNEXT = new Command(Constants.get(Constants.searchNext), Command.SCREEN, 3);
	public static final Command CMD_STOPSEARCH = new Command(Constants.get(Constants.stopSearch), Command.SCREEN, 3);

	public SearchForm(String lastSearchString, long lastSearchPos, Controller ct)
	{
		super(Constants.get(Constants.search));
		this.ct = ct;
		this.lastSearchString = lastSearchString;
		this.lastSearchPos = lastSearchPos;

		tf = new TextField(Constants.get(Constants.search) + ":\t", "", Parameters.searchContentLength, TextField.ANY);
		gs = new Gauge(Constants.get(Constants.startPosition) + "0%", true, 100, 0);
		r = new StringItem(Constants.get(Constants.searchResult) + "\n", "");
		tf.setString(lastSearchString);
		gs.setValue((int)(lastSearchPos * 100 / ct.getReadingCanvas().getTotalLength()));
		gs.setLabel(Constants.get(Constants.startPosition) + gs.getValue() + "%");
		this.append(tf);
		this.append(gs);
		this.append(r);

		this.addCommand(SearchForm.CMD_SEARCH);
		this.addCommand(Parameters.CMD_BACK);
		this.setCommandListener(ct);
		this.setItemStateListener(this);
	}

	public void itemStateChanged(Item arg0)
	{
		if(arg0.equals(gs))
		{
			gs.setLabel(Constants.get(Constants.startPosition) + gs.getValue() + "%");
			ct.setSearchPos((gs.getValue() * ct.getReadingCanvas().getTotalLength()) / 100L);
		}
		else if(arg0.equals(tf))
		{

		}
		if(!isSearching)
		{
			this.removeCommand(SearchForm.CMD_SEARCHNEXT);
			this.removeCommand(SearchForm.CMD_SEARCH);
			this.addCommand(SearchForm.CMD_SEARCH);
			//System.out.println("ItemStateChanged. Editing search string.");
		}
	}

	public String getString()
	{
		return tf.getString();
	}

	public int getStartPercent()
	{
		return gs.getValue();
	}

	public int getEndPercent()
	{
		return 100;
	}

	public void setIsSearching(boolean sis)
	{
		this.isSearching = sis;
	}

	public void setResult(String arg0)
	{
		r.setText(arg0);
	}

	public void setPercent(int a, int b)
	{
		gs.setValue(a);
		gs.setLabel(Constants.get(Constants.startPosition) + gs.getValue() + "%");
	}
}
