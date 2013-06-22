package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import TFC.Reference;
import TFC.API.Armor;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Util.StringUtil;

public class ItemTFCArmor extends ItemArmor implements ISize
{
	public Armor ArmorType;
	public ItemTFCArmor(int itemID, Armor armor, int renderIndex, int armorSlot)
	{
		super(itemID,EnumArmorMaterial.IRON,renderIndex,armorSlot);
		ArmorType = armor;
		this.setCreativeTab(TFCTabs.TFCArmor);
		this.setMaxDamage(ArmorType.getDurability(armorSlot));
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize().stackSize * getWeight().multiplier;
		else
			return 1;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "armor/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);

		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = -1;
				float boilTemp = 10000;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
				{
					meltTemp = hi.meltTemp;
				}

				if(meltTemp != -1)
				{
					if(is.itemID == Item.stick.itemID)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
			}
		}

		if (TFC_Core.showExtraInformation()) 
		{
			arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.Armor.Advanced") + ":");
			arraylist.add(EnumChatFormatting.ITALIC + StringUtil.localize("gui.Armor.Pierce") + ": " + EnumChatFormatting.AQUA + ArmorType.getPiercingAR());
			arraylist.add(EnumChatFormatting.ITALIC + StringUtil.localize("gui.Armor.Slash") + ": " + EnumChatFormatting.AQUA + ArmorType.getSlashingAR());
			arraylist.add(EnumChatFormatting.ITALIC + StringUtil.localize("gui.Armor.Crush") + ": " + EnumChatFormatting.AQUA + ArmorType.getCrushingAR());
			arraylist.add("");
			if (is.hasTagCompound())
			{
				NBTTagCompound stackTagCompound = is.getTagCompound();

				if(stackTagCompound.hasKey("creator"))
				{
					arraylist.add(EnumChatFormatting.ITALIC + StringUtil.localize("gui.Armor.ForgedBy") + " " + stackTagCompound.getString("creator"));
				}
			}
		}
		else
		{
			arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.Armor.Advanced") + ": (" + StringUtil.localize("gui.Armor.Hold") + " " + EnumChatFormatting.AQUA + StringUtil.localize("gui.Armor.Shift") + EnumChatFormatting.WHITE + ")");
		}

	}

	@Override
	public int getItemMaxDamageFromStack(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("armorDuraBuff"))
			{
				return super.getItemMaxDamageFromStack(stack) + nbt.getShort("armorDuraBuff");
			}
		}
		return super.getItemMaxDamageFromStack(stack);
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}
	
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}
}

