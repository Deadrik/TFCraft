package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Entities.EntityStand;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemArmorStand extends ItemTerra
{
	public ItemArmorStand()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(TFCTabs.TFCDecoration);
		this.MetaNames = Global.WOOD_ALL.clone();
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.LARGE);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		if(world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(i, j-0.1, k, i+1, j+2, k+1)).size() == 0){
			return true;
		}
		return false;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	IIcon[] icons = new IIcon[Global.WOOD_ALL.length];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+Global.WOOD_ALL[i]+" Log");
		}
	}
	
	private boolean CreateStand(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		float rotation = (((int)((((entityplayer.rotationYaw)%360)+360)+45)/90)*90)%360;
		rotation = (rotation + 360)%360;
		if(side == 1 && world.isAirBlock(x, y + 1, z) && isValid(world, x, y + 1, z) && world.isBlockNormalCubeDefault(x, y, z, false))
		{
			EntityStand es = new EntityStand(world,rotation);
			es.setLocationAndAngles(x+0.5,y+1,z+0.5,rotation,0);
			world.spawnEntityInWorld(es);
		}
		else
		{
			return false;
		}

		return true;
	}


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
				int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
				if (CreateStand(itemstack, entityplayer, world, x, y, z, side, dir)) {
					itemstack.stackSize = itemstack.stackSize-1;
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.LogNatural.stepSound.func_150496_b(), (TFCBlocks.LogNatural.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.LogNatural.stepSound.getPitch() * 0.8F);
				}
				return true;
		}
		return false;
	}

	public void setSide(World world, ItemStack itemstack, int m, int side, int x, int y, int z)
	{
		// don't call this function with side==0 or side==1, it won't do anything

		int meta = m % 8;
		Block log = TFCBlocks.WoodHoriz;
		switch (m/8) {
			case 0:
				log = TFCBlocks.WoodHoriz;
				break;
			case 1:
				log = TFCBlocks.WoodHoriz2;
				break;
			case 2:
				log = TFCBlocks.WoodHoriz3;
				break;
			case 3:
				log = TFCBlocks.WoodHoriz4;
				break;
		}

		if (side == 2 || side == 3) {
			world.setBlock(x, y, z, log, meta, 0x2);
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if (side == 4 || side == 5) {
			world.setBlock(x, y, z, log, meta | 8, 0x2);
			itemstack.stackSize = itemstack.stackSize-1;
		}
	}
}
