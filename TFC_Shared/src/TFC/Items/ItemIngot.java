package TFC.Items;

import java.awt.image.BufferedImage;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Enums.EnumMetalType;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityIngotPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemTerra
{
	EnumSize size = EnumSize.SMALL;
	public EnumMetalType MetalType;
	private static String[] metalTypes =  new String[]{"Bismuth", "Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Brass", 
		"Bronze", "Copper", "Gold", "Wrought Iron", "Lead", "Nickel", "Pig Iron", "Platinum", "Red Steel", "Rose Gold", "Silver", "Steel",
		"Sterlign Silver", "Tin", "Zinc" };
	BufferedImage bi;
	public ItemIngot(int i, EnumMetalType metalType) 
	{
		super(i);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		MetalType = metalType;
		/*try {
			bi= ImageIO.read(new File("/textures/blocks/metal/"+metalTypes[MetalType.MetalID]+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		this.setFolder("ingots/");

	}
	
	@Override
	public void updateIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		NBTTagCompound stackTagCompound = par1ItemStack.getTagCompound();
	
		if(stackTagCompound!=null&&stackTagCompound.hasKey("temperature")&&par2!=0)
		{
			float temp = stackTagCompound.getFloat("temperature");
			
			int red = (int)((Math.min(Math.max(0, temp-600),350)/350)*255);
			int yellow = (int)((Math.min(Math.max(50,temp-850),350)/350)*255);
			int white = (int)((Math.min(Math.max(50, temp-1000),600)/600)*255);
			int result = 0;
			
			//int pixel = bi.getRGB(2,2);
			int originalR = 169;//pixel >> 24;
			int originalG = 183;//pixel >> 16;
			int originalB = 203;//pixel >> 8;
			int tempRed = originalR - Math.min((yellow + white)/2,originalR);
			int tempGreen = red<200?originalG - ((white + red)/2):0;
			int tempBlue = red<200?originalB - ((yellow + red)/2):0;
			
			float mod = Math.max(0, (120-red)/120);
			result+=(Math.min(red + (int)(originalR*mod), 255))<<16;
			result+=(Math.min(yellow+(int)(originalG*mod), 255))<<8;
			result+=Math.min(white+(int)(originalB*mod), 255);
			System.out.println((result>>16 & 255) + ", " + (result>>8 & 255)+", "+(result & 255));
			return result;
		}
		return 16777215;
	}*/

	@Override
	public EnumSize getSize() {
		return size;
	}

	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

	public ItemIngot setSize(EnumSize s)
	{
		size = s;
		return this;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}


	private boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y,
			int z, int side, int l) 
	{

		boolean fullStack = true;

		TileEntityIngotPile te = null;

		if (world.getBlockTileEntity(x, y, z) instanceof TileEntityIngotPile && world.getBlockId(x,y,z) == TFCBlocks.IngotPile.blockID)
		{
			te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z);
			if (te.contentsMatch(0,itemstack) && te.getStackInSlot(0).stackSize < te.getInventoryStackLimit()){

				fullStack = false;
				te.injectContents(0,1);
			}
		}
		else{fullStack = true;}

		if(fullStack)
		{
			if (itemstack.getItemDamage() >= 16)
			{
				if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					world.setBlock( x, y-1, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y-1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y-1, z);
				}
				else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
				{
					world.setBlock( x, y+1, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y+1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y+1, z);
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
				{
					world.setBlock( x, y, z-1, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z-1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z-1);
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
				{
					world.setBlock( x, y, z+1, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z+1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z+1);
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
				{
					world.setBlock( x-1, y, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x-1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x-1, y, z);
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
				{
					world.setBlock( x+1, y, z, TFCBlocks.IngotPile.blockID, l, 3);
					if(world.isRemote)
						world.markBlockForUpdate(x+1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x+1, y, z);
				}
				else
				{
					return false;
				}
			}
			else
			{

				if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					world.setBlock( x, y-1, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y-1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y-1, z);
				}
				else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
				{
					world.setBlock( x, y+1, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y+1, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y+1, z);
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
				{
					world.setBlock( x, y, z-1, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z-1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z-1);
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
				{
					world.setBlock( x, y, z+1, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x, y, z+1);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z+1);
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
				{
					world.setBlock( x-1, y, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x-1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x-1, y, z);
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
				{
					world.setBlock( x+1, y, z, TFCBlocks.IngotPile.blockID, l, 0x2);
					if(world.isRemote)
						world.markBlockForUpdate(x+1, y, z);
					te = (TileEntityIngotPile)world.getBlockTileEntity(x+1, y, z);
				}
				else
				{
					return false;
				}
			}

			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1,0);
				te.setType(this.MetalType.MetalID);

				if(entityplayer.capabilities.isCreativeMode)
				{
					te.storage[0] = new ItemStack(this,32,0);
				}
			}
		}		
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(entityplayer.username.equals("capt_slowpoke") && /*(!entityplayer.worldObj.isRemote)||*/ entityplayer instanceof EntityPlayerMP){
			((EntityPlayerMP)entityplayer).mcServer.getConfigurationManager().sendChatMsg(entityplayer.username+" sniffs the ingot");

		}
		NBTTagCompound stackTagCompound = itemstack.getTagCompound();

		if(entityplayer.isSneaking() && stackTagCompound == null && (itemstack.getItem().getUnlocalizedName().indexOf("Double")==-1))
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			if(!world.isRemote && entityplayer.isSneaking() && (world.getBlockId(x, y, z) != TFCBlocks.IngotPile.blockID || (side != 1 && side != 0)))
			{

				if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir))
				{

					itemstack.stackSize = itemstack.stackSize-1;
					world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
					return true;

				}
			}
			else if(world.getBlockId(x, y, z) == TFCBlocks.IngotPile.blockID)
			{
				TileEntityIngotPile te = (TileEntityIngotPile)world.getBlockTileEntity(x, y, z);
				//TileEntityIngotPile te2 = (TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
				if(te != null)
				{
					te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
					if(te.storage[0] != null && te.contentsMatch(0,itemstack) && te.storage[0].stackSize < 64) 
					{
						te.injectContents(0,1);
						te.validate();
					} 
					else if(te.storage[0] == null) 
					{
						te.addContents(0, new ItemStack(this,1, itemstack.getItem().itemID));
					} 
					else
					{
						if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir))
						{						
							itemstack.stackSize = itemstack.stackSize-1;
							if (world.getBlockTileEntity(x,y,z) != null)
							{
								if (((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).getType()==-1)
								{
									((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.MetalType.MetalID);
								}
							}
							world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
							te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
						}
						return true;

					}
					itemstack.stackSize = itemstack.stackSize-1;
					if (world.getBlockTileEntity(x,y,z) != null)
					{
						((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.MetalType.MetalID);
					}
					world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
					return true;
				}

			}
			else
			{
				int m = itemstack.getItemDamage();
				if(side == 1)
				{
					if (m>=16){
						world.setBlock(x, y+1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y+1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					if(m >=16){
						world.setBlock(x, y-1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y-1, z, TFCBlocks.IngotPile.blockID, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, -1);
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, 1);
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, -1, 0, 0);
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, 1, 0, 0);
				}
				if (world.getBlockTileEntity(x,y,z) != null && world.getBlockTileEntity(x,y,z) instanceof TileEntityIngotPile)
            {
					((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).setType(this.itemID - 16028 - 256);
				}
				world.addBlockEvent(x,y,z,TFCBlocks.IngotPile.blockID,0,0);
				return true;
			}

		}
		return false;
	}

	public void setSide(World world, ItemStack itemstack, int m, int dir, int x, int y, int z, int i, int j, int k)
	{
		if(m < 8)
		{
			if(dir == 0 || dir == 2)
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m, 0x2);
			else
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m | 8, 0x2);
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 16)
		{
			if(dir == 0 || dir == 2)
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m-8, 0x2);
			else
				world.setBlock(x+i, y+j, z+k, TFCBlocks.IngotPile.blockID, m-8 | 8, 0x2);
			itemstack.stackSize = itemstack.stackSize-1;
		}

	}
}
