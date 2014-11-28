package in.ender.evader.generator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanfengbing
 * @version 1.0
 */
public class BookIcon
{
	public static final int width = 48;
	public static final int height = 48;
	private BufferedImage buffimg = null;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();

	BookIcon(InputStream is, String name) throws IOException
	{
		BufferedImage bicon = ImageIO.read(is);
		this.buffimg = new BufferedImage(48, 48, 1);
		int w = bicon.getWidth();
		int h = bicon.getHeight();
		Graphics2D g2d = this.buffimg.createGraphics();
		this.buffimg = g2d.getDeviceConfiguration().createCompatibleImage(48, 48, 3);
		g2d.dispose();
		g2d = this.buffimg.createGraphics();
		g2d.drawImage(bicon, (48 - w) / 2, (48 - h) / 2, (ImageObserver)null);
		byte fsize = 23;
		Font fn = new Font("Hei", 1, fsize);
		g2d.setFont(fn);
		g2d.setColor(new Color(12632256));
		g2d.drawString(name.substring(0, 1), 5, 44);
		int rgb = bicon.getRGB(w / 2, h / 2);
		rgb = ~rgb;
		rgb &= 16777215;
		g2d.setColor(new Color(rgb));
		g2d.drawString(name.substring(0, 1), 4, 43);
		fsize = 13;
		fn = new Font("Hei", 1, fsize);
		g2d.setFont(fn);
		g2d.setColor(new Color(12632256));
		String n2 = ".";
		if(name.length() >= 2)
		{
			n2 = name.substring(1, 2);
		}

		g2d.drawString(n2, 27, 44);
		rgb = bicon.getRGB(w / 2, h / 2);
		rgb = ~rgb;
		rgb &= 16777215;
		g2d.setColor(new Color(rgb));
		g2d.drawString(n2, 26, 43);
		g2d.setColor(new Color(12632256));
		g2d.drawString("..", 39, 44);
		rgb = bicon.getRGB(w / 2, h / 2);
		rgb = ~rgb;
		rgb &= 16777215;
		g2d.setColor(new Color(rgb));
		g2d.drawString("..", 38, 43);
		g2d.dispose();
		ImageIO.write(this.buffimg, "png", this.baos);
	}

	public InputStream asInputStream()
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(this.baos.toByteArray());
		return bais;
	}
}


