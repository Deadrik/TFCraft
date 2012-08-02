package TFC.Core;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import net.minecraft.src.Entity;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPoolEntry;
import net.minecraft.src.forge.ISoundHandler;

public class SoundHandler implements ISoundHandler
{

    @Override
    public void onSetupAudio(SoundManager soundManager)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadSoundSettings(SoundManager soundManager)
    {
        //soundManager.addSound("", new File("/sounds/tfc/fallingrock.ogg").);
        String [] soundFiles = {
                "fallingrockshort1.ogg","fallingrockshort2.ogg","fallingrocklong1.ogg","fallingrocklong2.ogg",
                "fallingdirtshort1.ogg","fallingdirtshort2.ogg",
                "metalimpact1.ogg","metalimpact2.ogg","metalimpact3.ogg","metalimpact4.ogg",};
        for (int i = 0; i < soundFiles.length; i++)
        {
            URL u = this.getClass().getResource("/sounds/tfc/" + soundFiles[i]);
            soundManager.getSoundsPool().addSound(soundFiles[i], u);
        }
    }

    @Override
    public SoundPoolEntry onPlayBackgroundMusic(SoundManager soundManager,
            SoundPoolEntry entry)
    {
        // TODO Auto-generated method stub
        return entry;
    }

    @Override
    public SoundPoolEntry onPlayStreaming(SoundManager soundManager,
            SoundPoolEntry entry, String soundName, float x, float y, float z)
    {
        // TODO Auto-generated method stub
        return entry;
    }

    @Override
    public SoundPoolEntry onPlaySound(SoundManager soundManager,
            SoundPoolEntry entry, String soundName, float x, float y, float z,
            float volume, float pitch)
    {
        // TODO Auto-generated method stub
        return entry;
    }

    @Override
    public SoundPoolEntry onPlaySoundEffect(SoundManager soundManager,
            SoundPoolEntry entry, String soundName, float volume, float pitch)
    {
        // TODO Auto-generated method stub
        return entry;
    }

    @Override
    public String onPlaySoundAtEntity(Entity entity, String soundName,
            float volume, float pitch)
    {
        // TODO Auto-generated method stub
        return soundName;
    }

}
