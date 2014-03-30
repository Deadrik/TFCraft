package TFC.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.API.ICausesDamage;
import TFC.API.ISize;
import TFC.API.TFCOptions;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemTerra;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemCustomAxe extends ItemAxe implements ISize, ICausesDamage
{
	private float toolDamage;
	
	public ItemCustomAxe(ToolMaterial e, float damage)
	{
		super(e);
		this.setMaxDamage(e.getMaxUses());
		this.toolDamage = damage;
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/"+this.getUnlocalizedName().replace("item.", ""));
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

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if(TFCOptions.enableDebugMode)
			arraylist.add("Damage: " + is.getItemDamage());
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
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
	public EnumDamageType GetDamageType()
	{
		return EnumDamageType.SLASHING;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.toolDamage, 0));
		return multimap;
	}
}
