package TFC.Items.Pottery;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.IBag;
import TFC.API.IMadeOfMetal;
import TFC.API.Enums.EnumMetalType;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.Alloy.Alloy;
import TFC.Core.Alloy.AlloyManager;
import TFC.Core.Alloy.AlloyMetal;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemOre;
import TFC.Items.ItemOreSmall;

public class ItemPotterySmallVessel extends ItemPotteryBase implements IBag
{

    public ItemPotterySmallVessel(int id) 
    {
        super(id);
        this.MetaNames = new String[]{"Clay Vessel", "Ceramic Vessel"};
    }
    
    @Override
    public void onDoneCooking(World world, ItemStack is)
	{
    	ItemStack[] bag = loadBagInventory(is);
    	boolean canCookAlloy = true;
    	for(int i = 0; i < 4; i++)
    	{
    		if(bag[i] != null && !(bag[i].getItem() instanceof ItemOreSmall) && !(bag[i].getItem() instanceof ItemOre))
    		{
    			canCookAlloy = false;
    		}
    	}
    	
    	if(canCookAlloy)
    	{
    		HeatIndex heatIndex0 = HeatRegistry.getInstance().findMatchingIndex(bag[0]);
    		HeatIndex heatIndex1 = HeatRegistry.getInstance().findMatchingIndex(bag[1]);
    		HeatIndex heatIndex2 = HeatRegistry.getInstance().findMatchingIndex(bag[2]);
    		HeatIndex heatIndex3 = HeatRegistry.getInstance().findMatchingIndex(bag[3]);
    		ItemStack is0 = heatIndex0 != null ? heatIndex0.getOutput(world.rand) : null;
    		ItemStack is1 = heatIndex1 != null ? heatIndex1.getOutput(world.rand) : null;
    		ItemStack is2 = heatIndex2 != null ? heatIndex2.getOutput(world.rand) : null;
    		ItemStack is3 = heatIndex3 != null ? heatIndex3.getOutput(world.rand) : null;
    		
    		EnumMetalType[] types = new EnumMetalType[4];
    		int[] metalAmounts = new int[4];
			
    		if(is0 != null)
    		{
    			types[0] = ((IMadeOfMetal)is0.getItem()).GetMetalType(is0);
    			metalAmounts[0] = ((IMadeOfMetal)is0.getItem()).GetMetalReturnAmount(is0);
    		}
    		
    		if(is1 != null)
    		{
    			types[1] = ((IMadeOfMetal)is1.getItem()).GetMetalType(is1);
    			metalAmounts[1] = ((IMadeOfMetal)is1.getItem()).GetMetalReturnAmount(is1);
    			
    			if(mergeMetals(types[0], types[1], metalAmounts[0], metalAmounts[1]))
    				types[1] = null;
    		}
    		
    		if(is2 != null)
    		{
    			types[2] = ((IMadeOfMetal)is2.getItem()).GetMetalType(is2);
    			metalAmounts[2] = ((IMadeOfMetal)is2.getItem()).GetMetalReturnAmount(is2);
    			
    			if(mergeMetals(types[0], types[2], metalAmounts[0], metalAmounts[2]))
    				types[2] = null;
    			if(mergeMetals(types[1], types[2], metalAmounts[1], metalAmounts[2]))
    				types[2] = null;
    		}
    		if(is3 != null)
    		{
    			types[3] = ((IMadeOfMetal)is3.getItem()).GetMetalType(is3);
    			metalAmounts[3] = ((IMadeOfMetal)is3.getItem()).GetMetalReturnAmount(is3);
    			
    			if(mergeMetals(types[0], types[3], metalAmounts[0], metalAmounts[3]))
    				types[3] = null;
    			if(mergeMetals(types[1], types[3], metalAmounts[1], metalAmounts[3]))
    				types[3] = null;
    			if(mergeMetals(types[2], types[3], metalAmounts[2], metalAmounts[3]))
    				types[3] = null;
    		}
    		int total = metalAmounts[0] + metalAmounts[1] + metalAmounts[2] + metalAmounts[3];
    		
    		int[] metalPercent = new int[4];
    		metalPercent[0] = (metalAmounts[0] / total) * 100;
    		metalPercent[1] = (metalAmounts[1] / total) * 100;
    		metalPercent[2] = (metalAmounts[2] / total) * 100;
    		metalPercent[3] = (metalAmounts[3] / total) * 100;
    		
    		List<AlloyMetal> a = new ArrayList<AlloyMetal>();
    		if(types[0] != null) a.add(new AlloyMetal(types[0], metalPercent[0]));
    		if(types[1] != null) a.add(new AlloyMetal(types[1], metalPercent[1]));
    		if(types[2] != null) a.add(new AlloyMetal(types[2], metalPercent[2]));
    		if(types[3] != null) a.add(new AlloyMetal(types[3], metalPercent[3]));
    		
    		Alloy output = new Alloy(AlloyManager.instance.matchesAlloy(a), total); 
    		NBTTagCompound tag = is.stackTagCompound;
    		tag.setInteger("MetalType", output.outputType.MetalID);
    		tag.setInteger("MetalAmount", output.outputAmount);
    	}
	}
    
    private boolean mergeMetals(EnumMetalType mt0, EnumMetalType mt1, float m0, float m1)
    {
    	if(mt0 != null && mt1 != null && m0 > 0)
    	{
    		if(mt0.MetalID == mt1.MetalID)
    		{
    			m0 += m1;
    			return true;
    		}
    	}
    	return false;
    }
    
    public ItemStack[] loadBagInventory(ItemStack is)
	{
    	ItemStack[] bag = new ItemStack[4];
    	
		if(is != null && is.hasTagCompound())
		{
			NBTTagList nbttaglist = is.getTagCompound().getTagList("Items");

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 4)
				{
					bag[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
		
		return bag;
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
