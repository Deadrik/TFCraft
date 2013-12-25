package TFC.Items;

import java.util.List;

import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDyeCustom extends ItemTerra
{
	/** List of dye color names */
	public static final String[] dyeColorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	public static final int[] dyeColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
	public static final String[] field_94595_b = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
	@SideOnly(Side.CLIENT)
	private Icon[] field_94594_d;

	public ItemDyeCustom(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
		return this.field_94594_d[j];
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + dyeColorNames[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.field_94594_d = new Icon[field_94595_b.length];

		for (int i = 0; i < field_94595_b.length; ++i)
		{
			this.field_94594_d[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + field_94595_b[i]);
		}
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
		{
			return false;
		}
		else
		{
			int var11;
			int var12;
			int var13;

			if (par1ItemStack.getItemDamage() == 15)
			{
				var11 = par3World.getBlockId(par4, par5, par6);

				BonemealEvent event = new BonemealEvent(par2EntityPlayer, par3World, var11, par4, par5, par6);
				if (MinecraftForge.EVENT_BUS.post(event))
				{
					return false;
				}

				if (event.getResult() == Result.ALLOW)
				{
					if (!par3World.isRemote)
					{
						par1ItemStack.stackSize--;
					}
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
	{
		if (par3EntityLivingBase instanceof EntitySheep)
		{
			EntitySheep var3 = (EntitySheep)par3EntityLivingBase;
			int var4 = BlockColored.getBlockFromDye(par1ItemStack.getItemDamage());

			if (!var3.getSheared() && var3.getFleeceColor() != var4)
			{
				var3.setFleeceColor(var4);
				--par1ItemStack.stackSize;
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 16; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
