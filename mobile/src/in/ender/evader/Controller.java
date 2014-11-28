package in.ender.evader;

import com.nokia.mid.ui.DeviceControl;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.*;
import java.io.*;
import java.util.Timer;
import java.util.Vector;

class Controller implements CommandListener
{

	private Book book = null;
	private Evader midlet = null;
	private Displayable nowDisplaying = null;

	private PasswordForm passwordForm = null;
	private InfoForm infoForm = null;
	private ChapterList chapterList = null;
	private ReadingCanvas readingCanvas = null;
	private Timer bgtimer = null;
	private Monitor mm = null;

	private SettingRecord sr = null;
	private RecordStore rs = null;

	private int bookmarkIndex = 0;
	private int jumpPercent = 0;

	private long searchPos = 0;
	private String lastSearchString = "";
	private SearchThread st = null;

	private int atemptPasswordTimes = 0;

	private boolean supportNokiaUI = false;

	public Controller(Evader midlet)
	{
		this.midlet = midlet;
		DataInputStream dis = null;
		int chapterNum = 1;
		try
		{
			try
			{
				//may be we can also use System property "com.nokia.mid.ui.version"
				Class.forName("com.nokia.mid.ui.FullCanvas");
				supportNokiaUI = true;
			}
			catch(Exception ex)
			{
				supportNokiaUI = false;
			}
			dis = new DataInputStream(this.getClass().getResourceAsStream("/data/0.txt"));
			String title = dis.readUTF();
			String author = dis.readUTF();
			String creator = dis.readUTF();
			String email = dis.readUTF();
			String notes = dis.readUTF();
			String charsetName = dis.readUTF();
			chapterNum = dis.readInt();
			long blockLength = dis.readLong();
			String[] chs = new String[chapterNum];
			long[] chl = new long[chapterNum];
			int[] cpn = new int[chapterNum];
			long[] lpl = new long[chapterNum];
			for(int i = 0; i < chapterNum; i++)
			{
				chs[i] = dis.readUTF();
				chl[i] = dis.readLong();
				cpn[i] = dis.readInt();
				lpl[i] = dis.readLong();
			}
			dis.close();
			dis = null;
			book = new Book(title, author, creator, email, notes, charsetName, blockLength,
					chs, chl, cpn, lpl);
		}
		catch(Exception ex)
		{
			Alert a = new Alert("Exception", ex.getMessage() + " Can't Read Book", null, AlertType.ERROR);
			a.setTimeout(Parameters.ALERT_TIMEOUT);
			midlet.showAlert(a, null);
			try
			{
				midlet.destroyApp(false);
				midlet.notifyDestroyed();
			}
			catch(MIDletStateChangeException ex1)
			{
				ex1.printStackTrace();
			}
		}

		infoForm = new InfoForm(this,
				book.getTitle(), book.getAuthor(), book.getCreator(), book.getEmail(), book.getNotes());
		chapterList = new ChapterList(this, book.getChapterNames());

		nowDisplaying = infoForm;
		try
		{
			rs = RecordStore.openRecordStore(Parameters.RECORD_NAME, false);
			RecordEnumeration re = rs.enumerateRecords(null, null, false);
			if(re.numRecords() > 0)
			{
				byte[] data = re.nextRecord();
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				dis = new DataInputStream(bis);
				sr = SettingRecord.deserialize(dis);
				dis.close();
				dis = null;
			}
			else
			{
				throw new RecordStoreNotFoundException();
			}
			rs.closeRecordStore();
			rs = null;
		}
		catch(Exception ex)
		{
			sr = new SettingRecord();
			Vector chsv = new Vector();
			for(int i = 0; i < chapterNum; i++)
			{
				chsv.addElement(new CursorLocation(0, i));
			}
			sr.setChapterCursors(chsv);
			System.out.println("NEW Setting Record");
			try
			{
				if(rs != null)
				{
					rs.closeRecordStore();
					rs = null;
				}
				RecordStore.deleteRecordStore(Parameters.RECORD_NAME);
				rs = RecordStore.openRecordStore(Parameters.RECORD_NAME, true);
				//rs.closeRecordStore();

			}
			catch(RecordStoreException exx)
			{
				Alert a = new Alert("RecordStoreException", exx.getMessage() + " Can't Create RMS", null, AlertType.ERROR);
				System.out.println(exx.getMessage());
				exx.printStackTrace();
				rs = null;
				a.setTimeout(Parameters.ALERT_TIMEOUT);
				nowDisplaying = infoForm;
				midlet.showAlert(a, nowDisplaying);
			}
			//Alert a = new Alert("Exception",ex.getMessage() + " Can't Read RMS",null,AlertType.ERROR);
			//a.setTimeout(Parameters.ALERT_TIMEOUT);
			//nowDisplaying = infoForm;
			//midlet.showAlert(a, nowDisplaying);
		}
		if(rs != null)
		{
			try
			{
				rs.closeRecordStore();
				rs = null;
			}
			catch(RecordStoreNotOpenException ex)
			{
				ex.printStackTrace();
			}
			catch(RecordStoreException ex)
			{
				ex.printStackTrace();
			}
		}
		System.out.println(sr.getLastChapter());
		chapterList.setSelectedIndex(sr.getLastChapter(), true);
		bgtimer = new Timer();
		mm = new Monitor(false, this);
		bgtimer.scheduleAtFixedRate(mm, 0, Parameters.sleepMilliseconds);
		if(sr.getPassword().length() > 0)
		{
			passwordForm = new PasswordForm(this);
			nowDisplaying = passwordForm;
		}
		if(supportNokiaUI)
		{
			if(sr.getIsLight())
			{
				DeviceControl.setLights(0, 100);
			}
		}
	}

	public SettingRecord getSettingRecord()
	{
		return sr;
	}

	public Displayable getDisplayable()
	{
		return nowDisplaying;
	}

	public Monitor getMonitor()
	{
		return mm;
	}

	public Book getBook()
	{
		return book;
	}

	public ReadingCanvas getReadingCanvas()
	{
		return this.readingCanvas;
	}

	public void setParameters(int back, int front, Font f, boolean isLight, String passwd, int asp)
	{
		if(readingCanvas != null)
		{
			sr.setBackColor(back);
			sr.setFrontColor(front);
			sr.setFont(f);
			sr.setIsLight(isLight);
			//mm.setIsLight(isLight);
			System.out.println(sr.getIsLight());
			sr.setPassword(passwd);
			sr.setAutoScrollSpeed(asp);
			readingCanvas.updateView();
			nowDisplaying = readingCanvas;
			if(supportNokiaUI)
			{
				if(sr.getIsLight())
				{
					DeviceControl.setLights(0, 100);
				}
			}
			this.midlet.setDisplayable();
		}
	}

	public void light()
	{
		this.midlet.light();
	}

	public void setCursor(int chapter, long cursor)
	{
		if(sr != null)
		{
			((CursorLocation)sr.getChapterCursors().elementAt(chapter)).setCursor(cursor);
			sr.setLastChapter(chapter);
		}
	}

	public void setNowDisplaying(Displayable dis)
	{
		this.nowDisplaying = dis;
		this.midlet.setDisplayable();
	}

	public void saveSetting()
	{
		try
		{
			if(readingCanvas != null)
			{
				readingCanvas.close();
				readingCanvas = null;
			}
			rs = RecordStore.openRecordStore(Parameters.RECORD_NAME, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			sr.serialize(dos);
			byte[] data = baos.toByteArray();
			RecordEnumeration re = rs.enumerateRecords(null, null, true);
			while(re.hasNextElement())
			{
				rs.deleteRecord(re.nextRecordId());//rs.setRecord(re.nextRecordId(), data, 0, data.length);
			}
			rs.addRecord(data, 0, data.length);
			//rs.closeRecordStore();
		}
		catch(RecordStoreException ex)
		{
			Alert a = new Alert("RecordStoreException", ex.getMessage() + " Can't save RMS", null, AlertType.ERROR);
			a.setTimeout(Parameters.ALERT_TIMEOUT);
			nowDisplaying = chapterList;
			midlet.showAlert(a, chapterList);
		}
		catch(IOException ex)
		{
			Alert a = new Alert("IOException", ex.getMessage() + " Can't save RMS", null, AlertType.ERROR);
			a.setTimeout(Parameters.ALERT_TIMEOUT);
			nowDisplaying = chapterList;
			midlet.showAlert(a, chapterList);
		}
		catch(Exception ex)
		{
			Alert a = new Alert("Exception", ex.getMessage() + " Can't save RMS", null, AlertType.ERROR);
			a.setTimeout(Parameters.ALERT_TIMEOUT);
			nowDisplaying = chapterList;
			midlet.showAlert(a, chapterList);
		}
		finally
		{
			if(rs != null)
			{
				try
				{
					rs.closeRecordStore();
					rs = null;
				}
				catch(RecordStoreNotOpenException ex)
				{
					ex.printStackTrace();
				}
				catch(RecordStoreException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	public void showAlert(Alert alert, Displayable nextDisplayable)
	{
		midlet.showAlert(alert, nextDisplayable);
	}

	public void setLastSearchString(String s)
	{
		this.lastSearchString = s;
	}

	public void setSearchPos(long searchPos)
	{
		this.searchPos = searchPos;
	}


	public void commandAction(Command cmd, Displayable dis)
	{
		if(cmd.equals(Parameters.CMD_EXIT))
		{
			try
			{
				bgtimer.cancel();
				midlet.destroyApp(false);
				midlet.notifyDestroyed();
			}
			catch(MIDletStateChangeException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			if(dis instanceof PasswordForm)
			{
				if(cmd.equals(Parameters.CMD_CONFIRM))
				{
					if(((PasswordForm)dis).getPassword().trim().equals(sr.getPassword()))
					{
						nowDisplaying = infoForm;
						midlet.setDisplayable();
					}
					else
					{
						atemptPasswordTimes++;
						Alert a = new Alert(Constants.get(Constants.wrongPassword), Constants.get(Constants.wrongPasswordTimess) + atemptPasswordTimes + Constants.get(Constants.wrongPasswordTimese) + Parameters.passwordLength + Constants.get(Constants.wrongPasswordTimest), null, AlertType.ERROR);
						a.setTimeout(Parameters.ALERT_TIMEOUT * 2);
						nowDisplaying = dis;
						midlet.showAlert(a, nowDisplaying);
						if(atemptPasswordTimes >= Parameters.passwordLength)
						{
							this.commandAction(Parameters.CMD_EXIT, dis);
						}
					}
				}

			}
			else if(dis instanceof InfoForm)
			{
				if(cmd.equals(Parameters.CMD_READ))
				{
					System.out.println("InfoForm OK Command");
					nowDisplaying = chapterList;
					midlet.setDisplayable();
				}
			}
			else if(dis instanceof ChapterList)
			{
				if(cmd.equals(Parameters.CMD_READ))
				{ //|| cmd.equals(List.SELECT_COMMAND)) {
					int si = ((List)dis).getSelectedIndex();
					if(readingCanvas != null)
					{
						readingCanvas.close();
						readingCanvas = null;
					}
					readingCanvas = new ReadingCanvas(book.getChapterNames()[si], this, si, ((CursorLocation)sr.getChapterCursors().elementAt(si)).getCursor(), book.getChapterLengths()[si], book.getChapterPartNums()[si], book.getBlockLength(), book.getCharsetName());
					readingCanvas.updateView();
					nowDisplaying = readingCanvas;
					midlet.setDisplayable();

				}
				else if(cmd.equals(Parameters.CMD_BACK))
				{
					nowDisplaying = infoForm;
					midlet.setDisplayable();
				}
			}
			else if(dis instanceof ReadingCanvas)
			{
				mm.pause();
				if(cmd.equals(ReadingCanvas.CMD_ABOUT))
				{
					this.setNowDisplaying(new AboutForm(this));

				}
				else if(cmd.equals(Parameters.CMD_BACK))
				{
					nowDisplaying = chapterList;
					chapterList.setSelectedIndex(((ReadingCanvas)dis).getNowChapter() - 1, true);
					midlet.setDisplayable();
				}
				else if(cmd.equals(ReadingCanvas.CMD_IOERROR))
				{
					Alert a = new Alert(((ReadingCanvas)dis).getErrorMessage());
					a.setTimeout(Parameters.ALERT_TIMEOUT);
					nowDisplaying = chapterList;
					midlet.showAlert(a, chapterList);
				}
				else if(cmd.equals(ReadingCanvas.CMD_SETTING))
				{
					nowDisplaying = new SettingForm(this, sr.getBackColor(), sr.getFrontColor(), sr.getFont(), sr.getIsLight(), sr.getPassword(), sr.getAutoScrollSpeed());
					this.midlet.setDisplayable();
				}
				else if(cmd.equals(ReadingCanvas.CMD_BOOKMARK))
				{
					String[] bms = new String[sr.getBookmarks().size()];
					for(int i = 0; i < bms.length; i++)
					{
						bms[i] = ((Bookmark)sr.getBookmarks().elementAt(i)).getName();
					}
					nowDisplaying = new BookmarkList(this, bms);
					this.midlet.setDisplayable();
				}
				else if(cmd.equals(ReadingCanvas.CMD_JUMP))
				{
					jumpPercent = ((ReadingCanvas)dis).getPercent();
					nowDisplaying = new JumpForm(this, jumpPercent);
					this.midlet.setDisplayable();
				}
				else if(cmd.equals(ReadingCanvas.CMD_SEARCH))
				{
					nowDisplaying = new SearchForm(lastSearchString, searchPos, this);
					this.midlet.setDisplayable();
				}
				else if(cmd.equals(ReadingCanvas.CMD_HELP))
				{
					nowDisplaying = new HelpLicenseForm(this);
					this.midlet.setDisplayable();
				}
				else
				{
					System.out.println("Hahahahahahahahhahahahahhahahahahahahahah " + cmd.getLabel() + " " + cmd.getCommandType());
				}
			}
			else if(dis instanceof BookmarkList)
			{
				if(cmd.equals(Parameters.CMD_BACK))
				{
					nowDisplaying = readingCanvas;
					midlet.setDisplayable();
				}
				else if(cmd.equals(BookmarkList.CMD_ADDBOOKMARK))
				{
					bookmarkIndex = ((BookmarkList)dis).getSelectedIndex();
					nowDisplaying = new AddBookmarkForm(this, readingCanvas.getNowCursor(), readingCanvas.getNowChapter(), readingCanvas.getNowString());
					this.midlet.setDisplayable();
				}
				else if(cmd.equals(BookmarkList.CMD_DELETEBOOKMARK))
				{
					bookmarkIndex = ((BookmarkList)dis).getSelectedIndex();
					if(bookmarkIndex >= 0 && bookmarkIndex < ((BookmarkList)dis).size())
					{
						sr.getBookmarks().removeElementAt(bookmarkIndex);
						String[] bms = new String[sr.getBookmarks().size()];
						for(int i = 0; i < bms.length; i++)
						{
							bms[i] = ((Bookmark)sr.getBookmarks().elementAt(i)).getName();
						}
						nowDisplaying = new BookmarkList(this, bms);
						bookmarkIndex--;
						if(bookmarkIndex >= 0 && bookmarkIndex < ((BookmarkList)nowDisplaying).size())
						{
							((BookmarkList)nowDisplaying).setSelectedIndex(bookmarkIndex, true);
						}
						this.midlet.setDisplayable();
					}
				}
				else if(cmd.equals(BookmarkList.CMD_JUMPTOBOOKMARK))
				{
					int si = ((BookmarkList)dis).getSelectedIndex();
					if(si >= 0 && si < ((BookmarkList)dis).size())
					{
						if(readingCanvas != null)
						{
							readingCanvas.close();
							readingCanvas = null;
						}
						String tt = ((Bookmark)sr.getBookmarks().elementAt(si)).getName();
						int ci = ((Bookmark)sr.getBookmarks().elementAt(si)).getChapter();
						long cl = ((Bookmark)sr.getBookmarks().elementAt(si)).getCursor();
						readingCanvas = new ReadingCanvas(book.getChapterNames()[ci], this, ci, cl, book.getChapterLengths()[ci], book.getChapterPartNums()[ci], book.getBlockLength(), book.getCharsetName());
						readingCanvas.updateView();
						nowDisplaying = readingCanvas;
						midlet.setDisplayable();

					}
				}
				else if(cmd.equals(List.SELECT_COMMAND))
				{
					bookmarkIndex = ((BookmarkList)dis).getSelectedIndex();
					if(bookmarkIndex >= 0 && bookmarkIndex < ((BookmarkList)dis).size())
					{
						String tt = ((Bookmark)sr.getBookmarks().elementAt(bookmarkIndex)).getName();
						tt = tt.substring(0, tt.lastIndexOf('\n'));
						int ci = ((Bookmark)sr.getBookmarks().elementAt(bookmarkIndex)).getChapter() + 1;
						long cl = ((Bookmark)sr.getBookmarks().elementAt(bookmarkIndex)).getCursor();
						nowDisplaying = new EditBookmarkForm(this, cl, ci, tt);
						midlet.setDisplayable();
					}
				}
				else
				{
					System.out.println("Unknown Command: " + cmd.getCommandType());
				}
			}
			else if(dis instanceof AddBookmarkForm)
			{
				if(cmd.equals(Parameters.CMD_CONFIRM))
				{
					sr.getBookmarks().addElement(new Bookmark(readingCanvas.getNowCursor(), readingCanvas.getNowChapter() - 1,
							((AddBookmarkForm)dis).getTextField() + "\n# [" + readingCanvas.getNowChapter() + "] - [" + readingCanvas.getPercent() + "%]"));
				}
				else if(cmd.equals(Parameters.CMD_BACK))
				{

				}
				String[] bms = new String[sr.getBookmarks().size()];
				for(int i = 0; i < bms.length; i++)
				{
					bms[i] = ((Bookmark)sr.getBookmarks().elementAt(i)).getName();
				}
				nowDisplaying = new BookmarkList(this, bms);
				if(bookmarkIndex >= 0 && bookmarkIndex < ((BookmarkList)nowDisplaying).size())
				{
					((BookmarkList)nowDisplaying).setSelectedIndex(bookmarkIndex, true);
				}
				this.midlet.setDisplayable();
			}
			else if(dis instanceof EditBookmarkForm)
			{
				if(cmd.equals(Parameters.CMD_CONFIRM))
				{
					Bookmark tb = (Bookmark)sr.getBookmarks().elementAt(bookmarkIndex);
					String per = tb.getName().substring(tb.getName().lastIndexOf('[') + 1, tb.getName().lastIndexOf('%'));
					tb.setName(((EditBookmarkForm)dis).getTextField() + "\n# [" + (tb.getChapter() + 1) + "] - [" + per + "%]");
				}
				else if(cmd.equals(Parameters.CMD_BACK))
				{

				}
				String[] bms = new String[sr.getBookmarks().size()];
				for(int i = 0; i < bms.length; i++)
				{
					bms[i] = ((Bookmark)sr.getBookmarks().elementAt(i)).getName();
				}
				nowDisplaying = new BookmarkList(this, bms);
				if(bookmarkIndex >= 0 && bookmarkIndex < ((BookmarkList)nowDisplaying).size())
				{
					((BookmarkList)nowDisplaying).setSelectedIndex(bookmarkIndex, true);
				}
				this.midlet.setDisplayable();

			}
			else if(dis instanceof JumpForm)
			{
				if(cmd.equals(Parameters.CMD_CONFIRM))
				{
					jumpPercent = ((JumpForm)dis).getPercent();
					readingCanvas.setPercent(jumpPercent);
					readingCanvas.updateView();
					nowDisplaying = readingCanvas;
					midlet.setDisplayable();

				}
				else if(cmd.equals(Parameters.CMD_BACK))
				{
					nowDisplaying = readingCanvas;
					this.midlet.setDisplayable();

				}
			}
			else if(dis instanceof SearchForm)
			{
				if(cmd.equals(SearchForm.CMD_SEARCH))
				{
					final String s = ((SearchForm)dis).getString().trim();
					if(s.length() > 0)
					{
						((SearchForm)dis).setIsSearching(true);
						((SearchForm)dis).removeCommand(SearchForm.CMD_SEARCH);
						((SearchForm)dis).removeCommand(SearchForm.CMD_SEARCHNEXT);
						//((SearchForm)dis).removeCommand(SearchForm.CMD_GOJUMP);
						((SearchForm)dis).addCommand(SearchForm.CMD_STOPSEARCH);
						long startCursor = searchPos; //(((SearchForm)dis).getStartPercent() * readingCanvas.getTotalLength()) / 100L ;
						long searchCursor = readingCanvas.getTotalLength();
						int nc = readingCanvas.getNowChapter();
						((SearchForm)dis).setResult(Constants.get(Constants.searching));
						System.out.println("StartCursor:\t" + startCursor + " EndCursor:\t" + searchCursor);
						st = new SearchThread(s, startCursor, searchCursor, nc, this);
						st.start();
					}

				}
				else if(cmd.equals(SearchForm.CMD_GOJUMP))
				{
					st = null;
					readingCanvas.setCursor(searchPos);
					readingCanvas.updateView();
					nowDisplaying = readingCanvas;
					midlet.setDisplayable();

				}
				else if(cmd.equals(SearchForm.CMD_STOPSEARCH))
				{
					st.stop();
					st = null;
					((SearchForm)dis).addCommand(SearchForm.CMD_SEARCH);
					((SearchForm)dis).removeCommand(SearchForm.CMD_STOPSEARCH);
					((SearchForm)dis).setIsSearching(false);
				}
				else if(cmd.equals(SearchForm.CMD_SEARCHNEXT))
				{
					final String s = lastSearchString.trim();
					if(s.length() > 0)
					{
						((SearchForm)dis).setIsSearching(true);
						((SearchForm)dis).removeCommand(SearchForm.CMD_SEARCH);
						((SearchForm)dis).removeCommand(SearchForm.CMD_SEARCHNEXT);
						//((SearchForm)dis).removeCommand(SearchForm.CMD_GOJUMP);
						((SearchForm)dis).addCommand(SearchForm.CMD_STOPSEARCH);
						long startCursor = searchPos + s.length();//(((SearchForm)dis).getStartPercent() * readingCanvas.getTotalLength()) / 100L ;
						searchPos = searchPos + s.length();
						System.out.println("last position is" + startCursor);
						long searchCursor = readingCanvas.getTotalLength();
						int nc = readingCanvas.getNowChapter();
						((SearchForm)dis).setResult(Constants.get(Constants.searching));
						System.out.println("StartCursor:\t" + startCursor + " EndCursor:\t" + searchCursor);
						st = new SearchThread(s, startCursor, searchCursor, nc, this);
						st.start();
					}
				}
				else if(cmd.equals(Parameters.CMD_BACK))
				{
					((SearchForm)dis).setIsSearching(false);
					nowDisplaying = readingCanvas;
					midlet.setDisplayable();
				}
			}
			else if(dis instanceof HelpLicenseForm)
			{
				if(cmd.equals(Parameters.CMD_CONFIRM))
				{
					nowDisplaying = readingCanvas;
					midlet.setDisplayable();
				}
			}
		}
	}

}
