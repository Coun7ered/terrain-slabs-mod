package net.countered.terrainslabs.worldgen.slabfeature;

import com.mojang.serialization.Codec;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.config.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        List<BlockPos> extendedPositionsGlobal = new ArrayList<>();
        List<BlockPos> botSlabPositions = new ArrayList<>();
        List<BlockPos> topSlabPositions = new ArrayList<>();

        for (int y = worldAccess.getBottomY(); y < highestBlock.getY()+1; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos currentPos = chunkPos.getBlockPos(x, y, z);
                    if (shouldPlaceBottomSlab(worldAccess, currentPos,  extendedPositionsGlobal)) {
                        botSlabPositions.add(currentPos);
                    }
                    else if (shouldPlaceTopSlab(worldAccess, currentPos)) {
                        topSlabPositions.add(currentPos);
                    }
                }
            }
        }
        if (ModConfig.enableCornerSlabs) {
            addCornerSlabsForDiagonals( botSlabPositions);
        }
        botSlabPositions.addAll(extendedPositionsGlobal);
        placeSlabs(worldAccess, botSlabPositions, topSlabPositions);
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

    private void placeSlabs(WorldAccess world, List<BlockPos> botSlabPositions, List<BlockPos> topSlabPositions) {
        for (BlockPos pos : botSlabPositions) placeBottomSlab(world, pos);
        for (BlockPos pos : topSlabPositions) placeTopSlab(world, pos);
    }

    /**
     * Determines if a slab should be placed at the given position based on world conditions.
     */
    private boolean shouldPlaceBottomSlab(WorldAccess world, BlockPos currentPos, List<BlockPos> extendedCollector) {
        BlockPos blockBelowPos = currentPos.down();
        BlockPos blockAbovePos = currentPos.up();
        BlockState blockBelowState = world.getBlockState(blockBelowPos);
        BlockState blockAboveState = world.getBlockState(blockAbovePos);
        BlockState currentBlockState = world.getBlockState(currentPos);

        if ((currentBlockState.isOpaqueFullCube() && !currentBlockState.isOf(Blocks.SNOW) && !currentBlockState.isReplaceable())
                || ModSlabsMap.getSlabForBlock(blockBelowState.getBlock()) == Blocks.AIR
                || (!blockAboveState.isOf(Blocks.AIR) && !blockAboveState.isOf(Blocks.WATER) && !blockAboveState.isOf(Blocks.CAVE_AIR) && !blockAboveState.isOf(Blocks.VOID_AIR)))
        {
            return false;
        }
        return validSurroundingBottom(world, currentPos, extendedCollector);
    }

    private boolean shouldPlaceTopSlab(WorldAccess world, BlockPos currentPos) {
        BlockPos blockBelowPos = currentPos.down();
        BlockPos blockAbovePos = currentPos.up();
        BlockState blockBelowState = world.getBlockState(blockBelowPos);
        BlockState blockAboveState = world.getBlockState(blockAbovePos);
        BlockState currentBlockState = world.getBlockState(currentPos);

        if (!currentBlockState.isOpaqueFullCube()
                || !(blockBelowState.isOf(Blocks.AIR) || blockBelowState.isOf(Blocks.WATER) || blockBelowState.isOf(Blocks.CAVE_AIR) || blockBelowState.isOf(Blocks.VOID_AIR))
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

    private boolean validSurroundingBottom(WorldAccess world, BlockPos currentPos, List<BlockPos> extendedCollector) {
        boolean bottomOfMountain = false;
        boolean validNeighbors = false;
        List<BlockPos> extendedPositions = new ArrayList<>();

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

            if (neighborState.isOpaqueFullCube() && !ModSlabsMap.getSlabForBlock(neighborState.getBlock()).equals(Blocks.AIR) && !neighborState.isOf(Blocks.SNOW)
                    && (!world.getBlockState(neighborPos.up()).isOpaque() || world.getBlockState(neighborPos.up()).getBlock() == Blocks.SNOW)) {
                validNeighbors = true;
                for (int i = 1; i < ModConfig.slabRunLength; i++) {
                    if (world.getBlockState(oppositePos.offset(direction.getOpposite(), i).down()).isOpaque()) {
                        extendedPositions.add(oppositePos.offset(direction.getOpposite(), i - 1));
                    }
                }
            }
        }
        if (validNeighbors && bottomOfMountain) {
            extendedCollector.addAll(extendedPositions);
            return true;
        }
        return false;
    }

    private void addCornerSlabsForDiagonals(List<BlockPos> botSlabPositions) {
        HashSet<BlockPos> allBotSlabs = new HashSet<>(botSlabPositions);
        HashSet<BlockPos> myBotSet = new HashSet<>(botSlabPositions);

        for (BlockPos slabPos : new ArrayList<>(allBotSlabs)) {
            BlockPos tEast = slabPos.offset(Direction.EAST);
            if (!myBotSet.contains(tEast)) {
                if (allBotSlabs.contains(tEast.offset(Direction.NORTH)) || allBotSlabs.contains(tEast.offset(Direction.SOUTH))) {
                    botSlabPositions.add(tEast);
                    myBotSet.add(tEast);
                }
            }
            BlockPos tWest = slabPos.offset(Direction.WEST);
            if (!myBotSet.contains(tWest)) {
                if (allBotSlabs.contains(tWest.offset(Direction.NORTH)) || allBotSlabs.contains(tWest.offset(Direction.SOUTH))) {
                    botSlabPositions.add(tWest);
                    myBotSet.add(tWest);
                }
            }
        }
    }

    private boolean isWithinChunkBounds(BlockPos pos, ChunkPos chunkPos) {
        return pos.getX() >= chunkPos.getStartX() && pos.getX() <= chunkPos.getEndX()
                && pos.getZ() >= chunkPos.getStartZ() && pos.getZ() <= chunkPos.getEndZ();
    }

    private boolean isTopStateWaterlogged(WorldAccess worldAccess, BlockPos currentPos) {
        if (!worldAccess.getBlockState(currentPos.down()).isOf(Blocks.WATER)) {
            return false;
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            // Check if the neighbor contains water to set the waterlogged property
            if (worldAccess.getBlockState(currentPos.offset(direction)).isOf(Blocks.WATER)) {
                return true;
            }
        }
        return false;
    }

    private static final Set<Block> doubleTallPlants = new HashSet<>();
    static {
        doubleTallPlants.add(Blocks.TALL_GRASS);
        doubleTallPlants.add(Blocks.LARGE_FERN);
        doubleTallPlants.add(Blocks.TALL_SEAGRASS);
    }

    private void placeBottomSlab(WorldAccess world, BlockPos pos) {
        BlockPos blockBelowPos = pos.down();
        BlockPos blockAbovePos = pos.up();
        BlockState blockAboveState = world.getBlockState(blockAbovePos);
        BlockState currentBlockState = world.getBlockState(pos);
        BlockState blockBelowState = world.getBlockState(blockBelowPos);

        if (!(currentBlockState.isOf(Blocks.AIR) || currentBlockState.isOf(Blocks.WATER) || currentBlockState.isOf(Blocks.CAVE_AIR) || currentBlockState.isOf(Blocks.VOID_AIR)  || currentBlockState.isOf(Blocks.LAVA))
                && !doubleTallPlants.contains(currentBlockState.getBlock())
                && !ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.containsKey(currentBlockState.getBlock())
                && !currentBlockState.isOf(Blocks.SNOW)
                && !currentBlockState.isOf(Blocks.LEAF_LITTER)
                && !currentBlockState.isOf(Blocks.WILDFLOWERS)
                && !currentBlockState.isOf(Blocks.PINK_PETALS)
                && !currentBlockState.isIn(BlockTags.SMALL_FLOWERS))
        {
            return;
        }
        // Retrieve the slab type based on the block below the current position
        BlockState slabState = ModSlabsMap.getSlabForBlock(blockBelowState.getBlock()).getDefaultState();

        if (slabState.getBlock().equals(Blocks.AIR)) {
            /* DEBUG
            System.out.println(blockBelowState);
            System.out.println(currentBlockState);
             */
            return;
        }
        // remove double tall plants
        if (doubleTallPlants.contains(currentBlockState.getBlock())) {
            if (currentBlockState.isOf(Blocks.TALL_SEAGRASS)) {
                setBlockState(world, blockAbovePos, Blocks.WATER.getDefaultState());
                blockAboveState = Blocks.WATER.getDefaultState();
            }
            else {
                setBlockState(world, blockAbovePos, Blocks.AIR.getDefaultState());
            }
        }
        // Handle grass slab special case by converting grass to dirt before placing the slab
        if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
            setBlockState(world, blockBelowPos, Blocks.DIRT.getDefaultState());
        }
        if (slabState.isOf(ModBlocksRegistry.WARPED_NYLIUM_SLAB) || slabState.isOf(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)) {
            setBlockState(world,blockBelowPos, Blocks.NETHERRACK.getDefaultState());
        }
        slabState = updateBottomWaterloggedState(currentBlockState, blockAboveState, slabState);

        // place vegetation / snow on top
        if (ModConfig.enableVegetationOnSlabs) {
            placeVegetationOnTop(world, currentBlockState, blockAboveState, blockAbovePos);
        }
        if (currentBlockState.isOf(Blocks.SNOW)) {
            if (ModConfig.enableSnowOnSlabs) {
                setBlockState(world, blockAbovePos, ModBlocksRegistry.SNOW_ON_TOP.getDefaultState());
                if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock()) && !slabState.isOf(ModBlocksRegistry.PATH_SLAB)) {
                    slabState = slabState.with(Properties.SNOWY, true);
                }
            } else {
                slabState = ModBlocksRegistry.SNOW_SLAB.getDefaultState();
            }
        }
        setBlockState(world, pos,  slabState.with(CustomSlab.GENERATED, true));
    }

    private void placeTopSlab(WorldAccess world, BlockPos pos) {
        Boolean waterlogged = isTopStateWaterlogged(world, pos);
        BlockState blockAboveState = world.getBlockState(pos.up());

        // Retrieve the slab type based on the block below the current position
        BlockState slabState = ModSlabsMap.getSlabForBlock(blockAboveState.getBlock()).getDefaultState();

        if (slabState.getBlock().equals(Blocks.AIR)) {
            /* DEBUG
            System.out.println(blockBelowState);
            System.out.println(currentBlockState);
             */
            return;
        }
        if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
            slabState = ModBlocksRegistry.DIRT_SLAB.getDefaultState();
        }
        if (slabState.isOf(ModBlocksRegistry.WARPED_NYLIUM_SLAB) || slabState.isOf(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)) {
            slabState = ModBlocksRegistry.NETHERRACK_SLAB.getDefaultState();
        }
        setBlockState(world, pos, slabState.with(CustomSlab.GENERATED, true).with(Properties.SLAB_TYPE, SlabType.TOP).with(Properties.WATERLOGGED, waterlogged));
    }

    private void placeVegetationOnTop(WorldAccess world, BlockState currentBlockState, BlockState blockAboveState, BlockPos blockAbovePos) {
        if (ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.containsKey(currentBlockState.getBlock())) {
            if (!(currentBlockState.getBlock().equals(Blocks.SEAGRASS) && !blockAboveState.getBlock().equals(Blocks.WATER))) {
                setBlockState(world, blockAbovePos, ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.get(currentBlockState.getBlock()).getDefaultState());
            }
        }
    }

    private BlockState updateBottomWaterloggedState(BlockState currentBlockState, BlockState blockAboveState, BlockState slabState) {
        if (slabState.contains(Properties.WATERLOGGED)) {
            if (currentBlockState.isOf(Blocks.WATER) || blockAboveState.isOf(Blocks.WATER) || currentBlockState.isOf(Blocks.SEAGRASS)) {
                return slabState.with(Properties.WATERLOGGED, true);
            }
        }
        return slabState;
    }
}
