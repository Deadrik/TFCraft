package TFC.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
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
							int type = -1;

							if (type == -1 && world.rand.nextInt(60) == 0) type = 0;
							if (type == -1 && world.rand.nextInt(150) == 0) type = 1;
							if (type == -1 && world.rand.nextInt(120) == 0) type = 3;
							if (type == -1 && world.rand.nextInt(500) == 0) type = 2;

							if(type != -1)
							{
								ItemStack out = null;
								switch(type)
								{
								case 0://Copper
									out = new ItemStack(TFCItems.SmallOreChunk, 1, 0); break;
								case 1://Gold
									out = new ItemStack(TFCItems.SmallOreChunk, 1, 1); break;
								case 2://Platinum
									out = new ItemStack(TFCItems.SmallOreChunk, 1, 2); break;
								case 3://Silver
									out = new ItemStack(TFCItems.SmallOreChunk, 1, 4); break;
								}
								if(!player.inventory.addItemStackToInventory(out))
								{
									player.dropPlayerItem(out);
								}
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
}
