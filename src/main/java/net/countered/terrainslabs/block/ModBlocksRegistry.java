package net.countered.terrainslabs.block;

import net.countered.terrainslabs.TerrainSlabs;
import net.countered.terrainslabs.block.customslabs.soilslabs.GrassSlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.PathSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.MyceliumSlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.PodzolSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.MudSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.dimensions.NetherrackSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.dimensions.NyliumSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.dimensions.SoulSandSlab;
import net.countered.terrainslabs.block.ontopofslabs.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class ModBlocksRegistry {

    public static final Block DIRT_SLAB = registerBlock("dirt_slab",
            new CustomSlab(Blocks.DIRT));
    public static final Block MUD_SLAB = registerBlock("mud_slab",
            new MudSlab(Blocks.MUD));
    public static final Block COARSE_SLAB = registerBlock("coarse_slab",
            new CustomSlab(Blocks.COARSE_DIRT));
    public static final Block SNOW_SLAB = registerBlock("snow_slab",
            new CustomSlab(Blocks.SNOW_BLOCK));
    public static final Block PACKED_ICE_SLAB = registerBlock("packed_ice_slab",
            new CustomSlab(Blocks.PACKED_ICE));
    public static final Block DEEPSLATE_SLAB = registerBlock("deepslate_slab",
            new CustomSlab(Blocks.DEEPSLATE));
    public static final Block CLAY_SLAB = registerBlock("clay_slab",
            new CustomSlab(Blocks.CLAY));
    public static final Block MOSS_SLAB = registerBlock("moss_slab",
            new CustomSlab(Blocks.MOSS_BLOCK));
    public static final Block CUSTOM_TUFF_SLAB = registerBlock("terrain_tuff_slab",
            new CustomSlab(Blocks.TUFF));

    public static final Block GRASS_SLAB = registerBlock("grass_slab",
            new GrassSlab(Blocks.GRASS_BLOCK));
    public static final Block MYCELIUM_SLAB = registerBlock("mycelium_slab",
            new MyceliumSlab(Blocks.MYCELIUM));
    public static final Block PODZOL_SLAB = registerBlock("podzol_slab",
            new PodzolSlab(Blocks.PODZOL));
    public static final Block PATH_SLAB = registerBlock("path_slab",
            new PathSlab(Blocks.DIRT_PATH));

    public static final Block GRAVEL_SLAB = registerBlock("gravel_slab",
            new GravityAffectedSlab(Blocks.GRAVEL));
    public static final Block SAND_SLAB = registerBlock("sand_slab",
            new GravityAffectedSlab(Blocks.SAND));
    public static final Block RED_SAND_SLAB = registerBlock("red_sand_slab",
            new GravityAffectedSlab(Blocks.RED_SAND));

    public static final Block TERRACOTTA_SLAB = registerBlock("terracotta_slab",
            new CustomSlab(Blocks.TERRACOTTA));
    public static final Block RED_TERRACOTTA_SLAB = registerBlock("red_terracotta_slab",
            new CustomSlab(Blocks.RED_TERRACOTTA));
    public static final Block ORANGE_TERRACOTTA_SLAB = registerBlock("orange_terracotta_slab",
            new CustomSlab(Blocks.ORANGE_TERRACOTTA));
    public static final Block LIGHT_GRAY_TERRACOTTA_SLAB = registerBlock("light_gray_terracotta_slab",
            new CustomSlab(Blocks.LIGHT_GRAY_TERRACOTTA));
    public static final Block WHITE_TERRACOTTA_SLAB = registerBlock("white_terracotta_slab",
            new CustomSlab(Blocks.WHITE_TERRACOTTA));
    public static final Block BROWN_TERRACOTTA_SLAB = registerBlock("brown_terracotta_slab",
            new CustomSlab(Blocks.BROWN_TERRACOTTA));
    public static final Block YELLOW_TERRACOTTA_SLAB = registerBlock("yellow_terracotta_slab",
            new CustomSlab(Blocks.YELLOW_TERRACOTTA));

    public static final Block CUSTOM_STONE_SLAB = registerBlock("terrain_stone_slab",
            new CustomSlab(Blocks.STONE_SLAB));
    public static final Block CUSTOM_SANDSTONE_SLAB = registerBlock("terrain_sandstone_slab",
            new CustomSlab(Blocks.SANDSTONE_SLAB));
    public static final Block CUSTOM_RED_SANDSTONE_SLAB = registerBlock("terrain_red_sandstone_slab",
            new CustomSlab(Blocks.RED_SANDSTONE_SLAB));
    public static final Block CUSTOM_ANDESITE_SLAB = registerBlock("terrain_andesite_slab",
            new CustomSlab(Blocks.ANDESITE_SLAB));
    public static final Block CUSTOM_DIORITE_SLAB = registerBlock("terrain_diorite_slab",
            new CustomSlab(Blocks.DIORITE_SLAB));
    public static final Block CUSTOM_GRANITE_SLAB = registerBlock("terrain_granite_slab",
            new CustomSlab(Blocks.GRANITE_SLAB));

    public static final Block SOUL_SAND_SLAB = registerBlock("soul_sand_slab",
            new SoulSandSlab(Blocks.SOUL_SAND));
    public static final Block SOUL_SOIL_SLAB = registerBlock("soul_soil_slab",
            new CustomSlab(Blocks.SOUL_SOIL));
    public static final Block NETHERRACK_SLAB = registerBlock("netherrack_slab",
            new NetherrackSlab(Blocks.NETHERRACK));
    public static final Block WARPED_NYLIUM_SLAB = registerBlock("warped_nylium_slab",
            new NyliumSlab(Blocks.WARPED_NYLIUM));
    public static final Block CRIMSON_NYLIUM_SLAB = registerBlock("crimson_nylium_slab",
            new NyliumSlab(Blocks.CRIMSON_NYLIUM));
    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new CustomSlab(Blocks.BASALT));
    public static final Block CUSTOM_BLACKSTONE_SLAB = registerBlock("terrain_blackstone_slab",
            new CustomSlab(Blocks.BLACKSTONE_SLAB));
    public static final Block ENDSTONE_SLAB = registerBlock("endstone_slab",
            new CustomSlab(Blocks.END_STONE));

    //terralith
    public static final Block CALCITE_SLAB = registerBlock("calcite_slab",
            new CustomSlab(Blocks.CALCITE));
    public static final Block SMOOTH_BASALT_SLAB = registerBlock("smooth_basalt_slab",
            new CustomSlab(Blocks.SMOOTH_BASALT));
    public static final Block LIGHT_BLUE_TERRACOTTA_SLAB = registerBlock("light_blue_terracotta_slab",
            new CustomSlab(Blocks.LIGHT_BLUE_TERRACOTTA));
    public static final Block CYAN_TERRACOTTA_SLAB = registerBlock("cyan_terracotta_slab",
            new CustomSlab(Blocks.CYAN_TERRACOTTA));
    public static final Block CUSTOM_COBBLESTONE_SLAB = registerBlock("terrain_cobblestone_slab",
            new CustomSlab(Blocks.COBBLESTONE_SLAB));
    public static final Block CUSTOM_MOSSY_COBBLESTONE_SLAB = registerBlock("terrain_mossy_cobblestone_slab",
            new CustomSlab(Blocks.MOSSY_COBBLESTONE_SLAB));
    public static final Block CUSTOM_COBBLED_DEEPSLATE_SLAB = registerBlock("terrain_cobbled_deepslate_slab",
            new CustomSlab(Blocks.COBBLED_DEEPSLATE_SLAB));
    public static final Block ICE_SLAB = registerBlock("ice_slab",
            new CustomSlab(Blocks.ICE));
    public static final Block ROOTED_DIRT_SLAB = registerBlock("rooted_dirt_slab",
            new CustomSlab(Blocks.ROOTED_DIRT));
    public static final Block PACKED_MUD_SLAB = registerBlock("packed_mud_slab",
            new CustomSlab(Blocks.PACKED_MUD));
    public static final Block BLUE_ICE_SLAB = registerBlock("blue_ice_slab",
            new CustomSlab(Blocks.BLUE_ICE));
    public static final Block BLACK_TERRACOTTA_SLAB = registerBlock("black_terracotta_slab",
            new CustomSlab(Blocks.BLACK_TERRACOTTA));
    public static final Block CUSTOM_PRISMARINE_SLAB = registerBlock("terrain_prismarine_slab",
            new CustomSlab(Blocks.PRISMARINE_SLAB));

    public static final Block SNOW_ON_TOP = registerBlock("snow_on_top",
            new SnowOnTop(Blocks.SNOW));
    public static final Block SEAGRASS_ON_TOP = registerBlock("seagrass_on_top",
            new SeagrassOnTop(Blocks.SEAGRASS));
    public static final Block POPPY_ON_TOP = registerBlock("poppy_on_top",
            new FlowerOnTop(Blocks.POPPY));
    public static final Block DANDELION_ON_TOP = registerBlock("dandelion_on_top",
            new FlowerOnTop(Blocks.DANDELION));
    public static final Block AZURE_BLUET_ON_TOP = registerBlock("azure_bluet_on_top",
            new FlowerOnTop(Blocks.AZURE_BLUET));
    public static final Block CORNFLOWER_ON_TOP = registerBlock("cornflower_on_top",
            new FlowerOnTop(Blocks.CORNFLOWER));
    public static final Block DEAD_BUSH_ON_TOP = registerBlock("dead_bush_on_top",
            new DeadBushOnTop(Blocks.DEAD_BUSH));
    public static final Block BROWN_MUSHROOM_ON_TOP = registerBlock("brown_mushroom_on_top",
            new MushroomOnTop(Blocks.BROWN_MUSHROOM, TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM));
    public static final Block RED_MUSHROOM_ON_TOP = registerBlock("red_mushroom_on_top",
            new MushroomOnTop(Blocks.RED_MUSHROOM, TreeConfiguredFeatures.HUGE_RED_MUSHROOM));
    public static final Block SHORT_GRASS_ON_TOP = registerBlock("short_grass_on_top",
            new GrassOnTop(Blocks.GRASS));
    public static final Block FERN_ON_TOP = registerBlock("fern_on_top",
            new GrassOnTop(Blocks.FERN));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TerrainSlabs.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(TerrainSlabs.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        TerrainSlabs.LOGGER.info("Registering Mod Blocks for " + TerrainSlabs.MOD_ID);
    }
}
