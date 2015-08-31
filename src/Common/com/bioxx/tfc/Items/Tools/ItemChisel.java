package com.bioxx.tfc.Items.Tools;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Tools.ChiselManager;
import com.bioxx.tfc.api.Tools.IToolChisel;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class ItemChisel extends ItemTerraTool implements IToolChisel
{
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[] {});

	public ItemChisel(ToolMaterial e)
	{
		super(0, e, BLOCKS);
		this.setMaxDamage(e.getMaxUses() / 2);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.VERYSMALL;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.Chisel.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}

	@Override
	public boolean onUsed(World world, EntityPlayer player, int x, int y, int z, Block block, int meta, int side, float hitX, float hitY, float hitZ)
	{
		return ChiselManager.getInstance().onUsedHandler(world, player, x, y, z, block, meta, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean canChisel(EntityPlayer player, int x, int y, int z) 
	{
		return true;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}

	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}
}
