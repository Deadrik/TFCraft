package TFC.Blocks;

import java.util.Random;

import TFC.TileEntities.TileEntityTerraSluice;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TFCItems;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Core;

public class BlockTerraSluice extends BlockContainer
{
	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}
	public static boolean isBlockFootOfBed(int i)
	{
		return (i & 8) != 0;
	}
	TileEntityTerraSluice entity;
	private int meta;
	private int xCoord;

	private int yCoord;

	private int zCoord;
	public static final int headBlockToFootBlockMap[][] = {
		{
			0, 1
		}, {
			-1, 0
		}, {
			0, -1
		}, {
			1, 0
		}
	};

	private Class EntityClass;

	public BlockTerraSluice(int i, Class tClass)
	{
		super(i, Material.wood);
		EntityClass = tClass;
		needsRandomTick = true;
		entity = new TileEntityTerraSluice();
		entity.canUpdate();
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		//Minecraft mc = ModLoader.getMinecraftInstance();
		TileEntity te = world.getBlockTileEntity(i, j, k);

		if(world.isRemote)
		{
			return true;
		} else
		{
			if(!isBlockFootOfBed(meta))
			{
				if((TileEntityTerraSluice)world.getBlockTileEntity(i, j, k)!=null)
				{
					TileEntityTerraSluice tileentitysluice;
					tileentitysluice = (TileEntityTerraSluice)world.getBlockTileEntity(i, j, k);
					ItemStack is =entityplayer.getCurrentEquippedItem();
					if(is!= null && is.itemID == TFCItems.terraGoldPan.shiftedIndex && is.getItemDamage() != 0)
					{
						tileentitysluice.soilAmount+=5;
						if(tileentitysluice.soilAmount > 50) {
							tileentitysluice.soilAmount = 50;
						}
						entityplayer.setItemInUse(new ItemStack(TFCItems.terraGoldPan, 1, 0), 0);
						/*The line below was uncommented before the smp inclusion. If this section isn't working it's because the above line never worked properly.
						 * Apparently I was doing the same thing twice, so I'm not sure if one of these wasn't working.*/
						//mc.thePlayer.inventory.setInventorySlotContents(mc.thePlayer.inventory.currentItem, new ItemStack(mod_TFC_Core.terraGoldPan,1,0));
						return true;
					}
					entityplayer.openGui(mod_TFC_Core.instance, mod_TFC_Core.terraSluiceGuiId, world, i, j, k);
					//ModLoader.openGUI(entityplayer, new GuiTerraSluice(entityplayer.inventory, tileentitysluice));
				}
			}
			return true;
		}
	}

	@Override
	public TileEntity getBlockEntity()
	{

		//Minecraft mc = ModLoader.getMinecraftInstance();
		try
		{
			if(!isBlockFootOfBed(meta)) {
				return (TileEntity) EntityClass.newInstance();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(j == 4) {
			return 223;
		} else {
			return 4;
		}
	}

	public boolean getIsRecievingWater(int i)
	{
		return (i & 4) != 0;
	}

	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = "Sluice";
		return s;
	}

	public int getRenderType()
	{
		return mod_TFC_Core.sluiceRenderId;
	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		if(!isBlockFootOfBed(i)) {
			return TFCItems.terraSluiceItem.shiftedIndex;
		} else {
			return 0;
		}
	}
	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
		byte byte0 = 0;
		byte byte1 = 0;
		if(l == 0)//+z
		{
			byte1 = 1;
		}
		if(l == 1)//-x
		{
			byte0 = -1;
		}
		if(l == 2)//-z
		{
			byte1 = -1;
		}
		if(l == 3)//+x
		{
			byte0 = 1;
		}
		world.setBlockMetadataWithNotify(i, j, k, l);
		if(world.getBlockId(i, j, k) == this.blockID)
		{
			world.setBlockAndMetadataWithNotify(i + byte0, j, k + byte1, this.blockID, l + 8);
		}
		//Minecraft mc = ModLoader.getMinecraftInstance();
		//mc.ingameGUI.addChatMessage("Dir = "+(new StringBuilder()).append(l).toString());
	}

	public void onBlockRemoval(World world, int i, int j, int k)
	{
		if((world.getBlockMetadata(i, j, k) & 8) == 0)
		{
			world.setBlock(i, j, k, 0);
		}
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		//Minecraft mc = ModLoader.getMinecraftInstance();
		int i1 = world.getBlockMetadata(i, j, k);
		int j1 = getDirectionFromMetadata(i1);

		if(isBlockFootOfBed(i1))
		{
			if(world.getBlockId(i - headBlockToFootBlockMap[j1][0], j, k - headBlockToFootBlockMap[j1][1]) != blockID)
			{
				world.setBlockWithNotify(i, j, k, 0);
			}
		}
		else
		{
			if(world.getBlockId(i + headBlockToFootBlockMap[j1][0], j, k + headBlockToFootBlockMap[j1][1]) != blockID)
			{
				world.setBlockWithNotify(i, j, k, 0);
				if(!world.isRemote)
				{
					dropBlockAsItem(world, i, j, k, i1, 0);
				}
			}
		}
		if(this.getBlockEntity()!= null)
		{
			this.getBlockEntity().canUpdate();
		}
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
	}

}
