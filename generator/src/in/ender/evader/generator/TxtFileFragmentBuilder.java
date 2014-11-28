package in.ender.evader.generator;

import java.io.*;

/**
 * @author yanfengbing
 * @version 1.0
 */
public class TxtFileFragmentBuilder implements FileFragmentBuilder
{
	private long blockLength = 0L;
	private long cursor = 0L;
	private long length = 0L;
	private String filename;
	private String charsetName;

	public TxtFileFragmentBuilder(String filename, long blockLength, String charsetName) throws UnsupportedEncodingException, FileNotFoundException
	{
		this.filename = filename;
		this.blockLength = blockLength;
		this.charsetName = charsetName;
	}

	public void buildContent() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(new FileInputStream(this.filename), this.charsetName);
		char[] c = new char[1024];

		int l1;
		for(boolean l = false; (l1 = isr.read(c)) != -1; this.length += (long)l1)
		{
		}
	}

	public void buildHeadAndTail()
	{
	}

	public boolean hasNext()
	{
		return this.cursor < this.length;
	}

	public FileFragment next()
	{
		TxtFileFragment tff = null;

		try
		{
			tff = new TxtFileFragment(new InputStreamReader(new FileInputStream(this.filename), this.charsetName), this.cursor, this.blockLength);
			this.cursor += this.blockLength;
		}
		catch(UnsupportedEncodingException var3)
		{
			var3.printStackTrace();
		}
		catch(FileNotFoundException var4)
		{
			var4.printStackTrace();
		}
		catch(IOException var5)
		{
			var5.printStackTrace();
		}

		return tff;
	}

	public void remove()
	{
		System.out.println("Not Support");
	}
}
