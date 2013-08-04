package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.AlloyMetal;
import TFC.Core.Metal.MetalRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TECrucible extends NetworkTileEntity 
{
	public HashMap metals = new HashMap();
	public Alloy currentAlloy;
	public float temperature = 0;;
	public TECrucible()
	{

	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist = new NBTTagList();
		Iterator iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("ID", m.type.IngotID);
				nbttagcompound1.setShort("Amount", m.amount);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Metals", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		NBTTagList nbttaglist = nbttagcompound.getTagList("Metals");

		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int id = nbttagcompound1.getInteger("ID");
			short amount = nbttagcompound1.getShort("Amount");

			Metal m = MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]);
			metals.put(m.Name, new MetalPair(m, amount));

			addMetal(MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]), amount);
		}
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			/*Create a list of all the items that are falling into the stack */
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					xCoord+0.0625, yCoord, zCoord+0.0625, xCoord+0.9375, yCoord+1, zCoord+0.9375));

			if (list != null && !list.isEmpty())
			{
				/*Iterate through the list and check for charcoal, coke, and ore*/
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem)iterator.next();
					if(entity.getEntityItem().getItem() instanceof ISmeltable)
					{
						ISmeltable obj = (ISmeltable)entity.getEntityItem().getItem();
						for(int c = 0; c < entity.getEntityItem().stackSize; c++)
						{
							
							if(addMetal(obj.GetMetalType(entity.getEntityItem()), obj.GetMetalReturnAmount(entity.getEntityItem())))
							{
								entity.getEntityItem().stackSize--;
							}
							else break;
						}

						if(entity.getEntityItem().stackSize == 0) 
						{
							entity.setDead();
						}
					}
				}
			}
		}
	}

	private boolean addMetal(Metal m, short amt)
	{
		if(getTotalMetal()+amt <= 3000)
		{
			if(metals.containsKey(m.Name))
				((MetalPair)metals.get(m.Name)).amount += amt;
			else
				metals.put(m.Name, new MetalPair(m, amt));

			updateCurrentAlloy();

			return true;
		}
		return false;
	}

	public int getTotalMetal()
	{
		Iterator iter = metals.values().iterator();
		int totalAmount = 0;
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				totalAmount += m.amount;
			}
		}
		return totalAmount;
	}

	private void updateCurrentAlloy()
	{
		List<AlloyMetal> a = new ArrayList<AlloyMetal>();

		Iterator iter = metals.values().iterator();
		int totalAmount = getTotalMetal();

		iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				a.add(new AlloyMetal(m.type, ((float)m.amount/(float)totalAmount) * 100f));
			}
		}

		Metal match = AlloyManager.instance.matchesAlloy(a, Alloy.EnumTier.TierV);
		if(match != null)
		{
			currentAlloy = new Alloy(match, totalAmount); 
			currentAlloy.AlloyIngred = a;
		}
		else 
		{
			currentAlloy = new Alloy(null, totalAmount);
			currentAlloy.AlloyIngred = a;
		}
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}

	private class MetalPair
	{
		public Metal type;
		public short amount;
		public MetalPair(Metal t, short amnt)
		{
			type = t;
			amount = amnt;
		}
	}
}
