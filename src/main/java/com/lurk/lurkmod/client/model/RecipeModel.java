package com.lurk.lurkmod.client.model;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class RecipeModel extends ItemModelProvider {

    public RecipeModel(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public ItemModelBuilder basicItem(ResourceLocation item)
    {
        return getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + "recipe"));
    }

    @Override
    protected void registerModels() {

    }
}
