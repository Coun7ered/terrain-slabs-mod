package net.countered.terrainslabs.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ModSlabsMap {
    public static final Map<Block, Block> SLAB_MAP = new HashMap<>();

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
        //terralith
        SLAB_MAP.put(Blocks.CALCITE, ModBlocksRegistry.CALCITE_SLAB);
        SLAB_MAP.put(Blocks.SMOOTH_BASALT, ModBlocksRegistry.SMOOTH_BASALT_SLAB);
        SLAB_MAP.put(Blocks.LIGHT_BLUE_TERRACOTTA, ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.CYAN_TERRACOTTA, ModBlocksRegistry.CYAN_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.COBBLESTONE, ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB);
        SLAB_MAP.put(Blocks.MOSSY_COBBLESTONE, ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB);
        SLAB_MAP.put(Blocks.COBBLED_DEEPSLATE, ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB);
        SLAB_MAP.put(Blocks.ICE, ModBlocksRegistry.ICE_SLAB);
        SLAB_MAP.put(Blocks.ROOTED_DIRT, ModBlocksRegistry.ROOTED_DIRT_SLAB);
        SLAB_MAP.put(Blocks.PACKED_MUD, ModBlocksRegistry.PACKED_MUD_SLAB);
        SLAB_MAP.put(Blocks.BLUE_ICE, ModBlocksRegistry.BLUE_ICE_SLAB);
        SLAB_MAP.put(Blocks.BLACK_TERRACOTTA, ModBlocksRegistry.BLACK_TERRACOTTA_SLAB);
        SLAB_MAP.put(Blocks.PRISMARINE, ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB);

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
    public static final Map<Block, Block> ON_TOP_VEGETATION_BLOCKS_MAP = new HashMap<>();

    static {
        // Register your block-to-slab mappings here
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.POPPY, ModBlocksRegistry.POPPY_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.DANDELION, ModBlocksRegistry.DANDELION_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.AZURE_BLUET, ModBlocksRegistry.AZURE_BLUET_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.CORNFLOWER, ModBlocksRegistry.CORNFLOWER_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.DEAD_BUSH, ModBlocksRegistry.DEAD_BUSH_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.BROWN_MUSHROOM, ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.RED_MUSHROOM, ModBlocksRegistry.RED_MUSHROOM_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.GRASS, ModBlocksRegistry.SHORT_GRASS_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.FERN, ModBlocksRegistry.FERN_ON_TOP);
        ON_TOP_VEGETATION_BLOCKS_MAP.put(Blocks.SEAGRASS, ModBlocksRegistry.SEAGRASS_ON_TOP);
    }
    public static final Map<Block, Block> BLOCK_BELOW_REPLACEMENT_MAP = new HashMap<>();

    static {
        BLOCK_BELOW_REPLACEMENT_MAP.put(ModBlocksRegistry.GRASS_SLAB, Blocks.DIRT);
        BLOCK_BELOW_REPLACEMENT_MAP.put(ModBlocksRegistry.PODZOL_SLAB, Blocks.DIRT);
        BLOCK_BELOW_REPLACEMENT_MAP.put(ModBlocksRegistry.MYCELIUM_SLAB, Blocks.DIRT);
        BLOCK_BELOW_REPLACEMENT_MAP.put(ModBlocksRegistry.PATH_SLAB, Blocks.DIRT);
        BLOCK_BELOW_REPLACEMENT_MAP.put(ModBlocksRegistry.WARPED_NYLIUM_SLAB, Blocks.NETHERRACK);
        BLOCK_BELOW_REPLACEMENT_MAP.put(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB, Blocks.NETHERRACK);
    }
    public static final Map<Block, Block> TOP_SLAB_REPLACEMENT_MAP = new HashMap<>();

    static {
        TOP_SLAB_REPLACEMENT_MAP.put(ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.DIRT_SLAB);
        TOP_SLAB_REPLACEMENT_MAP.put(ModBlocksRegistry.PODZOL_SLAB, ModBlocksRegistry.DIRT_SLAB);
        TOP_SLAB_REPLACEMENT_MAP.put(ModBlocksRegistry.MYCELIUM_SLAB, ModBlocksRegistry.DIRT_SLAB);
        TOP_SLAB_REPLACEMENT_MAP.put(ModBlocksRegistry.PATH_SLAB, ModBlocksRegistry.DIRT_SLAB);
        TOP_SLAB_REPLACEMENT_MAP.put(ModBlocksRegistry.WARPED_NYLIUM_SLAB, ModBlocksRegistry.NETHERRACK_SLAB);
        TOP_SLAB_REPLACEMENT_MAP.put(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB, ModBlocksRegistry.NETHERRACK_SLAB);
    }

    public static Block getSlabForBlock(Block blockBelow) {
        return SLAB_MAP.getOrDefault(blockBelow, Blocks.AIR); // Default slab if no match
    }

    // Utilities to allow forge compats to cross the platform barrier with a common java object
    public static void putOnTopVegetationFromString( String keyMod, String keyName, String valueMod, String valueName ) {
        putWithId( ON_TOP_VEGETATION_BLOCKS_MAP, keyMod, keyName, valueMod, valueName );
    }
    public static void putTerrainSlabFromString( String keyMod, String keyName, String valueMod, String valueName ) {
        putWithId( SLAB_MAP, keyMod, keyName, valueMod, valueName );
    }
    public static void putBlockBelowReplacementFromString( String keyMod, String keyName, String valueMod, String valueName ) {
        putWithId( BLOCK_BELOW_REPLACEMENT_MAP, keyMod, keyName, valueMod, valueName );
    }
    public static void putTopSlabReplacementFromString( String keyMod, String keyName, String valueMod, String valueName ) {
        putWithId( TOP_SLAB_REPLACEMENT_MAP, keyMod, keyName, valueMod, valueName );
    }

    private static void putWithId( Map<Block, Block> map, String keyMod, String keyName, String valueMod, String valueName ) {
        Block block1 = Registries.BLOCK.get( Identifier.of( keyMod, keyName ) );
        Block block2 = Registries.BLOCK.get( Identifier.of( valueMod, valueName ) );
        map.put( block1, block2 );
    }

}
