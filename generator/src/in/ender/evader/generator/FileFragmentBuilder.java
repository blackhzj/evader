package in.ender.evader.generator;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author yanfengbing
 * @version 1.0
 */
public interface FileFragmentBuilder extends Iterator<FileFragment>
{
	void buildHeadAndTail() throws IOException;

	void buildContent() throws IOException;
}
