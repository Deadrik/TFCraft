package TFC.Items;

import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.src.*;

public class ItemChisel extends ItemTool
{
    public static int mode = 0;
    public ItemChisel(int i, EnumToolMaterial e)
    {
        super(i, 0, e, new Block[] {});
        this.setMaxDamage(e.getMaxUses()/2);
        this.maxStackSize = 1;
    }

    public String getTextureFile() {
        return "/bioxx/terratools.png";
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
    {
        par1ItemStack.damageItem(1, par6EntityLiving);
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) 
    {
        if(!player.worldObj.isRemote)      
        {
            MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, player.worldObj);
            if(objectMouseOver == null) {
                return false;
            }		

            int id = player.worldObj.getBlockId(x, y, z);
            int meta = player.worldObj.getBlockMetadata(x, y, z);

            itemstack.setItemDamage(itemstack.getItemDamage()+1);
            if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
                itemstack.stackSize = 0;

            return true;
        }
        return false;
    }

    public static void CreateStairs(World world, int x, int y, int z, int id, byte meta, byte m)
    {
        if(!world.isRemote)
        {
            world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStairs.blockID, m);
            TileEntityPartial te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
            te.TypeID = (short) id;
            te.MetaID = (byte) meta;
            te.extraData = 0;
            te.setMaterial(world.getBlockMaterial(x, y, z));
            te.validate();
            world.markBlockNeedsUpdate(x, y, z);

        }
    }
    public static void CreateSlab(World world, int x, int y, int z, int id, byte meta, int side, int SlabID)
    {
        TileEntityPartial te;
        if(!world.isRemote)
        {

            if(world.getBlockId(x, y, z) != SlabID)
            {
                world.setBlockAndMetadataWithNotify(x, y, z, SlabID, side);

                te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
                te.TypeID = (short) id;
                te.MetaID = (byte) meta;
                te.setMaterial(world.getBlockMaterial(x, y, z));
            }
            else
            {
                te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
                world.notifyBlockChange(x, y, z, SlabID);
            }

            if(TFC_Settings.enableDebugMode)
                System.out.println(side);

            long extraX = (te.extraData) & 0xf;
            long extraY = (te.extraData >> 4) & 0xf;
            long extraZ = (te.extraData >> 8) & 0xf;
            long extraX2 = (te.extraData >> 12) & 0xf;
            long extraY2 = (te.extraData >> 16) & 0xf;
            long extraZ2 = (te.extraData >> 20) & 0xf;

            if(side == 0)
            {
                long e = extraY + 1; 
                long new1 = (extraY << 4);
                long new2 = (e << 4);
                long old2 = new2 | (te.extraData - new1);

                if(e + extraY2 >= 10)
                    world.setBlockWithNotify(x, y, z, 0);
                else
                    te.extraData =  old2;
            }
            else if(side == 1)
            {
                long e = extraY2 + 1; 
                long new1 = (extraY2 << 16);
                long new2 = (e << 16);
                long old2 = new2 | (te.extraData - new1);

                if(e + extraY >= 10)
                    world.setBlockWithNotify(x, y, z, 0);
                else
                    te.extraData =  old2;
            }
            else if(side == 2)
            {
                long e = extraZ + 1; 
                long new1 = (extraZ << 8);
                long new2 = (e << 8);
                long old2 = new2 | (te.extraData - new1);

                if(e + extraZ2 >= 10)
                    world.setBlockWithNotify(x, y, z, 0);
                else
                    te.extraData =  old2;
            }
            else if(side == 3)
            {
                long e = extraZ2 + 1; 
                long new1 = (extraZ2 << 20);
                long new2 = (e << 20);
                long old2 = new2 | (te.extraData - new1);

                if(e + extraZ >= 10)
                    world.setBlockWithNotify(x, y, z, 0);
                else
                    te.extraData =  old2;
            }
            else if(side == 4)
            {
                long e = extraX + 1; 
                long new1 = (extraX);
                long new2 = (e);
                long old2 = new2 | (te.extraData - new1);

                if(e + extraX2 >= 10)
                    world.setBlockWithNotify(x, y, z, 0);
                else
                    te.extraData =  old2;
            }
            else if(side == 5)
            {
                long e = extraX2 + 1; 
                long new1 = (extraX2 << 12);
                long new2 = (e << 12);
                long old2 = new2 | (te.extraData - new1);

                if(e + extraX >= 10)
                    world.setBlockWithNotify(x, y, z, 0);
                else
                    te.extraData =  old2;
            }
            
            if(TFC_Settings.enableDebugMode)
            {
                System.out.println("Extra ="+te.extraData);  
            }
            
            
        }
        
        te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
        if(te != null)
        	te.broadcastPacketInRange(te.createUpdatePacket());
        
        world.markBlockNeedsUpdate(x, y, z);
    }
    public static void CreateSlab(World world, int x, int y, int z, int id, byte meta, int side)
    {
        CreateSlab(world,x,y,z,id,meta,side, TFCBlocks.stoneSlabs.blockID);
    }
}