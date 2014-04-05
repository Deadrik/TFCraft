package TFC.Handlers.Client;

import java.util.List;
import java.util.Random;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import TFC.Reference;
import TFC.Core.TFC_Sounds;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SoundHandler
{
	private ISound iSound;

	@SubscribeEvent
	public void onSound17(PlaySoundEvent17 event)
	{
		if(event.category.getCategoryName().equalsIgnoreCase("music"))
		{
			//System.out.println("Now Playing: " + event.sound.getPositionedSoundLocation().getResourcePath() + " : " + event.manager.isSoundPlaying(iSound));
			if(event.manager.isSoundPlaying(iSound))
			{
				event.result = null;
			}
			else
			{
				iSound = PositionedSoundRecord.func_147673_a(new ResourceLocation(Reference.ModID + ":music.tfc"));
				//System.out.println("START Playing: " + event.name + " : " + iSound.getPositionedSoundLocation().getResourcePath());
				event.result = iSound;
			}
		}
	}
}
