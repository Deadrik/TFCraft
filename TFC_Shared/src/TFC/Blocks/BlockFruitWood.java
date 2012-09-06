package TFC.Blocks;

import java.util.Random;

import TFC.Core.FloraIndex;
import TFC.Core.FloraManager;
import TFC.Core.TFCItems;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Core.Direction;
import TFC.TileEntities.TileEntityFruitTreeWood;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockFruitWood extends BlockContainer
{
    private Class EntityClass;
    public BlockFruitWood(int i, int index, Class c) 
    {
        super(i, Material.wood);
        this.blockIndexInTexture = index;
        EntityClass = c;
    }

    public void addCreativeItems(java.util.ArrayList list)
    {
        //for(int i = 0; i < 16; i++) {
        list.add(new ItemStack(this, 1, 0));
        //}
    }


    private boolean checkOut(World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) == blockID && world.getBlockMetadata(i, j, k) == l)
        {
            return true;
        }
        return false;
    }

    @Override
    protected int damageDropped(int j) {
        return j;
    }	

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        return j + blockIndexInTexture;
    }

    @Override
    public String getTextureFile() 
    {
        return "/bioxx/Vegetation.png";
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {		
        //we need to make sure teh palyer has the correct tool out
        boolean isAxeorSaw = false;
        ItemStack equip = entityplayer.getCurrentEquippedItem();
        if(equip!=null)
        {
            for(int cnt = 0; cnt < TFC_Core.Axes.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == TFC_Core.Axes[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            for(int cnt = 0; cnt < TFC_Core.Saws.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == TFC_Core.Saws[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            if(!isAxeorSaw && equip.getItem() == TFCItems.FlintPaxel)
            {
                isAxeorSaw = true;
            }
        }
        if(isAxeorSaw)
        {
            int x = i;
            int y = 0;
            int z = k;
            int count = 0;
            
            if(world.getBlockId(i, j+1, k) == blockID || world.getBlockId(i, j-1, k) == blockID)
            {
                //super.harvestBlock(world, entityplayer, i, j, k, l);
                boolean checkArray[][][] = new boolean[11][50][11];

                if(TFC_Core.isGrass(world.getBlockId(i, j+y-1, k)) || TFC_Core.isDirt(world.getBlockId(i, j+y-1, k)))
                {
                    boolean reachedTop = false;
                    while(!reachedTop)
                    {
                        if(l != 9 && l != 15 && world.getBlockId(x, j+y+1, z) == 0)
                        {
                            reachedTop = true;
                        }
                        else if((l == 9 || l == 15) && world.getBlockId(x, j+y+1, z) == 0
                                && world.getBlockId(x+1, j+y+1, z) != blockID && world.getBlockId(x-1, j+y+1, z) != blockID && world.getBlockId(x, j+y+1, z+1) != blockID &&
                                world.getBlockId(x, j+y+1, z-1) != blockID && world.getBlockId(x-1, j+y+1, z-1) != blockID && world.getBlockId(x-1, j+y+1, z+1) != blockID && 
                                world.getBlockId(x+1, j+y+1, z+1) != blockID && world.getBlockId(x+1, j+y+1, z-1) != blockID)
                        {
                            reachedTop = true;
                        }

                        scanLogs(world,i,j+y,k,l,checkArray,6,y,6);

                        y++;
                    }
                }
            }
            else if(world.getBlockId(i+1, j, k) == blockID || world.getBlockId(i-1, j, k) == blockID || world.getBlockId(i, j, k+1) == blockID || world.getBlockId(i, j, k-1) == blockID)
            {
                Random R = new Random();
                if(R.nextInt(100) > 50 && isAxeorSaw)
                {
                    dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.FruitTreeSapling1, 1, l));
                }
            }
        }
        else
        {
            world.setBlockAndMetadata(i, j, k, blockID, l);
        }
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.Logs.shiftedIndex;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean check = false;
        for(int h = -1; h <= 1; h++)
        {
            for(int g = -1; g <= 1; g++)
            {
                for(int f = -1; f <= 1; f++)
                {
                    if(world.getBlockId(i+h, j+g, k+f) == blockID && world.getBlockMetadata(i+h, j+g, k+f) == world.getBlockMetadata(i, j, k))
                    {
                        check = true;
                    }
                }
            }
        }
        if(!check)
        {
            world.setBlock(i, j, k, 0);
        }
    }

    private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray,int x, int y, int z)
    {
        if(y >= 0)
        {
            checkArray[x][y][z] = true;
            int offsetX = 0;int offsetY = 0;int offsetZ = 0;

            for (offsetY = 0; offsetY <= 1; offsetY++)
            {
                for (offsetX = -1; offsetX <= 1; offsetX++)
                {
                    for (offsetZ = -1; offsetZ <= 1; offsetZ++)
                    {
                        if(x+offsetX < 11 && x+offsetX >= 0 && z+offsetZ < 11 && z+offsetZ >= 0 && y+offsetY < 50 && y+offsetY >= 0)
                        {
                            if(checkOut(world, i+offsetX, j+offsetY, k+offsetZ, l) && !checkArray[x+offsetX][y+offsetY][z+offsetZ])
                            {
                                scanLogs(world,i+offsetX, j+offsetY, k+offsetZ, l, checkArray,x+offsetX,y+offsetY,z+offsetZ);
                            }
                        }
                    }
                }
            }

            world.setBlockWithNotify(i, j, k, 0);
            world.markBlockNeedsUpdate(i, j, k);
        }
    }

    public int getRenderType()
    {
        return TFCBlocks.woodFruitRenderId;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j-1, k) == this.blockID || world.isBlockOpaqueCube(i, j-1, k))
        {
            return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
        }
        return null;
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j-1, k) == this.blockID || world.isBlockOpaqueCube(i, j-1, k))
        {
            return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
        }
        return AxisAlignedBB.getBoundingBox(i, j+0.4, k, i+1, j+0.6, k+1);
    }

    public void updateTick(World world, int i, int j, int k, Random rand)
    {
        FloraManager manager = FloraManager.getInstance();
        FloraIndex fi = manager.findMatchingIndex(this.getType(world.getBlockMetadata(i, j, k)));
        if(!world.isRemote && world.getBlockTileEntity(i, j, k) != null && TFC_Time.currentMonth < 6 && 
                fi != null && world.getBiomeGenForCoords(i, k).getFloatTemperature() >= fi.minTemp && world.getBiomeGenForCoords(i, k).getFloatTemperature() < fi.maxTemp)
        {
            TileEntityFruitTreeWood te = (TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k);
            int t = 1;
            if(TFC_Time.currentMonth < 3)
                t = 2;
            
            int leafGrowthRate = 20;
            int trunkGrowTime = 30;
            int branchGrowTime = 20;

            //grow upward
            if(te.birthTimeWood + trunkGrowTime < TFC_Time.totalDays() && te.height < 3 && te.isTrunk && rand.nextInt(16/t) == 0 &&
                    (world.getBlockId(i, j+1, k) == 0 || world.getBlockId(i, j+1, k) == TFCBlocks.fruitTreeLeaves.blockID))
            {
                    world.setBlockAndMetadata(i, j+1, k, this.blockID, world.getBlockMetadata(i, j, k));
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setTrunk(true);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setHeight(te.height+1);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setBirth();

                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setBirthWood(trunkGrowTime);
            }
            else if(te.birthTimeWood + branchGrowTime < TFC_Time.totalDays() && te.height == 2 && te.isTrunk && rand.nextInt(16/t) == 0 &&
                    world.getBlockId(i, j+1, k) != blockID)
            {
                    int r = rand.nextInt(4);
                    if(r == 0 && world.getBlockId(i+1, j, k) == 0 || world.getBlockId(i+1, j, k) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlockAndMetadata(i+1, j, k, this.blockID, world.getBlockMetadata(i, j, k));
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i+1, j, k)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i+1, j, k)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i+1, j, k)).setBirth();
                    }
                    else if(r == 1 && world.getBlockId(i, j, k-1) == 0 || world.getBlockId(i, j, k-1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlockAndMetadata(i, j, k-1, this.blockID, world.getBlockMetadata(i, j, k));
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k-1)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k-1)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k-1)).setBirth();
                    }
                    else if(r == 2 && world.getBlockId(i-1, j, k) == 0 || world.getBlockId(i-1, j, k) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlockAndMetadata(i-1, j, k, this.blockID, world.getBlockMetadata(i, j, k));
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i-1, j, k)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i-1, j, k)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i-1, j, k)).setBirth();
                    }
                    else if(r == 3 && world.getBlockId(i, j, k+1) == 0 || world.getBlockId(i, j, k+1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlockAndMetadata(i, j, k+1, this.blockID, world.getBlockMetadata(i, j, k));
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k+1)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k+1)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k+1)).setBirth();
                    }

                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setBirthWood(branchGrowTime);
            }
            else if(te.birthTimeWood + 1 < TFC_Time.totalDays() && rand.nextInt(leafGrowthRate) == 0 && world.getBlockId(i, j+2, k) != blockID)
            {
                if(world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i, j+2, k) == 0 && BlockFruitLeaves.canStay(world, i, j+1, k, TFCBlocks.fruitTreeLeaves.blockID))//above
                {
                    world.setBlockAndMetadata(i, j+1, k, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i, j+1, k);
                }
                else if(world.getBlockId(i+1, j, k) == 0 && world.getBlockId(i+1, j+1, k) == 0 && BlockFruitLeaves.canStay(world, i+1, j, k, TFCBlocks.fruitTreeLeaves.blockID))//+x
                {
                    world.setBlockAndMetadata(i+1, j, k, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i+1, j, k);
                }
                else if(world.getBlockId(i-1, j, k) == 0 && world.getBlockId(i-1, j+1, k) == 0 && BlockFruitLeaves.canStay(world, i-1, j, k, TFCBlocks.fruitTreeLeaves.blockID))//-x
                {
                    world.setBlockAndMetadata(i-1, j, k, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i-1, j, k);
                }
                else if(world.getBlockId(i, j, k+1) == 0 && world.getBlockId(i, j+1, k+1) == 0 && BlockFruitLeaves.canStay(world, i, j, k+1, TFCBlocks.fruitTreeLeaves.blockID))//+z
                {
                    world.setBlockAndMetadata(i, j, k+1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i, j, k+1);
                }
                else if(world.getBlockId(i, j, k-1) == 0 && world.getBlockId(i, j+1, k-1) == 0 && BlockFruitLeaves.canStay(world, i, j, k-1, TFCBlocks.fruitTreeLeaves.blockID))//-z
                {
                    world.setBlockAndMetadata(i, j, k-1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i, j, k-1);
                }
                else if(world.getBlockId(i+1, j, k-1) == 0 && world.getBlockId(i+1, j+1, k-1) == 0 && BlockFruitLeaves.canStay(world, i+1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID))//+x/-z
                {
                    world.setBlockAndMetadata(i+1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i+1, j, k-1);
                }
                else if(world.getBlockId(i+1, j, k+1) == 0 && world.getBlockId(i+1, j+1, k+1) == 0 && BlockFruitLeaves.canStay(world, i+1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID))//+x/+z
                {
                    world.setBlockAndMetadata(i+1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i+1, j, k+1);
                }
                else if(world.getBlockId(i-1, j, k-1) == 0 && world.getBlockId(i-1, j+1, k-1) == 0 && BlockFruitLeaves.canStay(world, i-1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID))//-x/-z
                {
                    world.setBlockAndMetadata(i-1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i-1, j, k-1);
                }
                else if(world.getBlockId(i-1, j, k+1) == 0 && world.getBlockId(i-1, j+1, k+1) == 0 && BlockFruitLeaves.canStay(world, i-1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID))//-x/+z
                {
                    world.setBlockAndMetadata(i-1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    world.markBlockNeedsUpdate(i-1, j, k+1);
                }
            }
        }
    }

    public void SurroundWithLeaves(World world, int i, int j, int k)
    {
        for (int y = 0; y <= 1; y++)
        {
            for (int x = 1; x >= -1; x--)
            {
                for (int z = 1; z >= -1; z--)
                {
                    if(world.getBlockId(i+x, j+y, k+z) == 0 && (world.getBlockId(i+x, j+y+1, k+z) == 0 || world.getBlockId(i+x, j+y+2, k+z) == 0)) 
                    {
                        int id = TFCBlocks.fruitTreeLeaves.blockID;

                        if(world.getBlockId(i, j, k) != TFCBlocks.fruitTreeWood.blockID)
                            id = 0;

                        world.setBlockAndMetadata(i+x, j+y, k+z, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k));
                    }
                }
            }
        }
    }
    
    public String getType(int meta)
    {
            switch(meta)
            {
                case 0:
                {
                    return "red apple";
                }
                case 1:
                {
                    return "banana";
                }
                case 2:
                {
                    return "orange";
                }
                case 3:
                {
                    return "green apple";
                }
                case 4:
                {
                    return "lemon";
                }
                case 5:
                {
                    return "olive";
                }
                case 6:
                {
                    return "cherry";
                }
                case 7:
                {
                    return "peach";
                }
                case 8:
                {
                    return "plum";
                }
                case 9:
                {
                    return "cacao";
                }
            }
        return "";
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityFruitTreeWood();
	}
}
