package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import TFC.Reference;
import TFC.API.Armor;
import TFC.API.IClothing;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFCArmor extends ItemArmor implements ISize, IClothing
{
	private static final String[] leatherNames = new String[] {"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
	public Armor ArmorType;
	public IIcon overlayIcon;
	private int thermal = 0;
	private int type = 0;
	private int trueType = 0;

	public ItemTFCArmor(Armor armor, int renderIndex, int armorSlot, int thermal, int type)
	{
		super(ArmorMaterial.IRON,renderIndex,armorSlot%4);
		ArmorType = armor;
		this.trueType = armorSlot;
		this.setCreativeTab(TFCTabs.TFCArmor);
		this.setMaxDamage(ArmorType.getDurability(armorSlot));
	}

	public ItemTFCArmor(Armor armor, int renderIndex, int armorSlot, ArmorMaterial m, int thermal, int type)
	{
		super(m, renderIndex, armorSlot%4);
		ArmorType = armor;
		this.trueType = armorSlot;
		this.setCreativeTab(TFCTabs.TFCArmor);
		this.setMaxDamage(ArmorType.getDurability(armorSlot));
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
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if (this.getArmorMaterial() == ArmorMaterial.CLOTH)
		{
			this.itemIcon = registerer.registerIcon("minecraft:" + getIconString());
			overlayIcon = registerer.registerIcon("minecraft:" + leatherNames[this.armorType]);
		}
		else
			this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "armor/"+this.getUnlocalizedName().replace("item.", ""));
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
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		ItemTerra.addHeatInformation(is, arraylist);

		if (TFC_Core.showExtraInformation()) 
		{
			arraylist.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.Armor.Advanced") + ":");
			arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.Pierce") + ": " + EnumChatFormatting.AQUA + ArmorType.getPiercingAR());
			arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.Slash") + ": " + EnumChatFormatting.AQUA + ArmorType.getSlashingAR());
			arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.Crush") + ": " + EnumChatFormatting.AQUA + ArmorType.getCrushingAR());
			arraylist.add("");
			if (is.hasTagCompound())
			{
				NBTTagCompound stackTagCompound = is.getTagCompound();

				if(stackTagCompound.hasKey("creator"))
					arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.ForgedBy") + " " + stackTagCompound.getString("creator"));
			}
		}
		else
			arraylist.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("gui.Armor.Advanced") + ": (" + StatCollector.translateToLocal("gui.Armor.Hold") + " " + 
					EnumChatFormatting.GRAY + StatCollector.translateToLocal("gui.Armor.Shift") + 
					EnumChatFormatting.DARK_GRAY + ")");

	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("armorDuraBuff"))
				return super.getMaxDamage(stack) + nbt.getShort("armorDuraBuff");
		}
		return super.getMaxDamage(stack);
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		if (this.getArmorMaterial() == ArmorMaterial.CLOTH)
			return EnumWeight.LIGHT;
		return EnumWeight.HEAVY;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		String m = ArmorType.metaltype.replace(" ", "").toLowerCase();
		return Reference.ModID+String.format(":textures/models/armor/%s_%d%s.png",
				m, (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}

	@Override
	public int getThermal()
	{
		return thermal;
	}

	//ItemArmor can't handle armor types >3, so this allows you to record the "true" armor type, whereas the value vanilla gets is %4
	public int getUnadjustedArmorType()
	{
		return trueType;
	}

	@Override
	public int getBodyPart()
	{
		return type;
	}
}

