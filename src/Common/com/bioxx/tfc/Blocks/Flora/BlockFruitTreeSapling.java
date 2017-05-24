package com.bioxx.tfc.Blocks.Flora;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TEFruitTreeSapling;
import com.bioxx.tfc.TileEntities.TEFruitTreeWood;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockFruitTreeSapling extends BlockTerraContainer
{
    protected IIcon[] icons;
    protected String[] fruitNames;

    public BlockFruitTreeSapling()
    {
        super(Material.plants);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.fruitNames = Global.FRUIT_META_NAMES;
        this.setTickRandomly(true);
        this.setCreativeTab(TFCTabs.TFC_FOODS);
        this.icons = new IIcon[fruitNames.length];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list)
    {
        for(int i = 0; i < fruitNames.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @Override
    public IIcon getIcon(int i, int j)
    {
        return icons[j];
    }

    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        for(int i = 0; i < fruitNames.length; i++)
            this.icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + this.fruitNames[i] + " Sapling");

    }

    public void growTree(World world, int i, int j, int k, Random rand, long timestamp)
    {
        int meta = world.getBlockMetadata(i, j, k);
        world.setBlockToAir(i, j, k);
        world.setBlock(i, j, k, TFCBlocks.fruitTreeWood, meta, 0x2);

        ((TEFruitTreeWood)world.getTileEntity(i, j, k)).setTrunk(true);
        ((TEFruitTreeWood)world.getTileEntity(i, j, k)).setHeight(0);
        ((TEFruitTreeWood)world.getTileEntity(i, j, k)).initBirth();
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block b)
    {
        Block block = world.getBlock(i, j, k);
        if(!TFC_Core.isGrass(block) && !TFC_Core.isDirt(block) && !this.canBlockStay(world, i, j, k))
        {
            int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, meta));
            world.setBlockToAir(i, j, k);
        }
    }


    // Set the sapling growth timer the moment it is planted, instead of the first random tick it gets after being planted.
    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {

        if (world.getTileEntity(i, j, k) instanceof TEFruitTreeSapling)
        {
            TEFruitTreeSapling te = (TEFruitTreeSapling) world.getTileEntity(i, j, k);

            // Set the growTime tick timestamp to be 7-11.2 days times config multiplier from now, plus up to an extra day.
            if (te != null && te.growTime == 0)
                te.growTime = (long) (TFC_Time.getTotalTicks() + (TFC_Time.DAY_LENGTH * 1) + (world.rand.nextFloat() * TFC_Time.DAY_LENGTH));
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random rand)
    {
        if (!world.isRemote)
        {
            super.updateTick(world, i, j, k, rand);

            if (world.getTileEntity(i, j, k) instanceof TEFruitTreeSapling)
            {
                long timestamp = ((TEFruitTreeSapling) world.getTileEntity(i, j, k)).growTime;

                if (world.getBlockLightValue(i, j + 1, k) >= 9 && TFC_Time.getTotalTicks() > timestamp)
                {
                    growTree(world, i, j, k, rand, timestamp);
                }
            }
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && this.canThisPlantGrowOnThisBlockID(world.getBlock(x, y - 1, z));
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return (world.isAirBlock(x, y, z) || world.getBlock(x, y, z).getMaterial().isReplaceable()) && this.canThisPlantGrowOnThisBlockID(world.getBlock(x, y - 1, z));
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess bAccess, int x, int y, int z)
    {
        return false;
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean canThisPlantGrowOnThisBlockID(Block b)
    {
        return TFC_Core.isSoil(b);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 1;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TEFruitTreeSapling();
    }

    protected void checkChange(World world, int x, int y, int z)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }
}
