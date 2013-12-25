package TFC.Items.Tools;

import java.util.List;

import TFC.API.TFCOptions;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Items.ItemTerra;
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
public class ItemMiscToolHead extends ItemTerra
{
	public ItemMiscToolHead(int i) 
	{
		super(i);
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		setCreativeTab(TFCTabs.TFCUnfinished);
		this.setFolder("toolheads/");
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.LARGE;
	}
	
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}
}
