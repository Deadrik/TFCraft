
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions // stop any transformers from touching this class
public class TFCLoadingPlugin implements IFMLLoadingPlugin {
        @Override
        public String[] getASMTransformerClass() {
                return null;
        }
        @Override
        public String[] getLibraryRequestClass() { // if you want to download extra libraries
                return null;
        }
        @Override
        public String getModContainerClass() { // this is vital. I'll get to that later.
                return null;
        }
        @Override
        public String getSetupClass() { // if you want to do stuff before Minecraft is loaded but after your mod is loaded.
                return null;
        }
        @Override
        public void injectData(Map<String, Object> data) {
               
               
               
        }
}