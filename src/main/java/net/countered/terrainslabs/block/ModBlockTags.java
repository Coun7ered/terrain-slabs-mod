package net.countered.terrainslabs.block;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static net.countered.terrainslabs.TerrainSlabs.MOD_ID;

public class ModBlockTags {
    public static final TagKey<Block> TALL_DECORATIONS =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "tall_decorations"));

    public static final TagKey<Block> SOIL_SLAB_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "soil_slabs"));

    public static final TagKey<Block> REQUIRES_WATER =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "requires_water"));
}
