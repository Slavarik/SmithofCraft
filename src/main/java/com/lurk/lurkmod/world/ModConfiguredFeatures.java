package com.lurk.lurkmod.world;

import com.lurk.lurkmod.LurkMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ADAMANTINE_ORE_KEY = registerKey("adamantine_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KOBOLT_ORE_KEY = registerKey("kobolt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MITHRILL_ORE_KEY = registerKey("mithrill_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITAN_ORE_KEY = registerKey("titan_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest DeepSlateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        register(context, ADAMANTINE_ORE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables,
                LurkMod.adamantine.oreBlock.get().defaultBlockState(), 9));

        register(context, KOBOLT_ORE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables,
                LurkMod.kobolt.oreBlock.get().defaultBlockState(), 9));

        register(context, MITHRILL_ORE_KEY, Feature.ORE, new OreConfiguration(DeepSlateReplaceables,
                LurkMod.mithrill.oreBlock.get().defaultBlockState(), 9));

        register(context, TITAN_ORE_KEY, Feature.ORE, new OreConfiguration(DeepSlateReplaceables,
                LurkMod.titan.oreBlock.get().defaultBlockState(), 9));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(LurkMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}