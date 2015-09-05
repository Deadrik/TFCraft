package com.bioxx.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class BlockLogVert extends BlockTerra
{
	protected String[] woodNames;
	public BlockLogVert()
	{
		super(Material.wood);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		//we need to make sure the player has the correct tool out
		boolean isAxeorSaw = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if (equip != null)
		{
			int[] equipIDs = OreDictionary.getOreIDs(equip);
			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemAxe") || name.startsWith("itemSaw"))
				{
					isAxeorSaw = true;
					break;
				}
				else if (name.startsWith("itemHammer"))
				{
					isHammer = true;
					break;
				}
			}

			if (isAxeorSaw)
			{
				super.harvestBlock(world, entityplayer, x, y, z, meta);
			}
			else if (isHammer)
			{
				EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.stick, 1 + world.rand.nextInt(3)));
				world.spawnEntityInWorld(item);
			}
		}
		else
		{
			world.setBlock(x, y, z, this, meta, 0x2);
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public Item getItemDropped(int i, Random r, int j)
	{
		return TFCItems.logs;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return TFCBlocks.logNatural.getIcon(side, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
	}
}
