package TFC.Items;

import java.awt.image.BufferedImage;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Metal.MetalRegistry;
import TFC.TileEntities.TileEntityIngotPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemTerra implements ISmeltable
{
	EnumSize size = EnumSize.SMALL;
	BufferedImage bi;
	String metal;
	short metalAmount;
	boolean smeltable = true;
	public ItemIngot(int i) 
	{
		super(i);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.setFolder("ingots/");
		metalAmount = 100;
	}
	public ItemIngot(int i, boolean canSmelt) 
	{
		this(i);
		smeltable = canSmelt;
	}

	public ItemTerra setMetal(String m, int amt)
	{
		metal = m;
		metalAmount = (short) amt;
		return this;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
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

	@Override
	public ItemIngot setSize(EnumSize s)
	{
		size = s;
		return this;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	private boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y,
			int z, int side, int l) 
	{

		boolean fullStack = true;

		TileEntityIngotPile te = null;

		if (world.getBlockTileEntity(x, y, z) instanceof TileEntityIngotPile && world.getBlockId(x,y,z) == TFCBlocks.IngotPile.blockID)
		{
			te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z);
			if (te.contentsMatch(0,itemstack) && te.getStackInSlot(0).stackSize < te.getInventoryStackLimit()){

				fullStack = false;
				te.injectContents(0,1);
			}
		}
		else{fullStack = true;}

		if(fullStack && isPlaceable(itemstack))
		{
			if(side == 0 && world.getBlockId(x, y-1, z) == 0)
			{
				world.setBlock( x, y-1, z, TFCBlocks.IngotPile.blockID, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y-1, z);
				}
				te = (TileEntityIngotPile)world.getBlockTileEntity(x, y-1, z);
			}
			else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlock( x, y+1, z, TFCBlocks.IngotPile.blockID, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y+1, z);
				}
				te = (TileEntityIngotPile)world.getBlockTileEntity(x, y+1, z);
			}
			else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
			{
				world.setBlock( x, y, z-1, TFCBlocks.IngotPile.blockID, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y, z-1);
				}
				te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z-1);
			}
			else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
			{
				world.setBlock( x, y, z+1, TFCBlocks.IngotPile.blockID, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y, z+1);
				}
				te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z+1);
			}
			else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
			{
				world.setBlock( x-1, y, z, TFCBlocks.IngotPile.blockID, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x-1, y, z);
				}
				te = (TileEntityIngotPile)world.getBlockTileEntity(x-1, y, z);
			}
			else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
			{
				world.setBlock( x+1, y, z, TFCBlocks.IngotPile.blockID, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x+1, y, z);
				}
				te = (TileEntityIngotPile)world.getBlockTileEntity(x+1, y, z);
			}
			else
			{
				return false;
			}

			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1,0);
				te.setType(MetalRegistry.instance.getMetalFromItem(this).Name);

				if(entityplayer.capabilities.isCreativeMode)
				{
					te.storage[0] = new ItemStack(this,32,0);
				}
			}
		}		
		return true;
	}

	public boolean isPlaceable(ItemStack is)
	{
		int id = is.itemID;

		if(id == TFCItems.WeakSteelIngot.itemID || id == TFCItems.HCSteelIngot.itemID || id == TFCItems.HCBlackSteelIngot.itemID || 
				id == TFCItems.WeakRedSteelIngot.itemID || id == TFCItems.WeakBlueSteelIngot.itemID || 
				id == TFCItems.HCRedSteelIngot.itemID || id == TFCItems.HCBlueSteelIngot.itemID || id == TFCItems.UnknownIngot.itemID)
		{
			return false;
		}

		else
		{
			return true;
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound stackTagCompound = itemstack.getTagCompound();

		if(entityplayer.isSneaking() && stackTagCompound == null && (itemstack.getItem().getUnlocalizedName().indexOf("Double")==-1) && this.isPlaceable(itemstack))
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			if(!world.isRemote && entityplayer.isSneaking() && (world.getBlockId(x, y, z) != TFCBlocks.IngotPile.blockID || (side != 1 && side != 0)))
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
				//TileEntityIngotPile te2 = (TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
				if(te != null)
				{
					te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
					if(te.storage[0] != null && te.contentsMatch(0,itemstack) && te.storage[0].stackSize < 64) 
					{
						te.injectContents(0,1);
						te.validate();
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
								((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(MetalRegistry.instance.getMetalFromItem(this).Name);
							}
							world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
							te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
						}
						return true;

					}
					itemstack.stackSize = itemstack.stackSize-1;
					if (world.getBlockTileEntity(x,y,z) != null)
					{
						((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(MetalRegistry.instance.getMetalFromItem(this).Name);
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
						world.setBlock(x, y+1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y+1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					if(m >=16){
						world.setBlock(x, y-1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y-1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
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
				if (world.getBlockTileEntity(x,y,z) != null && world.getBlockTileEntity(x,y,z) instanceof TileEntityIngotPile)
				{
					//((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.itemID - 16028 - 256);
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
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 16)
		{
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m-8, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m-8 | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}

	}

	@Override
	public Metal GetMetalType(ItemStack is) 
	{
		if(metal == null) 
		{
			return MetalRegistry.instance.getMetalFromItem(this);
		} 
		else 
		{
			return MetalRegistry.instance.getMetalFromString(metal);
		}
	}

	@Override
	public short GetMetalReturnAmount(ItemStack is) {
		// TODO Auto-generated method stub
		return metalAmount;
	}

	@Override
	public boolean isSmeltable(ItemStack is) 
	{
		// TODO Auto-generated method stub
		return smeltable;
	}

	@Override
	public EnumTier GetSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}
}
