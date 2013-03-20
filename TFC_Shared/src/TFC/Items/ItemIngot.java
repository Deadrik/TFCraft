package TFC.Items;

import TFC.TFCBlocks;
import TFC.Blocks.BlockIngotPile;
import TFC.Enums.EnumMetalType;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.TileEntities.TileEntityIngotPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemIngot extends ItemTerra
{
	EnumSize size = EnumSize.SMALL;
	public EnumMetalType MetalType;
	
	public ItemIngot(int i, EnumMetalType metalType) 
	{
		super(i);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		MetalType = metalType;
	}

	@Override
	public EnumSize getSize() {
		return size;
	}

	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

	public ItemIngot setSize(EnumSize s)
	{
		size = s;
		return this;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}

	private boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y,
			int z, int side, int l) 
	{

		boolean fullStack = true;
		
		TileEntityIngotPile te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z);
		
		if (world.getBlockId(x,y,z) == TFCBlocks.IngotPile.blockID)
		{
			if (te.contentsMatch(0,itemstack) && te.getStackInSlot(0).stackSize < te.getInventoryStackLimit()){

				fullStack = false;
				((TileEntityIngotPile)(world.getBlockTileEntity(x, y, z))).injectContents(0,1);
			}

		}

		if(fullStack)
		{
			if (itemstack.getItemDamage() >= 16)
			{
				if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y-1, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y-1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y-1, z);
				}
				else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y+1, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y+1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y+1, z);
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y, z-1, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z-1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z-1);
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y, z+1, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z+1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z+1);
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x-1, y, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x-1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x-1, y, z);
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x+1, y, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x+1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x+1, y, z);
				}
				else
				{
					return false;
				}
			}
			else
			{

				if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y-1, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y-1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y-1, z);
				}
				else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y+1, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y+1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y+1, z);
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y, z-1, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z-1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z-1);
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
				{
					world.setBlockAndMetadataWithNotify( x, y, z+1, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z+1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z+1);
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x-1, y, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x-1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x-1, y, z);
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
				{
					world.setBlockAndMetadataWithNotify( x+1, y, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x+1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x+1, y, z);
				}
				else
				{
					return false;
				}
			}

			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1,0);
				te.setType(this.MetalType.MetalID);

				if(entityplayer.capabilities.isCreativeMode)
				{
					te.storage[0] = new ItemStack(this,32,0);
				}
			}
		}		
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
			if(entityplayer.username.equals("capt_slowpoke") && /*(!entityplayer.worldObj.isRemote)||*/ entityplayer instanceof EntityPlayerMP){
				((EntityPlayerMP)entityplayer).mcServer.getConfigurationManager().sendChatMsg(entityplayer.username+" sniffs the ingot");

			}
			if(entityplayer.isSneaking() &&  !world.isRemote )
			{
				int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

				if(entityplayer.isSneaking() && (world.getBlockId(x, y, z) != TFCBlocks.IngotPile.blockID || (side != 1 && side != 0)))
				{
					
					if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir))
					{

						itemstack.stackSize = itemstack.stackSize-1;
						world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
						return true;

					}
				}
				else if(world.getBlockId(x, y, z) == TFCBlocks.IngotPile.blockID)
				{
					TileEntityIngotPile te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z);
					TileEntityIngotPile te2 = (TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
					if(te != null)
					{
						te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
						if(te.storage[0] != null && te.contentsMatch(0,itemstack)) 
						{
							te.injectContents(0,1);
						} 
						else if(te.storage[0] == null) 
						{
							te.addContents(0, new ItemStack(this,1, itemstack.getItem().itemID));
						} 
						else
						{
							if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir))
							{						
								itemstack.stackSize = itemstack.stackSize-1;
								if (world.getBlockTileEntity(x,y,z) != null)
								{
									if (((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).getType()==-1)
									{
										((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.MetalType.MetalID);
									}
								}
								world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
								te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
							}
							return true;

						}
						itemstack.stackSize = itemstack.stackSize-1;
						if (world.getBlockTileEntity(x,y,z) != null)
						{
							((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.MetalType.MetalID);
						}
						world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
						return true;
					}

				}
				else
				{
					int m = itemstack.getItemDamage();
					if(side == 1)
					{
						if (m>=16){
							world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.IngotPile.blockID, m, 3);
							itemstack.stackSize = itemstack.stackSize-1;
						}
						else{
							world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.IngotPile.blockID, m, 3);
							itemstack.stackSize = itemstack.stackSize-1;
						}
					}
					else if(side == 0 && world.getBlockId(x, y-1, z) == 0)
					{
						if(m >=16){
							world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.IngotPile.blockID, m, 3);
							itemstack.stackSize = itemstack.stackSize-1;
						}
						else{
							world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.IngotPile.blockID, m, 3);
							itemstack.stackSize = itemstack.stackSize-1;
						}
					}
					else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
					{
						setSide(world, itemstack, m, dir, x, y, z, 0, 0, -1);
					}
					else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
					{
						setSide(world, itemstack, m, dir, x, y, z, 0, 0, 1);
					}
					else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
					{
						setSide(world, itemstack, m, dir, x, y, z, -1, 0, 0);
					}
					else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
					{
						setSide(world, itemstack, m, dir, x, y, z, 1, 0, 0);
					}
					if (world.getBlockTileEntity(x,y,z) != null){
						((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.itemID - 16028 - 256);
					}
					world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
					return true;
				}

			}
		return false;
	}

	public void setSide(World world, ItemStack itemstack, int m, int dir, int x, int y, int z, int i, int j, int k)
	{
		if(m < 8)
		{
			if(dir == 0 || dir == 2)
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m, 3);
			else
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m | 8, 3);
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 16)
		{
			if(dir == 0 || dir == 2)
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m-8, 3);
			else
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m-8 | 8, 3);
			itemstack.stackSize = itemstack.stackSize-1;
		}

	}
}