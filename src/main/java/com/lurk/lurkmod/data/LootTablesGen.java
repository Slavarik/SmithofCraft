package com.lurk.lurkmod.data;

import com.lurk.lurkmod.LurkMod;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;


public class LootTablesGen extends BlockLootSubProvider {


    protected LootTablesGen() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(LurkMod.adamantine.oreBlock.get());
        dropSelf(LurkMod.kobolt.oreBlock.get());
        dropSelf(LurkMod.mithrill.oreBlock.get());
        dropSelf(LurkMod.titan.oreBlock.get());
        dropSelf(LurkMod.smithingTable.get());
        dropSelf(LurkMod.modFurnace.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return LurkMod.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }



}
