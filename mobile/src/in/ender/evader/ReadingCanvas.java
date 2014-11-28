package in.ender.evader;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import java.io.IOException;
import java.util.Vector;

class ReadingCanvas extends Canvas
{

	private Controller ct = null;
	private int nowChapter = 1;
	private long nowCursor = 0L;
	private long totalLength = 0L;

	private char[] content = null;
	private int length = 30;

	private String errorMessage = "";

	private int backColor = 0xFFFFFF;
	private int frontColor = 0;
	private Font font = Font.getDefaultFont();

	private int rows;
	private int columns;
	private int dx;
	private int dy;

	private int nextLength = 30;
	private int nextLineLength = 10;
	private int lastLineLength = 0;

	private int predy = 0;

	private boolean previous = false;

	private CombineInputStreamReader cisr = null;

	public static final int SWIDTH = 3;
	public static Command CMD_HELP = new Command(Constants.get(Constants.helpLicense), Command.SCREEN, 7);
	public static final Command CMD_ABOUT = new Command(Constants.get(Constants.about), Command.SCREEN, 2);
	public static final Command CMD_SETTING = new Command(Constants.get(Constants.setting), Command.SCREEN, 3);

	public static final Command CMD_BOOKMARK = new Command(Constants.get(Constants.bookmark), Command.SCREEN, 4);
	public static final Command CMD_SEARCH = new Command(Constants.get(Constants.search), Command.SCREEN, 5);
	public static final Command CMD_JUMP = new Command(Constants.get(Constants.jump), Command.SCREEN, 6);

	public static final Command CMD_IOERROR = new Command(Constants.get(Constants.error), Command.SCREEN, 8);

	public ReadingCanvas(String title, Controller ct, int chn, long cun, long totalLength, int partNum, long partLength, String charsetName)
	{
		this.ct = ct;
		this.nowChapter = chn + 1;
		this.nowCursor = cun;
		this.totalLength = totalLength;
		this.setTitle(title);

		this.addCommand(Parameters.CMD_BACK);
		this.addCommand(CMD_ABOUT);
		this.addCommand(CMD_SETTING);
		this.addCommand(CMD_BOOKMARK);
		this.addCommand(CMD_SEARCH);
		this.addCommand(CMD_JUMP);
		this.addCommand(CMD_HELP);
		this.setCommandListener(ct);

		this.cisr = new CombineInputStreamReader("/data/" + this.nowChapter, this.totalLength, partNum, partLength, charsetName);
		ct.setCursor(this.nowChapter - 1, this.nowCursor);

		this.setFullScreenMode(false);
	}

	public int getNowChapter()
	{
		return this.nowChapter;
	}

	public long getNowCursor()
	{
		return this.nowCursor;
	}

	public String getNowString()
	{
		return new String(content).substring(0, Parameters.bookmarkTitleLength);
	}

	public long getTotalLength()
	{
		return totalLength;
	}

	public int getPercent()
	{
		if(totalLength <= 0)
		{
			return 100;
		}
		int p = (int)(100 * nowCursor / totalLength);
		return p;
	}

	public void setPercent(int p)
	{
		this.nowCursor = (this.totalLength * p) / 100;
	}

	public void setCursor(long c)
	{
		this.nowCursor = c;
		if(nowCursor >= totalLength)
		{
			nowCursor = totalLength - content.length / 2;
		}
		if(nowCursor < 0)
		{
			nowCursor = 0;
		}
	}

	public void updateView()
	{
		backColor = ct.getSettingRecord().getBackColor();
		frontColor = ct.getSettingRecord().getFrontColor();
		font = ct.getSettingRecord().getFont();
		dx = font.charWidth(Constants.get(Constants.constantsWidth).charAt(0));
		dy = font.getHeight() + 1;
		rows = (this.getHeight() - (ReadingCanvas.SWIDTH * 2)) / dy;
		columns = (this.getWidth() - (ReadingCanvas.SWIDTH * 2)) / dx;
		length = rows * columns;
		nextLength = length;

		content = new char[length];
		if(nowCursor >= totalLength)
		{
			nowCursor = totalLength - content.length / 2;
		}
		if(nowCursor < 0)
		{
			nowCursor = 0;
		}
		System.out.println(dx + " " + dy + " " + rows + " " + columns + " " + length);
	}

	protected void paint(Graphics g)
	{
		g.setColor(this.backColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(this.frontColor);
		//g.drawLine(0, 0, 30, 20);
		System.out.println("Now Cursor:\t" + nowCursor);
		g.setFont(this.font);

		try
		{
			int l = 0;
			if(previous)
			{
				if(nowCursor > length)
				{
					l = cisr.read(content, nowCursor - length);
				}
				else
				{
					l = cisr.read(content, 0);
				}
			}
			else
			{
				l = cisr.read(content, nowCursor);
			}
			if(l >= 0 && l < length)
			{
				for(int i = l; i < length; i++)
				{
					content[i] = ' ';
				}
			}
		}
		catch(IOException e)
		{
			errorMessage = "IOException Reading File: " + e.getMessage();
			ct.commandAction(ReadingCanvas.CMD_IOERROR, this);
			return;
		}
		catch(java.lang.OutOfMemoryError e)
		{
			e.printStackTrace();
			errorMessage = "java.lang.OutOfMemoryError Reading File: " + e.getMessage();
			ct.commandAction(ReadingCanvas.CMD_IOERROR, this);
			return;
		}
		catch(java.lang.Exception e)
		{
			e.printStackTrace();
			errorMessage = "Exception Reading File: " + e.getMessage();
			ct.commandAction(ReadingCanvas.CMD_IOERROR, this);
			return;
		}

		System.out.println("Length is:\t" + content.length + " rows x columns = " + rows + " x " + columns + " = " + rows * columns);
		System.out.println();
		this.draw(g);

		previous = false;
		g.setColor(this.backColor);
		g.fillRect(0, 0, this.getWidth(), ReadingCanvas.SWIDTH);
		g.setColor(0x9932CD);
		g.fillRoundRect(0, 0, (int)(this.getWidth() * this.getPercent() / 100), ReadingCanvas.SWIDTH, 1, 1);

	}

	public void keyPressed(int keyCode)
	{
		int action = this.getGameAction(keyCode);
		System.out.println("KeyCode:\t" + keyCode + " Action:\t" + action);

		switch(action)
		{
			case Canvas.UP:
			case Canvas.LEFT:

				previous = true;
				predy = 0;

				break;
			case Canvas.DOWN:
			case Canvas.RIGHT:
			case Canvas.FIRE:

				previous = false;

				nowCursor += nextLength;
				predy = 0;
				if(nowCursor > this.totalLength)
				{
					nowCursor -= nextLength;
				}
				if(nowCursor < 0)
				{
					nowCursor = 0;
				}
				break;

			default:
				switch(keyCode)
				{
					case Canvas.KEY_STAR:
						System.out.println("What's wrong! Canvas.KEY_STAR");
						ct.getMonitor().setAutoScroll(true);
						//ct.getMonitor().go();
						//predy = 0;
						break;
					case Canvas.KEY_POUND:
						System.out.println("What's wrong! Canvas.KEY_POUND");
						ct.getMonitor().setAutoScroll(false);
						predy = 0;
						break;
				}
				break;
		}
		repaint();
		serviceRepaints();
	}
	
	/*public void update() {
		this.repaint();
		this.serviceRepaints();
	}*/

	public void scroll()
	{
		if(nowCursor + nextLength > this.totalLength)
		{

		}
		else
		{
			predy -= ct.getMonitor().getAutoScrollSpeed();
		}
		if(predy <= -(font.getHeight()))
		{
			lastLineLength = nextLineLength;
			nowCursor += nextLineLength;
			if(nowCursor > this.totalLength)
			{
				nowCursor -= nextLength;
				if(nowCursor < 0)
				{
					nowCursor = 0;
				}
			}
			predy += font.getHeight();
		}
		repaint();
		serviceRepaints();
	}

	public void normal()
	{
		predy = 0;
		repaint();
		serviceRepaints();
	}

	protected void hideNotify()
	{
		System.out.println("Hide Notify");
		//predy = 0;
		ct.getMonitor().pause();

		//ct.setCursor(nowChapter - 1, nowCursor);
	}

	protected void showNotify()
	{
		System.out.println("Show Notify");
		//predy = 0;
	    /*nowCursor -= lastLineLength;
        if(nowCursor < 0) {
          nowCursor = 0;
        }*/
		ct.getMonitor().go();
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void close()
	{
		ct.setCursor(nowChapter - 1, nowCursor);
		try
		{
			if(cisr != null)
			{
				this.cisr.close();
				cisr = null;
			}
		}
		catch(IOException e)
		{
			errorMessage = e.getMessage();
			ct.commandAction(ReadingCanvas.CMD_IOERROR, this);
		}
	}

	private void draw(Graphics g)
	{

		if(this.previous)
		{
			int i = 0;
			int x = 0;
			int y = 0;
			Vector map = new Vector();
			Vector mmm = new Vector();
			char[] ins = new char[columns];
			//System.out.println(new String(content) + "\n------------------------------------\n");
			int ei = 0;
			int len = length;
			if(nowCursor < length)
			{
				len = (int)nowCursor;
			}
			while(i < len)
			{
				if(content[i] <= 0x20)
				{
					if(content[i] != 0x0A)
					{
						ins[y] = 0x20;
					}
					else
					{
						ins[y] = 0x20;
						y = columns - 1;
					}
				}
				else
				{
					ins[y] = content[i];
				}
				if(y == columns - 1)
				{
					x++;
					y = 0;
					map.addElement(ins);
					//System.out.println(new String(ins));
					//System.out.println(ei);
					ins = new char[columns];
					mmm.addElement(new Integer(ei));
					ei = 0;

				}
				else
				{
					y++;
				}
				i++;
				ei++;
			}
			if(ei != 1)
			{
				map.addElement(ins);
				mmm.addElement(new Integer(ei - 1));
				//System.out.println(new String(ins));
				//System.out.println(ei - 1);
			}
			System.out.println("\nx = " + x + " y = " + y + " i = " + i);

			if(map.size() < rows)
			{
				predy = 0;
				int ii = 0;
				for(int xi = 0; xi < rows; xi++)
				{
					for(int yi = 0; yi < columns; yi++)
					{
						if(content[ii] < 0x20)
						{
							if(content[ii] != 0x0A)
							{
								content[ii] = 0x20;
							}
							else
							{
								content[ii] = 0x20;
								yi = columns - 1;
							}
						}
						if(xi < rows)
						{
							g.drawChar(content[ii], ReadingCanvas.SWIDTH + yi * dx, ReadingCanvas.SWIDTH + dy * xi + predy, Graphics.TOP | Graphics.LEFT);
							ii++;
						}

					}
					if(xi == 0)
					{
						nextLineLength = ii;
					}
				}
				nextLength = ii;
				nowCursor = 0;
			}
			else
			{
				int st = map.size() - rows;
				int nj = map.size();

				int lo = 0;
				nextLineLength = ((Integer)mmm.elementAt(st)).intValue();
				for(int j = st, l = 0; j < nj; j++, l++)
				{
					char[] t = (char[])map.elementAt(j);
					lo = lo + ((Integer)mmm.elementAt(j)).intValue();
					for(int k = 0; k < t.length; k++)
					{
						g.drawChar(t[k], ReadingCanvas.SWIDTH + k * dx, ReadingCanvas.SWIDTH + dy * l + predy, Graphics.TOP | Graphics.LEFT);
					}
				}
				System.out.println("Lo:\t" + lo);
				nextLength = lo;
				nowCursor = nowCursor - lo;
			}
		}
		else
		{
			int i = 0;
			for(int x = 0; x < rows; x++)
			{
				for(int y = 0; y < columns; y++)
				{
					if(content[i] < 0x20)
					{
						if(content[i] != 0x0A)
						{
							content[i] = 0x20;
						}
						else
						{
							content[i] = 0x20;
							y = columns - 1;
						}
					}
					if(x < rows)
					{
						g.drawChar(content[i], ReadingCanvas.SWIDTH + y * dx, ReadingCanvas.SWIDTH + dy * x + predy, Graphics.TOP | Graphics.LEFT);
						i++;
					}

				}
				if(x == 0)
				{
					nextLineLength = i;
				}
			}
			nextLength = i;
		}
	}

}
