package com.lurk.lurkmod.registries;

import com.lurk.lurkmod.LurkMod;
import com.lurk.lurkmod.common.menus.ModFurnaceMenu;
import com.lurk.lurkmod.common.menus.ModSmithingMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, LurkMod.MODID);

    public static final RegistryObject<MenuType<ModSmithingMenu>> MENU_TYPE_SMITHING =
            registerMenuType(ModSmithingMenu::new, "smithing_menu");

    public static final RegistryObject<MenuType<ModFurnaceMenu>> MENU_TYPE_FURNACE =
            registerMenuType(ModFurnaceMenu::new, "furnace_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}