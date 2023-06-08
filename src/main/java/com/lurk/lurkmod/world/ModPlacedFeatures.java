package com.lurk.lurkmod.world;

import com.lurk.lurkmod.LurkMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ADAMANTINE_PLACED_KEY = createKey("adamantine_placed");
    public static final ResourceKey<PlacedFeature> MITHRIL_PLACED_KEY = createKey("mithrill_placed");
    public static final ResourceKey<PlacedFeature> KOBOLT_PLACED_KEY = createKey("kobolt_placed");
    public static final ResourceKey<PlacedFeature> TITAN_PLACED_KEY = createKey("titan_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, ADAMANTINE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ADAMANTINE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, // veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(80))));
        register(context, MITHRIL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MITHRILL_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, // veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(80))));
        register(context, KOBOLT_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.KOBOLT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(16, // veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(80))));
        register(context, TITAN_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TITAN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4, // veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(80))));
    }


    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LurkMod.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}