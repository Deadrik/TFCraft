package com.bioxx.tfc.Items.Tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class ItemHammer extends ItemTerraTool implements ICausesDamage
{
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[] {});
	private float damageVsEntity;

	public ItemHammer(ToolMaterial e, float damage)
	{
		super(0, e, BLOCKS);
		this.damageVsEntity = damage;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block id2 = player.worldObj.getBlock(x, y, z);
		int meta2 = player.worldObj.getBlockMetadata(x, y, z);

		if(id2 == TFCBlocks.stoneIgEx || id2 == TFCBlocks.stoneIgIn)
		{
			if(side == 1)
			{
				world.setBlock(x, y, z, TFCBlocks.anvil);
				player.triggerAchievement(TFC_Achievements.achAnvil);
				TEAnvil te = (TEAnvil) world.getTileEntity(x, y, z);
				if(te == null)
					world.setTileEntity(x, y, z, new TEAnvil());
				if(te != null)
				{
					te.stonePair[0] = Block.getIdFromBlock(id2);
					te.stonePair[1] = meta2;
					te.validate();
				}
				//world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
		return false;
	}

	public boolean onBlockDestroyed(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, EntityLiving entity)
	{
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumDamageType getDamageType()
	{
		return EnumDamageType.CRUSHING;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", getWeaponDamage(is), 0));
		return multimap;
	}

	public double getWeaponDamage(ItemStack is)
	{
		return Math.floor(damageVsEntity + (damageVsEntity * AnvilManager.getDamageBuff(is)));
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return (int) Math.floor(getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(is)));
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.MEDIUM;
	}
}
