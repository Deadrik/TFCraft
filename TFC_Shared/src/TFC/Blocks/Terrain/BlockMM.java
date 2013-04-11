package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Core.TFC_Core;
import TFC.Items.ItemChisel;
import TFC.Items.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMM extends BlockCollapsable
{
    public static boolean fallInstantly = false;

    public BlockMM(int i, Material material,int id) {
        super(i, material, id);
        this.dropBlock = TFCBlocks.StoneMMCobble.blockID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 6; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

    /*
     * Mapping from metadata value to damage value
     */
    @Override
    public int damageDropped(int i) 
    {
        return i;
    }

    @Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return icons[j];
	}

    public static Icon[] icons = new Icon[6];
	protected String[] names = {"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < 6; i++)
		{
			icons[i] = iconRegisterer.registerIcon("rocks/"+names[i]+" Raw");
		}
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {	
        Random R = new Random();
        if(R.nextBoolean())
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.LooseRock, 1+R.nextInt(4), l+17));

        super.harvestBlock(world, entityplayer, i, j, k, l);
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.LooseRock.itemID;
    }

    @Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
    {
        if(!world.isRemote)
        {
            //super.onBlockDestroyedByExplosion(world, i, j, k, ex);
            Random random = new Random();

            ItemStack is = null;

            is = TFC_Core.RandomGem(random, 0);

            if(is != null)
            {
                EntityItem item = new EntityItem(world, i, j, k, is);
                world.spawnEntityInWorld(item);
            }
        }
    }
    @Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
    {
        if(!world.isRemote)
        {
            Random random = new Random();
            ItemStack is = null;

            is = TFC_Core.RandomGem(random,3);

            if(is != null)
            {
                EntityItem item = new EntityItem(world, i, j, k, is);
                world.spawnEntityInWorld(item);
            }

        }
    }
    
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
    {
        int metadata = world.getBlockMetadata(i, j, k);

        //Minecraft mc = ModLoader.getMinecraftInstance();

        //mc.ingameGUI.addChatMessage("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());  
    }
    
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        DropCarvedStone(world, i, j, k);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float par7, float par8, float par9) 
    {
        boolean hasHammer = false;
        for(int i = 0; i < 9;i++)
        {
            if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
                hasHammer = true;
        }
        if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && !world.isRemote)
        {
            int id = world.getBlockId(x, y, z);
            byte meta = (byte) world.getBlockMetadata(x, y, z);

            return ItemChisel.handleActivation(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
        }
        return false;
    }

}

