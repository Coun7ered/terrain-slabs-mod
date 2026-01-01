package net.countered.terrainslabs.worldgen.slabfeature;

import com.mojang.serialization.Codec;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.config.ModConfig;
import net.countered.terrainslabs.persistence.SlabChunkAttachment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Set;

public class SlabFeatureLogic extends Feature<DefaultFeatureConfig> {

    public SlabFeatureLogic(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public static final Set<Block> SOIL_SLAB_BLOCKS = Set.of(
            ModBlocksRegistry.GRASS_SLAB,
            ModBlocksRegistry.PODZOL_SLAB,
            ModBlocksRegistry.MYCELIUM_SLAB,
            ModBlocksRegistry.PATH_SLAB
    );

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context){
        if (ModConfig.enableSlabGeneration) {
            storeSlabPositions(context); // Logic for slab generation
            return true;
        }
        return false;
    }

    private void storeSlabPositions(FeatureContext<DefaultFeatureConfig> context) {
        WorldAccess worldAccess = context.getWorld();
        BlockPos origin = context.getOrigin();
        ChunkPos chunkPos = new ChunkPos(origin);
        Chunk chunk = worldAccess.getChunk(chunkPos.x, chunkPos.z);
        BlockPos highestBlock = findHighestChunkPos(worldAccess, chunkPos);
        for (int y = worldAccess.getBottomY(); y < highestBlock.getY()+1; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos currentPos = chunkPos.getBlockPos(x, y, z);
                    BlockPos blockBelowPos = currentPos.down();
                    BlockPos blockAbovePos = currentPos.up();
                    BlockState blockBelowState = worldAccess.getBlockState(blockBelowPos);
                    BlockState blockAboveState = worldAccess.getBlockState(blockAbovePos);
                    BlockState currentBlockState = worldAccess.getBlockState(currentPos);
                    // Check conditions to place a slab on top of the current block
                    if (shouldPlaceBottomSlab(worldAccess, currentPos, blockAboveState, blockBelowState, currentBlockState)) {
                        chunk.getAttachedOrCreate(SlabChunkAttachment.BOT_SLAB_POSITIONS).add(currentPos);
                    }
                    else if (shouldPlaceTopSlab(worldAccess, currentPos, currentBlockState, blockBelowState, blockAboveState, blockAbovePos)) {
                        chunk.getAttachedOrCreate(SlabChunkAttachment.TOP_SLAB_POSITIONS).add(currentPos);
                    }
                }
            }
        }
    }

    private BlockPos findHighestChunkPos(WorldAccess worldAccess, ChunkPos chunkPos) {
        BlockPos.Mutable highestChunkPos = new BlockPos.Mutable(0,0,0);
        BlockPos.Mutable testPos = new BlockPos.Mutable(0,0,0);
        // Loop through x and z within the chunk boundaries
        for (int x = chunkPos.getStartX(); x <= chunkPos.getEndX(); x++) {
            for (int z = chunkPos.getStartZ(); z <= chunkPos.getEndZ(); z++) {
                testPos.set(x, 0, z);
                // Ensure the chunk at this position is loaded
                BlockPos topPosition = worldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, testPos);
                // Check and update highest block
                if (highestChunkPos.getY() < topPosition.getY()) {
                    highestChunkPos = topPosition.mutableCopy();
                }
            }
        }
        return highestChunkPos;
    }

    /**
     * Determines if a slab should be placed at the given position based on world conditions.
     */
    private boolean shouldPlaceBottomSlab(WorldAccess world, BlockPos currentPos, BlockState blockAboveState, BlockState blockBelowState, BlockState currentBlockState) {
        if ((currentBlockState.isOpaqueFullCube() && !currentBlockState.isOf(Blocks.SNOW) && !currentBlockState.isReplaceable())
                || ModSlabsMap.getSlabForBlock(blockBelowState.getBlock()) == Blocks.AIR
                || (!blockAboveState.isOf(Blocks.AIR) && !blockAboveState.isOf(Blocks.WATER) && !blockAboveState.isOf(Blocks.CAVE_AIR) && !blockAboveState.isOf(Blocks.VOID_AIR)))
        {
            return false;
        }
        return validSurroundingBottom(world, currentPos);
    }

    private boolean shouldPlaceTopSlab(WorldAccess world, BlockPos currentPos, BlockState currentState, BlockState blockBelow, BlockState blockAboveState, BlockPos blockAbovePos) {
        if (!currentState.isOpaqueFullCube()
                || !(blockBelow.isOf(Blocks.AIR) || blockBelow.isOf(Blocks.WATER) || blockBelow.isOf(Blocks.CAVE_AIR) || blockBelow.isOf(Blocks.VOID_AIR))
                || ModSlabsMap.getSlabForBlock(blockAboveState.getBlock()).equals(Blocks.AIR))
        {
            return false;
        }
        return validSurroundingTop(world, currentPos);
    }

    private boolean validSurroundingTop(WorldAccess world, BlockPos currentPos) {
        boolean topOfCeiling = false;
        boolean validNeighbors = false;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos neighborPos = currentPos.offset(direction);
            BlockPos aboveNeighborPos = neighborPos.up();
            BlockPos oppositePos = currentPos.offset(direction.getOpposite());
            BlockPos belowOppositePos = oppositePos.down();
            BlockState neighborState = world.getBlockState(neighborPos);
            BlockState aboveNeighborState = world.getBlockState(aboveNeighborPos);
            BlockState oppositeState = world.getBlockState(oppositePos);
            BlockState belowOppositeState = world.getBlockState(belowOppositePos);

            if (neighborState.isOf(Blocks.GLOW_LICHEN) || neighborState.isOf(Blocks.LAVA)) {
                return false;
            }
            boolean isNeighborStateNotOpaque = !neighborState.isOpaqueFullCube();
            boolean isOppositeStateOpaque = oppositeState.isOpaqueFullCube();
            boolean isAboveNeighborStateOpaque = aboveNeighborState.isOpaqueFullCube();
            boolean isBelowOppositeStateNotOpaque = !belowOppositeState.isOpaqueFullCube();

            if (isNeighborStateNotOpaque && isOppositeStateOpaque && isBelowOppositeStateNotOpaque) {
                topOfCeiling = true;
            }
            // Check neighboring blocks to ensure at least one horizontal neighbor is air or water
            if (neighborState.isOf(Blocks.AIR) || neighborState.isOf(Blocks.WATER) || neighborState.isOf(Blocks.CAVE_AIR) || neighborState.isOf(Blocks.VOID_AIR)) {
                validNeighbors = true;
            }
        }
        return topOfCeiling && validNeighbors;
    }

    private boolean validSurroundingBottom(WorldAccess world, BlockPos currentPos) {
        boolean bottomOfMountain = false;
        boolean validNeighbors = false;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos neighborPos = currentPos.offset(direction);
            BlockPos belowNeighborPos = neighborPos.down();
            BlockPos oppositePos = currentPos.offset(direction.getOpposite());
            BlockState neighborState = world.getBlockState(neighborPos);
            BlockState belowNeighborState = world.getBlockState(belowNeighborPos);
            BlockState oppositeState = world.getBlockState(oppositePos);

            if (neighborState.isOf(Blocks.LAVA)) return false;

            boolean isNeighborBelowOpaque = belowNeighborState.isOpaque();
            boolean isOppositeDirOpaque = oppositeState.isOpaque();
            boolean isBelowNoSlab =
                    !(belowNeighborState.getBlock() instanceof SlabBlock);
            boolean isOppositeDirNoSlab =
                    !(oppositeState.getBlock() instanceof SlabBlock);
            boolean isNeighborBelowNoSnow =
                    !belowNeighborState.isOf(Blocks.SNOW) &&
                            !belowNeighborState.isOf(ModBlocksRegistry.SNOW_ON_TOP);
            boolean isOppositeDirNoSnow =
                    !oppositeState.isOf(Blocks.SNOW) &&
                            !oppositeState.isOf(ModBlocksRegistry.SNOW_ON_TOP);

            if (isNeighborBelowOpaque && isOppositeDirOpaque &&
                    isBelowNoSlab && isOppositeDirNoSlab &&
                    isNeighborBelowNoSnow && isOppositeDirNoSnow) {
                bottomOfMountain = true;
            }

            // Check if a neighboring block is opaque and not a slab
            if (neighborState.isOpaqueFullCube() && !(neighborState.getBlock() instanceof SlabBlock) && !neighborState.isOf(Blocks.SNOW)
                    && (!world.getBlockState(neighborPos.up()).isOpaque() || world.getBlockState(neighborPos.up()).getBlock() == Blocks.SNOW)) {
                validNeighbors = true;
            }
        }
        return validNeighbors && bottomOfMountain;
    }
}
