/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package in.ender.evader;

/**
 * @author Ender
 */
class Sunday
{

	private String a = "";


	public Sunday(String a)
	{
		this.a = a;
	}

	public int search(String x)
	{
		if(x == null || a == null)
		{
			return -1;
		}
		if(x.length() < a.length() || a.length() == 0 || x.length() == 0)
		{
			return -1;
		}

		int posx = 0;
		int posa = 0;
		int m = a.length();
		int n = x.length();

		//System.out.println("SearchLength:\t" + n);
		while(posx + m <= n)
		{
			if(a.charAt(posa) != x.charAt(posx))
			{
				posx = posx + m;
				if(posx >= n)
				{
					break;
				}
				if((posa = a.indexOf(x.charAt(posx))) == -1)
				{
					posa = 0;
					posx++;
				}
				else
				{
					posx = posx - posa;
					posa = 0;
				}
			}
			else
			{
				posx++;
				posa++;
				if(posa == m)
				{
					return posx - m;
				}
			}
		}

		return -1;
	}

}
