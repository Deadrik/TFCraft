package com.bioxx.tfc.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCItems;

public class ItemSteelBucketRed extends ItemSteelBucket
{
	public ItemSteelBucketRed(Block par2)
	{
		super(par2);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		name = name.replace("Salt ", "");
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + name);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (this.bucketContents != Blocks.air && world.getBlock(x, y, z) == Blocks.cauldron)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (meta < 3)
			{
				if (!player.capabilities.isCreativeMode)
				{
					player.setCurrentItemOrArmor(0, new ItemStack(TFCItems.redSteelBucketEmpty));
				}
				world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(3, 0, 3), 2);
				world.func_147453_f(x, y, z, Blocks.cauldron);

				return true;
			}
		}

		return false;
	}
}
