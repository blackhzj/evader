package in.ender.evader;

import javax.microedition.lcdui.*;


class SettingForm extends Form implements CommandListener
{

	private Controller ct = null;
	private int backColor = 0xFFFFFF;
	private int frontColor = 0;
	private Font f = Font.getDefaultFont();
	private boolean isLight = false;

	private ChoiceGroup il;
	private ChoiceGroup bc;
	private ChoiceGroup fc;
	//private ChoiceGroup ff ;
	private ChoiceGroup ft;
	private ChoiceGroup fs;

	private TextField tf = null;
	private String password = "";

	private TextField tsp = null;
	private int asp = 1;

	public SettingForm(Controller ct, int backColor, int frontColor, Font f, boolean isLight, String passwd, int asp)
	{
		super(Constants.get(Constants.setting));
		this.ct = ct;

		this.setCommandListener(this);
		this.addCommand(Parameters.CMD_BACK);
		this.addCommand(Parameters.CMD_CONFIRM);

		String[] cs = {Constants.get(Constants.cs[0]), Constants.get(Constants.cs[1]), Constants.get(Constants.cs[2]), Constants.get(Constants.cs[3]), Constants.get(Constants.cs[4]), Constants.get(Constants.cs[5])};//{"Black", "Red", "Blue", "White", "Green", "Yellow"};
		bc = new ChoiceGroup(Constants.get(Constants.backColor), ChoiceGroup.POPUP, cs, null);
		fc = new ChoiceGroup(Constants.get(Constants.frontColor), ChoiceGroup.POPUP, cs, null);
		this.append(bc);
		this.append(fc);
		//String[] ffs = {"SYSTEM", "MONOSPACE", "PROPORTIONAL"};
		//ff = new ChoiceGroup(Constants.fontFace, ChoiceGroup.POPUP, ffs, null);
		String[] fts = {Constants.get(Constants.fts[0]), Constants.get(Constants.fts[1]), Constants.get(Constants.fts[2])};//{"BOLD", "ITALIC", "UNDERLINED"};
		ft = new ChoiceGroup(Constants.get(Constants.fontStyle), ChoiceGroup.MULTIPLE, fts, null);
		String[] fss = {Constants.get(Constants.fss[0]), Constants.get(Constants.fss[1]), Constants.get(Constants.fss[2])};//{"MEDIUM", "SMALL", "LARGE"};
		fs = new ChoiceGroup(Constants.get(Constants.fontSize), ChoiceGroup.POPUP, fss, null);
		//this.append(ff);
		this.append(ft);
		this.append(fs);
		String[] ics = {Constants.get(Constants.isLight)};
		il = new ChoiceGroup(Constants.get(Constants.ligth), ChoiceGroup.MULTIPLE, ics, null);
		this.append(il);

		this.password = passwd;
		tf = new TextField(Constants.get(Constants.setPassword), this.password, Parameters.passwordLength, TextField.PASSWORD);
		tf.setInitialInputMode("IS_LATIN");
		this.append(tf);

		this.asp = asp;
		tsp = new TextField(Constants.get(Constants.autoScrollSpeed), this.asp + "", 1, TextField.NUMERIC);
		this.append(tsp);
		this.append(Constants.get(Constants.autoScrollNote));

		//here read from rms and set the default value
		this.backColor = backColor;
		this.frontColor = frontColor;
		this.f = f;
		this.isLight = isLight;
		bc.setSelectedIndex(bc.getSelectedIndex(), false);
		switch(backColor)
		{
			case RGB.black:
				bc.setSelectedIndex(0, true);
				break;
			case RGB.red:
				bc.setSelectedIndex(1, true);
				break;
			case RGB.blue:
				bc.setSelectedIndex(2, true);
				break;
			case RGB.white:
				bc.setSelectedIndex(3, true);
				break;
			case RGB.green:
				bc.setSelectedIndex(4, true);
				break;
			case RGB.yellow:
				bc.setSelectedIndex(5, true);
				break;
		}
		fc.setSelectedIndex(fc.getSelectedIndex(), false);
		switch(frontColor)
		{
			case RGB.black:
				fc.setSelectedIndex(0, true);
				break;
			case RGB.red:
				fc.setSelectedIndex(1, true);
				break;
			case RGB.blue:
				fc.setSelectedIndex(2, true);
				break;
			case RGB.white:
				fc.setSelectedIndex(3, true);
				break;
			case RGB.green:
				fc.setSelectedIndex(4, true);
				break;
			case RGB.yellow:
				fc.setSelectedIndex(5, true);
				break;
		}
		//ff.setSelectedIndex(ff.getSelectedIndex(), false);
		switch(this.f.getFace())
		{
			case Font.FACE_SYSTEM:
				//ff.setSelectedIndex(0, true);
				break;
			case Font.FACE_MONOSPACE:
				//ff.setSelectedIndex(1, true);
				break;
			case Font.FACE_PROPORTIONAL:
				//ff.setSelectedIndex(2, true);
				break;
		}
		fs.setSelectedIndex(fs.getSelectedIndex(), false);
		switch(this.f.getSize())
		{
			case Font.SIZE_MEDIUM:
				fs.setSelectedIndex(0, true);
				break;
			case Font.SIZE_SMALL:
				fs.setSelectedIndex(1, true);
				break;
			case Font.SIZE_LARGE:
				fs.setSelectedIndex(2, true);
				break;
		}
		int t = f.getStyle();
		if((t & Font.STYLE_BOLD) > 0)
		{
			ft.setSelectedIndex(0, true);
		}
		if((t & Font.STYLE_ITALIC) > 0)
		{
			ft.setSelectedIndex(1, true);
		}
		if((t & Font.STYLE_UNDERLINED) > 0)
		{
			ft.setSelectedIndex(2, true);
		}
		il.setSelectedIndex(0, this.isLight);
	}

	public void commandAction(Command cmd, Displayable arg1)
	{
		if(cmd.equals(Parameters.CMD_CONFIRM))
		{
			switch(bc.getSelectedIndex())
			{
				case 0:
					this.backColor = RGB.black;
					break;
				case 1:
					this.backColor = RGB.red;
					break;
				case 2:
					this.backColor = RGB.blue;
					break;
				case 3:
					this.backColor = RGB.white;
					break;
				case 4:
					this.backColor = RGB.green;
					break;
				case 5:
					this.backColor = RGB.yellow;
					break;
				default:
					this.backColor = RGB.white;
					break;
			}
			switch(fc.getSelectedIndex())
			{
				case 0:
					this.frontColor = RGB.black;
					break;
				case 1:
					this.frontColor = RGB.red;
					break;
				case 2:
					this.frontColor = RGB.blue;
					break;
				case 3:
					this.frontColor = RGB.white;
					break;
				case 4:
					this.frontColor = RGB.green;
					break;
				case 5:
					this.frontColor = RGB.yellow;
					break;
				default:
					this.frontColor = RGB.black;
					break;
			}
			int[] fps = new int[3];
			switch(0/*ff.getSelectedIndex()*/)
			{
				case 0:
					fps[0] = Font.FACE_SYSTEM;
					break;
				case 1:
					fps[0] = Font.FACE_MONOSPACE;
					break;
				case 2:
					fps[0] = Font.FACE_PROPORTIONAL;
					break;
				default:
					fps[0] = Font.FACE_SYSTEM;
					break;
			}
			fps[1] = Font.STYLE_PLAIN;
			if(ft.isSelected(0))
			{
				fps[1] |= Font.STYLE_BOLD;
			}
			if(ft.isSelected(1))
			{
				fps[1] |= Font.STYLE_ITALIC;
			}
			if(ft.isSelected(2))
			{
				fps[1] |= Font.STYLE_UNDERLINED;
			}
			switch(fs.getSelectedIndex())
			{
				case 0:
					fps[2] = Font.SIZE_MEDIUM;
					break;
				case 1:
					fps[2] = Font.SIZE_SMALL;
					break;
				case 2:
					fps[2] = Font.SIZE_LARGE;
					break;
				default:
					fps[2] = Font.SIZE_MEDIUM;
					break;
			}
			f = Font.getFont(fps[0], fps[1], fps[2]);
			isLight = il.isSelected(0);
			password = tf.getString().trim();
			try
			{
				this.asp = Integer.parseInt(tsp.getString());
				if(this.asp <= 0)
				{
					this.asp = 1;
				}
				else if(this.asp >= 10)
				{
					this.asp = 9;
				}
			}
			catch(NumberFormatException e)
			{
				this.asp = 1;
			}
			ct.getMonitor().setAutoScrollSpeed(this.asp);

		}

		ct.setParameters(backColor, frontColor, f, isLight, password, this.asp);

	}


}
