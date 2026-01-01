package net.countered.datagen;


import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;

import static net.minecraft.client.data.BlockStateModelGenerator.createSlabBlockState;
import static net.minecraft.client.data.BlockStateModelGenerator.createWeightedVariant;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        generateSlabModel(blockStateModelGenerator, Blocks.GRAVEL, ModBlocksRegistry.GRAVEL_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.SAND, ModBlocksRegistry.SAND_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.RED_SAND, ModBlocksRegistry.RED_SAND_SLAB);

        generateSlabModel(blockStateModelGenerator, Blocks.DIRT, ModBlocksRegistry.DIRT_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.MOSS_BLOCK, ModBlocksRegistry.MOSS_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.MUD, ModBlocksRegistry.MUD_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.PACKED_ICE, ModBlocksRegistry.PACKED_ICE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.COARSE_DIRT, ModBlocksRegistry.COARSE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.DEEPSLATE, ModBlocksRegistry.DEEPSLATE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.CLAY, ModBlocksRegistry.CLAY_SLAB);

        // terralith
        generateSlabModel(blockStateModelGenerator, Blocks.CALCITE, ModBlocksRegistry.CALCITE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.SMOOTH_BASALT, ModBlocksRegistry.SMOOTH_BASALT_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.LIGHT_BLUE_TERRACOTTA, ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.CYAN_TERRACOTTA, ModBlocksRegistry.CYAN_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.ICE, ModBlocksRegistry.ICE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.COBBLESTONE, ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.MOSSY_COBBLESTONE, ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.COBBLED_DEEPSLATE, ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.ROOTED_DIRT, ModBlocksRegistry.ROOTED_DIRT_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.PACKED_MUD, ModBlocksRegistry.PACKED_MUD_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.BLUE_ICE, ModBlocksRegistry.BLUE_ICE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.BLACK_TERRACOTTA, ModBlocksRegistry.BLACK_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.PRISMARINE, ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB);

        generateSlabModel(blockStateModelGenerator, Blocks.TERRACOTTA, ModBlocksRegistry.TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.BROWN_TERRACOTTA, ModBlocksRegistry.BROWN_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.ORANGE_TERRACOTTA, ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.WHITE_TERRACOTTA, ModBlocksRegistry.WHITE_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.RED_TERRACOTTA, ModBlocksRegistry.RED_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.YELLOW_TERRACOTTA, ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.LIGHT_GRAY_TERRACOTTA, ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB);

        generateSlabModel(blockStateModelGenerator, Blocks.SOUL_SAND, ModBlocksRegistry.SOUL_SAND_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.SOUL_SOIL, ModBlocksRegistry.SOUL_SOIL_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.NETHERRACK, ModBlocksRegistry.NETHERRACK_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.BLACKSTONE, ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB);
        generateSlabModel(blockStateModelGenerator, Blocks.END_STONE, ModBlocksRegistry.ENDSTONE_SLAB);
   }

    public void generateSlabModel(BlockStateModelGenerator blockStateModelGenerator, Block baseBlock, Block slabBlock) {
        WeightedVariant doubleModel = createWeightedVariant(ModelIds.getBlockModelId(baseBlock));

        TextureMap textureMap = TextureMap.all(baseBlock);
        WeightedVariant bottomModel = createWeightedVariant(Models.SLAB.upload(slabBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant topModel = createWeightedVariant(Models.SLAB_TOP.upload(slabBlock, textureMap, blockStateModelGenerator.modelCollector));

        blockStateModelGenerator.blockStateCollector.accept(
                createSlabBlockState(slabBlock, bottomModel, topModel, doubleModel)
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // itemModelGenerator.register(ModItems.SAND_SLAB_ITEM, Models.SLAB);
    }
}
