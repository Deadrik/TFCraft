package TFC.Blocks;

import java.util.Random;

import TFC.Core.Helper;
import TFC.Core.PlayerInfo;
import TFC.Core.PlayerManagerTFC;
import TFC.Core.TFC_Core;
import TFC.Items.ItemChisel;
import TFC.Items.ItemHammer;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraSedSmooth extends Block implements ITextureProvider
{

	public BlockTerraSedSmooth(int i, int tex) 
	{
		super(i, Material.rock);
		this.blockIndexInTexture = tex;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < 10; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return blockIndexInTexture+j;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terraRock.png";
	}
	
	/**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
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
        if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer)
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
            if(!TFC_Core.isClient())
            {
                PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
                
                if(pi!=null) mode = pi.ChiselMode;
            }
            else
                mode = ItemChisel.mode;
            
            if(mode == 1)
            {
                ItemChisel.CreateStairs(world, x, y, z, id, meta, m);
                return true;
            }
            else if(mode == 2)
            {
                ItemChisel.CreateSlab(world, x, y, z, id, meta, side);
                return true;
            }
        }
        return false;
    }
}

