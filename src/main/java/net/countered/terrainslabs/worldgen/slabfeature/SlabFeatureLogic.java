package net.countered.terrainslabs.worldgen.slabfeature;

import com.mojang.serialization.Codec;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.config.MyModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.HashSet;
import java.util.Set;

public class SlabFeatureLogic extends Feature<DefaultFeatureConfig> {

    public SlabFeatureLogic(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }
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
    private static final Set<Block> SOIL_SLAB_BLOCKS = Set.of(
            ModBlocksRegistry.GRASS_SLAB,
            ModBlocksRegistry.PODZOL_SLAB,
            ModBlocksRegistry.MYCELIUM_SLAB,
            ModBlocksRegistry.PATH_SLAB
    );
    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context){
        if (MyModConfig.enableSlabGeneration) {
            return runLogic(context); // Logic for slab generation
        }
        return false;
    }

    private boolean runLogic(FeatureContext<DefaultFeatureConfig> context) {
        WorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        ChunkPos chunkPos = new ChunkPos(origin);
        BlockPos highestBlock = new BlockPos.Mutable(0,0,0);
        BlockPos.Mutable pos = new BlockPos.Mutable();

        // Loop through x and z within the chunk boundaries
        for (int x = chunkPos.getStartX(); x <= chunkPos.getEndX(); x++) {
            for (int z = chunkPos.getStartZ(); z <= chunkPos.getEndZ(); z++) {
                pos.set(x, 0, z);
                // Ensure the chunk at this position is loaded
                if (world.isChunkLoaded(chunkPos.x, chunkPos.z)) {
                    BlockPos topPosition = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);
                    // Check and update highest block
                    if (highestBlock.getY() < topPosition.getY()) {
                        highestBlock = topPosition;
                    }
                }
            }
        }

        for (int y = world.getBottomY(); y < highestBlock.getY()+1; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos currentPos = chunkPos.getBlockPos(x, y, z);
                    BlockPos blockBelowPos = currentPos.down();
                    BlockPos blockAbovePos = currentPos.up();
                    BlockState blockBelowState = world.getBlockState(blockBelowPos);
                    BlockState blockAboveState = world.getBlockState(blockAbovePos);
                    BlockState currentBlockState = world.getBlockState(currentPos);
                    BlockState slabState;
                    // Check conditions to place a slab on top of the current block
                    if (shouldPlaceSlabTop(world, currentPos, blockBelowPos, blockAboveState, blockBelowState, currentBlockState)) {
                        if (world.getBlockState(currentPos).isOf(Blocks.SNOW)){
                            if (world.getBlockState(blockBelowPos).isOf(Blocks.GRASS_BLOCK)){
                                world.setBlockState(blockBelowPos, Blocks.DIRT.getDefaultState(), 3);
                                world.setBlockState(currentPos, ModBlocksRegistry.GRASS_SLAB.getDefaultState().with(Properties.SNOWY, true).with(CustomSlab.GENERATED, true), 3);
                            }
                            else {
                                world.setBlockState(currentPos, ModSlabsMap.getSlabForBlock(world.getBlockState(blockBelowPos).getBlock()).getDefaultState().with(CustomSlab.GENERATED, true), 3);
                            }
                            world.setBlockState(blockAbovePos, ModBlocksRegistry.SNOW_ON_TOP.getDefaultState(), 3);
                        }
                        else {
                            blockBelowState = world.getBlockState(blockBelowPos);

                            // Retrieve the slab type based on the block below the current position
                            slabState = ModSlabsMap.getSlabForBlock(blockBelowState.getBlock()).getDefaultState();

                            // Handle grass slab special case by converting grass to dirt before placing the slab
                            if (SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
                                world.setBlockState(blockBelowPos, Blocks.DIRT.getDefaultState(), 3);
                            }
                            if (slabState.isOf(ModBlocksRegistry.WARPED_NYLIUM_SLAB) || slabState.isOf(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)) {
                                world.setBlockState(blockBelowPos, Blocks.NETHERRACK.getDefaultState(), 3);
                            }

                            slabState = updateWaterloggedState(world, currentPos, slabState);

                            if (ModSlabsMap.ON_TOP_SLAB_BLOCKS_MAP.containsKey(currentBlockState.getBlock())){
                                if (world.getBlockState(blockAbovePos).isOf(Blocks.WATER)){
                                    world.setBlockState(blockAbovePos, ModSlabsMap.ON_TOP_SLAB_BLOCKS_MAP.get(currentBlockState.getBlock()).getDefaultState(), 3);
                                }
                                else if (!world.getBlockState(currentPos).isOf(Blocks.SEAGRASS)){
                                    world.setBlockState(blockAbovePos, ModSlabsMap.ON_TOP_SLAB_BLOCKS_MAP.get(currentBlockState.getBlock()).getDefaultState(), 3);
                                }
                            }
                            world.setBlockState(currentPos, slabState.with(CustomSlab.GENERATED, true), 3);
                        }
                    }
                    else if (shouldPlaceSlabOnUnderside(world, currentPos, blockAbovePos, blockBelowPos, currentBlockState, blockBelowState)) {
                        slabState = ModSlabsMap.getSlabForBlock(currentBlockState.getBlock()).getDefaultState();

                        if (SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
                            slabState = ModBlocksRegistry.DIRT_SLAB.getDefaultState();
                        }
                        slabState = slabState.with(Properties.SLAB_TYPE, SlabType.TOP);
                        slabState = updateWaterloggedState(world, currentPos, slabState);
                        world.setBlockState(currentPos, slabState.with(CustomSlab.GENERATED, true), 3);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if a slab should be placed at the given position based on world conditions.
     */
    private boolean shouldPlaceSlabTop(WorldAccess world, BlockPos currentPos, BlockPos blockBelowPos, BlockState blockAboveState, BlockState blockBelow, BlockState currentBlockState) {
        if (ModSlabsMap.getSlabForBlock(world.getBlockState(blockBelowPos).getBlock()) == Blocks.AIR
                || world.getBlockState(blockBelowPos).getBlock() instanceof SlabBlock
                || world.getBlockState(currentPos).isOf(Blocks.POWDER_SNOW)){
            return false;
        }
        // Check that the block below is opaque and not a slab, and the position is air or snow
        if (blockBelow.isOpaque() && !(blockBelow.getBlock() instanceof SlabBlock) && bottomOfMountain(world, currentPos)
                && (!currentBlockState.isOpaque() || currentBlockState.isOf(Blocks.SNOW) || currentBlockState.isReplaceable())
                && (blockAboveState.isAir() || blockAboveState.isOf(Blocks.WATER))) {

            // Check neighboring blocks to ensure at least one opaque block is adjacent
            for (Direction direction : Direction.Type.HORIZONTAL) {

                BlockPos neighborPos = currentPos.offset(direction);
                BlockState neighborState = world.getBlockState(neighborPos);
                // Check if a neighboring block is opaque and not a slab
                if (neighborState.isOpaque() && VALID_BLOCKS_FOR_SLAB_PLACEMENT.contains(neighborState.getBlock())
                        && !(neighborState.getBlock() instanceof SlabBlock)
                        && (!world.getBlockState(neighborPos.up()).isOpaque() || world.getBlockState(neighborPos.up()).getBlock() == Blocks.SNOW)
                        && !world.getBlockState(neighborPos).isOf(Blocks.SNOW)) {

                    if (badNextToWaterSlab(world, currentPos, blockBelowPos, Direction.NORTH) || badNextToWaterSlab(world, currentPos, blockBelowPos, Direction.WEST) || badNextToWaterSlab(world, currentPos, blockBelowPos, Direction.SOUTH) || badNextToWaterSlab(world, currentPos, blockBelowPos, Direction.EAST) ||
                            world.getBlockState(currentPos.offset(Direction.NORTH)).isOf(Blocks.ICE) || world.getBlockState(currentPos.offset(Direction.WEST)).isOf(Blocks.ICE) || world.getBlockState(currentPos.offset(Direction.SOUTH)).isOf(Blocks.ICE) || world.getBlockState(currentPos.offset(Direction.EAST)).isOf(Blocks.ICE) ||
                            world.getBlockState(currentPos.offset(Direction.NORTH)).isOf(Blocks.LAVA) || world.getBlockState(currentPos.offset(Direction.WEST)).isOf(Blocks.LAVA) || world.getBlockState(currentPos.offset(Direction.SOUTH)).isOf(Blocks.LAVA) || world.getBlockState(currentPos.offset(Direction.EAST)).isOf(Blocks.LAVA)){
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
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
                if (badNextToWaterSlab(world, currentPos, blockBelowPos, direction1) || nextToGlowLichen(world, currentPos, direction1) || neighborState.isOf(Blocks.LAVA)) {
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

    private Boolean badNextToWaterSlab(WorldAccess world, BlockPos currentPos, BlockPos blockBelowPos, Direction direction) {
        return world.getBlockState(currentPos.offset(direction)).isOf(Blocks.WATER) &&
                (world.getBlockState(blockBelowPos).isOf(Blocks.AIR) || world.getBlockState(currentPos.offset(direction.getOpposite())).getBlock() == Blocks.AIR);
    }

    private boolean bottomOfMountain(WorldAccess world, BlockPos currentPos) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (world.getBlockState(currentPos.offset(direction).down()).isOpaque() && world.getBlockState(currentPos.offset(direction.getOpposite())).isOpaque()
            && !(world.getBlockState(currentPos.offset(direction).down()).getBlock() instanceof SlabBlock) && !(world.getBlockState(currentPos.offset(direction.getOpposite())).getBlock() instanceof SlabBlock)
                    && !world.getBlockState(currentPos.offset(direction.getOpposite())).isOf(Blocks.SNOW) && !world.getBlockState(currentPos.offset(direction).down()).isOf(Blocks.SNOW)
                    && !world.getBlockState(currentPos.offset(direction.getOpposite())).isOf(ModBlocksRegistry.SNOW_ON_TOP) && !world.getBlockState(currentPos.offset(direction).down()).isOf(ModBlocksRegistry.SNOW_ON_TOP))
            {
                return true;
            }
        }
        return false;
    }

    private boolean nextToGlowLichen(WorldAccess world, BlockPos currentPos, Direction direction) {
        return world.getBlockState(currentPos.offset(direction)).isOf(Blocks.GLOW_LICHEN);
    }
}
