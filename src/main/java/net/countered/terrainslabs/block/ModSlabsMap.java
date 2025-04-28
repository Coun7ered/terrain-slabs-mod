package net.countered.terrainslabs.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class ModSlabsMap {
    private static final Map<Block, Block> SLAB_MAP = new HashMap<>();

    static {
        // Register your block-to-slab mappings here
        SLAB_MAP.put(Blocks.STONE, ModBlocksRegistry.CUSTOM_STONE_SLAB);
        SLAB_MAP.put(Blocks.TUFF, ModBlocksRegistry.CUSTOM_TUFF_SLAB);
        SLAB_MAP.put(Blocks.SANDSTONE, ModBlocksRegistry.CUSTOM_SANDSTONE_SLAB);
        SLAB_MAP.put(Blocks.RED_SANDSTONE, ModBlocksRegistry.CUSTOM_RED_SANDSTONE_SLAB);
        SLAB_MAP.put(Blocks.ANDESITE, ModBlocksRegistry.CUSTOM_ANDESITE_SLAB);
        SLAB_MAP.put(Blocks.DIORITE, ModBlocksRegistry.CUSTOM_DIORITE_SLAB);
        SLAB_MAP.put(Blocks.GRANITE, ModBlocksRegistry.CUSTOM_GRANITE_SLAB);

        SLAB_MAP.put(Blocks.GRASS_BLOCK, ModBlocksRegistry.GRASS_SLAB);
        SLAB_MAP.put(Blocks.MYCELIUM, ModBlocksRegistry.MYCELIUM_SLAB);
        SLAB_MAP.put(Blocks.PODZOL, ModBlocksRegistry.PODZOL_SLAB);
        SLAB_MAP.put(Blocks.DIRT_PATH, ModBlocksRegistry.PATH_SLAB);

        SLAB_MAP.put(Blocks.GRAVEL, ModBlocksRegistry.GRAVEL_SLAB);
        SLAB_MAP.put(Blocks.SAND, ModBlocksRegistry.SAND_SLAB);
        SLAB_MAP.put(Blocks.RED_SAND, ModBlocksRegistry.RED_SAND_SLAB);

        SLAB_MAP.put(Blocks.DIRT, ModBlocksRegistry.DIRT_SLAB);
        SLAB_MAP.put(Blocks.MUD, ModBlocksRegistry.MUD_SLAB);
        SLAB_MAP.put(Blocks.COARSE_DIRT, ModBlocksRegistry.COARSE_SLAB);
        SLAB_MAP.put(Blocks.SNOW_BLOCK, ModBlocksRegistry.SNOW_SLAB);
        SLAB_MAP.put(Blocks.PACKED_ICE, ModBlocksRegistry.PACKED_ICE_SLAB);
        SLAB_MAP.put(Blocks.CLAY, ModBlocksRegistry.CLAY_SLAB);
        SLAB_MAP.put(Blocks.DEEPSLATE, ModBlocksRegistry.DEEPSLATE_SLAB);
        SLAB_MAP.put(Blocks.MOSS_BLOCK, ModBlocksRegistry.MOSS_SLAB);

        SLAB_MAP.put(Blocks.TERRACOTTA, ModBlocksRegistry.TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.RED_TERRACOTTA, ModBlocksRegistry.RED_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.ORANGE_TERRACOTTA, ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.LIGHT_GRAY_TERRACOTTA, ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.WHITE_TERRACOTTA, ModBlocksRegistry.WHITE_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.BROWN_TERRACOTTA, ModBlocksRegistry.BROWN_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.YELLOW_TERRACOTTA, ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB);

        SLAB_MAP.put(Blocks.SOUL_SAND, ModBlocksRegistry.SOUL_SAND_SLAB);
        SLAB_MAP.put(Blocks.SOUL_SOIL, ModBlocksRegistry.SOUL_SOIL_SLAB);
        SLAB_MAP.put(Blocks.NETHERRACK, ModBlocksRegistry.NETHERRACK_SLAB);
        SLAB_MAP.put(Blocks.WARPED_NYLIUM, ModBlocksRegistry.WARPED_NYLIUM_SLAB);
        SLAB_MAP.put(Blocks.CRIMSON_NYLIUM, ModBlocksRegistry.CRIMSON_NYLIUM_SLAB);
        SLAB_MAP.put(Blocks.BASALT, ModBlocksRegistry.BASALT_SLAB);
        SLAB_MAP.put(Blocks.BLACKSTONE, ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB);
        SLAB_MAP.put(Blocks.END_STONE, ModBlocksRegistry.ENDSTONE_SLAB);
    }
    public static final Map<Block, Block> ON_TOP_SLAB_BLOCKS_MAP = new HashMap<>();
    static {
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.POPPY, ModBlocksRegistry.POPPY_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.DANDELION, ModBlocksRegistry.DANDELION_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.AZURE_BLUET, ModBlocksRegistry.AZURE_BLUET_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.CORNFLOWER, ModBlocksRegistry.CORNFLOWER_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.DEAD_BUSH, ModBlocksRegistry.DEAD_BUSH_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.BROWN_MUSHROOM, ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.RED_MUSHROOM, ModBlocksRegistry.RED_MUSHROOM_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.SHORT_GRASS, ModBlocksRegistry.SHORT_GRASS_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.FERN, ModBlocksRegistry.FERN_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.SEAGRASS, ModBlocksRegistry.SEAGRASS_ON_TOP);
        ON_TOP_SLAB_BLOCKS_MAP.put(Blocks.SNOW, ModBlocksRegistry.SNOW_ON_TOP);
    }

    public static Block getSlabForBlock(Block blockBelow) {
        return SLAB_MAP.getOrDefault(blockBelow, Blocks.AIR); // Default slab if no match
    }

}
