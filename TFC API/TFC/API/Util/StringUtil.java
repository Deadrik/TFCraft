package TFC.API.Util;

import TFC.Core.Util.Localization;

public class StringUtil 
{

	public static String localize(String key) 
	{
		return Localization.get(key);
	}
}
