package net.minecraft.src;

import java.io.File;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.TFC_Core.*;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.General.TFCHeat;
import net.minecraft.src.TFC_Game.*;
import net.minecraft.src.TFC_Mining.*;
import net.minecraft.src.forge.*;

public class mod_TFC_Game extends NetworkMod
{
	static Configuration config;
	public static mod_TFC_Game instance;
	public static IProxy proxy;

	public static int terraFirepitGuiId = 20;
	public static int terraAnvilGuiId = 21;
	public static int terraScribeGuiId = 22;
	public static int terraForgeGuiId = 23;
	public static int terraMetallurgyGuiId = 24;
	public static int terraSluiceGuiId = 25;
	public static int terraBloomeryGuiId = 26;

	public static Block terraFirepit;
	public static Block terraFirepitOn;
	public static Item terraFireStarter;
	public static Block terraBellows;
	public static Block terraAnvil;
	public static Block terraScribe;
	public static Block terraForge;
	public static Block terraForgeOn;
	public static Block terraBloomery;
	public static Block terraBloomeryOn;
	public static Block terraMetalTable;
	public static Block terraMolten;

	public static Item terraBellowsItem;
	public static Item terraStoneAnvilItem;
	public static Item terraBlackSteelAnvilItem;
	public static Item terraBlueSteelAnvilItem;
	public static Item terraBronzeAnvilItem;
	public static Item terraCopperAnvilItem;
	public static Item terraWroughtIronAnvilItem;
	public static Item terraRedSteelAnvilItem;
	public static Item terraSteelAnvilItem;

	public static Item StoneHammer;
	public static Item BismuthHammer;
	public static Item BismuthBronzeHammer;
	public static Item BlackBronzeHammer;
	public static Item BlackSteelHammer;
	public static Item BlueSteelHammer;
	public static Item BronzeHammer;
	public static Item CopperHammer;
	public static Item WroughtIronHammer;
	public static Item RedSteelHammer;
	public static Item RoseGoldHammer;
	public static Item SteelHammer;
	public static Item TinHammer;
	public static Item ZincHammer;

	public static Item terraMeltedBismuth;
	public static Item terraMeltedBismuthBronze;
	public static Item terraMeltedBlackBronze;
	public static Item terraMeltedBlackSteel;
	public static Item terraMeltedHCBlackSteel;
	public static Item terraMeltedBlueSteel;
	public static Item terraMeltedWeakBlueSteel;
	public static Item terraMeltedHCBlueSteel;
	public static Item terraMeltedBrass;
	public static Item terraMeltedBronze;
	public static Item terraMeltedCopper;
	public static Item terraMeltedGold;
	public static Item terraMeltedWroughtIron;
	public static Item terraMeltedLead;
	public static Item terraMeltedNickel;
	public static Item terraMeltedPigIron;
	public static Item terraMeltedPlatinum;
	public static Item terraMeltedRedSteel;
	public static Item terraMeltedWeakRedSteel;
	public static Item terraMeltedHCRedSteel;
	public static Item terraMeltedRoseGold;
	public static Item terraMeltedSilver;
	public static Item terraMeltedSteel;
	public static Item terraMeltedWeakSteel;
	public static Item terraMeltedHCSteel;
	public static Item terraMeltedSterlingSilver;
	public static Item terraMeltedTin;
	public static Item terraMeltedZinc;
	public static Item terraClayMold;
	public static Item terraCeramicMold;
	public static Item terraMeltedUnknown;

	public static Item terraSlag;
	public static Item terraInk;

	//Plans
	public static Item PickaxeHeadPlan;
	public static Item ShovelHeadPlan;
	public static Item HoeHeadPlan;
	public static Item AxeHeadPlan;
	public static Item HammerHeadPlan;
	public static Item ChiselHeadPlan;
	public static Item SwordBladePlan;
	public static Item MaceHeadPlan;
	public static Item SawBladePlan;
	public static Item ProPickBladePlan;

	//Tool Heads
	public static Item BismuthPickaxeHead;
	public static Item BismuthBronzePickaxeHead;
	public static Item BlackBronzePickaxeHead;
	public static Item BlackSteelPickaxeHead;
	public static Item BlueSteelPickaxeHead;
	public static Item BronzePickaxeHead;
	public static Item CopperPickaxeHead;
	public static Item WroughtIronPickaxeHead;
	public static Item RedSteelPickaxeHead;
	public static Item RoseGoldPickaxeHead;
	public static Item SteelPickaxeHead;
	public static Item TinPickaxeHead;
	public static Item ZincPickaxeHead;

	public static Item BismuthShovelHead;
	public static Item BismuthBronzeShovelHead;
	public static Item BlackBronzeShovelHead;
	public static Item BlackSteelShovelHead;
	public static Item BlueSteelShovelHead;
	public static Item BronzeShovelHead;
	public static Item CopperShovelHead;
	public static Item WroughtIronShovelHead;
	public static Item RedSteelShovelHead;
	public static Item RoseGoldShovelHead;
	public static Item SilverShovelHead;
	public static Item SteelShovelHead;
	public static Item TinShovelHead;
	public static Item ZincShovelHead;

	public static Item BismuthHoeHead;
	public static Item BismuthBronzeHoeHead;
	public static Item BlackBronzeHoeHead;
	public static Item BlackSteelHoeHead;
	public static Item BlueSteelHoeHead;
	public static Item BronzeHoeHead;
	public static Item CopperHoeHead;
	public static Item WroughtIronHoeHead;
	public static Item RedSteelHoeHead;
	public static Item RoseGoldHoeHead;
	public static Item SteelHoeHead;
	public static Item TinHoeHead;
	public static Item ZincHoeHead;

	public static Item BismuthAxeHead;
	public static Item BismuthBronzeAxeHead;
	public static Item BlackBronzeAxeHead;
	public static Item BlackSteelAxeHead;
	public static Item BlueSteelAxeHead;
	public static Item BronzeAxeHead;
	public static Item CopperAxeHead;
	public static Item WroughtIronAxeHead;
	public static Item RedSteelAxeHead;
	public static Item RoseGoldAxeHead;
	public static Item SteelAxeHead;
	public static Item TinAxeHead;
	public static Item ZincAxeHead;

	public static Item BismuthHammerHead;
	public static Item BismuthBronzeHammerHead;
	public static Item BlackBronzeHammerHead;
	public static Item BlackSteelHammerHead;
	public static Item BlueSteelHammerHead;
	public static Item BronzeHammerHead;
	public static Item CopperHammerHead;
	public static Item WroughtIronHammerHead;
	public static Item RedSteelHammerHead;
	public static Item RoseGoldHammerHead;
	public static Item SteelHammerHead;
	public static Item TinHammerHead;
	public static Item ZincHammerHead;

	public static Item BismuthChiselHead;
	public static Item BismuthBronzeChiselHead;
	public static Item BlackBronzeChiselHead;
	public static Item BlackSteelChiselHead;
	public static Item BlueSteelChiselHead;
	public static Item BronzeChiselHead;
	public static Item CopperChiselHead;
	public static Item WroughtIronChiselHead;
	public static Item RedSteelChiselHead;
	public static Item RoseGoldChiselHead;
	public static Item SteelChiselHead;
	public static Item TinChiselHead;
	public static Item ZincChiselHead;

	public static Item BismuthSwordHead;
	public static Item BismuthBronzeSwordHead;
	public static Item BlackBronzeSwordHead;
	public static Item BlackSteelSwordHead;
	public static Item BlueSteelSwordHead;
	public static Item BronzeSwordHead;
	public static Item CopperSwordHead;
	public static Item WroughtIronSwordHead;
	public static Item RedSteelSwordHead;
	public static Item RoseGoldSwordHead;
	public static Item SteelSwordHead;
	public static Item TinSwordHead;
	public static Item ZincSwordHead;

	public static Item BismuthMaceHead;
	public static Item BismuthBronzeMaceHead;
	public static Item BlackBronzeMaceHead;
	public static Item BlackSteelMaceHead;
	public static Item BlueSteelMaceHead;
	public static Item BronzeMaceHead;
	public static Item CopperMaceHead;
	public static Item WroughtIronMaceHead;
	public static Item RedSteelMaceHead;
	public static Item RoseGoldMaceHead;
	public static Item SteelMaceHead;
	public static Item TinMaceHead;
	public static Item ZincMaceHead;

	public static Item BismuthSawHead;
	public static Item BismuthBronzeSawHead;
	public static Item BlackBronzeSawHead;
	public static Item BlackSteelSawHead;
	public static Item BlueSteelSawHead;
	public static Item BronzeSawHead;
	public static Item CopperSawHead;
	public static Item WroughtIronSawHead;
	public static Item RedSteelSawHead;
	public static Item RoseGoldSawHead;
	public static Item SteelSawHead;
	public static Item TinSawHead;
	public static Item ZincSawHead;

	public static Item BismuthProPickHead;
	public static Item BismuthBronzeProPickHead;
	public static Item BlackBronzeProPickHead;
	public static Item BlackSteelProPickHead;
	public static Item BlueSteelProPickHead;
	public static Item BronzeProPickHead;
	public static Item CopperProPickHead;
	public static Item WroughtIronProPickHead;
	public static Item RedSteelProPickHead;
	public static Item RoseGoldProPickHead;
	public static Item SteelProPickHead;
	public static Item TinProPickHead;
	public static Item ZincProPickHead;

	public static Item Coke;
	public static Item Flux;

	//Formerly TFC_Mining
	public static Block terraSluice;
	public static Item terraGoldPan;
	public static Item terraSluiceItem;

	public static Item terraProPickStone;
	public static Item terraProPickBismuth;	
	public static Item terraProPickBismuthBronze;	
	public static Item terraProPickBlackBronze;
	public static Item terraProPickBlackSteel;
	public static Item terraProPickBlueSteel;
	public static Item terraProPickBronze;
	public static Item terraProPickCopper;
	public static Item terraProPickIron;
	public static Item terraProPickRedSteel;
	public static Item terraProPickRoseGold;
	public static Item terraProPickSteel;
	public static Item terraProPickTin;
	public static Item terraProPickZinc;

	/**Armor Crafting related items*/
	public static Item MetalSheet;


	

	public static void DamageItem(EntityPlayer entityplayer, IInventory iinventory, int i, int shiftedindex)
	{
		if(iinventory.getStackInSlot(i).itemID == shiftedindex) 
		{
			int index = i;
			ItemStack item = iinventory.getStackInSlot(index).copy();
			if(item != null)
			{
				item.damageItem(1 , entityplayer);
				if (item.getItemDamage() != 0)
				{
					//ContainerTerraWorkbench cw = (ContainerTerraWorkbench)((GuiContainerTFC)guiscreen).inventorySlots;
					iinventory.setInventorySlotContents(index, item);
					iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
					if(iinventory.getStackInSlot(index).stackSize > 2) {
						iinventory.getStackInSlot(index).stackSize = 2;
					}
				}
			}
		}
	}

	public static int getBlockIdFor(String item, int standardID)
	{
		if (config == null)
		{
			return standardID;
		}
		try
		{
			return new Integer(config.getOrCreateIntProperty(item, "Block", standardID).value).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access ID-List, config wasn't loaded properly!").toString());
		}return standardID;
	}

	public static int getGeneralIdFor(String item, int standardID)
	{
		if (config == null)
		{
			return standardID;
		}
		try
		{
			return new Integer(config.getOrCreateIntProperty(item, "General", standardID).value).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access ID-List, config wasn't loaded properly!").toString());
		}return standardID;
	}
	public static int getItemIdFor(String item, int standardID)
	{
		if (config == null)
		{
			return standardID;
		}
		try
		{
			return new Integer(config.getOrCreateIntProperty(item, "Item", standardID).value).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access ID-List, config wasn't loaded properly!").toString());
		}return standardID;
	}
	//this will loop through a list and handle damaging the item in the crafting area if it matches
	public static void HandleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] Items)
	{
		ItemStack item = null;
		for(int i = 0; i < iinventory.getSizeInventory(); i++) 
		{             
			if(iinventory.getStackInSlot(i) == null) 
			{
				continue;
			}
			for(int j = 0; j < Items.length; j++) 
			{  
				DamageItem(entityplayer,iinventory,i,Items[j].shiftedIndex);
			}
		}
	}

	private static void setupCraftHook() 
	{
		ICraftingHandler handler = new ICraftingHandler()
		{
			public void onTakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) 
			{
				int index = 0;

				if(itemstack.itemID == mod_TFC_Game.Flux.shiftedIndex)
				{
					HandleItem(entityplayer, iinventory, TFC_Game.Hammers);
				}
			}
		};

		MinecraftForge.registerCraftingHandler(handler);
	}
	public mod_TFC_Game()
	{
		ModLoader.setInGameHook(this, true, true);
	}
	@Override
	public boolean clientSideRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getVersion()
	{
		return "0.2a";
	}
	
	public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        
        return false;
    }

	@Override
	public void load()
	{
		proxy = ServerClientProxy.getProxy();
		instance = this;
		

		//Register Blocks	
		ModLoader.registerBlock(terraFirepit);
		ModLoader.registerBlock(terraBellows);
		ModLoader.registerBlock(terraAnvil);
		ModLoader.registerBlock(terraScribe);
		ModLoader.registerBlock(terraForge);
		ModLoader.registerBlock(terraMetalTable);
		ModLoader.registerBlock(terraMolten);
		ModLoader.registerBlock(terraBloomery);
		ModLoader.registerBlock(terraSluice);


		MinecraftForge.setGuiHandler(this, proxy);
		MinecraftForge.registerConnectionHandler(new PacketHandler());
		proxy.registerTileEntities();
		proxy.registerRenderInformation();

		setupCraftHook();
	}

	@Override
	public boolean serverSideRequired() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean onTickInGame(MinecraftServer minecraftServer)
    {
	    for (Iterator iterator = minecraftServer.getWorldManager(0).playerManager.players.iterator(); iterator.hasNext();)
	    {
	        EntityPlayer thePlayer = (EntityPlayer)iterator.next();
	        ItemStack[] inv = thePlayer.inventory.mainInventory;
	        double xCoord = thePlayer.posX;
	        double yCoord = thePlayer.posY;
	        double zCoord = thePlayer.posZ;

	        TFCHeat.HandleContainerHeat((World)minecraftServer.getWorldManager(0), inv, (int)xCoord,(int)yCoord,(int)zCoord);
	    }
        return true;
    }
	
	public boolean onTickInGame(float var1, Minecraft mc)
    {
        ItemStack[] inv = ((Minecraft)mc).thePlayer.inventory.mainInventory;
        double xCoord = ((Minecraft)mc).thePlayer.posX;
        double yCoord = ((Minecraft)mc).thePlayer.posY;
        double zCoord = ((Minecraft)mc).thePlayer.posZ;

        TFCHeat.HandleContainerHeat(((Minecraft)mc).theWorld, inv, (int)xCoord,(int)yCoord,(int)zCoord);
        return true;
    }
	
	
	
	static
    {

        try
        {
            config = new Configuration(new File(ServerClientProxy.getProxy().getMinecraftDir(), "/config/TFC.cfg"));
            config.load();
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("[TFC] Error while trying to access configuration!").toString());
            config = null;
        }

        /////////////////////////
        //Blocks
        /////////////////////////
        terraFirepit = new BlockTerraFirepit(getBlockIdFor("terraFirepit", 207), TileEntityTerraFirepit.class, 80).setBlockName("terraFirepit").setHardness(1).setLightValue(0F);
        terraBellows = new BlockTerraBellows(getBlockIdFor("terraBellows", 206),Material.wood).setBlockName("terraBellows").setHardness(2);
        terraForge= new BlockTerraForge(getBlockIdFor("terraForge", 216), TileEntityTerraForge.class, 90).setBlockName("terraForge").setHardness(20).setLightValue(0F);
        terraScribe = new BlockTerraScribe(getBlockIdFor("terraScribe", 204), TileEntityTerraScribe.class).setBlockName("terraScribe").setHardness(2);
        terraAnvil = new BlockTerraAnvil(getBlockIdFor("terraAnvil", 205), TileEntityTerraAnvil.class).setBlockName("terraAnvil").setHardness(3);
        terraMetalTable = new BlockTerraMetallurgy(getBlockIdFor("terraMetallurgy", 218), TileEntityTerraMetallurgy.class).setBlockName("terraMetallurgy").setHardness(3);
        terraMolten = new BlockTerraMolten(getBlockIdFor("terraMolten", 219)).setBlockName("terraMolten").setHardness(20);
        terraBloomery = new BlockTerraBloomery(getBlockIdFor("terraBloomery", 220), TileEntityTerraBloomery.class, 65).setBlockName("terraBloomery").setHardness(20).setLightValue(0F);
        terraBloomeryOn = new BlockTerraBloomery(getBlockIdFor("terraBloomeryOn", 221), TileEntityTerraBloomery.class, 66).setBlockName("terraBloomeryOn").setHardness(20).setLightValue(1.0F);
        terraFirepitOn = new BlockTerraFirepit(getBlockIdFor("terraFirepitOn", 222), TileEntityTerraFirepit.class, 81).setBlockName("terraFirepitOn").setHardness(1).setLightValue(1.0F);
        terraForgeOn = new BlockTerraForge(getBlockIdFor("terraForgeOn", 223), TileEntityTerraForge.class, 91).setBlockName("terraForgeOn").setHardness(20).setLightValue(1.0F);
        /////////////////////////
        //Items
        /////////////////////////


        terraSlag = new ItemTerra(getItemIdFor("terraSlag",16349),"/bioxx/terrasprites.png").setItemName("terraSlag").setIconCoord(2, 0);

        terraMeltedBismuth = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBismuth",16350)).setItemName("terraMeltedBismuth").setIconCoord(0, 9);
        terraMeltedBismuthBronze = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBismuthBronze",16351)).setItemName("terraMeltedBismuthBronze").setIconCoord(1, 9);
        terraMeltedBlackBronze = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBlackBronze",16352)).setItemName("terraMeltedBlackBronze").setIconCoord(2, 9);
        terraMeltedBlackSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBlackSteel",16353)).setItemName("terraMeltedBlackSteel").setIconCoord(3, 9);
        terraMeltedBlueSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBlueSteel",16354)).setItemName("terraMeltedBlueSteel").setIconCoord(4, 9);
        terraMeltedBrass = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBrass",16355)).setItemName("terraMeltedBrass").setIconCoord(5, 9);
        terraMeltedBronze = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedBronze",16356)).setItemName("terraMeltedBronze").setIconCoord(6, 9);
        terraMeltedCopper = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedCopper",16357)).setItemName("terraMeltedCopper").setIconCoord(7, 9);
        terraMeltedGold = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedGold",16358)).setItemName("terraMeltedGold").setIconCoord(8, 9);
        terraMeltedWroughtIron = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedIron",16359)).setItemName("terraMeltedWroughtIron").setIconCoord(9, 9);
        terraMeltedLead = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedLead",16360)).setItemName("terraMeltedLead").setIconCoord(10, 9);
        terraMeltedNickel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedNickel",16361)).setItemName("terraMeltedNickel").setIconCoord(0, 10);
        terraMeltedPigIron = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedPigIron",16362)).setItemName("terraMeltedPigIron").setIconCoord(1, 10);
        terraMeltedPlatinum = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedPlatinum",16363)).setItemName("terraMeltedPlatinum").setIconCoord(2, 10);
        terraMeltedRedSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedRedSteel",16364)).setItemName("terraMeltedRedSteel").setIconCoord(3, 10);
        terraMeltedRoseGold = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedRoseGold",16365)).setItemName("terraMeltedRoseGold").setIconCoord(4, 10);
        terraMeltedSilver = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedSilver",16366)).setItemName("terraMeltedSilver").setIconCoord(5, 10);
        terraMeltedSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedSteel",16367)).setItemName("terraMeltedSteel").setIconCoord(6, 10);
        terraMeltedSterlingSilver = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedSterlingSilver",16368)).setItemName("terraMeltedSterlingSilver").setIconCoord(7, 10);
        terraMeltedTin = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedTin",16369)).setItemName("terraMeltedTin").setIconCoord(8, 10);
        terraMeltedZinc = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedZinc",16370)).setItemName("terraMeltedZinc").setIconCoord(9, 10);

        //Hammers
        StoneHammer = new ItemHammer(getItemIdFor("terraStoneHammer",16371),mod_TFC_Core.IgInToolMaterial).setItemName("Stone Hammer").setIconCoord(0, 11).setMaxDamage(mod_TFC_Core.IgInStoneUses);
        BismuthHammer = new ItemHammer(getItemIdFor("terraBismuthHammer",16372),mod_TFC_Core.BismuthToolMaterial).setItemName("Bismuth Hammer").setIconCoord(1, 11).setMaxDamage(mod_TFC_Core.BismuthUses);
        BismuthBronzeHammer = new ItemHammer(getItemIdFor("terraBismuthBronzeHammer",16373),mod_TFC_Core.BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Hammer").setIconCoord(2, 11).setMaxDamage(mod_TFC_Core.BismuthBronzeUses);
        BlackBronzeHammer = new ItemHammer(getItemIdFor("terraBlackBronzeHammer",16374),mod_TFC_Core.BlackBronzeToolMaterial).setItemName("Black Bronze Hammer").setIconCoord(3, 11).setMaxDamage(mod_TFC_Core.BlackBronzeUses);
        BlackSteelHammer = new ItemHammer(getItemIdFor("terraBlackSteelHammer",16375),mod_TFC_Core.BlackSteelToolMaterial).setItemName("Black Steel Hammer").setIconCoord(4, 11).setMaxDamage(mod_TFC_Core.BlackSteelUses);
        BlueSteelHammer = new ItemHammer(getItemIdFor("terraBlueSteelHammer",16376),mod_TFC_Core.BlueSteelToolMaterial).setItemName("Blue Steel Hammer").setIconCoord(5, 11).setMaxDamage(mod_TFC_Core.BlueSteelUses);
        BronzeHammer = new ItemHammer(getItemIdFor("terraBronzeHammer",16377),mod_TFC_Core.BronzeToolMaterial).setItemName("Bronze Hammer").setIconCoord(6, 11).setMaxDamage(mod_TFC_Core.BronzeUses);
        CopperHammer = new ItemHammer(getItemIdFor("terraCopperHammer",16378),mod_TFC_Core.CopperToolMaterial).setItemName("Copper Hammer").setIconCoord(7, 11).setMaxDamage(mod_TFC_Core.CopperUses);
        WroughtIronHammer = new ItemHammer(getItemIdFor("terraWroughtIronHammer",16379),mod_TFC_Core.IronToolMaterial).setItemName("Wrought Iron Hammer").setIconCoord(8, 11).setMaxDamage(mod_TFC_Core.WroughtIronUses);
        RedSteelHammer = new ItemHammer(getItemIdFor("terraRedSteelHammer",16380),mod_TFC_Core.RedSteelToolMaterial).setItemName("Red Steel Hammer").setIconCoord(9, 11).setMaxDamage(mod_TFC_Core.RedSteelUses);
        RoseGoldHammer = new ItemHammer(getItemIdFor("terraRoseGoldHammer",16381),mod_TFC_Core.RoseGoldToolMaterial).setItemName("Rose Gold Hammer").setIconCoord(10, 11).setMaxDamage(mod_TFC_Core.RoseGoldUses);
        SteelHammer = new ItemHammer(getItemIdFor("terraSteelHammer",16382),mod_TFC_Core.SteelToolMaterial).setItemName("Steel Hammer").setIconCoord(11, 11).setMaxDamage(mod_TFC_Core.SteelUses);
        TinHammer = new ItemHammer(getItemIdFor("terraTinHammer",16383),mod_TFC_Core.TinToolMaterial).setItemName("Tin Hammer").setIconCoord(12, 11).setMaxDamage(mod_TFC_Core.TinUses);
        ZincHammer = new ItemHammer(getItemIdFor("terraZincHammer",16384),mod_TFC_Core.ZincToolMaterial).setItemName("Zinc Hammer").setIconCoord(13, 11).setMaxDamage(mod_TFC_Core.ZincUses);

        terraInk = new ItemTerra(getItemIdFor("terraInk",16391),"/bioxx/terrasprites.png").setItemName("terraInk").setIconCoord(3, 0);

        //Plans
        PickaxeHeadPlan = new ItemTerraMiscTool(getItemIdFor("PickaxeHeadPlan",17000)).setItemName("PickaxeHeadPlan").setIconCoord(0, 0);
        ShovelHeadPlan = new ItemTerraMiscTool(getItemIdFor("ShovelHeadPlan",17001)).setItemName("ShovelHeadPlan").setIconCoord(0, 0);
        HoeHeadPlan = new ItemTerraMiscTool(getItemIdFor("HoeHeadPlan",17002)).setItemName("HoeHeadPlan").setIconCoord(0, 0);
        AxeHeadPlan = new ItemTerraMiscTool(getItemIdFor("AxeHeadPlan",17003)).setItemName("AxeHeadPlan").setIconCoord(0, 0);
        HammerHeadPlan = new ItemTerraMiscTool(getItemIdFor("HammerHeadPlan",17004)).setItemName("HammerHeadPlan").setIconCoord(0, 0);
        ChiselHeadPlan = new ItemTerraMiscTool(getItemIdFor("ChiselHeadPlan",17005)).setItemName("ChiselHeadPlan").setIconCoord(0, 0);
        SwordBladePlan = new ItemTerraMiscTool(getItemIdFor("SwordBladePlan",17006)).setItemName("SwordBladePlan").setIconCoord(0, 0);
        MaceHeadPlan = new ItemTerraMiscTool(getItemIdFor("MaceHeadPlan",17007)).setItemName("MaceHeadPlan").setIconCoord(0, 0);
        SawBladePlan = new ItemTerraMiscTool(getItemIdFor("SawBladePlan",17008)).setItemName("SawBladePlan").setIconCoord(0, 0);
        ProPickBladePlan = new ItemTerraMiscTool(getItemIdFor("ProPickHeadPlan",17009)).setItemName("ProPickHeadPlan").setIconCoord(0, 0);

        //Anvils
        terraStoneAnvilItem = new ItemTerraAnvil(getItemIdFor("terraStoneAnvilItem",16398), 0).setItemName("terraStoneAnvilItem").setIconCoord(0, 2);
        terraBlackSteelAnvilItem = new ItemTerraAnvil(getItemIdFor("terraBlackSteelAnvilItem",16399), 5).setItemName("terraBlackSteelAnvilItem").setIconCoord(4, 2);
        terraBlueSteelAnvilItem = new ItemTerraAnvil(getItemIdFor("terraBlueSteelAnvilItem",16400), 7).setItemName("terraBlueSteelAnvilItem").setIconCoord(5, 2);
        terraBronzeAnvilItem = new ItemTerraAnvil(getItemIdFor("terraBronzeAnvilItem",16401), 2).setItemName("terraBronzeAnvilItem").setIconCoord(6, 2);
        terraCopperAnvilItem = new ItemTerraAnvil(getItemIdFor("terraCopperAnvilItem",16402), 1).setItemName("terraCopperAnvilItem").setIconCoord(7, 2);
        terraWroughtIronAnvilItem = new ItemTerraAnvil(getItemIdFor("terraWroughtIronAnvilItem",16403), 3).setItemName("terraWroughtIronAnvilItem").setIconCoord(8, 2);
        terraRedSteelAnvilItem = new ItemTerraAnvil(getItemIdFor("terraRedSteelAnvilItem",16404), 6).setItemName("terraRedSteelAnvilItem").setIconCoord(9, 2);
        terraSteelAnvilItem = new ItemTerraAnvil(getItemIdFor("terraSteelAnvilItem",16405), 4).setItemName("terraSteelAnvilItem").setIconCoord(11, 2);

        terraBellowsItem = new ItemTerraBellows(getItemIdFor("terraBellowsItem",16406)).setItemName("terraBellowsItem").setIconCoord(8, 0);

        terraFireStarter = new ItemTerraFirestarter(getItemIdFor("terraFireStarter",16407)).setItemName("terraFireStarter").setIconCoord(7, 0);
        terraClayMold = new ItemTerra(getItemIdFor("terraClayMold",16408),"/bioxx/terrasprites.png").setItemName("terraClayMold").setIconCoord(10, 10);
        terraCeramicMold = new ItemTerra(getItemIdFor("terraFiredClayMold",16409),"/bioxx/terrasprites.png").setItemName("terraFiredClayMold").setIconCoord(6, 10);
        //Tool heads
        BismuthPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthPickaxeHead",16500)).setItemName("Bismuth Pickaxe Head").setIconCoord(1, 3);
        BismuthBronzePickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzePickaxeHead",16501)).setItemName("Bismuth Bronze Pickaxe Head").setIconCoord(2, 3);
        BlackBronzePickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzePickaxeHead",16502)).setItemName("Black Bronze Pickaxe Head").setIconCoord(3, 3);
        BlackSteelPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelPickaxeHead",16503)).setItemName("Black Steel Pickaxe Head").setIconCoord(4, 3);
        BlueSteelPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelPickaxeHead",16504)).setItemName("Blue Steel Pickaxe Head").setIconCoord(5, 3);
        BronzePickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("BronzePickaxeHead",16505)).setItemName("Bronze Pickaxe Head").setIconCoord(6, 3);
        CopperPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("CopperPickaxeHead",16506)).setItemName("Copper Pickaxe Head").setIconCoord(7, 3);
        WroughtIronPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronPickaxeHead",16507)).setItemName("Wrought Iron Pickaxe Head").setIconCoord(8, 3);
        RedSteelPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelPickaxeHead",16508)).setItemName("Red Steel Pickaxe Head").setIconCoord(9, 3);
        RoseGoldPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldPickaxeHead",16509)).setItemName("Rose Gold Pickaxe Head").setIconCoord(10, 3);
        SteelPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("SteelPickaxeHead",16510)).setItemName("Steel Pickaxe Head").setIconCoord(11, 3);
        TinPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("TinPickaxeHead",16511)).setItemName("Tin Pickaxe Head").setIconCoord(12, 3);
        ZincPickaxeHead = new ItemTerraMiscToolHead(getItemIdFor("ZincPickaxeHead",16512)).setItemName("Zinc Pickaxe Head").setIconCoord(13, 3);

        BismuthShovelHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthShovelHead",16513)).setItemName("Bismuth Shovel Head").setIconCoord(1, 4);
        BismuthBronzeShovelHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeShovelHead",16514)).setItemName("Bismuth Bronze Shovel Head").setIconCoord(2, 4);
        BlackBronzeShovelHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeShovelHead",16515)).setItemName("Black Bronze Shovel Head").setIconCoord(3, 4);
        BlackSteelShovelHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelShovelHead",16516)).setItemName("Black Steel Shovel Head").setIconCoord(4, 4);
        BlueSteelShovelHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelShovelHead",16517)).setItemName("Blue Steel Shovel Head").setIconCoord(5, 4);
        BronzeShovelHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeShovelHead",16518)).setItemName("Bronze Shovel Head").setIconCoord(6, 4);
        CopperShovelHead = new ItemTerraMiscToolHead(getItemIdFor("CopperShovelHead",16519)).setItemName("Copper Shovel Head").setIconCoord(7, 4);
        WroughtIronShovelHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronShovelHead",16520)).setItemName("Wrought Iron Shovel Head").setIconCoord(8, 4);
        RedSteelShovelHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelShovelHead",16521)).setItemName("Red Steel Shovel Head").setIconCoord(9, 4);
        RoseGoldShovelHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldShovelHead",16522)).setItemName("Rose Gold Shovel Head").setIconCoord(10, 4);
        SteelShovelHead = new ItemTerraMiscToolHead(getItemIdFor("SteelShovelHead",16523)).setItemName("Steel Shovel Head").setIconCoord(11, 4);
        TinShovelHead = new ItemTerraMiscToolHead(getItemIdFor("TinShovelHead",16524)).setItemName("Tin Shovel Head").setIconCoord(12, 4);
        ZincShovelHead = new ItemTerraMiscToolHead(getItemIdFor("ZincShovelHead",16525)).setItemName("Zinc Shovel Head").setIconCoord(13, 4);

        BismuthHoeHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthHoeHead",16526)).setItemName("Bismuth Hoe Head").setIconCoord(1, 6);
        BismuthBronzeHoeHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeHoeHead",16527)).setItemName("Bismuth Bronze Hoe Head").setIconCoord(2, 6);
        BlackBronzeHoeHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeHoeHead",16528)).setItemName("Black Bronze Hoe Head").setIconCoord(3, 6);
        BlackSteelHoeHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelHoeHead",16529)).setItemName("Black Steel Hoe Head").setIconCoord(4, 6);
        BlueSteelHoeHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelHoeHead",16530)).setItemName("Blue Steel Hoe Head").setIconCoord(5, 6);
        BronzeHoeHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeHoeHead",16531)).setItemName("Bronze Hoe Head").setIconCoord(6, 6);
        CopperHoeHead = new ItemTerraMiscToolHead(getItemIdFor("CopperHoeHead",16532)).setItemName("Copper Hoe Head").setIconCoord(7, 6);
        WroughtIronHoeHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronHoeHead",16533)).setItemName("Wrought Iron Hoe Head").setIconCoord(8, 6);
        RedSteelHoeHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelHoeHead",16534)).setItemName("Red Steel Hoe Head").setIconCoord(9, 6);
        RoseGoldHoeHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldHoeHead",16535)).setItemName("Rose Gold Hoe Head").setIconCoord(10, 6);
        SteelHoeHead = new ItemTerraMiscToolHead(getItemIdFor("SteelHoeHead",16536)).setItemName("Steel Hoe Head").setIconCoord(11, 6);
        TinHoeHead = new ItemTerraMiscToolHead(getItemIdFor("TinHoeHead",16537)).setItemName("Tin Hoe Head").setIconCoord(12, 6);
        ZincHoeHead = new ItemTerraMiscToolHead(getItemIdFor("ZincHoeHead",16538)).setItemName("Zinc Hoe Head").setIconCoord(13, 6);

        BismuthAxeHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthAxeHead",16539)).setItemName("Bismuth Axe Head").setIconCoord(1, 5);
        BismuthBronzeAxeHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeAxeHead",16540)).setItemName("Bismuth Bronze Axe Head").setIconCoord(2, 5);
        BlackBronzeAxeHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeAxeHead",16541)).setItemName("Black Bronze Axe Head").setIconCoord(3, 5);
        BlackSteelAxeHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelAxeHead",16542)).setItemName("Black Steel Axe Head").setIconCoord(4, 5);
        BlueSteelAxeHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelAxeHead",16543)).setItemName("Blue Steel Axe Head").setIconCoord(5, 5);
        BronzeAxeHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeAxeHead",16544)).setItemName("Bronze Axe Head").setIconCoord(6, 5);
        CopperAxeHead = new ItemTerraMiscToolHead(getItemIdFor("CopperAxeHead",16545)).setItemName("Copper Axe Head").setIconCoord(7, 5);
        WroughtIronAxeHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronAxeHead",16546)).setItemName("Wrought Iron Axe Head").setIconCoord(8, 5);
        RedSteelAxeHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelAxeHead",16547)).setItemName("Red Steel Axe Head").setIconCoord(9, 5);
        RoseGoldAxeHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldAxeHead",16548)).setItemName("Rose Gold Axe Head").setIconCoord(10, 5);
        SteelAxeHead = new ItemTerraMiscToolHead(getItemIdFor("SteelAxeHead",16549)).setItemName("Steel Axe Head").setIconCoord(11, 5);
        TinAxeHead = new ItemTerraMiscToolHead(getItemIdFor("TinAxeHead",16550)).setItemName("Tin Axe Head").setIconCoord(12, 5);
        ZincAxeHead = new ItemTerraMiscToolHead(getItemIdFor("ZincAxeHead",16551)).setItemName("Zinc Axe Head").setIconCoord(13, 5);

        BismuthHammerHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthHammerHead",16552)).setItemName("Bismuth Hammer Head").setIconCoord(1, 11);
        BismuthBronzeHammerHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeHammerHead",16553)).setItemName("Bismuth Bronze Hammer Head").setIconCoord(2, 11);
        BlackBronzeHammerHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeHammerHead",16554)).setItemName("Black Bronze Hammer Head").setIconCoord(3, 11);
        BlackSteelHammerHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelHammerHead",16555)).setItemName("Black Steel Hammer Head").setIconCoord(4, 11);
        BlueSteelHammerHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelHammerHead",16556)).setItemName("Blue Steel Hammer Head").setIconCoord(5, 11);
        BronzeHammerHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeHammerHead",16557)).setItemName("Bronze Hammer Head").setIconCoord(6, 11);
        CopperHammerHead = new ItemTerraMiscToolHead(getItemIdFor("CopperHammerHead",16558)).setItemName("Copper Hammer Head").setIconCoord(7, 11);
        WroughtIronHammerHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronHammerHead",16559)).setItemName("Wrought Iron Hammer Head").setIconCoord(8, 11);
        RedSteelHammerHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelHammerHead",16560)).setItemName("Red Steel Hammer Head").setIconCoord(9, 11);
        RoseGoldHammerHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldHammerHead",16561)).setItemName("Rose Gold Hammer Head").setIconCoord(10, 11);
        SteelHammerHead = new ItemTerraMiscToolHead(getItemIdFor("SteelHammerHead",16562)).setItemName("Steel Hammer Head").setIconCoord(11, 11);
        TinHammerHead = new ItemTerraMiscToolHead(getItemIdFor("TinHammerHead",16563)).setItemName("Tin Hammer Head").setIconCoord(12, 11);
        ZincHammerHead = new ItemTerraMiscToolHead(getItemIdFor("ZincHammerHead",16564)).setItemName("Zinc Hammer Head").setIconCoord(13, 11);

        //chisel heads
        BismuthChiselHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthChiselHead",16565)).setItemName("Bismuth Chisel Head").setIconCoord(1, 7);
        BismuthBronzeChiselHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeChiselHead",16566)).setItemName("Bismuth Bronze Chisel Head").setIconCoord(2, 7);
        BlackBronzeChiselHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeChiselHead",16567)).setItemName("Black Bronze Chisel Head").setIconCoord(3, 7);
        BlackSteelChiselHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelChiselHead",16568)).setItemName("Black Steel Chisel Head").setIconCoord(4, 7);
        BlueSteelChiselHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelChiselHead",16569)).setItemName("Blue Steel Chisel Head").setIconCoord(5, 7);
        BronzeChiselHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeChiselHead",16570)).setItemName("Bronze Chisel Head").setIconCoord(6, 7);
        CopperChiselHead = new ItemTerraMiscToolHead(getItemIdFor("CopperChiselHead",16571)).setItemName("Copper Chisel Head").setIconCoord(7, 7);
        WroughtIronChiselHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronChiselHead",16572)).setItemName("Wrought Iron Chisel Head").setIconCoord(8, 7);
        RedSteelChiselHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelChiselHead",16573)).setItemName("Red Steel Chisel Head").setIconCoord(9, 7);
        RoseGoldChiselHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldChiselHead",16574)).setItemName("Rose Gold Chisel Head").setIconCoord(10, 7);
        SteelChiselHead = new ItemTerraMiscToolHead(getItemIdFor("SteelChiselHead",16575)).setItemName("Steel Chisel Head").setIconCoord(11, 7);
        TinChiselHead = new ItemTerraMiscToolHead(getItemIdFor("TinChiselHead",16576)).setItemName("Tin Chisel Head").setIconCoord(12, 7);
        ZincChiselHead = new ItemTerraMiscToolHead(getItemIdFor("ZincChiselHead",16577)).setItemName("Zinc Chisel Head").setIconCoord(13, 7);

        BismuthSwordHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthSwordHead",16578)).setItemName("Bismuth Sword Blade").setIconCoord(1, 13);
        BismuthBronzeSwordHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeSwordHead",16579)).setItemName("Bismuth Bronze Sword Blade").setIconCoord(2, 13);
        BlackBronzeSwordHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeSwordHead",16580)).setItemName("Black Bronze Sword Blade").setIconCoord(3, 13);
        BlackSteelSwordHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelSwordHead",16581)).setItemName("Black Steel Sword Blade").setIconCoord(4, 13);
        BlueSteelSwordHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelSwordHead",16582)).setItemName("Blue Steel Sword Blade").setIconCoord(5, 13);
        BronzeSwordHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeSwordHead",16583)).setItemName("Bronze Sword Blade").setIconCoord(6, 13);
        CopperSwordHead = new ItemTerraMiscToolHead(getItemIdFor("CopperSwordHead",16584)).setItemName("Copper Sword Blade").setIconCoord(7, 13);
        WroughtIronSwordHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronSwordHead",16585)).setItemName("Wrought Iron Sword Blade").setIconCoord(8, 13);
        RedSteelSwordHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelSwordHead",16586)).setItemName("Red Steel Sword Blade").setIconCoord(9, 13);
        RoseGoldSwordHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldSwordHead",16587)).setItemName("Rose Gold Sword Blade").setIconCoord(10, 13);
        SteelSwordHead = new ItemTerraMiscToolHead(getItemIdFor("SteelSwordHead",16588)).setItemName("Steel Sword Blade").setIconCoord(11, 13);
        TinSwordHead = new ItemTerraMiscToolHead(getItemIdFor("TinSwordHead",16589)).setItemName("Tin Sword Blade").setIconCoord(12, 13);
        ZincSwordHead = new ItemTerraMiscToolHead(getItemIdFor("ZincSwordHead",16590)).setItemName("Zinc Sword Blade").setIconCoord(13, 13);

        BismuthMaceHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthMaceHead",16591)).setItemName("Bismuth Mace Head").setIconCoord(1, 12);
        BismuthBronzeMaceHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeMaceHead",16592)).setItemName("Bismuth Bronze Mace Head").setIconCoord(2, 12);
        BlackBronzeMaceHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeMaceHead",16593)).setItemName("Black Bronze Mace Head").setIconCoord(3, 12);
        BlackSteelMaceHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelMaceHead",16594)).setItemName("Black Steel Mace Head").setIconCoord(4, 12);
        BlueSteelMaceHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelMaceHead",16595)).setItemName("Blue Steel Mace Head").setIconCoord(5, 12);
        BronzeMaceHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeMaceHead",16596)).setItemName("Bronze Mace Head").setIconCoord(6, 12);
        CopperMaceHead = new ItemTerraMiscToolHead(getItemIdFor("CopperMaceHead",16597)).setItemName("Copper Mace Head").setIconCoord(7, 12);
        WroughtIronMaceHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronMaceHead",16598)).setItemName("Wrought Iron Mace Head").setIconCoord(8, 12);
        RedSteelMaceHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelMaceHead",16599)).setItemName("Red Steel Mace Head").setIconCoord(9, 12);
        RoseGoldMaceHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldMaceHead",16600)).setItemName("Rose Gold Mace Head").setIconCoord(10, 12);
        SteelMaceHead = new ItemTerraMiscToolHead(getItemIdFor("SteelMaceHead",16601)).setItemName("Steel Mace Head").setIconCoord(11, 12);
        TinMaceHead = new ItemTerraMiscToolHead(getItemIdFor("TinMaceHead",16602)).setItemName("Tin Mace Head").setIconCoord(12, 12);
        ZincMaceHead = new ItemTerraMiscToolHead(getItemIdFor("ZincMaceHead",16603)).setItemName("Zinc Mace Head").setIconCoord(13, 12);

        BismuthSawHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthSawHead",16604)).setItemName("Bismuth Saw Blade").setIconCoord(1, 8);
        BismuthBronzeSawHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeSawHead",16605)).setItemName("Bismuth Bronze Saw Blade").setIconCoord(2, 8);
        BlackBronzeSawHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeSawHead",16606)).setItemName("Black Bronze Saw Blade").setIconCoord(3, 8);
        BlackSteelSawHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelSawHead",16607)).setItemName("Black Steel Saw Blade").setIconCoord(4, 8);
        BlueSteelSawHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelSawHead",16608)).setItemName("Blue Steel Saw Blade").setIconCoord(5, 8);
        BronzeSawHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeSawHead",16609)).setItemName("Bronze Saw Blade").setIconCoord(6, 8);
        CopperSawHead = new ItemTerraMiscToolHead(getItemIdFor("CopperSawHead",16610)).setItemName("Copper Saw Blade").setIconCoord(7, 8);
        WroughtIronSawHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronSawHead",16611)).setItemName("Wrought Iron Saw Blade").setIconCoord(8, 8);
        RedSteelSawHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelSawHead",16612)).setItemName("Red Steel Saw Blade").setIconCoord(9, 8);
        RoseGoldSawHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldSawHead",16613)).setItemName("Rose Gold Saw Blade").setIconCoord(10, 8);
        SteelSawHead = new ItemTerraMiscToolHead(getItemIdFor("SteelSawHead",16614)).setItemName("Steel Saw Blade").setIconCoord(11, 8);
        TinSawHead = new ItemTerraMiscToolHead(getItemIdFor("TinSawHead",16615)).setItemName("Tin Saw Blade").setIconCoord(12, 8);
        ZincSawHead = new ItemTerraMiscToolHead(getItemIdFor("ZincSawHead",16616)).setItemName("Zinc Saw Blade").setIconCoord(13, 8);

        terraMeltedHCBlackSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedHCBlackSteel",16617)).setItemName("terraMeltedHCBlackSteel").setIconCoord(3, 9);
        terraMeltedWeakBlueSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedWeakBlueSteel",16618)).setItemName("terraMeltedWeakBlueSteel").setIconCoord(4, 9);
        terraMeltedHCBlueSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedHCBlueSteel",16619)).setItemName("terraMeltedHCBlueSteel").setIconCoord(4, 9);
        terraMeltedWeakRedSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedWeakRedSteel",16621)).setItemName("terraMeltedWeakRedSteel").setIconCoord(3, 10);
        terraMeltedHCRedSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedHCRedSteel",16622)).setItemName("terraMeltedHCRedSteel").setIconCoord(3, 10);
        terraMeltedWeakSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedWeakSteel",16623)).setItemName("terraMeltedWeakSteel").setIconCoord(6, 10);
        terraMeltedHCSteel = new ItemTerraMeltedMetal(getItemIdFor("terraMeltedHCSteel",16624)).setItemName("terraMeltedHCSteel").setIconCoord(6, 10);
        Coke = ((ItemTerra) new ItemTerra(getItemIdFor("Coke",16625)).setItemName("coke").setIconCoord(2, 0)).setTexturePath("/bioxx/terrasprites.png");

        BismuthProPickHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthProPickHead",16626)).setItemName("Bismuth ProPick Head").setIconCoord(1, 1);
        BismuthBronzeProPickHead = new ItemTerraMiscToolHead(getItemIdFor("BismuthBronzeProPickHead",16627)).setItemName("Bismuth Bronze ProPick Head").setIconCoord(2, 1);
        BlackBronzeProPickHead = new ItemTerraMiscToolHead(getItemIdFor("BlackBronzeProPickHead",16628)).setItemName("Black Bronze ProPick Head").setIconCoord(3, 1);
        BlackSteelProPickHead = new ItemTerraMiscToolHead(getItemIdFor("BlackSteelProPickHead",16629)).setItemName("Black Steel ProPick Head").setIconCoord(4, 1);
        BlueSteelProPickHead = new ItemTerraMiscToolHead(getItemIdFor("BlueSteelProPickHead",16630)).setItemName("Blue Steel ProPick Head").setIconCoord(5, 1);
        BronzeProPickHead = new ItemTerraMiscToolHead(getItemIdFor("BronzeProPickHead",16631)).setItemName("Bronze ProPick Head").setIconCoord(6, 1);
        CopperProPickHead = new ItemTerraMiscToolHead(getItemIdFor("CopperProPickHead",16632)).setItemName("Copper ProPick Head").setIconCoord(7, 1);
        WroughtIronProPickHead = new ItemTerraMiscToolHead(getItemIdFor("WroughtIronProPickHead",16633)).setItemName("Wrought Iron ProPick Head").setIconCoord(8, 1);
        RedSteelProPickHead = new ItemTerraMiscToolHead(getItemIdFor("RedSteelProPickHead",16634)).setItemName("Red Steel ProPick Head").setIconCoord(9, 1);
        RoseGoldProPickHead = new ItemTerraMiscToolHead(getItemIdFor("RoseGoldProPickHead",16635)).setItemName("Rose Gold ProPick Head").setIconCoord(10, 1);
        SteelProPickHead = new ItemTerraMiscToolHead(getItemIdFor("SteelProPickHead",16636)).setItemName("Steel ProPick Head").setIconCoord(11, 1);
        TinProPickHead = new ItemTerraMiscToolHead(getItemIdFor("TinProPickHead",16637)).setItemName("Tin ProPick Head").setIconCoord(12, 1);
        ZincProPickHead = new ItemTerraMiscToolHead(getItemIdFor("ZincProPickHead",16638)).setItemName("Zinc ProPick Head").setIconCoord(13, 1);

        Flux = ((ItemTerra) new ItemTerra(getItemIdFor("Flux",16639)).setItemName("flux").setIconCoord(0, 0)).setTexturePath("/bioxx/terrasprites.png");
        /**Armor Crafting related items*/



        /**Formerly TFC_Mining*/
        terraSluice = new BlockTerraSluice(getBlockIdFor("TerraSluice", 217), TileEntityTerraSluice.class).setBlockName("Sluice").setHardness(2F).setResistance(20F);

        terraGoldPan = new ItemTerraGoldPan(getItemIdFor("terraGoldPan",16001)).setItemName("GoldPan").setIconCoord(1, 0);
        terraSluiceItem = new ItemTerraSluice(getItemIdFor("terraSluiceItem",16002)).setItemName("SluiceItem").setIconCoord(6, 0);
        terraProPickStone = new ItemTerraProPick(getItemIdFor("terraProPickStone",16003)).setItemName("StoneProPick").setIconCoord(0, 1).setMaxDamage(64);
        terraProPickBismuth = new ItemTerraProPick(getItemIdFor("terraProPickBismuth",16004)).setItemName("BismuthProPick").setIconCoord(1, 1).setMaxDamage(128);
        terraProPickBismuthBronze = new ItemTerraProPick(getItemIdFor("terraProPickBismuthBronze",16005)).setItemName("BismuthBronzeProPick").setIconCoord(2, 1).setMaxDamage(180);
        terraProPickBlackBronze = new ItemTerraProPick(getItemIdFor("terraProPickBlackBronze",16006)).setItemName("BlackBronzeProPick").setIconCoord(3, 1).setMaxDamage(220);
        terraProPickBlackSteel = new ItemTerraProPick(getItemIdFor("terraProPickBlackSteel",16007)).setItemName("BlackSteelProPick").setIconCoord(4, 1).setMaxDamage(1024);
        terraProPickBlueSteel = new ItemTerraProPick(getItemIdFor("terraProPickBlueSteel",16008)).setItemName("BlueSteelProPick").setIconCoord(5, 1).setMaxDamage(1800);
        terraProPickBronze = new ItemTerraProPick(getItemIdFor("terraProPickBronze",16009)).setItemName("BronzeProPick").setIconCoord(6, 1).setMaxDamage(180);
        terraProPickCopper = new ItemTerraProPick(getItemIdFor("terraProPickCopper",16010)).setItemName("CopperProPick").setIconCoord(7, 1).setMaxDamage(180);
        terraProPickIron = new ItemTerraProPick(getItemIdFor("terraProPickWroughtIron",16012)).setItemName("WroughtIronProPick").setIconCoord(8, 1).setMaxDamage(256);
        terraProPickRedSteel = new ItemTerraProPick(getItemIdFor("terraProPickRedSteel",16016)).setItemName("RedSteelProPick").setIconCoord(9, 1).setMaxDamage(1800);
        terraProPickRoseGold = new ItemTerraProPick(getItemIdFor("terraProPickRoseGold",16017)).setItemName("RoseGoldProPick").setIconCoord(10, 1).setMaxDamage(190);
        terraProPickSteel = new ItemTerraProPick(getItemIdFor("terraProPickSteel",16019)).setItemName("SteelProPick").setIconCoord(11, 1).setMaxDamage(768);
        terraProPickTin = new ItemTerraProPick(getItemIdFor("terraProPickTin",16021)).setItemName("TinProPick").setIconCoord(12, 1).setMaxDamage(96);
        terraProPickZinc = new ItemTerraProPick(getItemIdFor("terraProPickZinc",16022)).setItemName("ZincProPick").setIconCoord(13, 1).setMaxDamage(160);
        TFC_Game.registerRecipes();

        MinecraftForge.registerOreHandler(new IOreHandler() 
        {
            public void registerOre(String oreClass, ItemStack ore) 
            {

            }
        });

        if (config != null) {
            config.save();
        }

    }
}
