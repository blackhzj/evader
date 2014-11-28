/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

/**
 * @author Ender
 */
class SearchThread extends Thread
{

	private Controller ct;
	private String s = "";

	private long startCursor = 0;
	private long searchCursor = 0;
	private int nc = 1;

	private boolean stop = false;

	public SearchThread(String s, long sc, long sec, int nc, Controller ct)
	{
		this.s = s;
		this.startCursor = sc;
		this.searchCursor = sec;
		this.nc = nc;
		this.ct = ct;
	}

	public void stop()
	{
		this.stop = true;
	}

	public void run()
	{
		boolean found = false;

		String searchResult = Constants.get(Constants.noResult);
		long searchPos = -1;
		try
		{
			Sunday su = new Sunday(s);
			CombineInputStreamReader cisr = new CombineInputStreamReader("/data/" + nc, ct.getBook().getChapterLengths()[nc - 1], ct.getBook().getChapterPartNums()[nc - 1], ct.getBook().getBlockLength(), ct.getBook().getCharsetName());
			char[] x = new char[2048];
			StringBuffer sb = new StringBuffer();
			long j = startCursor;
			for(j = startCursor; j < searchCursor && !stop; j = j + 2048)
			{
				if(cisr.read(x, j) != -1)
				{
					if(j == startCursor + 2048)
					{
						sb.delete(0, 2048 - s.length());
					}
					else
					{
						sb.delete(0, 2048);
					}
					sb.append(x);
					searchPos = su.search(sb.toString());
					if(searchPos >= 0 && searchPos < x.length + s.length())
					{
						found = true;
						System.out.println("j is " + j + " searchPos is " + searchPos + " " + sb.toString().substring((int)searchPos));
						//searchResult = sb.toString().substring((int) searchPos);
						break;
					}
				}
			}
			if(found)
			{
				if(j == startCursor)
				{
					searchPos = j + searchPos;// - s.length();
				}
				else
				{
					searchPos = j + searchPos - s.length();
				}
				char[] res = new char[40];
				cisr.read(res, searchPos);
				searchResult = new String(res);
			}
		}
		catch(Exception ex)
		{
			Alert a = new Alert("Exception", ex.getMessage() + " Search　Error", null, AlertType.ERROR);
			a.setTimeout(Parameters.ALERT_TIMEOUT);
			if(ct.getDisplayable() instanceof SearchForm)
			{
				ct.showAlert(a, ct.getDisplayable());
			}
			else
			{
				ct.showAlert(a, ct.getReadingCanvas());
			}
		}
		if(ct.getDisplayable() instanceof SearchForm)
		{
			((SearchForm)ct.getDisplayable()).setResult(searchResult);
			if(found)
			{
				if(ct.getReadingCanvas().getTotalLength() > 0)
				{
					((SearchForm)ct.getDisplayable()).setPercent((int)(searchPos * 100 / ct.getReadingCanvas().getTotalLength()), (int)(searchCursor * 100 / ct.getReadingCanvas().getTotalLength()));
				}
				((SearchForm)ct.getDisplayable()).removeCommand(SearchForm.CMD_STOPSEARCH);
				((SearchForm)ct.getDisplayable()).removeCommand(SearchForm.CMD_SEARCH);
				((SearchForm)ct.getDisplayable()).addCommand(SearchForm.CMD_GOJUMP);
				((SearchForm)ct.getDisplayable()).addCommand(SearchForm.CMD_SEARCHNEXT);
				ct.setLastSearchString(s);
				ct.setSearchPos(searchPos);
				((SearchForm)ct.getDisplayable()).setIsSearching(false);
			}
			else
			{
				((SearchForm)ct.getDisplayable()).removeCommand(SearchForm.CMD_STOPSEARCH);
				((SearchForm)ct.getDisplayable()).removeCommand(SearchForm.CMD_GOJUMP);
				((SearchForm)ct.getDisplayable()).addCommand(SearchForm.CMD_SEARCH);
				//ct.setSearchPos(0);
				((SearchForm)ct.getDisplayable()).setIsSearching(false);
			}
		}
	}

}
