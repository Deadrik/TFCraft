package TFC.Items.Pottery;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPotteryMold extends ItemPotteryBase 
{
	Icon CopperIcon;
	Icon BronzeIcon;
	Icon BismuthBronzeIcon;
	Icon BlackBronzeIcon;
	public ItemPotteryMold(int id) 
	{
		super(id);
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.ClayIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[0]);
		this.CeramicIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[1]);
		if(MetaNames.length > 2)
		{
			CopperIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[2]);
			BronzeIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[3]);
			BismuthBronzeIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[4]);
			BlackBronzeIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + MetaNames[5]);
		}
	}
	
	@Override
	public Icon getIconFromDamage(int damage)
	{
		if(damage == 0) return this.ClayIcon;
		else if(damage == 1) return this.CeramicIcon; 
		else if(damage == 2) return this.CopperIcon; 
		else if(damage == 3) return this.BronzeIcon; 
		else if(damage == 4) return this.BismuthBronzeIcon; 
		else if(damage == 5) return this.BlackBronzeIcon; 
		
		return this.ClayIcon; 
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}
}
