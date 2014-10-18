package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Entities.EntityStand;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemArmourStand extends ItemTerraBlock
{
	public ItemArmourStand(Block i)
	{
		super(i);
		MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 16);
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		if(world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(i, j-0.1, k, i+1, j+2, k+1)).size() == 0){
			return true;
		}
		return false;
	}

	protected boolean CreateStand(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
		if(side == 1 && world.isAirBlock(x, y + 1, z) && isValid(world, x, y + 1, z) && world.isBlockNormalCubeDefault(x, y, z, false))
		{
			EntityStand es = new EntityStand(world,dir*90-180,itemstack.getItemDamage()+getOffset());
			es.setLocationAndAngles(x+0.5,y+1,z+0.5,dir*90-180,0);
			world.spawnEntityInWorld(es);
			return true;
		}
		else
		{
			return false;
		}
	}

	protected int getOffset()
	{
		return 0;
	}


	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if(!world.isRemote)
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			if (CreateStand(itemstack, entityplayer, world, x, y-1, z, side, dir)) {
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.LogNatural.stepSound.func_150496_b(), (TFCBlocks.LogNatural.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.LogNatural.stepSound.getPitch() * 0.8F);
			}
			return true;
		}
		return false;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.VERYLARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}
}