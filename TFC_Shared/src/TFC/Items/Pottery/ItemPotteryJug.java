package TFC.Items.Pottery;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;

public class ItemPotteryJug extends ItemPotteryBase
{
	Icon WaterIcon;
	
    public ItemPotteryJug(int id) 
    {
        super(id);
        this.MetaNames = new String[]{"Clay Jug", "Ceramic Jug", "Water Jug"};
    }
    
    @Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote)
        {
            if(par1ItemStack.getItemDamage() == 2)
            	TFC_Core.getPlayerFoodStats(par3EntityPlayer).restoreWater(par3EntityPlayer, 8000);
            
            if (par1ItemStack.getItemDamage() > 1)
            {
            	if(par2World.rand.nextInt(25) == 0)
            		par1ItemStack = null;
            	else
            	{
            		par1ItemStack.setItemDamage(1);
            	}
            }
        }

        return par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
    {
    	if(is.getItemDamage() > 1)
    		entity.setItemInUse(is, this.getMaxItemUseDuration(is));
        return is;
    }
    
    @Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float HitX, float HitY, float HitZ) 
	{
		int id = world.getBlockId(x, y+1, z);
		if(!world.isRemote && (id == Block.waterStill.blockID || id == Block.waterMoving.blockID))
		{
			if(itemstack.getItemDamage() == 1)
			{
				itemstack.setItemDamage(2);
				return true;
			}
		}
		return super.onItemUse(itemstack, entityplayer, world, x, y, z, side, HitX, HitY, HitZ);
	}

	@Override
	public Icon getIconFromDamage(int damage)
	{
		if(damage == 0) return this.ClayIcon;
		else if(damage == 1) return this.CeramicIcon; 
		else if(damage == 2) return this.WaterIcon; 
		
		return this.WaterIcon; 
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		super.registerIcons(registerer);
		this.WaterIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + "Water Jug");
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
    	super.addInformation(is, player, arraylist, flag);
    	if(is.hasTagCompound() && is.stackTagCompound.hasKey("LiquidType"))
    	{
    		arraylist.add(is.stackTagCompound.getString("LiquidType"));
    	}
    }
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.LIGHT;
	}

}
