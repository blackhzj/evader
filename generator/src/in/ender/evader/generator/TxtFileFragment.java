package in.ender.evader.generator;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yanfengbing
 * @version 1.0
 */
public class TxtFileFragment implements FileFragment
{
	private InputStreamReader isr = null;
	private long pos;
	private long len;
	private long charCursor;

	public TxtFileFragment(InputStreamReader isr, long pos, long len) throws IOException
	{
		this.isr = isr;
		this.pos = pos;
		this.len = len;
		isr.skip(this.pos);
		this.charCursor = 0L;
	}

	public int readByte(byte[] b) throws IOException
	{
		throw new IOException("Can\'t read as byte!");
	}

	public int readChar(char[] c) throws IOException
	{
		if(this.charCursor >= this.len)
		{
			return -1;
		}
		else
		{
			int n = this.isr.read(c);
			if(n == -1)
			{
				return -1;
			}
			else
			{
				int i;
				if(this.charCursor + (long)n > this.len)
				{
					i = (int)(this.len - this.charCursor);

					for(int i1 = i; i1 < c.length; ++i1)
					{
						c[i1] = 0;
					}

					this.charCursor = this.len;
					return i;
				}
				else
				{
					if(n < c.length)
					{
						for(i = n; i < c.length; ++i)
						{
							c[i] = 0;
						}
					}

					this.charCursor += (long)n;
					return n;
				}
			}
		}
	}

	public void close() throws IOException
	{
		if(this.isr != null)
		{
			this.isr.close();
			this.isr = null;
		}

	}
}
