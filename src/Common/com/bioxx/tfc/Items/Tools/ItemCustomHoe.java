package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TileEntityFarmland;
import com.bioxx.tfc.api.ISize;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemCustomHoe extends ItemHoe implements ISize
{
	public ItemCustomHoe(ToolMaterial e)
	{
		super(e);
		setCreativeTab(TFCTabs.TFCTools);
		setNoRepair();
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		name = name.replace("IgIn ", "");
		name = name.replace("IgEx ", "");
		name = name.replace("Sed ", "");
		name = name.replace("MM ", "");
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/" + name);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(pass == 1 && nbt != null && nbt.hasKey("broken"))
			return TFC_Textures.BrokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if (world.isRemote || world.getBlock(x, y, z) == TFCBlocks.ToolRack || world.getBlock(x, y, z) == TFCBlocks.ToolRack2)
			return false;
		else
		{
			UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
			if (MinecraftForge.EVENT_BUS.post(event))
				return false;

			if (event.getResult() == Result.ALLOW)
			{
				stack.damageItem(1, player);
				return true;
			}

			Block var8 = world.getBlock(x, y, z);
			Block var9 = world.getBlock(x, y + 1, z);

			boolean isDirt = TFC_Core.isDirt(var8);

			if (side != 1 || var9 != Blocks.air || (!TFC_Core.isGrass(var8) && !isDirt))
				return false;
			else
			{
				Block var10 = var8 == TFCBlocks.Dirt || var8 == TFCBlocks.Grass || var8 == TFCBlocks.DryGrass ? TFCBlocks.Dirt : 
						var8 == TFCBlocks.Dirt2 || var8 == TFCBlocks.Grass2 || var8 == TFCBlocks.DryGrass2 ? TFCBlocks.Dirt2 : null;
				if(var10 != null)
				{
					int meta = world.getBlockMetadata(x, y, z);
					if(var10 == TFCBlocks.Dirt)
					{
						world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, var10.stepSound.getStepResourcePath(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
							return true;
						else
						{
							world.setBlock(x, y, z, TFCBlocks.tilledSoil, meta, 0x2);
							world.markBlockForUpdate(x, y, z);
							stack.damageItem(1, player);

							if(isDirt)
							{
								TileEntityFarmland te = (TileEntityFarmland) world.getTileEntity(x, y, z);
								te.nutrients[0] = 100;
								te.nutrients[1] = 100;
								te.nutrients[2] = 100;
							}
							return true;
						}
					}
					else if(var10 == TFCBlocks.Dirt2)
					{
						world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, var10.stepSound.getStepResourcePath(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
							return true;
						else
						{
							world.setBlock(x, y, z, TFCBlocks.tilledSoil2, meta, 0x2);
							world.markBlockForUpdate(x, y, z);
							stack.damageItem(1, player);

							if(isDirt)
							{
								TileEntityFarmland te = (TileEntityFarmland) world.getTileEntity(x, y, z);
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
		ItemTerra.addSizeInformation(is, arraylist);
		
		if(TFCOptions.enableDebugMode)
			arraylist.add("Damage: " + is.getItemDamage());
	}
	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}
}