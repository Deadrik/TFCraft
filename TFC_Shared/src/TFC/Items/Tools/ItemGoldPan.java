package TFC.Items.Tools;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.API.Util.Helper;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemGoldPan extends ItemTerra
{
	public Icon[] icons = new Icon[5];

	private int useTimer = 0;
	public ItemGoldPan(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName("GoldPan");
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.SMALL;
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
	}

	@Override
	public Icon getIconFromDamage(int i)
	{
		i = i & 15;
		if(i == 1) {
			return icons[1];
		}
		if(i == 2) {
			return icons[2];
		}
		if(i == 3) {
			return icons[3];
		}
		if(i == 4) {
			return icons[4];
		}

		return icons[0];
	}

	@Override
	public void registerIcons(IconRegister registerer)
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

		float distMod = 1.0F;
		double var5 = player.prevPosX + (player.posX - player.prevPosX) * distMod;
		double var7 = player.prevPosY + (player.posY - player.prevPosY) * distMod + 1.62D - player.yOffset;
		double var9 = player.prevPosZ + (player.posZ - player.prevPosZ) * distMod;
		MovingObjectPosition mop = Helper.getMovingObjectPositionFromPlayer(world, player, true);

		if (mop == null)
		{
			return is;
		}
		else
		{
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			Block blockHit = Block.blocksList[world.getBlockId(x, y, z)];
			if (mop.typeOfHit == EnumMovingObjectType.TILE)
			{
				if(is.getItemDamage() == 0)
				{
					ChunkData cd = ChunkDataManager.getData(x >> 4, z >> 4);

					if(cd.sluicedAmount < 100)
					{
						if(world.getBlockId(x, y, z) == Block.gravel.blockID)
						{
							is.setItemDamage((5 << 4) + 2);
							if(world.rand.nextInt(10) == 0) {
								world.setBlock(x, y, z, 0);
							}
							TFC_Core.addPlayerExhaustion(player, 0.0005f);
							cd.sluicedAmount++;
						}
						else if(world.getBlockId(x, y, z) == TFCBlocks.Sand.blockID || world.getBlockId(x, y, z) == TFCBlocks.Sand2.blockID)
						{
							is.setItemDamage((5 << 4) + 1);
							if(world.rand.nextInt(10) == 0) {
								world.setBlock(x, y, z, 0);
							}
							TFC_Core.addPlayerExhaustion(player, 0.0005f);
							cd.sluicedAmount++;
						}
						else if(world.getBlockMaterial(x, y, z) == Material.water)
						{
							if(world.getBlockId(x, y-1, z) == Block.gravel.blockID)
							{
								is.setItemDamage((5 << 4) + 2);
								if(world.rand.nextInt(10) == 0) {
									world.setBlock(x, y-1, z, 0);
								}
								TFC_Core.addPlayerExhaustion(player, 0.0005f);
								cd.sluicedAmount++;
							}
							else if(world.getBlockId(x, y-1, z) == TFCBlocks.Sand.blockID || world.getBlockId(x, y-1, z) == TFCBlocks.Sand2.blockID)
							{
								is.setItemDamage((5 << 4) + 1);
								if(world.rand.nextInt(10) == 0) {
									world.setBlock(x, y-1, z, 0);
								}
								TFC_Core.addPlayerExhaustion(player, 0.0005f);
								cd.sluicedAmount++;
							}
						}
					}
					else
					{
						player.addChatMessage(StringUtil.localize("gui.goldpan.overused"));
					}
				}
				else
				{
					int bMeta = world.getBlockMetadata(x, y+1, z);
					if(world.getBlockMaterial(x, y+1, z) == Material.water && bMeta > 0)
					{
						int uses = (is.getItemDamage() >> 4);
						if(uses > 0)
						{
							int type = getMetalToDrop(world, x, y+1, z);

							if(type != -1)
							{
								ItemStack out = new ItemStack(TFCItems.SmallOreChunk, 1, type);
								dropItem(world,(int) Math.floor(player.posX),(int) Math.floor(player.posY),(int) Math.floor(player.posZ),out);
							}
							uses--;
							if(uses > 0)
								is.setItemDamage((is.getItemDamage() & 15) + (uses << 4));
							else
								is.setItemDamage(0);

						}
					}
				}
			}
		}
		return is;
	}

	private int getMetalToDrop(World world, int x, int y, int z) {
		int type = -1;
		int chunk_X = (x >> 4) << 4;
		int chunk_Z = (z >> 4) << 4;
		Random rand = new Random(world.getSeed() + ((chunk_X >> 3) - (chunk_Z >> 3)) * (chunk_Z >> 3));
		int randType = rand.nextInt(100);

		if (randType > 25 && world.rand.nextInt(60) == 0) type = 0;  // Copper
		if (randType > 50 && world.rand.nextInt(120) == 0) type = 4; // Silver
		if (randType > 75 && world.rand.nextInt(150) == 0) type = 1; // Gold
		if (world.rand.nextInt(500) == 0) type = 2; // Platinum

		return type;
	}

	private void dropItem(World world, double x, double y, double z, ItemStack stack)
	{
		if (!world.isRemote)
		{
			float d = 0.175F;
			double  v = 0.10d;
			double dx = (world.rand.nextFloat() - 0.5) * d;
			double dy = (world.rand.nextFloat() - 0.5) * d + 1.0d;
			double dz = (world.rand.nextFloat() - 0.5) * d;
			EntityItem drop = new EntityItem(world, x + dx, y + dy, z + dz, stack);
			drop.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(drop);
		}
	}

	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}
}
