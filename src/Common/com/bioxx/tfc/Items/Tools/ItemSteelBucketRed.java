package com.bioxx.tfc.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Reference;

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
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + name);
	}
}
