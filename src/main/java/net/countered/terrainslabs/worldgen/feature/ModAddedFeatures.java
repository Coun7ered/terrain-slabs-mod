package net.countered.terrainslabs.worldgen.feature;

import net.countered.terrainslabs.TerrainSlabs;
import net.countered.terrainslabs.worldgen.slabfeature.SlabFeatureLogic;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ModAddedFeatures {
    // Register your custom SlabFeature
    public static final Feature<DefaultFeatureConfig> SLAB_FEATURE = new SlabFeatureLogic(DefaultFeatureConfig.CODEC);

    public static void init() {
        // Register the slab feature in the feature registry
        Registry.register(Registries.FEATURE, Identifier.of(TerrainSlabs.MOD_ID, "slab_feature"), SLAB_FEATURE);
    }
}
