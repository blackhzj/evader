package in.ender.evader.generator;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author yanfengbing
 * @version 1.0
 */

public class EvaderGenerator
{
	private OutputStreamWriter pw = null;
	private String infoFilename = "";
	private String outpath = "";
	private JarOutputStream jos = null;
	private OutputStreamWriter osw = null;
	private String title = "";
	private String author = "";
	private String creator = "";
	private String email = "";
	private String notes = "";
	private int chapter = 0;
	private String[] chapterNames = null;
	private String[] chapterPaths = null;
	private String charsetName = "GBK";
	private String toCharsetName = "GBK";
	private long blockLength = 524288L;
	private InputStream resPath = null;

	public EvaderGenerator(String infoFilename, InputStream resPath, String outpath) throws UnsupportedEncodingException
	{
		this.infoFilename = infoFilename;
		this.resPath = resPath;
		this.outpath = outpath;
		if(System.getProperty("user.language").equalsIgnoreCase("zh"))
		{
			this.pw = new OutputStreamWriter(System.out, "GBK");
		}
		else
		{
			this.pw = new OutputStreamWriter(System.out, "UTF-8");
		}

	}

	public void doText() throws NoSuchElementException, IOException
	{
		Scanner sc = new Scanner(new FileInputStream(this.infoFilename), this.charsetName);
		this.title = sc.nextLine();
		if(this.title.length() <= 255 && this.title.length() >= 1)
		{
			this.pw.write(this.title + "\r\n");
			this.pw.flush();
			this.author = sc.nextLine();
			this.pw.write(this.author + "\r\n");
			this.pw.flush();
			this.creator = sc.nextLine();
			this.email = sc.nextLine();
			this.notes = sc.nextLine();
			this.chapter = Integer.parseInt(sc.nextLine());
			this.chapterNames = new String[this.chapter];
			this.chapterPaths = new String[this.chapter];

			for(int mi = 0; mi < this.chapter; ++mi)
			{
				this.chapterNames[mi] = sc.nextLine();
				this.chapterPaths[mi] = sc.nextLine();
			}

			this.charsetName = sc.nextLine();
			this.toCharsetName = sc.nextLine();
			this.blockLength = Long.parseLong(sc.nextLine()) * 10L;
			MIDletInfo var17 = new MIDletInfo();
			var17.setMIDletName(this.title);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeUTF(this.title);
			dos.writeUTF(this.author);
			dos.writeUTF(this.creator);
			dos.writeUTF(this.email);
			dos.writeUTF(this.notes);
			dos.writeUTF(this.toCharsetName);
			dos.writeInt(this.chapter);
			dos.writeLong(this.blockLength);
			this.jos = new JarOutputStream(new FileOutputStream(this.outpath + this.title + ".jar"));
			this.osw = new OutputStreamWriter(this.jos, this.toCharsetName);

			int icn;
			for(icn = 0; icn < this.chapter; ++icn)
			{
				dos.writeUTF(this.chapterNames[icn]);
				this.pw.write(this.chapterNames[icn] + "\r\n");
				this.pw.flush();
				this.doChapter(this.chapterPaths[icn], icn + 1, dos);
			}

			dos.close();
			icn = Math.abs(this.title.hashCode()) % 4;
			String iconname = "book1.png";
			BookIcon bi = null;
			InputStream icis = ClassLoader.getSystemResourceAsStream("res/icon.zip");
			ZipInputStream zicis = new ZipInputStream(icis);
			ZipEntry zeic = null;

			for(int kk = 0; (zeic = zicis.getNextEntry()) != null; ++kk)
			{
				if(kk == icn)
				{
					iconname = zeic.getName();
					this.pw.write("Icon: " + iconname + "\r\n");
					bi = new BookIcon(zicis, this.title);
					zicis.closeEntry();
					break;
				}
			}

			zicis.close();
			ZipInputStream zis = new ZipInputStream(this.resPath);
			ZipEntry ze = null;

			InputStream ooo;
			while((ze = zis.getNextEntry()) != null)
			{
				this.jos.putNextEntry(new JarEntry(ze.getName()));
				if(ze.getName().equalsIgnoreCase("icon.png"))
				{
					ooo = bi.asInputStream();
					byte[] fos = new byte[2048];
					boolean l = false;

					int var21;
					while((var21 = ooo.read(fos)) != -1)
					{
						this.jos.write(fos, 0, var21);
						this.jos.flush();
					}
				}
				else
				{
					byte[] var19 = new byte[2048];
					boolean var18 = false;

					int var23;
					while((var23 = zis.read(var19)) != -1)
					{
						this.jos.write(var19, 0, var23);
						this.jos.flush();
					}
				}

				zis.closeEntry();
				this.jos.closeEntry();
			}

			zis.close();
			this.jos.putNextEntry(new JarEntry("data/0.txt"));
			this.jos.write(baos.toByteArray());
			this.jos.flush();
			this.jos.closeEntry();
			this.jos.putNextEntry(new JarEntry("META-INF/MANIFEST.MF"));
			OutputStreamWriter var20 = new OutputStreamWriter(this.jos, "UTF-8");
			this.pw.write(var17.getManifest() + "\r\n");
			this.pw.flush();
			var20.write(var17.getManifest() + "\r\n");
			var20.flush();
			var20.close();
			ooo = null;
			var17.setMIDletJarURL(this.title + ".jar");
			var17.setMIDletJarSize((new File(this.outpath + this.title + ".jar")).length());
			OutputStreamWriter var22 = new OutputStreamWriter(new FileOutputStream(this.outpath + this.title + ".jad"), "UTF-8");
			var22.write(var17.getJadFile());
			var22.flush();
			var22.close();
			this.pw.close();
			this.pw = null;
		}
		else
		{
			throw new IOException("Book title is null or more than 255 characters");
		}
	}

	public void doChapter(String chapterPath, int chapterIndex, DataOutputStream dos) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		TxtFileFragmentBuilder tffb = new TxtFileFragmentBuilder(chapterPath, this.blockLength, this.charsetName);
		FileSpliter fs = new FileSpliter(tffb);
		fs.split();
		int i = 0;
		long cn = 0L;

		long lbn;
		long n;
		for(lbn = 0L; tffb.hasNext(); lbn = n)
		{
			FileFragment ff = (FileFragment)tffb.next();
			n = 0L;
			boolean l = false;
			char[] c = new char[1024];
			JarEntry je = new JarEntry("data/" + chapterIndex + "x" + i + ".txt");
			this.jos.putNextEntry(je);

			int var17;
			while((var17 = ff.readChar(c)) != -1)
			{
				this.osw.write(c, 0, var17);
				this.osw.flush();
				n += (long)var17;
			}

			this.jos.closeEntry();
			cn += n;
			ff.close();
			ff = null;
			++i;
		}

		dos.writeLong(cn);
		dos.writeInt(i);
		dos.writeLong(lbn);
		this.pw.write("[" + cn + "] - [" + i + "] - [" + lbn + "]" + "\r\n");
		this.pw.flush();
	}

	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Usage:\tjava -jar evaderGenerator.rj.ip.jar [config file]\r\n");
			System.out.println("The config file\'s each line represents a parameter.");
			System.out.println("Here is the config file format. Every line is necessary.");
			System.out.println("[config file format]");
			System.out.println("\tTitle");
			System.out.println("\tAuthor");
			System.out.println("\tCreator");
			System.out.println("\tContact Information");
			System.out.println("\tBook notes");
			System.out.println("\tChapter number");
			System.out.println("\tChapter 1 name");
			System.out.println("\tChapter 1 file path");
			System.out.println("\t...");
			System.out.println("\tChapter n name");
			System.out.println("\tChapter n file path");
			System.out.println("\tBook txt file encoding, usually is GBK");
			System.out.println("\tE-Book file encoding, usually is GBK");
			System.out.println("\tA magic number, the smaller, the reading faster.\r\n\t(It is better that the number is smaller than 512.)");
		}
		else
		{
			try
			{
				String e = args[0];
				String outpath = "./";
				InputStream res;
				if(args.length > 1)
				{
					res = new FileInputStream(args[1]);
				}
				else
				{
					res = ClassLoader.getSystemResourceAsStream("res/res2.zip");
				}
				EvaderGenerator ebg = new EvaderGenerator(e, res, outpath);
				ebg.doText();
			}
			catch(NoSuchElementException var5)
			{
				System.out.println("[Fatal Error]\tNot enough lines in the config file.\r\n");
			}
			catch(NumberFormatException var6)
			{
				System.out.println("[Fatal Error]\tThe last line of config file must be a number.\r\n");
			}
			catch(UnsupportedEncodingException var7)
			{
				System.out.println("[Fatal Error]\tThe character encoding in the config file does not supported.\r\n");
			}
			catch(FileNotFoundException var8)
			{
				System.out.println("[Fatal Error]\tOh! Some file can\'t be found.\r\n");
			}
			catch(IOException var9)
			{
				System.out.println("[Fatal Error]\tIO exeception! Maybe it is a reading or writing files error.\r\n");
			}
			catch(NullPointerException var10)
			{
				System.out.println("[Fatal Error]\tNull Pointer Exception. Maybe some object is null.\r\n");
			}

		}
	}
}

