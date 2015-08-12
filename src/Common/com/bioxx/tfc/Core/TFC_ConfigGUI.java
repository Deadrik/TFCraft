package com.bioxx.tfc.Core;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.TerraFirmaCraft;
import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.DummyConfigElement.DummyListElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import com.bioxx.tfc.Reference;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import static com.bioxx.tfc.Core.TFC_ConfigFiles.*;

public class TFC_ConfigGUI extends GuiConfig
{
	public TFC_ConfigGUI(GuiScreen parent)
	{
		super(parent, getConfigElements(), Reference.ModID, false, false, Reference.ModName);
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> root = new ArrayList<IConfigElement>();
		root.add(new DummyCategoryElement("TFCOptions.cfg", "config.gui.TFCConfig", getAllFrom(TFC_ConfigFiles.getGeneralConfig())));
		root.add(new DummyCategoryElement("TFCCrafting.cfg", "config.gui.TFCCrafting", getAllFrom(TFC_ConfigFiles.getCraftingConfig())).setRequiresMcRestart(true)); //todo: Remove setRequiresMcRestart
		root.add(new DummyCategoryElement("TFCOre.cfg", "config.gui.TFCOre", getAllFrom(TFC_ConfigFiles.getOresConfig())));
		return root;
	}

	private static List<IConfigElement> getAllFrom(Configuration generalConfig)
	{
		List<IConfigElement> root = new ArrayList<IConfigElement>();
		for (String catName : generalConfig.getCategoryNames())
		{
			if (catName.contains(Configuration.CATEGORY_SPLITTER)) continue; // getCategoryNames includes sub categories, which we don't want.
			root.add(new ConfigElement(generalConfig.getCategory(catName)));
		}
		return root;
	}
}
