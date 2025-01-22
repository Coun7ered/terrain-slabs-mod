package net.countered.terrainslabs.block.customslabs.specialslabs.dimensions;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class NyliumSlab extends CustomSlab implements Fertilizable {

    public NyliumSlab(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, Boolean.FALSE)
                .with(GENERATED, Boolean.FALSE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    private static boolean stayAlive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        int i = ChunkLightProvider.getRealisticOpacity(Blocks.CRIMSON_NYLIUM.getDefaultState(), blockState, Direction.UP, blockState.getOpacity());
        return i < 15;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!stayAlive(state, world, pos)) {
            if (state.get(TYPE) == SlabType.TOP) {
                world.setBlockState(pos, ModBlocksRegistry.NETHERRACK_SLAB.getDefaultState().with(TYPE, SlabType.TOP), 3);
            }
            else if (state.get(TYPE) == SlabType.DOUBLE) {
                world.setBlockState(pos, ModBlocksRegistry.NETHERRACK_SLAB.getDefaultState().with(TYPE, SlabType.DOUBLE), 3);
            }
            else if (state.get(TYPE) == SlabType.BOTTOM){
                world.setBlockState(pos, ModBlocksRegistry.NETHERRACK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM), 3);
            }
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {

    }

    @Override
    public Fertilizable.FertilizableType getFertilizableType() {
        return Fertilizable.FertilizableType.NEIGHBOR_SPREADER;
    }
}
