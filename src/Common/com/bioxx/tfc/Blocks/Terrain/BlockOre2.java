package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.Random;

import com.bioxx.tfc.TileEntities.TEOre;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class BlockOre2 extends BlockOre
{
	public BlockOre2(Material mat)
	{
		super(mat);
		blockNames = Global.ORE_METAL2;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg + Global.ORE_METAL.length;
	}

	public static Item getDroppedItem(int meta)
	{
		if(meta < (Global.ORE_METAL.length + 1))
			return TFCItems.smallOreChunk;
		else
			return null;
	}
}
