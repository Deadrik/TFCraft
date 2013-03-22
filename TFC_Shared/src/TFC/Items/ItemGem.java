package TFC.Items;

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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemGem extends ItemTerra
{
	public static String[] gemNames = {"Chipped", "Flawed", "Normal", "Flawless", "Exquisite"};
	private Icon[] icons = new Icon[5];
	public ItemGem(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,0, 0));
		list.add(new ItemStack(this,0, 1));
		list.add(new ItemStack(this,0, 2));
		list.add(new ItemStack(this,0, 3));
		list.add(new ItemStack(this,0, 4));
	}

	public Icon getIconFromDamage(int i)
	{
		return icons[i];
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(gemNames[itemstack.getItemDamage()]).toString();
		return s;
	}
	
	public void registerIcon(IconRegister registerer)
    {
		icons[0] = registerer.func_94245_a("gems/"+gemNames[0] + " " + getUnlocalizedName().replace("item.", ""));
		icons[1] = registerer.func_94245_a("gems/"+gemNames[1] + " " + getUnlocalizedName().replace("item.", ""));
		icons[2] = registerer.func_94245_a("gems/"+gemNames[2] + " " + getUnlocalizedName().replace("item.", ""));
		icons[3] = registerer.func_94245_a("gems/"+gemNames[3] + " " + getUnlocalizedName().replace("item.", ""));
		icons[4] = registerer.func_94245_a("gems/"+gemNames[4] + " " + getUnlocalizedName().replace("item.", ""));
    }

}
