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
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.Armor;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFCArmor extends ItemArmor implements ISize
{
	private static final String[] leatherNames = new String[] {"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
	public Armor ArmorType;
	public Icon overlayIcon;

	public ItemTFCArmor(int itemID, Armor armor, int renderIndex, int armorSlot)
	{
		super(itemID,EnumArmorMaterial.IRON,renderIndex,armorSlot);
		ArmorType = armor;
		this.setCreativeTab(TFCTabs.TFCArmor);
		this.setMaxDamage(ArmorType.getDurability(armorSlot));
	}

	public ItemTFCArmor(int itemID, Armor armor, int renderIndex, int armorSlot, EnumArmorMaterial m)
	{
		super(itemID, m, renderIndex, armorSlot);
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
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		if (this.getArmorMaterial() == EnumArmorMaterial.CLOTH)
		{
			this.itemIcon = registerer.registerIcon("minecraft:" + getIconString());
			overlayIcon = registerer.registerIcon("minecraft:" + leatherNames[this.armorType]);
		}
		else
		{
			this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "armor/"+this.getUnlocalizedName().replace("item.", ""));
		}
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
		if (this.getArmorMaterial() == EnumArmorMaterial.CLOTH)
		{
			return EnumWeight.LIGHT;
		}
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
		String m = ArmorType.metaltype.replace(" ", "").toLowerCase();
		return Reference.ModID+String.format(":textures/models/armor/%s_%d%s.png",
				m, (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}
}

