package TFC.Items.Pottery;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Core.TFC_ItemHeat;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.Items.ISize;
import TFC.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPotteryBase extends ItemTerra implements ISize
{
	public Icon ClayIcon;
	public Icon CeramicIcon;
	
    public ItemPotteryBase(int id) 
    {
        super(id);
        this.hasSubtypes = true;
        this.setFolder("pottery/");
    }
    
    @Override
	public void updateIcons(IconRegister registerer)
    {
		this.ClayIcon = registerer.registerIcon(textureFolder + MetaNames[0]);
		this.CeramicIcon = registerer.registerIcon(textureFolder + MetaNames[1]);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int damage)
    {
    	if(damage == 0)
    		return this.ClayIcon;
    	else return this.CeramicIcon;    		
    }

    @Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (!world.isRemote && is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
            }
        }
    }
    	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

}
