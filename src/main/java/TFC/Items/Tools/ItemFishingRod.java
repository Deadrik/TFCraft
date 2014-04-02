package TFC.Items.Tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.Entities.EntityFishHookTFC;
import TFC.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishingRod extends ItemTerra
{
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;

	public ItemFishingRod()
	{
		super();
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
	 * hands.
	 */
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.fishEntity != null)
		{
			int i = par3EntityPlayer.fishEntity.func_146034_e();
			par1ItemStack.damageItem(i, par3EntityPlayer);
			par3EntityPlayer.swingItem();
		}
		else
		{
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!par2World.isRemote)
				par2World.spawnEntityInWorld(new EntityFishHookTFC(par2World, par3EntityPlayer));
			par3EntityPlayer.swingItem();
		}
		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		Items.fishing_rod.registerIcons(par1IconRegister);
		this.itemIcon = par1IconRegister.registerIcon(this.getIconString() + "_uncast");
		this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_cast");
	}

	@SideOnly(Side.CLIENT)
	public IIcon func_94597_g()
	{
		return this.theIcon;
	}
}
