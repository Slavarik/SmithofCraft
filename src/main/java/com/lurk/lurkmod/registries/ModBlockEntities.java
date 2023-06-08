package com.lurk.lurkmod.registries;

import com.lurk.lurkmod.LurkMod;
import com.lurk.lurkmod.block.ModFurnace;
import com.lurk.lurkmod.entity.ModFurnaceEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LurkMod.MODID);

    public static final RegistryObject<BlockEntityType<ModFurnaceEntity>> GEM_CUTTING_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("gem_cutting_station_block_entity", () -> BlockEntityType.Builder.of(ModFurnaceEntity::new, LurkMod.modFurnace.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}