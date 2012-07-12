package vazkii.um;

import net.minecraft.src.BaseMod;
import net.minecraft.src.forge.ForgeHooks;

/**
 * This class is dedicated to add compatibility with other mods.
 * @author Vazkii
 */
public final class ModCompatibility {
	
	static boolean inited = false;
	
	public ModCompatibility(){
		if(!inited){
			new Forge();
			inited = true;
		}
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

		public String getModURL() {
			return "http://minecraftforge.net/forum/index.php/topic,5.0.html";
		}

		public String getUpdateURL() {
			return "http://dl.dropbox.com/u/28221422/MinecraftForge/Recommended.txt";
		}
		
		public  ModType getModType(){
			return ModType.API;
		}
		
		public String getModName(){
			return "Minecraft Forge";
		}
		
		public String[] addNotes(){
			return new String[]{
					"The version being checked for is the reccommended version rather",
					"than the latest version."
			};
		}
	}
}
