/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import javax.microedition.lcdui.Command;

/**
 * @author Ender
 */
class Parameters
{


	public static final int passwordLength = 6;
	public static final long sleepMilliseconds = 200;
	public static final int bookmarkTitleLength = 10;
	public static final int searchContentLength = 30;

	//for Command
	public static final Command CMD_READ = new Command(Constants.get(Constants.read), Command.SCREEN, 1);
	public static final Command CMD_EXIT = new Command(Constants.get(Constants.exit), Command.EXIT, 1);
	public static final Command CMD_BACK = new Command(Constants.get(Constants.back), Command.BACK, 1);
	public static final Command CMD_CONFIRM = new Command(Constants.get(Constants.confirm), Command.OK, 1);

	//for Alert
	public static final int ALERT_TIMEOUT = 3000;


	//for RecordStore
	public static final String RECORD_NAME = "eBookReader_Ender_xxxx";

	public static final int lightDuration = 1;

}
