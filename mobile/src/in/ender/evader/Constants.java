/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

import java.io.DataInputStream;
import java.util.Hashtable;

/**
 * @author Ender
 */
class Constants
{

	public static final String[] args = {"Version 0.1.6", "Book Information", "Book Title: ",
			"Author: ", "Creator: ", "Contact Information: ", "Notes: \n",
			"Read", "Back", "Exit", "OK", "Password", "Please Input Password: ",
			"Password Incorrect", "Password error ", " chances.", " times. \nYou totally have ",
			"Chapters", "About", "Help/License", "Error", "W", "Setting", "Background Color",
			"Font Color", "Font Face", "Font Style", "Font Size", "Background Light",
			"Always On", "Set Password", "Black", "Red", "Blue", "White", "Green", "Yellow",
			"BOLD", "ITALIC", "UNDERLINED", "MEDIUM", "SMALL", "LARGE", "Bookmarks", "Add Bookmark",
			"Delete Bookmark", "Jump To BookMark", "Bookmark Position", "Bookmark Chapter", "Bookmark Title",
			"Search", "Search Result", "[No Result]", "Searching.....", "Jump To...", "Search Next...",
			"Stop Searching", "Start Position: ", "Jump To...", "What you are reading now is \"", "\", chapter ",
			".", "You have read ", "%.", "This chapter have ", " words. ", "Producer: Ender\nEmail: thelastender@gmail.com",
			"Set Auto Scrolling Speed:\n",
			"You can press key '*' to start auto scrolling, and press key '#' to stop.\nThe value is a one-digit number which is more than 0 and less than 10.",
			"Help", "Just press the button to test and learn how to use this software.\nIt is very easy.\nThe important skill is that you can press '*' to start auto scrolling, and then press '#' to stop.\nEnjoy it.",
			"License", "NO COMMERCIAL USE.\nIf you want to deliver this software, you should include its license file.\nAll Rights Reserved."};


	public static final int version = 0;// "Version 0.1.6";
	public static final int info = 1;//"Book Information";
	public static final int title = 2;//"Book Title: ";
	public static final int author = 3;//"Author: ";
	public static final int creator = 4;//"Creator: ";
	public static final int email = 5;//"Contact Information: ";
	public static final int notes = 6;//"Notes: \n";
	public static final int read = 7;//"Read";
	public static final int back = 8;//"Back";
	public static final int exit = 9;//"Exit";
	public static final int confirm = 10;//"OK";
	public static final int password = 11;//"Password";
	public static final int inputPassword = 12;//"Please Input Password: ";
	public static final int wrongPassword = 13;//"Password Incorrect";
	public static final int wrongPasswordTimess = 14;//"Password error ";
	public static final int wrongPasswordTimest = 15;//" chances.";
	public static final int wrongPasswordTimese = 16;//" times. \nYou totally have ";
	public static final int chapter = 17;//"Chapters";
	public static final int about = 18;//"About";
	public static final int helpLicense = 19;//"Help/License";
	public static final int error = 20;//"Error";
	public static final int constantsWidth = 21;//'W';
	public static final int setting = 22;//"Setting";
	public static final int backColor = 23;//"Background Color";
	public static final int frontColor = 24;//"Font Color";
	public static final int fontFace = 25;//"Font Face";
	public static final int fontStyle = 26;//"Font Style";
	public static final int fontSize = 27;//"Font Size";
	public static final int ligth = 28;//"Background Light";
	public static final int isLight = 29;//"Always On";
	public static final int setPassword = 30;//"Set Password";
	public static final int[] cs = {31, 32, 33, 34, 35, 36};//{"Black", "Red", "Blue", "White", "Green", "Yellow"};
	public static final int[] fts = {37, 38, 39};//{"BOLD", "ITALIC", "UNDERLINED"};
	public static final int[] fss = {40, 41, 42};//{"MEDIUM", "SMALL", "LARGE"};

	public static final int bookmark = 43;//"Bookmarks";
	public static final int addBookmark = 44;//"Add Bookmark";
	public static final int deleteBookmark = 45;//"Delete Bookmark";
	public static final int jumpToBookmark = 46;//"Jump To BookMark";
	public static final int bookmarkPosition = 47;//"Bookmark Position";
	public static final int bookmarkChapter = 48;//"Bookmark Chapter";
	public static final int bookmarkTitle = 49;//"Bookmark Title";
	public static final int search = 50;//"Search";
	public static final int searchResult = 51;//"Search Result";
	public static final int noResult = 52;//"[No Result]";
	public static final int searching = 53;//"Searching.....";
	public static final int gojump = 54;//"Jump To...";
	public static final int searchNext = 55;//"Search Next...";
	public static final int stopSearch = 56;//"Stop Searching";
	public static final int startPosition = 57;//"Start Position: ";
	public static final int jump = 58;//"Jump To...";
	public static final int whatyoureads = 59;//"What you are reading now is \"";
	public static final int whatyoureade = 60;//"\", chapter ";
	public static final int whatyoureadchp = 61;//".";
	public static final int whatyoureadps = 62;//"You have read ";
	public static final int whatyoureadpe = 63;//"%.";
	public static final int whatyoureadchcs = 64;//"This chapter have " ;
	public static final int whatyoureadchce = 65;//" words. ";
	public static final int programer = 66;//"Producer: Ender\nEmail: thelastender@gmail.com";

	public static final int autoScrollSpeed = 67;
	public static final int autoScrollNote = 68;

	public static final int help = 69;
	public static final int helpContent = 70;
	public static final int license = 71;
	public static final int licenseContent = 72;

	protected static Hashtable v = new Hashtable();

	public static final int length = 73;

	public Constants()
	{
		//v = new Hashtable();
	}

	public static String get(int key)
	{
		return (String)v.get(new Integer(key));
	}

	static
	{
		String locale = System.getProperty("microedition.locale");
		System.out.println("What is it: " + locale);
		try
		{
			DataInputStream dis = new DataInputStream(locale.getClass().getResourceAsStream("/locale/" + locale + ".strings"));
			for(int i = 0; i < Constants.length; i++)
			{
				Constants.v.put(new Integer(i), dis.readUTF());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			Constants.v.clear();
			for(int j = 0; j < Constants.length; j++)
			{
				Constants.v.put(new Integer(j), Constants.args[j]);
			}
		}
	}


}
