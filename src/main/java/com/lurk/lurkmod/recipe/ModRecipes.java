package com.lurk.lurkmod.recipe;

import com.lurk.lurkmod.LurkMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LurkMod.MODID);

    public static final RegistryObject<RecipeSerializer<ModSmithingRecipe>> MOD_SMITHING_SERIALIZER =
            SERIALIZERS.register("mod_smithing", () -> ModSmithingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
