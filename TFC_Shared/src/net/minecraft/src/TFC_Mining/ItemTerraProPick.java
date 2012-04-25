package net.minecraft.src.TFC_Mining;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.TFC_Core.General.Vector3f;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraProPick extends Item implements ITextureProvider
{
	List vecArray;

	public ItemTerraProPick(int i) 
	{
		super(i);
		maxStackSize = 1;
		vecArray = new ArrayList<Vector3f>();
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}


	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		if(objectMouseOver == null) {
			return itemstack;
		}		
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;
		int side = objectMouseOver.sideHit;
		if(vecArray.size() >= 100)
		{
			vecArray.remove(0);
		}

		boolean b = false;
		if(vecArray.size() > 0)
		{
			for(int i = 0; i < vecArray.size() && b == false; i++)
			{
				Vector3f vec = (Vector3f)vecArray.toArray()[i];
				if(vec.X == x && vec.Y == y && vec.Z == z)
				{
					b = true;
				}
			}
		}


		//sides XN(0), XP(1), YN(2), YP(3), ZN(4), ZP(5);
		ArrayList oreArray = new ArrayList<String>();
		ArrayList oreNumArray = new ArrayList<Integer>();
		for (int i = -12; i < 12; i++)
		{
			for (int j = -12; j < 12; j++)
			{
				for (int k = -6; k < 6; k++)
				{
					int oreid = world.getBlockId(x+i, y+k, z+j);

					ItemStack is;
					if(oreid == mod_TFC_Core.terraOre.blockID)
					{
						int meta = world.getBlockMetadata(x+i, y+k, z+j);
						if(meta < 7) {
							is = new ItemStack(mod_TFC_Core.terraOre,1,meta+32);
						} else {
							is = new ItemStack(mod_TFC_Core.terraOre,1,meta+16);
						}
						if(!oreArray.contains(is.getItem().getItemNameIS(is)))
						{
							oreArray.add(is.getItem().getItemNameIS(is));
							oreNumArray.add(1);
						}
						else
						{
							int index = oreArray.indexOf(is.getItem().getItemNameIS(is));
							oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
						}


					}
					else if(oreid == mod_TFC_Core.terraOre2.blockID)
					{
						int meta = world.getBlockMetadata(x+i, y+k, z+j);
						if(meta < 7) {
							is = new ItemStack(mod_TFC_Core.terraOre2,1,meta+48);
						} else {
							is = new ItemStack(mod_TFC_Core.terraOre2,1,meta+32);
						}
						if(!oreArray.contains(is.getItem().getItemNameIS(is)))
						{
							oreArray.add(is.getItem().getItemNameIS(is));
							oreNumArray.add(1);
						}
						else
						{
							int index = oreArray.indexOf(is.getItem().getItemNameIS(is));
							oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
						}
					}
					else if(oreid == mod_TFC_Core.terraOre3.blockID)
					{
						int meta = world.getBlockMetadata(x+i, y+k, z+j);
						if(meta < 7) {
							is = new ItemStack(mod_TFC_Core.terraOre3,1,meta+64);
						} else {
							is = new ItemStack(mod_TFC_Core.terraOre3,1,meta+48);
						}
						if(!oreArray.contains(is.getItem().getItemNameIS(is)))
						{
							oreArray.add(is.getItem().getItemNameIS(is));
							oreNumArray.add(1);
						}
						else
						{
							int index = oreArray.indexOf(is.getItem().getItemNameIS(is));
							oreNumArray.set(index, (Integer)oreNumArray.toArray()[index]+1);
						}
					}
				}
			}
		}
		Random random = new Random();
		if(b == true)
		{
			entityplayer.addChatMessage("You've already prospected here.");
		}
		else if(oreArray.toArray().length > 0 && b == false && random.nextInt(100) < 75)
		{
			int rand = random.nextInt(oreArray.toArray().length);
			if((Integer)oreNumArray.toArray()[rand] < 5) {
				entityplayer.addChatMessage("Found Traces of " + oreArray.toArray()[rand]);
			} else if((Integer)oreNumArray.toArray()[rand] < 10) {
				entityplayer.addChatMessage("Found a small sample of " + oreArray.toArray()[rand]);
			} else if((Integer)oreNumArray.toArray()[rand] < 20) {
				entityplayer.addChatMessage("Found a medium sample of " + oreArray.toArray()[rand]);
			} else if((Integer)oreNumArray.toArray()[rand] < 40) {
				entityplayer.addChatMessage("Found a large sample of " + oreArray.toArray()[rand]);
			} else {
				entityplayer.addChatMessage("Found a very large sample of " + oreArray.toArray()[rand]);
			}
		} else {
			entityplayer.addChatMessage("Found nothing of interest.");
		}

		itemstack.setItemDamage(itemstack.getItemDamage()+1);

		vecArray.add(new Vector3f(x,y,z));
		if(itemstack.getItemDamage() == itemstack.getMaxDamage()) {
			return new ItemStack(this,0);
		}

		return itemstack;
	}
}
