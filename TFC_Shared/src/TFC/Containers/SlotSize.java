package TFC.Containers;

import java.util.ArrayList;
import java.util.List;

import TFC.*;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
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

public class SlotSize extends Slot
{
	EnumSize size = EnumSize.MEDIUM;
	
	List excpetions;
	
	public SlotSize(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		excpetions = new ArrayList<Item>();
	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		boolean except = excpetions.contains(itemstack.getItem());
		
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize && !except)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize) && !except)
			return true;
		
		return false;
	}
	
	public SlotSize setSize(EnumSize s)
	{
		size = s;
		return this;
	}
	
	public SlotSize addItemException(ArrayList<Item> ex)
	{
		excpetions = ex;
		return this;
	}
}
