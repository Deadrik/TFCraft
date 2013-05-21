package TFC.Blocks.Vanilla;

import java.util.ArrayList;
import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Settings;
import TFC.Items.Tools.ItemCustomScythe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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
import net.minecraftforge.common.IShearable;

public class BlockCustomLeaves extends BlockLeaves implements IShearable
{
    int adjacentTreeBlocks[][][];
    
    Icon[] icons = new Icon[16];
	Icon[] iconsOpaque = new Icon[16];

    public BlockCustomLeaves(int i) 
    {
        super(i);
        this.setTickRandomly(false);
    }
    
    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return true;
    }
    
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
         return TerraFirmaCraft.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.motionX *= 0.8D;
        par5Entity.motionZ *= 0.8D;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return TFC_Settings.enableInnerGrassFix;
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	onNeighborBlockChange(par1World,par2,par3,par4,0);
        return;
    }
    /**onBlockRemoval needs to remain here in order to override the Block Leaves implementation 
     * of the method which causes leaves to change metadata*/
    @Override
    public void beginLeavesDecay(World world, int x, int y, int z)
    {
    	
    }

    @Override
    public void onNeighborBlockChange(World par1World, int xOrig, int yOrig, int zOrig, int l)
    {
        Random R = new Random();
        if (!par1World.isRemote)
        {
            int var6 = par1World.getBlockMetadata(xOrig, yOrig, zOrig);

            byte searchRadius = 4;
            int maxDist = searchRadius + 1;
            byte searchDistance = 11;
            int center = searchDistance / 2;
            adjacentTreeBlocks = null;
            if (this.adjacentTreeBlocks == null)
            {
                this.adjacentTreeBlocks = new int[searchDistance][searchDistance][searchDistance];
            }

            if (par1World.checkChunksExist(xOrig - maxDist, yOrig - maxDist, zOrig - maxDist, xOrig + maxDist, yOrig + maxDist, zOrig + maxDist))
            {
                for (int xd = -searchRadius; xd <= searchRadius; ++xd)
                {
                	int searchY = searchRadius - Math.abs(xd);
                    for (int yd = -searchY; yd <= searchY; ++yd)
                    {
                    	int searchZ = searchY - Math.abs(yd);
                        for (int zd = -searchZ; zd <= searchZ; ++zd)
                        {
                            int testID = par1World.getBlockId(xOrig + xd, yOrig + yd, zOrig + zd);

                            if (testID == TFCBlocks.Wood.blockID)
                            {
                                this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = 0;
                            }
                            else if (testID == TFCBlocks.Leaves.blockID && var6 == par1World.getBlockMetadata(xOrig + xd, yOrig + yd, zOrig + zd))
                            {
                                this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = -2;
                            }
                            else
                            {
                                this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = -1;
                            }
                        }
                    }
                }

                for (int pass = 1; pass <= 4; ++pass)
                {
                    for (int xd = -searchRadius; xd <= searchRadius; ++xd)
                    {
                    	int searchY = searchRadius - Math.abs(xd);
                        for (int yd = -searchY; yd <= searchY; ++yd)
                        {
                        	int searchZ = searchY - Math.abs(yd);
                            for (int zd = -searchZ; zd <= searchZ; ++zd)
                            {
                                if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center] == pass - 1)
                                {
                                    if (this.adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] == -2)
                                    {
                                        this.adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] = pass;
                                    }

                                    if (this.adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] == -2)
                                    {
                                        this.adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] = pass;
                                    }

                                    if (this.adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] == -2)
                                    {
                                        this.adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] = pass;
                                    }

                                    if (this.adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] == -2)
                                    {
                                        this.adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] = pass;
                                    }

                                    if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] == -2)
                                    {
                                        this.adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] = pass;
                                    }

                                    if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] == -2)
                                    {
                                        this.adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] = pass;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            int res = this.adjacentTreeBlocks[center][center][center];

            if (res < 0)
            {
                if(par1World.getChunkFromBlockCoords(xOrig, zOrig) != null)
                {
                    this.destroyLeaves(par1World, xOrig, yOrig, zOrig);
                }
            }
        }
    }

    private void destroyLeaves(World world, int par1, int par2, int par3)
    {
        world.setBlock(par1, par2, par3, 0);
        world.scheduleBlockUpdate(par1 - 1, par2, par3, blockID, 5);
        world.scheduleBlockUpdate(par1 + 1, par2, par3, blockID, 5);
        world.scheduleBlockUpdate(par1, par2 - 1, par3, blockID, 5);
        world.scheduleBlockUpdate(par1, par2 + 1, par3, blockID, 5);
        world.scheduleBlockUpdate(par1, par2, par3 - 1, blockID, 5);
        world.scheduleBlockUpdate(par1, par2, par3 + 1, blockID, 5);
        world.scheduleBlockUpdate(par1 - 1, par2, par3+1, blockID, 5);
        world.scheduleBlockUpdate(par1 - 1, par2, par3-1, blockID, 5);
        world.scheduleBlockUpdate(par1 + 1, par2, par3+1, blockID, 5);
        world.scheduleBlockUpdate(par1 + 1, par2, par3-1, blockID, 5);
    }

    private void removeLeaves(World world, int i, int j, int k)
    {
        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
        if(new Random().nextInt(100) < 30)
            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
        world.setBlock(i, j, k, 0);
    }

    public int quantityDropped(Random random)
    {
        return random.nextInt(20) != 0 ? 0 : 1;
    }
    
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCBlocks.Sapling.blockID;
    }
    
    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
    {
        if (!world.isRemote)
        {

        }
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (!world.isRemote && itemstack != null && itemstack.getItem() instanceof ItemCustomScythe)
        {
            for(int x = -1; x < 2; x++)
            {
                for(int z = -1; z < 2; z++)
                {
                    for(int y = -1; y < 2; y++)
                    {
                        if(world.getBlockMaterial( i+x, j+y, k+z) == Material.leaves && entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem) != null)
                        {
                            entityplayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
                            entityplayer.addExhaustion(0.045F);
                            if(new Random().nextInt(100) < 11)
                                dropBlockAsItem_do(world, i+x, j+y, k+z, new ItemStack(Item.stick, 1));
                            else if(new Random().nextInt(100) < 4 && l != 9 && l != 15)
                                dropBlockAsItem_do(world, i+x, j+y, k+z, new ItemStack(TFCBlocks.Sapling, 1, l));
                            removeLeaves(world, i+x, j+y, k+z);
                            super.harvestBlock(world, entityplayer, i+x, j+y, k+z, l);
                            
                            int ss = itemstack.stackSize;
                            int dam = itemstack.getItemDamage()+2;
                            
                            if(dam >= itemstack.getItem().getMaxDamage())
                            {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
                                        null);
                            }
                            else
                            {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
                                    new ItemStack(itemstack.getItem(),ss,dam));
                            }
                        }
                    }
                }
            }
        }
        else if(!world.isRemote)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            entityplayer.addExhaustion(0.025F);
            if(new Random().nextInt(100) < 28)
                dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
            else if(new Random().nextInt(100) < 6 && l != 9 && l != 15)
                dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCBlocks.Sapling, 1, l));

            super.harvestBlock(world, entityplayer, i, j, k, l);
        }
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @Override
    public boolean isOpaqueCube()
    {
        if(TerraFirmaCraft.proxy != null)
            return !TerraFirmaCraft.proxy.getGraphicsLevel();
        else
            return false;
    }

    @Override
    public Icon getIcon(int i, int j)
    {
        if (TerraFirmaCraft.proxy.getGraphicsLevel())
        {
        	return icons[j];
        }
        else
        {
            return iconsOpaque[j];
        }
    }
    
    String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < 16; i++)
		{
			icons[i] = iconRegisterer.registerIcon("wood/trees/" + WoodNames[i] + " Leaves Fancy");
			iconsOpaque[i] = iconRegisterer.registerIcon("wood/trees/" + WoodNames[i] + " Leaves");
		}
    }

    @Override
    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        super.onEntityWalking(world, i, j, k, entity);
    }

    @Override
    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 16; i++)
            list.add(new ItemStack(this,1,i));
    }

    @Override
    public boolean isShearable(ItemStack item, World world, int x, int y, int z) 
    {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) 
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
    }

}
