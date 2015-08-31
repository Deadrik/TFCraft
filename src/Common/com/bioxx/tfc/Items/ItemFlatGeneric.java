package com.bioxx.tfc.Items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;

public class ItemFlatGeneric extends ItemTerra
{
	public IIcon[] icons;
	public ItemFlatGeneric() 
	{
		super();
		this.hasSubtypes = false;
		this.setMaxDamage(0);
		this.maxStackSize = 25;
		this.setCreativeTab(null);
	}

	public ItemFlatGeneric(int id, String tex) 
	{
		super();
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		//if(par1ItemStack.stackSize == 0)
			((EntityPlayer)par3Entity).inventory.setInventorySlotContents(par4, null);
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
	{
		return false;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if(this.metaNames == null) {
			this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
		} else
		{
			icons = new IIcon[this.metaNames.length];
			for(int i = 0; i < this.metaNames.length; i++)
			{
				this.icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + metaNames[i]);
			}
		}
	}

	@Override
	public IIcon getIconFromDamage(int damage)
	{
		if(this.metaNames == null) {
			return this.itemIcon;
		} else {
			return icons[damage];
		}
	}
}
