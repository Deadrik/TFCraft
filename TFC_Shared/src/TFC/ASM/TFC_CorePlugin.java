package TFC.ASM;

import java.io.File;
import java.util.Map;

import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/**
 * Currently this is Unused. Keeping this file for future use.
 * */
@TransformerExclusions(value={"TFC.ASM"})
public class TFC_CorePlugin  implements IFMLLoadingPlugin, IFMLCallHook
{
	@Override
	public String[] getLibraryRequestClass()
	{
        return null;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]{"TFC.ASM.TFCTransformer"};
	}

	@Override
	public String getModContainerClass()
	{
		return "TFC.ASM.TFCModContainer";
	}

	@Override
	public String getSetupClass()
	{
        return "TFC.ASM.TFC_CorePlugin";
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		if(data.containsKey("coremodLocation"))
			location = (File) data.get("coremodLocation");
	}

	@Override
	public Void call() throws Exception
	{
		//CodeChickenAccessTransformer.addTransformerMap("nei_at.cfg");
		return null;
	}
	
	public static File location;
}
