package TFC.Items;

import java.util.BitSet;

import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.src.*;

public class ItemChisel extends ItemTerraTool
{
    public static int mode = 0;
    public static int depth = 1;
    public ItemChisel(int i, EnumToolMaterial e)
    {
        super(i, 0, e, new Block[] {});
        this.setMaxDamage(e.getMaxUses()/2);
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
	public EnumSize getSize() {
		return EnumSize.VERYSMALL;
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

            world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStairs.blockID, m);
            TileEntityPartial te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
            te.TypeID = (short) id;
            te.MetaID = (byte) meta;
            te.extraData = 0;
            te.setMaterial(world.getBlockMaterial(x, y, z));
            te.validate();
            world.markBlockNeedsUpdate(x, y, z);

    }
    public static void CreateSlab(World world, int x, int y, int z, int id, byte meta, int side, int SlabID)
    {
        TileEntityPartial te;
        if(true)
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

                if(e + BlockSlab.getTopChiselLevel(te.extraData) >= 10)
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

                if(e + BlockSlab.getBottomChiselLevel(te.extraData) >= 10)
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

                if(e + BlockSlab.getPosZChiselLevel(te.extraData) >= 10)
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

                if(e + BlockSlab.getNegZChiselLevel(te.extraData) >= 10)
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

                if(e + BlockSlab.getPosXChiselLevel(te.extraData) >= 10)
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

                if(e + BlockSlab.getNegXChiselLevel(te.extraData) >= 10)
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
    
    public static void CreateDetailed(World world, int x, int y, int z, int id, byte meta, int side, float hitX, float hitY, float hitZ)
    {
    	TileEntityDetailed te;
    	
    	if(id == TFCBlocks.stoneSlabs.blockID)
        {
    		TileEntityPartial tep = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
    		int extraX = (int) ((tep.extraData) & 0xf);
    		int extraY = (int) ((tep.extraData >> 4) & 0xf);
            int extraZ = (int) ((tep.extraData >> 8) & 0xf);
            int extraX2 = 10 - (int) ((tep.extraData >> 12) & 0xf);
            int extraY2 = 10 - (int) ((tep.extraData >> 16) & 0xf);
            int extraZ2 = 10 - (int) ((tep.extraData >> 20) & 0xf);
            world.setBlock(x, y, z, TFCBlocks.StoneDetailed.blockID);
            te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
            te.TypeID = tep.TypeID;
            te.MetaID = tep.MetaID;
            te.setMaterial(tep.getMaterial());
            
            for(int subX = 0; subX < 10; subX++)
            {
            	for(int subZ = 0; subZ < 10; subZ++)
                {
            		for(int subY = 0; subY < 10; subY++)
                    {
            			if(subX >= extraX && subX < extraX2 && subY >= extraY && subY < extraY2 && subZ >= extraZ && subZ < extraZ2)
            				te.data.set((subX * 10 + subZ)*10 + subY);
                    }
                }
            }
            return;
        }
    	else if(TFC_Core.isRawStone(id) || TFC_Core.isSmoothStone(id))
        {
            world.setBlockWithNotify(x, y, z, TFCBlocks.StoneDetailed.blockID);

            te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
            te.TypeID = (short) id;
            te.MetaID = (byte) meta;
            te.setMaterial(world.getBlockMaterial(x, y, z));
            
            for(int subX = 0; subX < 10; subX++)
            {
            	for(int subZ = 0; subZ < 10; subZ++)
                {
            		for(int subY = 0; subY < 10; subY++)
                    {
            			te.data.set((subX * 10 + subZ)*10 + subY);
                    }
                }
            }
        }
    	else if(id == TFCBlocks.StoneDetailed.blockID)
    	{
    		te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
    		    		
    		int subX = (int) ((hitX)*10);
    		int subY = (int) ((hitY)*10);
    		int subZ = (int) ((hitZ)*10);
    		if(subX > 9)
    			subX = 9;
    		if(subY > 9)
    			subY = 9;
    		if(subZ > 9)
    			subZ = 9;
    		
    		if(side == 2)
    			subX -= depth;
    		else if(side == 3)
    			subX += depth;
    		
    		if(side == 1)
    			subY -= depth;
    		else if(side == 0)
    			subY += depth;
    		
    		if(side == 4)
    			subZ -= depth;
    		else if(side == 5)
    			subZ += depth;
    		
    		int index = (subX * 10 + subZ)*10 + subY;
    		if(te.data.get(index))
    		{
    			te.data.clear(index);
    			te.broadcastPacketInRange(te.createUpdatePacket());
    		}
    	}
        else
        {
            te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
            world.notifyBlockChange(x, y, z, TFCBlocks.StoneDetailed.blockID);
        }
    }
}