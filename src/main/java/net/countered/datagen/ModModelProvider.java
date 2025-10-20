package net.countered.datagen;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        Map<Block, Block> slabMap = new HashMap<>();
        slabMap.put(Blocks.STONE, ModBlocksRegistry.CUSTOM_STONE_SLAB);
        slabMap.put(Blocks.TUFF, ModBlocksRegistry.CUSTOM_TUFF_SLAB);
        slabMap.put(Blocks.ANDESITE, ModBlocksRegistry.CUSTOM_ANDESITE_SLAB);
        slabMap.put(Blocks.DIORITE, ModBlocksRegistry.CUSTOM_DIORITE_SLAB);
        slabMap.put(Blocks.GRANITE, ModBlocksRegistry.CUSTOM_GRANITE_SLAB);

        slabMap.put(Blocks.GRAVEL, ModBlocksRegistry.GRAVEL_SLAB);
        slabMap.put(Blocks.SAND, ModBlocksRegistry.SAND_SLAB);
        slabMap.put(Blocks.RED_SAND, ModBlocksRegistry.RED_SAND_SLAB);

        slabMap.put(Blocks.DIRT, ModBlocksRegistry.DIRT_SLAB);
        slabMap.put(Blocks.MUD, ModBlocksRegistry.MUD_SLAB);
        slabMap.put(Blocks.COARSE_DIRT, ModBlocksRegistry.COARSE_SLAB);
        slabMap.put(Blocks.PACKED_ICE, ModBlocksRegistry.PACKED_ICE_SLAB);
        slabMap.put(Blocks.CLAY, ModBlocksRegistry.CLAY_SLAB);
        slabMap.put(Blocks.DEEPSLATE, ModBlocksRegistry.DEEPSLATE_SLAB);
        slabMap.put(Blocks.MOSS_BLOCK, ModBlocksRegistry.MOSS_SLAB);
        // terralith
        slabMap.put(Blocks.CALCITE, ModBlocksRegistry.CALCITE_SLAB);
        slabMap.put(Blocks.SMOOTH_BASALT, ModBlocksRegistry.SMOOTH_BASALT_SLAB);
        slabMap.put(Blocks.LIGHT_BLUE_TERRACOTTA, ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB);
        slabMap.put(Blocks.CYAN_TERRACOTTA, ModBlocksRegistry.CYAN_TERRACOTTA_SLAB);
        slabMap.put(Blocks.COBBLESTONE, ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB);
        slabMap.put(Blocks.MOSSY_COBBLESTONE, ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB);
        slabMap.put(Blocks.COBBLED_DEEPSLATE, ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB);
        slabMap.put(Blocks.ICE, ModBlocksRegistry.ICE_SLAB);
        slabMap.put(Blocks.ROOTED_DIRT, ModBlocksRegistry.ROOTED_DIRT_SLAB);
        slabMap.put(Blocks.PACKED_MUD, ModBlocksRegistry.PACKED_MUD_SLAB);
        slabMap.put(Blocks.BLUE_ICE, ModBlocksRegistry.BLUE_ICE_SLAB);
        slabMap.put(Blocks.BLACK_TERRACOTTA, ModBlocksRegistry.BLACK_TERRACOTTA_SLAB);
        slabMap.put(Blocks.PRISMARINE, ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB);

        slabMap.put(Blocks.TERRACOTTA, ModBlocksRegistry.TERRACOTTA_SLAB);
        slabMap.put(Blocks.RED_TERRACOTTA, ModBlocksRegistry.RED_TERRACOTTA_SLAB);
        slabMap.put(Blocks.ORANGE_TERRACOTTA, ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB);
        slabMap.put(Blocks.LIGHT_GRAY_TERRACOTTA, ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB);
        slabMap.put(Blocks.WHITE_TERRACOTTA, ModBlocksRegistry.WHITE_TERRACOTTA_SLAB);
        slabMap.put(Blocks.BROWN_TERRACOTTA, ModBlocksRegistry.BROWN_TERRACOTTA_SLAB);
        slabMap.put(Blocks.YELLOW_TERRACOTTA, ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB);

        slabMap.put(Blocks.SOUL_SAND, ModBlocksRegistry.SOUL_SAND_SLAB);
        slabMap.put(Blocks.SOUL_SOIL, ModBlocksRegistry.SOUL_SOIL_SLAB);
        slabMap.put(Blocks.NETHERRACK, ModBlocksRegistry.NETHERRACK_SLAB);
        slabMap.put(Blocks.BLACKSTONE, ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB);
        slabMap.put(Blocks.END_STONE, ModBlocksRegistry.ENDSTONE_SLAB);

        for (Map.Entry<Block, Block> entry : slabMap.entrySet()) {
            generateCubeAllSlab(entry.getKey(), entry.getValue(), generator);
        }
    }

    private void generateCubeAllSlab(Block base, Block slab, BlockStateModelGenerator generator) {
        Identifier baseId = Registries.BLOCK.getId(base);
        Identifier baseTextureId = new Identifier(baseId.getNamespace(), "block/" + baseId.getPath());

        TextureMap textures = TextureMap.all(baseTextureId);

        Identifier slabModel = Models.SLAB.upload(slab, textures, generator.modelCollector);
        Identifier slabTopModel = Models.SLAB_TOP.upload(slab, textures, generator.modelCollector);

        Identifier doubleModel = Models.CUBE_ALL.uploadWithoutVariant(slab, "_double", textures, generator.modelCollector);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(
                slab, slabModel, slabTopModel, doubleModel
        ));
        generator.registerParentedItemModel(slab, slabModel);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
