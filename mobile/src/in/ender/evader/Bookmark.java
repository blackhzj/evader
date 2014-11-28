/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

/**
 * @author Ender
 */
class Bookmark
{

	private long cursor = 0;
	private int chapter = 0;
	private String name = "";

	public Bookmark(long c, int t, String n)
	{
		this.cursor = c;
		this.chapter = t;
		this.name = n;
	}

	public void setCursor(long c)
	{
		this.cursor = c;
	}

	public void setChapter(int t)
	{
		this.chapter = t;
	}

	public void setName(String n)
	{
		this.name = n;
	}

	public long getCursor()
	{
		return cursor;
	}

	public int getChapter()
	{
		return chapter;
	}

	public String getName()
	{
		return name;
	}
}
