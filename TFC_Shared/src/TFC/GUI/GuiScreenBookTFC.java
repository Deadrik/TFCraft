package TFC.GUI;

import TFC.TFCItems;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiScreenBookTFC extends GuiScreen
{
    /** The player editing the book */
    private final EntityPlayer editingPlayer;
    private final ItemStack itemstackBook;

    /** Whether the book is signed or can still be edited */
    private final boolean bookIsUnsigned;
    private boolean bookModified;
    private boolean editingTitle;

    /** Update ticks since the gui was opened */
    private int updateCount;
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;
    private int bookTotalPages = 1;
    private int currPage;
    private NBTTagList bookPages;
    private String bookTitle = "";
    private String preTitle = "";
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private GuiButton buttonDone;

    /** The GuiButton to sign this book. */
    private GuiButton buttonSign;
    private GuiButton buttonFinalize;
    private GuiButton buttonCancel;

    public GuiScreenBookTFC(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, boolean par3)
    {
        this.editingPlayer = par1EntityPlayer;
        this.itemstackBook = par2ItemStack;
        this.bookIsUnsigned = par3;

        if (par2ItemStack.hasTagCompound())
        {
            NBTTagCompound var4 = par2ItemStack.getTagCompound();
            this.bookPages = var4.getTagList("pages");
            this.bookTitle = var4.getString("title");
            this.preTitle = bookTitle;
            if (this.bookPages != null)
            {
                this.bookPages = (NBTTagList)this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();

                if (this.bookTotalPages < 1)
                {
                    this.bookTotalPages = 1;
                }
            }
        }

        if (this.bookPages == null && par3)
        {
            this.bookPages = new NBTTagList("pages");
            this.bookPages.appendTag(new NBTTagString("1", ""));
            this.bookTotalPages = 1;
        }
    }
    

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.updateCount;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        if (this.bookIsUnsigned)
        {
            this.buttonList.add(this.buttonSign = new GuiButton(3, this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("book.signButton")));
            this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("gui.done")));
            this.buttonList.add(this.buttonFinalize = new GuiButton(5, this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("book.finalizeButton")));
            this.buttonList.add(this.buttonCancel = new GuiButton(4, this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("gui.cancel")));
        }
        else
        {
            this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, StatCollector.translateToLocal("gui.done")));
        }

        int var1 = (this.width - this.bookImageWidth) / 2;
        byte var2 = 2;
        this.buttonList.add(this.buttonNextPage = new GuiButtonNextPage(1, var1 + 120, var2 + 154, true));
        this.buttonList.add(this.buttonPreviousPage = new GuiButtonNextPage(2, var1 + 38, var2 + 154, false));
        this.updateButtons();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    private void updateButtons()
    {
        this.buttonNextPage.drawButton = !this.editingTitle && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned);
        this.buttonPreviousPage.drawButton = !this.editingTitle && this.currPage > 0;
        this.buttonDone.drawButton = !this.bookIsUnsigned || !this.editingTitle;

        if (this.bookIsUnsigned)
        {
            this.buttonSign.drawButton = !this.editingTitle;
            this.buttonCancel.drawButton = this.editingTitle;
            this.buttonFinalize.drawButton = this.editingTitle;
            this.buttonFinalize.enabled = this.bookTitle.trim().length() > 0 && bookTitle != preTitle;
        }
    }

    private void sendBookToServer(boolean par1)
    {
        if (this.bookIsUnsigned /*&& this.bookModified*/)
        {
            if (this.bookPages != null)
            {
            	
                while (this.bookPages.tagCount() > 1)
                {
                    NBTTagString var2 = (NBTTagString)this.bookPages.tagAt(this.bookPages.tagCount() - 1);
                    if (var2.data != null && var2.data.length() != 0)
                    {
                        break;
                    }
                    
                    this.bookPages.removeTag(this.bookPages.tagCount() - 1);
                }

                if (this.itemstackBook.hasTagCompound())
                {
                    NBTTagCompound var7 = this.itemstackBook.getTagCompound();
                    var7.setTag("pages", this.bookPages);
                }
                else
                {
                    this.itemstackBook.setTagInfo("pages", this.bookPages);
                }
                String var8 = "TerraFirmaCraft";

                //if (par1)
                //{
                    var8 = "TerraFirmaCraft";
                    this.itemstackBook.setTagInfo("author", new NBTTagString("author", this.editingPlayer.username));
                    this.itemstackBook.setTagInfo("title", new NBTTagString("title", this.bookTitle.trim().length()>0?this.bookTitle.trim():"Book"));
                    this.itemstackBook.itemID = TFCItems.writabeBookTFC.itemID;
                //}

                ByteArrayOutputStream var3 = new ByteArrayOutputStream(1000);
                DataOutputStream var4 = new DataOutputStream(var3);
                try
                {
                	var4.writeByte(PacketHandler.Packet_Book_Sign);
                    Packet.writeItemStack(this.itemstackBook, var4);
                    this.mc.getSendQueue().addToSendQueue(new Packet250CustomPayload(var8, var3.toByteArray()));
                }
                catch (Exception var6)
                {
                    var6.printStackTrace();
                }
            }
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 0)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
                //editingPlayer.entityDropItem(itemstackBook, 0);
                this.sendBookToServer(false);
            }
            else if (par1GuiButton.id == 3 && this.bookIsUnsigned)
            {
                this.editingTitle = true;
            }
            else if (par1GuiButton.id == 1)
            {
                if (this.currPage < this.bookTotalPages - 1)
                {
                    ++this.currPage;
                }
                else if (this.bookIsUnsigned)
                {
                    this.addNewPage();

                    if (this.currPage < this.bookTotalPages - 1)
                    {
                        ++this.currPage;
                    }
                }
            }
            else if (par1GuiButton.id == 2)
            {
                if (this.currPage > 0)
                {
                    --this.currPage;
                }
            }
            else if (par1GuiButton.id == 5 && this.editingTitle)
            {
                this.sendBookToServer(true);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (par1GuiButton.id == 4 && this.editingTitle)
            {
                this.editingTitle = false;
            }

            this.updateButtons();
        }
    }

    private void addNewPage()
    {
        if (this.bookPages != null && this.bookPages.tagCount() < 50)
        {
            this.bookPages.appendTag(new NBTTagString("" + (this.bookTotalPages + 1), ""));
            ++this.bookTotalPages;
            this.bookModified = true;
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);

        if (this.bookIsUnsigned)
        {
            if (this.editingTitle)
            {
            	preTitle = "";
                this.func_74162_c(par1, par2);
            }
            else
            {
                this.keyTypedInBook(par1, par2);
            }
        }
    }

    /**
     * Processes keystrokes when editing the text of a book
     */
    private void keyTypedInBook(char par1, int par2)
    {
        switch (par1)
        {
            case 22:
                this.func_74160_b(GuiScreen.getClipboardString());
                return;
            default:
                switch (par2)
                {
                    case 14:
                        String var3 = this.func_74158_i();

                        if (var3.length() > 0)
                        {
                            this.func_74159_a(var3.substring(0, var3.length() - 1));
                        }

                        return;
                    case 28:
                        this.func_74160_b("\n");
                        return;
                    default:
                        if (ChatAllowedCharacters.isAllowedCharacter(par1))
                        {
                            this.func_74160_b(Character.toString(par1));
                        }
                }
        }
    }

    private void func_74162_c(char par1, int par2)
    {
        switch (par2)
        {
            case 14:
                if (this.bookTitle.length() > 0)
                {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }

                return;
            case 28:
                if (this.bookTitle.length() > 0)
                {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }

                return;
            default:
                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(par1))
                {
                    this.bookTitle = this.bookTitle + Character.toString(par1);
                    this.updateButtons();
                    this.bookModified = true;
                }
        }
    }

    private String func_74158_i()
    {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
        {
            NBTTagString var1 = (NBTTagString)this.bookPages.tagAt(this.currPage);
            return var1.toString();
        }
        else
        {
            return "";
        }
    }

    private void func_74159_a(String par1Str)
    {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
        {
            NBTTagString var2 = (NBTTagString)this.bookPages.tagAt(this.currPage);
            var2.data = par1Str;
            this.bookModified = true;
        }
    }

    private void func_74160_b(String par1Str)
    {
        String var2 = this.func_74158_i();
        String var3 = var2 + par1Str;
        int var4 = this.fontRenderer.splitStringWidth(var3 + "\u00a70_", 118);

        if (var4 <= 118 && var3.length() < 256)
        {
            this.func_74159_a(var3);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/gui/book.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.bookImageWidth) / 2;
        byte var6 = 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.bookImageWidth, this.bookImageHeight);
        String var7;
        String var8;
        int var9;

        if (this.editingTitle)
        {
            var7 = this.bookTitle;

            if (this.bookIsUnsigned)
            {
                if (this.updateCount / 6 % 2 == 0)
                {
                    var7 = var7 + "\u00a70_";
                }
                else
                {
                    var7 = var7 + "\u00a77_";
                }
            }

            var8 = StatCollector.translateToLocal("book.editTitle");
            var9 = this.fontRenderer.getStringWidth(var8);
            this.fontRenderer.drawString(var8, var5 + 36 + (116 - var9) / 2, var6 + 16 + 16, 0);
            int var10 = this.fontRenderer.getStringWidth(var7);
            this.fontRenderer.drawString(var7, var5 + 36 + (116 - var10) / 2, var6 + 48, 0);
            String var11 = String.format(StatCollector.translateToLocal("book.byAuthor"), new Object[] {this.editingPlayer.username});
            int var12 = this.fontRenderer.getStringWidth(var11);
            this.fontRenderer.drawString("\u00a78" + var11, var5 + 36 + (116 - var12) / 2, var6 + 48 + 10, 0);
            String var13 = StatCollector.translateToLocal("book.finalizeWarning");
            this.fontRenderer.drawSplitString(var13, var5 + 36, var6 + 80, 116, 0);
        }
        else
        {
            var7 = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[] {Integer.valueOf(this.currPage + 1), Integer.valueOf(this.bookTotalPages)});
            var8 = "";

            if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
            {
                NBTTagString var14 = (NBTTagString)this.bookPages.tagAt(this.currPage);
                var8 = var14.toString();
            }

            if (this.bookIsUnsigned)
            {
                if (this.fontRenderer.getBidiFlag())
                {
                    var8 = var8 + "_";
                }
                else if (this.updateCount / 6 % 2 == 0)
                {
                    var8 = var8 + "\u00a70_";
                }
                else
                {
                    var8 = var8 + "\u00a77_";
                }
            }

            var9 = this.fontRenderer.getStringWidth(var7);
            this.fontRenderer.drawString(var7, var5 - var9 + this.bookImageWidth - 44, var6 + 16, 0);
            this.fontRenderer.drawSplitString(var8, var5 + 36, var6 + 16 + 16, 116, 0);
        }

        super.drawScreen(par1, par2, par3);
    }
}
