package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.API.TFC_ItemHeat;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Util.StringUtil;

public class ItemMeltedMetal extends ItemTerra
{

	public ItemMeltedMetal(int i) 
	{
		super(i);
		setMaxDamage(101);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.setFolder("ingots/");
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.SMALL);
	}	

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}


	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{

		super.addInformation(is, player, arraylist, flag);

	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{		
		if(is.getItemDamage() > 1) {
			arraylist.add(StringUtil.localize("gui.MeltedMetal.NotFull"));
		}
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is,world,entity,i,isSelected);
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();
			if(TFC_ItemHeat.HasTemp(is) && TFC_ItemHeat.GetTemp(is) >= TFC_ItemHeat.IsCookable(is))
			{
				if(is.getItemDamage()==0){
					is.setItemDamage(1);
				}
			}
			else if(is.getItemDamage()==1){
				is.setItemDamage(0);
			}

		}
		else if(is.getItemDamage()==1){
			is.setItemDamage(0);
		}
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getItemDamage() > 1;
	}
	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{	
		if (TFC_Core.showExtraInformation())
		{
			arraylist.add(StringUtil.localize("gui.Help"));
			arraylist.add(StringUtil.localize("gui.MeltedMetal.Inst0"));
		}
		else
		{
			arraylist.add(StringUtil.localize("gui.ShowHelp"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize <= 0) {
			itemstack.stackSize = 1;
		}

		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = itemstack.copy();

		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
		entityplayer.openGui(TerraFirmaCraft.instance, 38, world, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ);

		return itemstack;
	}
}
