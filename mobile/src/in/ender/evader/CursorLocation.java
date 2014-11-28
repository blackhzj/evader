/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

/**
 * @author Ender
 */
class CursorLocation
{

	private long cursor = 0;
	private int chapter = 0;

	public CursorLocation(long c, int n)
	{
		this.cursor = c;
		this.chapter = n;
	}

	public void setCursor(long c)
	{
		this.cursor = c;
	}

	public void setChapter(int n)
	{
		this.chapter = n;
	}

	public long getCursor()
	{
		return cursor;
	}

	public int getChapter()
	{
		return chapter;
	}

}
