/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ender
 */
class CombineInputStreamReader
{

	private String filenamePrefix = "";
	private long length = 0L;
	private int partNum = 0;
	private long partLength;
	private String charsetName = "GBK";

	private InputStreamReader isr = null;

	public CombineInputStreamReader(String filenamePrefix, long length, int partNum, long partLength, String charsetName)
	{
		this.filenamePrefix = filenamePrefix;
		this.length = length;
		this.partNum = partNum;
		this.partLength = partLength;
		this.charsetName = charsetName;
	}

	public synchronized int read(char[] a, long cursor) throws IOException
	{
		int r = -1;
		if(cursor >= length)
		{
			return -1;
		}
		if(cursor < 0)
		{
			return -1;
		}
		int index = (int)(cursor / partLength);
		long subCursor = cursor % partLength;
		//System.out.println("index = " + index);
		isr = new InputStreamReader(this.getClass().getResourceAsStream(filenamePrefix + "x" + index + ".txt"), this.charsetName);
		isr.skip(subCursor);
		int rs = isr.read(a);
		if(rs == -1)
		{
			isr.close();
			isr = null;
			return -1;
		}
		r = rs;
		//System.out.println("SubCursor + rs = " + subCursor + " + " + rs + " = " + (subCursor + rs) + " , partLength = " + partLength);
		if((subCursor + rs) == partLength)
		{
			if(rs < a.length && index < (partNum - 1))
			{
				index++;
				isr.close();
				isr = null;
				isr = new InputStreamReader(this.getClass().getResourceAsStream(filenamePrefix + "x" + index + ".txt"), this.charsetName);
				int re = isr.read(a, rs, a.length - rs);
				//System.out.println("re -- " + re + " rs -- " + rs);
				cursor += re;
				r += re;
			}
		}
		cursor += rs;
		isr.close();
		isr = null;
		return r;
	}

	public synchronized void close() throws IOException
	{
		if(isr != null)
		{
			isr.close();
			isr = null;
		}
	}


}
