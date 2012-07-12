package vazkii.um;

import net.minecraft.src.BaseMod;
import net.minecraft.src.forge.ForgeHooks;

/**
 * This class is dedicated to add compatibility with other mods.
 * @author Vazkii
 */
public final class ModCompatibility {
	
	public ModCompatibility(){
		new Forge();
	}
	
	public final class Forge extends UpdateManagerMod {

		public Forge() {
			super(new BaseMod(){
				public String getVersion() {
			        return String.format("%d.%d.%d.%d",
			                ForgeHooks.majorVersion,    ForgeHooks.minorVersion,
			                ForgeHooks.revisionVersion, ForgeHooks.buildVersion);
				}

				public void load() {}
			});
		}

		public String getUpdateURL() {
			return "http://dl.dropbox.com/u/28221422/MinecraftForge/Recommended.txt";
		}
		
		public String getModName(){
			return "Minecraft Forge";
		}
	}
}
