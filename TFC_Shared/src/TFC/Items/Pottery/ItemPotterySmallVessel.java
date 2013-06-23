package TFC.Items.Pottery;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import TFC.API.IBag;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;

public class ItemPotterySmallVessel extends ItemPotteryBase implements IBag
{

    public ItemPotterySmallVessel(int id) 
    {
        super(id);
        this.MetaNames = new String[]{"Clay Vessel", "Ceramic Vessel"};
    }
    
    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.openGui(TerraFirmaCraft.instance, 39, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		return itemstack;
	}
    	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}
	
	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
    {
		if (TFC_Core.showExtraInformation()) 
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.Help") + ":");
			arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryBase.Inst0") + " " + 
					EnumChatFormatting.AQUA + StringUtil.localize("gui.RightClick") + " " + 
					EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryBase.Inst1"));
			arraylist.add( 
					EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryVesselSmall.Inst0") + " " + 
					EnumChatFormatting.AQUA + StringUtil.localize("gui.RightClick") + " " + 
					EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryVesselSmall.Inst1"));
		}
		else
		{
			arraylist.add(
					EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.Help") + ": (" + StringUtil.localize("gui.Armor.Hold") + " " + 
							EnumChatFormatting.GRAY + StringUtil.localize("gui.Armor.Shift") + 
							EnumChatFormatting.DARK_GRAY + ")");
		}
    }

}
