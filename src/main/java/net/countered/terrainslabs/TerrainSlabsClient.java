package net.countered.terrainslabs;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GrassColors;

public class TerrainSlabsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModContainer mod = FabricLoader.getInstance().getModContainer(TerrainSlabs.MOD_ID).orElseThrow();
        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(TerrainSlabs.MOD_ID, "better_grass_slabs"),
                mod,
                ResourcePackActivationType.NORMAL
        );
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.TRANSLUCENT, ModBlocksRegistry.ICE_SLAB);
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                ModBlocksRegistry.GRASS_SLAB,
                ModBlocksRegistry.POPPY_ON_TOP,
                ModBlocksRegistry.DANDELION_ON_TOP,
                ModBlocksRegistry.AZURE_BLUET_ON_TOP,
                ModBlocksRegistry.CORNFLOWER_ON_TOP,
                ModBlocksRegistry.SHORT_GRASS_ON_TOP,
                ModBlocksRegistry.FERN_ON_TOP,
                ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP,
                ModBlocksRegistry.RED_MUSHROOM_ON_TOP,
                ModBlocksRegistry.DEAD_BUSH_ON_TOP,
                ModBlocksRegistry.SEAGRASS_ON_TOP
        );

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
                return world != null && pos != null
                        ? BiomeColors.getGrassColor(world, pos)
                        : GrassColors.getDefaultColor();
        }, ModBlocksRegistry.GRASS_SLAB);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
                return world != null && pos != null
                        ? BiomeColors.getGrassColor(world, pos)
                        : GrassColors.getDefaultColor();
        }, ModBlocksRegistry.SHORT_GRASS_ON_TOP);


        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
                return world != null && pos != null
                        ? BiomeColors.getGrassColor(world, pos)
                        : GrassColors.getDefaultColor();
        }, ModBlocksRegistry.FERN_ON_TOP);
    }
}
