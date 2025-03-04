package net.countered.terrainslabs;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.block.*;
import net.minecraft.client.color.block.*;
import net.minecraft.client.color.world.*;
import net.minecraft.client.render.*;
import net.minecraft.world.biome.*;

import static net.countered.terrainslabs.block.ModBlocksRegistry.*;

public class TerrainSlabsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), getCutoutMippedBlocks());
        ColorProviderRegistry.BLOCK.register(colorBlockOrDefaultProvider(), getTintedBlocks());
    }

    private static Block[] getCutoutMippedBlocks() {
        return new Block[]{
                GRASS_SLAB,
                POPPY_ON_TOP,
                DANDELION_ON_TOP,
                AZURE_BLUET_ON_TOP,
                CORNFLOWER_ON_TOP,
                SHORT_GRASS_ON_TOP,
                FERN_ON_TOP,
                BROWN_MUSHROOM_ON_TOP,
                RED_MUSHROOM_ON_TOP,
                DEAD_BUSH_ON_TOP,
                SEAGRASS_ON_TOP
        };
    }

    private static Block[] getTintedBlocks() {
        return new Block[] {
                GRASS_SLAB,
                SHORT_GRASS_ON_TOP,
                FERN_ON_TOP
        };
    }

    private static BlockColorProvider colorBlockOrDefaultProvider() {
        return (state, world, pos, tintIndex) -> world != null && pos != null
                ? BiomeColors.getGrassColor(world, pos)
                : GrassColors.getDefaultColor();
    }
}
