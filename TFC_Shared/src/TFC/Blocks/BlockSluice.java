package TFC.Blocks;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntitySluice;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.world.gen.feature.*;

public class BlockSluice extends BlockContainer
{
	TileEntitySluice entity;
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

	public BlockSluice(int i)
	{
		super(i, Material.wood);
		needsRandomTick = true;
//		entity = new TileEntityTerraSluice();
//		entity.canUpdate();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		//Minecraft mc = ModLoader.getMinecraftInstance();
		TileEntity te = world.getBlockTileEntity(i, j, k);

		if(world.isRemote)
		{
			return true;
		} else
		{
			if(!isBlockFootOfBed(meta))
			{
				if((TileEntitySluice)world.getBlockTileEntity(i, j, k)!=null)
				{
					TileEntitySluice tileentitysluice;
					tileentitysluice = (TileEntitySluice)world.getBlockTileEntity(i, j, k);
					ItemStack is =entityplayer.getCurrentEquippedItem();
					if(is!= null && is.itemID == TFCItems.terraGoldPan.itemID && is.getItemDamage() != 0)
					{
						tileentitysluice.soilAmount+=7;
						tileentitysluice.soilType = (byte) is.getItemDamage();
						if(tileentitysluice.soilAmount > 50) {
							tileentitysluice.soilAmount = 50;
						}
						is.setItemDamage(0);
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, is);
						/*The line below was uncommented before the smp inclusion. If this section isn't working it's because the above line never worked properly.
						 * Apparently I was doing the same thing twice, so I'm not sure if one of these wasn't working.*/
						//mc.thePlayer.inventory.setInventorySlotContents(mc.thePlayer.inventory.currentItem, new ItemStack(mod_TFC_Core.terraGoldPan,1,0));
						return true;
					}
					entityplayer.openGui(TerraFirmaCraft.instance, 25, world, i, j, k);
					//ModLoader.openGUI(entityplayer, new GuiTerraSluice(entityplayer.inventory, tileentitysluice));
				}
			}
			return true;
		}
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(j == 4) {
			return Block.waterMoving.getBlockTextureFromSideAndMetadata(i, 0);
		} else {
			return Block.planks.getBlockTextureFromSideAndMetadata(i, 0);
		}
	}
	
	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}
	public static boolean isBlockFootOfBed(int i)
	{
		return (i & 8) != 0;
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
	@Override
	public int getRenderType()
	{
		return TFCBlocks.sluiceRenderId;
	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		if(!isBlockFootOfBed(i)) {
			return TFCItems.terraSluiceItem.itemID;
		} else {
			return 0;
		}
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack is)
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
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
		if(world.getBlockId(i, j, k) == this.blockID)
		{
			world.setBlock(i + byte0, j, k + byte1, this.blockID, l + 8, 3);
		}
		
		//Minecraft mc = ModLoader.getMinecraftInstance();
		//mc.ingameGUI.addChatMessage("Dir = "+(new StringBuilder()).append(l).toString());
	}
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockId(par2, par3, par4);
        
        int dir = getDirectionFromMetadata(par1World.getBlockMetadata(par2, par3, par4));
        int[] offset = headBlockToFootBlockMap[dir];
        
        boolean stay = (canStay(par1World, par2,par3,par4,false,dir) && 
                canStay(par1World, par2+offset[0],par3,par4+offset[1],true,dir)) && 
                (var5 == 0 || blocksList[var5].blockMaterial.isReplaceable());
        
        return stay;
    }
	
	public boolean canPlace(World world, int i, int j, int k,int dir)
    {
	    int var5 = world.getBlockId(i, j, k);
        
        int[] offset = headBlockToFootBlockMap[dir];
        
        boolean stay = (canStay(world, i,j,k,false,dir) && 
                canStay(world, i+offset[0],j,k+offset[1],true,dir)) && 
                (var5 == 0 || blocksList[var5].blockMaterial.isReplaceable());
        
        return stay;
    }

	private boolean canStay(World world, int i, int j, int k, boolean foot, int dir)
	{
	    int l = dir;
        if(l == 0)//+z
        {
            if(!foot && 
                    (!world.isBlockNormalCube(i+1, j, k) || 
                    !world.isBlockNormalCube(i-1, j, k) || 
                    !world.isBlockNormalCube(i, j, k-1) || 
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
            else if(foot && 
                    (!world.isBlockNormalCube(i+1, j, k) || 
                    !world.isBlockNormalCube(i-1, j, k) || 
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
        }
        if(l == 1)//-x
        {
            if(!foot && 
                    (!world.isBlockNormalCube(i, j, k+1) || 
                    !world.isBlockNormalCube(i, j, k-1) || 
                    !world.isBlockNormalCube(i+1, j, k)  ||
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
            else if(foot && 
                    (!world.isBlockNormalCube(i, j, k+1) || 
                     !world.isBlockNormalCube(i, j, k-1) || 
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
        }
        if(l == 2)//-z
        {
            if(!foot && 
                    (!world.isBlockNormalCube(i+1, j, k) || 
                    !world.isBlockNormalCube(i-1, j, k) || 
                    !world.isBlockNormalCube(i, j, k+1) || 
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
            else if(foot && 
                    (!world.isBlockNormalCube(i+1, j, k) || 
                    !world.isBlockNormalCube(i-1, j, k) || 
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
        }
        if(l == 3)//+x
        {
            if(!foot && 
                    (!world.isBlockNormalCube(i, j, k+1) || 
                    !world.isBlockNormalCube(i, j, k-1) || 
                    !world.isBlockNormalCube(i-1, j, k)  ||
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
            else if(foot && 
                    (!world.isBlockNormalCube(i, j, k+1) || 
                     !world.isBlockNormalCube(i, j, k-1) || 
                    !world.isBlockNormalCube(i, j-1, k)  || 
                    world.isBlockNormalCube(i, j+2, k)))
                return false;
        }
        return true;
	}
	
//	@Override
//	public void onBlockRemoval(World world, int i, int j, int k)
//	{
//		if((world.getBlockMetadata(i, j, k) & 8) == 0)
//		{
//			world.setBlock(i, j, k, 0);
//		}
//	}
	
	@Override
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
			if(world.getBlockId(i - headBlockToFootBlockMap[j1][0], j, k - headBlockToFootBlockMap[j1][1]) != blockID || !canStay(world, i, j, k,true,j1))
			{
				world.setBlock(i, j, k, 0);
			}
		}
		else
		{
			if(world.getBlockId(i + headBlockToFootBlockMap[j1][0], j, k + headBlockToFootBlockMap[j1][1]) != blockID || !canStay(world, i, j, k,false,j1))
			{
				world.setBlock(i, j, k, 0);
				if(!world.isRemote)
				{
					dropBlockAsItem(world, i, j, k, i1, 0);
				}
			}
		}
	}
	@Override
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
	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		if(!isBlockFootOfBed(meta)) {
			return new TileEntitySluice();
		}
		return null;
	}

}
