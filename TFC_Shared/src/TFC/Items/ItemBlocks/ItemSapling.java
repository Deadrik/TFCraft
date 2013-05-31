package TFC.Items.ItemBlocks;

import TFC.*;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumWeight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class ItemSapling extends ItemTerraBlock
{
	public ItemSapling(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	@Override
	public Icon getIconFromDamage(int par1)
    {
        return Icons[par1];
    }
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		if(MetaNames != null)
			return new StringBuilder().append(Global.WOOD_ALL[itemstack.getItemDamage()]+" Sapling").toString();
		return super.getItemDisplayName(itemstack);
	}
	public static Icon[] Icons = new Icon[Global.WOOD_ALL.length];

	@Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			Icons[i] = registerer.registerIcon("wood/trees/" + Global.WOOD_ALL[i] + " Sapling");
		}
    }
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}
