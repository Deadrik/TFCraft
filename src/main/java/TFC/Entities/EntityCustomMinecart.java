package TFC.Entities;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCustomMinecart extends EntityMinecartChest
{
	public EntityCustomMinecart(World par1World)
	{
		super(par1World);
	}

	public EntityCustomMinecart(World par1, double par2, double par4, double par6)
	{
		super(par1, par2, par4, par6);
	}

	@Override
	public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);
		this.func_145778_a(Item.getItemFromBlock(Blocks.chest), 1, 0.0F);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return 18;
	}

	@Override
	public int getMinecartType()
	{
		return 1;
	}

	@Override
	public Block func_145817_o()/*getDefaultDisplayTile*/
	{
		return Blocks.chest;
	}

	@Override
	public int getDefaultDisplayTileOffset()
	{
		return 8;
	}
}
