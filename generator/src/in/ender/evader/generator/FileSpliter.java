package in.ender.evader.generator;

import java.io.IOException;

/**
 * @author yanfengbing
 * @version 1.0
 */
public class FileSpliter
{
	private FileFragmentBuilder fb = null;

	public FileSpliter(FileFragmentBuilder fb)
	{
		this.fb = fb;
	}

	public void split() throws IOException
	{
		this.fb.buildHeadAndTail();
		this.fb.buildContent();
	}
}

