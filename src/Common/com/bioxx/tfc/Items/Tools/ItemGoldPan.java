package com.bioxx.tfc.Items.Tools;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemGoldPan extends ItemTerra
{
	public static String[] MetaNames = {"", "Sand", "Gravel", "Clay", "Dirt"};
	public IIcon[] icons = new IIcon[MetaNames.length];

	//private int useTimer = 0;
	public ItemGoldPan() 
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		i = i & 15;
		if(i == 1)
			return icons[1];
		if(i == 2)
			return icons[2];
		if(i == 3)
			return icons[3];
		if(i == 4)
			return icons[4];
		return icons[0];
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_0");
		icons[1] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_1");
		icons[2] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_2");
		icons[3] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_3");
		icons[4] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_4");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(world.isRemote)
			return is;

		//float distMod = 1.0F;
		MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, true);

		if (mop == null)
		{
			return is;
		}
		else
		{
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			Block blockHit = world.getBlock(x, y, z);
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				if(is.getItemDamage() == 0)
				{
					ChunkData cd = TFC_Core.getCDM(world).getData(x >> 4, z >> 4);

					// Make sure our chunk data isn't null.
					if(cd == null)
					{
						player.addChatMessage(new ChatComponentText("The ChunkData returned null, please report this to the developer."));
						return is;
					}

					if(cd.sluicedAmount < 50)
					{
						if (blockHit == TFCBlocks.Gravel || blockHit == TFCBlocks.Gravel2)
						{
							is.setItemDamage((5 << 4) + 2);
							if(world.rand.nextInt(10) == 0)
								world.setBlockToAir(x, y, z);
							TFC_Core.addPlayerExhaustion(player, 0.0005f);
							cd.sluicedAmount++;
						}
						else if (blockHit == TFCBlocks.Sand || blockHit == TFCBlocks.Sand2)
						{
							is.setItemDamage((5 << 4) + 1);
							if(world.rand.nextInt(10) == 0)
								world.setBlockToAir(x, y, z);
							TFC_Core.addPlayerExhaustion(player, 0.0005f);
							cd.sluicedAmount++;
						}
						else if (blockHit.getMaterial() == Material.water)
						{
							/*if(world.getBlock(x, y-1, z) == Blocks.gravel)
							{
								is.setItemDamage((5 << 4) + 2);
								if(world.rand.nextInt(10) == 0)
									world.setBlockToAir(x, y-1, z);
								TFC_Core.addPlayerExhaustion(player, 0.0005f);
								cd.sluicedAmount++;
							}
							else if(world.getBlock(x, y-1, z) == TFCBlocks.Sand || world.getBlock(x, y-1, z) == TFCBlocks.Sand2)
							{
								is.setItemDamage((5 << 4) + 1);
								if(world.rand.nextInt(10) == 0)
									world.setBlockToAir(x, y-1, z);
								TFC_Core.addPlayerExhaustion(player, 0.0005f);
								cd.sluicedAmount++;
							}*/
						}
					}
					else
					{
						player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("gui.goldpan.overused")));
					}
				}
				else
				{
					int bMeta = world.getBlockMetadata(x, y + 1, z);
					if(world.getBlock(x, y + 1, z).getMaterial() == Material.water && bMeta > 0)
					{
						int uses = (is.getItemDamage() >> 4);
						if(uses > 0)
						{
							int type = getMetalToDrop(world, player, x, y + 1, z);

							if(type != -1)
							{
								ItemStack out = new ItemStack(TFCItems.SmallOreChunk, 1, type);
								world.playSoundAtEntity(player, "random.pop", 0.7F, world.rand.nextFloat() + 1);
								if(!player.inventory.addItemStackToInventory(out))
								{
									player.dropPlayerItemWithRandomChoice(out, false);
								}
								TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_PROSPECTING, 1);
							}
							uses--;
							if(uses > 0)
								is.setItemDamage((is.getItemDamage() & 15) + (uses << 4));
							else
							{
								if (world.rand.nextInt(100) == 0)
								{
									world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
									is.stackSize--;
								}
								else
									is.setItemDamage(0);
							}
						}
					}
				}
			}
		}
		return is;
	}

	private int getMetalToDrop(World world, EntityPlayer player, int x, int y, int z)
	{
		int type = -1;
		int chunk_X = (x >> 4) << 4;
		int chunk_Z = (z >> 4) << 4;
		Random rand = new Random(world.getSeed() + ((chunk_X >> 3) - (chunk_Z >> 3)) * (chunk_Z >> 3));
		int randType = rand.nextInt(100);
		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_PROSPECTING);
		float skillMod = 1-rank.ordinal()*0.111f;

		if (randType > 25 && world.rand.nextInt((int)Math.floor(60*skillMod)) == 0) type = 0;  // Copper
		if (rank.ordinal() > 0 && randType > 50 && world.rand.nextInt((int)Math.floor(120*skillMod)) == 0) type = 4; // Silver
		if (rank.ordinal() > 1 && randType > 75 && world.rand.nextInt((int)Math.floor(150*skillMod)) == 0) type = 1; // Gold
		if (world.rand.nextInt((int)Math.floor(500*skillMod)) == 0) type = 2; // Platinum

		return type;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
