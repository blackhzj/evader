/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Ender
 */
class SettingRecord
{

	private int backColor = RGB.white;
	private int frontColor = RGB.black;
	private int fontFace = Font.FACE_SYSTEM;
	private int fontStyle = Font.STYLE_PLAIN;
	private int fontSize = Font.SIZE_MEDIUM;

	private boolean isLight = false;

	private Vector bms = new Vector();

	private Vector chs = new Vector();
	private int lastChapter = 0;

	private String password = "";

	private int autoScrollSpeed = 1;

	public SettingRecord()
	{

	}

	public int getBackColor()
	{
		return backColor;
	}

	public int getFrontColor()
	{
		return frontColor;
	}

	public Font getFont()
	{
		return Font.getFont(fontFace, fontStyle, fontSize);
	}

	public boolean getIsLight()
	{
		return isLight;
	}

	public Vector getBookmarks()
	{
		return bms;
	}

	public Vector getChapterCursors()
	{
		return chs;
	}

	public int getLastChapter()
	{
		return lastChapter;
	}

	public String getPassword()
	{
		return password;
	}

	public int getAutoScrollSpeed()
	{
		return autoScrollSpeed;
	}

	public void setBackColor(int bc)
	{
		this.backColor = bc;
	}

	public void setFrontColor(int fc)
	{
		this.frontColor = fc;
	}

	public void setIsLight(boolean isl)
	{
		this.isLight = isl;
	}

	public void setFont(int ff, int ft, int fs)
	{
		this.fontFace = ff;
		this.fontStyle = ft;
		this.fontSize = fs;
	}

	public void setFont(Font f)
	{
		this.fontFace = f.getFace();
		this.fontStyle = f.getStyle();
		this.fontSize = f.getSize();
	}

	public void addBookmark(Bookmark bm)
	{
		bms.addElement(bm);
	}

	public void deleteBookmark(int i)
	{
		bms.removeElementAt(i);
	}

	public void editBookmark(int i, long cursor, String name)
	{
		((Bookmark)bms.elementAt(i)).setCursor(cursor);
		((Bookmark)bms.elementAt(i)).setName(name);
	}

	public void setChapterCursors(Vector v)
	{
		this.chs = v;
	}

	public void editChapter(int i, long cursor)
	{
		((CursorLocation)chs.elementAt(i)).setChapter(i);
		((CursorLocation)chs.elementAt(i)).setCursor(cursor);
	}

	public void setLastChapter(int i)
	{
		this.lastChapter = i;
	}

	public void setPassword(String passwd)
	{
		this.password = passwd;
	}

	public void setAutoScrollSpeed(int asp)
	{
		this.autoScrollSpeed = asp;
	}

	public void serialize(DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.backColor);
		dos.writeInt(this.frontColor);
		dos.writeInt(fontFace);
		dos.writeInt(fontStyle);
		dos.writeInt(fontSize);
		dos.writeBoolean(isLight);

		dos.writeInt(bms.size());
		for(int i = 0; i < bms.size(); i++)
		{
			dos.writeLong(((Bookmark)bms.elementAt(i)).getCursor());
			dos.writeInt(((Bookmark)bms.elementAt(i)).getChapter());
			dos.writeUTF(((Bookmark)bms.elementAt(i)).getName());
		}
		dos.writeInt(chs.size());
		for(int i = 0; i < chs.size(); i++)
		{
			dos.writeLong(((CursorLocation)chs.elementAt(i)).getCursor());
			dos.writeInt(((CursorLocation)chs.elementAt(i)).getChapter());
		}
		dos.writeInt(this.lastChapter);
		dos.writeUTF(password);
		dos.writeInt(this.autoScrollSpeed);
	}

	public static SettingRecord deserialize(DataInputStream dis) throws IOException
	{
		SettingRecord sr = new SettingRecord();
		sr.setBackColor(dis.readInt());
		sr.setFrontColor(dis.readInt());
		sr.setFont(dis.readInt(), dis.readInt(), dis.readInt());
		sr.setIsLight(dis.readBoolean());
		int bmsSize = dis.readInt();
		for(int i = 0; i < bmsSize; i++)
		{
			sr.getBookmarks().addElement(new Bookmark(dis.readLong(), dis.readInt(), dis.readUTF()));
		}
		int chsSize = dis.readInt();
		for(int i = 0; i < chsSize; i++)
		{
			sr.getChapterCursors().addElement(new CursorLocation(dis.readLong(), dis.readInt()));
		}
		sr.setLastChapter(dis.readInt());
		sr.setPassword(dis.readUTF());
		sr.setAutoScrollSpeed(dis.readInt());

		return sr;
	}

}
