package net.countered.datagen;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.GRAVEL).slab(ModBlocksRegistry.GRAVEL_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.SAND).slab(ModBlocksRegistry.SAND_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.RED_SAND).slab(ModBlocksRegistry.RED_SAND_SLAB);

        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DIRT).slab(ModBlocksRegistry.DIRT_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.MOSS_BLOCK).slab(ModBlocksRegistry.MOSS_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.MUD).slab(ModBlocksRegistry.MUD_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.PACKED_ICE).slab(ModBlocksRegistry.PACKED_ICE_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.COARSE_DIRT).slab(ModBlocksRegistry.COARSE_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DEEPSLATE).slab(ModBlocksRegistry.DEEPSLATE_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.CLAY).slab(ModBlocksRegistry.CLAY_SLAB);

        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.TERRACOTTA).slab(ModBlocksRegistry.TERRACOTTA_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.BROWN_TERRACOTTA).slab(ModBlocksRegistry.BROWN_TERRACOTTA_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.ORANGE_TERRACOTTA).slab(ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.WHITE_TERRACOTTA).slab(ModBlocksRegistry.WHITE_TERRACOTTA_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.RED_TERRACOTTA).slab(ModBlocksRegistry.RED_TERRACOTTA_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.YELLOW_TERRACOTTA).slab(ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.LIGHT_GRAY_TERRACOTTA).slab(ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB);

        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.SOUL_SAND).slab(ModBlocksRegistry.SOUL_SAND_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.SOUL_SOIL).slab(ModBlocksRegistry.SOUL_SOIL_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.NETHERRACK).slab(ModBlocksRegistry.NETHERRACK_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.BLACKSTONE).slab(ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.END_STONE).slab(ModBlocksRegistry.ENDSTONE_SLAB);

    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}

