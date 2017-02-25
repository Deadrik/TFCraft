package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Tools.IToolChisel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockMetal extends BlockTerra
{
    protected String[] metalNames;
    protected IIcon[] icons;

    public BlockMetal(Material material)
    {
        super(Material.iron);
        setCreativeTab(TFCTabs.TFC_MATERIALS);
        metalNames = new String[Global.METAL_ALL.length];
        System.arraycopy(Global.METAL_ALL, 0, metalNames, 0, Global.METAL_ALL.length);
        icons = new IIcon[metalNames.length];
    }

    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
    {
        for(int i = 0; i < metalNames.length; i++)
            list.add(new ItemStack(this,1,i));
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return icons[meta];
    }

    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        for(int i = 0; i < metalNames.length; i++)
            icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/"+metalNames[i]);
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float par7, float par8, float par9)
    {
        boolean hasHammer = false;
        for(int i = 0; i < 9;i++)
            if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
                hasHammer = true;
        if(!world.isRemote && entityplayer.getCurrentEquippedItem() != null &&
                entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel &&
                hasHammer && ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z))
        {
            Block block = world.getBlock(x, y, z);
            byte meta = (byte) world.getBlockMetadata(x, y, z);

            return ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, block, meta, side, par7, par8, par9);
        }
        return false;
    }
    
    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {

    	return true;
    	
    }

}