package TFC.Handlers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;

import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Sounds;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler
{	
	@ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {

        // For each custom sound file we have defined in Sounds
        for (String soundFile : TFC_Sounds.soundFiles) {
            // Try to add the custom sound file to the pool of sounds
            try {
            	URL path = TerraFirmaCraft.instance.getClass().getResource("/" + soundFile);
                event.manager.soundPoolSounds.addSound(soundFile, path);
            }
            // If we cannot add the custom sound file to the pool, log the
            // exception
            catch (Exception e) {
                FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "TFC Failed loading sound file: " + soundFile);
            }
        }
        //event.manager.soundPoolMusic = new SoundPool();
     // For each custom music file we have defined in Sounds
        for (String soundFile : TFC_Sounds.musicFiles) {
            // Try to add the custom music file to the pool of sounds
            try {
            	URL path = TerraFirmaCraft.instance.getClass().getResource("/" + soundFile);
                event.manager.soundPoolMusic.addSound(soundFile, path);
            }
            // If we cannot add the custom music file to the pool, log the
            // exception
            catch (Exception e) {
                FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "TFC Failed loading music file: " + soundFile);
            }
        }

    }
}
