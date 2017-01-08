package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.TECoalPile;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.UP;

public class BlockCoalPile extends BlockTerraContainer
{
    private IIcon[] icons = new IIcon[3];

    public BlockCoalPile()
    {
        super(Material.ground);
        this.setTickRandomly(true);
    }

    public static int getDirectionFromMetadata(int i)
    {
        return i & 3;
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
    {
        if (world.getTileEntity(x,y,z) instanceof TECoalPile && side == UP)
        {
            if (((TECoalPile) world.getTileEntity(x, y, z)).isOnFire)
                return true;
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            if((TECoalPile)world.getTileEntity(i, j, k)!=null)
            {

                ItemStack is = entityplayer.getCurrentEquippedItem();

                if(is != null && is.getItem() == TFCItems.coal && is.getItemDamage() == 0)
                {
                    return false;
                }
                else
                {
                    entityplayer.openGui(TerraFirmaCraft.instance, 50, world, i, j, k);
                }
                return true;
            } else {
                return false;
            }

        }

    }

    @Override
    public IIcon getIcon(int i, int j)
    {
        if(j == 0 || j == 2)//+z
        {
            if(i == 0) {
                return icons[1];
            } else if(i == 1) {
                return icons[1];
            } else if(i == 2) {
                return icons[2];
            } else if(i == 3) {
                return icons[2];
            } else if(i == 4) {
                return icons[0];
            } else if(i == 5) {
                return icons[0];
            }
        }
        else if(j == 1 || j == 3)//-x
        {
            if(i == 0) {
                return icons[0];
            } else if(i == 1) {
                return icons[0];
            } else if(i == 2) {
                return icons[0];
            } else if(i == 3) {
                return icons[0];
            } else if(i == 4) {
                return icons[2];
            } else if(i == 5) {
                return icons[2];
            }
        }

        return icons[0];

    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegisterer)
    {
        icons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Coal Pile Side 0");
        icons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Coal Pile Side 1");
        icons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Coal Pile End");
    }

    public void eject(World world, int x, int y, int z)
    {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TECoalPile)
        {
            TECoalPile te = (TECoalPile) world.getTileEntity(x, y, z);
            te.ejectContents();
            world.removeTileEntity(x, y, z);
        }
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par3)
    {
        return Item.getItemById(0);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
        eject(world, i, j, k);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
    {
        eject(world, x, y, z);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i)
    {
        eject(world, x, y, z);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        eject(world, x, y, z);
        return world.setBlockToAir(x, y, z); // super.removedByPlayer is deprecated, and causes a loop.
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TECoalPile();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TECoalPile)
        {
            ((TECoalPile) world.getTileEntity(x, y, z)).lightNeighbors();
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getTileEntity(x, y, z) instanceof TECoalPile)
        {
            TECoalPile te = (TECoalPile) world.getTileEntity(x, y, z);

            if (te.isOnFire && te.fireTimer + TFCOptions.charcoalPitBurnTime < TFC_Time.getTotalHours())
            {
                te.createCoke(x, y, z, true);
            }

        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getTileEntity(x, y, z) instanceof TECoalPile && ((TECoalPile) world.getTileEntity(x, y, z)).isOnFire)
        {
            double centerX = x + 0.5F;
            double centerY = y + 2F;
            double centerZ = z + 0.5F;
            //double d3 = 0.2199999988079071D;
            //double d4 = 0.27000001072883606D;
            world.spawnParticle("smoke", centerX+(rand.nextDouble()-0.5), centerY, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.1D, 0.0D);
            world.spawnParticle("smoke", centerX+(rand.nextDouble()-0.5), centerY, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.15D, 0.0D);
            world.spawnParticle("smoke", centerX+(rand.nextDouble()-0.5), centerY-1, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.1D, 0.0D);
            world.spawnParticle("smoke", centerX+(rand.nextDouble()-0.5), centerY-1, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.15D, 0.0D);
        }
    }
}
