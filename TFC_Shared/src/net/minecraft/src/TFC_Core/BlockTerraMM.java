package net.minecraft.src.TFC_Core;

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
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraMM extends BlockCollapsable
{
	public static boolean fallInstantly = false;

	public BlockTerraMM(int i, Material material,int id) {
		super(i, material, id);
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{	
		//super.harvestBlock(world, entityplayer, i, j, k, l);
		//		Minecraft mc = ModLoader.getMinecraftInstance();
		//		int meta = l;
		//		mc.ingameGUI.addChatMessage("Harvest Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(meta).toString());  

		dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Core.terraStoneMMCobble, 1, damageDropped(l)));
		Random R = new Random();
		if(R.nextInt(TFCSettings.initialCollapseRatio) == 0)
		{
			if(tryToFall(world, i, j, k, l))
			{
				int height = 4;
				int range = R.nextInt(20);
				for(int y = -4; y <= 1; y++)
				{
					for(int x = -range; x <= range; x++)
					{
						for(int z = -range; z <= range; z++)
						{
							if(R.nextInt(100) < TFCSettings.propogateCollapseChance)
							{
								if(tryToFall(world, i+x, j+y, k+z,world.getBlockMetadata( i+x, j+y, k+z)))
								{
									int done = 0;
									while(done < height)
									{
										done++;
										if(R.nextInt(100) < TFCSettings.propogateCollapseChance) {
											tryToFall(world, i+x, j+y+done, k+z,world.getBlockMetadata( i+x, j+y+done, k+z));
										} else {
											done = height;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		return mod_TFC_Core.terraStoneMMCobble.blockID;
	}

	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
	{
		Random random = new Random();

		ItemStack is = null;

		is = TFC_Core.RandomGem(random, 0);

		if(is != null)
		{
			EntityItem item = new EntityItem(world, i, j, k, is);
			world.spawnEntityInWorld(item);
		}
	}
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{
		Random random = new Random();
		if(true)
		{
			ItemStack is = null;

			is = TFC_Core.RandomGem(random,3);

			if(is != null)
			{
				EntityItem item = new EntityItem(world, i, j, k, is);
				world.spawnEntityInWorld(item);
			}

		}
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
	{
		int metadata = world.getBlockMetadata(i, j, k);

		//Minecraft mc = ModLoader.getMinecraftInstance();

		//mc.ingameGUI.addChatMessage("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());  
	}
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		DropCarvedStone(world, i, j, k);
	}

	public int tickRate()
	{
		return 3;
	}

}

