package TFC.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.ISize;
import TFC.API.TFCOptions;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityFarmland;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemCustomHoe extends ItemHoe implements ISize
{
	public ItemCustomHoe(int i, EnumToolMaterial e)
	{
		super(i, e);
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if (world.isRemote || world.getBlockId(x, y, z) == TFCBlocks.ToolRack.blockID)
		{
			return false;
		}
		else
		{
			UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return false;
			}

			if (event.getResult() == Result.ALLOW)
			{
				stack.damageItem(1, player);
				return true;
			}

			int var8 = world.getBlockId(x, y, z);
			int var9 = world.getBlockId(x, y + 1, z);

			boolean isDirt = TFC_Core.isDirt(var8);

			if (side != 1 || var9 != 0 || (!TFC_Core.isGrass(var8) && !isDirt))
			{
				return false;
			}
			else
			{
				Block var10 = var8 == TFCBlocks.Dirt.blockID || var8 == TFCBlocks.Grass.blockID || var8 == TFCBlocks.DryGrass.blockID ? TFCBlocks.Dirt : 
					var8 == TFCBlocks.Dirt2.blockID || var8 == TFCBlocks.Grass2.blockID || var8 == TFCBlocks.DryGrass2.blockID ? TFCBlocks.Dirt2 : null;
				if(var10 != null)
				{
					int meta = world.getBlockMetadata(x, y, z);
					if(var10.blockID == TFCBlocks.Dirt.blockID)
					{
						world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
						{
							return true;
						}
						else
						{
							world.setBlock(x, y, z, TFCBlocks.tilledSoil.blockID, meta, 0x2);
							world.markBlockForUpdate(x, y, z);
							stack.damageItem(1, player);

							if(isDirt)
							{
								TileEntityFarmland te = (TileEntityFarmland) world.getBlockTileEntity(x, y, z);
								te.nutrients[0] = 100;
								te.nutrients[1] = 100;
								te.nutrients[2] = 100;
							}
							return true;
						}
					}
					else if(var10.blockID == TFCBlocks.Dirt2.blockID)
					{
						world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
						{
							return true;
						}
						else
						{
							world.setBlock(x, y, z, TFCBlocks.tilledSoil2.blockID, meta, 0x2);
							world.markBlockForUpdate(x, y, z);
							stack.damageItem(1, player);

							if(isDirt)
							{
								TileEntityFarmland te = (TileEntityFarmland) world.getBlockTileEntity(x, y, z);
								te.nutrients[0] = 100;
								te.nutrients[1] = 100;
								te.nutrients[2] = 100;
							}
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);

		if(TFCOptions.enableDebugMode) {
			arraylist.add("Damage: " + is.getItemDamage());
		}
	}
	@Override
	public int getItemStackLimit()
	{
		if(canStack()) {
			return this.getSize().stackSize * getWeight().multiplier;
		} else {
			return 1;
		}
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}
}