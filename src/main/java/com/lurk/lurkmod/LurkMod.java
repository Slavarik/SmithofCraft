package com.lurk.lurkmod;

import com.lurk.lurkmod.block.ModFurnace;
import com.lurk.lurkmod.block.SmithingTable;
import com.lurk.lurkmod.client.gui.ModFurnaceGUI;
import com.lurk.lurkmod.client.gui.ModSmithingGUI;
import com.lurk.lurkmod.item.ItemSet;
import com.lurk.lurkmod.recipe.ModRecipes;
import com.lurk.lurkmod.registries.ModBlockEntities;
import com.lurk.lurkmod.registries.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LurkMod.MODID)
public class LurkMod {
    // Define mod id in a common place for everything to reference
    public static Items items;
    public static final String MODID = "lurkmod";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path

    public static final RegistryObject<Block> smithingTable = BLOCKS.register("smithing_table", () -> new SmithingTable(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Item> smithingTableItem = ITEMS.register("smithing_table", () -> new BlockItem(smithingTable.get(), new Item.Properties()));

    public static final RegistryObject<Block> modFurnace = BLOCKS.register("mod_furnace", () -> new ModFurnace(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Item> modFurnaceItem = ITEMS.register("mod_furnace", () -> new BlockItem(modFurnace.get(), new Item.Properties()));
    //Precious stones
    public final RegistryObject<Item> topaz = ITEMS.register("topaz",() -> new Item(new Item.Properties()));
    public final RegistryObject<Item> ruby = ITEMS.register("ruby",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> topazRing = ITEMS.register("topaz_ring",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> rubyRing = ITEMS.register("ruby_ring",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> diamondRing = ITEMS.register("diamond_ring",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> emeraldRing = ITEMS.register("emerald_ring",() -> new Item(new Item.Properties()));

    public static ItemSet adamantine = new ItemSet("adamantine", 1, new int[]{2,3,4,2});

    public static ItemSet kobolt = new ItemSet("kobolt", 0, new int[]{1,2,3,1});

    public static ItemSet mithrill = new ItemSet("mithrill", 2, new int[]{3,4,5,3});

    public static ItemSet titan = new ItemSet("titan", 3, new int[]{4,5,6,4});

    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);




    public ArrayList<RegistryObject<Item>>recipes = new ArrayList<>();

    public LurkMod() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        FEATURES.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ITEMS.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(smithingTableItem);
            event.accept(modFurnace);
        }
        if (event.getTab() == CreativeModeTabs.COMBAT) {
           adamantine.accept(event);
        }
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            kobolt.accept(event);
        }
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            mithrill.accept(event);
        }
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            titan.accept(event);
        }
        if (event.getTab() == CreativeModeTabs.INGREDIENTS){
            event.accept(ruby);
            event.accept(topaz);
            event.accept(rubyRing);
            event.accept(diamondRing);
            event.accept(emeraldRing);
            event.accept(topazRing);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            MenuScreens.register(ModMenuTypes.MENU_TYPE_SMITHING.get(), ModSmithingGUI::new);
            MenuScreens.register(ModMenuTypes.MENU_TYPE_FURNACE.get(), ModFurnaceGUI::new);
        }
    }

}
