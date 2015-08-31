package com.bioxx.tfc.Items.Tools;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

import net.minecraftforge.common.IShearable;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.google.common.collect.Sets;


public class ItemShears extends ItemTerraTool
{

	public ItemShears(float dam, ToolMaterial par3) 
	{
		super(dam, par3, Sets.newHashSet( new Block[] {}));
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecraft:" + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World world, Block block, int x, int y, int z, EntityLivingBase entity)
	{
		if (block.getMaterial() != Material.leaves && block != Blocks.web && block != Blocks.tallgrass && block != Blocks.vine && block != Blocks.tripwire && !(block instanceof IShearable))
		{
			return super.onBlockDestroyed(is, world, block, x, y, z, entity);
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean func_150897_b(Block block)
	{
		return block == Blocks.web || block == Blocks.redstone_wire || block == Blocks.tripwire;
	}

	@Override
	public float func_150893_a(ItemStack is, Block block)
	{
		return block != Blocks.web && block.getMaterial() != Material.leaves ? (block == Blocks.wool ? 5.0F : super.func_150893_a(is, block)) : 15.0F;
	}

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
	 */
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
	{
		if (entity.worldObj.isRemote)
		{
			return false;
		}
		if (entity instanceof IShearable)
		{
			IShearable target = (IShearable)entity;
			if (target.isShearable(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
			{
				ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ,
						EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));

				Random rand = new Random();
				for(ItemStack stack : drops)
				{
					EntityItem ent = entity.entityDropItem(stack, 1.0F);
					ent.motionY += rand.nextFloat() * 0.05F;
					ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
					ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				}
				itemstack.damageItem(1, entity);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
	{
		if (player.worldObj.isRemote)
		{
			return false;
		}
		Block block = player.worldObj.getBlock(x, y, z);
		if (block instanceof IShearable)
		{
			IShearable target = (IShearable)block;
			if (target.isShearable(itemstack, player.worldObj, x, y, z))
			{
				ArrayList<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z,
						EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
				Random rand = new Random();

				for(ItemStack stack : drops)
				{
					float f = 0.7F;
					double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(player.worldObj, x + d, y + d1, z + d2, stack);
					entityitem.delayBeforeCanPickup = 10;
					player.worldObj.spawnEntityInWorld(entityitem);
				}

				itemstack.damageItem(1, player);
				player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
			}
		}
		return false;
	}

}
