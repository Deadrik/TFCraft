package com.bioxx.tfc.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class TFC_ConfigGUI extends GuiConfig
{
	public TFC_ConfigGUI(GuiScreen parent)
	{
		super(parent, getConfigElements(), Reference.ModID, false, false, "TFCOptions.cfg");
	}

	/** Compiles a list of config elements */
	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		//Add categories to config GUI
		list.add(categoryElement(TerraFirmaCraft.GENERAL_HEADER, "General", "com.bioxx.tfc.Core.TFC_ConfigGUI.GeneralConfig"));
		list.add(categoryElement(TerraFirmaCraft.CAVEIN_OPTIONS_HEADER, "Cave-Ins", "com.bioxx.tfc.Core.TFC_ConfigGUI.CaveInConfig"));
		list.add(categoryElement(TerraFirmaCraft.CROPS_HEADER, "Crops", "com.bioxx.tfc.Core.TFC_ConfigGUI.CropConfig"));
		list.add(categoryElement(TerraFirmaCraft.FOOD_DECAY_HEADER, "Food Decay", "com.bioxx.tfc.Core.TFC_ConfigGUI.FoodDecayConfig"));
		list.add(categoryElement(TerraFirmaCraft.MATERIALS_HEADER, "Materials", "com.bioxx.tfc.Core.TFC_ConfigGUI.MaterialConfig"));
		list.add(categoryElement(TerraFirmaCraft.PROTECTION_HEADER, "Mob Spawning Protection", "com.bioxx.tfc.Core.TFC_ConfigGUI.ProtectionConfig"));
		list.add(categoryElement(TerraFirmaCraft.OVERWORKED_HEADER, "Overworked Chunks", "com.bioxx.tfc.Core.TFC_ConfigGUI.OverworkedConfig"));
		list.add(categoryElement(TerraFirmaCraft.PLAYER_HEADER, "Player", "com.bioxx.tfc.Core.TFC_ConfigGUI.PlayerConfig"));
		list.add(categoryElement(TerraFirmaCraft.SERVER_HEADER, "Server", "com.bioxx.tfc.Core.TFC_ConfigGUI.ServerConfig"));
		list.add(categoryElement(TerraFirmaCraft.TIME_HEADER, "Time", "com.bioxx.tfc.Core.TFC_ConfigGUI.TimeConfig"));
		list.add(categoryElement(TerraFirmaCraft.WORLD_GEN_HEADER, "World Generation", "com.bioxx.tfc.Core.TFC_ConfigGUI.WorldGenConfig"));

		list.add(categoryElement(TerraFirmaCraft.ANVIL_RULE_COLOR0_HEADER, "Anvil Rule 0 Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.Color0Config"));
		list.add(categoryElement(TerraFirmaCraft.ANVIL_RULE_COLOR1_HEADER, "Anvil Rule 1 Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.Color1Config"));
		list.add(categoryElement(TerraFirmaCraft.ANVIL_RULE_COLOR2_HEADER, "Anvil Rule 2 Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.Color2Config"));
		list.add(categoryElement(TerraFirmaCraft.CROP_FERTILIZER_COLOR_HEADER, "Fertilizer Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.ColorFertConfig"));
		list.add(categoryElement(TerraFirmaCraft.COLOR_NUTRIENT_A_HEADER, "Nutrient A Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.ColorAConfig"));
		list.add(categoryElement(TerraFirmaCraft.COLOR_NUTRIENT_B_HEADER, "Nutrient B Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.ColorBConfig"));
		list.add(categoryElement(TerraFirmaCraft.COLOR_NUTRIENT_C_HEADER, "Nutrient C Color", "com.bioxx.tfc.Core.TFC_ConfigGUI.ColorCConfig"));

		return list;
	}

	private static IConfigElement categoryElement(String category, String name, String tooltip_key)
	{
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement(TerraFirmaCraft.config.getCategory(category)).getChildElements());
	}

	public class GeneralConfig extends GuiConfig
	{
		public GeneralConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.GENERAL_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "General");
		}
	}
	
	public class TimeConfig extends GuiConfig
	{
		public TimeConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.TIME_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Time");
		}
	}

	public class FoodDecayConfig extends GuiConfig
	{
		public FoodDecayConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.FOOD_DECAY_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Food Decay");
		}
	}

	public class CaveInConfig extends GuiConfig
	{
		public CaveInConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.CAVEIN_OPTIONS_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Cave-Ins");
		}
	}

	public class WorldGenConfig extends GuiConfig
	{
		public WorldGenConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.WORLD_GEN_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "World Generation");
		}
	}
	
	public class CropConfig extends GuiConfig
	{
		public CropConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.CROPS_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Crops");
		}
	}
	
	public class ProtectionConfig extends GuiConfig
	{
		public ProtectionConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.PROTECTION_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Mob Spawning Protection");
		}
	}
	
	public class PlayerConfig extends GuiConfig
	{
		public PlayerConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.PLAYER_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Player");
		}
	}
	
	public class MaterialConfig extends GuiConfig
	{
		public MaterialConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.MATERIALS_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Materials");
		}
	}
	
	public class ServerConfig extends GuiConfig
	{
		public ServerConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.SERVER_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Server");
		}
	}
	
	public class OverworkedConfig extends GuiConfig
	{
		public OverworkedConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.OVERWORKED_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Oveworked Chunks");
		}
	}

	public class ColorAConfig extends GuiConfig
	{
		public ColorAConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.COLOR_NUTRIENT_A_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Nutrient A Color");
		}
	}

	public class ColorBConfig extends GuiConfig
	{
		public ColorBConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.COLOR_NUTRIENT_B_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Nutrient B Color");
		}
	}

	public class ColorCConfig extends GuiConfig
	{
		public ColorCConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.COLOR_NUTRIENT_C_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Nutrient C Color");
		}
	}

	public class ColorFertConfig extends GuiConfig
	{
		public ColorFertConfig(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.CROP_FERTILIZER_COLOR_HEADER)).getChildElements(),
					Reference.ModID, false, false, "Fertilizer Color");
		}
	}

	public class Color0Config extends GuiConfig
	{
		public Color0Config(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.ANVIL_RULE_COLOR0_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Anvil Rule 0 Color");
		}
	}

	public class Color1Config extends GuiConfig
	{
		public Color1Config(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.ANVIL_RULE_COLOR1_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Anvil Rule 1 Color");
		}
	}

	public class Color2Config extends GuiConfig
	{
		public Color2Config(GuiScreen parent)
		{
			super(parent, new ConfigElement(TerraFirmaCraft.config.getCategory(TerraFirmaCraft.ANVIL_RULE_COLOR2_HEADER)).getChildElements(), 
					Reference.ModID, false, false, "Anvil Rule 2 Color");
		}
	}
}
