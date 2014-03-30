package TFC.Items.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.TFCOptions;
import TFC.API.Enums.EnumSize;
import TFC.API.Util.Helper;
import TFC.Blocks.Terrain.BlockOre;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Items.ItemTerra;
import TFC.WorldGen.Biomes.BiomeGenRiverTFC;

public class ItemGoldPan extends ItemTerra
{
	public static String[] MetaNames = {"", "Sand", "Gravel", "Clay", "Dirt"};
	public IIcon[] icons = new IIcon[MetaNames.length];

	private int useTimer = 0;
	public ItemGoldPan() 
	{
		super();
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
	public IIcon getIconFromDamage(int i)
	{
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
	public void registerIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_0");
		icons[1] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_1");
		icons[2] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_2");
		icons[3] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_3");
		icons[4] = registerer.registerIcon(Reference.ModID + ":" + "tools/GoldPan_4");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) 
	{
		return this.getUnlocalizedName();//"GoldPan";//blockNames[itemstack.getItemDamage()];
	}

	public String getName(ItemStack itemstack) 
	{
		return this.getUnlocalizedName();//MetaNames[itemstack.getItemDamage()];
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x0, int y0, int z0, int l, float par8, float par9, float par10)
	{

		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		if(objectMouseOver == null) {
			return false;
		}		
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;
		int side = objectMouseOver.sideHit;

		if(!world.isRemote)
		{            
			//Do this if it's an empty pan
			if(itemstack.getItemDamage() == 0)
			{
				boolean isBiomeRiver = false;
				for (int i = -25; i < 25; i++)
				{
					for (int j = -25; j < 25; j++)
					{
						if(world.getBiomeGenForCoords(x+i, z+j) instanceof BiomeGenRiverTFC)
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
						int k = world.getBiomeGenForCoords(x+i, z+j).biomeID;
						if(!L.contains(k)) {
							L.add(k);
						}
					}
				}
				Random R = new Random();
				if(TFC_Core.isSand(world.getBlock(x, y-1, z)) && isBiomeRiver)
				{
					if(R.nextInt(10) == 0) {
						world.setBlockToAir(x, y-1, z);
					}
					itemstack.setItemDamage(1);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
					return true;
				}
				else if(world.getBlock(x, y-1, z) == Blocks.gravel)
				{
					if(R.nextInt(10) == 0) {
						world.setBlockToAir(x, y-1, z);
					}
					itemstack.setItemDamage(2);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
					return true;
				}
				else if(TFC_Core.isSand(world.getBlock(x, y-2, z)) && isBiomeRiver)
				{
					if(R.nextInt(10) == 0) {
						world.setBlockToAir(x, y-2, z);
					}
					itemstack.setItemDamage(1);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
					return true;
				}
				else if(world.getBlock(x, y-2, z) == Blocks.gravel)
				{
					if(R.nextInt(10) == 0) {
						world.setBlockToAir(x, y-2, z);
					}
					itemstack.setItemDamage(2);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
					return true;
				}
				entityplayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("gui.GoldPan.UseEmpty")));
			}
			else
			{
				entityplayer.addExhaustion(0.2f);

				float gemMod = 1;
				float oreMod = 1;
				if(itemstack.getItemDamage() == 1) {
					gemMod = 0.75f;
				} else if(itemstack.getItemDamage() == 2) {
					oreMod = 0.7f;
				}

				Block blockAbove = world.getBlock(x, y+1, z);
				int blockAboveMeta = world.getBlockMetadata(x, y+1, z);

				if(TFCOptions.enableDebugMode) {
					System.out.println(new StringBuilder().append(blockAbove.getUnlocalizedName()).append(" ").append(blockAboveMeta).toString());
				}

				if(blockAbove == Blocks.water && blockAboveMeta > 0)
				{
					if(TFCOptions.enableDebugMode) {
						System.out.println(new StringBuilder().append("True").toString());
					}
					useTimer = 20;
					Random random = new Random();

					ArrayList coreSample = new ArrayList<Item>();
					ArrayList coreSampleStacks = new ArrayList<ItemStack>();

					for(int i = -50; i <= 50; i += 2)
					{
						for(int k = -50; k <= 50; k += 2)
						{
							for(int j = y; j > y-35; j--)
							{
								if(world.getBlock(i+x, j, k+z) == TFCBlocks.Ore)
								{
									int m = world.getBlockMetadata(i+x, j, k+z);
									if(!coreSample.contains(BlockOre.getDroppedItem(m)))
									{
										//coreSample.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC_Core.terraOre).damageDropped(meta)));
										if(m!= 14 && m != 15)
										{
											coreSample.add(BlockOre.getDroppedItem(m));
											coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
										}
									}
								}
							}
						}
					}
					if(random.nextInt((int) (250 * oreMod)) == 0 && !coreSampleStacks.isEmpty())
					{
						entityplayer.inventory.addItemStackToInventory((ItemStack)coreSampleStacks.toArray()[random.nextInt(coreSampleStacks.toArray().length)]);
					}
					else if(random.nextInt((int) (400 * gemMod)) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.GemAgate,1,0));
						items.add(new ItemStack(TFCItems.GemAmethyst,1,0));
						items.add(new ItemStack(TFCItems.GemBeryl,1,0));
						items.add(new ItemStack(TFCItems.GemEmerald,1,0));
						items.add(new ItemStack(TFCItems.GemGarnet,1,0));
						items.add(new ItemStack(TFCItems.GemJade,1,0));
						items.add(new ItemStack(TFCItems.GemJasper,1,0));
						items.add(new ItemStack(TFCItems.GemOpal,1,0));
						items.add(new ItemStack(TFCItems.GemRuby,1,0));
						items.add(new ItemStack(TFCItems.GemSapphire,1,0));
						items.add(new ItemStack(TFCItems.GemTourmaline,1,0));
						items.add(new ItemStack(TFCItems.GemTopaz,1,0));
						items.add(new ItemStack(Items.gold_nugget,1,0));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

					}
					else if(random.nextInt((int) (800 * gemMod)) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.GemAgate,1,1));
						items.add(new ItemStack(TFCItems.GemAmethyst,1,1));
						items.add(new ItemStack(TFCItems.GemBeryl,1,1));
						items.add(new ItemStack(TFCItems.GemEmerald,1,1));
						items.add(new ItemStack(TFCItems.GemGarnet,1,1));
						items.add(new ItemStack(TFCItems.GemJade,1,1));
						items.add(new ItemStack(TFCItems.GemJasper,1,1));
						items.add(new ItemStack(TFCItems.GemOpal,1,1));
						items.add(new ItemStack(TFCItems.GemRuby,1,1));
						items.add(new ItemStack(TFCItems.GemSapphire,1,1));
						items.add(new ItemStack(TFCItems.GemTourmaline,1,1));
						items.add(new ItemStack(TFCItems.GemTopaz,1,1));
						items.add(new ItemStack(TFCItems.GemDiamond,1,0));
						items.add(new ItemStack(Items.gold_nugget,2,0));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (1600 * gemMod)) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.GemAgate,1,2));
						items.add(new ItemStack(TFCItems.GemAmethyst,1,2));
						items.add(new ItemStack(TFCItems.GemBeryl,1,2));
						items.add(new ItemStack(TFCItems.GemEmerald,1,2));
						items.add(new ItemStack(TFCItems.GemGarnet,1,2));
						items.add(new ItemStack(TFCItems.GemJade,1,2));
						items.add(new ItemStack(TFCItems.GemJasper,1,2));
						items.add(new ItemStack(TFCItems.GemOpal,1,2));
						items.add(new ItemStack(TFCItems.GemRuby,1,2));
						items.add(new ItemStack(TFCItems.GemSapphire,1,2));
						items.add(new ItemStack(TFCItems.GemTourmaline,1,2));
						items.add(new ItemStack(TFCItems.GemTopaz,1,2));
						items.add(new ItemStack(TFCItems.GemDiamond,1,1));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (3200 * gemMod)) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.GemAgate,1,3));
						items.add(new ItemStack(TFCItems.GemAmethyst,1,3));
						items.add(new ItemStack(TFCItems.GemBeryl,1,3));
						items.add(new ItemStack(TFCItems.GemEmerald,1,3));
						items.add(new ItemStack(TFCItems.GemGarnet,1,3));
						items.add(new ItemStack(TFCItems.GemJade,1,3));
						items.add(new ItemStack(TFCItems.GemJasper,1,3));
						items.add(new ItemStack(TFCItems.GemOpal,1,3));
						items.add(new ItemStack(TFCItems.GemRuby,1,3));
						items.add(new ItemStack(TFCItems.GemSapphire,1,3));
						items.add(new ItemStack(TFCItems.GemTourmaline,1,3));
						items.add(new ItemStack(TFCItems.GemTopaz,1,3));
						items.add(new ItemStack(TFCItems.GemDiamond,1,2));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (6400 * gemMod)) == 0)
					{
						ArrayList items = new ArrayList<ItemStack>();
						items.add(new ItemStack(TFCItems.GemAgate,1,4));
						items.add(new ItemStack(TFCItems.GemAmethyst,1,4));
						items.add(new ItemStack(TFCItems.GemBeryl,1,4));
						items.add(new ItemStack(TFCItems.GemEmerald,1,4));
						items.add(new ItemStack(TFCItems.GemGarnet,1,4));
						items.add(new ItemStack(TFCItems.GemJade,1,4));
						items.add(new ItemStack(TFCItems.GemJasper,1,4));
						items.add(new ItemStack(TFCItems.GemOpal,1,4));
						items.add(new ItemStack(TFCItems.GemRuby,1,4));
						items.add(new ItemStack(TFCItems.GemSapphire,1,4));
						items.add(new ItemStack(TFCItems.GemTourmaline,1,4));
						items.add(new ItemStack(TFCItems.GemTopaz,1,4));
						items.add(new ItemStack(TFCItems.GemDiamond,1,3));

						entityplayer.inventory.addItemStackToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

					}
					else if(random.nextInt((int) (12800 * gemMod)) == 0)
					{
						entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.GemDiamond,1,2));
					}

					itemstack.setItemDamage(0);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
					return true;
				}
				else
				{
					entityplayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("gui.GoldPan.UseFull")));
				}
			}
		}
		return true;
	}
}
