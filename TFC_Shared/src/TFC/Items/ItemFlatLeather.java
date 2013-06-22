package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;

public class ItemFlatLeather extends ItemTerra
{
	public Icon[] icons;
    public ItemFlatLeather(int id) 
    {
        super(id);
        this.hasSubtypes = false;
        this.setMaxDamage(0);
        this.maxStackSize = 25;
    }
    
    public ItemFlatLeather(int id, String tex) 
    {
        super(id);
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
        //if(par1ItemStack.stackSize == 0)
        ((EntityPlayer)par3Entity).inventory.setInventorySlotContents(par4, null);
    }
    
    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return false;
    }
    
    @Override
	public void registerIcons(IconRegister registerer)
    {
    	if(this.MetaNames == null)
    		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
    	else
    	{
    		icons = new Icon[this.MetaNames.length];
    		for(int i = 0; i < this.MetaNames.length; i++)
    		{
    			this.icons[i] = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + MetaNames[i]);
    		}
    	}
    }
    
    @Override
	public Icon getIconFromDamage(int damage)
	{
    	if(this.MetaNames == null)
    		return this.itemIcon; 
    	else
    		return icons[damage];
	}
}
