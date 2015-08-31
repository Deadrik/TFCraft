package com.bioxx.tfc.Core.Config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.bioxx.tfc.TerraFirmaCraft;

public class TFC_Configuration extends Configuration
{

	/**
	 * Create a configuration file for the file given in parameter.
	 */
	public TFC_Configuration(File file)
	{
		super(file, null);
	}

	/**
	 * Creates a integer property.
	 * 
	 * @param name Name of the property.
	 * @param category Category of the property.
	 * @param defaultValue Default value of the property.
	 * @param minValue Minimum value of the property.
	 * @param maxValue Maximum value of the property.
	 * @param comment A brief description what the property does.
	 * @param langKey A language key used for localization of GUIs
	 * @return The value of the new integer property.
	 */
	@Override
	public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment, String langKey)
	{
		Property prop = this.get(category, name, defaultValue);
		prop.setLanguageKey(langKey);
		prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]";
		prop.setMinValue(minValue);
		prop.setMaxValue(maxValue);
		if (prop.getInt(defaultValue) < minValue || prop.getInt(defaultValue) > maxValue)
		{
			TerraFirmaCraft.LOG.warn("An invalid value has been entered for " + name + " in the config file. Reverting to the default value.");
			prop.set(defaultValue);
			return defaultValue;
		}
		return prop.getInt(defaultValue);
	}

	/**
	 * Creates a float property.
	 * 
	 * @param name Name of the property.
	 * @param category Category of the property.
	 * @param defaultValue Default value of the property.
	 * @param minValue Minimum value of the property.
	 * @param maxValue Maximum value of the property.
	 * @param comment A brief description what the property does.
	 * @param langKey A language key used for localization of GUIs
	 * @return The value of the new float property.
	 */
	@Override
	public float getFloat(String name, String category, float defaultValue, float minValue, float maxValue, String comment, String langKey)
    {
        Property prop = this.get(category, name, Float.toString(defaultValue), name);
        prop.setLanguageKey(langKey);
        prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]";
        prop.setMinValue(minValue);
        prop.setMaxValue(maxValue);
        try
        {
        	if(Float.parseFloat(prop.getString()) < minValue || Float.parseFloat(prop.getString()) > maxValue)
        	{
    			TerraFirmaCraft.LOG.warn("An invalid value has been entered for " + name + " in the config file. Reverting to the default value.");
    			prop.set(defaultValue);
    			return defaultValue;        		
        	}
			return Float.parseFloat(prop.getString());
        }
        catch (Exception e)
        {
			TerraFirmaCraft.LOG.catching(e);
        }
        return defaultValue;
    }

}
