package TFC.Items.Tools;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;
import java.util.HashMap;

public class ItemProPick extends ItemTerra
{
    private class ProspectResult {
        public ItemStack ItemStack;
        public int Count;
        
        public ProspectResult(ItemStack itemStack, int count) {
            ItemStack = itemStack;
            Count = count;
        }
    }
    
    HashMap<String, ProspectResult> results =
            new HashMap<String, ProspectResult>();
    Random random = null;

    public ItemProPick(int i) 
    {
        super(i);
        maxStackSize = 1;
        setCreativeTab(TFCTabs.TFCTools);
    }
    
    @Override
    public void registerIcons(IconRegister registerer)
    {
    	this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/"+this.getUnlocalizedName().replace("item.", ""));
    }
    
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int blockID = world.getBlockId(x, y, z);
        
        // Negated the old condition and exiting the method here instead.
        if (blockID == TFCBlocks.ToolRack.blockID)
            return true;

        // Damage the item on prospecting use.
        if (!world.isRemote) {
            // Don't apply damage in creative mode.
            if (!player.capabilities.isCreativeMode) {
                itemStack.damageItem(1, player);
                if (itemStack.getItemDamage() >= itemStack.getMaxDamage())
                    player.destroyCurrentEquippedItem();
            }
            
            return true;
        }

        // Getting the meta data only when we actually need it.
        int meta = world.getBlockMetadata(x, y, z);
        
        // If an ore block is targeted directly, it'll tell you what it is.
        if (blockID == TFCBlocks.Ore.blockID ||
                blockID == TFCBlocks.Ore2.blockID ||
                blockID == TFCBlocks.Ore3.blockID) {
            
            TellResult(player, new ItemStack(blockID, 1, meta));
            return true;
        }
        
        random = new Random(x * z + y);
        
        // If random(100) is less than 60, it used to succeed. we don't need to
        // gather the blocks in a 25x25 area if it doesn't.
        if (random.nextInt(100) >= 60) {
            player.addChatMessage(StringUtil.localize("gui.ProPick.FoundNothing"));
            return true;
        }
        
        // Check all blocks in the 25x25 area, centered on the targeted block.
        for (int i = -12; i <= 12; i++) {
            for (int j = -12; j <= 12; j++) {
                for(int k = -12; k <= 12; k++) {
                    int blockX = x + i, 
                            blockY = y + j,
                            blockZ = z + k;
                    
                    blockID = world.getBlockId(blockX, blockY, blockZ);
                    
                    if (blockID != TFCBlocks.Ore.blockID &&
                            blockID != TFCBlocks.Ore2.blockID &&
                            blockID != TFCBlocks.Ore3.blockID)
                        continue;
                    
                    meta = world.getBlockMetadata(blockX, blockY, blockZ);
                    
                    if (blockID == TFCBlocks.Ore2.blockID && meta == 6)
                        continue;
                        
                    ItemStack ore = new ItemStack(blockID, 1, meta);
                    String oreName = ore.getDisplayName();
                    
                    if (results.containsKey(oreName))
                        results.get(oreName).Count++;
                    else
                        results.put(oreName, new ProspectResult(ore, 1));
                    
                    ore = null;
                    oreName = null;
                }
            }
        }
        
        // Tell the player what was found.
        TellResult(player);
        
        results.clear();
        random = null;
        
        return true;
    }
    
    /*
     * Tells the player what block of ore he found, when directly targeting an ore block.
     */
    private void TellResult(EntityPlayer player, ItemStack ore) {
        player.addChatMessage(String.format("%s %s", 
                StringUtil.localize("gui.ProPick.Found"), 
                ore.getItem().getItemDisplayName(ore)));
    }
    
    /*
     * Tells the player what ore has been found, randomly picked off the HashMap.
     */
    private void TellResult(EntityPlayer player) {
        if (results == null || results.size() == 0) {
            player.addChatMessage(StringUtil.localize("gui.ProPick.FoundNothing"));
            return;
        }
        
        int index = random.nextInt(results.size());
        ProspectResult result = results.values().toArray(new ProspectResult[0])[index];
        String oreName = result.ItemStack.getItem().getItemDisplayName(result.ItemStack);
        
        if (result.Count < 10)
            player.addChatMessage(String.format("%s %s", StringUtil.localize("gui.ProPick.FoundTraces"), oreName));
        else if(result.Count < 20)
            player.addChatMessage(String.format("%s %s", StringUtil.localize("gui.ProPick.FoundSmall"), oreName));
        else if (result.Count < 40)
            player.addChatMessage(String.format("%s %s", StringUtil.localize("gui.ProPick.FoundMedium"), oreName));
        else if (result.Count < 80)
            player.addChatMessage(String.format("%s %s", StringUtil.localize("gui.ProPick.FoundLarge"), oreName));
        else
            player.addChatMessage(String.format("%s %s", StringUtil.localize("gui.ProPick.FoundVeryLarge"), oreName));
        
        oreName = null;
        result = null;
    }
    
    @Override
    public EnumSize getSize() {
            // TODO Auto-generated method stub
            return EnumSize.SMALL;
    }

    @Override
    public boolean canStack() {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public EnumWeight getWeight() {
            // TODO Auto-generated method stub
            return EnumWeight.LIGHT;
    }
}
