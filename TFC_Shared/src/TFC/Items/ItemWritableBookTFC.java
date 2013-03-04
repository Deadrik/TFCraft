package TFC.Items;

import TFC.TerraFirmaCraft;
import TFC.GUI.GuiScreenBookTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.client.entity.*;

public class ItemWritableBookTFC extends ItemTerra
{
    public ItemWritableBookTFC(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer entityplayer)
    {
        //entityplayer.displayGUIBook(par1ItemStack);
    	if(entityplayer.worldObj.isRemote){
    	((EntityPlayerSP) entityplayer).getMcField().displayGuiScreen(new GuiScreenBookTFC(entityplayer, par1ItemStack, false));
    	}
    	return par1ItemStack;
    }
    
    public static boolean validBookTagContents(NBTTagCompound par0NBTTagCompound)
    {
        if (!ItemWritableBook.validBookTagPages(par0NBTTagCompound))
        {
            return false;
        }
        else if (!par0NBTTagCompound.hasKey("title"))
        {
            return false;
        }
        else
        {
            String var1 = par0NBTTagCompound.getString("title");
            return var1 != null && var1.length() <= 16 ? par0NBTTagCompound.hasKey("author") : false;
        }
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    public boolean getShareTag()
    {
        return true;
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound())
        {
            NBTTagCompound var2 = par1ItemStack.getTagCompound();
            NBTTagString var3 = (NBTTagString)var2.getTag("title");

            if (var3 != null)
            {
                return var3.toString();
            }
        }

        return super.getItemDisplayName(par1ItemStack);
    }
    
    public static boolean validBookTagPages(NBTTagCompound par0NBTTagCompound)
    {
        if (par0NBTTagCompound == null)
        {
            return false;
        }
        else if (!par0NBTTagCompound.hasKey("pages"))
        {
            return false;
        }
        else
        {
            NBTTagList var1 = (NBTTagList)par0NBTTagCompound.getTag("pages");

            for (int var2 = 0; var2 < var1.tagCount(); ++var2)
            {
                NBTTagString var3 = (NBTTagString)var1.tagAt(var2);

                if (var3.data == null)
                {
                    return false;
                }

                if (var3.data.length() > 256)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
