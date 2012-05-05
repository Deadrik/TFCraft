package net.minecraft.src.TFC_Core;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraMMCobble extends BlockTerra2 implements ITextureProvider
{

	public static boolean fallInstantly = false;

	public static boolean canFallBelow(World world, int i, int j, int k)
	{
		int l = world.getBlockId(i, j, k);
		if (l == 0)
		{
			return true;
		}
		if (l == Block.fire.blockID)
		{
			return true;
		}
		Material material = Block.blocksList[l].blockMaterial;
		if (material == Material.water)
		{
			return true;
		}
		return material == Material.lava;
	}

	public BlockTerraMMCobble(int i, Material material) {
		super(i, material);
	}


	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 17; i < 23; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public int damageDropped(int i) 
	{
		return i + 16;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		if(j < 16) {
			return (int)j+16;
		} else {
			return j;
		}
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks2.png";
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{	
		super.harvestBlock(world, entityplayer, i, j, k, l);
		//Minecraft mc = ModLoader.getMinecraftInstance();
		//int meta = l;
		//mc.ingameGUI.addChatMessage("Harvest Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(meta).toString());  
	}

	public void onBlockAdded(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
	}

	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{

	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
	}

	public int tickRate()
	{
		return 3;
	}

	private void tryToFall(World world, int i, int j, int k)
	{
		int l = i;
		int i1 = j;
		int j1 = k;
		int meta = world.getBlockMetadata(i, j, k);
		if (canFallBelow(world, l, i1 - 1, j1) && i1 >= 0)
		{
			byte byte0 = 32;
			if (fallInstantly || !world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
			{
				world.setBlockWithNotify(i, j, k, 0);
				for (; canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
				if (j > 0)
				{
					world.setBlockWithNotify(i, j, k, blockID);
				}
			}
			else if (!world.isRemote)
			{

				world.spawnEntityInWorld(new EntityFallingStone2(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, blockID, meta, 0));
			}
		}
	}

	public void updateTick(World world, int i, int j, int k, Random random)
	{
		tryToFall(world, i, j, k);
	}
}
