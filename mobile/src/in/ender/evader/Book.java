package in.ender.evader;

class Book
{

	private String title;
	private String author;
	private String creator;
	private String email;
	private String notes;

	private String charsetName = "GBK";

	private int chaptersNum = 1;
	private long blockLength = 256 * 1024L;
	private String[] chapterNames;
	private long[] chapterLengths;
	private int[] chapterPartNums;
	private long[] lastPartLengths;


	public Book(String t, String a, String c, String e, String n, String s, long bl, String[] chs, long[] chl, int[] cpn, long[] lpl)
	{
		this.title = t;
		this.author = a;
		this.creator = c;
		this.email = e;
		this.notes = n;
		this.charsetName = s;
		this.blockLength = bl;
		this.chapterNames = chs;
		this.chaptersNum = chs.length;
		this.chapterLengths = chl;
		this.chapterPartNums = cpn;
		this.lastPartLengths = lpl;
	}

	public String getTitle()
	{
		return title;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getCreator()
	{
		return creator;
	}

	public String getEmail()
	{
		return email;
	}

	public String getNotes()
	{
		return notes;
	}

	public String getCharsetName()
	{
		return charsetName;
	}

	public long getBlockLength()
	{
		return blockLength;
	}

	public String[] getChapterNames()
	{
		return this.chapterNames;
	}

	public long[] getChapterLengths()
	{
		return this.chapterLengths;
	}

	public int[] getChapterPartNums()
	{
		return this.chapterPartNums;
	}

	public long[] getLastPartLengths()
	{
		return this.lastPartLengths;
	}
}
