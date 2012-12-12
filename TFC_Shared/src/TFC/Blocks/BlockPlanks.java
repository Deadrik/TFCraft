package TFC.Blocks;

import java.util.List;

import TFC.TFCBlocks;
import TFC.Core.Helper;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemChisel;
import TFC.Items.ItemHammer;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

public class BlockPlanks extends BlockTerra
{
	public BlockPlanks(int i, Material material) 
	{
		super(i, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return j+176;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		super.harvestBlock(world, entityplayer, i, j, k, l);
	}
	
	/**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) 
    {
        boolean hasHammer = false;
        for(int i = 0; i < 9;i++)
        {
            if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
                hasHammer = true;
        }
        if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && !world.isRemote)
        {
            MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
            if(objectMouseOver == null) {
                return false;
            }       
            int side = objectMouseOver.sideHit;

            int id = world.getBlockId(x, y, z);
            byte meta = (byte) world.getBlockMetadata(x, y, z);

            byte newMeta = 0;
            if (side == 0)
            {
                newMeta = (byte) (newMeta | 4);
            }

            int rot = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte flip = (byte) (newMeta & 4);
            byte m = 0;

            if (rot == 0)
            {
                m = (byte) ( 2 | flip);
            }
            else if (rot == 1)
            {
                m = (byte) ( 1 | flip);
            }
            else if (rot == 2)
            {
                m = (byte) ( 3 | flip);
            }
            else if (rot == 3)
            {
                m = (byte) ( 0 | flip);
            }
            
            int mode = 0;
            if(!world.isRemote)
            {
                PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
                
                if(pi!=null) mode = pi.ChiselMode;
            }
            else
                mode = PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode;
            
            if(mode == 1)
            {
//            	if(side == 0 && world.getBlockId(x, y+1, z) == blockID)
//        		return false;
        	
                ItemChisel.CreateStairs(world, x, y, z, id, meta, m);
                return true;
            }
            else if(mode == 2)
            {
                ItemChisel.CreateSlab(world, x, y, z, id, meta, side);
                return true;
            }
            else if(mode == 3)
            {
                ItemChisel.CreateDetailed(world, x, y, z, id, meta, side, par7, par8, par9);
                return true;
            }
        }
        return false;
    }

}
