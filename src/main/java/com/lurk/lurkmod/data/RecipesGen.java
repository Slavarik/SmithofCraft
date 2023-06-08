package com.lurk.lurkmod.data;

import com.lurk.lurkmod.LurkMod;
import com.lurk.lurkmod.item.ItemSet;
import com.lurk.lurkmod.recipe.ModRecipeBuilderShaped;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class RecipesGen extends RecipeProvider implements IConditionBuilder {
    public RecipesGen(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        for (ItemSet set : ItemSet.sets) {
            oreSmelting(pWriter, List.of(set.oreBlock.get()), RecipeCategory.MISC, set.ingot.get(), 0.7f, 200, set.name + "_ingot");

            //armor
            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.chestplate.get()).define('I', set.ingot.get())
                    .pattern("I I")
                    .pattern("III")
                    .pattern("III")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.helmet.get()).define('I', set.ingot.get())
                    .pattern("III")
                    .pattern("I I")
                    .pattern("   ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.leggings.get()).define('I', set.ingot.get())
                    .pattern("III")
                    .pattern("I I")
                    .pattern("I I")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.boots.get()).define('I', set.ingot.get())
                    .pattern("   ")
                    .pattern("I I")
                    .pattern("I I")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            //tools

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.sword.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern(" I ")
                    .pattern(" I ")
                    .pattern(" S ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.shovel.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern(" I ")
                    .pattern(" S ")
                    .pattern(" S ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.pickaxe.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern("III")
                    .pattern(" S ")
                    .pattern(" S ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.hoe.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern("II ")
                    .pattern(" S ")
                    .pattern(" S ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.axe.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern("II ")
                    .pattern("IS ")
                    .pattern(" S ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.chisel.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern("  I")
                    .pattern(" S ")
                    .pattern("S  ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.file.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern("  I")
                    .pattern(" I ")
                    .pattern("S  ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);

            ModRecipeBuilderShaped.shaped(RecipeCategory.TOOLS, set.hammer.get()).define('I', set.ingot.get()).define('S', Items.STICK)
                    .pattern(" I ")
                    .pattern(" II")
                    .pattern("S  ")
                    .unlockedBy("has_" + set.name + "_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(set.ingot.get()).build()))
                    .save(pWriter);
        }
    }
}
