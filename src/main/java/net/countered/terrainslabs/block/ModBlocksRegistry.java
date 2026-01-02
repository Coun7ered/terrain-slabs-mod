package net.countered.terrainslabs.block;

import net.countered.terrainslabs.TerrainSlabs;
import net.countered.terrainslabs.block.customslabs.soilslabs.GrassSlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.MyceliumSlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.PathSlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.PodzolSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.MudSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.dimensions.NetherrackSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.dimensions.NyliumSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.dimensions.SoulSandSlab;
import net.countered.terrainslabs.block.ontopofslabs.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.function.Function;

public class ModBlocksRegistry {

    public static final Block DIRT_SLAB = registerBlock("dirt_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.DIRT).registryKey(key)));
    public static final Block MUD_SLAB = registerBlock("mud_slab",
            key -> new MudSlab(AbstractBlock.Settings.copy(Blocks.MUD).registryKey(key).blockVision(Blocks::never)));
    public static final Block COARSE_SLAB = registerBlock("coarse_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.COARSE_DIRT).registryKey(key)));
    public static final Block SNOW_SLAB = registerBlock("snow_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.SNOW_BLOCK).registryKey(key)));
    public static final Block PACKED_ICE_SLAB = registerBlock("packed_ice_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.PACKED_ICE).registryKey(key)));
    public static final Block DEEPSLATE_SLAB = registerBlock("deepslate_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).registryKey(key)));
    public static final Block CLAY_SLAB = registerBlock("clay_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.CLAY).registryKey(key)));
    public static final Block MOSS_SLAB = registerBlock("moss_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.MOSS_BLOCK).registryKey(key)));

    public static final Block GRASS_SLAB = registerBlock("grass_slab",
            key -> new GrassSlab(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).registryKey(key)));
    public static final Block MYCELIUM_SLAB = registerBlock("mycelium_slab",
            key -> new MyceliumSlab(AbstractBlock.Settings.copy(Blocks.MYCELIUM).registryKey(key)));
    public static final Block PODZOL_SLAB = registerBlock("podzol_slab",
            key -> new PodzolSlab(AbstractBlock.Settings.copy(Blocks.PODZOL).registryKey(key)));
    public static final Block PATH_SLAB = registerBlock("path_slab",
            key -> new PathSlab(AbstractBlock.Settings.copy(Blocks.DIRT_PATH).registryKey(key).blockVision(Blocks::never)));

    public static final Block GRAVEL_SLAB = registerBlock("gravel_slab",
            key -> new GravityAffectedSlab(AbstractBlock.Settings.copy(Blocks.GRAVEL).registryKey(key)));
    public static final Block SAND_SLAB = registerBlock("sand_slab",
            key -> new GravityAffectedSlab(AbstractBlock.Settings.copy(Blocks.SAND).registryKey(key)));
    public static final Block RED_SAND_SLAB = registerBlock("red_sand_slab",
            key -> new GravityAffectedSlab(AbstractBlock.Settings.copy(Blocks.RED_SAND).registryKey(key)));

    public static final Block TERRACOTTA_SLAB = registerBlock("terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.TERRACOTTA).registryKey(key)));
    public static final Block RED_TERRACOTTA_SLAB = registerBlock("red_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.RED_TERRACOTTA).registryKey(key)));
    public static final Block ORANGE_TERRACOTTA_SLAB = registerBlock("orange_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.ORANGE_TERRACOTTA).registryKey(key)));
    public static final Block LIGHT_GRAY_TERRACOTTA_SLAB = registerBlock("light_gray_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_TERRACOTTA).registryKey(key)));
    public static final Block WHITE_TERRACOTTA_SLAB = registerBlock("white_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.WHITE_TERRACOTTA).registryKey(key)));
    public static final Block BROWN_TERRACOTTA_SLAB = registerBlock("brown_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.BROWN_TERRACOTTA).registryKey(key)));
    public static final Block YELLOW_TERRACOTTA_SLAB = registerBlock("yellow_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.YELLOW_TERRACOTTA).registryKey(key)));

    public static final Block CUSTOM_STONE_SLAB = registerBlock("terrain_stone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.STONE_SLAB).registryKey(key)));
    public static final Block CUSTOM_TUFF_SLAB = registerBlock("terrain_tuff_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.TUFF_SLAB).registryKey(key)));
    public static final Block CUSTOM_SANDSTONE_SLAB = registerBlock("terrain_sandstone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.SANDSTONE_SLAB).registryKey(key)));
    public static final Block CUSTOM_RED_SANDSTONE_SLAB = registerBlock("terrain_red_sandstone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.RED_SANDSTONE_SLAB).registryKey(key)));
    public static final Block CUSTOM_ANDESITE_SLAB = registerBlock("terrain_andesite_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.ANDESITE_SLAB).registryKey(key)));
    public static final Block CUSTOM_DIORITE_SLAB = registerBlock("terrain_diorite_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.DIORITE_SLAB).registryKey(key)));
    public static final Block CUSTOM_GRANITE_SLAB = registerBlock("terrain_granite_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.GRANITE_SLAB).registryKey(key)));

    public static final Block SOUL_SAND_SLAB = registerBlock("soul_sand_slab",
            key -> new SoulSandSlab(AbstractBlock.Settings.copy(Blocks.SOUL_SAND).registryKey(key).blockVision(Blocks::never)));
    public static final Block SOUL_SOIL_SLAB = registerBlock("soul_soil_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.SOUL_SOIL).registryKey(key)));
    public static final Block NETHERRACK_SLAB = registerBlock("netherrack_slab",
            key -> new NetherrackSlab(AbstractBlock.Settings.copy(Blocks.NETHERRACK).registryKey(key)));
    public static final Block WARPED_NYLIUM_SLAB = registerBlock("warped_nylium_slab",
            key -> new NyliumSlab(AbstractBlock.Settings.copy(Blocks.WARPED_NYLIUM).registryKey(key)));
    public static final Block CRIMSON_NYLIUM_SLAB = registerBlock("crimson_nylium_slab",
            key -> new NyliumSlab(AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM).registryKey(key)));
    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.BASALT).registryKey(key)));
    public static final Block CUSTOM_BLACKSTONE_SLAB = registerBlock("terrain_blackstone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.BLACKSTONE_SLAB).registryKey(key)));
    public static final Block ENDSTONE_SLAB = registerBlock("endstone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.END_STONE).registryKey(key)));

    //terralith
    public static final Block CALCITE_SLAB = registerBlock("calcite_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.CALCITE).registryKey(key)));
    public static final Block SMOOTH_BASALT_SLAB = registerBlock("smooth_basalt_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.SMOOTH_BASALT).registryKey(key)));
    public static final Block LIGHT_BLUE_TERRACOTTA_SLAB = registerBlock("light_blue_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_TERRACOTTA).registryKey(key)));
    public static final Block CYAN_TERRACOTTA_SLAB = registerBlock("cyan_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.CYAN_TERRACOTTA).registryKey(key)));
    public static final Block CUSTOM_COBBLESTONE_SLAB = registerBlock("terrain_cobblestone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.COBBLESTONE_SLAB).registryKey(key)));
    public static final Block CUSTOM_MOSSY_COBBLESTONE_SLAB = registerBlock("terrain_mossy_cobblestone_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.MOSSY_COBBLESTONE_SLAB).registryKey(key)));
    public static final Block CUSTOM_COBBLED_DEEPSLATE_SLAB = registerBlock("terrain_cobbled_deepslate_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.COBBLED_DEEPSLATE_SLAB).registryKey(key)));
    public static final Block ICE_SLAB = registerBlock("ice_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.ICE).registryKey(key)));
    public static final Block ROOTED_DIRT_SLAB = registerBlock("rooted_dirt_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.ROOTED_DIRT).registryKey(key)));
    public static final Block PACKED_MUD_SLAB = registerBlock("packed_mud_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.PACKED_MUD).registryKey(key)));
    public static final Block BLUE_ICE_SLAB = registerBlock("blue_ice_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.BLUE_ICE).registryKey(key)));
    public static final Block BLACK_TERRACOTTA_SLAB = registerBlock("black_terracotta_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.BLACK_TERRACOTTA).registryKey(key)));
    public static final Block CUSTOM_PRISMARINE_SLAB = registerBlock("terrain_prismarine_slab",
            key -> new CustomSlab(AbstractBlock.Settings.copy(Blocks.PRISMARINE_SLAB).registryKey(key)));

    public static final Block SNOW_ON_TOP = registerBlock("snow_on_top",
            key -> new SnowOnTop(AbstractBlock.Settings.copy(Blocks.SNOW).registryKey(key)));
    public static final Block SEAGRASS_ON_TOP = registerBlock("seagrass_on_top",
            key -> new SeagrassOnTop(AbstractBlock.Settings.copy(Blocks.SEAGRASS).registryKey(key)));
    public static final Block POPPY_ON_TOP = registerBlock("poppy_on_top",
            key -> new FlowerOnTop(StatusEffects.NIGHT_VISION, 5.0F, AbstractBlock.Settings.copy(Blocks.POPPY).registryKey(key)));
    public static final Block DANDELION_ON_TOP = registerBlock("dandelion_on_top",
            key -> new FlowerOnTop(StatusEffects.SATURATION, 0.35F, AbstractBlock.Settings.copy(Blocks.DANDELION).registryKey(key)));
    public static final Block AZURE_BLUET_ON_TOP = registerBlock("azure_bluet_on_top",
            key -> new FlowerOnTop(StatusEffects.BLINDNESS, 8.0F, AbstractBlock.Settings.copy(Blocks.AZURE_BLUET).registryKey(key)));
    public static final Block CORNFLOWER_ON_TOP = registerBlock("cornflower_on_top",
            key -> new FlowerOnTop(StatusEffects.JUMP_BOOST, 6.0F, AbstractBlock.Settings.copy(Blocks.CORNFLOWER).registryKey(key)));
    public static final Block DEAD_BUSH_ON_TOP = registerBlock("dead_bush_on_top",
            key -> new DeadBushOnTop(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH).registryKey(key)));
    public static final Block BROWN_MUSHROOM_ON_TOP = registerBlock("brown_mushroom_on_top",
            key -> new MushroomOnTop(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).registryKey(key)));
    public static final Block RED_MUSHROOM_ON_TOP = registerBlock("red_mushroom_on_top",
            key -> new MushroomOnTop(TreeConfiguredFeatures.HUGE_RED_MUSHROOM, AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM).registryKey(key)));
    public static final Block SHORT_GRASS_ON_TOP = registerBlock("short_grass_on_top",
            key -> new GrassOnTop(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).registryKey(key)));
    public static final Block FERN_ON_TOP = registerBlock("fern_on_top",
            key -> new GrassOnTop(AbstractBlock.Settings.copy(Blocks.FERN).registryKey(key)));

    private static Block registerBlock(
            String name,
            Function<RegistryKey<Block>, Block> factory
    ) {
        Identifier id = Identifier.of(TerrainSlabs.MOD_ID, name);
        RegistryKey<Block> blockKey =
                RegistryKey.of(RegistryKeys.BLOCK, id);

        Block block = factory.apply(blockKey);

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void init() {
        TerrainSlabs.LOGGER.info("Registering Mod Blocks for " + TerrainSlabs.MOD_ID);
    }
}
