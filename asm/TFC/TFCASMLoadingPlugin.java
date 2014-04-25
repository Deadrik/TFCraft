package TFC;

import java.io.File;
import java.util.Map;

import TFC.ASM.Transform.TF_EntityFallingBlock;
import TFC.ASM.Transform.TF_EntityRenderer;
import TFC.ASM.Transform.TF_RenderGlobal;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "TFC.ASM" })
public class TFCASMLoadingPlugin implements IFMLLoadingPlugin
{
	public static boolean runtimeDeobf;
	public static File location;

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{
				TF_EntityFallingBlock.class.getName(),
				TF_EntityRenderer.class.getName(),
				TF_RenderGlobal.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return TerraFirmaCraftCore.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		runtimeDeobf = (Boolean) data.get("runtimeDeobfuscationEnabled");
		location = (File) data.get("coremodLocation");
	}

}
