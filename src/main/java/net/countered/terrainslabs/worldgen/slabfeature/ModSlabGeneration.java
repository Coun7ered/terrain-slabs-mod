package net.countered.terrainslabs.worldgen.slabfeature;

import net.countered.terrainslabs.worldgen.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModSlabGeneration {
    public static void init(){
        BiomeModifications.addFeature(BiomeSelectors.all(),
                GenerationStep.Feature.UNDERGROUND_STRUCTURES, ModPlacedFeatures.SLAB_FEATURE_PLACED_KEY);
    }
}
