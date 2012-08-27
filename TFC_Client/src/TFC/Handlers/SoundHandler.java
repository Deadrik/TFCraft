package TFC.Handlers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;

import TFC.Core.TFCSounds;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.src.Entity;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPoolEntry;
import net.minecraft.src.TerraFirmaCraft;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler
{	
	@ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {

        // For each custom sound file we have defined in Sounds
        for (String soundFile : TFCSounds.soundFiles) {
            // Try to add the custom sound file to the pool of sounds
            try {
            	URL path = TerraFirmaCraft.instance.getClass().getResource("/" + soundFile);
                event.manager.soundPoolSounds.addSound(soundFile, path);
            }
            // If we cannot add the custom sound file to the pool, log the
            // exception
            catch (Exception e) {
                FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING,"TFC Failed loading sound file: " + soundFile);
            }
        }

    }
}
