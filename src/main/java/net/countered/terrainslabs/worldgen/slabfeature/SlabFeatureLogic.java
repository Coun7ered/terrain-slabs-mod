package net.countered.terrainslabs.worldgen.slabfeature;

import com.mojang.serialization.Codec;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.config.MyModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.*;

public class SlabFeatureLogic extends Feature<DefaultFeatureConfig> {

    public SlabFeatureLogic(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public static Map<ChunkPos, Pair<List<BlockPos>, List<BlockPos>>> chunkSlabPlacementPositions = new HashMap<>();

    public static final Set<Block> VALID_BLOCKS_FOR_SLAB_PLACEMENT = new HashSet<>();
    static {
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.GRASS_BLOCK);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.PODZOL);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.MYCELIUM);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.DIRT_PATH);

        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.DIRT);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.MOSS_BLOCK);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.PACKED_ICE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.COARSE_DIRT);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.MUD);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.SNOW_BLOCK);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.CLAY);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.DEEPSLATE);

        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.STONE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.ANDESITE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.DIORITE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.GRANITE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.TUFF);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.SANDSTONE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.RED_SANDSTONE);

        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.SAND);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.GRAVEL);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.RED_SAND);

        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.TERRACOTTA);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.BROWN_TERRACOTTA);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.RED_TERRACOTTA);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.ORANGE_TERRACOTTA);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.LIGHT_GRAY_TERRACOTTA);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.YELLOW_TERRACOTTA);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.WHITE_TERRACOTTA);

        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.SOUL_SAND);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.SOUL_SOIL);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.NETHERRACK);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.WARPED_NYLIUM);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.CRIMSON_NYLIUM);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.BASALT);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.BLACKSTONE);
        VALID_BLOCKS_FOR_SLAB_PLACEMENT.add(Blocks.END_STONE);
    }

    public static final Set<Block> SOIL_SLAB_BLOCKS = Set.of(
            ModBlocksRegistry.GRASS_SLAB,
            ModBlocksRegistry.PODZOL_SLAB,
            ModBlocksRegistry.MYCELIUM_SLAB,
            ModBlocksRegistry.PATH_SLAB
    );

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context){
        if (MyModConfig.enableSlabGeneration) {
            storeSlabPositions(context); // Logic for slab generation
            return true;
        }
        return false;
    }

    private void storeSlabPositions(FeatureContext<DefaultFeatureConfig> context) {
        WorldAccess worldAccess = context.getWorld();
        BlockPos origin = context.getOrigin();
        ChunkPos chunkPos = new ChunkPos(origin);
        Pair<List<BlockPos>, List<BlockPos>> chunkPlacePositions = new Pair<>(new ArrayList<>(), new ArrayList<>());

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
                        chunkPlacePositions.getLeft().add(currentPos);
                    }
                    /*
                    else if (shouldPlaceSlabOnUnderside(worldAccess, currentPos, blockAbovePos, blockBelowPos, currentBlockState, blockBelowState)) {
                        slabState = ModSlabsMap.getSlabForBlock(currentBlockState.getBlock()).getDefaultState();

                        if (SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
                            slabState = ModBlocksRegistry.DIRT_SLAB.getDefaultState();
                        }
                        slabState = slabState.with(Properties.SLAB_TYPE, SlabType.TOP);
                        slabState = updateWaterloggedState(worldAccess, currentPos, slabState);
                        worldAccess.setBlockState(currentPos, slabState.with(CustomSlab.GENERATED, true), 3);

                 */
                }
            }
        }
        chunkSlabPlacementPositions.put(chunkPos, chunkPlacePositions);
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
        if ((currentBlockState.isOpaque() && !currentBlockState.isOf(Blocks.SNOW) && !currentBlockState.isReplaceable())
                || ModSlabsMap.getSlabForBlock(blockBelowState.getBlock()) == Blocks.AIR
                || (!blockAboveState.isAir() && !blockAboveState.isOf(Blocks.WATER)))
        {
            return false;
        }
        return validSurrounding(world, currentPos);
    }

    private boolean shouldPlaceSlabOnUnderside(WorldAccess world, BlockPos currentPos, BlockPos blockAbovePos, BlockPos blockBelowPos, BlockState currentBlock, BlockState blockBelow) {

        if (ModSlabsMap.getSlabForBlock(world.getBlockState(blockAbovePos).getBlock()) == Blocks.AIR || world.getBlockState(blockAbovePos).getBlock() instanceof SlabBlock){
            return false;
        }
        // Check that the block above is a valid block for slab placement and that the current block is air or water
        if (VALID_BLOCKS_FOR_SLAB_PLACEMENT.contains(currentBlock.getBlock())
                && (blockBelow.isAir() || blockBelow.getBlock() == Blocks.WATER)
                && currentBlock.isOpaque()) {

            for (Direction direction1 : Direction.Type.HORIZONTAL){
                BlockPos neighborPos = currentPos.offset(direction1);
                BlockState neighborState = world.getBlockState(neighborPos);
                if ( nextToGlowLichen(world, currentPos, direction1) || neighborState.isOf(Blocks.LAVA)) {
                    return false;
                }
            }
            // Check neighboring blocks to ensure at least one horizontal neighbor is air or water
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos neighborPos = currentPos.offset(direction);
                BlockState neighborState = world.getBlockState(neighborPos);

                // If at least one horizontal neighbor is air or water, mark this position for slab placement
                if ((neighborState.isAir() || neighborState.getBlock() == Blocks.WATER)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the slab state to be waterlogged if applicable.
     */
    private BlockState updateWaterloggedState(WorldAccess world, BlockPos pos, BlockState slabState) {

        if (slabState.contains(Properties.WATERLOGGED)) {
            if (world.getBlockState(pos).isOf(Blocks.WATER) || world.getBlockState(pos.up()).isOf(Blocks.WATER)) {
                return slabState.with(Properties.WATERLOGGED, true);
            }
            for (Direction direction1 : Direction.Type.HORIZONTAL) {
                // Check if the neighbor or the block above contains water to set the waterlogged property
                if (world.getBlockState(pos.offset(direction1)).isOf(Blocks.WATER)) {
                    return slabState.with(Properties.WATERLOGGED, true);
                }
            }
        }
        return slabState;
    }

    /*
    private Boolean nextToLiquidAndAir(WorldAccess world, BlockPos currentPos, Direction direction) {
        BlockState directionBlockState = world.getBlockState(currentPos.offset(direction));
        BlockState oppositeDirectionBlockState = world.getBlockState(currentPos.offset(direction.getOpposite()));
        if ((directionBlockState.isOf(Blocks.WATER) && oppositeDirectionBlockState.getBlock() == Blocks.AIR)
            || (directionBlockState.isOf(Blocks.LAVA) && oppositeDirectionBlockState.getBlock() == Blocks.AIR)){
            return true;
        }
        return false;
    }

     */

    private boolean validSurrounding(WorldAccess world, BlockPos currentPos) {
        boolean bottomOfMountain = false;
        boolean validNeighbors = false;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos offset = currentPos.offset(direction);
            BlockPos belowOffset = offset.down();
            BlockPos opposite = currentPos.offset(direction.getOpposite());
            BlockState offsetState = world.getBlockState(offset);
            BlockState belowOffsetState = world.getBlockState(belowOffset);
            BlockState oppositeState = world.getBlockState(opposite);

            if (offsetState.isOf(Blocks.LAVA)) return false;
            // Prüfe Bedingungen
            boolean isOffsetBelowOpaque = belowOffsetState.isOpaque();
            boolean isOppositeDirOpaque = oppositeState.isOpaque();
            boolean isBelowNoSlab =
                    !(belowOffsetState.getBlock() instanceof SlabBlock);
            boolean isOppositeDirNoSlab =
                    !(oppositeState.getBlock() instanceof SlabBlock);
            boolean isOffsetBelowNoSnow =
                    !belowOffsetState.isOf(Blocks.SNOW) &&
                            !belowOffsetState.isOf(ModBlocksRegistry.SNOW_ON_TOP);
            boolean isOppositeDirNoSnow =
                    !oppositeState.isOf(Blocks.SNOW) &&
                            !oppositeState.isOf(ModBlocksRegistry.SNOW_ON_TOP);

            // Gesamtbedingung prüfen
            if (isOffsetBelowOpaque && isOppositeDirOpaque &&
                    isBelowNoSlab && isOppositeDirNoSlab &&
                    isOffsetBelowNoSnow && isOppositeDirNoSnow) {
                bottomOfMountain = true;
            }

            BlockState neighborState = world.getBlockState(offset);
            // Check if a neighboring block is opaque and not a slab
            if (neighborState.isOpaqueFullCube(world, offset) && !(neighborState.getBlock() instanceof SlabBlock) && !neighborState.isOf(Blocks.SNOW)
                    && (!world.getBlockState(offset.up()).isOpaque() || world.getBlockState(offset.up()).getBlock() == Blocks.SNOW)) {
                validNeighbors = true;
            }
        }
        return validNeighbors && bottomOfMountain;
    }


    private boolean nextToGlowLichen(WorldAccess world, BlockPos currentPos, Direction direction) {
        if (world.getBlockState(currentPos.offset(direction)).isOf(Blocks.GLOW_LICHEN)){
            return true;
        }
        return false;
    }
}
