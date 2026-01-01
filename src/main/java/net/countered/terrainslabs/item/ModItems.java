package net.countered.terrainslabs.item;

import net.countered.terrainslabs.TerrainSlabs;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item DIRT_SLAB_ITEM = registerBlockItem("dirt_slab", ModBlocksRegistry.DIRT_SLAB);
    public static final Item MUD_SLAB_ITEM = registerBlockItem("mud_slab", ModBlocksRegistry.MUD_SLAB);
    public static final Item COARSE_SLAB_ITEM = registerBlockItem("coarse_slab", ModBlocksRegistry.COARSE_SLAB);
    public static final Item SNOW_SLAB_ITEM = registerBlockItem("snow_slab", ModBlocksRegistry.SNOW_SLAB);
    public static final Item PACKED_ICE_SLAB_ITEM = registerBlockItem("packed_ice_slab", ModBlocksRegistry.PACKED_ICE_SLAB);
    public static final Item DEEPSLATE_SLAB_ITEM = registerBlockItem("deepslate_slab", ModBlocksRegistry.DEEPSLATE_SLAB);
    public static final Item CLAY_SLAB_ITEM = registerBlockItem("clay_slab", ModBlocksRegistry.CLAY_SLAB);
    public static final Item MOSS_SLAB_ITEM = registerBlockItem("moss_slab", ModBlocksRegistry.MOSS_SLAB);

    public static final Item GRASS_SLAB_ITEM = registerBlockItem("grass_slab", ModBlocksRegistry.GRASS_SLAB);
    public static final Item MYCELIUM_SLAB_ITEM = registerBlockItem("mycelium_slab", ModBlocksRegistry.MYCELIUM_SLAB);
    public static final Item PODZOL_SLAB_ITEM = registerBlockItem("podzol_slab", ModBlocksRegistry.PODZOL_SLAB);
    public static final Item PATH_SLAB_ITEM = registerBlockItem("path_slab", ModBlocksRegistry.PATH_SLAB);

    public static final Item GRAVEL_SLAB_ITEM = registerBlockItem("gravel_slab", ModBlocksRegistry.GRAVEL_SLAB);
    public static final Item SAND_SLAB_ITEM = registerBlockItem("sand_slab", ModBlocksRegistry.SAND_SLAB);
    public static final Item RED_SAND_SLAB_ITEM = registerBlockItem("red_sand_slab", ModBlocksRegistry.RED_SAND_SLAB);

    public static final Item TERRACOTTA_SLAB_ITEM = registerBlockItem("terracotta_slab", ModBlocksRegistry.TERRACOTTA_SLAB);
    public static final Item RED_TERRACOTTA_SLAB_ITEM = registerBlockItem("red_terracotta_slab", ModBlocksRegistry.RED_TERRACOTTA_SLAB);
    public static final Item ORANGE_TERRACOTTA_SLAB_ITEM = registerBlockItem("orange_terracotta_slab", ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB);
    public static final Item LIGHT_GRAY_TERRACOTTA_SLAB_ITEM = registerBlockItem("light_gray_terracotta_slab", ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB);
    public static final Item WHITE_TERRACOTTA_SLAB_ITEM = registerBlockItem("white_terracotta_slab", ModBlocksRegistry.WHITE_TERRACOTTA_SLAB);
    public static final Item BROWN_TERRACOTTA_SLAB_ITEM = registerBlockItem("brown_terracotta_slab", ModBlocksRegistry.BROWN_TERRACOTTA_SLAB);
    public static final Item YELLOW_TERRACOTTA_SLAB_ITEM = registerBlockItem("yellow_terracotta_slab", ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB);

    public static final Item CUSTOM_STONE_SLAB_ITEM = registerBlockItem("terrain_stone_slab", ModBlocksRegistry.CUSTOM_STONE_SLAB);
    public static final Item CUSTOM_TUFF_SLAB_ITEM = registerBlockItem("terrain_tuff_slab", ModBlocksRegistry.CUSTOM_TUFF_SLAB);
    public static final Item CUSTOM_SANDSTONE_SLAB_ITEM = registerBlockItem("terrain_sandstone_slab", ModBlocksRegistry.CUSTOM_SANDSTONE_SLAB);
    public static final Item CUSTOM_RED_SANDSTONE_SLAB_ITEM = registerBlockItem("terrain_red_sandstone_slab", ModBlocksRegistry.CUSTOM_RED_SANDSTONE_SLAB);
    public static final Item CUSTOM_ANDESITE_SLAB_ITEM = registerBlockItem("terrain_andesite_slab", ModBlocksRegistry.CUSTOM_ANDESITE_SLAB);
    public static final Item CUSTOM_DIORITE_SLAB_ITEM = registerBlockItem("terrain_diorite_slab", ModBlocksRegistry.CUSTOM_DIORITE_SLAB);
    public static final Item CUSTOM_GRANITE_SLAB_ITEM = registerBlockItem("terrain_granite_slab", ModBlocksRegistry.CUSTOM_GRANITE_SLAB);

    public static final Item SOUL_SAND_SLAB_ITEM = registerBlockItem("soul_sand_slab", ModBlocksRegistry.SOUL_SAND_SLAB);
    public static final Item SOUL_SOIL_SLAB_ITEM = registerBlockItem("soul_soil_slab", ModBlocksRegistry.SOUL_SOIL_SLAB);
    public static final Item NETHERRACK_SLAB_ITEM = registerBlockItem("netherrack_slab", ModBlocksRegistry.NETHERRACK_SLAB);
    public static final Item WARPED_NYLIUM_SLAB_ITEM = registerBlockItem("warped_nylium_slab", ModBlocksRegistry.WARPED_NYLIUM_SLAB);
    public static final Item CRIMSON_NYLIUM_SLAB_ITEM = registerBlockItem("crimson_nylium_slab", ModBlocksRegistry.CRIMSON_NYLIUM_SLAB);
    public static final Item BASALT_SLAB_ITEM = registerBlockItem("basalt_slab", ModBlocksRegistry.BASALT_SLAB);
    public static final Item CUSTOM_BLACKSTONE_SLAB_ITEM = registerBlockItem("terrain_blackstone_slab", ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB);
    public static final Item ENDSTONE_SLAB_ITEM = registerBlockItem("endstone_slab", ModBlocksRegistry.ENDSTONE_SLAB);

    // terralith
    public static final Item CALCITE_SLAB_ITEM = registerBlockItem("calcite_slab", ModBlocksRegistry.CALCITE_SLAB);
    public static final Item SMOOTH_BASALT_SLAB_ITEM = registerBlockItem("smooth_basalt_slab", ModBlocksRegistry.SMOOTH_BASALT_SLAB);
    public static final Item LIGHT_BLUE_TERRACOTTA_SLAB_ITEM = registerBlockItem("light_blue_terracotta_slab", ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB);
    public static final Item CYAN_TERRACOTTA_SLAB_ITEM = registerBlockItem("cyan_terracotta_slab", ModBlocksRegistry.CYAN_TERRACOTTA_SLAB);
    public static final Item CUSTOM_COBBLESTONE_SLAB_ITEM = registerBlockItem("terrain_cobblestone_slab", ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB);
    public static final Item CUSTOM_MOSSY_COBBLESTONE_SLAB_ITEM = registerBlockItem("terrain_mossy_cobblestone_slab", ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB);
    public static final Item CUSTOM_COBBLED_DEEPSLATE_SLAB_ITEM = registerBlockItem("terrain_cobbled_deepslate_slab", ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB);
    public static final Item ICE_SLAB_ITEM = registerBlockItem("ice_slab", ModBlocksRegistry.ICE_SLAB);
    public static final Item ROOTED_DIRT_SLAB_ITEM = registerBlockItem("rooted_dirt_slab", ModBlocksRegistry.ROOTED_DIRT_SLAB);
    public static final Item PACKED_MUD_SLAB_ITEM = registerBlockItem("packed_mud_slab", ModBlocksRegistry.PACKED_MUD_SLAB);
    public static final Item BLUE_ICE_SLAB_ITEM = registerBlockItem("blue_ice_slab", ModBlocksRegistry.BLUE_ICE_SLAB);
    public static final Item BLACK_TERRACOTTA_SLAB_ITEM = registerBlockItem("black_terracotta_slab", ModBlocksRegistry.BLACK_TERRACOTTA_SLAB);
    public static final Item CUSTOM_PRISMARINE_SLAB_ITEM = registerBlockItem("terrain_prismarine_slab", ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB);

    public static final Item SNOW_ON_TOP_ITEM = registerBlockItem("snow_on_top", ModBlocksRegistry.SNOW_ON_TOP);
    public static final Item SEAGRASS_ON_TOP_ITEM = registerBlockItem("seagrass_on_top", ModBlocksRegistry.SEAGRASS_ON_TOP);
    public static final Item POPPY_ON_TOP_ITEM = registerBlockItem("poppy_on_top", ModBlocksRegistry.POPPY_ON_TOP);
    public static final Item DANDELION_ON_TOP_ITEM = registerBlockItem("dandelion_on_top", ModBlocksRegistry.DANDELION_ON_TOP);
    public static final Item AZURE_BLUET_ON_TOP_ITEM = registerBlockItem("azure_bluet_on_top", ModBlocksRegistry.AZURE_BLUET_ON_TOP);
    public static final Item CORNFLOWER_ON_TOP_ITEM = registerBlockItem("cornflower_on_top", ModBlocksRegistry.CORNFLOWER_ON_TOP);
    public static final Item DEAD_BUSH_ON_TOP_ITEM = registerBlockItem("dead_bush_on_top", ModBlocksRegistry.DEAD_BUSH_ON_TOP);
    public static final Item BROWN_MUSHROOM_ON_TOP_ITEM = registerBlockItem("brown_mushroom_on_top", ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP);
    public static final Item RED_MUSHROOM_ON_TOP_ITEM = registerBlockItem("red_mushroom_on_top", ModBlocksRegistry.RED_MUSHROOM_ON_TOP);
    public static final Item SHORT_GRASS_ON_TOP_ITEM = registerBlockItem("short_grass_on_top", ModBlocksRegistry.SHORT_GRASS_ON_TOP);
    public static final Item FERN_ON_TOP_ITEM = registerBlockItem("fern_on_top", ModBlocksRegistry.FERN_ON_TOP);

    private static Item registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of(TerrainSlabs.MOD_ID, name);
        return Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id)).useBlockPrefixedTranslationKey())
        );
    }

    public static void init() {
        TerrainSlabs.LOGGER.info("Registering Mod items for " + TerrainSlabs.MOD_ID);
    }
}
