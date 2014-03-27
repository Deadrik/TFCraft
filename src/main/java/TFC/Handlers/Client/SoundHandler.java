package TFC.Handlers.Client;

import java.util.List;
import java.util.Random;

import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import TFC.Reference;
import TFC.Core.TFC_Sounds;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SoundHandler
{
	private final List<SoundPoolEntry> TFCMusic = Lists.newArrayList();

	@SubscribeEvent
	public void onSoundLoad(SoundLoadEvent event)
	{
		for (String soundFile : TFC_Sounds.musicFiles)
			TFCMusic.add(new SoundPoolEntry(new ResourceLocation(Reference.ModID, soundFile), 0, 1, true));
	}

	@SubscribeEvent
	public void onBackgroundMusic(PlayBackgroundMusicEvent event)
	{
		int m = new Random().nextInt(TFCMusic.size());
		/*if(TFC_Settings.enableDebugMode) System.out.println("Playing " + TFCMusic.get(m).func_110458_a());*/
		event.result = TFCMusic.get(m);
	}
}
