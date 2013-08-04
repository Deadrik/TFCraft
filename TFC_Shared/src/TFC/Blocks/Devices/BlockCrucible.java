package TFC.Blocks.Devices;

import java.util.Iterator;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.Metal.AlloyMetal;
import TFC.TileEntities.TECrucible;

public class BlockCrucible extends BlockTerraContainer 
{
	Icon[] icons;

	public BlockCrucible(int par1) 
	{
		super(par1);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.9375f, 0.9375f);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(!world.isRemote && (TECrucible)world.getBlockTileEntity(i, j, k) != null)
		{
			TECrucible te = (TECrucible)world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if(is != null && is.getItem().itemID == TFCItems.CeramicMold.itemID && is.getItemDamage() == 1)
			{
				if(te.currentAlloy != null && te.currentAlloy.outputType != null)
				{
					if(te.currentAlloy.outputAmount >= 100)
					{
						entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.itemsList[te.currentAlloy.outputType.MeltedItemID], 1));
						te.currentAlloy.outputAmount -= 100;
					}
					else
					{
						entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.itemsList[te.currentAlloy.outputType.MeltedItemID], 1, 100-te.currentAlloy.outputAmount));
						te.currentAlloy.outputAmount = 0;
					}
					is.stackSize--;
				}
			}
			else
			{
				if(te.currentAlloy != null)
				{
					Iterator iter = te.currentAlloy.AlloyIngred.iterator();
					if(te.currentAlloy.outputType != null)
						entityplayer.sendChatToPlayer(te.currentAlloy.outputType.Name + ":");
					else
						entityplayer.sendChatToPlayer("Unknown:");
					while(iter.hasNext())
					{
						AlloyMetal am = (AlloyMetal)iter.next();
						entityplayer.sendChatToPlayer(am.metalType.Name + ": " + Math.round(am.metal * 100d)/100d + "%");
					}
				}
			}
		}
		return true;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		icons = new Icon[2];
		icons[0] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Crucible Top");
		icons[1] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Crucible Side");
	}

	@Override
	public Icon getIcon(int i, int j) 
	{
		if(i < 2)
			return icons[0];

		return icons[1];
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.crucibleRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TECrucible();
	}

}
