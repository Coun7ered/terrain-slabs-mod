package net.countered.terrainslabs.block.customslabs.specialslabs;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MudSlab extends CustomSlab {
    protected static final VoxelShape BOTTOM_SHAPE_COL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    protected static final VoxelShape FULL_SHAPE_COL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape TOP_SHAPE_COL = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 14.0, 16.0);

    protected static final VoxelShape BOTTOM_SHAPE_OUT = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape TOP_SHAPE_OUT = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

    public MudSlab(Settings settings) {
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

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> FULL_SHAPE_COL;
            case TOP -> TOP_SHAPE_COL;
            default -> BOTTOM_SHAPE_COL;
        };
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> VoxelShapes.fullCube();
            case TOP -> TOP_SHAPE_OUT;
            default -> BOTTOM_SHAPE_OUT;
        };
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> VoxelShapes.fullCube();
            case TOP -> TOP_SHAPE_OUT;
            default -> BOTTOM_SHAPE_OUT;
        };
    }
    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        SlabType slabType = state.get(TYPE);
        return slabType == SlabType.DOUBLE ? 0.2F : 1F;
    }
}

