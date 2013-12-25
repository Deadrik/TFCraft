//=======================================================
//Mod Client File
//=======================================================
package TFC;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class TerraFirmaCraftCore extends DummyModContainer
{
	@Instance("tfc_coremod")
	public static TerraFirmaCraftCore instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public TerraFirmaCraftCore()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "tfc_coremod";
		meta.name = "TFC[coremod]";
		meta.version = Reference.ModVersion;
		meta.credits = "";
		meta.authorList = Arrays.asList("Bioxx");
		meta.description = "";
		meta.url = "www.terrafirmacraft.com";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		instance = this;

	}

	@EventHandler
	public void initialize(FMLInitializationEvent evt)
	{

	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{

	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt)
	{

	}	

}
