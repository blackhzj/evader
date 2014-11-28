package in.ender.evader.generator;

/**
 * @author yanfengbing
 * @version 1.0
 */

import java.io.IOException;

public interface FileFragment
{
	int readByte(byte[] var1) throws IOException;

	int readChar(char[] var1) throws IOException;

	void close() throws IOException;
}

