package TFC.Items;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.Reference;

public class ItemCustomLeash extends ItemLead
{
	public String textureFolder;
	public ItemCustomLeash()
	{
		super();
		textureFolder = "";
	}
	@Override
	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		Block i1 = par3World.getBlock(par4, par5, par6);
		if (i1 != null && i1.getRenderType() == 11)
		{
			if (par3World.isRemote)
			{
				return true;
			}
			else
			{
				this.func_135066_a(par2EntityPlayer, par3World, par4, par5, par6);
				return true;
			}
		}
		else
		{
			return false;
		}
	}


	public static boolean func_135066_a(EntityPlayer par0EntityPlayer, World par1World, int par2, int par3, int par4)
	{
		EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForBlock(par1World, par2, par3, par4);
		boolean flag = false;
		double d0 = 7.0D;
		List list = par1World.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB((double)par2 - d0, (double)par3 - d0, (double)par4 - d0, (double)par2 + d0, (double)par3 + d0, (double)par4 + d0));

		if (list != null)
		{
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityLiving entityliving = (EntityLiving)iterator.next();

				if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == par0EntityPlayer)
				{
					if (entityleashknot == null)
					{
						entityleashknot = EntityLeashKnot.func_110129_a(par1World, par2, par3, par4);
					}

					entityliving.setLeashedToEntity(entityleashknot, true);
					flag = true;
				}
			}
		}

		return flag;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{        
		return this.itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}

}
