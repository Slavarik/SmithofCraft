package com.lurk.lurkmod.data;

import com.lurk.lurkmod.LurkMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelsGen extends ItemModelProvider {

    public ItemModelsGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LurkMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(LurkMod.adamantine.ingot);
        simpleItem(LurkMod.adamantine.helmet);
        simpleItem(LurkMod.adamantine.chestplate);
        simpleItem(LurkMod.adamantine.leggings);
        simpleItem(LurkMod.adamantine.boots);

        handheldItem(LurkMod.adamantine.axe);
        handheldItem(LurkMod.adamantine.sword);
        handheldItem(LurkMod.adamantine.hoe);
        handheldItem(LurkMod.adamantine.pickaxe);
        handheldItem(LurkMod.adamantine.shovel);

        handheldItem(LurkMod.adamantine.hammer);
        handheldItem(LurkMod.adamantine.chisel);
        handheldItem(LurkMod.adamantine.file);

        simpleItem(LurkMod.kobolt.ingot);
        simpleItem(LurkMod.kobolt.helmet);
        simpleItem(LurkMod.kobolt.chestplate);
        simpleItem(LurkMod.kobolt.leggings);
        simpleItem(LurkMod.kobolt.boots);

        handheldItem(LurkMod.kobolt.axe);
        handheldItem(LurkMod.kobolt.sword);
        handheldItem(LurkMod.kobolt.hoe);
        handheldItem(LurkMod.kobolt.pickaxe);
        handheldItem(LurkMod.kobolt.shovel);

        handheldItem(LurkMod.kobolt.hammer);
        handheldItem(LurkMod.kobolt.chisel);
        handheldItem(LurkMod.kobolt.file);

        simpleItem(LurkMod.mithrill.ingot);
        simpleItem(LurkMod.mithrill.helmet);
        simpleItem(LurkMod.mithrill.chestplate);
        simpleItem(LurkMod.mithrill.leggings);
        simpleItem(LurkMod.mithrill.boots);

        handheldItem(LurkMod.mithrill.axe);
        handheldItem(LurkMod.mithrill.sword);
        handheldItem(LurkMod.mithrill.hoe);
        handheldItem(LurkMod.mithrill.pickaxe);
        handheldItem(LurkMod.mithrill.shovel);

        handheldItem(LurkMod.mithrill.hammer);
        handheldItem(LurkMod.mithrill.chisel);
        handheldItem(LurkMod.mithrill.file);

        simpleItem(LurkMod.titan.ingot);
        simpleItem(LurkMod.titan.helmet);
        simpleItem(LurkMod.titan.chestplate);
        simpleItem(LurkMod.titan.leggings);
        simpleItem(LurkMod.titan.boots);

        handheldItem(LurkMod.titan.axe);
        handheldItem(LurkMod.titan.sword);
        handheldItem(LurkMod.titan.hoe);
        handheldItem(LurkMod.titan.pickaxe);
        handheldItem(LurkMod.titan.shovel);

        handheldItem(LurkMod.titan.hammer);
        handheldItem(LurkMod.titan.chisel);
        handheldItem(LurkMod.titan.file);
    }

    private ItemModelBuilder simpleItem(RegistryObject<? extends Item> item) {
        System.out.println("registering " + item.getId().toString());
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(LurkMod.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<? extends Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(LurkMod.MODID,"item/" + item.getId().getPath()));
    }
}