package com.lurk.lurkmod.data;

import com.lurk.lurkmod.LurkMod;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockModelsGen extends BlockStateProvider {

    public BlockModelsGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LurkMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(LurkMod.adamantine.oreBlock);
        blockWithItem(LurkMod.kobolt.oreBlock);
        blockWithItem(LurkMod.mithrill.oreBlock);
        blockWithItem(LurkMod.titan.oreBlock);

    }

    private void blockWithItem(RegistryObject<? extends Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
