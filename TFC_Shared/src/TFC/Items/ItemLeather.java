package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.Tools.ItemCustomKnife;

public class ItemLeather extends ItemLooseRock
{
	public ItemLeather(int id) 
	{
		super(id);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}


	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = new ItemStack(specialCraftingType, 1, itemstack.getItemDamage());
		
		boolean hasKnife = false;
        for(int i = 0; i < entityplayer.inventory.mainInventory.length; i++)
        {
            if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemCustomKnife)
                hasKnife = true;
        }
        
		if(hasKnife)
		{
			itemstack.stackSize--;
			entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		return itemstack;

	}


	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}
	
	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return this.itemIcon;
	}
	
	
	@Override
	public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1,0));
	}
}
