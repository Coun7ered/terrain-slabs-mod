package net.countered.terrainslabs.block.customslabs.soilslabs;

import com.mojang.serialization.MapCodec;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class PathSlab extends CustomSlab {

    public PathSlab(Settings settings) {
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

    public static final MapCodec<PathSlab> CODEC = createCodec(PathSlab::new);

    @Override
    public MapCodec<PathSlab> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    protected static final VoxelShape BOTTOM_SHAPE_COL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    protected static final VoxelShape TOP_SHAPE_COL = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape DOUBLE_SHAPE_COL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> DOUBLE_SHAPE_COL;
            case TOP -> TOP_SHAPE_COL;
            default -> BOTTOM_SHAPE_COL;
        };
    }
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> DOUBLE_SHAPE_COL;
            case TOP -> TOP_SHAPE_COL;
            default -> BOTTOM_SHAPE_COL;
        };
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        SlabType slabType = state.get(TYPE);
        switch (slabType) {
            case DOUBLE:
                world.setBlockState(pos, ModBlocksRegistry.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.DOUBLE));
                break;
            case TOP:
                world.setBlockState(pos, ModBlocksRegistry.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.TOP));
                break;
            default:
                world.setBlockState(pos, ModBlocksRegistry.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM));
                break;
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);

        // Check if the block at the position is the same as this slab type
        if (blockState.isOf(this)) {
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, Boolean.FALSE);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM)
                    .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

            Direction direction = ctx.getSide();
            // Determine whether to place it as a bottom or top slab based on hit position
            if (direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5))) {
                return blockState2;
            } else {
                // Ensure the placement is valid; otherwise, push entities up
                return !blockState2.canPlaceAt(ctx.getWorld(), blockPos)
                        ? Block.pushEntitiesUpBeforeBlockChange(this.getDefaultState(), Blocks.DIRT.getDefaultState(), ctx.getWorld(), blockPos)
                        : blockState2.with(TYPE, SlabType.TOP);
            }
        }
    }


    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            tickView.scheduleBlockTick(pos, this, 1);
        }
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }
}

