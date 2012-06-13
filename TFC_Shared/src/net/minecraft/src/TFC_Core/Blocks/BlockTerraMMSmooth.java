package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraMMSmooth extends Block implements ITextureProvider
{
	public BlockTerraMMSmooth(int i, int tex) 
	{
		super(i, Material.rock);
		this.blockIndexInTexture = tex;
	}

	public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 6; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }

    /*
     * Mapping from metadata value to damage value
     */
    @Override
    public int damageDropped(int i) 
    {
        return i;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        return blockIndexInTexture + j;
    }

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terraRock.png";
	}
}

