package TFC.Items.Tools;

import java.util.List;

import TFC.*;
import TFC.API.TFCOptions;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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

public class ItemCustomScythe extends ItemTerraTool
{
    static Block[] blocks = {Block.blocksList[18]};

	public ItemCustomScythe(int i, EnumToolMaterial e)
	{
		super(i, e.getDamageVsEntity(),e, blocks);
		this.setMaxDamage(e.getMaxUses()*3);
		this.damageVsEntity = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

}