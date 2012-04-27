package net.minecraft.src.TFC_Core;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraLogs extends Item implements ITextureProvider
{
	public static String[] blockNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
		"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};


	public ItemTerraLogs(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.maxStackSize = 4;
	}

	@Override
	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	private boolean CreatePile(ItemStack itemstack, World world, int x, int y,
			int z, int side, int l) {
		if(side == 0 && world.getBlockId(x, y-1, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y-1, z, mod_TFC_Core.LogPile.blockID, l);
			if(world.isRemote)
                world.markBlockNeedsUpdate(x, y-1, z);

			TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y-1, z);
			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1,itemstack.getItemDamage());
			}
			
			itemstack.stackSize--;
			return true;
		}
		else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y+1, z, mod_TFC_Core.LogPile.blockID, l);
			if(world.isRemote)
			    world.markBlockNeedsUpdate(x, y+1, z);

			TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y+1, z);
			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1, itemstack.getItemDamage());
			}
			itemstack.stackSize--;
            return true;
		}
		else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y, z-1, mod_TFC_Core.LogPile.blockID, l);
			if(world.isRemote)
                world.markBlockNeedsUpdate(x, y, z-1);

			TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y, z-1);
			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1, itemstack.getItemDamage());
			}
			itemstack.stackSize--;
            return true;
		}
		else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y, z+1, mod_TFC_Core.LogPile.blockID, l);
			if(world.isRemote)
                world.markBlockNeedsUpdate(x, y, z+1);

			TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y, z+1);
			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1, itemstack.getItemDamage());
			}
			itemstack.stackSize--;
            return true;
		}
		else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x-1, y, z, mod_TFC_Core.LogPile.blockID, l);
			if(world.isRemote)
                world.markBlockNeedsUpdate(x-1, y, z);

			TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x-1, y, z);
			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1, itemstack.getItemDamage());
			}
			itemstack.stackSize--;
            return true;
		}
		else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x+1, y, z, mod_TFC_Core.LogPile.blockID, l);
			if(world.isRemote)
                world.markBlockNeedsUpdate(x+1, y, z);

			TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x+1, y, z);
			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1, itemstack.getItemDamage());
			}
			itemstack.stackSize--;
            return true;
		} else {
			return false;
		}
	}

	public int getIconFromDamage(int par1)
	{
		return this.iconIndex+par1;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites2.png";
	}
	
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
	    MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
        if(objectMouseOver == null) {
            return false;
        }       
        int x = objectMouseOver.blockX;
        int y = objectMouseOver.blockY;
        int z = objectMouseOver.blockZ;
        int side = objectMouseOver.sideHit;
        int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

        if(world.getBlockId(x, y, z) != mod_TFC_Core.LogPile.blockID || entityplayer.isSneaking())
        {
            CreatePile(itemstack, world, x, y, z, side, dir); 
            return true;
        }
        else
        {
            TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y, z);
            if(te != null)
            {
                boolean created = false;
                if(te.storage[0] != null && te.contentsMatch(0,itemstack)) {
                    te.injectContents(0,1);
                } else if(te.storage[0] == null) {
                    te.addContents(0, new ItemStack(this,1, itemstack.getItemDamage()));
                } else if(te.storage[1] != null && te.contentsMatch(1,itemstack)) {
                    te.injectContents(1,1);
                } else if(te.storage[1] == null) {
                    te.addContents(1, new ItemStack(this,1, itemstack.getItemDamage()));
                } else if(te.storage[2] != null && te.contentsMatch(2,itemstack)) {
                    te.injectContents(2,1);
                } else if(te.storage[2] == null) {
                    te.addContents(2, new ItemStack(this,1, itemstack.getItemDamage()));
                } else if(te.storage[3] != null  && te.contentsMatch(3,itemstack)) {
                    te.injectContents(3,1);
                } else if(te.storage[3] == null) {
                    te.addContents(3, new ItemStack(this,1, itemstack.getItemDamage()));
                } else
                {
                    return CreatePile(itemstack, world, x, y, z, side, dir);
                }
                if(!created)
                {
                    return true;
                }
            }
        }
        return false;
    }

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		

		return itemstack;
	}

}
