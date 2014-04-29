package com.bioxx.tfc.Items.Tools;

import java.util.List;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.ICausesDamage;
import com.bioxx.tfc.api.ISize;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.google.common.collect.Multimap;

public class ItemTerraTool extends ItemTool implements ISize
{
	private static boolean registeredGlobalTex = false;

	public ItemTerraTool(float par2, ToolMaterial par3, Set par4)
	{
		super(par2, par3, par4);
		this.setCreativeTab(TFCTabs.TFCTools);
		setNoRepair();
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;

		ItemTerra.addSizeInformation(is, arraylist);
		ItemTerra.addHeatInformation(is, arraylist);
		

		if(is.getItem() instanceof ICausesDamage)
			arraylist.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal(((ICausesDamage)this).GetDamageType().toString()));

		addItemInformation(is, player, arraylist);
		addExtraInformation(is, player, arraylist);

		if(TFCOptions.enableDebugMode)
		{
			NBTTagCompound nbt = is.getTagCompound();
			if(nbt != null && nbt.hasKey("craftingTag") && nbt.getCompoundTag("craftingTag").hasKey("durabuff"))
				arraylist.add("durabuff=" + is.getMaxDamage()+ "/" + is.getItem().getMaxDamage());
		}
	}

	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
		if (TFC_Textures.BrokenItem == null) TFC_Textures.BrokenItem = registerer.registerIcon(Reference.ModID + ":" + "tools/Broken Item");
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		return StatCollector.translateToLocal(getUnlocalizedName(is).replace("item.", ""));
	}
	
	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
//		Multimap multimap = HashMultimap.create();
//		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
//		return multimap;
		return super.getItemAttributeModifiers();
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt != null)
		{
			float buff = 0;
			if(nbt.hasKey("craftingTag") && nbt.getCompoundTag("craftingTag").hasKey("durabuff"))
				buff = nbt.getCompoundTag("craftingTag").getFloat("durabuff");
			return (int) (getMaxDamage()+(getMaxDamage() * (buff / 100f)));
		}
		else return super.getMaxDamage(stack);
	}

	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(pass == 1 && nbt != null && nbt.hasKey("broken"))
			return TFC_Textures.BrokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}
}
