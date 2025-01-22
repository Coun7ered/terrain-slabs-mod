package net.countered.terrainslabs.block.ontopofslabs;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import static net.minecraft.block.SlabBlock.TYPE;

public class SnowOnTop extends SnowBlock {
    public SnowOnTop(Settings settings) {
        super(settings);
    }

    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
            VoxelShapes.empty(),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, -6.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, -4.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, -2.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 0.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 8.0, 16.0)
    };
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)-1];
    }

    @Override
    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.getBlock() instanceof SlabBlock) {
            return blockState.get(TYPE).equals(SlabType.BOTTOM);
        }
        return false;
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }
}
