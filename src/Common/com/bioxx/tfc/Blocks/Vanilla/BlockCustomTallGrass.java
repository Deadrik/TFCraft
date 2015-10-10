package com.bioxx.tfc.Blocks.Vanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.IShearable;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.*;
import com.bioxx.tfc.api.TFCItems;

public class BlockCustomTallGrass extends BlockTallGrass implements IShearable
{
	private static final String[] META_NAMES = new String[] {"tallgrass", "fern", "shortgrass"};
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockCustomTallGrass()
	{
		super();
		float var3 = 0.4F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < META_NAMES.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerGrassTFC.getGrassColor(var1, var3);
	}

	@Override
	public int getRenderColor(int par1)
	{
		return par1 == 0 ? 16777215 : ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.grassColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random rand)
	{
		return 1 + rand.nextInt(i * 2 + 1);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
	{
		super.harvestBlock(world, player, i, j, k, l);

		ItemStack is = player.inventory.getCurrentItem();
		int[] equipIDs = OreDictionary.getOreIDs(is);

		for (int id : equipIDs)
		{
			String name = OreDictionary.getOreName(id);
			if (name.startsWith("itemKnife"))
			{
				createStraw(world, player, i, j, k);
				is.damageItem(1, player);
				if (is.stackSize == 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				break;
			}
			else if (name.startsWith("itemScythe"))
			{
				//Spawn the straw for the block that we've already destroyed
				createStraw(world, player, i, j, k );
				//Now check each block around the destroyed block for AOE directions
				for(int x = -1; x < 2; x++)
				{
					for(int z = -1; z < 2; z++)
					{
						if(world.getBlock(i + x, j, k + z) == this)
						{
							createStraw(world, player, i + x, j, k + z);
							is.damageItem(1, player);
							if (is.stackSize == 0)
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							world.setBlockToAir(i + x, j, k + z);
						}
					}
				}
				break;
			}
		}
	}

	private void createStraw(World world, EntityPlayer player, int i, int j, int k)
	{
		EntityItem ei = new EntityItem(world, i+0.5F, j+0.5F, k+0.5F, new ItemStack(TFCItems.straw, 1));
		world.spawnEntityInWorld(ei);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		//if (world.rand.nextInt(8) != 0) return ret;
		ItemStack item = getSeeds(world.rand);
		if (item != null)
			ret.add(item);
		return ret;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		return ret;
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block); // || par1 == Block.tilledField.blockID;
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return (world.getFullBlockLightValue(x, y, z) >= 8 || 
				world.canBlockSeeTheSky(x, y, z)) && 
				this.canThisPlantGrowOnThisBlock(world.getBlock(x, y - 1, z));
	}

	public static ItemStack getSeeds(Random r)
	{
		ItemStack is = null;
		/*if(R.nextInt(100) == 0)
		{
			int r = R.nextInt(19);
			switch(r)
			{
			case 0:
				is = new ItemStack(TFCItems.SeedsWheat,1); break;
			case 1:
				is = new ItemStack(TFCItems.SeedsMaize,1); break;
			case 2:
				is = new ItemStack(TFCItems.SeedsTomato,1); break;
			case 3:
				is = new ItemStack(TFCItems.SeedsBarley,1); break;
			case 4:
				is = new ItemStack(TFCItems.SeedsRye,1); break;
			case 5:
				is = new ItemStack(TFCItems.SeedsOat,1); break;
			case 6:
				is = new ItemStack(TFCItems.SeedsRice,1); break;
			case 7:
				is = new ItemStack(TFCItems.SeedsPotato,1); break;
			case 8:
				is = new ItemStack(TFCItems.SeedsOnion,1); break;
			case 9:
				is = new ItemStack(TFCItems.SeedsCabbage,1); break;
			case 10:
				is = new ItemStack(TFCItems.SeedsGarlic,1); break;
			case 11:
				is = new ItemStack(TFCItems.SeedsCarrot,1); break;
			case 12:
				is = new ItemStack(TFCItems.SeedsYellowBellPepper,1); break;
			case 13:
				is = new ItemStack(TFCItems.SeedsRedBellPepper,1); break;
			case 14:
				is = new ItemStack(TFCItems.SeedsSoybean,1); break;
			case 15:
				is = new ItemStack(TFCItems.SeedsGreenbean,1); break;
			case 16:
				is = new ItemStack(TFCItems.SeedsSquash,1); break;
			}
		}*/
		return is;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.icons = new IIcon[META_NAMES.length];
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = register.registerIcon((i > 1 ? Reference.MOD_ID + ":plants/" : "") + META_NAMES[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= this.icons.length) meta = 0;
		return this.icons[meta];
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random rand)
	{
		// Play cricket sound at night
		float temp = TFC_Climate.getHeightAdjustedTemp(w, x, y, z);
		/*Crickets typically don't mate below 55F and are nocturnal. We're being oddly accurate about this lol -B*/
		if(!w.isRemote && w.getBlockLightValue(x, y, z) < 7 && temp > 12.77)
		{
			if(w.rand.nextInt(Math.max(((int)((160)/(temp-4))),1)) < 2) //chirp intensity grows with higher temperature
			{
				float vol = 0.1f + (w.rand.nextFloat() * 0.20F); // keep the volume between 0 and 0.3
				float pitch = (temp / 100) * 2 + 0.5F + vol; // the chirp frequency will change depending on the climate temperature
				w.playSoundEffect(x, y, z, TFC_Sounds.CRICKET, vol, pitch);
			}

			if(rand.nextInt(8)==0){
				w.scheduleBlockUpdate(x, y, z, this, 5);
			}
		}

		super.updateTick(w, x, y, z, rand);
	}
}
