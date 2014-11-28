package in.ender.evader.generator;

import java.io.UnsupportedEncodingException;

/**
 * @author yanfengbing
 * @version 1.0
 */
public class MIDletInfo
{
	public static final String MIDlet_n = ",/icon.png,in.ender.evader.Evader";
	public static final String MIDlet_Icon = "MIDlet-Icon: /icon.png";
	public static final String MIDlet_Vendor = "MIDlet-Vendor: Ender";
	public static final String MIDlet_Version = "MIDlet-Version: 1.0";
	public static final String MicroEdition_Configuration = "MicroEdition-Configuration: CLDC-1.0";
	public static final String MicroEdition_Profile = "MicroEdition-Profile: MIDP-2.0";
	public static final String Manifest_Version = "Manifest-Version: 1.0";
	public static final String Ant_Version = "Ant-Version: Apache Ant 1.7.1";
	public static final String Created_By = "Created-By: Ender, Java 1.5.0_16-b02 (Sun Microsystems Inc.)";
	public static final String MIDlet_Description = "MIDlet-Description: A Phone E-Book Reader";
	public static final String MIDlet_Info_URL = "MIDlet-Info-URL: http://ender.in";
	private long MIDlet_Jar_Size = 0L;
	private String MIDlet_Jar_URL = "ebookReader.jar";
	private String MIDlet_Name = "ebookReader";

	public MIDletInfo()
	{
	}

	public String getManifest() throws UnsupportedEncodingException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Manifest-Version: 1.0\r\n");
		sb.append("Ant-Version: Apache Ant 1.7.1\r\n");
		sb.append("Created-By: Ender, Java 1.5.0_16-b02 (Sun Microsystems Inc.)\r\n");
		sb.append("MIDlet-1: " + this.getMIDletName() + ",/icon.png,in.ender.evader.Evader" + "\r\n");
		sb.append("MIDlet-Info-URL: http://ender.in\r\n");
		sb.append("MIDlet-Vendor: Ender\r\n");
		sb.append("MIDlet-Version: 1.0\r\n");
		sb.append("MIDlet-Icon: /icon.png\r\n");
		sb.append("MIDlet-Description: A Phone E-Book Reader\r\n");
		sb.append("MIDlet-Name: " + this.getMIDletName() + "\r\n");
		sb.append("MicroEdition-Configuration: CLDC-1.0\r\n");
		sb.append("MicroEdition-Profile: MIDP-2.0\r\n");
		return sb.toString();
	}

	public String getJadFile() throws UnsupportedEncodingException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MIDlet-1: " + this.getMIDletName() + ",/icon.png,thelastender.micro.evader.evader" + "\r\n");
		sb.append("MIDlet-Description: A Phone E-Book Reader\r\n");
		sb.append("MIDlet-Info-URL: http://thelastender.blogspot.com\r\n");
		sb.append("MIDlet-Vendor: Ender\r\n");
		sb.append("MIDlet-Version: 1.0\r\n");
		sb.append("MIDlet-Icon: /icon.png\r\n");
		sb.append("MIDlet-Name: " + this.getMIDletName() + "\r\n");
		sb.append("MicroEdition-Configuration: CLDC-1.0\r\n");
		sb.append("MicroEdition-Profile: MIDP-2.0\r\n");
		sb.append("MIDlet-Jar-Size: " + this.getMIDletJarSize() + "\r\n");
		sb.append("MIDlet-Jar-URL: " + this.getMIDletJarURL() + "\r\n");
		return sb.toString();
	}

	public String getMIDletName()
	{
		return this.MIDlet_Name;
	}

	public long getMIDletJarSize()
	{
		return this.MIDlet_Jar_Size;
	}

	public String getMIDletJarURL()
	{
		return this.MIDlet_Jar_URL;
	}

	public void setMIDletName(String MN)
	{
		this.MIDlet_Name = MN;
	}

	public void setMIDletJarSize(long s)
	{
		this.MIDlet_Jar_Size = s;
	}

	public void setMIDletJarURL(String u)
	{
		this.MIDlet_Jar_URL = u;
	}
}

