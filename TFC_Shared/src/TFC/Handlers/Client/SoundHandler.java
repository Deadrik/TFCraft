package TFC.Handlers.Client;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Sounds;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;

public class SoundHandler {
	private final List<SoundPoolEntry> TFCMusic = Lists.newArrayList();
	
	@ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {
        // For each custom sound file we have defined in Sounds
        for (String soundFile : TFC_Sounds.soundFiles) {
            // Try to add the custom sound file to the pool of sounds
            try {
                //URL path = TerraFirmaCraft.instance.getClass().getResource("/" + soundFile);
                event.manager.soundPoolSounds.addSound(soundFile);
            }
            // If we cannot add the custom sound file to the pool, log the exception
            catch (Exception e) {
                FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "TFC Failed loading sound file: " + soundFile);
            }
        }
/*
        // event.manager.soundPoolMusic = new SoundPool();
        // For each custom music file we have defined in Sounds
        for (String soundFile : TFC_Sounds.musicFiles) {
            // Try to add the custom music file to the pool of sounds
            try {
            	URL path = TerraFirmaCraft.instance.getClass().getResource("/" + soundFile);
                event.manager.soundPoolMusic.addSound(soundFile, path);
            }
            // If we cannot add the custom music file to the pool, log the exception
            catch (Exception e) {
                FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "TFC Failed loading music file: " + soundFile);
            }
        }
*/

		for (String soundFile : TFC_Sounds.musicFiles) {
			TFCMusic.add(new SoundPoolEntry(soundFile.substring(1), this.getClass().getResource(soundFile)));
		}
	}

	@ForgeSubscribe
	public void onBackgroundMusic(PlayBackgroundMusicEvent event) {
		int m = new Random().nextInt(TFCMusic.size());
		/*if(TFC_Settings.enableDebugMode)
			System.out.println("Playing " + TFCMusic.get(m).soundName);*/
		event.result = TFCMusic.get(m);
	}
}
