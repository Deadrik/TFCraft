package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.Entities.EntityStand;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmourStand2 extends ItemArmourStand
{
	public ItemArmourStand2(Block par1) 
	{
		super(par1);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}
	
	@Override
	protected boolean CreateStand(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		float rotation = (((int)((((entityplayer.rotationYaw)%360)+360)+45)/90)*90)%360;
		rotation = (rotation + 360)%360;
		if(side == 1 && world.isAirBlock(x, y + 1, z) && isValid(world, x, y + 1, z) && world.isBlockNormalCubeDefault(x, y, z, false))
		{
			EntityStand es = new EntityStand(world,rotation,itemstack.getItemDamage()+16);
			es.setLocationAndAngles(x+0.5,y+1,z+0.5,rotation,0);
			world.spawnEntityInWorld(es);
			return true;
		}
		else
		{
			return false;
		}
	}
}