package net.minecraft.src.TFC_Core.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.TileEntityTerraSluice;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraGoldPan extends Item implements ITextureProvider
{
	public static String[] blockNames = {"GoldPan", "GoldPanSand", "GoldPanGravel", "GoldPanClay", "GoldPanDirt"};
	public int usesLeft;

	public ItemTerraGoldPan(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setItemName("GoldPan");
		maxStackSize = 1;
		usesLeft = 25;
	}

	public int getIconFromDamage(int i)
	{
		int j = 1;
		if(i == 1) {
			return 2;
		}
		if(i == 2) {
			return 3;
		}
		if(i == 3) {
			return 4;
		}
		if(i == 4) {
			return 5;
		}

		return j;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
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


		//Do this if it's an empty pan
		if(itemstack.getItemDamage() == 0)
		{
			boolean isBiomeRiver = false;
			for (int i = -25; i < 25; i++)
			{
				for (int j = -25; j < 25; j++)
				{
					if(world.getWorldChunkManager().getBiomeGenAt((x+i)/16*16, (z+j)/16*16).biomeName.contains("River"))
					{					
						isBiomeRiver = true;
					}
				}
			}
			List L = new ArrayList<Integer>();
			for (int i = -10; i < 10; i++)
			{
				for (int j = -10; j < 10; j++)
				{
					int k = world.getWorldChunkManager().getBiomeGenAt((x+i*6)/16*16, (z+j*6)/16*16).biomeID;
					if(!L.contains(k)) {
						L.add(k);
					}
				}
			}
			Random R = new Random();
			if(world.getBlockId(x, y, z) == Block.sand.blockID && isBiomeRiver)
			{
				if(R.nextInt(100) < 10) {
					world.setBlockWithNotify(x, y, z, 0);
				}
				return new ItemStack(this,1,1);
			}
			else if(world.getBlockId(x, y, z) == Block.gravel.blockID && isBiomeRiver)
			{
				if(R.nextInt(100) < 10) {
					world.setBlockWithNotify(x, y, z, 0);
				}
				return new ItemStack(this,1,2);
			}
			else if((world.getBlockId(x, y, z) == mod_TFC_Core.terraClay.blockID || world.getBlockId(x, y, z) == mod_TFC_Core.terraClay2.blockID ||
					world.getBlockId(x, y, z) == mod_TFC_Core.terraClayGrass.blockID || world.getBlockId(x, y, z) == mod_TFC_Core.terraClayGrass2.blockID) && isBiomeRiver)
			{
				if(R.nextInt(100) < 10) {
					world.setBlockWithNotify(x, y, z, 0);
				}
				return new ItemStack(this,1,3);
			}
			else if((world.getBlockId(x, y, z) == mod_TFC_Core.terraDirt.blockID || world.getBlockId(x, y, z) == mod_TFC_Core.terraDirt2.blockID ||
					world.getBlockId(x, y, z) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(x, y, z) == mod_TFC_Core.terraGrass2.blockID) && isBiomeRiver)
			{
				if(R.nextInt(100) < 10) {
					world.setBlockWithNotify(x, y, z, 0);
				}
				return new ItemStack(this,1,4);
			}
			entityplayer.addChatMessage("Must use on soil in or near a river.");
		}
		else
		{
			if(world.getBlockId(x, y, z) == mod_TFC_Core.terraSluice.blockID)
			{
				((TileEntityTerraSluice)world.getBlockTileEntity(x, y, z)).soilAmount += 5;
				return new ItemStack(this,1,0);
			}
			else
			{
				int blockAboveId = world.getBlockId(x, y+1, z);
				int blockAboveMeta = world.getBlockMetadata(x, y+1, z);
				System.out.println(new StringBuilder().append(blockAboveId).append(" ").append(blockAboveMeta).toString());
				if(blockAboveId == Block.waterStill.blockID && blockAboveMeta > 0)
				{
					usesLeft--;
					System.out.println(new StringBuilder().append("True").toString());

					Random random = new Random();

					if(random.nextInt(200) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.terraGemAgate,1,0));
						items.add(new ItemStack(TFCItems.terraGemAmethyst,1,0));
						items.add(new ItemStack(TFCItems.terraGemBeryl,1,0));
						items.add(new ItemStack(TFCItems.terraGemEmerald,1,0));
						items.add(new ItemStack(TFCItems.terraGemGarnet,1,0));
						items.add(new ItemStack(TFCItems.terraGemJade,1,0));
						items.add(new ItemStack(TFCItems.terraGemJasper,1,0));
						items.add(new ItemStack(TFCItems.terraGemOpal,1,0));
						items.add(new ItemStack(TFCItems.terraGemRuby,1,0));
						items.add(new ItemStack(TFCItems.terraGemSapphire,1,0));
						items.add(new ItemStack(TFCItems.terraGemTourmaline,1,0));
						items.add(new ItemStack(TFCItems.terraGemTopaz,1,0));
						items.add(new ItemStack(Item.goldNugget,1,0));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

					}
					else if(random.nextInt(350) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.terraGemAgate,1,1));
						items.add(new ItemStack(TFCItems.terraGemAmethyst,1,1));
						items.add(new ItemStack(TFCItems.terraGemBeryl,1,1));
						items.add(new ItemStack(TFCItems.terraGemEmerald,1,1));
						items.add(new ItemStack(TFCItems.terraGemGarnet,1,1));
						items.add(new ItemStack(TFCItems.terraGemJade,1,1));
						items.add(new ItemStack(TFCItems.terraGemJasper,1,1));
						items.add(new ItemStack(TFCItems.terraGemOpal,1,1));
						items.add(new ItemStack(TFCItems.terraGemRuby,1,1));
						items.add(new ItemStack(TFCItems.terraGemSapphire,1,1));
						items.add(new ItemStack(TFCItems.terraGemTourmaline,1,1));
						items.add(new ItemStack(TFCItems.terraGemTopaz,1,1));
						items.add(new ItemStack(TFCItems.terraGemDiamond,1,0));
						items.add(new ItemStack(Item.goldNugget,2,0));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt(600) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,2));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,2));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,2));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,2));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,2));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,2));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,2));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,2));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,2));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,2));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,2));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,2));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,1));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt(850) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,3));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,3));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,3));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,3));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,3));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,3));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,3));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,3));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,3));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,3));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,3));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,3));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,2));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt(1250) == 0)
                    {
                        ArrayList items = new ArrayList<ItemStack>();
                        items.add(new ItemStack(TFCItems.terraGemAgate,1,4));
                        items.add(new ItemStack(TFCItems.terraGemAmethyst,1,4));
                        items.add(new ItemStack(TFCItems.terraGemBeryl,1,4));
                        items.add(new ItemStack(TFCItems.terraGemEmerald,1,4));
                        items.add(new ItemStack(TFCItems.terraGemGarnet,1,4));
                        items.add(new ItemStack(TFCItems.terraGemJade,1,4));
                        items.add(new ItemStack(TFCItems.terraGemJasper,1,4));
                        items.add(new ItemStack(TFCItems.terraGemOpal,1,4));
                        items.add(new ItemStack(TFCItems.terraGemRuby,1,4));
                        items.add(new ItemStack(TFCItems.terraGemSapphire,1,4));
                        items.add(new ItemStack(TFCItems.terraGemTourmaline,1,4));
                        items.add(new ItemStack(TFCItems.terraGemTopaz,1,4));
                        items.add(new ItemStack(TFCItems.terraGemDiamond,1,3));

                        entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

                    }
                    else if(random.nextInt(2000) == 0)
                    {
                        entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.terraGemDiamond,1,2));
                    }

					if(usesLeft == 0)
					{
						usesLeft = 15 + random.nextInt(10);
						this.setIconCoord(1, 0);
						return new ItemStack(this,1,0);
					}

					return itemstack;
				}
			}
		}
		return itemstack;
	}
}
