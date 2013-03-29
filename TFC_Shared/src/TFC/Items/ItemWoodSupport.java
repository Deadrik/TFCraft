package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.Helper;
import TFC.Enums.EnumSize;

public class ItemWoodSupport extends ItemTerra
{
	Boolean isVertical;

	String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

	public ItemWoodSupport(int i, Boolean vert) 
	{
		super(i);
		isVertical = vert;
		this.hasSubtypes = true;
		this.setMaxDamage(0);
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}
	
	Icon[] icons = new Icon[16];
	@Override
	public void updateIcons(IconRegister registerer)
    {
		for(int i = 0; i < 16; i++){
			registerer.registerIcon("wood/"+WoodNames[i]+" Vertical Support");
			registerer.registerIcon("wood/"+WoodNames[i]+" Horizontal Support");
		}
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack) 
	{
		return getUnlocalizedName() + WoodNames[itemstack.getItemDamage()];
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}
	private Boolean getSupportInRange(World world, int x, int y, int z, int range, int supportID)
	{
		for(int i = 0; i < range; i++)
		{
			if(world.getBlockId(x+i, y, z) == supportID)
			{
				return true;
			}
			if(world.getBlockId(x-i, y, z) == supportID)
			{
				return true;
			}
			if(world.getBlockId(x, y, z+i) == supportID)
			{
				return true;
			}
			if(world.getBlockId(x, y, z-i) == supportID)
			{
				return true;
			}
		}
		return false;
	}



	private Boolean isNearVerticalSupport(World world, int i, int j, int k)
	{
		for(int y = -1; y < 0; y++)
		{
			for(int x = -6; x < 4; x++)
			{
				for(int z = -6; z < 4; z++)
				{

					if(world.getBlockId(i+x, j+y, k+z) == TFCBlocks.WoodSupportV.blockID)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private int isNextToSupport(World world, int x, int y, int z)
	{
		if(world.getBlockId(x+1, y, z) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x+1, y, z) == TFCBlocks.WoodSupportH.blockID)
		{
			return 5;
		}
		if(world.getBlockId(x-1, y, z) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x-1, y, z) == TFCBlocks.WoodSupportH.blockID)
		{
			return 4;
		}
		if(world.getBlockId(x, y, z+1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x, y, z+1) == TFCBlocks.WoodSupportH.blockID)
		{
			return 3;
		}
		if(world.getBlockId(x, y, z-1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x, y, z-1) == TFCBlocks.WoodSupportH.blockID)
		{
			return 2;
		}
		return 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		if(objectMouseOver == null) {
			return itemstack;
		}		
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;
		int side = objectMouseOver.sideHit;

		int metaID = itemstack.getItemDamage();
		int id0 = world.getBlockId(x, y-1, z);
		Boolean vSupport = id0 == TFCBlocks.WoodSupportV.blockID;
		Boolean b1 = world.isBlockOpaqueCube(x, y-2, z);

		//bottom
		if(!isVertical)
		{
			if(side == 0 && world.getBlockId(x, y-1, z) == 0 /*&& world.isBlockOpaqueCube(x, y, z)*/)
			{
				boolean nextToSupport = isNextToSupport(world,x,y-1,z) != 0;
				boolean SupportRange1 = getSupportInRange(world, x,y-1,z,5,TFCBlocks.WoodSupportV.blockID);
				boolean SupportRange2 = getSupportInRange(world, x,y-2,z,5,TFCBlocks.WoodSupportV.blockID);
				if(nextToSupport && (SupportRange1 || SupportRange2) || world.getBlockId(x, y-2, z) == TFCBlocks.WoodSupportV.blockID)
				{
					world.setBlock( x, y-1, z, TFCBlocks.WoodSupportH.blockID, metaID, 0x2);
					return new ItemStack(this,itemstack.stackSize-1, metaID);
				}
			}
			else if(side == 2 && world.getBlockId(x, y, z-1) == 0 /*&& world.isBlockOpaqueCube(x, y+1, z-1)*/)
			{
				if(isNextToSupport(world,x,y,z-1) != 0 && 
						(getSupportInRange(world, x,y,z-1,5,TFCBlocks.WoodSupportV.blockID) || 
								getSupportInRange(world, x,y-1,z-1,5,TFCBlocks.WoodSupportV.blockID)) || 
								world.getBlockId(x, y-1, z-1) == TFCBlocks.WoodSupportV.blockID)
				{
					world.setBlock( x, y, z-1, TFCBlocks.WoodSupportH.blockID, metaID, 0x2);
					return new ItemStack(this,itemstack.stackSize-1, metaID);
				}
			}
			else if(side == 3 && world.getBlockId(x, y, z+1) == 0 /*&& world.isBlockOpaqueCube(x, y+1, z+1)*/)
			{
				if(isNextToSupport(world,x,y,z+1) != 0 && 
						(getSupportInRange(world, x,y,z+1,5,TFCBlocks.WoodSupportV.blockID) || 
								getSupportInRange(world, x,y-1,z+1,5,TFCBlocks.WoodSupportV.blockID)) || 
								world.getBlockId(x, y-1, z+1) == TFCBlocks.WoodSupportV.blockID)
				{
					world.setBlock( x, y, z+1, TFCBlocks.WoodSupportH.blockID, metaID, 0x2);
					return new ItemStack(this,itemstack.stackSize-1, metaID);
				}
			}
			else if(side == 4 && world.getBlockId(x-1, y, z) == 0 /*&& world.isBlockOpaqueCube(x-1, y+1, z)*/)
			{
				if(isNextToSupport(world,x-1,y,z) != 0  && 
						(getSupportInRange(world, x-1,y,z,5,TFCBlocks.WoodSupportV.blockID) || 
								getSupportInRange(world, x-1,y-1,z,5,TFCBlocks.WoodSupportV.blockID)) || 
								world.getBlockId(x-1, y-1, z) == TFCBlocks.WoodSupportV.blockID)
				{
					world.setBlock( x-1, y, z, TFCBlocks.WoodSupportH.blockID, metaID, 0x2);
					return new ItemStack(this,itemstack.stackSize-1, metaID);
				}
			}
			else if(side == 5 && world.getBlockId(x+1, y, z) == 0 /*&& world.isBlockOpaqueCube(x+1, y+1, z)*/)
			{
				if(isNextToSupport(world,x+1,y,z) != 0 && 
						(getSupportInRange(world, x+1,y,z,5,TFCBlocks.WoodSupportV.blockID) || 
								getSupportInRange(world, x+1,y-1,z,5,TFCBlocks.WoodSupportV.blockID)) || 
								world.getBlockId(x+1, y-1, z) == TFCBlocks.WoodSupportV.blockID)
				{
					world.setBlock( x+1, y, z, TFCBlocks.WoodSupportH.blockID, metaID, 0x2);
					return new ItemStack(this,itemstack.stackSize-1, metaID);
				}
			}
		}
		else if(isVertical)
		{
			if(side == 0)
			{
				//if the block beneath is opaque or is another support
				if(vSupport || b1 && world.getBlockId(x, y-1, z) == 0)
				{
					world.setBlock( x, y-1, z, TFCBlocks.WoodSupportH.blockID, metaID, 0x2);
					return new ItemStack(this,itemstack.stackSize-1, metaID);
				}
			}
			//top
			else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlock( x, y+1, z, TFCBlocks.WoodSupportV.blockID, metaID, 0x2);
				return new ItemStack(this,itemstack.stackSize-1, metaID);
			}
			else if(side == 2 && (world.getBlockId(x, y-1, z-1) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x, y-1, z-1)) &&
					world.getBlockId(x, y, z-1) == 0)
			{
				world.setBlock( x, y, z-1, TFCBlocks.WoodSupportV.blockID, metaID, 0x2);
				return new ItemStack(this,itemstack.stackSize-1, metaID);
			}
			else if(side == 3 && (world.getBlockId(x, y-1, z+1) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x, y-1, z+1)) &&
					world.getBlockId(x, y, z+1) == 0)
			{
				world.setBlock( x, y, z+1, TFCBlocks.WoodSupportV.blockID, metaID, 0x2);
				return new ItemStack(this,itemstack.stackSize-1, metaID);
			}
			else if(side == 4 && (world.getBlockId(x-1, y-1, z) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x-1, y-1, z)) &&
					world.getBlockId(x-1, y, z) == 0)
			{
				world.setBlock( x-1, y, z, TFCBlocks.WoodSupportV.blockID, metaID, 0x2);
				return new ItemStack(this,itemstack.stackSize-1, metaID);
			}
			else if(side == 5 && (world.getBlockId(x+1, y-1, z) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x+1, y-1, z)) &&
					world.getBlockId(x+1, y, z) == 0)
			{
				world.setBlock( x+1, y, z, TFCBlocks.WoodSupportV.blockID, metaID, 0x2);
				return new ItemStack(this,itemstack.stackSize-1, metaID);
			}
		}

		return itemstack;
	}
}
