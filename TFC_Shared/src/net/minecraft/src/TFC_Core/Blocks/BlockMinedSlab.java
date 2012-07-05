package net.minecraft.src.TFC_Core.Blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.TFC_Core;
import net.minecraft.src.TFC_Core.TileEntityPartial;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.TFC_Core.General.PlayerInfo;
import net.minecraft.src.TFC_Core.General.PlayerManagerTFC;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.TFC_Core.Items.ItemChisel;
import net.minecraft.src.TFC_Core.Items.ItemHammer;

public class BlockMinedSlab extends BlockPartial
{

    public BlockMinedSlab(int par1)
    {
        super(par1, Material.rock);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRenderType()
    {
        return mod_TFC_Core.slabRenderId;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityplayer) 
    {
        boolean hasHammer = false;
        for(int i = 0; i < 9;i++)
        {
            if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
                hasHammer = true;
        }
        if(entityplayer.getCurrentEquippedItem().getItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer)
        {
            MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
            if(objectMouseOver == null) {
                return false;
            }       
            int side = objectMouseOver.sideHit;

            int id = world.getBlockId(x, y, z);
            byte meta = (byte) world.getBlockMetadata(x, y, z);

            int mode = 0;
            if(!TFC_Core.isClient())
            {
                PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);

                if(pi!=null) mode = pi.ChiselMode;
            }
            else
                mode = ItemChisel.mode;

            if(mode == 2)
            {
                ItemChisel.CreateSlab(world, x, y, z, id, meta, side, this.blockID);
                return true;
            }
        }
        return false;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
        if(entityplayer != null)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            entityplayer.addExhaustion(0.075F);
        }
    }

    public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k) 
    {
        if(player != null)
        {
            player.addStat(StatList.mineBlockStatArray[blockID], 1);
            player.addExhaustion(0.075F);
        }

        MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, world);
        if(objectMouseOver == null) {
            return false;
        }       
        int side = objectMouseOver.sideHit;
        int sub = objectMouseOver.subHit;

        TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(i,j,k);

        if(te != null)
        {
            int id = te.TypeID;
            int meta = te.MetaID;
            ItemChisel.CreateSlab(world, i, j, k, te.TypeID, te.MetaID, side, this.blockID);
            te = (TileEntityPartial) world.getBlockTileEntity(i,j,k);
            ItemChisel.CreateSlab(world, i, j, k, te.TypeID, te.MetaID, side, this.blockID);
            te = (TileEntityPartial) world.getBlockTileEntity(i, j, k);
            
            if(te != null)
            {
                long extraX = (te.extraData) & 0xf;
                long extraY = (te.extraData >> 4) & 0xf;
                long extraZ = (te.extraData >> 8) & 0xf;
                long extraX2 = (te.extraData >> 12) & 0xf;
                long extraY2 = (te.extraData >> 16) & 0xf;
                long extraZ2 = (te.extraData >> 20) & 0xf;
                
                if(extraX+extraX2 + extraY+extraY2 + extraZ + extraZ2 <= 10)
                    Block.blocksList[id].harvestBlock(world, player, i, j, k, meta);

                if(extraX+extraX2 > 9 || extraY+extraY2 > 9 || extraZ + extraZ2 > 9)
                    return world.setBlockWithNotify(i, j, k, 0);
            }
            else
                return world.setBlockWithNotify(i, j, k, 0);
        }

        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(i, j, k);
        int md = world.getBlockMetadata(i, j, k);

        short type = te.TypeID;
        if (type <= 0)
            return super.getCollisionBoundingBoxFromPool(world, i, j, k);
        int meta = te.MetaID;
        int tex = Block.blocksList[type].getBlockTextureFromSideAndMetadata(0, meta);
        long extraX = (te.extraData) & 0xf;
        long extraY = (te.extraData >> 4) & 0xf;
        long extraZ = (te.extraData >> 8) & 0xf;
        long extraX2 = (te.extraData >> 12) & 0xf;
        long extraY2 = (te.extraData >> 16) & 0xf;
        long extraZ2 = (te.extraData >> 20) & 0xf;



        return AxisAlignedBB.getBoundingBoxFromPool(i + (0.1F * extraX), j + (0.1F * extraY),  k + (0.1F * extraZ), i + (1 - (0.1F * extraX2)), j + (1 - (0.1F * extraY2)), k + (1 - (0.1F * extraZ2)));
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return getCollisionBoundingBoxFromPool(world,i,j,k);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
    {
        TileEntityPartial te = (TileEntityPartial) par1IBlockAccess.getBlockTileEntity(i, j, k);
        int md = par1IBlockAccess.getBlockMetadata(i, j, k);

        long extraX = (te.extraData) & 0xf;
        long extraY = (te.extraData >> 4) & 0xf;
        long extraZ = (te.extraData >> 8) & 0xf;
        long extraX2 = (te.extraData >> 12) & 0xf;
        long extraY2 = (te.extraData >> 16) & 0xf;
        long extraZ2 = (te.extraData >> 20) & 0xf;

        setBlockBounds(0.0F+ (0.1F * extraX), 0.0F+ (0.1F * extraY), 0.0F+ (0.1F * extraZ), 1.0F-(0.1F * extraX2), 1-(0.1F * extraY2), 1.0F-(0.1F * extraZ2));
    }
}
