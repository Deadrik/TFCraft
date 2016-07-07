package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class ItemIngot extends ItemTerra implements ISmeltable
{
	private EnumSize size = EnumSize.SMALL;
	//private BufferedImage bi;
	private String metal;
	private short metalAmount;
	private boolean smeltable = true;

	public ItemIngot()
	{
		super();
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.setFolder("ingots/");
		metalAmount = 100;
	}

	public ItemIngot(boolean canSmelt)
	{
		this();
		smeltable = canSmelt;
	}

	public ItemTerra setMetal(String m, int amt)
	{
		metal = m;
		metalAmount = (short) amt;
		return this;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return size;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public ItemIngot setSize(EnumSize s)
	{
		size = s;
		return this;
	}

	public void addCreativeItems(List<ItemStack> list)
	{
		list.add(new ItemStack(this));
	}

	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{

		boolean fullStack = true;

		TEIngotPile te = null;

		if (world.getTileEntity(x, y, z) instanceof TEIngotPile && world.getBlock(x,y,z) == TFCBlocks.ingotPile)
		{
			te = (TEIngotPile)world.getTileEntity(x, y, z);
			if (te.contentsMatch(0,itemstack) && te.getStackInSlot(0).stackSize < te.getInventoryStackLimit())
			{
				fullStack = false;
				te.injectContents(0, 1);
			}
		}
		else{fullStack = true;}

		if(fullStack && isPlaceable(itemstack))
		{
			if(side == 0 && world.isAirBlock(x, y-1, z) && isValid(world, x, y-1, z))
			{
				world.setBlock( x, y-1, z, TFCBlocks.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y-1, z);
				}
				te = (TEIngotPile)world.getTileEntity(x, y-1, z);
			}
			else if(side == 1 && world.isAirBlock(x, y+1, z) && isValid(world, x, y+1, z))
			{
				world.setBlock( x, y+1, z, TFCBlocks.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y+1, z);
				}
				te = (TEIngotPile)world.getTileEntity(x, y+1, z);
			}
			else if(side == 2 && world.isAirBlock(x, y, z-1) && isValid(world, x, y, z-1))
			{
				world.setBlock( x, y, z-1, TFCBlocks.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y, z-1);
				}
				te = (TEIngotPile)world.getTileEntity(x, y, z-1);
			}
			else if(side == 3 && world.isAirBlock(x, y, z+1) && isValid(world, x, y, z+1))
			{
				world.setBlock( x, y, z+1, TFCBlocks.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y, z+1);
				}
				te = (TEIngotPile)world.getTileEntity(x, y, z+1);
			}
			else if(side == 4 && world.isAirBlock(x-1, y, z) && isValid(world, x-1, y, z))
			{
				world.setBlock( x-1, y, z, TFCBlocks.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x-1, y, z);
				}
				te = (TEIngotPile)world.getTileEntity(x-1, y, z);
			}
			else if(side == 5 && world.isAirBlock(x+1, y, z) && isValid(world, x+1, y, z))
			{
				world.setBlock( x+1, y, z, TFCBlocks.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x+1, y, z);
				}
				te = (TEIngotPile)world.getTileEntity(x+1, y, z);
			}
			else
			{
				return false;
			}

			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1,0);
				te.setType(MetalRegistry.instance.getMetalFromItem(this).name);

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
		Item id = is.getItem();

		return !(id == TFCItems.weakSteelIngot || id == TFCItems.highCarbonSteelIngot || id == TFCItems.highCarbonBlackSteelIngot || 
				id == TFCItems.weakRedSteelIngot || id == TFCItems.weakBlueSteelIngot || 
				id == TFCItems.highCarbonRedSteelIngot || id == TFCItems.highCarbonBlueSteelIngot);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound stackTagCompound = itemstack.getTagCompound();

		if (entityplayer.isSneaking() &&stackTagCompound == null && itemstack.getItem().getUnlocalizedName().indexOf("Double") == -1 &&
			this.isPlaceable(itemstack))
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			if (!world.isRemote && entityplayer.isSneaking() && (world.getBlock(x, y, z) != TFCBlocks.ingotPile || side != 1 && side != 0))
			{

				if(createPile(itemstack, entityplayer, world, x, y, z, side, dir))
				{

					itemstack.stackSize = itemstack.stackSize-1;
					world.addBlockEvent(x,y,z,TFCBlocks.ingotPile,0,0);
					return true;

				}
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.ingotPile)
			{
				TEIngotPile te = (TEIngotPile)world.getTileEntity(x, y, z);
				//TileEntityIngotPile te2 = (TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z);
				if(te != null)
				{
					te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
					if(te.storage[0] != null && te.contentsMatch(0,itemstack) && te.storage[0].stackSize < 64) 
					{
						te.injectContents(0,1);
						te.validate();
					} 
					else if(te.storage[0] != null && !te.contentsMatch(0,itemstack) && te.storage[0].stackSize < 64) 
					{
						return false;
					}
					else if(te.storage[0] == null) 
					{
						te.addContents(0, new ItemStack(this,1));
					} 
					else
					{
						if(createPile(itemstack, entityplayer, world, x, y, z, side, dir))
						{
							itemstack.stackSize = itemstack.stackSize-1;
							/*if (world.getTileEntity(x,y,z) != null)
							{
								//((TileEntityIngotPile)world.getTileEntity(x,y,z)).setType(MetalRegistry.instance.getMetalFromItem(this).Name);
							}*/
							world.addBlockEvent(x,y,z,TFCBlocks.ingotPile,0,0);
							te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
						}
						return true;

					}
					itemstack.stackSize = itemstack.stackSize-1;
					/*if (world.getTileEntity(x,y,z) != null)
					{
						//((TileEntityIngotPile)world.getTileEntity(x,y,z)).setType(MetalRegistry.instance.getMetalFromItem(this).Name);
					}*/
					world.addBlockEvent(x,y,z,TFCBlocks.ingotPile,0,0);
					return true;
				}

			}
			else
			{
				int m = itemstack.getItemDamage();
				if(side == 1)
				{
					if (m>=16){
						world.setBlock(x, y+1, z, TFCBlocks.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y+1, z, TFCBlocks.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 0 && world.isAirBlock(x, y-1, z))
				{
					if(m >=16){
						world.setBlock(x, y-1, z, TFCBlocks.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y-1, z, TFCBlocks.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 2 && world.isAirBlock(x, y, z-1))
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, -1);
				}
				else if(side == 3 && world.isAirBlock(x, y, z+1))
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, 1);
				}
				else if(side == 4 && world.isAirBlock(x-1, y, z))
				{
					setSide(world, itemstack, m, dir, x, y, z, -1, 0, 0);
				}
				else if(side == 5 && world.isAirBlock(x+1, y, z))
				{
					setSide(world, itemstack, m, dir, x, y, z, 1, 0, 0);
				}
				/*if (world.getTileEntity(x,y,z) != null && world.getTileEntity(x,y,z) instanceof TEIngotPile)
				{
					//((TileEntityIngotPile)world.getTileEntity(x,y,z)).setType(this.getItem() - 16028 - 256);
				}*/
				world.addBlockEvent(x,y,z,TFCBlocks.ingotPile,0,0);
				return true;
			}

		}
		return false;
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		if(world.isSideSolid(i, j-1, k, ForgeDirection.UP) || world.getBlock(i, j-1, k)==TFCBlocks.ingotPile)
		{
			TileEntity te = world.getTileEntity(i, j-1, k);

			if (te instanceof TEIngotPile)
			{
				TEIngotPile ip = (TEIngotPile)te;

				if (ip.storage[0] == null || ip.storage[0].stackSize < 64)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public void setSide(World world, ItemStack itemstack, int m, int dir, int x, int y, int z, int i, int j, int k)
	{
		if(m < 8)
		{
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.ingotPile, m, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.ingotPile, m | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 16)
		{
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.ingotPile, m-8, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.ingotPile, m-8 | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}

	}

	@Override
	public Metal getMetalType(ItemStack is) 
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
	public short getMetalReturnAmount(ItemStack is) {
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
	public EnumTier getSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		// hot or worked ingots cannot stack
		if (is.hasTagCompound())
		{
			NBTTagCompound tag = is.getTagCompound();
			if (TFC_ItemHeat.hasTemp(is) || tag.hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || tag.hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
			{
				return 1;
			}
		}

		return super.getItemStackLimit(is);
	}
}
