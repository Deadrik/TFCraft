package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import TFC.Reference;
import TFC.API.Armor;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
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
		if(canStack()) {
			return this.getSize().stackSize * getWeight().multiplier;
		} else {
			return 1;
		}
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
		ItemTerra.addHeatInformation(is, arraylist);

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
			arraylist.add(EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.Armor.Advanced") + ": (" + StringUtil.localize("gui.Armor.Hold") + " " + 
					EnumChatFormatting.GRAY + StringUtil.localize("gui.Armor.Shift") + 
					EnumChatFormatting.DARK_GRAY + ")");
		}

	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("armorDuraBuff"))
			{
				return super.getMaxDamage(stack) + nbt.getShort("armorDuraBuff");
			}
		}
		return super.getMaxDamage(stack);
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

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return Reference.ModID+String.format(":textures/models/armor/%s_%d%s.png",
				ArmorType.metaltype, (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}
}

