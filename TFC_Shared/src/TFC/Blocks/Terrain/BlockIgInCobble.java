package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Blocks.BlockTerra;
import TFC.Core.Helper;
import TFC.Core.TFC_Sounds;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIgInCobble extends BlockTerra
{
	public BlockIgInCobble(int i, Material material) {
		super(i, material);
	}


	@SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 3; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public Icon getIcon(int i, int j) 
	{
		return icons[j];
	}

	protected Icon[] icons = new Icon[3];
	protected String[] names = {"Granite", "Diorite", "Gabbro"};

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		for(int i = 0; i < 3; i++)
		{
			icons[i] = iconRegisterer.registerIcon("rocks/"+names[i]+" Cobble");
		}
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{

	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	public static boolean canFallBelow(World world, int i, int j, int k)
    {
        int l = world.getBlockId(i, j, k);
        if (l == 0)
        {
            return true;
        }
        if (l == Block.fire.blockID)
        {
            return true;
        }
        Material material = Block.blocksList[l].blockMaterial;
        if (material == Material.water)
        {
            return true;
        }
        return material == Material.lava;
    }
	
	private void tryToFall(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if (!BlockCollapsable.isNearSupport(world, i, j, k) && BlockCollapsable.canFallBelow(world, i, j - 1, k) && j >= 0)
		{
			EntityFallingSand ent = new EntityFallingSand(world, i + 0.5F, j + 0.5F, k + 0.5F, blockID, meta);
			Random R = new Random(i*j+k);
			
			byte byte0 = 32;
			if (!world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
			{
				world.setBlock(i, j, k, 0);
				for (; canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
				if (j > 0)
				{
					world.setBlock(i, j, k, blockID);
				}
			}
			else if (!world.isRemote)
			{
                world.spawnEntityInWorld(ent);
                world.playSoundAtEntity(ent, TFC_Sounds.FALLININGROCKSHORT, 1.0F, 0.8F + (R.nextFloat()/2));
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		tryToFall(world, i, j, k);
	}
	
	/**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
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

            return ItemChisel.handleActivation(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
        }
        return false;
    }
}
