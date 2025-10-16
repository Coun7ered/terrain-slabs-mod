package net.countered.datagen;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.DIRT_SLAB, Ingredient.ofItems(Blocks.DIRT)).criterion("has_dirt_block", conditionsFromItem(Blocks.DIRT)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.MUD_SLAB, Ingredient.ofItems(Blocks.MUD)).criterion("has_mud_block", conditionsFromItem(Blocks.MUD)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.COARSE_SLAB, Ingredient.ofItems(Blocks.COARSE_DIRT)).criterion("has_coarse_dirt_block", conditionsFromItem(Blocks.COARSE_DIRT)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.SNOW_SLAB, Ingredient.ofItems(Blocks.SNOW_BLOCK)).criterion("has_snow_block", conditionsFromItem(Blocks.SNOW_BLOCK)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.PACKED_ICE_SLAB, Ingredient.ofItems(Blocks.PACKED_ICE)).criterion("has_packed_ice_block", conditionsFromItem(Blocks.PACKED_ICE)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.DEEPSLATE_SLAB, Ingredient.ofItems(Blocks.DEEPSLATE)).criterion("has_deepslate_block", conditionsFromItem(Blocks.DEEPSLATE)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.CLAY_SLAB, Ingredient.ofItems(Blocks.CLAY)).criterion("has_clay_block", conditionsFromItem(Blocks.CLAY)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.MOSS_SLAB, Ingredient.ofItems(Blocks.MOSS_BLOCK)).criterion("has_moss_block", conditionsFromItem(Blocks.MOSS_BLOCK)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.GRASS_SLAB, Ingredient.ofItems(Blocks.GRASS_BLOCK)).criterion("has_grass_block", conditionsFromItem(Blocks.GRASS_BLOCK)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.MYCELIUM_SLAB, Ingredient.ofItems(Blocks.MYCELIUM)).criterion("has_mycelium_block", conditionsFromItem(Blocks.MYCELIUM)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.PODZOL_SLAB, Ingredient.ofItems(Blocks.PODZOL)).criterion("has_podzol_block", conditionsFromItem(Blocks.PODZOL)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.PATH_SLAB, Ingredient.ofItems(Blocks.DIRT_PATH)).criterion("has_dirt_path_block", conditionsFromItem(Blocks.DIRT_PATH)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.GRAVEL_SLAB, Ingredient.ofItems(Blocks.GRAVEL)).criterion("has_gravel_block", conditionsFromItem(Blocks.GRAVEL)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.SAND_SLAB, Ingredient.ofItems(Blocks.SAND)).criterion("has_sand_block", conditionsFromItem(Blocks.SAND)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.RED_SAND_SLAB, Ingredient.ofItems(Blocks.RED_SAND)).criterion("has_red_sand_block", conditionsFromItem(Blocks.RED_SAND)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.TERRACOTTA)).criterion("has_terracotta_block", conditionsFromItem(Blocks.TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.RED_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.RED_TERRACOTTA)).criterion("has_red_terracotta_block", conditionsFromItem(Blocks.RED_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.ORANGE_TERRACOTTA)).criterion("has_orange_terracotta_block", conditionsFromItem(Blocks.ORANGE_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.LIGHT_GRAY_TERRACOTTA)).criterion("has_light_gray_terracotta_block", conditionsFromItem(Blocks.LIGHT_GRAY_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.WHITE_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.WHITE_TERRACOTTA)).criterion("has_white_terracotta_block", conditionsFromItem(Blocks.WHITE_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.BROWN_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.BROWN_TERRACOTTA)).criterion("has_brown_terracotta_block", conditionsFromItem(Blocks.BROWN_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.YELLOW_TERRACOTTA)).criterion("has_yellow_terracotta_block", conditionsFromItem(Blocks.YELLOW_TERRACOTTA)).offerTo(exporter);

        //terralith
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.CALCITE_SLAB, Ingredient.ofItems(Blocks.CALCITE)).criterion("has_calcite", conditionsFromItem(Blocks.CALCITE)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.SMOOTH_BASALT_SLAB, Ingredient.ofItems(Blocks.SMOOTH_BASALT)).criterion("has_smooth_basalt", conditionsFromItem(Blocks.SMOOTH_BASALT)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.LIGHT_BLUE_TERRACOTTA)).criterion("has_light_blue_terracotta", conditionsFromItem(Blocks.LIGHT_BLUE_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.CYAN_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.CYAN_TERRACOTTA)).criterion("has_cyan_terracotta", conditionsFromItem(Blocks.CYAN_TERRACOTTA)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.ICE_SLAB, Ingredient.ofItems(Blocks.ICE)).criterion("has_ice", conditionsFromItem(Blocks.ICE)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.ROOTED_DIRT_SLAB, Ingredient.ofItems(Blocks.ROOTED_DIRT)).criterion("has_rooted_dirt", conditionsFromItem(Blocks.ROOTED_DIRT)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.PACKED_MUD_SLAB, Ingredient.ofItems(Blocks.PACKED_MUD)).criterion("has_packed_mud", conditionsFromItem(Blocks.PACKED_MUD)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.BLUE_ICE_SLAB, Ingredient.ofItems(Blocks.BLUE_ICE)).criterion("has_blue_ice", conditionsFromItem(Blocks.BLUE_ICE)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.BLACK_TERRACOTTA_SLAB, Ingredient.ofItems(Blocks.BLACK_TERRACOTTA)).criterion("has_black_terracotta", conditionsFromItem(Blocks.BLACK_TERRACOTTA)).offerTo(exporter);

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.SOUL_SAND_SLAB, Ingredient.ofItems(Blocks.SOUL_SAND)).criterion("has_soul_sand_block", conditionsFromItem(Blocks.SOUL_SAND)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.SOUL_SOIL_SLAB, Ingredient.ofItems(Blocks.SOUL_SOIL)).criterion("has_soul_soil_block", conditionsFromItem(Blocks.SOUL_SOIL)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.NETHERRACK_SLAB, Ingredient.ofItems(Blocks.NETHERRACK)).criterion("has_netherrack_block", conditionsFromItem(Blocks.NETHERRACK)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.WARPED_NYLIUM_SLAB, Ingredient.ofItems(Blocks.WARPED_NYLIUM)).criterion("has_warped_nylium_block", conditionsFromItem(Blocks.WARPED_NYLIUM)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.CRIMSON_NYLIUM_SLAB, Ingredient.ofItems(Blocks.CRIMSON_NYLIUM)).criterion("has_crimson_nylium_block", conditionsFromItem(Blocks.CRIMSON_NYLIUM)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.BASALT_SLAB, Ingredient.ofItems(Blocks.BASALT)).criterion("has_basalt_block", conditionsFromItem(Blocks.BASALT)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocksRegistry.ENDSTONE_SLAB, Ingredient.ofItems(Blocks.END_STONE)).criterion("has_end_stone_block", conditionsFromItem(Blocks.END_STONE)).offerTo(exporter);

    }
}
