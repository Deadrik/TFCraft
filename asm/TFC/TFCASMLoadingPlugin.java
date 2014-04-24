package TFC;

import java.util.Map;

import TFC.ASM.Transform.TF_EntityFallingBlock;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "TFC.ASM" })
public class TFCASMLoadingPlugin implements IFMLLoadingPlugin
{
	public static boolean runtimeDeobf;

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{/*TF_RenderBlock.class.getName(),*/TF_EntityFallingBlock.class.getName()};
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
		// TODO Auto-generated method stub
		runtimeDeobf = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

}
