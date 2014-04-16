package TFC.Items;

import java.util.Iterator;
import java.util.List;

import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.ISize;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemLeash;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemCustomLeash extends ItemLeash implements ISize
{
	public String textureFolder;
    public ItemCustomLeash(int par1)
    {
        super(par1);
		textureFolder = "";
    }
    @Override
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i1 = par3World.getBlockId(par4, par5, par6);
        if (TFCBlocks.isIdAFence(i1))
        {
            if (par3World.isRemote)
            {
                return true;
            }
            else
            {
                this.func_135066_a(par2EntityPlayer, par3World, par4, par5, par6);
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    
    
    public static boolean func_135066_a(EntityPlayer par0EntityPlayer, World par1World, int par2, int par3, int par4)
    {
        EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForBlock(par1World, par2, par3, par4);
        boolean flag = false;
        double d0 = 7.0D;
        List list = par1World.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB((double)par2 - d0, (double)par3 - d0, (double)par4 - d0, (double)par2 + d0, (double)par3 + d0, (double)par4 + d0));

        if (list != null)
        {
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityLiving entityliving = (EntityLiving)iterator.next();

                if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == par0EntityPlayer)
                {
                    if (entityleashknot == null)
                    {
                        entityleashknot = EntityLeashKnot.func_110129_a(par1World, par2, par3, par4);
                    }

                    entityliving.setLeashedToEntity(entityleashknot, true);
                    flag = true;
                }
            }
        }

        return flag;
    }
    
    @Override
	public Icon getIconFromDamage(int meta)
	{        
		return this.itemIcon;
	}
    
    @Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(is, arraylist);

		ItemTerra.addHeatInformation(is, arraylist);
	}
	
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
    }

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}
	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.MEDIUM;
	}
	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.FAR;
	}
	@Override
	public boolean canStack() {
		return true;
	}
	
}
