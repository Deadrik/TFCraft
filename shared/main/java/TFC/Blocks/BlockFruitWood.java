package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Core.Recipes;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import TFC.TileEntities.TileEntityFruitTreeWood;

public class BlockFruitWood extends BlockTerraContainer
{
    public BlockFruitWood(int i) 
    {
        super(i, Material.wood);
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
    public int damageDropped(int j) {
        return j;
    }	

    String[] WoodNames = {"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum"};
    Icon[] icons = new Icon[9];
    
    @Override
    public Icon getIcon(int i, int j) 
    {
        return icons[j];
    }
    
    @Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < 9; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/"+WoodNames[i]+" Wood");
		}
    }


    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {		
        //we need to make sure the player has the correct tool out
        boolean isAxeorSaw = false;
        ItemStack equip = entityplayer.getCurrentEquippedItem();
        if(equip!=null)
        {
            for(int cnt = 0; cnt < Recipes.Axes.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == Recipes.Axes[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == Recipes.Saws[cnt])
                {
                    isAxeorSaw = true;
                }
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
            world.setBlock(i, j, k, blockID, l, 0x2);
        }
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.Logs.itemID;
    }

    @Override
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
            world.setBlock(i, j, k, 0);
        }
    }

    @Override
    public int getRenderType()
    {
        return TFCBlocks.woodFruitRenderId;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}


	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j-1, k) == this.blockID || world.isBlockOpaqueCube(i, j-1, k))
        {
            return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
        }
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j-1, k) == this.blockID || world.isBlockOpaqueCube(i, j-1, k))
        {
            return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
        }
        return AxisAlignedBB.getBoundingBox(i, j+0.4, k, i+1, j+0.6, k+1);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random rand)
    {
        FloraManager manager = FloraManager.getInstance();
        FloraIndex fi = manager.findMatchingIndex(this.getType(world.getBlockMetadata(i, j, k)));
        
        float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);
        
        if(!world.isRemote && world.getBlockTileEntity(i, j, k) != null && TFC_Time.currentMonth < 6 && 
                fi != null && temp >= fi.minTemp && temp < fi.maxTemp)
        {
            TileEntityFruitTreeWood te = (TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k);
            int t = 1;
            if(TFC_Time.currentMonth < 3)
                t = 2;
            
            int leafGrowthRate = 20;
            int trunkGrowTime = 30;
            int branchGrowTime = 20;

            //grow upward
            if(te.birthTimeWood + trunkGrowTime < TFC_Time.getTotalDays() && te.height < 3 && te.isTrunk && rand.nextInt(16/t) == 0 &&
                    (world.getBlockId(i, j+1, k) == 0 || world.getBlockId(i, j+1, k) == TFCBlocks.fruitTreeLeaves.blockID))
            {
                    world.setBlock(i, j+1, k, this.blockID, world.getBlockMetadata(i, j, k), 0x2);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setTrunk(true);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setHeight(te.height+1);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setBirth();

                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setBirthWood(trunkGrowTime);
            }
            else if(te.birthTimeWood + branchGrowTime < TFC_Time.getTotalDays() && te.height == 2 && te.isTrunk && rand.nextInt(16/t) == 0 &&
                    world.getBlockId(i, j+1, k) != blockID)
            {
                    int r = rand.nextInt(4);
                    if(r == 0 && world.getBlockId(i+1, j, k) == 0 || world.getBlockId(i+1, j, k) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlock(i+1, j, k, this.blockID, world.getBlockMetadata(i, j, k), 0x2);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i+1, j, k)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i+1, j, k)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i+1, j, k)).setBirth();
                    }
                    else if(r == 1 && world.getBlockId(i, j, k-1) == 0 || world.getBlockId(i, j, k-1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlock(i, j, k-1, this.blockID, world.getBlockMetadata(i, j, k), 0x2);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k-1)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k-1)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k-1)).setBirth();
                    }
                    else if(r == 2 && world.getBlockId(i-1, j, k) == 0 || world.getBlockId(i-1, j, k) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlock(i-1, j, k, this.blockID, world.getBlockMetadata(i, j, k), 0x2);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i-1, j, k)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i-1, j, k)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i-1, j, k)).setBirth();
                    }
                    else if(r == 3 && world.getBlockId(i, j, k+1) == 0 || world.getBlockId(i, j, k+1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                        world.setBlock(i, j, k+1, this.blockID, world.getBlockMetadata(i, j, k), 0x2);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k+1)).setTrunk(false);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k+1)).setHeight(te.height);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k+1)).setBirth();
                    }

                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setBirthWood(branchGrowTime);
            }
            else if(te.birthTimeWood + 1 < TFC_Time.getTotalDays() && rand.nextInt(leafGrowthRate) == 0 && world.getBlockId(i, j+2, k) != blockID)
            {
                if(world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i, j+2, k) == 0 && BlockFruitLeaves.canStay(world, i, j+1, k, TFCBlocks.fruitTreeLeaves.blockID))//above
                {
                    world.setBlock(i, j+1, k, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i+1, j, k) == 0 && world.getBlockId(i+1, j+1, k) == 0 && BlockFruitLeaves.canStay(world, i+1, j, k, TFCBlocks.fruitTreeLeaves.blockID))//+x
                {
                    world.setBlock(i+1, j, k, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i-1, j, k) == 0 && world.getBlockId(i-1, j+1, k) == 0 && BlockFruitLeaves.canStay(world, i-1, j, k, TFCBlocks.fruitTreeLeaves.blockID))//-x
                {
                    world.setBlock(i-1, j, k, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i, j, k+1) == 0 && world.getBlockId(i, j+1, k+1) == 0 && BlockFruitLeaves.canStay(world, i, j, k+1, TFCBlocks.fruitTreeLeaves.blockID))//+z
                {
                    world.setBlock(i, j, k+1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i, j, k-1) == 0 && world.getBlockId(i, j+1, k-1) == 0 && BlockFruitLeaves.canStay(world, i, j, k-1, TFCBlocks.fruitTreeLeaves.blockID))//-z
                {
                    world.setBlock(i, j, k-1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i+1, j, k-1) == 0 && world.getBlockId(i+1, j+1, k-1) == 0 && BlockFruitLeaves.canStay(world, i+1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID))//+x/-z
                {
                    world.setBlock(i+1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i+1, j, k+1) == 0 && world.getBlockId(i+1, j+1, k+1) == 0 && BlockFruitLeaves.canStay(world, i+1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID))//+x/+z
                {
                    world.setBlock(i+1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i-1, j, k-1) == 0 && world.getBlockId(i-1, j+1, k-1) == 0 && BlockFruitLeaves.canStay(world, i-1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID))//-x/-z
                {
                    world.setBlock(i-1, j, k-1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
                }
                else if(world.getBlockId(i-1, j, k+1) == 0 && world.getBlockId(i-1, j+1, k+1) == 0 && BlockFruitLeaves.canStay(world, i-1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID))//-x/+z
                {
                    world.setBlock(i-1, j, k+1, TFCBlocks.fruitTreeLeaves.blockID, world.getBlockMetadata(i, j, k), 0x2);
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
                    	int meta = world.getBlockMetadata(i, j, k);
                        int id = meta < 8 ? TFCBlocks.fruitTreeLeaves.blockID : TFCBlocks.fruitTreeLeaves2.blockID;

                        if(world.getBlockId(i, j, k) != TFCBlocks.fruitTreeWood.blockID)
                            id = 0;

                        world.setBlock(i+x, j+y, k+z, id, world.getBlockMetadata(i, j, k), 0x2);
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


	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId,
			int metadata) {
		if(!world.isRemote && checkOut(world,x,y-1,z,metadata) && world.getBlockTileEntity(x, y-1, z) != null) {
			((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y-1, z)).setBirth();
		}
		super.breakBlock(world, x, y, z, blockId, metadata);
	}
}
