package net.countered.terrainslabs.worldgen.slabfeature;

import net.countered.terrainslabs.worldgen.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModSlabGeneration {
    public static void generateSlabs(){
        BiomeModifications.addFeature(BiomeSelectors.all(),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SLAB_FEATURE_PLACED_KEY);
    }
}
